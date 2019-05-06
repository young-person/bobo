package com.app.cattask.shiro.policy;

import com.app.cattask.pojo.UserToken;
import org.apache.shiro.authc.AuthenticationToken;

public interface PolicyLoginEvent {

    public UserToken authentication(AuthenticationToken authcToken);

}
