package com.sky.mychat.controller;

import com.sky.mychat.dto.LoginAndRegisterParam;
import com.sky.mychat.dto.LoginInfoDto;
import com.sky.mychat.service.ChatGroupService;
import com.sky.mychat.service.UmsUserService;
import com.sky.mychat.service.impl.GroupServiceImpl;
import com.sky.mychat.util.JsonResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author tiankong
 * @date 2019/11/17 17:32
 */
@RestController
@RequestMapping("/user")
public class UmsUserController extends BaseController {
    @Resource
    private UmsUserService userService;
    @Resource
    private ChatGroupService chatGroupService;

    @PostMapping("/register")
    public JsonResult register(@Validated @RequestBody LoginAndRegisterParam param) {
        LoginInfoDto loginInfo = userService.register(param);
        return JsonResult.success(loginInfo);
    }

    @PostMapping("/login")
    public JsonResult login(@Validated @RequestBody LoginAndRegisterParam param) {
        return JsonResult.success(userService.login(param, true));
    }
}
