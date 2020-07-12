package com.chang.ccloud.common;

import com.chang.ccloud.common.constants.HttpRequestStatus;
import com.github.pagehelper.PageInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author changxizhao
 * @Date 2020/7/12 21:34
 * @Description
 */
public class TableInfo {

    private Integer code;

    private String msg;

    private Integer total;

    private List<?> rows = new ArrayList<>();

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<Object> rows) {
        this.rows = rows;
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

    public TableInfo(Integer code, String msg, Integer total, List<?> rows) {
        this.code = code;
        this.msg = msg;
        this.total = total;
        this.rows = rows;
    }

    public TableInfo() {
    }

    public static TableInfo tableInfo(PageInfo<?> pageInfo) {
        Integer total = (int)pageInfo.getTotal();
        List<?> list = pageInfo.getList();
        return new TableInfo(HttpRequestStatus.SUCCESS.getCode(), "success", total, list);
    }
}
