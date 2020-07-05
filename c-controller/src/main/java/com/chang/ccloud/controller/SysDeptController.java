package com.chang.ccloud.controller;

import com.chang.ccloud.common.Result;
import com.chang.ccloud.entities.bo.DeptBO;
import com.chang.ccloud.service.SysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author changxizhao
 * @Date 2020/7/5 17:24
 * @Description
 */
@RestController
@RequestMapping("/sys/dept")
public class SysDeptController {

    @Autowired
    private SysDeptService deptService;

    @PostMapping
    public Result save(DeptBO deptBO) throws IllegalAccessException, InstantiationException {
        deptService.save(deptBO);
        return Result.success();
    }

}
