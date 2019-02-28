
package com.app.distributed.service.impl;

import com.app.distributed.CatTransaction;
import com.app.distributed.config.CatConfig;
import com.app.distributed.config.CoordinatorRepositoryAdapter;
import com.app.distributed.config.RedisConfig;
import com.app.distributed.context.RepositoryConvertUtils;
import com.app.distributed.service.CoordinatorRepository;
import com.app.jedis.JedisClient;
import com.app.jedis.JedisClientCluster;
import com.app.jedis.JedisClientSentinel;
import com.app.jedis.JedisClientSingle;
import com.bobo.enums.JTAEnum;
import com.bobo.serializer.CObjectSerializer;
import com.bobo.utils.ComUtils;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.*;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RedisCoordinatorRepository implements CoordinatorRepository {

    private CObjectSerializer objectSerializer;

    private JedisClient jedisClient;

    private String keyPrefix;

    @Override
    public int create(final CatTransaction transaction) {
        try {
            final String redisKey = ComUtils.buildRedisKey(keyPrefix, transaction.getTransId());
            jedisClient.set(redisKey, RepositoryConvertUtils.convert(transaction, objectSerializer));
            return JTAEnum.SUCCESS.getCode();
        } catch (Exception e) {
            e.printStackTrace();
            return JTAEnum.ERROR.getCode();
        }
    }

    @Override
    public int remove(final String transId) {
        try {
            final String redisKey = ComUtils.buildRedisKey(keyPrefix, transId);
            return jedisClient.del(redisKey).intValue();
        } catch (Exception e) {
            e.printStackTrace();
            return JTAEnum.ERROR.getCode();
        }
    }

    @Override
    public int update(final CatTransaction transaction) throws RuntimeException {
        try {
            final String redisKey = ComUtils.buildRedisKey(keyPrefix, transaction.getTransId());
            transaction.setVersion(transaction.getVersion() + 1);
            transaction.setLastTime(new Date());
            transaction.setRetriedCount(transaction.getRetriedCount() + 1);
            jedisClient.set(redisKey, RepositoryConvertUtils.convert(transaction, objectSerializer));
            return JTAEnum.SUCCESS.getCode();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateFailTransaction(final CatTransaction transaction) throws RuntimeException {
        try {
            final String redisKey = ComUtils.buildRedisKey(keyPrefix, transaction.getTransId());
            transaction.setLastTime(new Date());
            jedisClient.set(redisKey, RepositoryConvertUtils.convert(transaction, objectSerializer));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateParticipant(final CatTransaction transaction) throws RuntimeException {
        final String redisKey = ComUtils.buildRedisKey(keyPrefix, transaction.getTransId());
        byte[] contents = jedisClient.get(redisKey.getBytes());
        try {
            if (contents != null) {
                CoordinatorRepositoryAdapter adapter = objectSerializer.deSerialize(contents, CoordinatorRepositoryAdapter.class);
                adapter.setContents(objectSerializer.serialize(transaction.getParticipants()));
                jedisClient.set(redisKey, objectSerializer.serialize(adapter));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public int updateStatus(final String id, final Integer status) throws RuntimeException {
        final String redisKey = ComUtils.buildRedisKey(keyPrefix, id);
        byte[] contents = jedisClient.get(redisKey.getBytes());
        try {
            if (contents != null) {
                CoordinatorRepositoryAdapter adapter = objectSerializer.deSerialize(contents, CoordinatorRepositoryAdapter.class);
                adapter.setStatus(status);
                jedisClient.set(redisKey, objectSerializer.serialize(adapter));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return JTAEnum.SUCCESS.getCode();
    }

    @Override
    public CatTransaction findByTransId(final String transId) {
        try {
            final String redisKey = ComUtils.buildRedisKey(keyPrefix, transId);
            byte[] contents = jedisClient.get(redisKey.getBytes());
            return RepositoryConvertUtils.transformBean(contents, objectSerializer);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<CatTransaction> listAllByDelay(final Date date) {
        final List<CatTransaction> transactionList = listAll();
        return transactionList.stream()
                .filter(mythTransaction -> mythTransaction.getLastTime().compareTo(date) > 0)
                .filter(mythTransaction -> mythTransaction.getStatus() == JTAEnum.BEGIN.getCode())
                .collect(Collectors.toList());
    }

    private List<CatTransaction> listAll() {
        try {
            List<CatTransaction> transactions = Lists.newArrayList();
            Set<byte[]> keys = jedisClient.keys((keyPrefix + "*").getBytes());
            for (final byte[] key : keys) {
                byte[] contents = jedisClient.get(key);
                if (contents != null) {
                    transactions.add(RepositoryConvertUtils.transformBean(contents, objectSerializer));
                }
            }
            return transactions;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void init(final String modelName, final CatConfig mythConfig) {
        keyPrefix = ComUtils.buildRedisKeyPrefix(modelName);
        final RedisConfig redisConfig = mythConfig.getRedisConfig();
        try {
            buildJedisPool(redisConfig);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getScheme() {
        return "redis";
    }

    @Override
    public void setSerializer(final CObjectSerializer objectSerializer) {
        this.objectSerializer = objectSerializer;
    }

    private void buildJedisPool(final RedisConfig redisConfig) {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(redisConfig.getMaxIdle());
        //最小空闲连接数, 默认0
        config.setMinIdle(redisConfig.getMinIdle());
        //最大连接数, 默认8个
        config.setMaxTotal(redisConfig.getMaxTotal());
        //获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,  默认-1
        config.setMaxWaitMillis(redisConfig.getMaxWaitMillis());
        //在获取连接的时候检查有效性, 默认false
        config.setTestOnBorrow(redisConfig.getTestOnBorrow());
        //返回一个jedis实例给连接池时，是否检查连接可用性（ping()）
        config.setTestOnReturn(redisConfig.getTestOnReturn());
        //在空闲时检查有效性, 默认false
        config.setTestWhileIdle(redisConfig.getTestWhileIdle());
        //逐出连接的最小空闲时间 默认1800000毫秒(30分钟 )
        config.setMinEvictableIdleTimeMillis(redisConfig.getMinEvictableIdleTimeMillis());
        //对象空闲多久后逐出, 当空闲时间>该值 ，且 空闲连接>最大空闲数 时直接逐出,不再根据MinEvictableIdleTimeMillis判断  (默认逐出策略)，默认30m
        config.setSoftMinEvictableIdleTimeMillis(redisConfig.getSoftMinEvictableIdleTimeMillis());
        //逐出扫描的时间间隔(毫秒) 如果为负数,则不运行逐出线程, 默认-1
        config.setTimeBetweenEvictionRunsMillis(redisConfig.getTimeBetweenEvictionRunsMillis());
        //每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
        config.setNumTestsPerEvictionRun(redisConfig.getNumTestsPerEvictionRun());

        JedisPool jedisPool;
        //如果是集群模式
        if (redisConfig.getCluster()) {
            logger.error("build redis cluster ............");
            final String clusterUrl = redisConfig.getClusterUrl();
            final Set<HostAndPort> hostAndPorts =
                    Splitter.on(";")
                            .splitToList(clusterUrl)
                            .stream()
                            .map(HostAndPort::parseString).collect(Collectors.toSet());
            JedisCluster jedisCluster = new JedisCluster(hostAndPorts, config);
            jedisClient = new JedisClientCluster(jedisCluster);
        } else if (redisConfig.getSentinel()) {
            logger.error("build redis sentinel ............");
            final String sentinelUrl = redisConfig.getSentinelUrl();
            final Set<String> hostAndPorts =
                    new HashSet<>(Splitter.on(";")
                            .splitToList(sentinelUrl));

            JedisSentinelPool pool =
                    new JedisSentinelPool(redisConfig.getMasterName(), hostAndPorts,
                            config, redisConfig.getTimeOut(), redisConfig.getPassword());
            jedisClient = new JedisClientSentinel(pool);
        } else {
            if (StringUtils.isNoneBlank(redisConfig.getPassword())) {
                jedisPool = new JedisPool(config, redisConfig.getHostName(), redisConfig.getPort(),
                        redisConfig.getTimeOut(),
                        redisConfig.getPassword());
            } else {
                jedisPool = new JedisPool(config, redisConfig.getHostName(), redisConfig.getPort(),
                        redisConfig.getTimeOut());
            }
            jedisClient = new JedisClientSingle(jedisPool);
        }
    }
}
