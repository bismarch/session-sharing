package cn.stark.common;

import cn.stark.utils.CookieUtils;
import cn.stark.utils.RedisUtils;
import cn.stark.utils.SerializeUtil;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import java.io.IOException;
import java.util.*;

/**
 * Created by nimitz on 2016/11/19.
 */
public class HttpSessionWrapper implements HttpSession {

    private String sessionId;

    private HttpSession httpSession;

    private HttpServletRequest httpServletRequest;

    private HttpServletResponse httpServletResponse;

    private Map<String, String> sessionContent = null;

    private RedisUtils redisUtils;

    public HttpSessionWrapper(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, HttpSession httpSession, String sessionId) {
        this.httpServletRequest = httpServletRequest;
        this.httpServletResponse = httpServletResponse;
        this.httpSession = httpSession;
        this.sessionId = sessionId;
        redisUtils = (RedisUtils) WebApplicationContextUtils.getWebApplicationContext(this.getServletContext()).getBean("redisUtils");
    }

    public Map<String, String> getSessionContent() {
        return redisUtils.hgetall(sessionId);
    }

    @Override
    public long getCreationTime() {
        return httpSession.getCreationTime();
    }

    @Override
    public String getId() {
        return this.sessionId;
    }

    @Override
    public long getLastAccessedTime() {
        return httpSession.getLastAccessedTime();
    }

    @Override
    public ServletContext getServletContext() {
        return httpSession.getServletContext();
    }

    @Override
    public void setMaxInactiveInterval(int i) {
        httpSession.setMaxInactiveInterval(i);
    }

    @Override
    public int getMaxInactiveInterval() {
        return httpSession.getMaxInactiveInterval();
    }

    @Override
    public HttpSessionContext getSessionContext() {
        return httpSession.getSessionContext();
    }

    @Override
    public Object getAttribute(String s) {
        if (sessionContent != null) {
            return sessionContent.get(s);
        } else {
            return this.getSessionContent().get(s);
        }
    }

    @Override
    public Object getValue(String s) {
        return sessionContent.get(s);
    }

    @Override
    public Enumeration getAttributeNames() {
        Enumeration<String> enumeration = new Enumeration<String>() {
            Iterator<String> iterator = sessionContent.keySet().iterator();

            @Override
            public boolean hasMoreElements() {
                return iterator.hasNext();
            }

            @Override
            public String nextElement() {
                return iterator.next();
            }
        };
        return enumeration;
    }

    @Override
    public String[] getValueNames() {
        return new String[0];
    }

    @Override
    public void setAttribute(String s, Object o) {
        try {
            redisUtils.hsetnx(sessionId, s, new String(SerializeUtil.serialize(o), "UTF-8"));
            if(sessionContent!=null){
                sessionContent.put(s,new String(SerializeUtil.serialize(o),"UTF-8"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void putValue(String s, Object o) {
        setAttribute(s, o);
    }

    @Override
    public void removeAttribute(String s) {
        redisUtils.hdel(sessionId,s);
        sessionContent.remove(s);
    }

    @Override
    public void removeValue(String s) {
        removeAttribute(s);
    }

    @Override
    public void invalidate() {
        sessionContent.clear();
        redisUtils.hdel(sessionId,(String [])this.getSessionContent().keySet().toArray());
        CookieUtils.removeCookieValue(httpServletRequest, httpServletResponse, Contants.COOKIE_NAME);
    }

    @Override
    public boolean isNew() {
        return httpSession.isNew();
    }
}
