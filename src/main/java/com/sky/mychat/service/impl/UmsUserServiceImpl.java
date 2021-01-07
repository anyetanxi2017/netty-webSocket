package com.sky.mychat.service.impl;

import com.sky.mychat.constant.ResultCodeEnum;
import com.sky.mychat.dto.LoginAndRegisterParam;
import com.sky.mychat.dto.LoginInfoDto;
import com.sky.mychat.dto.UserInfo;
import com.sky.mychat.entiry.UmsUserDo;
import com.sky.mychat.mapper.UmsUserDoMapper;
import com.sky.mychat.service.UmsUserService;
import com.sky.mychat.service.ex.ServiceException;
import com.sky.mychat.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author tiankong
 * @date 2019/11/17 17:42
 */
@Service
public class UmsUserServiceImpl implements UmsUserService {
    @Resource
    private UmsUserDoMapper userMapper;
    @Resource
    private PasswordEncoder encoder;
    @Resource
    private JwtTokenUtil jwtTokenUtil;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Override
    public LoginInfoDto register(LoginAndRegisterParam param) {
        UmsUserDo user = getUserByUsername(param.getUsername());
        if (user != null) {
            throw new ServiceException(ResultCodeEnum.USER_ALREADY_EXISTS);
        }
        user = new UmsUserDo();
        user.setUsername(param.getUsername());
        user.setPassword(encoder.encode(param.getPassword()));
        System.out.println(user);
        userMapper.insert(user);
        return login(param, false);
    }


    @Override
    public LoginInfoDto login(LoginAndRegisterParam param, boolean checkPassword) {
        UmsUserDo user = getUserByUsername(param.getUsername());
        if (checkPassword) {
            if (user == null) {
                throw new ServiceException(ResultCodeEnum.USER_NOT_FOND);
            }
            if (!encoder.matches(param.getPassword(), user.getPassword())) {
                throw new ServiceException(ResultCodeEnum.PASSWORD_NOT_MATCH);
            }
        }
        String token = tokenHead + " " + jwtTokenUtil.generateToken(user.getUsername());
        return new LoginInfoDto(user.getId(), user.getUsername(), user.getAvatar(), token);
    }

    @Override
    public UmsUserDo getByUserId(Integer userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    @Override
    public UmsUserDo getByUsername(String username) {
        return userMapper.selectOneByUsername(username);
    }

    @Override
    public List<UserInfo> getUserByIdList(List<Integer> userIdList) {
        return userMapper.getUserByIdList(userIdList);
    }

    private UmsUserDo getUserByUsername(String username) {
        return userMapper.selectOneByUsername(username);
    }

}
