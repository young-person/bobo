package com.module.app.properties;

public class CloudProperties {
    private ReliableMessageProperties message = new ReliableMessageProperties();
    private AliyunProperties aliyun = new AliyunProperties();
    private AsyncTaskProperties task = new AsyncTaskProperties();
    private SwaggerProperties swagger = new SwaggerProperties();
    private QiniuProperties qiniu = new QiniuProperties();
    private GaodeProperties gaode = new GaodeProperties();
    private JobProperties job = new JobProperties();
    private ZookeeperProperties zk = new ZookeeperProperties();

    public ReliableMessageProperties getMessage() {
        return message;
    }

    public void setMessage(ReliableMessageProperties message) {
        this.message = message;
    }

    public AliyunProperties getAliyun() {
        return aliyun;
    }

    public void setAliyun(AliyunProperties aliyun) {
        this.aliyun = aliyun;
    }

    public AsyncTaskProperties getTask() {
        return task;
    }

    public void setTask(AsyncTaskProperties task) {
        this.task = task;
    }

    public SwaggerProperties getSwagger() {
        return swagger;
    }

    public void setSwagger(SwaggerProperties swagger) {
        this.swagger = swagger;
    }

    public QiniuProperties getQiniu() {
        return qiniu;
    }

    public void setQiniu(QiniuProperties qiniu) {
        this.qiniu = qiniu;
    }

    public GaodeProperties getGaode() {
        return gaode;
    }

    public void setGaode(GaodeProperties gaode) {
        this.gaode = gaode;
    }

    public JobProperties getJob() {
        return job;
    }

    public void setJob(JobProperties job) {
        this.job = job;
    }

    public ZookeeperProperties getZk() {
        return zk;
    }

    public void setZk(ZookeeperProperties zk) {
        this.zk = zk;
    }


}
