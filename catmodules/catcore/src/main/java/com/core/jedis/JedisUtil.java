package com.core.jedis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.*;

import java.util.List;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Set;



/**
 *	@author  	Byron.Y.Y
 *  @optDate 	2016年10月10日
 *  redis连接池工具类
 */
public class JedisUtil {
	/**
	 * 日志属性
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(JedisUtil.class);
	
	/**
	 * 属性配置文件工具类 - 会读取classpath下的redis.properties
	 */
	private static ResourceBundle bundle = PropertyResourceBundle.getBundle("redis");
	
	/**
	 * redis 客户端连接实例
	 */
	private static JedisPool jedisPool = null;
	
	
	/**
	 * 获取jedis客户端连接对象
	 * @return
	 */
	private static Jedis getConnection(){
		Jedis jedis = null;
		try{
			if( null ==  jedisPool){
				initJedisPool();
			}
			jedis = jedisPool.getResource();
		}catch(Exception e){
			e.printStackTrace();
			LOGGER.error("获取redis连接失败!");
		}
		return jedis;
	}
	
	/**
	 * 初始化redis配置信息，并获得连接池对象
	 */
	private static void  initJedisPool(){
		try{
			JedisPoolConfig config = new JedisPoolConfig();
			config.setMaxIdle(Integer.parseInt(bundle.getString("redis.pool.maxIdle")));
			config.setMaxTotal(Integer.parseInt(bundle.getString("redis.pool.maxIdle")));
			config.setMaxWaitMillis(Integer.parseInt(bundle.getString("redis.pool.maxIdle")));
			config.setTestOnBorrow(Boolean.getBoolean(bundle.getString("redis.pool.testOnBorrow")));
			config.setTestOnReturn(Boolean.getBoolean(bundle.getString("redis.pool.testOnReturn")));

			jedisPool = new JedisPool(config, bundle.getString("redis.host"),Integer.parseInt(bundle.getString("redis.port")));
		}catch(Exception e){
			e.printStackTrace();
			LOGGER.error("获取redis连接失败!");
		}
	}
	
	
	/**
	 * 关闭连接
	 * @return
	 */
	public String closeConnection(){
		String result = null;
		try{
			result = getConnection().quit();
		}catch(Exception e){
			e.printStackTrace();
			LOGGER.info("关闭redis连接失败!");
		}
		return result;
	}
	
	/*
	 * 1 - 封装通用方法 
	 */
	/**
	 * 清空所有数据库实例的所有键
	 */
	public  static String flushAll(){
		String result = null;
		try{
			result = getConnection().flushAll();
		}catch(Exception e){
			e.printStackTrace();
			LOGGER.info("清空所有数据库实例的所有键失败!");
		}
		return result;
	}
	
	/**
	 * 清空当前数据库实例的所有键
	 */
	public  static String flushDB(){
		String result = null;
		try{
			result = getConnection().flushDB();
		}catch(Exception e){
			e.printStackTrace();
			LOGGER.info("清空当前数据库实例的所有键失败!");
		}
		return result;
	}
	
	/**
	 * 判断key是否存在
	 */
	public static  boolean exists(String key){
		boolean result = false;
		try{
			result =  getConnection().exists(key);
		}catch(Exception e){
			e.printStackTrace();
			LOGGER.info("判断键是否存在失败!");
		}
		return result;
	}
	
	/**
	 * 判断key的类型(string、list、set、hash、zset)
	 */
	public static String type(String key){
		String type = null;
		try{
			type =  getConnection().type(key);
		}catch(Exception e){
			e.printStackTrace();
			LOGGER.info("判断键类型失败!");
		}
		return type;
	}
	
	
	/**
	 * 对key设置超时时间，超过seconds秒则删除该key，seconds为0则不处理
	 */
	public static long expire(String key, int seconds){
		long result = 0;
		try{
			result =  getConnection().expire(key, seconds);
		}catch(Exception e){
			e.printStackTrace();
			LOGGER.info("对键设置超时时间失败!");
		}
		return result;
	}
	
	/**
	 * 获取key的剩余存活时间-秒
	 */
	public static long ttl(String key){
		long result = 0;
		try{
			result = getConnection().ttl(key);
		}catch(Exception e){
			e.printStackTrace();
			LOGGER.info("获取键剩余存活时间失败!");
		}
		return result;
	}
	
	/**
	 * 对key解除超时限制，返回1则已解除。返回0则未解除（一般是key不存在）
	 */
	public static long persist(String key){
		long result = 0;
		try{
			result = getConnection().persist(key);
		}catch(Exception e){
			e.printStackTrace();
			LOGGER.info("持久化键失败!");
		}
		return result;
	}
	
	/**
	 * 按匹配模式查看键
	 */
	public Set<String> checkKeys(String pattern){
		Set<String> keySet = null;
		try{
			keySet = getConnection().keys(pattern);
		}catch(Exception e){
			e.printStackTrace();
			LOGGER.info("按匹配模式查看键失败!");
		}
		return keySet;
	}
	
	
	/**
	 * string类型操作：设置键key的值为value
	 */
	public static String setValue(String key, String value){
		String result = null;
		try{
			result = getConnection().set(key, value);
		}catch(Exception e){
			e.printStackTrace();
			LOGGER.info("设置键值失败!");
		}
		return result;
	}
	
	/**
	 * string类型操作：获取键key的值
	 */
	public static String getValue(String key){
		String result = null;
		try{
			result = getConnection().get(key);
		}catch(Exception e){
			e.printStackTrace();
			LOGGER.info("获取键值失败!");
		}
		return result;
	}
	
	
	/*
	 * 3 - list操作方法
	 */
	/**
	 * 通过lpush插入list值
	 */
	public static long lpush(String key, String... value){
		long result = 0;
		try{
			result = getConnection().lpush(key, value);
		}catch(Exception e){
			e.printStackTrace();
			LOGGER.info("插入list值失败!");
		}
		return result;
	}
	
	/**
	 * 通过lpop获取list值
	 */
	public static String lpop(String key){
		String result = null;
		try{
			result = getConnection().lpop(key);
		}catch(Exception e){
			e.printStackTrace();
			LOGGER.info("通过lpop获取list值失败!");
		}
		return result;
	}
	
	/**
	 * 通过rpush插入list值
	 */
	public static long rpush(String key, String... value){
		long result = 0;
		try{
			result = getConnection().rpush(key, value);
		}catch(Exception e){
			e.printStackTrace();
			LOGGER.info("插入list值失败!");
		}
		return result;
	}
	
	/**
	 * 通过rpop获取list值
	 */
	public static String rpop(String key){
		String result = null;
		try{
			result = getConnection().rpop(key);
		}catch(Exception e){
			e.printStackTrace();
			LOGGER.info("通过rpop获取list值失败!");
		}
		return result;
	}
	
	/**
	 * 阻塞获取key的值，超时时间timeout为0则一直阻塞
	 */
	public static  List<String> brpop(String key, int timeout){
		List<String> result = null;
		try{
			result = getConnection().brpop(timeout, key);
			
		}catch(Exception e){
			e.printStackTrace();
			LOGGER.info("通过brpop获取list值失败!");
		}
		return result;
	}
	
	
	
	/*
	 * publish/subscribe封装
	 */
	public static void subscribe(JedisPubSub jedisPubSub, String... key){
		try{
			getConnection().subscribe(jedisPubSub, key);
		}catch(Exception e){
			e.printStackTrace();
			LOGGER.info("订阅" + key +"的消息失败!");
		}
	}
	
	
	/*
	 * 地理位置信息封装
	 */
	
	/**
	 * 获取经纬度
	 */
	public static List<GeoCoordinate> geopos(String key, String... members ){
		List<GeoCoordinate> result = getConnection().geopos(key, members);
		
		return result;
	}
	
	
	/**
	 * 分布式锁
	 */
	public static Long lock4Concurr(String key,String value){
		Jedis jedis = getConnection();
		Long exec = 0L;
		try {
			exec = jedis.setnx("LOCK." + key, value);
			if( exec == 1 ){
				jedis.expire(key, 15);
			}
			return exec;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(jedis !=null){
				jedis.close();
			}
		}
		return exec;
	}
	
	/**
	 * 释放分布式锁
	 */
	public static void releaseLock4Concurr(String key){
		Jedis jedis = getConnection();
		try {
			jedis.del("LOCK." + key);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(jedis !=null){
				jedis.close();
			}
		}
	}
	
	
	/*
	 * ...其余操作封装
	 */
	
}
