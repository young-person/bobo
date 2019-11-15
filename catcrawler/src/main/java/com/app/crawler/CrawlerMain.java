package com.app.crawler;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.app.crawler.base.RCache;
import com.app.crawler.riches.BRiches;

/**
 * 所有爬虫抓取
 */
public class CrawlerMain {
	
	public static void main(String[] args) {
		RCache rCache = new RCache();
		rCache.loadCatConfig();
		
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
//                CrawlerDown down1 = new Department();
//                down1.start();
//                CrawlerDown down2 = new WeatherRecordTask();
//                down2.start();
//                CrawlerDown down3 = new MainParse();
//                down3.start();
            	BRiches bRiches = new BRiches();
            	if (!bRiches.isRuning()) {
            		bRiches.start();
				}
            }
        }, 0, Integer.valueOf(RCache.CAT_CACHE.get("periodTime").getValue()));
        
	}
	
	
	
	public static ThreadPoolExecutor newThreadPool(String threadPoolName, int corePoolSize, int maxPoolSize,
			long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> queue, RejectedExecutionHandler handler) {
		return new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveTime, unit, queue, new DefaultThreadFactory(),
				handler);
	}

	public static ThreadPoolExecutor newThreadPool(String threadPoolName, int corePoolSize, int maxPoolSize,
			long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> queue) {
		return newThreadPool(threadPoolName, corePoolSize, maxPoolSize, keepAliveTime, unit, queue,
				new ThreadPoolExecutor.AbortPolicy());
	}

	public static ThreadPoolExecutor newThreadPool(String threadPoolName, int maxPoolSize) {
		int corePoolSize = Math.max(Math.round(maxPoolSize / 2), 1);
		return newThreadPool(threadPoolName, corePoolSize, maxPoolSize, 1L, TimeUnit.SECONDS,
				new LinkedBlockingQueue<Runnable>(100), new ThreadPoolExecutor.CallerRunsPolicy());
	}

	public static ThreadPoolExecutor newScheduledThreadPool(String threadPoolName, int corePoolSize,
			RejectedExecutionHandler handler) {
		return new ScheduledThreadPoolExecutor(corePoolSize, new DefaultThreadFactory(), handler);
	}

	public static ThreadPoolExecutor newScheduledThreadPool(String threadPoolName, int corePoolSize) {
		return newScheduledThreadPool(threadPoolName, corePoolSize, new ThreadPoolExecutor.AbortPolicy());
	}

	static class DefaultThreadFactory implements ThreadFactory {
		private static final AtomicInteger poolNumber = new AtomicInteger(1);
		private final ThreadGroup group;
		private final AtomicInteger threadNumber = new AtomicInteger(1);
		private final String namePrefix;

		DefaultThreadFactory() {
			SecurityManager s = System.getSecurityManager();
			group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
			namePrefix = "pool-" + poolNumber.getAndIncrement() + "-thread-";
		}

		public Thread newThread(Runnable r) {
			Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
			if (t.isDaemon())
				t.setDaemon(false);
			if (t.getPriority() != Thread.NORM_PRIORITY)
				t.setPriority(Thread.NORM_PRIORITY);
			return t;
		}
	}
}
