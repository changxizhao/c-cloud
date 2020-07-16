package com.chang.ccloud.dao;


import com.chang.ccloud.entities.dto.DeptLevelDTO;
import com.chang.ccloud.entities.vo.DeptTableVO;
import com.chang.ccloud.model.SysDept;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysDeptMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysDept record);

    int insertSelective(SysDept record);

    SysDept selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysDept record);

    int updateByPrimaryKey(SysDept record);

//    List<SysDept> selectAllDepts();
    List<DeptLevelDTO> selectAllDepts();

    List<SysDept> selectChildDeptByLevel(@Param("level") String level);

    void updateDeptLevel(SysDept sysDept);

    int checkDeptExist(@Param("parentId") Long parentId, @Param("name") String name, @Param("id") Long id);

    List<DeptTableVO> selectDeptTable(DeptTableVO deptTableVO);

//    List<SysDept> selectChildDeptByLevel(@Param("id") Long id, @Param("level") String level);
}