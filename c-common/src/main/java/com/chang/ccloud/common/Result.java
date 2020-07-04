package com.chang.ccloud.common;

import com.chang.ccloud.common.constants.HttpRequestStatus;

/**
 * 返回结果包装类
 * @Author changxizhao
 * @Date 2020/7/3 16:59
 * @Description
 */
public class Result {

    private Integer code;

    private String msg;

    private Object data;

    public Result() {

    }

    public Result(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Result(Integer code) {
        this.code = code;
    }

    public static Result success(String msg, Object data) {
        Result result = new Result(HttpRequestStatus.SUCCESS.getCode());
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    public static Result success(String msg) {
        return success(msg, null);
    }

    public static Result success(Object data) {
        return success("",data);
    }

    public static Result success() {
        return success("", null);
    }

    public static Result fail(Integer code, String msg, Object data) {
        return new Result(code,msg,data);
    }

    public static Result fail(String msg) {
        return fail(HttpRequestStatus.FAIL.getCode(),msg,null);
    }

    public static Result fail(Object data) {
        return fail(HttpRequestStatus.FAIL.getCode(),"",data);
    }

    public static Result fail() {
        return fail(HttpRequestStatus.FAIL.getCode(),"",null);
    }

    public static Result fail(Integer code) {
        return fail(code,"",null);
    }

    public static Result fail(Integer code, String msg) {
        return fail(code,msg,null);
    }

    public static Result fail(Integer code,Object data) {
        return fail(code,"",data);
    }


    public static Result error(String msg, Object data) {
        Result result = new Result(HttpRequestStatus.ERROR.getCode());
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    public static Result error(String msg) {
        return error(msg,null);
    }

    public static Result error(Object data) {
        return error("",data);
    }

    public static Result error() {
        return error("",null);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
