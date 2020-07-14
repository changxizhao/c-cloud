package com.chang.ccloud.controller;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @Author changxizhao
 * @Date 2020/7/11 10:55
 * @Description
 */
@RestController
public class ResourceDataController {

    private static Logger log = LoggerFactory.getLogger(ResourceDataController.class);

    @Value("classpath:static/json/user_info.json")
    private Resource userInfo;

    @Value("classpath:static/json/menu.json")
    private Resource menuList;

    @GetMapping("/getUserData")
    public String getUserInfo(){
        try {
            String result = IOUtils.toString(userInfo.getInputStream(), Charset.forName("UTF-8"));
            return result;
        } catch (IOException e) {
            log.error("",e);
            return "";
        }
    }

    @GetMapping("/getMenuList")
    public String getMenuList(){
        try {
            String result = IOUtils.toString(menuList.getInputStream(), Charset.forName("UTF-8"));
            return result;
        } catch (IOException e) {
            log.error("",e);
            return "";
        }
    }

}
