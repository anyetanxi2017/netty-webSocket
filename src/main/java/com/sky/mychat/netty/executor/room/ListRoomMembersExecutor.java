package com.sky.mychat.netty.executor.room;

import com.alibaba.fastjson.JSONObject;
import com.sky.mychat.constant.Command;
import com.sky.mychat.constant.ResultCodeEnum;
import com.sky.mychat.dto.UserInfo;
import com.sky.mychat.netty.executor.ChatExecutor;
import com.sky.mychat.util.JsonResult;
import com.sky.mychat.util.SessionUtil;
import io.netty.channel.Channel;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author tiankong
 * @date 2019/11/20 13:27
 */
@Service
public class ListRoomMembersExecutor extends ChatExecutor {
    public ListRoomMembersExecutor() {
        command = Command.LIST_ROOM_MEMBERS;
    }

    @Override
    public void execute(JSONObject param, Channel channel) {
        Integer roomId = param.getInteger("room_id");
        if (roomId == null) {
            sendMessage(channel, JsonResult.failed(ResultCodeEnum.VALIDATE_FAILED, command));
        }
        List<UserInfo> list = SessionUtil.listRoomMembers(roomId);
        sendMessage(channel, JsonResult.success(list, command));
    }
}
