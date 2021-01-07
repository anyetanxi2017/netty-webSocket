package com.sky.mychat.constant;

/**
 * 系统参数
 *
 * @author tiankong
 * @date 2019/11/18 13:28
 */
public interface SystemConfig {
    /**
     * 心跳最大等待时间单位(s)
     * 如果在规则时间内没有收到客户的数据则会断开连接
     */
    long HEARTBEAT = 120;
    /**
     * 单聊信息是否保存数据库
     */
    boolean SINGLE_MESSAGE_SAVE_DB = false;
}
