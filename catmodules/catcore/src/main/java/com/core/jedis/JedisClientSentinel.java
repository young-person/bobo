
package com.core.jedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

import java.util.Set;

public class JedisClientSentinel implements JedisClient {

    private JedisSentinelPool jedisSentinelPool;

    public JedisClientSentinel(final JedisSentinelPool jedisSentinelPool) {
        this.jedisSentinelPool = jedisSentinelPool;
    }

    @Override
    public String set(final String key, final String value) {
        try (Jedis jedis = jedisSentinelPool.getResource()) {
            return jedis.set(key, value);
        }
    }

    @Override
    public String set(final String key, final byte[] value) {
        try (Jedis jedis = jedisSentinelPool.getResource()) {
            return jedis.set(key.getBytes(), value);
        }
    }

    @Override
    public Long del(final String... keys) {
        try (Jedis jedis = jedisSentinelPool.getResource()) {
            return jedis.del(keys);
        }
    }

    @Override
    public String get(final String key) {
        try (Jedis jedis = jedisSentinelPool.getResource()) {
            return jedis.get(key);
        }
    }

    @Override
    public byte[] get(final byte[] key) {
        try (Jedis jedis = jedisSentinelPool.getResource()) {
            return jedis.get(key);
        }
    }

    @Override
    public Set<byte[]> keys(final byte[] pattern) {
        try (Jedis jedis = jedisSentinelPool.getResource()) {
            return jedis.keys(pattern);
        }
    }

    @Override
    public Set<String> keys(final String key) {
        try (Jedis jedis = jedisSentinelPool.getResource()) {
            return jedis.keys(key);
        }
    }

    @Override
    public Long hset(final String key, final String item, final String value) {
        try (Jedis jedis = jedisSentinelPool.getResource()) {
            return jedis.hset(key, item, value);
        }
    }

    @Override
    public String hget(final String key, final String item) {
        try (Jedis jedis = jedisSentinelPool.getResource()) {
            return jedis.hget(key, item);
        }
    }

    @Override
    public Long hdel(final String key, final String item) {
        try (Jedis jedis = jedisSentinelPool.getResource()) {
            return jedis.hdel(key, item);
        }
    }

    @Override
    public Long incr(final String key) {
        try (Jedis jedis = jedisSentinelPool.getResource()) {
            return jedis.incr(key);
        }
    }

    @Override
    public Long decr(final String key) {
        try (Jedis jedis = jedisSentinelPool.getResource()) {
            return jedis.decr(key);
        }
    }

    @Override
    public Long expire(final String key, final int second) {
        try (Jedis jedis = jedisSentinelPool.getResource()) {
            return jedis.expire(key, second);
        }
    }

    @Override
    public Set<String> zrange(final String key, final long start, final long end) {
        try (Jedis jedis = jedisSentinelPool.getResource()) {
            return jedis.zrange(key, start, end);
        }
    }

}
