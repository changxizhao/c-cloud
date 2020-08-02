package com.chang.ccloud.filter;

import com.github.pagehelper.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author changxizhao
 * @Date 2020/8/2 20:46
 * @Description
 */
public class BrowserFilter implements Filter {

    private static Logger log = LoggerFactory.getLogger(BrowserFilter.class);

    private final static String[] USER_AGENT = { "Android", "iPhone", "iPod", "iPad", "Windows Phone", "MQQBrowser" };


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        StringBuffer requestURI = request.getRequestURL();
        if(requestURI.toString().contains("error/browser.html")) {
            chain.doFilter(request, response);
            return;
        }

        String userAgent = request.getHeader("User-Agent");
        log.info("userAgent = {}", userAgent);
        if (StringUtil.isEmpty(userAgent)) {
            chain.doFilter(request, response);
            return;
        }
        boolean isMobile = false;
        if (!userAgent.contains("Windows NT") || (userAgent.contains("Windows NT") && userAgent.contains("compatible; MSIE 9.0;"))) {
            // 排除 苹果桌面系统
            if (!userAgent.contains("Windows NT") && !userAgent.contains("Macintosh")) {
                for (String item : USER_AGENT) {
                    if (userAgent.contains(item)) {
                        isMobile = true;
                        break;
                    }
                }
            }
        }
        if(isMobile) {
            response.sendRedirect("/error/browser.html");
            return;
        }else {
            chain.doFilter(request, response);
            return;
        }
    }
}
