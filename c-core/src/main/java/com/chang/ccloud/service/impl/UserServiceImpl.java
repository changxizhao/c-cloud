package com.chang.ccloud.service.impl;

import com.chang.ccloud.common.utils.MD5Util;
import com.chang.ccloud.dao.SysUserMapper;
import com.chang.ccloud.exception.LoginException;
import com.chang.ccloud.model.SysUser;
import com.chang.ccloud.service.UserService;
import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author changxizhao
 * @Date 2020/7/16 12:27
 * @Description
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private SysUserMapper userMapper;

    @Override
    public void login(String username, String password) {
        if(StringUtils.isBlank(username)) {
            throw new LoginException("用户名不能为空");
        }
        if(StringUtils.isBlank(password)) {
            throw new LoginException("密码不能为空");
        }
        SysUser sysUser = userMapper.selectUserByUsername(username);
        Preconditions.checkNotNull(sysUser, "此用户不存在");
        if(!MD5Util.verify(password, sysUser.getPassword())) {
            throw new LoginException("用户名或密码错误");
        }
        if(sysUser.getStatus() != 1) {
            throw new LoginException("此账号已冻结，请联系系统管理员");
        }
    }
}
