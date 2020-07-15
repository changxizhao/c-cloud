package com.chang.ccloud.service;

import com.chang.ccloud.entities.bo.DeptBO;
import com.chang.ccloud.entities.vo.DeptTableVO;
import com.chang.ccloud.model.SysDept;

import java.util.List;

/**
 * @Author changxizhao
 * @Date 2020/7/5 17:27
 * @Description
 */
public interface SysDeptService {

    void addDept(DeptBO deptBO);

    void updateDept(DeptBO deptBO);

    boolean checkDeptExist(Long parentId, String deptName, Long deptId);

    String getDeptLevel(Long id);

    List<DeptTableVO> selectDeptTable(Long id, String level);

    SysDept selectDeptById(Long id);
}
