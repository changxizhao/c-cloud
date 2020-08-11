package com.chang.ccloud.entities.vo;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author changxizhao
 * @Date 2020/8/11 8:55
 * @Description
 */
public class RoleUserVO {

    @NotNull(message = "角色ID不能为空")
    private Long roleId;

    private List<Long> userIdList;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public List<Long> getUserIdList() {
        return userIdList;
    }

    public void setUserIdList(List<Long> userIdList) {
        this.userIdList = userIdList;
    }
}
