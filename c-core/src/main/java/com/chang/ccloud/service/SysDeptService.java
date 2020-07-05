package com.chang.ccloud.service;

import com.chang.ccloud.common.Result;
import com.chang.ccloud.entities.bo.DeptBO;

/**
 * @Author changxizhao
 * @Date 2020/7/5 17:27
 * @Description
 */
public interface SysDeptService {

    void save(DeptBO deptBO) throws InstantiationException, IllegalAccessException;

    boolean checkDeptExist(Long parentId, String deptName, Long deptId);

    String getDeptLevel(Long id);
}
