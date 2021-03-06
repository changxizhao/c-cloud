package com.chang.ccloud.service;

import com.chang.ccloud.entities.dto.SysMenuDTO;
import com.chang.ccloud.entities.dto.DeptLevelDTO;
import com.chang.ccloud.entities.dto.TreeViewDTO;
import com.chang.ccloud.model.SysMenu;

import java.util.List;

/**
 * @Author changxizhao
 * @Date 2020/7/6 9:18
 * @Description
 */
public interface SysTreeService {

    List<DeptLevelDTO> deptTree();

    List<TreeViewDTO> deptToTreeView(List<DeptLevelDTO> list);

    List<SysMenuDTO> menuTree();

    List<SysMenuDTO> menuTree(SysMenu sysMenu);

    List<SysMenuDTO> menuTree(List<Long> idList);

    List<TreeViewDTO> menuToTreeView(List<SysMenuDTO> list, long roleId);

}
