package com.chang.ccloud.config;

import com.chang.ccloud.interceptor.HttpInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * 系统全局配置
 * @Author changxizhao
 * @Date 2020/7/4 19:37
 * @Description
 */
@Configuration
public class SysConfig extends WebMvcConfigurationSupport {

    @Autowired
    private HttpInterceptor httpInterceptor;

    /**
     * 添加自定义拦截器
     * @param: registry
     * @return void
     * @Author changxizhao
     * @Date 2020/7/4 19:58
     */
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(httpInterceptor).addPathPatterns("/**");
    }
}
