package com.sky.mychat.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

import com.sky.mychat.entiry.ChatRoomDo;

/**
 * @author tiankong
 * @date 2019/11/17 16:06
 */
public interface ChatRoomDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChatRoomDo record);

    int insertSelective(ChatRoomDo record);

    ChatRoomDo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChatRoomDo record);

    int updateByPrimaryKey(ChatRoomDo record);

    List<ChatRoomDo> selectByAll(ChatRoomDo chatRoomDo);


}
