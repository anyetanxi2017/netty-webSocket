package com.sky.mychat.netty.executor.base;

import com.alibaba.fastjson.JSONObject;
import com.sky.mychat.constant.Attributes;
import com.sky.mychat.constant.Command;
import com.sky.mychat.constant.ResultCodeEnum;
import com.sky.mychat.entiry.ChatGroupDo;
import com.sky.mychat.entiry.UmsUserDo;
import com.sky.mychat.bo.Session;
import com.sky.mychat.netty.executor.ChatExecutor;
import com.sky.mychat.util.JsonResult;
import com.sky.mychat.util.SessionUtil;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 注册用户channel
 *
 * @author tiankong
 * @date 2019/11/17 20:00
 */
@Service
@Slf4j
public class RegisterExecutor extends ChatExecutor {
    public RegisterExecutor() {
        this.command = Command.REGISTER;
    }

    @Override
    public void execute(JSONObject param, Channel channel) {
        Integer userId = param.getInteger("userId");
        boolean checkOnline = checkOnline(userId, channel);
        if (checkOnline) {
            sendMessage(channel, JsonResult.failed(ResultCodeEnum.USER_LOGGED_IN, command));
            channel.close();
            return;
        }
        UmsUserDo user = userService.getByUserId(userId);
        if (user == null) {
            sendMessage(channel, JsonResult.failed(ResultCodeEnum.USER_NOT_FOND, command));
            return;
        }
        SessionUtil.registerUserChannel(userId, channel);
        channel.attr(Attributes.SESSION).set(new Session(user.getId(), user.getUsername(), user.getAvatar(), jwtTokenUtil.generateToken(user.getUsername())));
        sendMessage(channel, JsonResult.success("您已连接成功!", command));
        // 加入群聊
        List<ChatGroupDo> userGroupList = groupService.getGroupByUserId(userId);
        for (ChatGroupDo groupDo : userGroupList) {
            SessionUtil.getChannelGroup(groupDo.getId()).add(channel);
            log.info("用户{}已进入群聊,群房间名为{}", user.getUsername(), groupDo.getGroupName());
        }
        log.info("用户{} 已登记到在线用户表,当前在线人数为:{}", user.getUsername(), SessionUtil.ONLINE_USER_MAP.size());
    }

    /**
     * 检查用户是否在线
     *
     * @param userId  用户ID
     * @param channel 用户channel
     * @return boolean
     */
    private boolean checkOnline(Integer userId, Channel channel) {
        Session session = channel.attr(Attributes.SESSION).get();
        if (session != null && session.getUserId().equals(userId)) {
            return true;
        } else if (session != null) {
            SessionUtil.ONLINE_USER_MAP.remove(session.getUserId());
        }
        return SessionUtil.ONLINE_USER_MAP.get(userId) != null;
    }
}
