package cn.stark.filter;

import cn.stark.common.Contants;
import cn.stark.common.HttpRequestWrapper;
import cn.stark.service.RedisService;
import cn.stark.utils.CookieUtils;
import cn.stark.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by nimitz on 2016/11/18.
 */
public class SessionFilter implements Filter {

    private final static Logger LOGGER = LoggerFactory.getLogger(SessionFilter.class);

    private static JedisPool jedisPool;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        ServletContext servletContext = filterConfig.getServletContext();
        ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        jedisPool = (JedisPool) applicationContext.getBean("jedisPool");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        String sessionId = CookieUtils.getCookie(httpServletRequest, Contants.COOKIE_NAME);
        if (StringUtils.isEmpty(sessionId)) {
            sessionId = StringUtils.getUuid();
            CookieUtils.setCookie(new HttpRequestWrapper(httpServletRequest, httpServletResponse, sessionId), httpServletResponse, Contants.COOKIE_NAME, sessionId);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        LOGGER.info("destroy");
    }
}
