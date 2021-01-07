package com.sky.mychat.mapper;

import com.sky.mychat.dto.UserInfo;
import com.sky.mychat.entiry.ChatGroupDo;
import com.sky.mychat.entiry.ChatUserGroupRelationDo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * @author tiankong
 * @date 2019/11/19 19:59
 */
public interface ChatGroupDoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChatGroupDo record);

    int insertSelective(ChatGroupDo record);

    ChatGroupDo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChatGroupDo record);

    int updateByPrimaryKey(ChatGroupDo record);

    ChatGroupDo getOneByGroupName(@Param("groupName") String groupName);

    int insertUserGroupRelation(@Param("gid") Integer gid, @Param("userIdSet") Set<Integer> userIdSet);

    /**
     * 查询在线群用户使用
     *
     * @param id id
     * @return UserInfo list
     */
    List<UserInfo> listGroupMembers(Integer id);

    /**
     * 查询所有所有群组
     *
     * @param uid uid
     * @return ChatUserGroupRelationDo
     */
    List<ChatUserGroupRelationDo> listGroupByUserId(@Param("uid") Integer uid);

    /**
     * 加入群组
     *
     * @param groupId   群ID
     * @param userIdSet 用户ID
     */
    void joinGroup(@Param("groupId") Integer groupId, @Param("userIdSet") Set<Integer> userIdSet);

    /**
     * 判断用户是否加入群组
     *
     * @param userId  userId
     * @param groupId groupId
     * @return ChatUserGroupRelationDo
     */
    ChatUserGroupRelationDo getUserGroupRelation(@Param("userId") Integer userId, @Param("groupId") Integer groupId);

    /**
     * 退群
     */
    void quitGroup(@Param("groupId") Integer groupId, @Param("userIdSet") Set<Integer> userIdSet);

    List<ChatGroupDo> getByAll(ChatGroupDo chatGroupDo);

    List<ChatGroupDo> getGroupByUserId(Integer uid);

    /**
     * 设置群是否保存数据到数据库
     */
    void settingGroupSaveDB(@Param("gid") Integer gid, @Param("type") boolean type);
}
