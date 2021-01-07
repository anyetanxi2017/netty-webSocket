package com.sky.mychat.netty.executor.room;

import com.alibaba.fastjson.JSONObject;
import com.sky.mychat.bo.RoomInfo;
import com.sky.mychat.constant.Command;
import com.sky.mychat.constant.ResultCodeEnum;
import com.sky.mychat.entiry.ChatRoomDo;
import com.sky.mychat.netty.executor.ChatExecutor;
import com.sky.mychat.service.ChatRoomService;
import com.sky.mychat.util.JsonResult;
import com.sky.mychat.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author tiankong
 * @date 2019/11/20 11:07
 */
@Service
public class CreateRoomExecutor extends ChatExecutor {
    @Resource
    private ChatRoomService roomService;

    public CreateRoomExecutor() {
        command = Command.CREATE_ROOM;
    }

    @Override
    public void execute(JSONObject param, Channel channel) {
        String name = param.getString("name");
        String explain = param.getString("explain");
        Byte roomType = param.getByte("room_type");
        if (name == null || explain == null || roomType == null) {
            sendMessage(channel, JsonResult.failed(ResultCodeEnum.VALIDATE_FAILED));
            return;
        }
        ChatRoomDo chatRoomDo = new ChatRoomDo(name, explain, roomType);
        roomService.save(chatRoomDo);
        SessionUtil.ROOM_MAP.put(chatRoomDo.getId(), new RoomInfo(chatRoomDo, new DefaultChannelGroup(GlobalEventExecutor.INSTANCE)));
        sendMessage(channel, JsonResult.success("创建房间成功", command));
    }
}
