package com.sky.mychat.service.impl;

import com.sky.mychat.entiry.ChatRoomDo;
import com.sky.mychat.mapper.ChatRoomDOMapper;
import com.sky.mychat.service.ChatRoomService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author tiankong
 * @date 2019/11/20 11:19
 */
@Service
@CacheConfig(cacheNames = "room")
public class RoomServiceImpl implements ChatRoomService {
    @Resource
    private ChatRoomDOMapper chatRoomDOMapper;

    @Override
    @CachePut(key = "#p0.id")
    public void save(ChatRoomDo chatRoomDo) {
        chatRoomDOMapper.insert(chatRoomDo);
    }

    @Override
    public List<ChatRoomDo> listAll() {
        return chatRoomDOMapper.selectByAll(null);
    }

    @Override
    @Cacheable(key = "#p0")
    public ChatRoomDo getByRoomId(Integer roomId) {
        return chatRoomDOMapper.selectByPrimaryKey(roomId);
    }
}
