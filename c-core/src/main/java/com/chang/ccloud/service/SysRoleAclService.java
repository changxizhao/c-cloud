package com.chang.ccloud.service;


import com.chang.ccloud.entities.vo.RoleAclVO;

import java.util.List;

/**
 * @Author changxizhao
 * @Date 2020/8/9 10:28
 * @Description
 */
public interface SysRoleAclService {

    List<Long> selectMenuIdListByRoleId(long roleId);

    void changeRoleAcl(RoleAclVO roleAclVO);

}
