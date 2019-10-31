package com.app.runner;

import com.app.crawler.CrawlerDown;
import com.app.crawler.statistics.Department;
import com.app.crawler.statistics.WeatherRecordTask;
import com.app.crawler.video.sexforum.MainParse;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

@Component
public class ApplicationRunnerImpl implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
//        Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//            public void run() {
//                CrawlerDown down1 = new Department();
//                down1.start();
//                CrawlerDown down2 = new WeatherRecordTask();
//                down2.start();
//                CrawlerDown down3 = new MainParse();
//                down3.start();
//            }
//        }, 0,PERIOD_TIME);
    }

    /**
     * 开启任务
     */
    //默认时间间隔一天
    private static long PERIOD_TIME = 24 * 60 * 60 * 1000;

    public void startTask(TimerTask task, int dayInterval, int hour, int minute){
        PERIOD_TIME =  dayInterval * 24 * 60 * 60 * 1000;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        //执行定时任务的时间
        Date date=calendar.getTime();
        //为了避免若容器启动的时间晚于定时时间，在重启容器的时候会立刻执行该任务
        if (date.before(new Date())) {
            date = this.addDay(date, 1);
        }
        Timer timer = new Timer();
        //任务在指定的时间开始进行重复的固定延迟执行
        timer.schedule(task,date);


    }
    // 增加或减少天数
    public Date addDay(Date date, int num) {
        Calendar startDT = Calendar.getInstance();
        startDT.setTime(date);
        startDT.add(Calendar.DAY_OF_MONTH, num);
        return startDT.getTime();
    }
}
