package com.app.cattask.config;

import com.app.cattask.pojo.UserToken;
import com.app.cattask.shiro.MyShiroRealm;
import com.app.cattask.shiro.filter.DeviceFilter;
import com.app.cattask.shiro.filter.KickoutSessionControlFilter;
import com.app.cattask.shiro.impl.DefaultPolicyLoginEvent;
import com.app.cattask.shiro.policy.PolicyLoginEvent;
import com.bobo.constant.Measure;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.crazycake.shiro.SerializeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.Filter;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Configuration
@AutoConfigureAfter(RedisConfig.class)
public class AppConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppConfig.class);

    @Autowired
    private RedisTemplate redisTemplate;

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
    public MemoryConstrainedCacheManager getMemoryConstrainedCacheManager(){
        MemoryConstrainedCacheManager manager = new MemoryConstrainedCacheManager();
        return manager;
    }

    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setCacheManager(colonyRedisManager());
        sessionManager.setSessionDAO(redisSessionDAO());
        return sessionManager;
    }


    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }

    public RedisManager redisManager() {
        ShiroRedisManager shiroRedisManager = new ShiroRedisManager();
        shiroRedisManager.setRedisTemplate(redisTemplate);
        return shiroRedisManager;
    }

    public CacheManager colonyRedisManager(){
        RedisCacheManager cacheManager = new RedisCacheManager();
        cacheManager.setRedisManager(redisManager());
        return cacheManager;
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


    class ShiroRedisManager extends RedisManager {
        private RedisTemplate<String, Object> redisTemplate;

        public void bindUserToken(Session session) {
            String sessionId = session.getId().toString();
            UserToken userToken = (UserToken) session.getAttribute("cat_user_token_key");
        }

        public byte[] get(byte[] key) {
            if (key != null) {
                byte[] val = (byte[]) this.redisTemplate.boundValueOps(new String(key)).get();
                return val;
            } else {
                return null;
            }
        }

        public byte[] set(byte[] key, byte[] value) {
            return key != null ? this.set(key, value, this.getExpire()) : null;
        }

        public byte[] set(byte[] key, byte[] value, int expire) {
            if (key != null) {
                this.redisTemplate.boundValueOps(new String(key)).set(value);
                if (expire != 0) {
                    this.redisTemplate.boundValueOps(new String(key)).expire((long) expire, TimeUnit.MILLISECONDS);
                }
            }

            this.redisTemplate.boundValueOps(new String(key)).get();
            return value;
        }

        public void del(byte[] key) {
            if (key != null) {
                this.redisTemplate.delete(new String(key));
            }

        }

        public int getExpire() {
            return super.getExpire();
        }

        public void setExpire(int expire) {
            super.setExpire(expire);
        }

        public void setExpire(String key, long expire, TimeUnit timeUnit) {
            this.redisTemplate.expire(key, expire, timeUnit);
        }

        public void flushDB() {
            this.redisTemplate.delete(this.getKeys("SESSION_CAT_COOKIE*"));
        }

        public Long dbSize() {
            return Integer.valueOf(this.getKeys("SESSION_CAT_COOKIE*").size()).longValue();
        }

        public Set<byte[]> keys(String pattern) {
            Set<String> keys = this.getKeys(pattern);
            Set<byte[]> keyBytes = new HashSet();
            Iterator var5 = keys.iterator();

            while (var5.hasNext()) {
                String k = (String) var5.next();
                keyBytes.add(k.getBytes());
            }

            return keyBytes;
        }

        public Set<String> getKeys(String pattern) {
            return this.redisTemplate.keys(pattern);
        }

        public void setRedisTemplate(RedisTemplate redisTemplate) {
            this.redisTemplate = redisTemplate;
        }
    }


    class AppRedisSessionDao extends AbstractSessionDAO {
        private RedisManager redisManager;
        private String keyPrefix = "SESSION_CAT_COOKIE:";

        public void update(Session session) throws UnknownSessionException {
            try {
                if (session instanceof ValidatingSession && !((ValidatingSession) session).isValid()) {
                    return;
                }
                this.saveSession(session);
            } catch (Exception e) {
                LOGGER.error("更新session 失败",e);
            }

        }

        private void saveSession(Session session) throws UnknownSessionException {
            if (session != null && session.getId() != null) {
                byte[] key = this.getByteKey(session.getId());
                byte[] value = SerializeUtils.serialize(session);
                int expire = (int) session.getTimeout();
                this.redisManager.set(key, value, expire);
            } else {
                LOGGER.error("session为null或者sessionId为null");
            }
        }

        public void delete(Session session) {
            RedisManager redisManager = this.getRedisManager();
            if (session != null && session.getId() != null) {
                redisManager.del(this.getByteKey(session.getId()));
            } else {
                LOGGER.error("session为null或者sessionId为null");
            }
        }

        public Collection<Session> getActiveSessions() {
            Set<Session> sessions = new HashSet();
            Set<byte[]> keys = this.redisManager.keys(this.keyPrefix + "*");
            if (keys != null && keys.size() > 0) {
                Iterator var4 = keys.iterator();

                while (var4.hasNext()) {
                    byte[] key = (byte[]) var4.next();
                    Session s = (Session) SerializeUtils.deserialize(this.redisManager.get(key));
                    sessions.add(s);
                }
            }

            return sessions;
        }

        protected Serializable doCreate(Session session) {
            Serializable sessionId = this.generateSessionId(session);
            this.assignSessionId(session, sessionId);
            this.saveSession(session);
            return sessionId;
        }

        protected Session doReadSession(Serializable sessionId) {
            if (sessionId == null) {
                LOGGER.error("sessionId为null");
                return null;
            } else {
                return (Session) SerializeUtils.deserialize(this.redisManager.get(this.getByteKey(sessionId)));
            }
        }

        private byte[] getByteKey(Serializable sessionId) {
            String preKey = this.keyPrefix + sessionId;
            return preKey.getBytes();
        }

        public void setKeyPrefix(String keyPrefix) {
            this.keyPrefix = keyPrefix;
        }

        public RedisManager getRedisManager() {
            return this.redisManager;
        }

        public void setRedisManager(RedisManager redisManager) {
            this.redisManager = redisManager;
            this.redisManager.init();
        }
    }



}
