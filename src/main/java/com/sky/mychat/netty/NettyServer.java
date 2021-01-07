package com.sky.mychat.netty;

import com.sky.mychat.bo.GroupInfo;
import com.sky.mychat.bo.RoomInfo;
import com.sky.mychat.entiry.ChatGroupDo;
import com.sky.mychat.entiry.ChatRoomDo;
import com.sky.mychat.netty.notice.IMNotice;
import com.sky.mychat.service.ChatGroupService;
import com.sky.mychat.service.ChatRoomService;
import com.sky.mychat.util.SessionUtil;
import com.sky.mychat.util.ThreadUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author tiankong
 * @date 2019/11/17 18:52
 */
@Slf4j
@Component
public class NettyServer implements Runnable {
    @Value("${webSocket.server.port}")
    private Integer port;
    @Resource(name = "bossGroup")
    private EventLoopGroup bossGroup;
    @Resource(name = "workerGroup")
    private EventLoopGroup workerGroup;
    @Resource
    private ServerBootstrap serverBootstrap;
    @Resource(name = "childChannelHandler")
    private ChannelHandler childChannelHandler;
    @Resource
    private IMNotice imNotice;
    @Resource
    private ChatGroupService groupService;
    @Resource
    private ChatRoomService roomService;
    private ChannelFuture channelFuture;

    @Override
    public void run() {
        try {
            long begin = System.currentTimeMillis();
            // boss负责客户端的TCP连接请求，worker负责与客户端之间的读写操作
            serverBootstrap.group(bossGroup, workerGroup)
                    // 配置客户端channel类型
                    .channel(NioServerSocketChannel.class)
                    // 配置TCP参数，握手字符串长度设置
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    // TCP_NODELAYT算法，尽可能发送大块数据，减少充斥的小块数据。
                    .option(ChannelOption.TCP_NODELAY, true)
                    // 开启心跳包活机制，客户端，服务端建立连接处于ESTABLISHED状态，超过2小时没有交流，机制会被启动。
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    // 配置固定长度接收缓存区分配器
                    .childOption(ChannelOption.RCVBUF_ALLOCATOR, new FixedRecvByteBufAllocator(592048))
                    // 绑定I/O事件的处理类
                    .childHandler(childChannelHandler);
            long end = System.currentTimeMillis();
            channelFuture = serverBootstrap.bind(port).addListener(future -> {
                boolean success = future.isSuccess();
                if (success) {
                    log.info("Netty服务器启动完成,耗时{}ms,已绑定端口{}阻塞式等候客户端连接", end - begin, port);
                    // 启动通知线程
                    ThreadUtil.getSingleton().submit(imNotice);
                    // 初始化群组
                    groupService.listAllChatGroup().forEach(chatGroupDo -> SessionUtil.GROUP_MAP.put(chatGroupDo.getId(), new GroupInfo(chatGroupDo, new DefaultChannelGroup(GlobalEventExecutor.INSTANCE))));
                    // 初始化房间
                    roomService.listAll().forEach(room -> SessionUtil.ROOM_MAP.put(room.getId(), new RoomInfo(room, new DefaultChannelGroup(GlobalEventExecutor.INSTANCE))));
                }
            }).sync();
        } catch (Exception e) {
            log.error(e.getMessage());
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
            e.printStackTrace();
        }
    }

    void close() {
        channelFuture.channel().close();
        Future<?> bossFuture = bossGroup.shutdownGracefully();
        Future<?> workerFuture = workerGroup.shutdownGracefully();
        try {
            bossFuture.await();
            workerFuture.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
