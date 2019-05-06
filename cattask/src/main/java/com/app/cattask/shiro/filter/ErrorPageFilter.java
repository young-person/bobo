package com.app.cattask.shiro.filter;

import javax.servlet.*;
import java.io.IOException;

public class ErrorPageFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.printf("ErrorPageFilter init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.printf("ErrorPageFilter doFilter");
    }

    @Override
    public void destroy() {
        System.out.printf("ErrorPageFilter destroy");
    }
}
