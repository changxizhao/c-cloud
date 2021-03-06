package com.chang.ccloud.dao;

import com.chang.ccloud.entities.vo.UserTableVO;
import com.chang.ccloud.model.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    SysUser selectUserByUsername(@Param("username") String username);

    List<UserTableVO> selectUserTable(UserTableVO userTableVO);

    List<SysUser> selectUserListByUserIdList(List<Long> idList);

    int selectCountByDeptId(@Param("deptId") Long deptId);
}