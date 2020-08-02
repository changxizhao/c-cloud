package com.chang.ccloud.service.impl;

import com.chang.ccloud.common.utils.*;
import com.chang.ccloud.dao.SysUserMapper;
import com.chang.ccloud.entities.vo.UserRequestVO;
import com.chang.ccloud.entities.vo.UserTableVO;
import com.chang.ccloud.exception.ParamsException;
import com.chang.ccloud.holder.RequestHolder;
import com.chang.ccloud.model.SysUser;
import com.chang.ccloud.service.SysUserService;
import com.chang.ccloud.validator.BeanValidator;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public void addUser(UserRequestVO userRequestVO) {
        BeanValidator.checkObject(userRequestVO);
        if(checkUser(userRequestVO)) {
            throw new ParamsException("该用户已存在");
        }
        String password = MD5Util.encode( "123456");
//        String password = PasswordUtil.randomPwd();
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(userRequestVO, sysUser);
        sysUser.setPassword(password);
        sysUser.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysUser.setOperateIp(IpUtil.getUserIP(RequestHolder.getCurrentRequest()));
        sysUser.setOperateTime(DateUtil.getNowDate());
        // TODO 发送邮件通知用户
        sysUserMapper.insertSelective(sysUser);
    }

    @Override
    public void updateUser(UserRequestVO userRequestVO) {
        BeanValidator.checkObject(userRequestVO);

        SysUser before = sysUserMapper.selectByPrimaryKey(userRequestVO.getId());
        Preconditions.checkNotNull(before, "不存在此用户");

        if(!before.getUsername().equals(userRequestVO.getUsername())) {
            if(checkUser(userRequestVO)) {
                throw new ParamsException("该用户已存在");
            }
        }

        SysUser after = new SysUser();
        BeanUtils.copyProperties(userRequestVO, after);
        after.setId(userRequestVO.getId());
        after.setOperator(RequestHolder.getCurrentUser().getUsername());
        after.setOperateIp(IpUtil.getUserIP(RequestHolder.getCurrentRequest()));
        after.setOperateTime(DateUtil.getNowDate());
        sysUserMapper.updateByPrimaryKeySelective(after);
    }

    @Override
    public SysUser findUserByUsername(Long id) {
        SysUser user = sysUserMapper.selectByPrimaryKey(id);
        String s = JsonConvertUtil.obj2String(user);
        logger.info("用户对象 -> {}", s);
        return user;
    }

    public boolean checkUser(UserRequestVO userRequestVO) {
        SysUser user = sysUserMapper.selectUserByUsername(userRequestVO.getUsername());
        return user != null ? true : false;
    }

    @Override
    public List<UserTableVO> selectUserTable(UserTableVO userTableVO) {
        return sysUserMapper.selectUserTable(userTableVO);
    }

}
