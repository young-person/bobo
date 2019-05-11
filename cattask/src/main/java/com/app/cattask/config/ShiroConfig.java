package com.app.cattask.config;


import com.app.cattask.shiro.MyShiroRealm;
import com.app.cattask.shiro.filter.DeviceFilter;
import com.app.cattask.shiro.filter.KickoutSessionControlFilter;
import com.app.cattask.shiro.impl.DefaultPolicyLoginEvent;
import com.app.cattask.shiro.policy.PolicyLoginEvent;
import com.bobo.constant.Measure;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {


	@Bean
	public ShiroFilterFactoryBean shiroFilter() {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(getSecurityManager());
		shiroFilterFactoryBean.setLoginUrl("/login");
		shiroFilterFactoryBean.setSuccessUrl("/index");
		shiroFilterFactoryBean.setUnauthorizedUrl("/403");

		Map<String, Filter> filtersMap = new LinkedHashMap<String, Filter>();
		filtersMap.put("kickout", kickoutSessionControlFilter());
		filtersMap.put("deviceBindFilter", deviceBindFilter());
		shiroFilterFactoryBean.setFilters(filtersMap);
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();

		filterChainDefinitionMap.put("/kickout","anon");
		filterChainDefinitionMap.put("/dologin","anon");
		filterChainDefinitionMap.put("/test","anon");
		filterChainDefinitionMap.put("/static/**","anon");

		filterChainDefinitionMap.put("/logout","logout");
		filterChainDefinitionMap.put("/**","authc,user,kickout,deviceBindFilter");

		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return shiroFilterFactoryBean;
	}
	@Bean
	public MyShiroRealm myShiroRealm() {
		MyShiroRealm myShiroRealm = new MyShiroRealm();
		PolicyLoginEvent loginEvent = new DefaultPolicyLoginEvent();
		myShiroRealm.setLoginEvent(loginEvent);
		return myShiroRealm;
	}

	public SecurityManager getSecurityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(myShiroRealm());
		securityManager.setCacheManager(getMemoryConstrainedCacheManager());
		securityManager.setSessionManager(sessionManager());
	    securityManager.setRememberMeManager(rememberMeManager());
		return securityManager;
	}

	@Bean
	public  MemoryConstrainedCacheManager getMemoryConstrainedCacheManager(){
		MemoryConstrainedCacheManager manager = new MemoryConstrainedCacheManager();
		return manager;
	}

	@Bean
	public DefaultWebSessionManager sessionManager() {
		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
		sessionManager.setSessionDAO(redisSessionDAO());
		return sessionManager;
	}
	@Bean
	public RedisSessionDAO redisSessionDAO() {
		RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
		redisSessionDAO.setRedisManager(redisManager());
		return redisSessionDAO;
	}
	public RedisManager redisManager() {
		RedisManager redisManager = new RedisManager();
//		redisManager.setHost("203.195.250.160");
//		redisManager.setPort(7013);
		redisManager.setHost("127.0.0.1");
		redisManager.setPort(6379);
		redisManager.setExpire(1800);// 配置缓存过期时间
		redisManager.setTimeout(1000);
		// redisManager.setPassword(password);
		return redisManager;
	}


	public SimpleCookie rememberMeCookie(){
       SimpleCookie simpleCookie = new SimpleCookie(Measure.head_Authorization);
       simpleCookie.setMaxAge(2592000);
       return simpleCookie;
    }

    public CookieRememberMeManager rememberMeManager(){
       CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
       cookieRememberMeManager.setCookie(rememberMeCookie());
       cookieRememberMeManager.setCipherKey(Base64.decode("3AvVhmFLUs0KTA3Kprsdag=="));
       return cookieRememberMeManager;
    }

    public KickoutSessionControlFilter kickoutSessionControlFilter(){
    	KickoutSessionControlFilter kickoutSessionControlFilter = new KickoutSessionControlFilter();
    	kickoutSessionControlFilter.setCacheManager(getMemoryConstrainedCacheManager());
    	kickoutSessionControlFilter.setSessionManager(sessionManager());
    	kickoutSessionControlFilter.setKickoutAfter(false);
    	kickoutSessionControlFilter.setMaxSession(1);
        return kickoutSessionControlFilter;
     }

	@Bean
	public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor(Ordered.LOWEST_PRECEDENCE);
	}

	public DeviceFilter deviceBindFilter() {
		DeviceFilter deviceFilter = new DeviceFilter();
		return deviceFilter;
	}

}
