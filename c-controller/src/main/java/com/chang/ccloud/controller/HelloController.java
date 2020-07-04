package com.chang.ccloud.controller;

import com.chang.ccloud.common.Result;
import com.chang.ccloud.exception.ParamsException;
import com.chang.ccloud.model.SysUser;
import com.chang.ccloud.model.vo.TestVO;
import com.chang.ccloud.service.SysUserService;
import com.chang.ccloud.validator.BeanValidator;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 测试controller
 * @Author changxizhao
 * @Date 2020/7/3 14:04
 * @Description
 */
@RestController
public class HelloController {

    private static Logger logger = LoggerFactory.getLogger(HelloController.class);

    @Autowired
    private SysUserService sysUserService;

    @ResponseBody
    @GetMapping("/hello/{id}")
    public Result hello(@PathVariable("id") Long id) {
        SysUser user = sysUserService.findUserByUsername(id);
        return Result.success();
    }

    @ResponseBody
    @RequestMapping("/validate")
    public Result validate(TestVO testVO) throws ParamsException {
        logger.info("validate");
        BeanValidator.checkObject(testVO);
//        Map<String, String> result = BeanValidator.validateObject(testVO);
//        if(MapUtils.isEmpty(result)){
//            for (Map.Entry<String, String> entry: result.entrySet()) {
//                logger.info("{}->{}",entry.getKey(),entry.getValue());
//            }
//        }
        return Result.success("success");
    }

}
