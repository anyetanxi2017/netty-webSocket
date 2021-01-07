package com.sky.mychat.service;

import com.sky.mychat.dto.LoginAndRegisterParam;
import com.sky.mychat.dto.LoginInfoDto;
import com.sky.mychat.dto.UserInfo;
import com.sky.mychat.entiry.UmsUserDo;

import java.util.List;

/**
 * @author tiankong
 * @date 2019/11/17 17:33
 */
public interface UmsUserService {
    /**
     * 用户注册
     *
     * @param param param
     * @return LoginInfo
     */
    LoginInfoDto register(LoginAndRegisterParam param);

    /**
     * 用户登录
     *
     * @param param         param
     * @param checkPassword 是否验证密码
     * @return LoginInfoDto
     */
    LoginInfoDto login(LoginAndRegisterParam param, boolean checkPassword);

    /**
     * 根据用户ID查询用户
     *
     * @param userId userId
     * @return UmsUserDo
     */
    UmsUserDo getByUserId(Integer userId);

    UmsUserDo getByUsername(String username);

    /**
     * 批量获取用户
     */
    List<UserInfo> getUserByIdList(List<Integer> userIdList);
}
