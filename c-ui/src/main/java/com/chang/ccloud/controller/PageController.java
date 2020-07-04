package com.chang.ccloud.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author changxizhao
 * @Date 2020/7/4 23:04
 * @Description
 */
@Controller
public class PageController {

    @RequestMapping("/")
    public String index(){
        return "index";
    }

}
