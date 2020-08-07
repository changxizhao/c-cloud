package com.chang.ccloud.entities.vo;

import com.chang.ccloud.entities.BaseTableInfo;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author changxizhao
 * @Date 2020/8/7 16:51
 * @Description
 */
public class RoleVO extends BaseTableInfo {

    private Long id;

    @NotBlank(message = "名称不能为空")
    private String name;

    @NotNull(message = "角色类型不能为空")
    @Min(value = 1, message = "类型不合法")
    @Max(value = 2, message = "类型不合法")
    private Integer type;

    @NotNull(message = "角色状态不能为空")
    @Min(value = 0, message = "状态不合法")
    @Max(value = 1, message = "状态不合法")
    private Integer status;

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
