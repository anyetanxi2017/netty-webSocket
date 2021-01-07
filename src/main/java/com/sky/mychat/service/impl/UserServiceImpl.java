package com.sky.mychat.service.impl;

import com.sky.mychat.entiry.UmsUserDo;
import com.sky.mychat.mapper.UmsUserDoMapper;
import com.sky.mychat.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author tiankong
 * @date 2019/11/17 16:12
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UmsUserDoMapper userMapper;

    @Override
    public UmsUserDo getByUsername(String username) {
        return userMapper.selectOneByUsername(username);
    }
}
