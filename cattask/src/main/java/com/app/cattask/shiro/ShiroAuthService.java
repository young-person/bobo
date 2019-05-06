package com.app.cattask.shiro;

import com.app.cattask.shiro.policy.PolicyEvent;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ShiroAuthService<T> {

    public int getOnlineMaxSize(HttpServletRequest request);

    public boolean isEnableKickOut(HttpServletRequest request);

    public boolean isLoginRequest(HttpServletRequest request);

    public void onLoginSuccess(HttpServletRequest request, HttpServletResponse response, T token);

    public void onLoginFail(HttpServletRequest request, HttpServletResponse response);

    public void init(PolicyEvent event);

}
