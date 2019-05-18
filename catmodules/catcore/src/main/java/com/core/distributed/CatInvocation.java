package com.app.distributed;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 执行器实例
 */
public class CatInvocation implements Serializable {
    private static final long serialVersionUID = -5108578223428529356L;
    /**
     * 目标调用对象
     */
    private Class targetClass;
    /**
     * 方法
     */
    private String method;
    /**
     * 参数类型
     */
    private Class[] parameterTypes;
    /**
     * 参数
     */
    private Object[] args;

    public CatInvocation(Class targetClass, String method, Class[] parameterTypes, Object[] args) {
        this.targetClass = targetClass;
        this.method = method;
        this.parameterTypes = parameterTypes;
        this.args = args;
    }

    public Class getTargetClass() {
        return targetClass;
    }

    public void setTargetClass(Class targetClass) {
        this.targetClass = targetClass;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Class[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    @Override
    public String toString() {
        return "CatInvocation{" +
                "targetClass=" + targetClass +
                ", method='" + method + '\'' +
                ", parameterTypes=" + Arrays.toString(parameterTypes) +
                ", args=" + Arrays.toString(args) +
                '}';
    }
}
