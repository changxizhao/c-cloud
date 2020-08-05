package com.chang.ccloud.entities.vo;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author changxizhao
 * @Date 2020/8/3 13:57
 * @Description
 */
public class AclModuleVO {

    private Long id;

    @NotBlank(message = "权限模块名称不能为空")
    @Length(min = 2, max = 64, message = "权限模块名称长度在2~64个字之间")
    private String name;

    private Long parentId = 0L;

    @NotNull(message = "权限模块展示顺序不能为空")
    private Integer seq;

    @NotNull(message = "权限模块状态不能为空")
    @Min(value = 0, message = "权限模块状态错误")
    @Max(value = 1, message = "权限模块状态错误")
    private Integer status;

    @Length(max = 100, message = "权限模块备注需要在100个字以内")
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
