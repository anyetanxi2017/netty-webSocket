package com.sky.mychat.service;

import com.sky.mychat.entiry.UmsUserDo;

/**
 * @author tiankong
 * @date 2019/11/17 16:11
 */
public interface UserService {
    /**
     * 分所用户名查询用户
     *
     * @param username 用户名
     * @return 用户
     */
    UmsUserDo getByUsername(String username);
}
