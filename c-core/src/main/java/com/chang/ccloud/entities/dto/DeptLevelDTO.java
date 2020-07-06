package com.chang.ccloud.entities.dto;

import com.chang.ccloud.model.SysDept;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * @Author changxizhao
 * @Date 2020/7/6 9:12
 * @Description
 */
public class DeptLevelDTO extends SysDept {

    private List<DeptLevelDTO> deptList = Lists.newArrayList();

    public static DeptLevelDTO adept(SysDept dept) {
        DeptLevelDTO dto = new DeptLevelDTO();
        BeanUtils.copyProperties(dept, dto);
        return dto;
    }

    public List<DeptLevelDTO> getDeptList() {
        return deptList;
    }

    public void setDeptList(List<DeptLevelDTO> deptList) {
        this.deptList = deptList;
    }

}
