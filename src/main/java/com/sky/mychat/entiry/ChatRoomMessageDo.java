package com.sky.mychat.entiry;

import java.io.Serializable;
import java.util.Date;

import com.sky.mychat.bo.Session;
import lombok.Data;

/**
 * @author tiankong
 * @date 2019/11/17 16:06
 */
@Data
public class ChatRoomMessageDo implements Serializable {
    private Integer id;

    private Integer roomId;

    private Integer fromUserId;

    private String content;

    private Date createTime;

    private static final long serialVersionUID = 1L;

    public ChatRoomMessageDo() {

    }

    public ChatRoomMessageDo(Integer roomId, String content, Session session, Date time) {
        this.roomId = roomId;
        this.content = content;
        this.fromUserId = session.getUserId();
        this.createTime = time;
    }
}
