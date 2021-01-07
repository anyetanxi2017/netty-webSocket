package com.sky.mychat.netty.executor.room;

import com.alibaba.fastjson.JSONObject;
import com.sky.mychat.bo.Session;
import com.sky.mychat.constant.Command;
import com.sky.mychat.constant.ResultCodeEnum;
import com.sky.mychat.entiry.ChatRoomDo;
import com.sky.mychat.entiry.ChatRoomMessageDo;
import com.sky.mychat.netty.executor.ChatExecutor;
import com.sky.mychat.service.ChatRoomMessageService;
import com.sky.mychat.service.ChatRoomService;
import com.sky.mychat.util.JsonResult;
import com.sky.mychat.util.SessionUtil;
import io.netty.channel.Channel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author tiankong
 * @date 2019/11/20 13:40
 */
@Service
public class RoomChatMessage extends ChatExecutor {
    @Resource
    private ChatRoomService chatRoomService;
    @Resource
    private ChatRoomMessageService chatRoomMessageService;

    public RoomChatMessage() {
        command = Command.ROOM_CHAT;
    }

    @Override
    public void execute(JSONObject param, Channel channel) {
        Integer roomId = param.getInteger("room_id");
        String content = param.getString("content");
        if (roomId == null || content == null) {
            sendMessage(channel, JsonResult.failed(ResultCodeEnum.VALIDATE_FAILED, command));
            return;
        }
        Session session = SessionUtil.getSession(channel);
        Date time = new Date();
        ChatRoomMessageDo roomMessage = new ChatRoomMessageDo(roomId, content, session, time);
        sendGroupMessage(SessionUtil.getRoomGroup(roomId), JsonResult.success(roomMessage, command));
        ChatRoomDo chatRoomDo = chatRoomService.getByRoomId(roomId);
        if (chatRoomDo.getSaveDb()) {
            chatRoomMessageService.save(roomMessage);
        }
    }
}
