package cn.stark.utils;

import cn.stark.common.Contants;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by nimitz on 2016/11/19.
 */
public class CookieUtils {

    /**
     * 设置cookie
     *
     * @param request
     * @param response
     * @param name
     * @param value
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String name, String value) {
        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(value)) {
            return;
        } else {
            Cookie cookie = new Cookie(name, value);
            cookie.setPath(Contants.COOKIE_PATH);
            cookie.setMaxAge(Contants.COOKIE_MAXAGE);
            response.setHeader("P3P", "CP='IDC DSP COR ADM DEVi TAIi PSA PSD IVAi IVDi CONi HIS OUR IND CNT'");
            response.addCookie(cookie);
        }
    }

    /**
     * 获取cookie
     *
     * @param request
     * @param name
     * @return
     */
    public static String getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equalsIgnoreCase(Contants.COOKIE_NAME)) {
                    return cookie.getValue();
                }
            }
        }
        return "";
    }
}
