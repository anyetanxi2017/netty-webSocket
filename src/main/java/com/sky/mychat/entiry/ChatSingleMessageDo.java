package com.sky.mychat.entiry;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author tiankong
 * @date 2019/11/18 15:53
 */
@Data
public class ChatSingleMessageDo implements Serializable {
    private Integer id;

    private Integer fromUserId;

    private Integer toUserId;

    private String content;

    private Date createTime;

    public ChatSingleMessageDo(Integer fromUserId, Integer toUserId, String content, Date createTime) {
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.content = content;
        this.createTime = createTime;
    }

    private static final long serialVersionUID = 1L;

    public ChatSingleMessageDo() {
    }
}
