package com.core.config;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import javax.annotation.Resource;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.core.properties.CatCloudProperties;

@EnableScheduling
@EnableAsync
public class AsyncTaskExecutorConfiguration implements AsyncConfigurer {
    @Resource
    private CatCloudProperties cloudProperties;
	/**
	 * 配置线程池
	 */
    @Override
    @Bean(name = "taskExecutor")
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(cloudProperties.getTask().getCorePoolSize());
        executor.setMaxPoolSize(cloudProperties.getTask().getMaxPoolSize());
        executor.setQueueCapacity(cloudProperties.getTask().getQueueCapacity());
        executor.setKeepAliveSeconds(cloudProperties.getTask().getKeepAliveSeconds());
        executor.setThreadNamePrefix(cloudProperties.getTask().getThreadNamePrefix());
        // 线程池对拒绝任务（无线程可用）的处理策略，目前只支持AbortPolicy、CallerRunsPolicy；默认为后者
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return new ExceptionHandlingAsyncTaskExecutor(executor);
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new SimpleAsyncUncaughtExceptionHandler();
    }
    
}
