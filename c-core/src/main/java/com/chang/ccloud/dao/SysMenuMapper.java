package com.chang.ccloud.dao;

import com.chang.ccloud.model.SysMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysMenuMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysMenu record);

    int insertSelective(SysMenu record);

    SysMenu selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysMenu record);

    int updateByPrimaryKey(SysMenu record);

    Integer checkSysMenuExist(@Param("parentId") Long parentId, @Param("name") String name);

    List<SysMenu> selectMenuTreegrid(SysMenu sysMenu);
}