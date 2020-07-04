package com.chang.ccloud.exception;

/**
 * 自定义参数校验异常
 * @Author changxizhao
 * @Date 2020/7/4 16:39
 * @Description
 */
public class ParamsException extends RuntimeException {

    public ParamsException() {
        super();
    }

    public ParamsException(String message) {
        super(message);
    }

    public ParamsException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParamsException(Throwable cause) {
        super(cause);
    }

    protected ParamsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
