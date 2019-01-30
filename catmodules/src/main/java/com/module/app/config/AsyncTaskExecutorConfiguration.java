package com.module.app.config;

import com.module.app.properties.CloudProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.Resource;
import java.util.concurrent.Executor;

@EnableScheduling
@EnableAsync
public class AsyncTaskExecutorConfiguration implements AsyncConfigurer {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    private CloudProperties cloudProperties;

    @Override
    @Bean(name = "taskExecutor")
    public Executor getAsyncExecutor() {
        logger.debug("Creating Async Task Executor");
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(cloudProperties.getTask().getCorePoolSize());
        executor.setMaxPoolSize(cloudProperties.getTask().getMaxPoolSize());
        executor.setQueueCapacity(cloudProperties.getTask().getQueueCapacity());
        executor.setKeepAliveSeconds(cloudProperties.getTask().getKeepAliveSeconds());
        executor.setThreadNamePrefix(cloudProperties.getTask().getThreadNamePrefix());
        return new ExceptionHandlingAsyncTaskExecutor(executor);
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new SimpleAsyncUncaughtExceptionHandler();
    }
}
