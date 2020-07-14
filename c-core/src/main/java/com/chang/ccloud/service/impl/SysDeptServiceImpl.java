package com.chang.ccloud.service.impl;

import com.chang.ccloud.dao.SysDeptMapper;
import com.chang.ccloud.entities.bo.DeptBO;
import com.chang.ccloud.exception.ParamsException;
import com.chang.ccloud.model.SysDept;
import com.chang.ccloud.service.SysDeptService;
import com.chang.ccloud.common.utils.DateUtil;
import com.chang.ccloud.common.utils.DeptLevelUtil;
import com.chang.ccloud.validator.BeanValidator;
import com.google.common.base.Preconditions;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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
    public void addDept(DeptBO deptBO) {
        BeanValidator.checkObject(deptBO);
        if(deptService.checkDeptExist(deptBO.getParentId(), deptBO.getName(), deptBO.getId())) {
            throw new ParamsException("部门已存在");
        }

        if(deptBO.getParentId() == null) {
            deptBO.setParentId(0L);
        }

        SysDept sysDept = new SysDept();
        BeanUtils.copyProperties(deptBO,sysDept);
        // 设置部门level字段
        String parentLevel = deptService.getDeptLevel(deptBO.getParentId());
        String level = DeptLevelUtil.getLevel(parentLevel, deptBO.getParentId());
        sysDept.setLevel(level);
        sysDept.setOperator("admin"); // TODO
        sysDept.setOperateIp("127.0.0.1"); // TODO
        sysDept.setOperateTime(DateUtil.getNowDate());

        sysDeptMapper.insertSelective(sysDept);
    }

    @Override
    public void updateDept(DeptBO deptBO) {
        BeanValidator.checkObject(deptBO);
        if(deptService.checkDeptExist(deptBO.getParentId(), deptBO.getName(), deptBO.getId())) {
            throw new ParamsException("部门已存在");
        }
        // 校验部门是否存在
        SysDept before = sysDeptMapper.selectByPrimaryKey(deptBO.getId());
        Preconditions.checkNotNull(before, "不存在此部门");

        SysDept after = new SysDept();
        BeanUtils.copyProperties(deptBO, after);
        String parentLevel = deptService.getDeptLevel(deptBO.getParentId());
        String level = DeptLevelUtil.getLevel(parentLevel, deptBO.getParentId());
        after.setLevel(level);
        after.setOperateTime(DateUtil.getNowDate());
        after.setOperator("admin"); // TODO
        after.setOperateIp("127.0.0.1"); // TODO
        updateDeptWithChild(before, after);
    }

    @Transactional
    public void updateDeptWithChild(SysDept before, SysDept after) {
        String oldLevelPrefix = before.getLevel();
        String newLevelPrefix = after.getLevel();

        //如果部门级别不一致，则更新子部门
        if(!after.getLevel().equals(before.getLevel())) {
            List<SysDept> deptList = sysDeptMapper.selectChildDeptByLevel(before.getLevel());
            if(CollectionUtils.isNotEmpty(deptList)) {
                for (SysDept dept : deptList) {
                    String level = dept.getLevel();
                    if(level.indexOf(oldLevelPrefix) == 0) {
                        level = newLevelPrefix + level.substring(oldLevelPrefix.length());
                        dept.setLevel(level);
                    }
                }
                sysDeptMapper.batchUpdateDeptLevel(deptList);
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
    public List<SysDept> selectDeptTable(Long id, String level) {
//        String level = DeptLevelUtil.getLevel(sysDept == null ? "" : sysDept.getLevel(), id);
        return sysDeptMapper.selectDeptTable(id, level);
    }

    @Override
    public SysDept selectDeptById(Long id) {
        return sysDeptMapper.selectByPrimaryKey(id);
    }
}
