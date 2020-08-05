package com.chang.ccloud.service;

import com.chang.ccloud.entities.vo.AclModuleVO;

/**
 * @Author changxizhao
 * @Date 2020/8/3 15:50
 * @Description
 */
public interface SysAclModuleService {

    void addAclModule(AclModuleVO aclModuleVO);

    void updateAclModule(AclModuleVO aclModuleVO);

    boolean checkAclModuleExist(Long parentId, String deptName, Long deptId);

    String getAclModuleLevel(Long id);
}
