package com.sky.mychat.mapper;

import com.sky.mychat.entiry.ChatSingleMessageDo;

/**
 * @author tiankong
 * @date 2019/11/18 15:53
 */
public interface ChatSingleMessageDoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChatSingleMessageDo record);

    int insertSelective(ChatSingleMessageDo record);

    ChatSingleMessageDo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChatSingleMessageDo record);

    int updateByPrimaryKey(ChatSingleMessageDo record);
}