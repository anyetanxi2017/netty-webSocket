package com.sky.mychat.netty.handle;

import com.sky.mychat.constant.Attributes;
import com.sky.mychat.constant.SystemConfig;
import com.sky.mychat.bo.Session;
import com.sky.mychat.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.springframework.stereotype.Component;

/**
 * 心跳监听器
 *
 * @author tiankong
 * @date 2019/11/18 13:05
 */
@Component
@ChannelHandler.Sharable
public class IMIdleStateAdapter extends ChannelInboundHandlerAdapter {
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleVent = (IdleStateEvent) evt;
            // 读
            if (idleVent.state() == IdleState.READER_IDLE) {
                System.out.println(SystemConfig.HEARTBEAT + "秒内未读到数据，关闭连接");
                WebSocketServerHandshaker handShaker = SessionUtil.WEB_SOCKET_SERVER_HAND_SHAKER.get(ctx.channel().id().asLongText());
                if (handShaker != null) {
                    SessionUtil.WEB_SOCKET_SERVER_HAND_SHAKER.remove(ctx.channel().id().asLongText());
                }
                Session session = ctx.channel().attr(Attributes.SESSION).get();
                if (session != null && SessionUtil.ONLINE_USER_MAP.get(session.getUserId()) != null) {
                    SessionUtil.ONLINE_USER_MAP.remove(session.getUserId());
                }
                ctx.channel().close();
            }
            // 写
            else if (idleVent.state() == IdleState.WRITER_IDLE) {

            }
            // 全部
            else if (idleVent.state() == IdleState.ALL_IDLE) {

            }
            super.userEventTriggered(ctx, evt);
        }
    }
}
