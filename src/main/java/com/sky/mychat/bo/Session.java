package com.sky.mychat.bo;

import lombok.Data;

/**
 * @author tiankong
 * @date 2019/11/17 18:43
 */
@Data
public class Session {
    private Integer userId;
    private String username;
    private String avatar;
    /**
     * token
     * 用户验证用户的token是否过期
     */
    private String token;

    public Session(Integer userId, String username, String avatar, String token) {
        this.userId = userId;
        this.username = username;
        this.avatar = avatar;
        this.token = token;
    }
}
