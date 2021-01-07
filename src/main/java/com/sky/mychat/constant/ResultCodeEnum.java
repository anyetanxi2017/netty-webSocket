package com.sky.mychat.constant;

/**
 * @author tiankong
 * @date 2019/11/17 15:41
 */
public enum ResultCodeEnum implements IErrorCode {
    ROOM_NOT_FOUND(4015, "房间不存在"),
    CONTENT_IS_NULL(4014, "内容不能为空"),
    USER_ID_IS_NULL(4013, "操作失败!用户ID不能为空"),
    GROUP_NOT_FOUND(4012, "操作失败!群组不存在"),
    GROUP_ID_IS_NULL(4011, "操作失败!群组ID不能为空"),
    USER_NOT_REGISTERED(4010, "请注册后操作"),
    USER_NOT_ONLINE(4009, "用户不在线"),
    USER_LOGGED_IN(4008, "用户已登录,请不要重复登录"),
    PASSWORD_NOT_MATCH(4007, "密码不匹配"),
    USER_ALREADY_EXISTS(4006, "用户已存在"),
    USER_NOT_FOND(4005, "用户不存在"),
    VALIDATE_FAILED(4004, "参数检验失败"),
    FORBIDDEN(4003, "没有相关权限"),
    UNAUTHORIZED(4001, "暂未登录或token已过期"),
    FAILED(4000, "failed"),
    SUCCESS(2000, "success");
    private Integer code;
    private String message;

    ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
