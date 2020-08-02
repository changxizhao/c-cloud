package com.chang.ccloud.interceptor;

import com.chang.ccloud.common.utils.JsonConvertUtil;
import com.chang.ccloud.holder.RequestHolder;
import com.chang.ccloud.model.SysUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义拦截器
 * @Author changxizhao
 * @Date 2020/7/4 19:09
 * @Description
 */
@Component
public class HttpInterceptor implements HandlerInterceptor {

    private static Logger logger = LoggerFactory.getLogger(HttpInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        removeRequestHolderInfo();
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        removeRequestHolderInfo();
    }

    public void removeRequestHolderInfo() {
        RequestHolder.remove();
    }

}
