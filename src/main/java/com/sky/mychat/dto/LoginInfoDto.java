package com.sky.mychat.dto;

import lombok.Data;

/**
 * @author tiankong
 * @date 2019/11/17 17:36
 */
@Data
public class LoginInfoDto {
    private Integer userId;
    private String username;
    private String avatar;
    private String token;

    public LoginInfoDto(Integer userId, String username, String avatar, String token) {
        this.userId = userId;
        this.username = username;
        this.avatar = avatar;
        this.token = token;
    }
}
