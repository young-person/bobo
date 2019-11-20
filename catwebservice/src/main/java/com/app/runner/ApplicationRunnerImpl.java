package com.app.runner;

import com.app.config.CatWebServiceProperty;
import com.app.crawler.base.RCache;
import com.app.crawler.riches.BRiches;
import com.app.crawler.riches.producer.DataEventHandler;
import com.corundumstudio.socketio.SocketIOServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Date;
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

    @Override
    public void run(ApplicationArguments args) throws Exception {
        RCache rCache = new RCache();
        rCache.loadCatConfig();

        server.start();

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("任务正在运行:"+(new Date().getTime()));
            }
        },0,1000*10);
        timer.schedule(new TimerTask() {
            public void run() {

                BRiches bRiches = new BRiches();
                if (!bRiches.isRuning()) {
                    System.out.println("开始执行任务......");
                    bRiches.start();
                }
            }
        }, 0, Integer.valueOf(RCache.CAT_CACHE.get("periodTime").getValue()));

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                dataEventHandler.pushMsg();
            }
        }, 0, 5 * 1000);
    }


}
