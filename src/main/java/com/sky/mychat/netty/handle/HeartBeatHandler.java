package com.sky.mychat.netty.handle;

import com.alibaba.fastjson.JSONObject;
import com.sky.mychat.constant.Command;
import com.sky.mychat.netty.executor.Executor;
import com.sky.mychat.util.JsonResult;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * @author tiankong
 * @date 2019/11/18 13:41
 */
@Service
@Slf4j
@Scope("prototype")
public class HeartBeatHandler extends Executor {
    private int count;

    public HeartBeatHandler() {
        command = Command.HEARTBEAT;
    }

    @Override
    public void execute(JSONObject param, Channel channel) {
        log.info("客户端心跳请求次数:{}", count++);
        sendMessage(channel, JsonResult.success("服务端已收到你的心跳请求", command));
    }
}
