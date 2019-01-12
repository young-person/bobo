package com.app.task;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

public abstract class AbstractScheduleJob implements Job, Serializable {

    private static final Logger logger = LoggerFactory.getLogger(AbstractScheduleJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        this.execute(context);
    }

    /**
     * 获取调度组
     * @return
     */
    public abstract String getGroupId();
    /**
     * 获取调度ID
     * @return
     */
    public abstract String scheduleId();

    /**
     * 具体调度执行
     */
    public abstract void schedule(JobExecutionContext context) throws JobExecutionException;
}
