package com.app.runner;

import com.app.config.CatWebServiceProperty;
import com.app.crawler.base.RCache;
import com.app.crawler.riches.BRiches;
import com.app.crawler.riches.producer.DataEventHandler;
import com.app.service.ReceiveRiches;
import com.corundumstudio.socketio.SocketIOServer;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

@Component
public class ApplicationRunnerImpl implements ApplicationRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationRunnerImpl.class);
    @Autowired
    private SocketIOServer server;
    @Autowired
    private DataEventHandler dataEventHandler;
    @Autowired
    private CatWebServiceProperty catWebServiceProperty;
    @Autowired
    private ReceiveRiches receiveRiches;

    /**
     * 定时刷新调度线程池
     */
    private static final ScheduledExecutorService EXECUTORS = new ScheduledThreadPoolExecutor(8, new ThreadFactoryBuilder().setNameFormat("showdata-refresh-pool-%d").build(), new ThreadPoolExecutor.CallerRunsPolicy());

    @Override
    public void run(ApplicationArguments args) throws Exception {
        RCache rCache = new RCache();
        rCache.setPath(catWebServiceProperty.getCollocation());
        rCache.loadCatConfig();

        server.start();

        EXECUTORS.scheduleAtFixedRate(() -> {
            BRiches bRiches = new BRiches();
            try {
                if (!bRiches.isRuning()) {
                    bRiches.start();
                }
            }catch (Exception e){
                LOGGER.error("开始抓取失败",e);
            }

        },30,12 * 3600,TimeUnit.SECONDS);

        this.sendMessage();
    }

    /**
     * 发送通知
     */
    private void sendMessage(){
        //推送数据给web前端
        EXECUTORS.scheduleAtFixedRate(() -> {
            try {
                dataEventHandler.pushMsg();
            }catch (Exception e){
                LOGGER.error("发送实时数据失败",e);
            }
        },60,60,TimeUnit.SECONDS);
        //每一个小时发送监控数据
        EXECUTORS.scheduleAtFixedRate(() -> {
            try {
                receiveRiches.sendScheduleData();
            }catch (Exception e){
                LOGGER.error("发送监控数据失败",e);
            }
        },60,3600,TimeUnit.SECONDS);
        //每12个小时进行发送邮件数据
        EXECUTORS.scheduleAtFixedRate(() -> {
            try {
                receiveRiches.receiveRichesData(BRiches.get());
            }catch (Exception e){
                LOGGER.error("每12个小时进行发送邮件数据失败",e);
            }
        },600,12 * 3600,TimeUnit.SECONDS);
    }

}
