package cn.stark.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by nimitz on 2016/11/19.
 */
public class HttpRequestWrapper extends HttpServletRequestWrapper {

    private HttpServletRequest request;

    private HttpServletResponse response;

    private String sessionId;

    private HttpSession httpSessionWrapper;


    public HttpRequestWrapper(HttpServletRequest request, HttpServletResponse response, String sessionId) {
        super(request);
        this.request = request;
        this.response = response;
        this.sessionId = sessionId;
        httpSessionWrapper = new HttpSessionWrapper(request, response, super.getSession(true), sessionId);
    }

    @Override
    public HttpSession getSession() {
        if (httpSessionWrapper == null) {
            httpSessionWrapper = new HttpSessionWrapper(request, response, super.getSession(true), sessionId);
        }
        return httpSessionWrapper;
    }

    @Override
    public HttpSession getSession(boolean create) {
        HttpSession httpSession;
        if (httpSessionWrapper != null) {
            httpSession = httpSessionWrapper;
        } else {
            if(create){
                httpSessionWrapper = new HttpSessionWrapper(request, response, super.getSession(true), sessionId);
                httpSession = httpSessionWrapper;
            }else{
                httpSession = null;
            }
        }
        return httpSession;
    }
}
