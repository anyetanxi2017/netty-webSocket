package com.sky.mychat.controller;

import com.sky.mychat.entiry.ChatGroupDo;
import com.sky.mychat.service.SystemService;
import com.sky.mychat.util.JsonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author tiankong
 * @date 2019/11/20 10:08
 */
@RestController
@RequestMapping("/system")
public class SystemController {
    @Resource
    private SystemService systemService;

    /**
     * 设置某群是否保存数据到数据库
     * @param gid 群ID
     * @param type 是否保存
     * @return JsonResult
     */
    @GetMapping("/settingGroupSaveDB/{gid}/{type}")
    public JsonResult settingGroupSaveDB(@PathVariable("gid") Integer gid, @PathVariable("type") boolean type) {
        ChatGroupDo groupDo = systemService.settingGroupSaveDB(gid, type);
        return JsonResult.success(groupDo.getSaveDb());
    }

}
