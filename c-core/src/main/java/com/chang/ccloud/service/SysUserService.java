package com.chang.ccloud.service;


import com.chang.ccloud.entities.vo.UserRequestVO;
import com.chang.ccloud.model.SysUser;

/**
 * @Author changxizhao
 * @Date 2020/7/3 14:05
 * @Description
 */
public interface SysUserService {

    void addUser(UserRequestVO userRequestVO);

    void updateUser(UserRequestVO userRequestVO);

    SysUser findUserByUsername(Long id);
}
