package com.app.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import redis.clients.jedis.JedisPoolConfig;

/** 
 * @Description: TODO
 * @date 2019年6月18日 下午6:57:15 
 * @ClassName: TestController 
 */
@Controller
@RequestMapping(value="test")
public class TestController {
    public static void main(String[] args) {
    	JedisPoolConfig config = new JedisPoolConfig();
    	
    	RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
    	JedisConnectionFactory connectionFactory = new JedisConnectionFactory();
    	connectionFactory.setHostName("129.28.191.240");
    	connectionFactory.setPort(6379);
    	connectionFactory.setPoolConfig(config);
    	connectionFactory.afterPropertiesSet();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
        redisTemplate.afterPropertiesSet();
        ValueOperations<String, Object>  op1 = redisTemplate.opsForValue();
        Object v = op1.get("aaaa");
        System.out.println(v);
        
		DefaultRedisScript<List> script = new DefaultRedisScript<List>();
		script.setScriptSource(new ResourceScriptSource(new ClassPathResource("script.lua")));
		script.setResultType(List.class);
		List<String> keys = new ArrayList<>();
        List<String> value = redisTemplate.execute(script, keys, "aaaa");
        System.out.println(value);
        
	}
    
    @RequestMapping(value =  "attendee",method=RequestMethod.POST)
    @ResponseBody
    public String name(@RequestParam("requiredKey") String requiredKey,@RequestParam("file") MultipartFile file) {
		System.out.println(requiredKey);
		return "aaaa"+requiredKey;
	}
}
