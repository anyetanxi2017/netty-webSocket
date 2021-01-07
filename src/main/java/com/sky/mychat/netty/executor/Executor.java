package com.sky.mychat.netty.executor;

import com.alibaba.fastjson.JSONObject;
import com.sky.mychat.netty.BaseHandler;
import io.netty.channel.Channel;

/**
 * 命令执行抽象类
 *
 * @author tiankong
 * @date 2019/11/18 11:53
 */
public abstract class Executor extends BaseHandler {
    /**
     * 命令类型
     */
    protected byte command;

    /**
     * 执行命令
     *
     * @param param   参数
     * @param channel 用户channel
     */
    public abstract void execute(JSONObject param, Channel channel);

//    /**
//     * 获取命令
//     *
//     * @return executor
//     */
//    public Byte getCommand() {
//        return command;
//    }
}
