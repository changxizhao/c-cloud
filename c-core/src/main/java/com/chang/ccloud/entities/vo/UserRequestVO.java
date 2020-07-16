package com.chang.ccloud.entities.vo;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


/**
 * @Author changxizhao
 * @Date 2020/7/16 8:43
 * @Description
 */
public class UserRequestVO {

    private Long id;

    @NotBlank(message = "用户名不能为空")
    @Length(max = 20, message = "用户名长度不能超过20个字符")
    private String username;

    private String nickname;

    private Integer sex;

    @NotBlank(message = "联系方式不能为空")
    @Length(max = 13, message = "联系方式不能超过13个字符")
    private String telephone;

    @NotBlank(message = "邮箱不能为空")
    @Length(max = 100, message = "邮箱不能超过100个字符")
    private String mail;

    @NotNull(message = "所属部门不能为空")
    private Long deptId;

    @NotNull(message = "用户状态不能为空")
    @Min(value = 0, message = "用户状态不正确")
    @Max(value = 2, message = "用户状态不正确")
    private Integer status;

    @Length(max = 255, message = "备注长度不能超过255个字符")
    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
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
