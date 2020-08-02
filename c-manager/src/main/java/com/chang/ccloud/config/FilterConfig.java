package com.chang.ccloud.config;

import com.chang.ccloud.filter.BrowserFilter;
import com.chang.ccloud.filter.LoginFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author changxizhao
 * @Date 2020/7/19 9:50
 * @Description
 */
@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<LoginFilter> loginFilterRegistrationBean() {
        FilterRegistrationBean<LoginFilter> loginFilterBean = new FilterRegistrationBean();
        loginFilterBean.setFilter(new LoginFilter());
        loginFilterBean.setName("loginFilter");
        loginFilterBean.setOrder(1);
        loginFilterBean.addUrlPatterns("/api/*", "/", "/index.html");
        return loginFilterBean;
    }

    @Bean
    public FilterRegistrationBean<BrowserFilter> browserFilterRegistrationBean() {
        FilterRegistrationBean<BrowserFilter> loginFilterBean = new FilterRegistrationBean();
        loginFilterBean.setFilter(new BrowserFilter());
        loginFilterBean.setName("browserFilter");
        loginFilterBean.setOrder(2);
        loginFilterBean.addUrlPatterns("/*");
        return loginFilterBean;
    }

}
