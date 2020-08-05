package com.chang.ccloud.service.impl;

import com.chang.ccloud.common.utils.IpUtil;
import com.chang.ccloud.dao.SysDeptMapper;
import com.chang.ccloud.entities.vo.DeptRequestVO;
import com.chang.ccloud.entities.vo.DeptTableVO;
import com.chang.ccloud.exception.ParamsException;
import com.chang.ccloud.holder.RequestHolder;
import com.chang.ccloud.model.SysDept;
import com.chang.ccloud.service.SysDeptService;
import com.chang.ccloud.common.utils.DateUtil;
import com.chang.ccloud.common.utils.LevelUtil;
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
 * @Date 2020/7/5 17:28
 * @Description
 */
@Service
public class SysDeptServiceImpl implements SysDeptService {

    @Autowired
    private SysDeptService deptService;

    @Autowired
    private SysDeptMapper sysDeptMapper;

    @Override
    public void addDept(DeptRequestVO deptRequestVO) {
        BeanValidator.checkObject(deptRequestVO);
        if(deptService.checkDeptExist(deptRequestVO.getParentId(), deptRequestVO.getName(), deptRequestVO.getId())) {
            throw new ParamsException("部门已存在");
        }

        if(deptRequestVO.getParentId() == null) {
            deptRequestVO.setParentId(0L);
        }

        SysDept sysDept = new SysDept();
        BeanUtils.copyProperties(deptRequestVO,sysDept);
        // 设置部门level字段
        String parentLevel = deptService.getDeptLevel(deptRequestVO.getParentId());
        String level = LevelUtil.getLevel(parentLevel, deptRequestVO.getParentId());
        sysDept.setLevel(level);
        sysDept.setOperateIp(RequestHolder.getCurrentRequest().getRemoteAddr());
        sysDept.setOperator(IpUtil.getUserIP(RequestHolder.getCurrentRequest()));
        sysDept.setOperateTime(DateUtil.getNowDate());

        sysDeptMapper.insertSelective(sysDept);
    }

    @Override
    public void updateDept(DeptRequestVO deptRequestVO) {
        BeanValidator.checkObject(deptRequestVO);
        if(deptService.checkDeptExist(deptRequestVO.getParentId(), deptRequestVO.getName(), deptRequestVO.getId())) {
            throw new ParamsException("部门已存在");
        }
        // 校验部门是否存在
        SysDept before = sysDeptMapper.selectByPrimaryKey(deptRequestVO.getId());
        Preconditions.checkNotNull(before, "不存在此部门");

        SysDept after = new SysDept();
        BeanUtils.copyProperties(deptRequestVO, after);
        String parentLevel = deptService.getDeptLevel(deptRequestVO.getParentId());
        String level = LevelUtil.getLevel(parentLevel, deptRequestVO.getParentId());
        after.setLevel(level);
        after.setOperateTime(DateUtil.getNowDate());
        after.setOperator(RequestHolder.getCurrentUser().getUsername());
        after.setOperateIp(IpUtil.getUserIP(RequestHolder.getCurrentRequest()));
        updateDeptWithChild(before, after);
    }

    @Transactional
    public void updateDeptWithChild(SysDept before, SysDept after) {
        String oldLevelPrefix = before.getLevel();
        String newLevelPrefix = after.getLevel();

        //如果部门级别不一致，则更新子部门
        if(!after.getLevel().equals(before.getLevel())) {
            String selectLevel = LevelUtil.getLevel(before.getLevel(), before.getId());
            List<SysDept> deptList = sysDeptMapper.selectChildDeptByLevel(selectLevel);
            if(CollectionUtils.isNotEmpty(deptList)) {
                for (SysDept dept : deptList) {
                    String level = dept.getLevel();
                    if(level.indexOf(oldLevelPrefix) == 0) {
                        level = newLevelPrefix + level.substring(oldLevelPrefix.length());
                        dept.setLevel(level);
                    }
                    sysDeptMapper.updateDeptLevel(dept);
                }
            }
        }
        sysDeptMapper.updateByPrimaryKeySelective(after);
    }

    /**
     * 校验部门是否存在
     * @param: parentId
     * @param: deptName
     * @param: deptId
     * @return boolean
     * @Author changxizhao
     * @Date 2020/7/5 17:34
     */
    @Override
    public boolean checkDeptExist(Long parentId, String deptName, Long deptId) {
        return sysDeptMapper.checkDeptExist(parentId, deptName, deptId) > 0;
    }

    /**
     * 获取部门的level
     * @param: id
     * @return java.lang.String
     * @Author changxizhao
     * @Date 2020/7/5 21:56
     */
    @Override
    public String getDeptLevel(Long id) {
        SysDept sysDept = sysDeptMapper.selectByPrimaryKey(id);
        if(sysDept == null) {
            return "";
        }
        return sysDept.getLevel();
    }

    @Override
    public List<DeptTableVO> selectDeptTable(DeptTableVO deptTableVO) {
        return sysDeptMapper.selectDeptTable(deptTableVO);
    }

    @Override
    public SysDept selectDeptById(Long id) {
        return sysDeptMapper.selectByPrimaryKey(id);
    }
}
