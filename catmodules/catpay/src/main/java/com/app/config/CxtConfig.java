package com.app.config;

import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.*;
import com.app.pay.listener.DefaultMessageQueueListener;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;


@Configuration
public class CxtConfig {

    //线程池配置
    @Bean
    public ThreadPoolTaskExecutor getThreadPoolTaskExecutor(){
		//线程池维护线程的最少数量
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(50);
        //线程池维护线程的最大数量，默认为Integer.MAX_VALUE
        executor.setMaxPoolSize(1000);
        //线程池所使用的缓冲队列，一般需要设置值>=notifyScheduledMainExecutor.maxNum；默认为Integer.MAX_VALUE
        executor.setQueueCapacity(20000);
        //线程池维护线程所允许的空闲时间，默认为60s
        executor.setKeepAliveSeconds(300);
        //线程池对拒绝任务（无线程可用）的处理策略，目前只支持AbortPolicy、CallerRunsPolicy；默认为后者
        //AbortPolicy:直接抛出java.util.concurrent.RejectedExecutionException异常
        // CallerRunsPolicy:主线程直接执行该任务，执行完之后尝试添加下一个任务到线程池中，可以有效降低向线程池内添加任务的速度
        //DiscardOldestPolicy:抛弃旧的任务、暂不支持；会导致被丢弃的任务无法再次被执行
        //DiscardPolicy:抛弃当前任务、暂不支持；会导致被丢弃的任务无法再次被执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }
//    private String brokerURL;
//    //连接工厂
//    @Bean
//    public ActiveMQConnectionFactory getActiveMQConnectionFactory(){
//        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
//        factory.setBrokerURL(brokerURL);
//        factory.setUseAsyncSend(true);
//        return factory;
//    }
//    @Bean
//    public CachingConnectionFactory getCachingConnectionFactory(){
//        CachingConnectionFactory factory = new CachingConnectionFactory();
//        factory.setTargetConnectionFactory(getActiveMQConnectionFactory());
//        factory.setSessionCacheSize(100);
//        return factory;
//    }
//    //点对点队列
//    @Bean
//    public ActiveMQQueue getActiveMQQueue(){
//        ActiveMQQueue queue = new ActiveMQQueue("cat.bobo.server.queue.default");
//        return queue;
//    }
//    //一对多队列
//    @Bean
//    public ActiveMQTopic getActiveMQTopic(){
//        ActiveMQTopic topic = new ActiveMQTopic("cat.bobo.server.topic.default");
//        return topic;
//    }
//    //生产者
//    @Bean
//    public JmsTemplate getJmsTemplate(){
//        JmsTemplate template = new JmsTemplate();
//        template.setConnectionFactory(getCachingConnectionFactory());
//        template.setPubSubDomain(false);
//        return template;
//    }
//    //生产者
//    @Bean("topicTemplate")
//    public JmsTemplate getTopicTemplate(){
//        JmsTemplate template = new JmsTemplate();
//        template.setConnectionFactory(getCachingConnectionFactory());
//        template.setPubSubDomain(true);
//        return template;
//    }
//    @Bean
//    public DefaultMessageQueueListener getDefaultMessageQueueListener(){
//        return new DefaultMessageQueueListener();
//    }
//    //消费者
//    @Bean
//    public DefaultMessageListenerContainer getDefaultMessageListenerContainer(){
//        DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
//        container.setConnectionFactory(getCachingConnectionFactory());
//        container.setDestination(getActiveMQQueue());
//        container.setMessageListener(getDefaultMessageQueueListener());
//        container.setSessionTransacted(true);
//        container.setConcurrency("4-10");
//        return container;
//    }
    /**
     * 支付客户端
     * @return
     */
    @Bean
    public DefaultAlipayClient getDefaultAlipayClient(){
        MProperties properties = new MProperties();
        DefaultAlipayClient client = new DefaultAlipayClient(properties.getAlipay_gateway(),
                properties.getAlipay_appid(),
                properties.getAlipay_rsaprivatekey(),
                properties.getAlipay_format(),
                properties.getAlipay_charset(),
                properties.getAlipay_public_key(),
                properties.getAlipay_sign_type());
        return client;
    }
    /**
     * 电脑网站支付
     * @return
     */
    @Bean
    public AlipayTradePagePayRequest getAlipayTradePagePayRequest(){
        MProperties properties = new MProperties();
        AlipayTradePagePayRequest pagePayRequest = new AlipayTradePagePayRequest();
        pagePayRequest.setReturnUrl(properties.getReturnUrl());
        pagePayRequest.setNotifyUrl(properties.getNotifyUrl());
        return pagePayRequest;
    }
    /**
     * 手机网站支付
     * @return
     */
    @Bean
    public AlipayTradeWapPayRequest getAlipayTradeWapPayRequest(){
        MProperties properties = new MProperties();
        AlipayTradeWapPayRequest wapPayRequest = new AlipayTradeWapPayRequest();
        wapPayRequest.setReturnUrl(properties.getReturnUrl());
        wapPayRequest.setNotifyUrl(properties.getNotifyUrl());
        return wapPayRequest;
    }
    /**
     * 交易查询
     * @return
     */
    @Bean
    public AlipayTradeQueryRequest getAlipayTradeQueryRequest(){
        AlipayTradeQueryRequest queryRequest = new AlipayTradeQueryRequest();
        return queryRequest;
    }
    /**
     * 交易退款
     * @return
     */
    @Bean
    public AlipayTradeRefundRequest getAlipayTradeRefundRequest(){
        AlipayTradeRefundRequest refundRequest = new AlipayTradeRefundRequest();
        return refundRequest;
    }
    /**
     * 交易退款查询
     * @return
     */
    @Bean
    public AlipayTradeFastpayRefundQueryRequest getAlipayTradeFastpayRefundQueryRequest(){
        AlipayTradeFastpayRefundQueryRequest refundQueryRequest = new AlipayTradeFastpayRefundQueryRequest();
        return refundQueryRequest;
    }
    /**
     * 交易关闭
     * @return
     */
    @Bean
    public AlipayTradeCloseRequest getAlipayTradeCloseRequest(){
        AlipayTradeCloseRequest tradeCloseRequest = new AlipayTradeCloseRequest();
        return tradeCloseRequest;
    }
    /**
     * 查询对账单下载地址
     * @return
     */
    @Bean
    public AlipayDataDataserviceBillDownloadurlQueryRequest getAlipayDataDataserviceBillDownloadurlQueryRequest(){
        AlipayDataDataserviceBillDownloadurlQueryRequest downloadurlQueryRequest =
                new AlipayDataDataserviceBillDownloadurlQueryRequest();
        return downloadurlQueryRequest;
    }

}
