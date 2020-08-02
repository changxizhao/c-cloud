package com.chang.ccloud.filter;

import com.chang.ccloud.common.Result;
import com.chang.ccloud.common.constants.HttpRequestStatus;
import com.chang.ccloud.common.utils.JsonConvertUtil;
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
public class ApiFilter implements Filter {

    private static Logger log = LoggerFactory.getLogger(ApiFilter.class);

    @Override
    public void init(javax.servlet.FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        /*HttpServletResponse response = (HttpServletResponse) resp;
        HttpServletRequest request = (HttpServletRequest) req;
        log.info("后端请求拦截：{}，{}", request.getRequestURI(), request.getRemoteAddr());
        SysUser user = (SysUser) request.getSession().getAttribute("user");
        if(user == null) {
            response.sendRedirect("/login.html");
            Result result = Result.fail(HttpRequestStatus.UNAUTHORIZATION.getCode(), "请登录");
            response.getWriter().write(JsonConvertUtil.obj2String(result));
            return;
        }
        RequestHolder.add(user);
        RequestHolder.add(request);
        chain.doFilter(request, response);
        return;*/
    }

    @Override
    public void destroy() {

    }
}
