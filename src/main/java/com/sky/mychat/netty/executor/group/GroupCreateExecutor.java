package com.sky.mychat.netty.executor.group;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.sky.mychat.bo.GroupInfo;
import com.sky.mychat.constant.Attributes;
import com.sky.mychat.constant.Command;
import com.sky.mychat.entiry.ChatGroupDo;
import com.sky.mychat.entiry.UmsUserDo;
import com.sky.mychat.netty.executor.ChatExecutor;
import com.sky.mychat.util.JsonResult;
import com.sky.mychat.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author tiankong
 * @date 2019/11/18 18:10
 */
@Service
public class GroupCreateExecutor extends ChatExecutor {
    public GroupCreateExecutor() {
        command = Command.CREATE_GROUP;
    }

    @Override
    public void execute(JSONObject param, Channel channel) {
        String groupName = param.getString("groupName");
        String userIds = param.getString("userIds");
        if (groupName == null) {
            sendMessage(channel, JsonResult.failed("群组名不能为空", command));
            return;
        }
        if (userIds == null) {
            sendMessage(channel, JsonResult.failed("用户Id不能为空", command));
            return;
        }
        if (groupService.getByGroupName(groupName) != null) {
            sendMessage(channel, JsonResult.failed(StrUtil.format("{}群组已存在,请不要重复创建!", groupName), command));
            return;
        }
        Date date = new Date();
        Set<Integer> userSet = Arrays.stream(StrUtil.splitToInt(userIds, ",")).boxed().collect(Collectors.toSet());
        for (Integer userId : userSet) {
            UmsUserDo umsUserDo = userService.getByUserId(userId);
            if (umsUserDo == null) {
                sendMessage(channel, JsonResult.failed(StrUtil.format("{}用户不存在", userId), command));
                return;
            }
        }
        // 加入自己的ID
        userSet.add(channel.attr(Attributes.SESSION).get().getUserId());
        ChatGroupDo groupDo = new ChatGroupDo();
        groupDo.setCreateTime(date);
        groupDo.setModifiedTime(date);
        groupDo.setGroupName(groupName);
        groupDo.setIcon("/test.png");
        groupDo.setMaxNumberOfPeople((short) 200);
        groupDo.setSaveDb(false);
        groupDo.setType((byte) 0);
        groupService.createGroup(groupDo, userSet);
        SessionUtil.GROUP_MAP.put(groupDo.getId(), new GroupInfo(groupDo, new DefaultChannelGroup(GlobalEventExecutor.INSTANCE)));
        sendMessage(channel, JsonResult.success("创建群组成功", command));
    }
}
