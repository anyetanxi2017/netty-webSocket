package com.sky.mychat.netty.response;

import com.sky.mychat.bo.Session;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author tiankong
 * @date 2019/11/18 15:06
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ChatMessageResponse extends BaseResponse {
    private Integer fromUserId;
    private String fromUserName;
    private String fromUserIcon;
    private String content;

    public ChatMessageResponse() {
    }

    public ChatMessageResponse(Session session, String content, Date time) {
        fromUserId = session.getUserId();
        fromUserName = session.getUsername();
        fromUserIcon = session.getAvatar();
        this.content = content;
        this.time = time;
    }
}
