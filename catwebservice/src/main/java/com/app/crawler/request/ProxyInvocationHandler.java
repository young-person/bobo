package com.app.crawler.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ProxyInvocationHandler implements InvocationHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProxyInvocationHandler.class);
	
	private RestRequest restRequest;
	/**
	 * 重试次数
	 */
	private final int num = 3;

	public ProxyInvocationHandler(RestRequest restRequest) {
		this.restRequest = restRequest;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		boolean flag = true;
		int count = 1;
		Object obj = null;
		do {
			try {
				obj = method.invoke(restRequest, args);
				if (obj instanceof ResponseEntity) {
					ResponseEntity<?> responseEntity = (ResponseEntity<?>) obj;
					HttpStatus httpStatus = responseEntity.getStatusCode();
					if (httpStatus.value() < 300) {
						flag = true;
						break;
					}
				}
			} catch (Exception e) {
				flag = false;
				Thread.sleep(2*1000);
				LOGGER.error("第{}次代理请求失败，方法名称：【{}】参数：【{}】",count,method.getName(),args,e);

			}
			count++;
		} while (!flag & count <= num);
		
		return obj;
	}

}
