package com.app.crawler.request;

import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 使用工厂方法创建 RestRequest
 * @author user
 *
 */
public class RestRequestFactory {

	static final Map<String, RestRequest> PROXYMAP = new ConcurrentHashMap<String, RestRequest>();
	/**
	 * 创建代理方式 进行运行
	 * 
	 * @param restRequest
	 * @return
	 */
	public static RestRequest createRestRequest(RestRequest restRequest) {
		
		if (null != PROXYMAP.get(restRequest.getClass().getName())) {
			return PROXYMAP.get(restRequest.getClass().getName());
		}else {
			ProxyInvocationHandler invocationHandler = new ProxyInvocationHandler(restRequest);
			RestRequest bean = (RestRequest) Proxy.newProxyInstance(restRequest.getClass().getClassLoader(),
					new Class<?>[] { RestRequest.class }, invocationHandler);
			PROXYMAP.put(restRequest.getClass().getName(),bean);
			return bean;
		}
	}

	
	/**
	 * 创建代理方式 进行运行 效率低下
	 * 
	 * @param restRequest
	 * @return
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public static RestRequest createRestRequest(Class<? extends RestRequest> clazz) throws InstantiationException, IllegalAccessException {
		return createRestRequest(clazz.newInstance());
	}
}
