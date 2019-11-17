package com.sky.mychat.entiry;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author tiankong
 * @date 2019/11/17 16:05
 */
@Data
public class UmsUserDO implements Serializable {
    private Integer id;

    private String username;

    private String password;

    private String avatar;

    /**
    * 用户等级
    */
    private Byte userLevel;

    /**
    * 用户类型
    */
    private Byte userType;

    /**
    * 是否删除 0删除 1正常
    */
    private Boolean isDelete;

    private String email;

    private String qq;

    private String tel;

    private String wechat;

    private Date createTime;

    private Date modifiedTime;

    private static final long serialVersionUID = 1L;
}