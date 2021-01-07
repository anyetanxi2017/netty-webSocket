package com.sky.mychat.service.impl;

import com.sky.mychat.entiry.ChatSingleMessageDo;
import com.sky.mychat.mapper.ChatSingleMessageDoMapper;
import com.sky.mychat.service.ChatSingleMessageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author tiankong
 * @date 2019/11/18 15:55
 */
@Service
public class ChatSingleMessageServiceImpl implements ChatSingleMessageService {
    @Resource
    private ChatSingleMessageDoMapper messageDoMapper;

    @Override
    public int save(ChatSingleMessageDo msg) {
        return messageDoMapper.insert(msg);
    }
}
