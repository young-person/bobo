package com.bobo.utils;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
        if (StringUtils.isNotBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("realClientIP");
        }
        if (StringUtils.isNotBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
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

    public static String buildRedisKey(final String keyPrefix, final String id) {
        return String.join(":", keyPrefix, id);
    }

    public static String buildFilePath(final String applicationName) {
        return String.join("/", PATH_SUFFIX, applicationName.replaceAll("-", "_"));
    }

    public static String getFullFileName(final String filePath, final String id) {
        return String.format("%s/%s", filePath, id);
    }

    public static String buildDbTableName(final String applicationName) {
        return DB_SUFFIX + applicationName.replaceAll("-", "_");
    }

    public static String buildMongoTableName(final String applicationName) {
        return DB_SUFFIX + applicationName.replaceAll("-", "_");
    }

    public static String buildRedisKeyPrefix(final String applicationName) {
        return String.format(RECOVER_REDIS_KEY_PRE, applicationName);
    }

    public static String buildZookeeperPathPrefix(final String applicationName) {
        return String.join("-", PATH_SUFFIX, applicationName);
    }

    public static String buildZookeeperRootPath(final String prefix, final String id) {
        return String.join("/", prefix, id);
    }

    static String DB_MYSQL = "mysql";

    static String DB_SQLSERVER = "sqlserver";

    static String DB_ORACLE = "oracle";

    static String PATH_SUFFIX = "/myth";

    static String DB_SUFFIX = "myth_";

    static String RECOVER_REDIS_KEY_PRE = "myth:transaction:%s";

    static String MYTH_TRANSACTION_CONTEXT = "MYTH_TRANSACTION_CONTEXT";

    static String TOPIC_TAG_SEPARATOR = ",";

    static int SUCCESS = 1;

    static int ERROR = 0;




    /**
     * 获取过去任意天内的日期集合
     *
     * @param intervals intervals天内
     * @return 日期数组
     */
    public static List<String> pastDaysList(int intervals) {
        List<String> pastDaysList = new ArrayList<>();
        for (int i = -intervals + 1; i <= 0; i++) {
            pastDaysList.add(getPastDate(i, DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.CHINA)));
        }
        return pastDaysList;
    }
    public static String dateToWeek(String datetime) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
        Calendar cal = Calendar.getInstance(); // 获得一个日历
        Date datet = null;
        try {
            datet = f.parse(datetime);
            cal.setTime(datet);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
        if (w < 0)
            w = 0;
        return weekDays[w];
    }
    /**
     * 获取某天为基准，前后某天日期
     * @param offset offset 0今天，1明天，-1昨天，依次类推
     * @return
     */
    public static String getPastDate(int offset,DateTimeFormatter format) {
        return LocalDate.now().plusDays(offset).atStartOfDay().format(format);
    }
}
