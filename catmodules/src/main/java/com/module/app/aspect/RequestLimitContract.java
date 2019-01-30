package com.module.app.aspect;

import com.bobo.annotation.RequestLimit;
import com.module.app.exception.RequestLimitException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

@Aspect
public class RequestLimitContract {
    /**
     * Logger
     */
    private static final Logger logger = LoggerFactory.getLogger(RequestLimitContract.class);
    /**
     * 缓存前缀
     */
    private static final String PREFIX = "req_limit_";

    @Before("within(@org.springframework.web.bind.annotation.RestController *) && @annotation(limit)")
    public void requestLimit(final JoinPoint joinPoint, RequestLimit limit) throws RequestLimitException {

        try {
            Object[] args = joinPoint.getArgs();
            HttpServletRequest request = null;
            for (Object arg : args) {
                if (arg instanceof HttpServletRequest) {
                    request = (HttpServletRequest) arg;
                    break;
                }
            }
            if (request == null) {
                throw new RequestLimitException("方法中缺失HttpServletRequest参数");
            }
            String ip = "";//WebHelper.getRemoteAddr(request);
            String url = request.getRequestURL().toString();
            String key = PREFIX.concat(url).concat(ip);
            // TODO 进行redis数据存储
            long count = 0;//redisTemplate.opsForValue().increment(key, 1);
            if (count == 1) {
                // TODO 进行redis数据存储
               // redisTemplate.expire(key, limit.time(), TimeUnit.MILLISECONDS);
            }
            if (count > limit.count()) {
                logger.info("用户IP[" + ip + "]访问地址[" + url + "]超过了限定的次数[" + limit.count() + "]");
                throw new RequestLimitException();
            }
        } catch (RequestLimitException e) {
            throw e;
        } catch (Exception e) {
            logger.error("发生异常: ", e);
        }
    }
}