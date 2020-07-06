package com.chang.ccloud.service.impl;

import com.chang.ccloud.dao.SysDeptMapper;
import com.chang.ccloud.entities.bo.DeptBO;
import com.chang.ccloud.exception.ParamsException;
import com.chang.ccloud.model.SysDept;
import com.chang.ccloud.service.SysDeptService;
import com.chang.ccloud.common.utils.DateUtil;
import com.chang.ccloud.common.utils.DeptLevelUtil;
import com.chang.ccloud.validator.BeanValidator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public void save(DeptBO deptBO) throws InstantiationException, IllegalAccessException {
        BeanValidator.checkObject(deptBO);
        if(deptService.checkDeptExist(deptBO.getParentId(), deptBO.getName(), deptBO.getId())) {
            throw new ParamsException("部门已存在");
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
        // TODO
        return true;
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
            return null;
        }
        return sysDept.getLevel();
    }
}
