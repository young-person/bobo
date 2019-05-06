package com.app.config;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.util.*;

public class SqlLogInterceptor implements Interceptor {
    /**
     * 关注时间 单位秒，默认值 5
     * 如果 执行SQL 超过时间 就会打印error 日志
     */
    private static final Double noticeTime = 5.0;
    private boolean enableSqlLogInterceptor;
    private static final Logger logger = LoggerFactory.getLogger(SqlLogInterceptor.class);
    public static final String DISPLAY_SQL = "DISPLAY_SQL";
    private int limit = 1000;
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        long start = System.currentTimeMillis();

        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        Object parameter = null;
        if (invocation.getArgs().length > 1) {
            parameter = invocation.getArgs()[1];
        }
        BoundSql boundSql = mappedStatement.getBoundSql(parameter);
        Configuration configuration = mappedStatement.getConfiguration();
        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
        List<String> paramList = getParamList(configuration, boundSql);
        Object proceed = invocation.proceed();
        int result = 0;
        if (proceed instanceof ArrayList) {
            ArrayList resultList = (ArrayList) proceed;
            result = resultList.size();
        }
        if (proceed instanceof Integer) {
            result = (Integer) proceed;
        }
        if (enableSqlLogInterceptor) {
            long end = System.currentTimeMillis();
            long time = end - start;
            Boolean flag = true;// (Boolean) ThreadLocalMap.get(DISPLAY_SQL);
            if (time >= noticeTime * limit) {
                logger.error("执行超过{}秒,sql={}", noticeTime, sql);
                logger.error("result={}, time={}ms, params={}", result, time, paramList);
                return proceed;
            }
            if (flag == null || Objects.equals(flag, true)) {
                logger.info("sql={}", sql);
                logger.info("result={},time={}ms, params={}", result, time, paramList);
            }
        }
        return proceed;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }


    /**
     * 获取sql参数集合。
     *
     * @param configuration the configuration
     * @param boundSql      the bound sql
     *
     * @return the param list
     */
    private List<String> getParamList(Configuration configuration, BoundSql boundSql) {
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        List<String> params = new ArrayList<>();
        if (parameterMappings.size() > 0 && parameterObject != null) {
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                params.add(getParameterValue(parameterObject));
            } else {
                MetaObject metaObject = configuration.newMetaObject(parameterObject);
                for (ParameterMapping parameterMapping : parameterMappings) {
                    String propertyName = parameterMapping.getProperty();
                    if (metaObject.hasGetter(propertyName)) {
                        Object obj = metaObject.getValue(propertyName);
                        params.add(getParameterValue(obj));
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        Object obj = boundSql.getAdditionalParameter(propertyName);
                        params.add(getParameterValue(obj));
                    }
                }
            }
        }
        return params;
    }
    private String getParameterValue(Object obj) {
        String value;
        if (obj instanceof String) {
            value = "'" + obj.toString() + "'";
        } else if (obj instanceof Date) {
            DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);
            value = "'" + formatter.format(obj) + "'";
        } else {
            if (obj != null) {
                value = obj.toString();
            } else {
                value = "";
            }

        }
        return value;
    }
}
