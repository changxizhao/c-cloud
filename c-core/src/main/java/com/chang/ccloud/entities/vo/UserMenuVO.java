package com.chang.ccloud.entities.vo;

import io.swagger.models.auth.In;

import java.util.List;

/**
 * @Author changxizhao
 * @Date 2020/8/11 19:47
 * @Description
 */
public class UserMenuVO {
    
    private Long menuId;

    private Long parentId;

    private String parentName;

    private String name;

    private String url;

    private String perms;

    private Integer type;

    private String icon;

    private Integer orderNum;

    private String open;

    private List<UserMenuVO> list;

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPerms() {
        return perms;
    }

    public void setPerms(String perms) {
        this.perms = perms;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public List<UserMenuVO> getList() {
        return list;
    }

    public void setList(List<UserMenuVO> list) {
        this.list = list;
    }
}
