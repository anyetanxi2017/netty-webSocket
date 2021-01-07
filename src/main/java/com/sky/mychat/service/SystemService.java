package com.sky.mychat.service;

import com.sky.mychat.entiry.ChatGroupDo;

/**
 * @author tiankong
 * @date 2019/11/20 10:08
 */
public interface SystemService {
    /**
     * 设置某群是否保存数据到数据库
     */
    ChatGroupDo settingGroupSaveDB(Integer gid, boolean type);
}
