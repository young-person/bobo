package com.bobo.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public abstract class TokenManager {
    protected static final Logger logger = LoggerFactory.getLogger(TokenManager.class);
    private final Timer timer = new Timer(true);
    // 令牌有效期，单位为秒，默认30分钟
    protected int tokenTimeout = 1800;

    public TokenManager(){
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                verifyExpired();
            }
        }, 60 * 1000, 60 * 1000);

    }
    protected void setTokenTimeout(int tokenTimeout) {
        this.tokenTimeout = tokenTimeout;
    }
    /**
     * 验证失效token
     */
    public abstract void verifyExpired();
    /**
     * 验证令牌是否失效 有效则延长session生命周期
     */
    public abstract String validator(String token);

    /**
     * 添加令牌
     */
    public abstract void addToken(String token,String value);
    /**
     * 移除令牌
     *
     * @param token
     */
    public abstract void remove(String token);

    /**
     * 扩展过期时间
     */
    protected void extendExpiredTime(Cin cin) {
        cin.date = new Date(new Date().getTime() + tokenTimeout * 1000);
    }

    protected class Cin{
        private Date date;
        private String serializable;
        public Cin(String serializable){
            this.serializable = serializable;
        }
        public Cin(Date date,String serializable){
            this.date = date;
            this.serializable = serializable;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public String getSerializable() {
            return serializable;
        }

        public void setSerializable(String serializable) {
            this.serializable = serializable;
        }
    }
}
