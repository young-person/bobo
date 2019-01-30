package com.app.zuul;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import javax.servlet.http.HttpServletRequest;

public class PreRequestFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;//int值来定义过滤器的执行顺序，数值越小优先级越高
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();

        doSomething(ctx);
        return null;
    }

    /**
     * 进行请求头 数据校验认证 逻辑处理 进行zuul放行
     * @param ctx
     */
    private void doSomething(RequestContext ctx){
        HttpServletRequest request = ctx.getRequest();
//        ctx.addZuulRequestHeader();
    }
}
