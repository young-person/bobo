package com.app.runner;

import com.app.config.CatWebServiceProperty;
import com.app.config.CatXml;
import com.app.crawler.base.RCache;
import com.app.crawler.riches.BRiches;
import com.app.crawler.riches.producer.DataEventHandler;
import com.app.service.ReceiveRiches;
import com.corundumstudio.socketio.SocketIOServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Timer;
import java.util.TimerTask;

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

    @Override
    public void run(ApplicationArguments args) throws Exception {
        RCache rCache = new RCache();
        rCache.setPath(catWebServiceProperty.getCollocation());
        rCache.loadCatConfig();

        server.start();
        CatXml catXml = new CatXml();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {

                BRiches bRiches = new BRiches();
                System.out.println("准备执行任务......");
                if (!bRiches.isRuning()) {
                    System.out.println("开始执行任务......");
                    bRiches.calculate(null,null);
                    System.out.println("结束执行任务......");
                }
            }
        }, 60 * 1000, 2 * 60 * 60 * 1000);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                dataEventHandler.pushMsg();
            }
        }, 30 * 60 * 1000, 5 * 1000);
        this.sendMessage();
    }

    /**
     * 发送通知
     */
    private void sendMessage(){
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                receiveRiches.sendScheduleData();
            }
        }, 30 * 60 * 1000, 30 * 60 * 1000);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                receiveRiches.receiveRichesData(BRiches.get());
            }
        }, 30 * 60 * 1000, 12 * 60 * 60 * 1000);

    }

}
