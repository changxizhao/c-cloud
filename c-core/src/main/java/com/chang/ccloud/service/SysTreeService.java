package com.chang.ccloud.service;

import com.chang.ccloud.entities.dto.DeptLevelDTO;

import java.util.List;

/**
 * @Author changxizhao
 * @Date 2020/7/6 9:18
 * @Description
 */
public interface SysTreeService {

    public List<DeptLevelDTO> deptTree();

}
