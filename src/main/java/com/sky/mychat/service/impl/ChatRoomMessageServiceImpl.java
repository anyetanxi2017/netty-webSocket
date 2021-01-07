package com.sky.mychat.service.impl;

import com.sky.mychat.entiry.ChatRoomMessageDo;
import com.sky.mychat.mapper.ChatRoomMessageDOMapper;
import com.sky.mychat.service.ChatRoomMessageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author tiankong
 * @date 2019/11/20 14:21
 */
@Service
public class ChatRoomMessageServiceImpl implements ChatRoomMessageService {
    @Resource
    private ChatRoomMessageDOMapper chatRoomMessageDOMapper;

    @Override
    public void save(ChatRoomMessageDo roomMessage) {
        chatRoomMessageDOMapper.insert(roomMessage);
    }
}
