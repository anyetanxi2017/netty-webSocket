package com.sky.mychat.dto;

import com.sky.mychat.bo.Session;
import lombok.Data;

import java.io.Serializable;

/**
 * @author tiankong
 * @date 2019/11/19 11:14
 */
@Data
public class UserInfo implements Serializable {

    private Integer userId;
    private String username;
    private String avatar;

    public UserInfo() {
    }

    public UserInfo(Integer userId, String username, String avatar) {
        this.userId = userId;
        this.username = username;
        this.avatar = avatar;
    }

    public UserInfo(Session session) {
        userId = session.getUserId();
        username = session.getUsername();
        avatar = session.getAvatar();
    }
}
