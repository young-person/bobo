package com.bobo.serializer;

import com.bobo.base.CatException;

public interface CObjectSerializer {

    /**
     * 序列化对象.
     *
     * @param obj 需要序更列化的对象
     * @return byte []
     * @throws Exception 异常信息
     */
    byte[] serialize(Object obj) throws Exception;


    /**
     * 反序列化对象.
     *
     * @param param 需要反序列化的byte []
     * @param clazz java对象
     * @param <T>   泛型支持
     * @return 对象
     * @throws Exception 异常信息
     */
    <T> T deSerialize(byte[] param, Class<T> clazz) throws CatException;

    /**
     * 设置scheme.
     *
     * @return scheme 命名
     */
    String getScheme();
}
