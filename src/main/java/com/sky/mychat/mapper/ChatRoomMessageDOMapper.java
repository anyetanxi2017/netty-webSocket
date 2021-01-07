package com.sky.mychat.mapper;

import com.sky.mychat.entiry.ChatRoomMessageDo;

/**
 * @author tiankong
 * @date 2019/11/17 16:06
 */
public interface ChatRoomMessageDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChatRoomMessageDo record);

    int insertSelective(ChatRoomMessageDo record);

    ChatRoomMessageDo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChatRoomMessageDo record);

    int updateByPrimaryKey(ChatRoomMessageDo record);
}
