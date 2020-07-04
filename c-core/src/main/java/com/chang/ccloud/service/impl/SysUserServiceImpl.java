package com.chang.ccloud.service.impl;

import com.chang.ccloud.common.util.JsonConvertUtil;
import com.chang.ccloud.dao.SysUserMapper;
import com.chang.ccloud.model.SysUser;
import com.chang.ccloud.service.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author changxizhao
 * @Date 2020/7/3 14:06
 * @Description
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    Logger logger = LoggerFactory.getLogger(SysUserServiceImpl.class);

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public SysUser findUserByUsername(Long id) {
        SysUser user = sysUserMapper.selectByPrimaryKey(id);
        String s = JsonConvertUtil.obj2String(user);
        logger.info("用户对象 -> {}", s);
        return user;
    }
}
