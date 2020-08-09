package com.chang.ccloud.entities.vo;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author changxizhao
 * @Date 2020/8/9 11:23
 * @Description
 */
public class RoleAclVO {

    @NotNull(message = "角色ID不能为空")
    private Long roleId;

    @NotNull(message = "菜单/权限ID不能为空")
    private List<Long> aclIdList;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public List<Long> getAclIdList() {
        return aclIdList;
    }

    public void setAclIdList(List<Long> aclIdList) {
        this.aclIdList = aclIdList;
    }
}
