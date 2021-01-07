package com.sky.mychat.service;

import com.sky.mychat.dto.UserInfo;
import com.sky.mychat.entiry.ChatGroupDo;
import com.sky.mychat.entiry.ChatGroupMessageDo;
import com.sky.mychat.entiry.ChatUserGroupRelationDo;

import java.util.List;
import java.util.Set;

/**
 * @author tiankong
 * @date 2019/11/18 18:31
 */
public interface ChatGroupService {
    /**
     * 创建群
     *
     * @param chatGroupDo chatGroupDo
     * @param userIdSet   userIdSet
     */
    void createGroup(ChatGroupDo chatGroupDo, Set<Integer> userIdSet);

    /**
     * 根据群名称查的群信息
     *
     * @param groupName groupNameg
     * @return ChatGroupDo
     */
    ChatGroupDo getByGroupName(String groupName);


    /**
     * 查询群成员
     * (查询所有群成员时使用)
     *
     * @param groupId groupId
     * @return UserInfo lists
     */
    List<UserInfo> listGroupMembersAll(Integer groupId);

    /**
     * 查询用户所有群组
     *
     * @param uid uid
     * @return ChatUserGroupRelationDo
     */
    List<ChatUserGroupRelationDo> listGroupByUserId(Integer uid);

    /**
     * 查询群组
     *
     * @param groupId id
     * @return 群组
     */
    ChatGroupDo getByGroupId(Integer groupId);


    /**
     * 加入群组
     *
     * @param groupId   群ID
     * @param userIdSet 用户ID
     */
    void joinGroup(Integer groupId, Set<Integer> userIdSet);

    /**
     * 查询用户是否加入群组
     *
     * @param userId  userId
     * @param groupId groupId
     * @return ChatUserGroupRelationDo
     */
    ChatUserGroupRelationDo getUserGroupRelation(Integer userId, Integer groupId);

    /**
     * 退群
     *
     * @param groupId   groupId
     * @param userIdSet userIdSet
     */
    void quitGroup(Integer groupId, Set<Integer> userIdSet);

    /**
     * 获取所有群
     *
     * @return chatGroupDo
     */
    List<ChatGroupDo> listAllChatGroup();

    /**
     * 查询用户所有群组
     */
    List<ChatGroupDo> getGroupByUserId(Integer uid);

    /**
     * 保存群组消息
     */
    int saveGroupMessage(ChatGroupMessageDo message);
}
