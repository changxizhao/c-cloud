package com.chang.ccloud.service.impl;

import com.chang.ccloud.common.utils.DateUtil;
import com.chang.ccloud.common.utils.IpUtil;
import com.chang.ccloud.common.utils.LevelUtil;
import com.chang.ccloud.dao.SysAclModuleMapper;
import com.chang.ccloud.entities.vo.AclModuleVO;
import com.chang.ccloud.exception.ParamsException;
import com.chang.ccloud.holder.RequestHolder;
import com.chang.ccloud.model.SysAclModule;
import com.chang.ccloud.model.SysDept;
import com.chang.ccloud.service.SysAclModuleService;
import com.chang.ccloud.validator.BeanValidator;
import com.google.common.base.Preconditions;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author changxizhao
 * @Date 2020/8/3 15:50
 * @Description
 */
@Service
public class SysAclModuleServiceImpl implements SysAclModuleService {


    @Autowired
    private SysAclModuleMapper sysAclModuleMapper;

    @Autowired
    private SysAclModuleService sysAclModuleService;

    @Override
    public void addAclModule(AclModuleVO aclModuleVO) {
        BeanValidator.checkObject(aclModuleVO);
        if(sysAclModuleService.checkAclModuleExist(aclModuleVO.getParentId(), aclModuleVO.getName(), aclModuleVO.getId())) {
            throw new ParamsException("权限模块已存在");
        }
        if(aclModuleVO.getParentId() == null) {
            aclModuleVO.setParentId(0L);
        }
        SysAclModule sysAclModule = new SysAclModule();
        BeanUtils.copyProperties(aclModuleVO, sysAclModule);
        sysAclModule.setLevel(LevelUtil.getLevel(sysAclModuleService.getAclModuleLevel(aclModuleVO.getParentId()), aclModuleVO.getParentId()));
        sysAclModule.setOperator(IpUtil.getUserIP(RequestHolder.getCurrentRequest()));
        sysAclModule.setOperateIp(RequestHolder.getCurrentRequest().getRemoteAddr());
        sysAclModule.setOperateTime(DateUtil.getNowDate());

        sysAclModuleMapper.insertSelective(sysAclModule);
    }

    @Override
    public void updateAclModule(AclModuleVO aclModuleVO) {
        BeanValidator.checkObject(aclModuleVO);
        if(sysAclModuleService.checkAclModuleExist(aclModuleVO.getParentId(), aclModuleVO.getName(), aclModuleVO.getId())) {
            throw new ParamsException("权限模块已存在");
        }

        SysAclModule before = sysAclModuleMapper.selectByPrimaryKey(aclModuleVO.getId());
        Preconditions.checkNotNull(before, "权限模块不存在");
        SysAclModule after = new SysAclModule();
        BeanUtils.copyProperties(aclModuleVO, after);
        after.setLevel(LevelUtil.getLevel(sysAclModuleService.getAclModuleLevel(aclModuleVO.getParentId()), aclModuleVO.getParentId()));
        after.setOperateIp(RequestHolder.getCurrentRequest().getRemoteAddr());
        after.setOperator(IpUtil.getUserIP(RequestHolder.getCurrentRequest()));
        after.setOperateTime(DateUtil.getNowDate());
        updateAclModuleWithChild(before, after);
    }

    @Transactional
    public void updateAclModuleWithChild(SysAclModule before, SysAclModule after) {
        String oldLevelPrefix = before.getLevel();
        String newLevelPrefix = after.getLevel();

        //如果部门级别不一致，则更新子部门
        if(!after.getLevel().equals(before.getLevel())) {
            String selectLevel = LevelUtil.getLevel(before.getLevel(), before.getId());
            List<SysAclModule> aclModuleList = sysAclModuleMapper.selectChildAclModuleByLevel(selectLevel);
            if(CollectionUtils.isNotEmpty(aclModuleList)) {
                for (SysAclModule aclModule : aclModuleList) {
                    String level = aclModule.getLevel();
                    if(level.indexOf(oldLevelPrefix) == 0) {
                        level = newLevelPrefix + level.substring(oldLevelPrefix.length());
                        aclModule.setLevel(level);
                    }
                    sysAclModuleMapper.updateAclModuleLevel(aclModule);
                }
            }
        }
        sysAclModuleMapper.updateByPrimaryKeySelective(after);
    }

    @Override
    public boolean checkAclModuleExist(Long parentId, String aclModuleName, Long aclModuleId) {
        return sysAclModuleMapper.checkAclModuleExist(parentId, aclModuleName, aclModuleId) > 0;
    }

    @Override
    public String getAclModuleLevel(Long id) {
        SysAclModule aclModule = sysAclModuleMapper.selectByPrimaryKey(id);
        if(aclModule == null) {
            return "";
        }
        return aclModule.getLevel();
    }
}
