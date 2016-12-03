package cn.stark.common;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by nimitz on 2016/11/19.
 */
public class HttpSessionWrapper implements HttpSession {

    private String sessionId;

    private HttpSession httpSession;

    private HttpServletRequest httpServletRequest;

    private HttpServletResponse httpServletResponse;

    private Map<String, String> sessionContent;

    private JedisPool jedisPool;

    public HttpSessionWrapper(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, HttpSession httpSession, String sessionId) {
        this.httpServletRequest = httpServletRequest;
        this.httpServletResponse = httpServletResponse;
        this.httpSession = httpSession;
        this.sessionId = sessionId;
        sessionContent = new HashMap<String, String>();
        ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(httpSession.getServletContext());
        jedisPool = (JedisPool) applicationContext.getBean("JedisPool");
        Jedis jedis = jedisPool.getResource();
        jedis.hmset(sessionId,sessionContent);
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
        return sessionContent.get(s);
    }

    @Override
    public Object getValue(String s) {
        return sessionContent.get(s);
    }

    @Override
    public Enumeration getAttributeNames() {
        return sessionContent.;
    }

    @Override
    public String[] getValueNames() {
        return new String[0];
    }

    @Override
    public void setAttribute(String s, Object o) {
        sessionContent.put(s,o);
    }

    @Override
    public void putValue(String s, Object o) {

    }

    @Override
    public void removeAttribute(String s) {

    }

    @Override
    public void removeValue(String s) {

    }

    @Override
    public void invalidate() {

    }

    @Override
    public boolean isNew() {
        return false;
    }
}
