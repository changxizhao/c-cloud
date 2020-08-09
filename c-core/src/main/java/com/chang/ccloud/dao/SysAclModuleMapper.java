package com.chang.ccloud.dao;


import com.chang.ccloud.entities.dto.SysMenuDTO;
import com.chang.ccloud.model.SysAclModule;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysAclModuleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysAclModule record);

    int insertSelective(SysAclModule record);

    SysAclModule selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysAclModule record);

    int updateByPrimaryKey(SysAclModule record);

    int checkAclModuleExist(@Param("parentId") Long parentId, @Param("name") String name, @Param("id") Long id);

    List<SysAclModule> selectChildAclModuleByLevel(@Param("level") String level);

    void updateAclModuleLevel(SysAclModule sysAclModule);

    List<SysMenuDTO> selectAllAclModule();
}