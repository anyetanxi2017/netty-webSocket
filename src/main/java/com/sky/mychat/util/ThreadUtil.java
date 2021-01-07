package com.sky.mychat.util;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author tiankong
 * @date 2019/11/17 15:27
 */
public class ThreadUtil {
    private static ExecutorService singleton;

    public static ExecutorService getSingleton() {
        if (singleton == null) {
            singleton = new ThreadPoolExecutor(10, 20, 0, TimeUnit.SECONDS,
                    new ArrayBlockingQueue<>(512),
                    new ThreadPoolExecutor.DiscardPolicy());
        }
        return singleton;
    }
}
