package com.sky.mychat.netty.executor;

import com.alibaba.fastjson.JSONObject;
import com.sky.mychat.constant.ResultCodeEnum;
import com.sky.mychat.mapper.UmsUserDoMapper;
import com.sky.mychat.netty.executor.Executor;
import com.sky.mychat.service.ChatGroupService;
import com.sky.mychat.service.UmsUserService;
import com.sky.mychat.util.JsonResult;
import com.sky.mychat.util.JwtTokenUtil;
import io.netty.channel.Channel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 命令执行者抽象类
 *
 * @author tiankong
 * @date 2019/11/17 19:54
 */
@Service
public class ChatExecutor extends Executor {
    @Resource
    protected JwtTokenUtil jwtTokenUtil;
    @Resource
    protected ChatGroupService groupService;
    @Resource
    protected UmsUserService userService;

    /**
     * 执行方法
     * 需要被子类重写
     *
     * @param param   参数
     * @param channel 用户channel
     */
    @Override
    public void execute(JSONObject param, Channel channel) {

    }
}
