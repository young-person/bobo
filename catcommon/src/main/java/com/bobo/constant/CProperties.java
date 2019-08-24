package com.bobo.constant;

import com.bobo.base.BaseClass;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class CProperties extends BaseClass {

    //策略url
    public static final String ALIYUN_OSS_POLICY = InitProperties.getInstance().getCfgValue("cat.aliyun.oss.policy");

    public static final String ALIYUN_OSS_ACCESSKEYID = InitProperties.getInstance().getCfgValue("aliyun.oss.accessKeyId");

    public static final String ALIYUN_OSS_ACCESSKEYSECRET = InitProperties.getInstance().getCfgValue("aliyun.oss.accessKeySecret");

    public static final String ALIYUN_OSS_ENDPOINT_INTERNAL = InitProperties.getInstance().getCfgValue("aliyun.oss.endpoint.internal");
    // endpoint
    public static final String ALIYUN_OSS_ENDPOINT = InitProperties.getInstance().getCfgValue("aliyun.oss.endpoint");

    // bucketName
    public static final String ALIYUN_OSS_BUCKET_NAME = InitProperties.getInstance().getCfgValue("aliyun.oss.bucketName");

    // 文件大小
    public static final int ALIYUN_OSS_MAX_SIZE = 10;

    // 签名有效期(单位:分钟)
    public static final int ALIYUN_OSS_EXPIRE = 300;

    //策略url
    public static final String ALIYUN_OSS_CALLBACK = InitProperties.getInstance().getCfgValue("aliyun.oss.callback");

    /**
     * redis ip
     */
    public static final String redis_ip = InitProperties.getInstance().getCfgValue("cat.redis.ip");
    /**
     * port
     */
    public static final int redis_port = Integer.valueOf(InitProperties.getInstance().getCfgValue("cat.redis.port"));
    /**
     * password
     */
    public static final String redis_password = InitProperties.getInstance().getCfgValue("cat.redis.password");
    public static final int redis_max_active = Integer.valueOf(InitProperties.getInstance().getCfgValue("cat.redis.max_active"));
    public static final int redis_max_idle = Integer.valueOf(InitProperties.getInstance().getCfgValue("cat.redis.max_idle"));
    public static final int redis_max_wait = Integer.valueOf(InitProperties.getInstance().getCfgValue("cat.redis.max_wait"));
    public static final int redis_timeout = Integer.valueOf(InitProperties.getInstance().getCfgValue("cat.redis.timeout"));


    private static class InitProperties{
        private static final String cfg = "cfg.properties";

        private volatile static InitProperties instance = null;
        private static Properties properties = null;

        private InitProperties(){
            init();
        }
        public static InitProperties getInstance(){
            if (null == instance){
                synchronized (InitProperties.class){
                    if (null == instance){
                        instance = new InitProperties();
                    }
                }
            }
            return instance;
        }

        public void init(){
            properties = new Properties();
            InputStream stream = InitProperties.class.getClassLoader().getResourceAsStream(cfg);
            try {
                properties.load(stream);
            } catch (IOException e) {
            	LOGGER.error("读取配置文件:{}失败",cfg,e.getMessage());
            }
        }

        public String getCfgValue(String key){
            return properties.getProperty(key);
        }
    }
}
