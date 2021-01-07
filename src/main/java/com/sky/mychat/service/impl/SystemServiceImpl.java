package com.sky.mychat.service.impl;

import com.sky.mychat.entiry.ChatGroupDo;
import com.sky.mychat.mapper.ChatGroupDoMapper;
import com.sky.mychat.service.SystemService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author tiankong
 * @date 2019/11/20 10:13
 */
@Service
@CacheConfig(cacheNames = "chat_group")
public class SystemServiceImpl implements SystemService {
    @Resource
    private ChatGroupDoMapper chatGroupDoMapper;

    @Override
    @CachePut(key = "'getByGroupId'+#p0")
    public ChatGroupDo settingGroupSaveDB(Integer gid, boolean type) {
        chatGroupDoMapper.settingGroupSaveDB(gid, type);
        return chatGroupDoMapper.selectByPrimaryKey(gid);
    }
}
