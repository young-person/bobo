package com.app.cattask.shiro;

import com.app.cattask.pojo.UserToken;
import com.app.cattask.shiro.policy.PolicyEvent;
import com.app.cattask.shiro.policy.PolicyLoginEvent;
import com.bobo.base.CatException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

/**
 * shiro身份校验核心类
 */

public class MyShiroRealm extends AuthorizingRealm {


	protected PolicyEvent event;

	private PolicyLoginEvent loginEvent;

	public PolicyLoginEvent getLoginEvent() {
		return loginEvent;
	}
	@Autowired
	public void setLoginEvent(PolicyLoginEvent loginEvent) {
		this.loginEvent = loginEvent;
	}

	public PolicyEvent getEvent() {
		return event;
	}
	@Autowired
	public void setEvent(PolicyEvent event) {
		this.event = event;
	}

	/**
	 * 认证信息.(身份验证) : Authentication 是用来验证用户身份
	 * 
	 * @return
	 * @throws AuthenticationException
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		if (null == authcToken)
			new CatException("认证信息为空");

		UserToken userToken = loginEvent.authentication(authcToken);

		if (null != userToken){
			if (null !=event){
				event.init(authcToken);
			}

			AuthenticationInfo info = new SimpleAuthenticationInfo(authcToken, userToken.getPassword(), this.getName());
			return info;
		}
		return null;
	}

	/**
	 * 授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		System.out.println("权限认证方法：MyShiroRealm.doGetAuthorizationInfo()");
		SimpleAuthorizationInfo info =  new SimpleAuthorizationInfo();
		Set<String> permissionSet = new HashSet<String>();
		permissionSet.add("权限删除");
		info.setStringPermissions(permissionSet);
        return info;
	}

	@Override
	protected Object getAuthorizationCacheKey(PrincipalCollection principals) {
		return principals;
	}

	@Override
	protected Object getAuthenticationCacheKey(AuthenticationToken token) {
		Object principal = token.getPrincipal();
		return principal;
	}
	@Override
	protected Object getAuthenticationCacheKey(PrincipalCollection principals) {
		return super.getAuthenticationCacheKey(principals);
	}
	@Override
	public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
		super.clearCachedAuthorizationInfo(principals);
	}
	@Override
	public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
		super.clearCachedAuthenticationInfo(principals);
	}
	@Override
	public void clearCache(PrincipalCollection principals) {
		super.clearCache(principals);
	}

}
