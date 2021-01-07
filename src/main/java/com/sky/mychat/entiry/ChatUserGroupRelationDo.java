package com.sky.mychat.entiry;

import java.io.Serializable;
import lombok.Data;

/**
 * @author tiankong
 * @date 2019/11/19 11:12
 */
@Data
public class ChatUserGroupRelationDo implements Serializable {
    private Integer id;

    private Integer uid;

    private Integer gid;

    private static final long serialVersionUID = 1L;
}