package com.chang.ccloud.model.vo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author changxizhao
 * @Date 2020/7/4 16:18
 * @Description
 */
public class TestVO {

    @NotBlank
    private String msg;

    @NotNull
    private Integer id;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
