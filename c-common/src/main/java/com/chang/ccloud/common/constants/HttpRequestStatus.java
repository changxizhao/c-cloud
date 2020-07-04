package com.chang.ccloud.common.constants;
/**
 * 状态码
 * @Author changxizhao
 * @Date 2020/7/4 19:59
 */
public enum HttpRequestStatus {


    SUCCESS(200),

    FAIL(000),

    UNAUTHORIZATION(403),

    ERROR(500);


    private final Integer code;

    HttpRequestStatus(int code) {
        this.code = code;
    }

    public Integer getCode(){
        return code;
    }
}
