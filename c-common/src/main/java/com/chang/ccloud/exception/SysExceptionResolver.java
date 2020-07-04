package com.chang.ccloud.exception;

import com.chang.ccloud.common.Result;
import com.chang.ccloud.common.constants.HttpRequestStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 全局异常捕获
 * @throws
 * @Author changxizhao
 * @Date 2020/7/4 16:44
 */

@ControllerAdvice
public class SysExceptionResolver {

    private final Logger logger = LoggerFactory.getLogger(SysExceptionResolver.class);

    @ExceptionHandler(PermissionException.class)
    public Result permissionExceptionHandler(PermissionException e) {
        return Result.fail(HttpRequestStatus.UNAUTHORIZATION.getCode(),"无权限");
    }

    @ExceptionHandler(ParamsException.class)
    public Result paramsExceptionHandler(ParamsException e) {
        logger.info("ParamsException->{}",e.getMessage());
        return Result.fail(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Result exceptionHandler(Exception e) {

//        String url = request.getRequestURL().toString();
//        logger.info("请求地址：{}", url);

        return Result.fail("system error");

    }
}
