package com.chang.ccloud.service;

import com.chang.ccloud.entities.vo.RoleVO;

import java.util.List;

/**
 * @Author changxizhao
 * @Date 2020/8/7 16:48
 * @Description
 */
public interface SysRoleService {

    void add(RoleVO roleVO);

    void update(RoleVO roleVO);

    List<RoleVO> selectAllRoles(RoleVO roleVO);

    void deleteRoleById(Long id);
}
