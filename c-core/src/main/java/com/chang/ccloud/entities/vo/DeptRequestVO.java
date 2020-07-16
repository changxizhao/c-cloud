package com.chang.ccloud.entities.vo;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 部门参数
 * @Author changxizhao
 * @Date 2020/7/5 17:18
 * @Description
 */
public class DeptRequestVO {

    private Long id;

    @NotBlank(message = "部门名称不能为空")
    private String name;

    private Long parentId = 0L;

    @NotNull(message = "展示顺序不能为空")
    private Integer seq;

    @Length(max = 150, message = "备注长度不能超过150个字")
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public DeptRequestVO(Long id, @NotBlank(message = "部门名称不能为空") String name, Long parentId, @NotNull(message = "展示顺序不能为空") Integer seq, @Length(max = 150, message = "备注长度不能超过150个字") String remark) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.seq = seq;
        this.remark = remark;
    }

    public DeptRequestVO() {
    }
}
