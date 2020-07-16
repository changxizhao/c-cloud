package com.chang.ccloud.service;

import com.chang.ccloud.entities.vo.DeptRequestVO;
import com.chang.ccloud.entities.vo.DeptTableVO;
import com.chang.ccloud.model.SysDept;

import java.util.List;

/**
 * @Author changxizhao
 * @Date 2020/7/5 17:27
 * @Description
 */
public interface SysDeptService {

    void addDept(DeptRequestVO deptRequestVO);

    void updateDept(DeptRequestVO deptRequestVO);

    boolean checkDeptExist(Long parentId, String deptName, Long deptId);

    String getDeptLevel(Long id);

    List<DeptTableVO> selectDeptTable(DeptTableVO deptTableVO);

    SysDept selectDeptById(Long id);
}
