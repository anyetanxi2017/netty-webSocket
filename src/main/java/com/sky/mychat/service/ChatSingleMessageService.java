package com.sky.mychat.service;

import com.sky.mychat.entiry.ChatSingleMessageDo;

/**
 * @author tiankong
 * @date 2019/11/18 15:47
 */
public interface ChatSingleMessageService {
    int save(ChatSingleMessageDo msg);
}
