package com.chang.ccloud.service.impl;

import com.chang.ccloud.common.utils.DateUtil;
import com.chang.ccloud.common.utils.IpUtil;
import com.chang.ccloud.common.utils.Util;
import com.chang.ccloud.dao.SysRoleUserMapper;
import com.chang.ccloud.dao.SysUserMapper;
import com.chang.ccloud.entities.vo.RoleUserVO;
import com.chang.ccloud.entities.vo.UserTableVO;
import com.chang.ccloud.entities.vo.UserVO;
import com.chang.ccloud.holder.RequestHolder;
import com.chang.ccloud.model.SysRoleAcl;
import com.chang.ccloud.model.SysRoleUser;
import com.chang.ccloud.model.SysUser;
import com.chang.ccloud.service.SysRoleUserService;
import com.chang.ccloud.validator.BeanValidator;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @Author changxizhao
 * @Date 2020/8/11 8:59
 * @Description
 */
@Service
public class SysRoleUserServiceImpl implements SysRoleUserService {

    @Autowired
    private SysRoleUserMapper roleUserMapper;

    @Autowired
    private SysUserMapper userMapper;

    @Override
    public List<UserVO> selectUserIdListByRoleId(long roleId) {
        List<Long> userIdList = roleUserMapper.selectUserIdListByRoleId(roleId);
        if(CollectionUtils.isEmpty(userIdList)) {
            return Lists.newArrayList();
        }
        List<UserVO> userVOList = new ArrayList<>();
        List<SysUser> users = userMapper.selectUserListByUserIdList(userIdList);
        for (SysUser user : users) {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user, userVO);
            userVOList.add(userVO);
        }
        return userVOList;
    }

    @Override
    public void changeRoleUser(RoleUserVO roleUserVO) {
        BeanValidator.checkObject(roleUserVO);
        List<Long> originUserIdList = roleUserMapper.selectUserIdListByRoleId(roleUserVO.getRoleId());
        if(Util.equalsTo(originUserIdList.size(), roleUserVO.getUserIdList().size())) {
            Set<Long> originAclIdSet = Sets.newHashSet(originUserIdList);
            Set<Long> newAclIdSet = Sets.newHashSet(roleUserVO.getUserIdList());
            originAclIdSet.removeAll(newAclIdSet);
            if(CollectionUtils.isEmpty(originAclIdSet)) {
                return;
            }
        }
        doChangeRoleUser(roleUserVO);
    }

    @Override
    public void deleteRoleByUserId(long id) {
        roleUserMapper.deleteRoleUserByUserId(id);
    }

    @Transactional
    public void doChangeRoleUser(RoleUserVO roleUserVO) {
        roleUserMapper.deleteRoleUserListByRoleID(roleUserVO.getRoleId());
        List<Long> list = roleUserVO.getUserIdList();
        for (Long userId: list) {
            SysRoleUser roleUser = new SysRoleUser();
            roleUser.setRoleId(roleUserVO.getRoleId());
            roleUser.setUserId(userId);
            roleUser.setOperator(RequestHolder.getCurrentUser().getUsername());
            roleUser.setOperateIp(IpUtil.getUserIP(RequestHolder.getCurrentRequest()));
            roleUser.setOperateTime(DateUtil.getNowDate());
            roleUserMapper.insertSelective(roleUser);
        }
    }
}
