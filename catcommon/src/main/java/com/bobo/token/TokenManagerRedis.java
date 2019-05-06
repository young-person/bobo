package com.bobo.token;

import com.bobo.utils.TokenManager;

public class TokenManagerRedis extends TokenManager {
    @Override
    public void verifyExpired() {

    }

    @Override
    public String validator(String token) {
        return null;
    }

    @Override
    public void addToken(String token,String value) {

    }

    @Override
    public void remove(String token) {

    }
}
