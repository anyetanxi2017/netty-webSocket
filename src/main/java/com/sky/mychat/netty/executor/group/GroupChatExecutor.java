package com.sky.mychat.netty.executor.group;

import com.alibaba.fastjson.JSONObject;
import com.sky.mychat.constant.Attributes;
import com.sky.mychat.constant.Command;
import com.sky.mychat.constant.ResultCodeEnum;
import com.sky.mychat.bo.Session;
import com.sky.mychat.entiry.ChatGroupDo;
import com.sky.mychat.entiry.ChatGroupMessageDo;
import com.sky.mychat.netty.executor.ChatExecutor;
import com.sky.mychat.netty.response.ChatMessageResponse;
import com.sky.mychat.util.JsonResult;
import com.sky.mychat.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author tiankong
 * @date 2019/11/19 15:13
 */
@Service
public class GroupChatExecutor extends ChatExecutor {
    public GroupChatExecutor() {
        command = Command.GROUP_CHAT;
    }

    @Override
    public void execute(JSONObject param, Channel channel) {
        Integer groupId = param.getInteger("groupId");
        String content = param.getString("content");
        if (groupId == null) {
            sendMessage(channel, JsonResult.failed(ResultCodeEnum.GROUP_ID_IS_NULL, command));
            return;
        }
        if (content == null) {
            sendMessage(channel, JsonResult.failed(ResultCodeEnum.CONTENT_IS_NULL, command));
            return;
        }

        ChannelGroup channelGroup;
        try {
            channelGroup = SessionUtil.getChannelGroup(groupId);
        } catch (NullPointerException e) {
            sendMessage(channel, JsonResult.failed(ResultCodeEnum.GROUP_NOT_FOUND, command));
            return;
        }
        Session session = channel.attr(Attributes.SESSION).get();
        Date date = new Date();
        ChatMessageResponse chatMessageResponse = new ChatMessageResponse(session, content, date);
        sendGroupMessage(channelGroup, JsonResult.success(chatMessageResponse, command));
        ChatGroupMessageDo message = new ChatGroupMessageDo(groupId, session.getUserId(), content, date);
        ChatGroupDo group = groupService.getByGroupId(groupId);
        if (group.getSaveDb()) {
            groupService.saveGroupMessage(message);
        }
    }
}
