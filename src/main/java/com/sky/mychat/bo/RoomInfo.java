package com.sky.mychat.bo;

import com.sky.mychat.entiry.ChatRoomDo;
import io.netty.channel.group.ChannelGroup;
import lombok.Data;

/**
 * @author tiankong
 * @date 2019/11/20 11:22
 */
@Data
public class RoomInfo {
    private ChatRoomDo chatRoomDo;
    private ChannelGroup channelGroup;

    public RoomInfo() {
    }

    public RoomInfo(ChatRoomDo chatRoomDo, ChannelGroup channelGroup) {
        this.chatRoomDo = chatRoomDo;
        this.channelGroup = channelGroup;
    }
}
