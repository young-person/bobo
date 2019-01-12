package com.app.task.load;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class QuartzManagerFactory {
    private static Map<String,QuartzManagerAbstract> providers = new ConcurrentHashMap<String,QuartzManagerAbstract>();
    // 静态方法
    public static<T> T createScheduleFactory(Class<? extends QuartzManagerAbstract> clazz) throws InstantiationException, IllegalAccessException {
        // 静态工厂一般使用类的反射来构建对象，像上面的构建也可以。
        String path = clazz.getName();
        QuartzManagerAbstract scheduleInit  = providers.get(path);
        if (null==scheduleInit) {
            scheduleInit  = clazz.newInstance();
            providers.put(path, scheduleInit);
        }
        return (T)scheduleInit;
    }
}
