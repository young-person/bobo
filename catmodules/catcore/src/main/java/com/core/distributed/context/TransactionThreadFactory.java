package com.core.distributed.context;

import com.core.distributed.config.CatConfig;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

public class TransactionThreadFactory implements ThreadFactory {

    private static volatile boolean daemon;

    private static final ThreadGroup THREAD_GROUP = new ThreadGroup(CatConfig.CNAME);

    private final AtomicLong threadNumber = new AtomicLong(1);

    private final String namePrefix;

    private TransactionThreadFactory(final String namePrefix, final boolean daemon) {
        this.namePrefix = namePrefix;
        TransactionThreadFactory.daemon = daemon;
    }

    /**
     * 创建事物工厂
     * @param namePrefix
     * @param daemon
     * @return
     */
    public static ThreadFactory create(final String namePrefix, final boolean daemon) {
        return new TransactionThreadFactory(namePrefix, daemon);
    }

    @Override
    public Thread newThread(final Runnable runnable) {
        StringBuilder builder = new StringBuilder(THREAD_GROUP.getName());
        builder.append("-");
        builder.append(namePrefix);
        builder.append("-");
        builder.append(threadNumber.getAndIncrement());

        Thread thread = new Thread(THREAD_GROUP, runnable,builder.toString());
        /**
         * 设置为守护进程
         */
        thread.setDaemon(daemon);
        if (thread.getPriority() != Thread.NORM_PRIORITY) {
            thread.setPriority(Thread.NORM_PRIORITY);
        }
        return thread;
    }
}
