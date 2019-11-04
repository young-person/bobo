package com.app.crawler;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

/**
 * 所有爬虫抓取
 */
public class CrawlerMain {



    public static ThreadPoolExecutor newThreadPool(String threadPoolName, int corePoolSize, int maxPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> queue, RejectedExecutionHandler handler) {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat(threadPoolName + "-pool-%d").build();
        return new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveTime, unit, queue, namedThreadFactory,handler);
    }

    public static ThreadPoolExecutor newThreadPool(String threadPoolName, int corePoolSize, int maxPoolSize,long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> queue) {
        return newThreadPool(threadPoolName, corePoolSize, maxPoolSize, keepAliveTime, unit, queue,new ThreadPoolExecutor.AbortPolicy());
    }

    public static ThreadPoolExecutor newThreadPool(String threadPoolName, int maxPoolSize) {
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
