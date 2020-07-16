package com.chang.ccloud.entities;

/**
 * @Author changxizhao
 * @Date 2020/7/16 17:40
 * @Description
 */
public class BaseTableInfo {

    private Integer pageNumber;

    private Integer pageSize;

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
