package com.app.cattask.shiro.impl;

import com.app.cattask.pojo.UserToken;
import com.app.cattask.shiro.policy.PolicyLoginEvent;
import com.bobo.base.CatException;
import com.bobo.domain.AuthUser;
import com.bobo.domain.ResultMeta;
import com.cloud.feign.catmain.LoginActionFeign;
import org.apache.shiro.authc.AuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;


public class DefaultPolicyLoginEvent implements PolicyLoginEvent {
    @Autowired
    private LoginActionFeign loginActionFeign;
    @Override
    public UserToken authentication(AuthenticationToken authcToken) {
        UserToken userToken = (UserToken)authcToken;

        AuthUser user = new AuthUser();
        user.setUsername(userToken.getUsername());
        user.setPassword(new String(userToken.getPassword()));
        ResultMeta result = loginActionFeign.queryUserToToken (user);

        try {
            if (result.isSuccess()){
                UserToken token = (UserToken)userToken.clone();
                token.setToken((String) result.getData());
                return token;
            }
            return null;
        } catch (CloneNotSupportedException e) {
            throw new CatException("UserToken 拷贝错误!",e);
        }

    }
}
