package com.config;

import com.bobo.constant.Measure;
import com.bobo.utils.ComUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ResourceInterceptor implements HandlerInterceptor {
    public static final Logger logger = LoggerFactory.getLogger(ResourceInterceptor.class);
    @Autowired
    private CatProperties catProperties;
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String token = httpServletRequest.getHeader(Measure.head_Authorization);
        String ip = ComUtils.getIpAddress(httpServletRequest);
        if (StringUtils.isBlank(token)){
            logger.error("请求token为空");
        }


        logger.info("请求ip地址:{}",ip);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        Object obj = httpServletRequest.getAttribute("request");
        httpServletRequest.setAttribute(Measure.head_Authorization,catProperties.getUserid());
    }
}
