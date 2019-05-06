package com.app.cattask.shiro.impl;

import com.app.cattask.pojo.UserToken;
import com.app.cattask.shiro.ShiroAuthService;
import com.app.cattask.shiro.policy.PolicyEvent;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DefaultShiroAuthService implements ShiroAuthService<UserToken> {
    @Override
    public int getOnlineMaxSize(HttpServletRequest request) {
        return 0;
    }

    @Override
    public boolean isEnableKickOut(HttpServletRequest request) {
        return false;
    }

    @Override
    public boolean isLoginRequest(HttpServletRequest request) {
        return false;
    }

    @Override
    public void onLoginSuccess(HttpServletRequest request, HttpServletResponse response, UserToken token) {

    }

    @Override
    public void onLoginFail(HttpServletRequest request, HttpServletResponse response) {

    }

    @Override
    public void init(PolicyEvent event) {
//        event.init();
    }
}
