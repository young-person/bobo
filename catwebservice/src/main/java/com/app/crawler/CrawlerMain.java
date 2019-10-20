package com.app.crawler;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * 所有爬虫抓取
 */
public class CrawlerMain {



    private static final ExecutorService EXECUTORSERVICE = newFixedThreadPool("statisticsDistrictsCode", 20);

    public static ThreadPoolExecutor newThreadPool(String threadPoolName, int corePoolSize, int maxPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> queue, RejectedExecutionHandler handler) {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat(threadPoolName + "-pool-%d").build();
        return new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveTime, unit, queue, namedThreadFactory,handler);
    }

    public static ThreadPoolExecutor newThreadPool(String threadPoolName, int corePoolSize, int maxPoolSize,long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> queue) {
        return newThreadPool(threadPoolName, corePoolSize, maxPoolSize, keepAliveTime, unit, queue,new ThreadPoolExecutor.AbortPolicy());
    }

    public static ThreadPoolExecutor newFixedThreadPool(String threadPoolName, int maxPoolSize) {
        int corePoolSize = Math.max(Math.round(maxPoolSize / 2), 1);
        return newThreadPool(threadPoolName, corePoolSize, maxPoolSize, 1L, TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>(1024), new ThreadPoolExecutor.AbortPolicy());
    }

    public static ThreadPoolExecutor newScheduledThreadPool(String threadPoolName, int corePoolSize,RejectedExecutionHandler handler) {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat(threadPoolName + "-pool-%d").build();
        return new ScheduledThreadPoolExecutor(corePoolSize, namedThreadFactory, handler);
    }

    public static ThreadPoolExecutor newScheduledThreadPool(String threadPoolName, int corePoolSize) {
        return newScheduledThreadPool(threadPoolName, corePoolSize, new ThreadPoolExecutor.AbortPolicy());
    }

}
