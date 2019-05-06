package com.app.cattask.shiro.filter;

import com.app.cattask.pojo.UserToken;
import com.bobo.constant.Measure;
import com.bobo.enums.Message;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;
import java.util.Deque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * 1.读取当前登录用户名，获取在缓存中的sessionId队列
 * 2.判断队列的长度，大于最大登录限制的时候，按踢出规则
 *  将之前的sessionId中的session域中存入kickout：true，并更新队列缓存
 * 3.判断当前登录的session域中的kickout如果为true，
 * 想将其做退出登录处理，然后再重定向到踢出登录提示页面
 */
public class KickoutSessionControlFilter extends AccessControlFilter {

    //踢出后到的地址
    private String kickoutUrl = "login";
    //踢出之前登录的/之后登录的用户 默认踢出之前登录的用户
    private boolean kickoutAfter = false;
    //同一个帐号最大会话数 默认1  0不做限制
    private int maxSession = 1;

    private SessionManager sessionManager;
    private Cache<String, Deque<Serializable>> cache;


    public void setKickoutAfter(boolean kickoutAfter) {
        this.kickoutAfter = kickoutAfter;
    }

    public void setMaxSession(int maxSession) {
        this.maxSession = maxSession;
    }

    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }
    //设置Cache的key的前缀
    public void setCacheManager(CacheManager cacheManager) {
        this.cache = cacheManager.getCache(Measure.SHIRO_REDIS_CACHE);
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = getSubject(request,response);
        if(!subject.isAuthenticated() && !subject.isRemembered()){
            return true;
        }
        Session session = subject.getSession();
        Serializable sessionId = session.getId();
        UserToken userToken = (UserToken)subject.getPrincipal();
        Serializable id = session.getId();

        Deque<Serializable> deque = cache.get(userToken.getToken());
        if (null == deque){
            deque = new LinkedBlockingDeque<>();
        }
        //如果队列里面没有当前sessionID  没有登录过
        if (!deque.contains(id) && null == session.getAttribute(Measure.CONST_KICK_OUT)){
            deque.push(id);
            cache.put(userToken.getToken(),deque);
        }
        kickOut(deque,userToken.getToken());
        return kickOutAndRedirect( session, subject, request,  response);
    }


    private void kickOut(Deque<Serializable> deque,String tokenID){
        //如果队列里的sessionId数超出最大会话数，开始踢人
        if (0 == maxSession)
            return;
        while(deque.size() > maxSession) {
            Serializable kickoutSessionId = null;
            if(kickoutAfter) { //如果踢出后者
                kickoutSessionId = deque.removeFirst();
                //踢出后再更新下缓存队列
                cache.put(tokenID, deque);
            } else { //否则踢出前者
                kickoutSessionId = deque.removeLast();
                //踢出后再更新下缓存队列
                cache.put(tokenID, deque);
            }
            try {
                //获取被踢出的sessionId的session对象
                Session kickoutSession = sessionManager.getSession(new DefaultSessionKey(kickoutSessionId));
                if(kickoutSession != null) {
                    //设置会话的kickout属性表示踢出了
                    kickoutSession.setAttribute(Measure.CONST_KICK_OUT, true);
                }
            } catch (Exception e) {
            }
        }
    }

    private boolean kickOutAndRedirect(Session session,Subject subject,ServletRequest request, ServletResponse response) throws IOException {
        //如果被踢出了，直接退出，重定向到踢出后的地址
        if ((Boolean)session.getAttribute(Measure.CONST_KICK_OUT)!=null&&(Boolean)session.getAttribute(Measure.CONST_KICK_OUT) == true) {
            //会话被踢出了
            try {
                //退出登录
                subject.logout();
            } catch (Exception e) {
            }
            saveRequest(request);
            //判断是不是Ajax请求
            if ("XMLHttpRequest".equalsIgnoreCase(((HttpServletRequest) request).getHeader("X-Requested-With"))) {
                //输出json串
                response.setCharacterEncoding("UTF-8");
                response.setContentType("text/plain;charset=UTF-8");
                response.getWriter().println(Message.HAS_ONLINE.getJson());
                response.flushBuffer();
            }else{
                //重定向
                WebUtils.issueRedirect(request, response, kickoutUrl);
            }
            return false;
        }
        return true;
    }
}
