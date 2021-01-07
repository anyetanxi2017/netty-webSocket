package com.sky.mychat.mapper;

import com.sky.mychat.entiry.ChatGroupMessageDo;

/**
 * @author tiankong
 * @date 2019/11/17 16:06
 */
public interface ChatGroupMessageDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChatGroupMessageDo record);

    int insertSelective(ChatGroupMessageDo record);

    ChatGroupMessageDo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChatGroupMessageDo record);

    int updateByPrimaryKey(ChatGroupMessageDo record);
}
