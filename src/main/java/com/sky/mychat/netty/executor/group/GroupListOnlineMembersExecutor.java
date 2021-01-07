package com.sky.mychat.netty.executor.group;

import com.alibaba.fastjson.JSONObject;
import com.sky.mychat.constant.Command;
import com.sky.mychat.dto.UserInfo;
import com.sky.mychat.netty.executor.ChatExecutor;
import com.sky.mychat.util.JsonResult;
import com.sky.mychat.util.SessionUtil;
import io.netty.channel.Channel;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author tiankong
 * @date 2019/11/19 11:41
 */
@Service
public class GroupListOnlineMembersExecutor extends ChatExecutor {
    public GroupListOnlineMembersExecutor() {
        command = Command.LIST_GROUP_MEMBERS;
    }

    @Override
    public void execute(JSONObject param, Channel channel) {
        Integer groupId = param.getInteger("groupId");
        List<Integer> userIdList = SessionUtil.listGroupMemberIds(groupId);
        List<UserInfo> userList = userService.getUserByIdList(userIdList);
        sendMessage(channel, JsonResult.success(userList, command));
    }
}
