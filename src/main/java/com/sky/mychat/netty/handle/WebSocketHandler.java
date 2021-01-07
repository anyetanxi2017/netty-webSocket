package com.sky.mychat.netty.handle;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONObject;
import com.sky.mychat.constant.Attributes;
import com.sky.mychat.bo.Session;
import com.sky.mychat.netty.executor.Executor;
import com.sky.mychat.netty.executor.ExecutorManager;
import com.sky.mychat.netty.executor.base.RegisterExecutor;
import com.sky.mychat.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * @author tiankong
 * @date 2019/11/17 18:51
 */
@Component
@ChannelHandler.Sharable
@Slf4j
public class WebSocketHandler extends SimpleChannelInboundHandler<WebSocketFrame> {
    @Resource(name = "executorManager")
    private ExecutorManager commandManager;
    @Resource
    private CheckLoginHandler checkLoginHandler;

    /**
     * 读取完连接的消息后，对消息进行处理
     * 这里主要是处理WebSocket请求。
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame msg) throws Exception {
        handlerWebSocketFrame(ctx, msg);
    }

    /**
     * 处理WebSocketFrame
     */
    private void handlerWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) throws Exception {
        // 关闭请求
        if (frame instanceof CloseWebSocketFrame) {
            WebSocketServerHandshaker handShaker = SessionUtil.WEB_SOCKET_SERVER_HAND_SHAKER.get(ctx.channel().id().asLongText());
            if (handShaker == null) {
                sendErrorMessage(ctx, "不存在的客户端连接！");
            } else {
                handShaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
            }
            return;
        }
        if (!(frame instanceof TextWebSocketFrame)) {
            sendErrorMessage(ctx, "仅支持文件格式，不支持二进制消息");
        }
        // 客户端发送过来的消息
        String request = ((TextWebSocketFrame) frame).text();
        JSONObject param = null;
        try {
            param = JSONObject.parseObject(request);
        } catch (Exception e) {
            sendErrorMessage(ctx, "JSON字符串转换出错！");
            e.printStackTrace();
        }
        assert param != null;
        Byte command = param.getByte("command");
        Executor executor = commandManager.getCommand(command);
        if (executor == null) {
            sendErrorMessage(ctx, "命令不存在");
            return;
        }
        if (executor instanceof RegisterExecutor) {
            executor.execute(param, ctx.channel());
            return;
        }
        // 检测用户是否登录
        checkLoginHandler.channelRead0(ctx, frame);
        if (null != ctx.channel().attr(Attributes.IS_LOGIN).get()) {
            ctx.channel().writeAndFlush(new TextWebSocketFrame("请登录后操作"));
            ctx.channel().close();
            SessionUtil.WEB_SOCKET_SERVER_HAND_SHAKER.remove(ctx.channel().id().asLongText());
            return;
        }
        executor.execute(param, ctx.channel());
    }

    private void sendErrorMessage(ChannelHandlerContext ctx, String msg) {
        ctx.channel().writeAndFlush(new TextWebSocketFrame(JSONUtils.toJSONString(msg)));
    }

    /**
     * 客户端断开连接
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SessionUtil.WEB_SOCKET_SERVER_HAND_SHAKER.remove(ctx.channel().id().asLongText());
        Session session = ctx.channel().attr(Attributes.SESSION).get();
        if (session == null) {
            ctx.channel().close();
            return;
        }
        SessionUtil.ONLINE_USER_MAP.remove(session.getUserId());
        log.info("已移除握手实例,当前握手实例总数为:{}", SessionUtil.WEB_SOCKET_SERVER_HAND_SHAKER.size());
        log.info("userId为{}的用户已经退出聊天,当前在线人数为{}", ctx.channel().attr(Attributes.SESSION).get().getUserId(), SessionUtil.ONLINE_USER_MAP.size());
        ctx.channel();
    }

    /**
     * 异常处理： 关闭channel
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Channel channel = ctx.channel();
        SessionUtil.WEB_SOCKET_SERVER_HAND_SHAKER.remove(channel.id().asLongText());
        Session session = channel.attr(Attributes.SESSION).get();
        if (session != null) {
            SessionUtil.ONLINE_USER_MAP.remove(session.getUserId());
        }
        cause.printStackTrace();
        ctx.close();
    }
}
