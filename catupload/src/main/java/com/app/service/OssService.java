package com.app.service;


import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface OssService {
    public static final Logger logger = LoggerFactory.getLogger(OssService.class);
    /**
     * 签名生成
     * @return
     */
    public JSONObject policy();
}
