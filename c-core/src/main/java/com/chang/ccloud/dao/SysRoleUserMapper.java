package com.chang.ccloud.dao;


import com.chang.ccloud.model.SysRoleUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysRoleUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysRoleUser record);

    int insertSelective(SysRoleUser record);

    SysRoleUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysRoleUser record);

    int updateByPrimaryKey(SysRoleUser record);

    void deleteRoleUserListByRoleID(@Param("roleId") Long roleId);

    List<Long> selectUserIdListByRoleId(@Param("roleId") Long roleId);
}