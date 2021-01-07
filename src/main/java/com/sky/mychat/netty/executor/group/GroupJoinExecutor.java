package com.sky.mychat.netty.executor.group;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.sky.mychat.constant.Command;
import com.sky.mychat.constant.ResultCodeEnum;
import com.sky.mychat.entiry.ChatGroupDo;
import com.sky.mychat.entiry.ChatUserGroupRelationDo;
import com.sky.mychat.entiry.UmsUserDo;
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
 * @date 2019/11/19 13:34
 */
@Service
public class GroupJoinExecutor extends ChatExecutor {
    public GroupJoinExecutor() {
        command = Command.JOIN_GROUP;
    }

    @Override
    public void execute(JSONObject param, Channel channel) {
        Integer groupId = param.getInteger("groupId");
        String userIds = param.getString("userIds");
        // 判断参数
        if (groupId == null) {
            sendMessage(channel, JsonResult.failed(ResultCodeEnum.GROUP_ID_IS_NULL, command));
            return;
        }
        // 判断参数
        if (userIds == null) {
            sendMessage(channel, JsonResult.failed(ResultCodeEnum.USER_ID_IS_NULL, command));
            return;
        }
        ChatGroupDo groupDo = groupService.getByGroupId(groupId);
        // 判断群组是否存在
        if (groupDo == null) {
            sendMessage(channel, JsonResult.failed(ResultCodeEnum.GROUP_NOT_FOUND, command));
            return;
        }
        Set<Integer> userIdSet = Arrays.stream(StrUtil.splitToInt(userIds, ",")).boxed().collect(Collectors.toSet());
        // 判断用户是否存在
        // 判断用户是否已经加入群组
        for (Integer userId : userIdSet) {
            UmsUserDo byUserId = userService.getByUserId(userId);
            if (byUserId == null) {
                sendMessage(channel, JsonResult.failed("加入群组失败!用户" + userId + "不存在", command));
                return;
            }
            ChatUserGroupRelationDo chatUserGroupRelationDo = groupService.getUserGroupRelation(userId, groupId);
            if (chatUserGroupRelationDo != null) {
                sendMessage(channel, JsonResult.failed("加入群组失败!用户" + byUserId.getUsername() + "请不要重复加入", command));
                return;
            }
            Channel userChannel = SessionUtil.getUserChannel(userId);
            if (userChannel != null) {
                SessionUtil.joinGroup(groupId, userChannel);
            }
        }
        groupService.joinGroup(groupId, userIdSet);
        sendMessage(channel, JsonResult.success(command));

    }
}
