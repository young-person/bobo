package com.bobo.common;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

public class TransactionThreadFactory implements ThreadFactory {

    private static volatile boolean daemon = false;

    private static final ThreadGroup THREAD_GROUP = new ThreadGroup("cat_Transaction");

    private final AtomicLong threadNumber = new AtomicLong(1);
    private final String prefix;
    private TransactionThreadFactory(final String namePrefix, final boolean daemon) {
        this.prefix = namePrefix;
        TransactionThreadFactory.daemon = daemon;
    }
    public static ThreadFactory create(final String namePrefix, final boolean daemon) {
        return new TransactionThreadFactory(namePrefix, daemon);
    }
    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(THREAD_GROUP, r,
                THREAD_GROUP.getName() + "-" + prefix + "-" + threadNumber.getAndIncrement());
        thread.setDaemon(daemon);
        if (thread.getPriority() != Thread.NORM_PRIORITY) {
            thread.setPriority(Thread.NORM_PRIORITY);
        }
        return thread;
    }
}
