package com.app.task.load;

import com.app.pojo.ScheduleJobStatus;
import com.app.pojo.TaskObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public abstract class QuartzManagerAbstract {
    protected static  final Logger logger = LoggerFactory.getLogger(QuartzManagerAbstract.class);
    protected static Map<String,TaskObject> TASK_GROUP_MAP = new ConcurrentHashMap<String,TaskObject>();
    protected static ObjectMapper objectMapper = new ObjectMapper();

    protected static ConcurrentLinkedQueue queue = new ConcurrentLinkedQueue();

    public abstract void init();
    public void reload(){
        init();
    }

    public List<TaskObject> getAllTasks(){
        List<TaskObject> list = new ArrayList<TaskObject>();
        for(String key : TASK_GROUP_MAP.keySet()){
            list.add(TASK_GROUP_MAP.get(key));
        }
        return list;
    }
    public Scheduler getScheduler() throws SchedulerException {
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        return schedulerFactory.getScheduler();
    }

    /**
     * 启动任务调度器
     * @throws SchedulerException
     * @throws ClassNotFoundException
     */
    public synchronized void start() throws SchedulerException, ClassNotFoundException {
        Scheduler sched = getScheduler();
        sched.start();
        for (Map.Entry<String,TaskObject> entry: TASK_GROUP_MAP.entrySet()) {
            TaskObject  task=  entry.getValue();
            if(!task.isDisable() && ScheduleJobStatus.STARTED == task.getStatus()){
                try{
                    runJob(task);
                }catch (SchedulerException e){
                    logger.trace("{}开始调度异常",task,e);
                }
            }
        }
    }
    /**
     * 关闭全部任务
     * @throws SchedulerException
     */
    public synchronized void shutDown() throws SchedulerException {
        Scheduler scheduler = getScheduler();
        if (!scheduler.isShutdown()) {
            scheduler.shutdown();
            logger.info("关闭所有调度任务");
        }
    }
    /**
     * 暂停全部任务
     * @throws SchedulerException
     */
    public synchronized void pause() throws SchedulerException{
        Scheduler sched = getScheduler();
        sched.pauseAll();
        logger.info("暂停全部任务");
    }
    /**
     * 恢复全部任务
     * @throws SchedulerException
     */
    public synchronized void resume() throws SchedulerException{
        Scheduler sched = getScheduler();
        sched.resumeAll();
        logger.info("恢复全部任务");
    }
    /**
     * 立即执行一个任务
     * @param task
     * @throws SchedulerException
     * @throws ClassNotFoundException
     */
    private synchronized void runJob(TaskObject task) throws SchedulerException, ClassNotFoundException {
        Scheduler sched = getScheduler();
        if (!sched.isShutdown()) {
            String classImpl = task.getClassPath();
            @SuppressWarnings("unchecked")
            Class<? extends Job> clazz = (Class<? extends Job>) Class.forName(classImpl);
            JobBuilder jobBuilder  = JobBuilder.newJob(clazz).withIdentity(task.getId(), task.getGroupId());
            String params = task.getParams();
            if(StringUtils.isNotBlank(params)){
                try {
                    Map<String,String> json = objectMapper.readValue(params, Map.class);
                    JobDataMap jobDataMap  = new JobDataMap();
                    for(String key : json.keySet()){
                        jobDataMap.put(key,json.get(key));
                    }
                    jobBuilder.setJobData(jobDataMap);//动态添加调度参数
                } catch (IOException e) {
                    logger.trace("{}任务参数格式不符合json对象格式已经跳过当前任务,参数:{}",task,task.getParams(),e);
                    return;
                }
            }
            JobDetail jobDetail =jobBuilder.build();
            Trigger trigger = TriggerBuilder.newTrigger()// 创建一个新的TriggerBuilder来规范一个触发器
                    .withIdentity(task.getId(),task.getGroupId())// 给触发器起一个名字和组名
                    .startNow()// 立即执行
                    .withSchedule(CronScheduleBuilder.cronSchedule(task.getExpression())) // 触发器的执行时间
                    .build();// 产生触发器
            sched.scheduleJob(jobDetail, trigger);
            logger.info("立即执行一个任务:{},时间格式:{}",task,task.getExpression());
        }
    }
    /**
     * 暂停一个任务
     * @throws SchedulerException
     */
    public synchronized void purseJob(TaskObject task) throws SchedulerException{
        Scheduler sched = getScheduler();
        JobKey jobKey = new JobKey(task.getId(), task.getGroupId());
        sched.pauseJob(jobKey);
        logger.info("暂停任务:{}",task);
    }
    /**
     * 恢复一个任务
     * @throws SchedulerException
     */
    public synchronized void resumeJob(TaskObject task) throws SchedulerException{
        Scheduler sched = getScheduler();
        JobKey jobKey = new JobKey(task.getId(), task.getGroupId());
        sched.resumeJob(jobKey);
        logger.info("恢复一个调度任务:{}",task);
    }
    /**
     * 中断任务
     * @throws UnableToInterruptJobException
     * @throws SchedulerException
     */
    public void interruptJob(TaskObject task) throws UnableToInterruptJobException, SchedulerException{
        JobKey jobKey = new JobKey(task.getId(), task.getGroupId());
        getScheduler().interrupt(jobKey);
        logger.info("中断一个调度任务:{}",task);
    }
    /**
     * 移除一个任务
     */
    public synchronized boolean removeJob(TaskObject task) throws SchedulerException{
        Scheduler server = getScheduler();
        TriggerKey key = new TriggerKey(task.getId(),task.getGroupId());
        server.pauseTrigger(key);// 停止触发器
        server.unscheduleJob(key);// 移除触发器
        logger.info("删除一个调度任务:{}",task);
        return server.deleteJob(new JobKey(task.getId(),task.getGroupId()));
    }
    /**
     * 添加一个定时任务
     * @throws SchedulerException
     * @throws ClassNotFoundException
     */
    public synchronized void addJob(TaskObject task) throws SchedulerException, ClassNotFoundException{
        Scheduler scheduler = getScheduler();
        JobKey jobKey = new JobKey(task.getId(), task.getGroupId());
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null) {
            Class<? extends Job> clazz = (Class<? extends Job>) Class.forName(task.getClassPath());
            JobBuilder jobBuilder  = JobBuilder.newJob(clazz).withIdentity(task.getId(), task.getGroupId());

            JobDataMap dataMap = new JobDataMap();
            try{
                Map<String,String> o = objectMapper.readValue(task.getParams(),Map.class);
                for(String key : o.keySet()){
                    dataMap.put(key, o.get(key));
                }
            }catch (IOException e) {
                logger.trace("{}任务参数格式不符合json对象格式添加失败,已经跳过当前任务,参数:{}",task,task.getParams(),e);
                return;
            }
            jobBuilder.setJobData(dataMap);
            Trigger trigger = TriggerBuilder.newTrigger()// 创建一个新的TriggerBuilder来规范一个触发器
                    .withIdentity(task.getId(),task.getGroupId())// 给触发器起一个名字和组名
                    .startNow()// 立即执行
                    .withSchedule(CronScheduleBuilder.cronSchedule(task.getExpression())) // 触发器的执行时间
                    .build();// 产生触发器
            scheduler.scheduleJob(jobDetail, trigger);
            logger.info("新添加了一个任务调度器:{},时间格式:{}",task,task.getExpression());
        }
    }
    /**
     * 修改调度器信息
     * @throws SchedulerException
     */
    public synchronized void modifyJobTime(TaskObject task) throws SchedulerException{
        Scheduler scheduler = getScheduler();
        TriggerKey triggerKey = new TriggerKey(task.getId(), task.getGroupId());
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        if (trigger == null) {
            return;
        }
        String oldTime = trigger.getCronExpression();
        if (!oldTime.equalsIgnoreCase(task.getExpression())) {
            scheduler.rescheduleJob(trigger.getKey(),trigger);
            logger.debug("修改调度器信息:{}",task);
        }
    }


    /**
     * 获取当前正在运行的调度任务
     * @return
     */
    public synchronized List<JobExecutionContext> getCurrentlyExecutingJobs(){
        List<JobExecutionContext> list = new ArrayList<JobExecutionContext>();
        try {
            Scheduler server = getScheduler();
            list = server.getCurrentlyExecutingJobs();
        } catch (SchedulerException e) {
            logger.error("获取当前正在运行的调度任务失败");
        }
        return list;
    }
    /**
     * 获取所有的在运行的触发器的列表
     * @return
     */
    public synchronized List<Trigger> getTriggerList(){
        List<Trigger> list = new ArrayList<Trigger>();
        try {
            Scheduler scheduler = getScheduler();
            for (String groupName : scheduler.getJobGroupNames()) {
                for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
                    String jobName = jobKey.getName();
                    String jobGroup = jobKey.getGroup();
                    List<Trigger> triggers = (List<Trigger>) scheduler.getTriggersOfJob(jobKey);
                    Date nextFireTime = triggers.get(0).getNextFireTime();//获取下载触发时间
                    list.addAll(triggers);
                }
            }
        } catch (SchedulerException e) {
            logger.error("获取所有的在运行的触发器的列表失败");
        }
        return list;
    }

    /**
     * 获取触发器的状态
     * 暂停：1 正常：0
     * @param jobName
     * @return
     * @throws SchedulerException
     */
    public synchronized Trigger.TriggerState getTriggerState(String jobName,String jobGroupName) throws SchedulerException{
        if(StringUtils.isNotBlank(jobName)){
            Scheduler sched = getScheduler();
            TriggerKey triggerKey = new TriggerKey(jobName, jobGroupName);
            Trigger.TriggerState  s = sched.getTriggerState(triggerKey);
            return s;
        }
        logger.error("输入的调度名称不能为空{},调度组名{}",jobName,jobGroupName);
        return null;
    }
}
