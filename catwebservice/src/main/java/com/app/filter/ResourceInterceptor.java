package com.app.filter;

import com.bobo.constant.Measure;
import com.bobo.utils.ComUtils;
import com.bobo.utils.CookieUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

public class ResourceInterceptor implements HandlerInterceptor {
    public static final Logger logger = LoggerFactory.getLogger(ResourceInterceptor.class);


    /**
     * 设置不拦截的url地址
     */
    private String[] urls = {"/","","/index","/system/login"};

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        String ip = ComUtils.getIpAddress(httpServletRequest);
        String schema = httpServletRequest.getScheme();
        String serverName = httpServletRequest.getServerName();//获取服务器名
        int port = httpServletRequest.getServerPort();//获取服务器端口号
        String contextPath = httpServletRequest.getContextPath();//获取项目名
        String servletPath = httpServletRequest.getServletPath();//获取Servlet路径
        String queryParam  = httpServletRequest.getQueryString();//获取get请求参数
        String requestUri  = httpServletRequest.getRequestURI();//获取请求URI，等于项目名+Servlet路径
        StringBuffer requestUrl  = httpServletRequest.getRequestURL();//获取请求URL，等于不包含参数的整个请求路径
        logger.info("ip:{},schema:{},serverName:{},port:{},contextPath:{},servletPath:{},queryParam:{},requestUri:{},requestUrl:{}",ip,schema,serverName,port,contextPath,servletPath,queryParam,requestUri,requestUrl);
        for(String s: urls){
            if (s.equals(requestUri)){
                return true;
            }
        }
        StringBuilder builder = new StringBuilder();
        builder.append(schema);
        builder.append("://");
        builder.append(serverName);
        builder.append(":");
        builder.append(port);
        builder.append(contextPath);
        httpServletRequest.setAttribute(Measure.CONTEXTKPATH,builder.toString());
        httpServletRequest.setAttribute(Measure.Addr,builder.toString());

        Object obj = httpServletRequest.getSession().getAttribute(Measure.head_Authorization);
        String token = null;
        if (Objects.nonNull(obj)){
            token = obj.toString();
        }

        if (StringUtils.isBlank(token)){
            httpServletResponse.sendRedirect(httpServletRequest.getSession().getServletContext().getContextPath()+"/");
            return false;
        }else{
            this.ok(token,httpServletResponse);
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        httpServletRequest.setAttribute(Measure.head_Authorization,"windlybobo");
    }

    /**
     * 判断当前登录的cookie 适合和当前的 session里面的 认证码 和当前 request 的认证码是否相等
     * @param httpServletRequest
     * @param t
     * @param token
     * @return
     */
    private boolean isOk(HttpServletRequest httpServletRequest,String t,String token){
        return StringUtils.isNotBlank(token) && t.equals(CookieUtils.getCookie(httpServletRequest,Measure.head_Authorization));
    }

    private void ok(String token, HttpServletResponse response){
        response.setHeader(Measure.head_Authorization,token);
    }
}
