package com.sky.mychat.service.impl;

import com.sky.mychat.dto.UserInfo;
import com.sky.mychat.entiry.ChatGroupDo;
import com.sky.mychat.entiry.ChatGroupMessageDo;
import com.sky.mychat.entiry.ChatUserGroupRelationDo;
import com.sky.mychat.mapper.ChatGroupDoMapper;
import com.sky.mychat.mapper.ChatGroupMessageDOMapper;
import com.sky.mychat.service.ChatGroupService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * @author tiankong
 * @date 2019/11/18 18:32
 */
@Service
@Transactional
@CacheConfig(cacheNames = "chat_group")
public class GroupServiceImpl implements ChatGroupService {
    @Resource
    private ChatGroupDoMapper groupMapper;
    @Resource
    private ChatGroupMessageDOMapper chatGroupMessageDOMapper;

    @Override
    public void createGroup(ChatGroupDo chatGroupDo, Set<Integer> userIdSet) {
        groupMapper.insert(chatGroupDo);
        groupMapper.insertUserGroupRelation(chatGroupDo.getId(), userIdSet);
    }

    @Override
    public ChatGroupDo getByGroupName(String groupName) {
        return groupMapper.getOneByGroupName(groupName);
    }

    @Override
    public List<UserInfo> listGroupMembersAll(Integer groupId) {
        return groupMapper.listGroupMembers(groupId);
    }

    @Override
    public List<ChatUserGroupRelationDo> listGroupByUserId(Integer uid) {
        return groupMapper.listGroupByUserId(uid);
    }

    /**
     * 查询群组信息
     * 说明: 每次发送群消息时会从缓存中获取群组信息,以判断是否需要保存群组消息到数据库.
     * 注意: 在修改用户消息时需要更新此缓存
     */
    @Override
    @Cacheable(key = "'getByGroupId'+#p0")
    public ChatGroupDo getByGroupId(Integer groupId) {
        return groupMapper.selectByPrimaryKey(groupId);
    }

    @Override
    public void joinGroup(Integer groupId, Set<Integer> userIdSet) {
        groupMapper.joinGroup(groupId, userIdSet);
    }

    @Override
    public ChatUserGroupRelationDo getUserGroupRelation(Integer userId, Integer groupId) {
        return groupMapper.getUserGroupRelation(userId, groupId);
    }

    @Override
    public void quitGroup(Integer groupId, Set<Integer> userIdSet) {
        groupMapper.quitGroup(groupId, userIdSet);
    }

    @Override
    public List<ChatGroupDo> listAllChatGroup() {
        return groupMapper.getByAll(null);
    }

    @Override
    public List<ChatGroupDo> getGroupByUserId(Integer uid) {
        return groupMapper.getGroupByUserId(uid);
    }

    @Override
    public int saveGroupMessage(ChatGroupMessageDo message) {
        return chatGroupMessageDOMapper.insert(message);
    }
}
