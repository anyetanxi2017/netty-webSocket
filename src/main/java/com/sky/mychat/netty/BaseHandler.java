package com.sky.mychat.netty;

import com.alibaba.fastjson.JSONObject;
import com.sky.mychat.constant.Attributes;
import com.sky.mychat.bo.Session;
import com.sky.mychat.util.JsonResult;
import com.sky.mychat.util.SessionUtil;
import com.sky.mychat.util.ThreadUtil;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 聊天室基础类
 *
 * @author tiankong
 * @date 2019/11/18 14:24
 */
@Component
@Slf4j
public class BaseHandler {
    /**
     * 发送信息
     *
     * @param channel    channel
     * @param jsonResult jsonResult
     */
    protected void sendMessage(Channel channel, JsonResult jsonResult) {
        channel.writeAndFlush(new TextWebSocketFrame(JSONObject.toJSONString(jsonResult)));
    }


    /**
     * 群聊
     *
     * @param group      group
     * @param jsonResult 信息
     */
    protected void sendGroupMessage(ChannelGroup group, JsonResult jsonResult) {
        group.writeAndFlush(new TextWebSocketFrame(JSONObject.toJSONString(jsonResult)));
    }

    protected void remove(Channel channel) {
        ThreadUtil.getSingleton().submit(() -> {
            SessionUtil.WEB_SOCKET_SERVER_HAND_SHAKER.remove(channel.id().asLongText());
            Session session = channel.attr(Attributes.SESSION).get();
            log.info("已移除握手实例");
            if (session == null) {
                channel.close();
                return;
            }
            SessionUtil.ONLINE_USER_MAP.remove(session.getUserId());
            log.info("已移除握手实例,当前握手实例总数为:{}", SessionUtil.WEB_SOCKET_SERVER_HAND_SHAKER.size());
            log.info("userId为{}的用户已经退出聊天,当前在线人数为{}", channel.attr(Attributes.SESSION).get().getUserId(), SessionUtil.ONLINE_USER_MAP.size());
            channel.close();
        });
//        Iterator<Map.Entry<Integer, Channel>> iterator = SessionUtil.onlineUserMap.entrySet().iterator();
//        while (iterator.hasNext()) {
//            Map.Entry<Integer, Channel> next = iterator.next();
//            if (next.getValue() == ctx.channel()) {
//                log.info("正在移除握手实例...");
//                SessionUtil.webSocketServerHandshakerMap.remove(ctx.channel().id().asLongText());
//                log.info("已移除握手实例,当前握手实例总数为:{}", SessionUtil.webSocketServerHandshakerMap.size());
//                iterator.remove();
//                log.info("userId为{}的用户已经退出聊天,当前在线人数为{}", next.getKey(), SessionUtil.onlineUserMap.size());
//                break;
//            }
//        }
    }
}
