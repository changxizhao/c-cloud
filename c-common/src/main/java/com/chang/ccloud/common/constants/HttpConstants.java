package com.chang.ccloud.common.constants;

/**
 * @Author changxizhao
 * @Date 2020/8/1 12:20
 * @Description
 */
public enum HttpConstants {

    SET_CHARSET("charset=utf-8"),

    UTF8("utf-8"),

    HEADER_SEPARATOR(";"),

    FORM_HEADER("application/x-www-form-urlencoded"),

    JSON_HEADER("application/json"),

    JSON_CONTENT_TYPE("text/json");


    private final String constants;

    HttpConstants(String constants) {
        this.constants = constants;
    }

    public String getValue(){
        return constants;
    }

}
