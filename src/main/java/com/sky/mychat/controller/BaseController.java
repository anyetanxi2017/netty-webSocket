package com.sky.mychat.controller;

import com.sky.mychat.service.ex.ServiceException;
import com.sky.mychat.util.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author tiankong
 * @date 2019/11/17 15:17
 */
public abstract class BaseController {

    @ExceptionHandler({ServiceException.class})
    public JsonResult<Void> handleException(Throwable e) {
        if (e instanceof ServiceException) {
            return JsonResult.failed(((ServiceException) e).getErrorCode());
        }
        e.printStackTrace();
        return JsonResult.failed();
    }
}
