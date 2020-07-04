package com.chang.ccloud.service;


import com.chang.ccloud.model.SysUser;

/**
 * @Author changxizhao
 * @Date 2020/7/3 14:05
 * @Description
 */
public interface SysUserService {

    SysUser findUserByUsername(Long id);
}
