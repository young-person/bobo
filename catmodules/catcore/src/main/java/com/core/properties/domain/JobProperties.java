package com.core.properties.domain;

import com.core.properties.CatConfigurationPropertySource;

public class JobProperties extends CatConfigurationPropertySource<JobProperties> {
    /**
     * 作业名称
     */
    private String jobName;
    /**
     * cron表达式，用于控制作业触发时间
     */
    private String cron;
    /**
     * 作业分片总数
     */
    private int shardingTotalCount;
    /**
     * 分片序列号和参数用等号分隔，多个键值对用逗号分隔,分片序列号从0开始，不可大于或等于作业分片总数如：0=a,1=b,2=c
     */
    private String shardingItemParameters;

    /**
     * 作业自定义参数，可通过传递该参数为作业调度的业务方法传参，用于实现带参数的作业例：每次获取的数据量、作业实例从数据库读取的主键等
     */
    private String jobParameter;
    /**
     * 是否开启任务执行失效转移，开启表示如果作业在一次任务执行中途宕机，允许将该次未完成的任务在另一作业节点上补偿执行
     */
    private boolean failover;
    /**
     * 是否开启错过任务重新执行
     */
    private boolean misfire = true;
    /**
     * 作业描述信息
     */
    private String description;

    /**
     * 配置jobProperties定义的枚举控制Elastic-Job的实现细节JOB_EXCEPTION_HANDLER用于扩展异常处理类EXECUTOR_SERVICE_HANDLER用于扩展作业处理线程池类
     */
    private Enum jobProperties;

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public int getShardingTotalCount() {
        return shardingTotalCount;
    }

    public void setShardingTotalCount(int shardingTotalCount) {
        this.shardingTotalCount = shardingTotalCount;
    }

    public String getShardingItemParameters() {
        return shardingItemParameters;
    }

    public void setShardingItemParameters(String shardingItemParameters) {
        this.shardingItemParameters = shardingItemParameters;
    }

    public String getJobParameter() {
        return jobParameter;
    }

    public void setJobParameter(String jobParameter) {
        this.jobParameter = jobParameter;
    }

    public boolean isFailover() {
        return failover;
    }

    public void setFailover(boolean failover) {
        this.failover = failover;
    }

    public boolean isMisfire() {
        return misfire;
    }

    public void setMisfire(boolean misfire) {
        this.misfire = misfire;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Enum getJobProperties() {
        return jobProperties;
    }

    public void setJobProperties(Enum jobProperties) {
        this.jobProperties = jobProperties;
    }
}
