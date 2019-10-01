package com.bobo.table.handler;

import java.lang.reflect.Proxy;

public class ProxyFactory {
    public static Object createPorxy(final Actuator target){
        Object proxyObject = Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                (proxy, method, args) -> {
                    Object res = null;
                    try {
                        res = method.invoke(target, args);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return res;
                });
        return proxyObject;
    }

    private Object target;
    public ProxyFactory(Object target){
        this.target=target;
    }

    //给目标对象生成代理对象
    public Object getProxyInstance(){
        return Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                (proxy, method, args) -> {
                    Object returnValue = method.invoke(target, args);
                    return returnValue;
                }
        );
    }

}
