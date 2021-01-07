package com.sky.mychat.service;

import com.sky.mychat.entiry.ChatRoomMessageDo;

/**
 * @author tiankong
 * @date 2019/11/20 14:21
 */
public interface ChatRoomMessageService {

    void save(ChatRoomMessageDo roomMessage);
}
