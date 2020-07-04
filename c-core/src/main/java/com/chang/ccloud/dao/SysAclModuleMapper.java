package com.chang.ccloud.dao;


import com.chang.ccloud.model.SysAclModule;

public interface SysAclModuleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysAclModule record);

    int insertSelective(SysAclModule record);

    SysAclModule selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysAclModule record);

    int updateByPrimaryKey(SysAclModule record);
}