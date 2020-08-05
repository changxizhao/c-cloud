package com.chang.ccloud.service;

import com.chang.ccloud.entities.dto.AclModuleLevelDTO;
import com.chang.ccloud.entities.dto.DeptLevelDTO;
import com.chang.ccloud.entities.dto.DeptTreeViewDTO;
import com.chang.ccloud.model.SysDept;

import java.util.List;

/**
 * @Author changxizhao
 * @Date 2020/7/6 9:18
 * @Description
 */
public interface SysTreeService {

    List<DeptLevelDTO> deptTree();

    List<DeptTreeViewDTO> toTreeView(List<DeptLevelDTO> list);

    public List<AclModuleLevelDTO> aclModuleTree();

}
