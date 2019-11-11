package com.app.runner;

import com.app.config.CatWebServiceProperty;
import com.app.crawler.riches.BRiches;
import com.app.crawler.riches.producer.DataEventHandler;
import com.app.pojo.Cat;
import com.app.pojo.Property;
import com.corundumstudio.socketio.SocketIOServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ApplicationRunnerImpl implements ApplicationRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationRunnerImpl.class);
    @Autowired
    private SocketIOServer server;
    @Autowired
    private DataEventHandler dataEventHandler;
    @Autowired
    private CatWebServiceProperty catWebServiceProperty;

    /**
     * 配置缓存
     */
    public static final Map<String, Property> CAT_CACHE = new ConcurrentHashMap<>();
    @Override
    public void run(ApplicationArguments args) throws Exception {
        this.loadCatConfig();
        LOGGER.debug("springboot 启动成功开始执行...");
        server.start();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
//                CrawlerDown down1 = new Department();
//                down1.start();
//                CrawlerDown down2 = new WeatherRecordTask();
//                down2.start();
//                CrawlerDown down3 = new MainParse();
//                down3.start();
                BRiches bRiches = new BRiches();
                bRiches.start();
            }
        }, 0, PERIOD_TIME);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                dataEventHandler.pushMsg();
            }
        }, 0, 5 * 1000);
    }

    /**
     * 开启任务
     */
    //默认时间间隔一天
    private static long PERIOD_TIME = 24 * 60 * 60 * 1000;

    public void startTask(TimerTask task, int dayInterval, int hour, int minute) {
        PERIOD_TIME = dayInterval * 24 * 60 * 60 * 1000;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        //执行定时任务的时间
        Date date = calendar.getTime();
        //为了避免若容器启动的时间晚于定时时间，在重启容器的时候会立刻执行该任务
        if (date.before(new Date())) {
            date = this.addDay(date, 1);
        }
        Timer timer = new Timer();
        //任务在指定的时间开始进行重复的固定延迟执行
        timer.schedule(task, date);


    }

    // 增加或减少天数
    public Date addDay(Date date, int num) {
        Calendar startDT = Calendar.getInstance();
        startDT.setTime(date);
        startDT.add(Calendar.DAY_OF_MONTH, num);
        return startDT.getTime();
    }

    /**
     * 加载配置文件
     */
    private void loadCatConfig() {
        try {
            LOGGER.debug("开始加载配置文件");
            File file = ResourceUtils.getFile(catWebServiceProperty.getCollocation());
            JAXBContext context = JAXBContext.newInstance(Cat.class);
            Unmarshaller unMar = context.createUnmarshaller();
            Cat cat = (Cat) unMar.unmarshal(file);
            List<Property> list = cat.getProperties();
            for (Property property : list) {
                CAT_CACHE.put(property.getName(), property);
                LOGGER.debug("加载配置项key：【】---》value：【】", property.getName(), property);
            }
            LOGGER.debug("加载配置文件完毕，配置项数量：【{}】", CAT_CACHE.size());

        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

}
