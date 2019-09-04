package com.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** 
 * @Description: TODO
 * @date 2019年6月15日 上午10:47:29 
 * @ClassName: RedisConfig 
 */
public class RedisConfig {
//	private String clusterNodes = "129.28.191.240:6379,129.28.191.240:6380,129.28.191.240:6381,129.28.191.240:6382,129.28.191.240:6383,129.28.191.240:6384";

	private String clusterNodes = "10.14.122.63:7379,10.14.122.63:7380,10.14.122.63:7381,10.14.122.63:8379,10.14.122.63:8380,10.14.122.63:8381";
	private int database = 8;
	@Bean
    public JedisPoolConfig getJedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        return jedisPoolConfig;
    }

    /**
     * Redis集群的配置
     * @return RedisClusterConfiguration
     * @throws
     */
    @Bean
    public RedisClusterConfiguration redisClusterConfiguration(){
        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
        String[] serverArray = clusterNodes.split(",");
        Set<RedisNode> nodes = new HashSet<RedisNode>();
        for(String ipPort:serverArray){
            String[] ipAndPort = ipPort.split(":");
            nodes.add(new RedisNode(ipAndPort[0].trim(),Integer.valueOf(ipAndPort[1])));
        }
        redisClusterConfiguration.setClusterNodes(nodes);
        redisClusterConfiguration.setMaxRedirects(3);
        return redisClusterConfiguration;
    }

    /**
     * @param
     * @return
     * @Description:redis连接工厂类
     * @date 2018/10/25 19:45
     */
    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        //集群模式
 		JedisConnectionFactory  factory = new JedisConnectionFactory(redisClusterConfiguration(),getJedisPoolConfig());
        factory.setDatabase(database);
        factory.setUsePool(true);
        factory.afterPropertiesSet();
        return factory;
    }
//    @Bean
//    LettuceConnectionFactory redisConnectionFactory() {
//        return new LettuceConnectionFactory(redisClusterConfiguration());
//    }
    /**
     * 实例化 RedisTemplate 对象
     *
     * @return
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        initDomainRedisTemplate(redisTemplate);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    /**
     * 设置数据存入 redis 的序列化方式,并开启事务
     *
     */
    private void initDomainRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        //如果不配置Serializer，那么存储的时候缺省使用String，如果用User类型存储，那么会提示错误User can't cast to String！
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //这个地方有一个问题，这种序列化器会将value序列化成对象存储进redis中，如果
        //你想取出value，然后进行自增的话，这种序列化器是不可以的，因为对象不能自增；
        //需要改成StringRedisSerializer序列化器。
        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setEnableTransactionSupport(false);
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
    }
    
    public static void main(String[] argss) {
    	RedisConfig config = new RedisConfig();
    	RedisTemplate<String, Object> template = config.redisTemplate();

    	for(int i = 0; i < 10; i ++){
            template.opsForSet().add("{20190624}:test1","test"+i);
        }


    	 List<String> keys = new ArrayList<String>();
//         keys.add("test_key1");

         keys.add(String.format("{cherry_cache:subapp_user}:%s:%s","213674717","aaa"));
//keys.add("{20190624}:test1");
//         keys.add(String.format("cherry_cache:subapp_org:%s:%s","213674717","ZXWK"));
//         keys.add(String.format("cherry_cache:subapp_org:%s:%s","213674717","30205000"));
//         keys.add(String.format("cherry_cache:subapp_org:%s:%s","213674717","1004"));
//         keys.add(String.format("cherry_cache:subapp_org:%s:%s","213674717","1004003"));
//
//         keys.add(String.format("cherry_cache:subapp_device:%s:%s","213674717","9D4A41FB-4486-4672-B8C4-D82D4E4FBA70"));
//         keys.add(String.format("{cherry_cache:subapp}:%s"));

         List<String> args = new ArrayList<String>();
         args.add("hello,key1");
//         String LUA = "redis.call('SET', KEYS[1], ARGV[1]); return ARGV[1]";



         
         StringBuilder builder = new StringBuilder();
         builder.append("local temp = {};");
         builder.append("for k,v in ipairs(KEYS) do ");
         builder.append("	local tmp2 = redis.call('SMEMBERS', v);");
         builder.append("   temp[k] = tmp2");
         builder.append(" end;");
         builder.append("return temp;");
         
         //spring自带的执行脚本方法中，集群模式直接抛出不支持执行脚本异常，此处拿到原redis的connection执行脚本
         List<String> result = (List<String>)template.execute(new RedisCallback<List<String>>() {
             public List<String> doInRedis(RedisConnection connection) throws DataAccessException {
                 Object nativeConnection = connection.getNativeConnection();
                 // 集群模式和单点模式虽然执行脚本的方法一样，但是没有共同的接口，所以只能分开执行
                 // 集群
                 if (nativeConnection instanceof JedisCluster) {
                     return (List<String>) ((JedisCluster) nativeConnection).eval(builder.toString(), keys, args);
                 }
                 // 单点
                 else if (nativeConnection instanceof Jedis) {
                     return (List<String>) ((Jedis) nativeConnection).eval(builder.toString(), keys, args);
                 }
                 return null;
             }
         });
		System.out.println(result);
    	template.opsForValue().set("aaaa", "爱我中华");
    	Object object = template.opsForValue().get("aaaa");
    	System.out.println(object);
    }
}
