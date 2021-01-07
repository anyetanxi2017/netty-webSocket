package com.sky.mychat.bo;

import com.sky.mychat.entiry.ChatGroupDo;
import io.netty.channel.group.ChannelGroup;
import lombok.Data;

/**
 * @author tiankong
 * @date 2019/11/19 18:21
 */
@Data
public class GroupInfo {
    private ChatGroupDo group;
    private ChannelGroup channelGroup;

    public GroupInfo(ChatGroupDo group, ChannelGroup channelGroup) {
        this.group = group;
        this.channelGroup = channelGroup;
    }
}
