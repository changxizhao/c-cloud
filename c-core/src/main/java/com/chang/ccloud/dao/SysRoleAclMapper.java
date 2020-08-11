package com.chang.ccloud.dao;


import com.chang.ccloud.model.SysRoleAcl;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysRoleAclMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysRoleAcl record);

    int insertSelective(SysRoleAcl record);

    SysRoleAcl selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysRoleAcl record);

    int updateByPrimaryKey(SysRoleAcl record);

    List<Long> selectMenuIdListByRoleId(@Param("roleId") long roleId);

    List<Long> selectMenuIdListByRoleIdList(@Param("roleIdList") List<Long> roleIdList);

    void deleteRoleAclListByRoleID(@Param("roleId") Long roleId);

    int selectCountRoleAclByAclId(@Param("aclId") long aclId);
}