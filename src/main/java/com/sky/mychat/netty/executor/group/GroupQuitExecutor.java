package com.sky.mychat.netty.executor.group;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.sky.mychat.constant.Command;
import com.sky.mychat.constant.ResultCodeEnum;
import com.sky.mychat.netty.executor.ChatExecutor;
import com.sky.mychat.util.JsonResult;
import com.sky.mychat.util.SessionUtil;
import io.netty.channel.Channel;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author tiankong
 * @date 2019/11/19 14:31
 */
@Service
public class GroupQuitExecutor extends ChatExecutor {
    public GroupQuitExecutor() {
        command = Command.QUIET_GROUP;
    }

    @Override
    public void execute(JSONObject param, Channel channel) {
        Integer groupId = param.getInteger("groupId");
        String userIds = param.getString("userIds");
        if (groupId == null) {
            sendMessage(channel, JsonResult.failed(ResultCodeEnum.GROUP_ID_IS_NULL, command));
            return;
        }
        if (userIds == null) {
            sendMessage(channel, JsonResult.failed(ResultCodeEnum.USER_ID_IS_NULL, command));
            return;
        }
        Set<Integer> userIdSet = Arrays.stream(StrUtil.splitToInt(userIds, ',')).boxed().collect(Collectors.toSet());
        groupService.quitGroup(groupId, userIdSet);
        SessionUtil.getChannelGroup(groupId).remove(channel);
        sendMessage(channel, JsonResult.success(command));
    }
}
