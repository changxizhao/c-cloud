package com.chang.ccloud.service;

import com.chang.ccloud.model.SysUser;

/**
 * @Author changxizhao
 * @Date 2020/7/16 12:19
 * @Description
 */
public interface UserService {

    SysUser login(String username, String password);

}
