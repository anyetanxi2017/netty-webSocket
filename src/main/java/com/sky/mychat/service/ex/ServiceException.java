package com.sky.mychat.service.ex;

import com.sky.mychat.constant.IErrorCode;
import lombok.Data;

import java.io.Serializable;

/**
 * @author tiankong
 * @date 2019/11/17 15:24
 */
@Data
public class ServiceException extends RuntimeException implements Serializable {
    private static final long serialVersionUID = -2879099986352308425L;

    protected IErrorCode errorCode;

    public ServiceException(IErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    protected ServiceException(String message) {
        super(message);
    }

    protected ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    protected ServiceException(Throwable cause) {
        super(cause);
    }

    protected ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
