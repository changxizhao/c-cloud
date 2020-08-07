package com.chang.ccloud.dao;


import com.chang.ccloud.model.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysRoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysRole record);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);

    Integer checkRoleExist(@Param("name") String name, @Param("type") Integer type);

    List<SysRole> selectAllRoles(@Param("name") String name);
}