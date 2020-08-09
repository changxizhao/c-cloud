package com.chang.ccloud.service.impl;

import com.chang.ccloud.common.utils.DateUtil;
import com.chang.ccloud.common.utils.IpUtil;
import com.chang.ccloud.common.utils.Util;
import com.chang.ccloud.dao.SysRoleAclMapper;
import com.chang.ccloud.entities.vo.RoleAclVO;
import com.chang.ccloud.holder.RequestHolder;
import com.chang.ccloud.model.SysRoleAcl;
import com.chang.ccloud.service.SysRoleAclService;
import com.chang.ccloud.validator.BeanValidator;
import com.google.common.collect.Sets;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author changxizhao
 * @Date 2020/8/9 10:29
 * @Description
 */
@Service
public class SysRoleAclServiceImpl implements SysRoleAclService {

    @Autowired
    private SysRoleAclMapper roleAclMapper;

    @Override
    public List<Long> selectMenuIdListByRoleId(long roleId) {

        return roleAclMapper.selectMenuIdListByRoleId(roleId);

    }

    @Override
    public void changeRoleAcl(RoleAclVO roleAclVO) {
        BeanValidator.checkObject(roleAclVO);
        List<Long> originAclIdList = roleAclMapper.selectMenuIdListByRoleId(roleAclVO.getRoleId());
        if(Util.equalsTo(originAclIdList.size(), roleAclVO.getAclIdList().size())) {
            Set<Long> originAclIdSet = Sets.newHashSet(originAclIdList);
            Set<Long> newAclIdSet = Sets.newHashSet(roleAclVO.getAclIdList());
            originAclIdSet.removeAll(newAclIdSet);
            if(CollectionUtils.isEmpty(originAclIdSet)) {
                return;
            }
        }
        doChangeRoleAcl(roleAclVO);
    }
    
    @Transactional
    void doChangeRoleAcl(RoleAclVO roleAclVO) {
        roleAclMapper.deleteRoleAclListByRoleID(roleAclVO.getRoleId());
        List<Long> list = roleAclVO.getAclIdList();
        for (Long aclId: list) {
            SysRoleAcl roleAcl = new SysRoleAcl();
            roleAcl.setRoleId(roleAclVO.getRoleId());
            roleAcl.setAclId(aclId);
            roleAcl.setOperator(RequestHolder.getCurrentUser().getUsername());
            roleAcl.setOperateIp(IpUtil.getUserIP(RequestHolder.getCurrentRequest()));
            roleAcl.setOperateTime(DateUtil.getNowDate());
            roleAclMapper.insertSelective(roleAcl);
        }
    }
}
