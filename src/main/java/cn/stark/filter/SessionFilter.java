package cn.stark.filter;

import cn.stark.common.Contants;
import cn.stark.common.HttpRequestWrapper;
import cn.stark.utils.CookieUtils;
import cn.stark.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;
import redis.clients.jedis.JedisPool;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by nimitz on 2016/11/18.
 */
public class SessionFilter extends OncePerRequestFilter implements Filter {

    private final static Logger LOGGER = LoggerFactory.getLogger(SessionFilter.class);

    @Autowired
    private static JedisPool jedisPool;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String sessionId = CookieUtils.getCookie(request, Contants.COOKIE_NAME);
        if (StringUtils.isEmpty(sessionId)) {
            sessionId = StringUtils.getUuid();
            CookieUtils.setCookie(request, response, Contants.COOKIE_NAME, sessionId);
        }
        filterChain.doFilter(new HttpRequestWrapper(request, response, sessionId), response);
    }
}
