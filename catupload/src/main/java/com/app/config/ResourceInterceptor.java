package com.app.config;

import com.bobo.constant.Measure;
import com.bobo.utils.ComUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ResourceInterceptor implements HandlerInterceptor {
    public static final Logger logger = LoggerFactory.getLogger(ResourceInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String token = httpServletRequest.getHeader(Measure.head_Authorization);
        String ip = ComUtils.getIpAddress(httpServletRequest);
        String schema = httpServletRequest.getScheme();
        String serverName = httpServletRequest.getServerName();
        int port = httpServletRequest.getServerPort();
        String contextPath = httpServletRequest.getContextPath();
        String servletPath = httpServletRequest.getServletPath();

        logger.info("schema:{},serverName:{},port:{},contextPath:{},servletPath:{}",new Object[]{schema,serverName,
                port,contextPath,servletPath});
        StringBuilder builder = new StringBuilder();
        builder.append(schema);//http://localhost:9000/home
        builder.append("://");
        builder.append(serverName);
        builder.append(":");
        builder.append(port);
        builder.append(contextPath);
        httpServletRequest.setAttribute(Measure.CONTEXTKPATH,builder.toString());
        builder.append(servletPath);
        httpServletRequest.setAttribute(Measure.Addr,builder.toString());

        httpServletRequest.setAttribute("appname",Measure.APPNAME);
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
        httpServletRequest.setAttribute(Measure.head_Authorization,"c0b3cbd5545a40d096298eec2b8f3e25");
    }
}
