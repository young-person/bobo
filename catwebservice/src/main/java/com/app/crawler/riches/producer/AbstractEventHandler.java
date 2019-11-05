package com.app.crawler.riches.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

public abstract class AbstractEventHandler implements ApplicationRunner{
	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractEventHandler.class);
	
    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {
        	
        } catch (Exception ex) {
            LOGGER.error("init error", ex);
        }
    }
    
    /**
     * 从新加载缓存键对应的值
     *
     * @param key 缓存键
     * @return 新的缓存值
     */
    protected abstract Object reloadKeyData(String key);
    
    /**
     * 获取消息事件发送对象（缓存刷新后用于推送数据到客户端）
     *
     * @return 消息事件发送对象
     */
    protected abstract MessageEventHandler getMessageEventHandler();
    
}
