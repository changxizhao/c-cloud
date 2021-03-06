package com.chang.ccloud.dao;

import com.chang.ccloud.entities.dto.SysMenuDTO;
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

    List<SysMenuDTO> selectAllMenu(SysMenu sysMenu);

    int selectCountMenuByParentId(@Param("parentId") long parentId);

    List<SysMenuDTO> selectMenuByIdList(@Param("idList") List<Long> idList);

}