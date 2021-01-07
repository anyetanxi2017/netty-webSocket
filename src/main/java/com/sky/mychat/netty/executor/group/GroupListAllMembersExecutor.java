package com.sky.mychat.netty.executor.group;

import com.alibaba.fastjson.JSONObject;
import com.sky.mychat.constant.Command;
import com.sky.mychat.netty.executor.ChatExecutor;
import com.sky.mychat.util.JsonResult;
import io.netty.channel.Channel;
import org.springframework.stereotype.Service;

/**
 * @author tiankong
 * @date 2019/11/19 12:59
 */
@Service
public class GroupListAllMembersExecutor extends ChatExecutor {
    public GroupListAllMembersExecutor() {
        command = Command.LIST_GROUP_ALL_MEMBERS;
    }

    @Override
    public void execute(JSONObject param, Channel channel) {
        Integer groupId = param.getInteger("groupId");
        sendMessage(channel, JsonResult.success(groupService.listGroupMembersAll(groupId)));
    }
}
