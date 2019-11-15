package com.app.crawler.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseClass {
	protected static final Logger LOGGER = LoggerFactory.getLogger(BaseClass.class);
    protected String trimSplit(String url) {
		
    	if (url != null && url.startsWith("/")) {
			return url.substring(1);
		}
    	
    	return url;
	}
}
