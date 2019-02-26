package com.bobo.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class ComUtils {
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 请求中参数转Map<String, String>,for支付宝异步回调,平时建议直接使用request.getParameterMap(),返回Map<String, String[]>
     * @param request
     * @return
     */
    public static Map<String, String> getParameterMap(HttpServletRequest request) {
        Map<String, String> result = new HashMap<>();
        Enumeration parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String parameterName = (String) parameterNames.nextElement();
            result.put(parameterName, request.getParameter(parameterName));
        }
        return result;
    }


    /**
     * 设置cookie
     * @param response
     * @param name
     * @param value
     * @param maxAge
     */
    public static void setCookie(HttpServletResponse response, String name, String value, String path, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath(path);
        if (maxAge > 0) {
            cookie.setMaxAge(maxAge);
        }
        response.addCookie(cookie);
    }
    public static void setCookie(HttpServletResponse response, String name, String value, int maxAge) {
        setCookie(response, name, value, "/", maxAge);
    }
    public static void setCookie(HttpServletResponse response, String name, String value) {
        setCookie(response, name, value, "/", 3600);
    }
    public static void setCookie(HttpServletResponse response, String name) {
        setCookie(response, name, "", "/", 3600);
    }

    /**
     * 获取cookie
     * @param request
     * @param name
     * @return
     */
    public static String getCookie(HttpServletRequest request, String name) {
        String value = null;
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    value = cookie.getValue();
                }
            }
        }
        return value;
    }

    /**
     * 删除cookie
     * @param response
     * @param name
     * @return
     */
    public static void removeCookie(HttpServletResponse response, String name) {
        setCookie(response, name, "", "/", 0);
    }
    private static final int ZERO = 0;

    /**
     * return default object.
     * @param type class
     * @return Object
     */
    public static Object getDefaultValue(final Class type) {
        if (boolean.class.equals(type)) {
            return Boolean.FALSE;
        } else if (byte.class.equals(type)) {
            return ZERO;
        } else if (short.class.equals(type)) {
            return ZERO;
        } else if (int.class.equals(type)) {
            return ZERO;
        } else if (long.class.equals(type)) {
            return ZERO;
        } else if (float.class.equals(type)) {
            return ZERO;
        } else if (double.class.equals(type)) {
            return ZERO;
        }
        return null;
    }
}
