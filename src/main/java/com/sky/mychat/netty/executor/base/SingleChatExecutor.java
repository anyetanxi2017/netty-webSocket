package com.sky.mychat.netty.executor.base;

import com.alibaba.fastjson.JSONObject;
import com.sky.mychat.constant.Attributes;
import com.sky.mychat.constant.Command;
import com.sky.mychat.constant.ResultCodeEnum;
import com.sky.mychat.constant.SystemConfig;
import com.sky.mychat.entiry.ChatSingleMessageDo;
import com.sky.mychat.bo.Session;
import com.sky.mychat.netty.executor.ChatExecutor;
import com.sky.mychat.netty.response.ChatMessageResponse;
import com.sky.mychat.service.ChatSingleMessageService;
import com.sky.mychat.util.JsonResult;
import com.sky.mychat.util.SessionUtil;
import com.sky.mychat.util.ThreadUtil;
import io.netty.channel.Channel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author tiankong
 * @date 2019/11/18 14:43
 */
@Service
public class SingleChatExecutor extends ChatExecutor {
    public SingleChatExecutor() {
        command = Command.SINGLE_CHAT;
    }

    @Resource
    public ChatSingleMessageService singleMessageService;

    @Override
    public void execute(JSONObject param, Channel channel) {
        ThreadUtil.getSingleton().submit(() -> {
            Integer toUserId = param.getInteger("toUserId");
            String content = param.getString("content");
            Session session = channel.attr(Attributes.SESSION).get();
            if (session == null) {
                sendMessage(channel, JsonResult.failed(ResultCodeEnum.USER_NOT_REGISTERED, command));
                remove(channel);
                return;
            }
            Channel toUserChannel = SessionUtil.ONLINE_USER_MAP.get(toUserId);
            if (toUserChannel == null) {
                sendMessage(channel, JsonResult.failed(ResultCodeEnum.USER_NOT_ONLINE, command));
                return;
            }
            Date time = new Date();
            sendMessage(toUserChannel, JsonResult.success(new ChatMessageResponse(session, content, time), command));
            if (SystemConfig.SINGLE_MESSAGE_SAVE_DB) {
                singleMessageService.save(new ChatSingleMessageDo(session.getUserId(), toUserId, content, time));
            }
        });
    }
}
