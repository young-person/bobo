package com.core.properties;

import com.core.properties.domain.*;

public class CatCloudProperties {
    private ReliableMessageProperties message;
    private AliyunProperties aliyun ;
    private AsyncTaskProperties task;
    private SwaggerProperties swagger;
    private QiniuProperties qiniu;
    private GaodeProperties gaode;
    private JobProperties job;
    private ZookeeperProperties zookeeperProperties;
    private RedisProperties redisProperties;

    public RedisProperties getRedisProperties() {
        return redisProperties == null? redisProperties = new RedisProperties() :redisProperties;
    }

    public void setRedisProperties(RedisProperties redisProperties) {
        this.redisProperties = redisProperties;
    }

    public ReliableMessageProperties getMessage() {
        return message == null? message = new ReliableMessageProperties() :message;
    }

    public void setMessage(ReliableMessageProperties message) {
        this.message = message;
    }

    public AliyunProperties getAliyun() {
        return aliyun == null ? aliyun = new AliyunProperties() :aliyun;
    }

    public void setAliyun(AliyunProperties aliyun) {
        this.aliyun = aliyun;
    }

    public AsyncTaskProperties getTask() {
        return task == null ? task = new AsyncTaskProperties() : task;
    }

    public void setTask(AsyncTaskProperties task) {
        this.task = task;
    }

    public SwaggerProperties getSwagger() {
        return swagger == null ? swagger = new SwaggerProperties() : swagger;
    }

    public void setSwagger(SwaggerProperties swagger) {
        this.swagger = swagger;
    }

    public QiniuProperties getQiniu() {
        return qiniu == null ? new QiniuProperties() : qiniu;
    }

    public void setQiniu(QiniuProperties qiniu) {
        this.qiniu = qiniu;
    }

    public GaodeProperties getGaode() {
        return gaode == null ? gaode = new GaodeProperties() : gaode;
    }

    public void setGaode(GaodeProperties gaode) {
        this.gaode = gaode;
    }

    public JobProperties getJob() {
        return job == null ? job = new JobProperties() : job;
    }

    public void setJob(JobProperties job) {
        this.job = job;
    }

	public ZookeeperProperties getZookeeperProperties() {
		return zookeeperProperties == null ? zookeeperProperties = new ZookeeperProperties() : zookeeperProperties;
	}

	public void setZookeeperProperties(ZookeeperProperties zookeeperProperties) {
		this.zookeeperProperties = zookeeperProperties;
	}


}
