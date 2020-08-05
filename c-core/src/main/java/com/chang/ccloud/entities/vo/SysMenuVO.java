package com.chang.ccloud.entities.vo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author changxizhao
 * @Date 2020/8/5 12:27
 * @Description
 */
public class SysMenuVO {

    private Long id;

    @NotBlank(message = "名称不能为空")
    private String name;

    @NotBlank(message = "权限码不能为空")
    private String code;

    @NotNull(message = "权限类型不能为空")
    private Integer type;

    private String url;

    private Long parentId;

    @NotNull(message = "显示顺序不能为空")
    private Integer seq;

    @NotNull(message = "权限状态不能为空")
    private Integer status;

    private Integer visible;

    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getVisible() {
        return visible;
    }

    public void setVisible(Integer visible) {
        this.visible = visible;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
