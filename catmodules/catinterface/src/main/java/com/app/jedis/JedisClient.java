
package com.app.jedis;

import java.util.Set;

public interface JedisClient {

    /**
     * set 操作.
     * @param key key
     * @param value key
     * @return String
     */
    String set(String key, String value);

    /**
     * set 操作.
     * @param key key
     * @param value key
     * @return String
     */
    String set(String key, byte[] value);

    /**
     * 批量删除key.
     * @param keys key集合
     * @return 数量
     */
    Long del(String... keys);

    /**
     * 根据key获取.
     * @param key redis key
     * @return String
     */
    String get(String key);

    /**
     * 根据key获取.
     * @param key redis key
     * @return  byte[]
     */
    byte[] get(byte[] key);


    /**
     * 根据key 模糊匹配.
     * @param pattern redis key
     * @return  Set byte[]
     */
    Set<byte[]> keys(byte[] pattern);

    /**
     * 根据key 模糊匹配.
     * @param key redis key
     * @return      Set String
     */
    Set<String> keys(String key);

    /**
     * hash set值.
     * @param key redis key
     * @param item hash key
     * @param value 值
     * @return 条数
     */
    Long hset(String key, String item, String value);

    /**
     * hash get 值.
     * @param key key
     * @param item hash key
     * @return value
     */
    String hget(String key, String item);

    /**
     * hash del 值.
     * @param key key
     * @param item hash key
     * @return 数量
     */
    Long hdel(String key, String item);

    /**
     * 增加.
     * @param key key
     * @return Long
     */
    Long incr(String key);

    /**
     * 减少.
     * @param key key
     * @return Long
     */
    Long decr(String key);


    /**
     * 设置key的过期时间.
     * @param key key
     * @param second 过期时间 秒
     * @return  Long
     */
    Long expire(String key, int second);

    /**
     * 分页获取zsort.
     * @param key key
     * @param start 开始
     * @param end 结束
     * @return Set String
     */
    Set<String> zrange(String key, long start, long end);

}
