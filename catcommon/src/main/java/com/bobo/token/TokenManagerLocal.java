package com.bobo.token;

import com.bobo.utils.TokenManager;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TokenManagerLocal extends TokenManager {
    private static final ConcurrentHashMap<String,Cin> localCache = new ConcurrentHashMap<String, Cin>();

    @Override
    public void verifyExpired() {
        Date now = new Date();
        for (Map.Entry<String, Cin> entry : localCache.entrySet()) {
            String token = entry.getKey();
            Cin cin = entry.getValue();
            // 当前时间大于过期时间
            if (now.compareTo(cin.getDate()) > 0) {
                // 已过期，清除对应token
                if (now.compareTo(cin.getDate()) > 0) {
                    localCache.remove(token);
                    logger.error("token : " + token + "已失效");
                }
            }
        }
    }

    @Override
    public String validator(String token) {
        Cin cin = localCache.get(token);
        if (null == cin)
            return null;
        extendExpiredTime(cin);
        return cin.getSerializable();
    }

    @Override
    public void addToken(String token,String value) {
        Date date = new Date();
        Cin cin = new Cin(value);
        extendExpiredTime(cin);
        localCache.put(token,cin);
    }

    @Override
    public void remove(String token) {
        localCache.remove(token);
    }


}
