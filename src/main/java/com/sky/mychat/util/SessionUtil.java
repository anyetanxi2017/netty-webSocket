package com.sky.mychat.util;

import com.sky.mychat.bo.GroupInfo;
import com.sky.mychat.bo.RoomInfo;
import com.sky.mychat.bo.Session;
import com.sky.mychat.constant.Attributes;
import com.sky.mychat.dto.UserInfo;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author tiankong
 * @date 2019/11/17 19:22
 */
@Component
public class SessionUtil {
    public static Map<String, WebSocketServerHandshaker> WEB_SOCKET_SERVER_HAND_SHAKER = new ConcurrentHashMap<>();

    /**
     * 所有房间
     */
    public static Map<Integer, RoomInfo> ROOM_MAP = new ConcurrentHashMap<>();
    /**
     * 所有群
     */
    public static Map<Integer, GroupInfo> GROUP_MAP = new ConcurrentHashMap<>();

    /**
     * 在线用户
     */
    public static Map<Integer, Channel> ONLINE_USER_MAP = new ConcurrentHashMap<>();

    // -------------------------------------基本----------------------------------------------------

    /**
     * 获取用户channel
     */
    public static Channel getUserChannel(Integer uid) {
        return ONLINE_USER_MAP.get(uid);
    }

    /**
     * 注册用户channel
     */
    public static void registerUserChannel(Integer uid, Channel channel) {
        ONLINE_USER_MAP.put(uid, channel);
    }

    public static Session getSession(Channel channel) {
        return channel.attr(Attributes.SESSION).get();
    }

    // --------------------------------群相关-----------------------------------

    /**
     * 加入群组
     */
    public static void joinGroup(Integer groupId, Channel channel) {
        GROUP_MAP.get(groupId).getChannelGroup().add(channel);
    }

    /**
     * 退出群组
     */
    public static void quitGroup(Integer groupId, Channel channel) {
        GROUP_MAP.get(groupId).getChannelGroup().remove(channel);
    }

    /**
     * 获取群组ChannelGroup
     */
    public static ChannelGroup getChannelGroup(Integer groupId) {
        return GROUP_MAP.get(groupId).getChannelGroup();
    }


    /**
     * 获取群组用户ID集合
     */
    public static List<Integer> listGroupMemberIds(Integer groupId) {
        List<Integer> list = new ArrayList<>();
        getChannelGroup(groupId).forEach(channel -> {
            if (channel != null) {
                list.add(getSession(channel).getUserId());
            }
        });
        return list;
    }


    // --------------------------------房间相关-----------------------------------


    /**
     * 获取房间ChannelGroup
     */
    public static ChannelGroup getRoomGroup(Integer roomId) {
        return ROOM_MAP.get(roomId).getChannelGroup();
    }

    /**
     * 进入房间
     *
     * @param roomId  房间ID
     * @param channel 用户channel
     */
    public static void entryRoom(Integer roomId, Channel channel) {
        ROOM_MAP.get(roomId).getChannelGroup().add(channel);
    }

    /**
     * 退出房间
     *
     * @param roomId  房间ID
     * @param channel 用户channel
     */
    public static void quitRoom(Integer roomId, Channel channel) {
        ROOM_MAP.get(roomId).getChannelGroup().remove(channel);
    }

    /**
     * 获取房间所有用户
     *
     * @param roomId roomId
     * @return UserInfo list
     */
    public static List<UserInfo> listRoomMembers(Integer roomId) {
        List<UserInfo> list = new ArrayList<>();
        ROOM_MAP.get(roomId).getChannelGroup().forEach(userChannel -> {
            list.add(new UserInfo(getSession(userChannel)));
        });
        return list;
    }
}
