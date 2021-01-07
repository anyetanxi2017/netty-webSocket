package com.sky.mychat.netty.executor.room;

import com.alibaba.fastjson.JSONObject;
import com.sky.mychat.constant.Command;
import com.sky.mychat.constant.ResultCodeEnum;
import com.sky.mychat.netty.executor.ChatExecutor;
import com.sky.mychat.util.JsonResult;
import com.sky.mychat.util.SessionUtil;
import io.netty.channel.Channel;
import org.springframework.stereotype.Service;

/**
 * @author tiankong
 * @date 2019/11/20 13:07
 */
@Service
public class QuitRoomExecutor extends ChatExecutor {
    public QuitRoomExecutor() {
        command = Command.QUIET_ROOM;
    }

    @Override
    public void execute(JSONObject param, Channel channel) {
        Integer roomId = param.getInteger("room_id");
        if (roomId == null) {
            sendMessage(channel, JsonResult.failed(ResultCodeEnum.VALIDATE_FAILED, command));
            return;
        }
        SessionUtil.quitRoom(roomId, channel);
        sendMessage(channel, JsonResult.success(command));
    }
}
