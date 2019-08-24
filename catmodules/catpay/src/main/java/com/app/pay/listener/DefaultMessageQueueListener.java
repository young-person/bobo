//package com.app.pay.listener;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
//
//import javax.jms.Message;
//import javax.jms.MessageListener;
//import javax.jms.TextMessage;
//
//public class DefaultMessageQueueListener implements MessageListener {
//
//    private static final Logger logger = LoggerFactory.getLogger(DefaultMessageQueueListener.class);
//
//    @Autowired
//    ThreadPoolTaskExecutor threadPoolTaskExecutor;
//
//    @Override
//    public void onMessage(final Message message) {
//        threadPoolTaskExecutor.execute(new Runnable() {
//            @Override
//            public void run() {
//                if (message instanceof TextMessage) {
//                    TextMessage textMessage = (TextMessage) message;
//                    try {
//                        logger.info("消费：{}", textMessage.getText());
//                    } catch (Exception e) {
//                        logger.error("消息消费错误:{}",e);
//                    }
//                }
//            }
//        });
//    }
//
//}
//
