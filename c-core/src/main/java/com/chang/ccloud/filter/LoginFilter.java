package com.chang.ccloud.filter;

import com.chang.ccloud.holder.RequestHolder;
import com.chang.ccloud.model.SysUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author changxizhao
 * @Date 2020/7/19 9:41
 * @Description
 */
public class LoginFilter implements Filter {

    private static Logger log = LoggerFactory.getLogger(LoginFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        log.info("前端请求拦截：{}，{}", request.getRequestURI(), request.getRemoteAddr());
        SysUser user = (SysUser) request.getSession().getAttribute("user");
        if(user == null) {
            response.sendRedirect("/login.html");
            return;
        }
//        String s = JsonConvertUtil.obj2String(user);
//        SysUser sysUser = JsonConvertUtil.String2Obj(s, new TypeReference<SysUser>() {});
//        log.info("filter user : {}", JsonConvertUtil.obj2String(sysUser));
        RequestHolder.remove();
        RequestHolder.add(user);
        RequestHolder.add(request);
        chain.doFilter(request, response);
        return;
    }

    @Override
    public void destroy() {

    }
}
