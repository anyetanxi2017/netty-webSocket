package com.sky.mychat.constant;

/**
 * @author tiankong
 * @date 2019/11/17 15:42
 */
public interface IErrorCode {
    /**
     * 获取状态码
     *
     * @return 状态码
     */
    Integer getCode();

    /**
     * 获取消息
     *
     * @return 消息
     */
    String getMessage();
}
