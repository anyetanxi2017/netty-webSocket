package com.sky.mychat.service;

import com.sky.mychat.entiry.ChatRoomDo;

import java.util.List;

/**
 * @author tiankong
 * @date 2019/11/20 11:18
 */
public interface ChatRoomService {
    void save(ChatRoomDo chatRoomDo);

    List<ChatRoomDo> listAll();

    ChatRoomDo getByRoomId(Integer roomId);

}
