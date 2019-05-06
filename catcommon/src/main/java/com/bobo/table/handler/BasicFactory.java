package com.bobo.table.handler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BasicFactory {
    protected static Map<String, Actuator> providers = new ConcurrentHashMap<String, Actuator>();
    // 静态方法
    public static <T> T createFactory(Class<? extends Actuator> clazz) throws InstantiationException,
            IllegalAccessException {
        // 静态工厂一般使用类的反射来构建对象，像上面的构建也可以。
        String path = clazz.getName();
        Actuator actuator  = providers.get(path);
        if (null==actuator) {
            actuator  = clazz.newInstance();
            providers.put(path, actuator);
        }
        return (T)actuator;
    }

    public static <T> T getResult(Actuator actuator, String sql){
        return  (T)actuator.exec(sql);
    }
}
