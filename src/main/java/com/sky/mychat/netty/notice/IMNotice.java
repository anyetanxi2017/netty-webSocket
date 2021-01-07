package com.sky.mychat.netty.notice;

import cn.hutool.core.thread.ThreadUtil;
import com.alibaba.fastjson.JSONObject;
import com.sky.mychat.netty.BaseHandler;
import com.sky.mychat.netty.executor.Executor;
import com.sky.mychat.util.JsonResult;
import com.sky.mychat.util.SessionUtil;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * @author tiankong
 * @date 2019/11/18 14:12
 */
@Component
@Slf4j
public class IMNotice extends BaseHandler implements Runnable {
    @Override
    public void run() {
        log.info("已启动通知线程!");
        for (; ; ) {
            if (SessionUtil.ONLINE_USER_MAP.size() > 0) {
                for (Map.Entry<Integer, Channel> integerChannelEntry : SessionUtil.ONLINE_USER_MAP.entrySet()) {
                    sendMessage(integerChannelEntry.getValue(), JsonResult.success("当前时间:" + new Date()));
                }
            }
            ThreadUtil.sleep(1000*60*10);
        }
    }
}
