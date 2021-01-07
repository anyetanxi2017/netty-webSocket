# MyChat
## 简介
Netty WebSocket聊天室.使用前后端思想,本项目只提供后端接口.没有前端的展示页面.要测试可以使用网上的WebSocket测试工具 如[easyswoole](http://www.easyswoole.com/)
实现了基础的聊天功能,如推送消息/单聊/群聊/房间聊天等. 命令方面使用了拆分,单个命令只执行一件事情,方便扩展.

## 使用技术
Spring Boot 整合Netty,各种依赖注入.具体使用技术如下
- Spring Boot
- Netty 使用Netty的WebSocket协议
- MyBatis
- Ehcache 减少与数据库的交互
- Spring Security 方便用户注册,登陆等不需要WebSocket的可以操作
- Jwt token认证
- druid数据库连接池 用于监控数据库
- hutool 工具包
- fastjson json处理工具
- lombok 代码优化
- log4j2 日志框架

## 项目结构说明
对主要的包类进行说明
- bo 业务对象包
	- CurrentUserDetails Spring Security 要求使用的当前用户信息
	- GroupInfo 群组信息(群组信息+群组的ChannelGroup)
	- RoomInfo 房间信息(房间信息+房间的ChannelGroup)
	- Session 保存在Channel里面的用户信息(用户ID,用户名,头像)
- component 组件类
	- JwtAuthenticationTokenFilter 正常http请求 Jwt Token认证过渡器
	- RestAuthenticationEntryPoint Spring Security使用,登录或者Token失效时的自定义返回结果
	- RestfulAccessDeniedHandler  Spring Security使用, 无权限时自定义返回结果
- config 项目配置包
	- MybatisConfig  Mybatis mapper的配置
	- NettyConfig  自定义的netty.xml 需要配置到框架中
	- SecurityConfig Spring Security的配置
- constant 常量包
	- Attributes netty使用 在用户channel中绑定用户Session需要使用到
	- Comamnd 命令接口 包含了聊天室使用的所有命令
- controller 正常http请求的控制层
- dto 数据传输对象包
- entiry 实体包
- mapper 数据库 mapper 映射包
- service 服务层 
- util 工具包
	- JwtTokenUtil Jwt工具类(生成token,获取token等)
	- SessionUtil 聊天室工具类(保存所有在线用户channel,所有群组channelGroup,所有房间channelGroup,提供获取注册channel等方法)
- netty netty核心包
	- executor 命令执行包(业务相关的命令在此包中)
		- base 包 处理 注册用户channel 和单聊
		- group 包 处理 群聊相关业务
		- room 包 处理 房间聊天相关业务
		- Executor 命令执行抽象类 (包含一个执行抽象方法,和一个命令属性)
		- ChatExecutor 聊天执行基础类(作用是 相关服务层可以在此类中注入,) 
	- handle netty程序相关的业务在此包中
	- notice 通知推送相关业务在此包中
	- response 聊天信息响应重用类包
	- BaseHadnler 聊天基础处理类(包含三个方法,发送单聊消息,发送群聊消息(也可以发送房间聊天消息),断开连接)
	- NettyServer NettyServer服务器类
	- NettyServerApplication 聊天服务器启动类

## 命令
命令接口类 public interface Command
```
    // ------------------------------房间相关--------------------------------------
    /**
     * 查看所有房间
     */
    byte LIST_ROOM = 14;
    /**
     * 退出房间
     */
    byte QUIET_ROOM = 13;
    /**
     * 房间聊天
     */
    byte ROOM_CHAT = 12;
    /**
     * 查看房间用户
     */
    byte LIST_ROOM_MEMBERS = 11;
    /**
     * 进入房间
     */
    byte ENTER_ROOM = 10;
    /**
     * 创建房间
     */
    byte CREATE_ROOM = 9;

    //  ---------------------------群聊相关--------------------------------
    /**
     * 退出群聊
     */
    byte QUIET_GROUP = 8;
    /**
     * 加入群聊
     */
    byte JOIN_GROUP = 7;
    /**
     * 查看所有群成员
     */
    byte LIST_GROUP_ALL_MEMBERS = 6;
    /**
     * 查看群成员
     */
    byte LIST_GROUP_MEMBERS = 5;
    /**
     * 创建群聊
     */
    byte CREATE_GROUP = 4;
    /**
     * 群聊
     */
    byte GROUP_CHAT = 3;

    // --------------------------基本------------------------------
    /**
     * 单聊
     */
    byte SINGLE_CHAT = 2;
    /**
     * 注册用户channel
     */
    byte REGISTER = 1;
    /**
     * 心跳
     */
    byte HEARTBEAT = 0;
```
### 心跳检测
请求
```
{"command":0}
```
响应

```
{
  "message":"服务端已收到你的心跳请求",
  "state":2000,
  "type":0
}
```
### 登录 绑定用户Channel
请求

```
{
  "userId":1,
  "command":1
}

```
响应

```
{
  "message":"您已连接成功!",
  "state":2000,
  "type":1
}
```

### 单聊
请求

```
{
  "toUserId":"2",
  "content":"hello",
  "command":2
}
```

响应

```
{
  "data":{
    "content":"hello",
    "fromUserIcon":"/test.png",
    "fromUserId":1,
    "fromUserName":"sky"
  },
  "message":"success",
  "state":2000,
  "type":2
}

```
### 创建群聊
请求

```
{
  "groupName":"圆圆的群聊",
  "userIds":"2",
  "command":4
}
```
响应

```
{
  "message":"创建群组成功",
  "state":2000,
  "type":4
}
```
### 查看在线群成员
请求

```
{
  "groupId":14,
  "command":5
}
```

响应

```
{
  "data":[
    {
      "avatar":"/test.png",
      "userId":1,
      "username":"sky"
    },
    {
      "avatar":"/test.png",
      "userId":2,
      "username":"yy"
    }
  ],
  "message":"success",
  "state":2000,
  "type":0
}
```
### 查看群所有成员
请求

```
{
  "groupId":14,
  "command":6
}
```
响应

```
{
  "data":[
    {
      "avatar":"/test.png",
      "userId":1,
      "username":"sky"
    },
    {
      "avatar":"/test.png",
      "userId":2,
      "username":"yy"
    },
    {
      "avatar":"/test.png",
      "userId":3,
      "username":"anyetanxi"
    }
  ],
  "message":"success",
  "state":2000,
  "type":0
}
```
### 退群
请求

```
{
    "groupId":14,
    "userIds":"3",  
    "command":8
}
```
> 多个用户userIds 用逗号隔开

响应

```
{
  "message":"success",
  "state":2000,
  "type":8
}
```
### 加群
请求

```
{
  "groupId":14,
  "userIds":"3",
  "command":7
}
```
响应

```
{
  "message":"success",
  "state":2000,
  "type":7
}
```
### 群聊
请求

```
{
  "groupId":14,
  "content":"大家好!",
  "command":3
}
```
响应

```
{
  "data":{
    "content":"大家好!",
    "fromUserIcon":"/test.png",
    "fromUserId":2,
    "fromUserName":"yy"
  },
  "message":"success",
  "state":2000,
  "type":3
}

```
### 创建房间
请求

```
{
  "command":9,
  "name":"圆圆的房间",
  "explain":"圆圆的房间",
  "room_type":1
}

```
响应

```
{
  "message":"创建房间成功",
  "state":2000,
  "type":9
}
```
### 进入房间
请求

```
{
  "command":10,
  "room_id":2
}
```

响应

```
{
  "message":"success",
  "state":2000,
  "type":10
}
```

### 查看房间所有用户
请求

```
{
  "command":11,
  "room_id":2
}
```

响应

```
{
  "data":[
    {
      "avatar":"/test.png",
      "userId":2,
      "username":"yy"
    }
  ],
  "message":"success",
  "state":2000,
  "type":0
}
```
### 房间聊天
请求

```
{
  "command":12,
  "room_id":2,
  "content":"房间里的朋友们大家好!"
}

```
响应

```
{
  "data":{
    "content":"房间里的朋友们大家好!",
    "createTime":1574303918676,
    "fromUserId":2,
    "roomId":2
  },
  "message":"success",
  "state":2000,
  "type":12
}
```

### 退出房间
请求

```
{
  "command":13,
  "room_id":2
}

```
响应

```
{
  "message":"success",
  "state":2000,
  "type":13
}
```

## 主要配置文件说明
### application.yml
```
jwt:
  tokenHeader: Authorization 
  secret: mySecret 
  expiration: 60 #过期时间 单位s
  tokenHead: Bearer 
```  
### ehcache.xml
缓存配置文件

### log4j.xml
日志配置文件

### netty.xml
自定义netty bossGroup workerGroup 的配置文件,方便扩展.



end
