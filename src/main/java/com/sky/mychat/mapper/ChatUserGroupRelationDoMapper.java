package com.sky.mychat.mapper;

import com.sky.mychat.entiry.ChatUserGroupRelationDo;

/**
 * @author tiankong
 * @date 2019/11/19 11:12
 */
public interface ChatUserGroupRelationDoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChatUserGroupRelationDo record);

    int insertSelective(ChatUserGroupRelationDo record);

    ChatUserGroupRelationDo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChatUserGroupRelationDo record);

    int updateByPrimaryKey(ChatUserGroupRelationDo record);
}