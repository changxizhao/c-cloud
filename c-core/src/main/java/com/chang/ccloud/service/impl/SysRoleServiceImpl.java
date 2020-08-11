package com.chang.ccloud.service.impl;

import com.chang.ccloud.common.utils.DateUtil;
import com.chang.ccloud.common.utils.IpUtil;
import com.chang.ccloud.dao.SysRoleMapper;
import com.chang.ccloud.entities.vo.RoleVO;
import com.chang.ccloud.entities.vo.UserVO;
import com.chang.ccloud.exception.ParamsException;
import com.chang.ccloud.holder.RequestHolder;
import com.chang.ccloud.model.SysRole;
import com.chang.ccloud.service.SysRoleAclService;
import com.chang.ccloud.service.SysRoleService;
import com.chang.ccloud.service.SysRoleUserService;
import com.chang.ccloud.validator.BeanValidator;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author changxizhao
 * @Date 2020/8/7 16:49
 * @Description
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleMapper roleMapper;

    @Autowired
    private SysRoleAclService roleAclService;

    @Autowired
    private SysRoleUserService roleUserService;


    @Override
    public void add(RoleVO roleVO) {
        BeanValidator.checkObject(roleVO);
        if(checkRoleExist(roleVO)) {
            throw new ParamsException("角色名称已经存在");
        }

        SysRole role = new SysRole();
        BeanUtils.copyProperties(roleVO, role);
        role.setOperator(RequestHolder.getCurrentUser().getUsername());
        role.setOperateIp(IpUtil.getUserIP(RequestHolder.getCurrentRequest()));
        role.setOperateTime(DateUtil.getNowDate());
        roleMapper.insertSelective(role);
    }

    @Override
    public void update(RoleVO roleVO) {
        BeanValidator.checkObject(roleVO);
        SysRole before = roleMapper.selectByPrimaryKey(roleVO.getId());
        Preconditions.checkNotNull(before, "角色不存在");
        if(!roleVO.getName().equals(before.getName()) || !roleVO.getType().equals(before.getType())) {
            if (checkRoleExist(roleVO)) {
                throw new ParamsException("角色名称已经存在");
            }
        }
        SysRole after = new SysRole();
        BeanUtils.copyProperties(roleVO, after);
        after.setOperator(RequestHolder.getCurrentUser().getUsername());
        after.setOperateIp(IpUtil.getUserIP(RequestHolder.getCurrentRequest()));
        after.setOperateTime(DateUtil.getNowDate());
        roleMapper.updateByPrimaryKeySelective(after);
    }

    @Override
    public List<RoleVO> selectAllRoles(RoleVO roleVO) {
        List<SysRole> sysRoles = roleMapper.selectAllRoles(roleVO.getName());
        if(CollectionUtils.isEmpty(sysRoles)) {
            return Lists.newArrayList();
        }
        List<RoleVO> voList = new ArrayList<>();
        for (SysRole role : sysRoles) {
            RoleVO vo = new RoleVO();
            BeanUtils.copyProperties(role, vo);
            voList.add(vo);
        }
        return voList;
    }

    @Override
    public void deleteRoleById(Long id) {
        SysRole role = roleMapper.selectByPrimaryKey(id);
        Preconditions.checkNotNull(role, "角色不存在");
        List<UserVO> userVOList = roleUserService.selectUserIdListByRoleId(id);
        if(CollectionUtils.isNotEmpty(userVOList)) {
            throw new ParamsException("角色已分配用户。不允许删除");
        }
        List<Long> menuIdList = roleAclService.selectMenuIdListByRoleId(id);
        if(CollectionUtils.isNotEmpty(menuIdList)) {
            throw new ParamsException("角色已授权，不允许删除");
        }
        roleMapper.deleteByPrimaryKey(id);
    }

    private boolean checkRoleExist(RoleVO roleVO) {
        return roleMapper.checkRoleExist(roleVO.getName(), roleVO.getType()) > 0;
    }
}
