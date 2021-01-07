package com.sky.mychat.constant;

/**
 * @author tiankong
 * @date 2019/11/18 9:58
 */
public interface Command {

    // ------------------------------房间相关--------------------------------------
    /**
     * 查看所有房间
     */
    byte LIST_ROOM = 14;
    /**
     * 退出房间
     */
    byte QUIET_ROOM = 13;
    /**
     * 房间聊天
     */
    byte ROOM_CHAT = 12;
    /**
     * 查看房间用户
     */
    byte LIST_ROOM_MEMBERS = 11;
    /**
     * 进入房间
     */
    byte ENTER_ROOM = 10;
    /**
     * 创建房间
     */
    byte CREATE_ROOM = 9;

    //  ---------------------------群聊相关--------------------------------
    /**
     * 退出群聊
     */
    byte QUIET_GROUP = 8;
    /**
     * 加入群聊
     */
    byte JOIN_GROUP = 7;
    /**
     * 查看所有群成员
     */
    byte LIST_GROUP_ALL_MEMBERS = 6;
    /**
     * 查看群成员
     */
    byte LIST_GROUP_MEMBERS = 5;
    /**
     * 创建群聊
     */
    byte CREATE_GROUP = 4;
    /**
     * 群聊
     */
    byte GROUP_CHAT = 3;

    // --------------------------基本------------------------------
    /**
     * 单聊
     */
    byte SINGLE_CHAT = 2;
    /**
     * 注册用户channel
     */
    byte REGISTER = 1;
    /**
     * 心跳
     */
    byte HEARTBEAT = 0;
}
