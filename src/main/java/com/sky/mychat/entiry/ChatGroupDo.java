package com.sky.mychat.entiry;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author tiankong
 * @date 2019/11/19 19:59
 */
@Data
public class ChatGroupDo implements Serializable {
    private Integer id;

    private String groupName;

    private String icon;

    private String description;

    /**
     * 群类型
     */
    private Byte type;

    /**
     * 加群人数最大值
     */
    private Short maxNumberOfPeople;

    /**
     * 是否保存数据到数据库
     */
    private Boolean saveDb;

    private Date createTime;

    private Date modifiedTime;

    private static final long serialVersionUID = 1L;
}