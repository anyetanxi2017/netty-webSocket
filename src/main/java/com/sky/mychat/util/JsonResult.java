package com.sky.mychat.util;

import com.sky.mychat.constant.IErrorCode;
import com.sky.mychat.constant.ResultCodeEnum;
import com.sky.mychat.entiry.ChatRoomMessageDo;
import com.sky.mychat.netty.response.BaseResponse;
import lombok.Data;

import java.util.List;

/**
 * @author tiankong
 * @date 2019/11/17 15:27
 */
@Data
public class JsonResult<T> {
    private Integer state;
    private String message;
    private byte type;
    private T data;

    public JsonResult() {
        super();
    }

    public JsonResult(Integer state, String message, byte type, T data) {
        this.state = state;
        this.message = message;
        this.type = type;
        this.data = data;
    }

    public JsonResult(Integer state) {
        super();
        this.state = state;
    }

    public JsonResult(Throwable throwable) {
        super();
        this.message = throwable.getMessage();
    }

    public JsonResult(Integer state, T data) {
        super();
        this.state = state;
        this.data = data;
    }

    private JsonResult(Integer state, String message) {
        this.state = state;
        this.message = message;
    }

    private JsonResult(IErrorCode code) {
        this.state = code.getCode();
        this.message = code.getMessage();

    }

    private JsonResult(IErrorCode success, T loginInfo) {
        this.state = success.getCode();
        this.message = success.getMessage();
        this.data = loginInfo;
    }

    private JsonResult(IErrorCode success, T loginInfo, byte command) {
        this.state = success.getCode();
        this.message = success.getMessage();
        this.data = loginInfo;
        this.type = command;
    }

    private JsonResult(IErrorCode success, byte type) {
        this.state = success.getCode();
        this.message = success.getMessage();
        this.type = type;
    }

    /**
     * 未授权
     */
    public static JsonResult forbidden() {
        return new JsonResult(ResultCodeEnum.FORBIDDEN);
    }

    /**
     * 未登录
     */
    public static JsonResult unauthorized() {
        return new JsonResult(ResultCodeEnum.UNAUTHORIZED);
    }

    public static JsonResult success(Object loginInfo) {
        return new JsonResult(ResultCodeEnum.SUCCESS, loginInfo);
    }

    public static <T> JsonResult success(List<T> loginInfo, byte command) {
        return new JsonResult(ResultCodeEnum.SUCCESS, loginInfo);
    }


    public static JsonResult success() {
        return new JsonResult(ResultCodeEnum.SUCCESS);
    }

    public static JsonResult success(String msg) {
        return new JsonResult(ResultCodeEnum.SUCCESS.getCode(), msg);
    }

    public static JsonResult success(byte type) {
        return new JsonResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getMessage(), type, null);
    }

    public static JsonResult success(String msg, byte type) {
        return new JsonResult(ResultCodeEnum.SUCCESS.getCode(), msg, type, null);
    }

    public static JsonResult success(BaseResponse response, byte type) {
        return new JsonResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getMessage(), type, response);
    }

    public static JsonResult success(ChatRoomMessageDo roomMessage, byte command) {
        return new JsonResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getMessage(), command, roomMessage);
    }

    /**
     * 业务异常
     *
     * @param errorCode 异常类型
     * @return JsonResult
     */
    public static JsonResult<Void> failed(IErrorCode errorCode) {
        return new JsonResult<>(errorCode);
    }

    public static JsonResult<Void> failed(IErrorCode errorCode, byte type) {
        return new JsonResult<>(errorCode, type);
    }

    public static JsonResult failed(String msg, byte type) {
        return new JsonResult(ResultCodeEnum.FAILED.getCode(), msg, type, null);
    }

    public static JsonResult<Void> failed(byte type) {
        return new JsonResult<>(ResultCodeEnum.FAILED, type);
    }

    /**
     * 操作失败
     *
     * @return JsonResult
     */
    public static JsonResult<Void> failed() {
        return new JsonResult<>(ResultCodeEnum.FAILED);
    }


}
