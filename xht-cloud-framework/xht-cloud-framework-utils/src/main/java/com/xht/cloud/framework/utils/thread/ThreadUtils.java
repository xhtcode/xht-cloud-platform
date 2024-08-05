package com.xht.cloud.framework.utils.thread;

import java.util.Objects;
import java.util.concurrent.ExecutorService;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * 线程池工具类.
 *
 * @author 小糊涂
 */
public class ThreadUtils {

    /**
     * 关闭线程池.
     *
     * @param executorService 执行器.
     * @param timeout         超时时间
     */
    public static void shutdown(ExecutorService executorService, int timeout) {
        if (Objects.nonNull(executorService) && !executorService.isShutdown()) {
            executorService.shutdown();
            try {
                if (!executorService.awaitTermination(timeout, SECONDS)) {
                    executorService.shutdownNow();
                }
            } catch (InterruptedException e) {
                executorService.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }

}
