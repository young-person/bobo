package com.bobo.table.handler;

import java.lang.reflect.Proxy;
import java.util.Objects;

/**
 * 反射工厂
 */
public class ProxyFactory {

    private InvokeFilter filter;

    //给目标对象生成代理对象
    public <T> Object createPorxy(final T target) {
        Object proxyObject = Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                (proxy, method, args) -> {
                    Object res = null;
                    if (Objects.isNull(filter)) {
                        res = method.invoke(target, args);
                    } else if (Objects.nonNull(filter) && filter.isEqualMethod(method.getName(), args.length)) {
                        res = method.invoke(target, args);
                    }
                    return res;
                });
        return proxyObject;
    }


    interface InvokeFilter {
        boolean isEqualMethod(String methodName, int args);
    }

    public InvokeFilter getFilter() {
        return filter;
    }

    public void setFilter(InvokeFilter filter) {
        this.filter = filter;
    }
}
