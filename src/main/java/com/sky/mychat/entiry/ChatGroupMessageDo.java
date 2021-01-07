package com.sky.mychat.entiry;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * @author tiankong
 * @date 2019/11/17 16:06
 */
@Data
public class ChatGroupMessageDo implements Serializable {
    private Integer id;

    private Integer groupId;

    private Integer fromUserId;

    private String content;

    private Date createTime;

    private static final long serialVersionUID = 1L;

    public ChatGroupMessageDo() {
    }

    public ChatGroupMessageDo(Integer groupId, Integer fromUserId, String content, Date createTime) {
        this.groupId = groupId;
        this.fromUserId = fromUserId;
        this.content = content;
        this.createTime = createTime;
    }
}
