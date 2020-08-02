package com.chang.ccloud.controller;

import com.chang.ccloud.common.Result;
import com.chang.ccloud.common.TableInfo;
import com.chang.ccloud.common.utils.DeptLevelUtil;
import com.chang.ccloud.entities.vo.DeptRequestVO;
import com.chang.ccloud.entities.dto.DeptLevelDTO;
import com.chang.ccloud.entities.dto.DeptTreeViewDTO;
import com.chang.ccloud.entities.vo.DeptTableVO;
import com.chang.ccloud.model.SysDept;
import com.chang.ccloud.service.SysDeptService;
import com.chang.ccloud.service.SysTreeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @Author changxizhao
 * @Date 2020/7/5 17:24
 * @Description
 */
@Api(tags = "部门相关接口")
@RestController
@RequestMapping("/api/sys/dept")
public class SysDeptController {

    private static Logger log = LoggerFactory.getLogger(SysDeptController.class);

    @Autowired
    private SysDeptService deptService;

    @Autowired
    private SysTreeService treeService;

    @ApiOperation(value = "新建部门")
    @PostMapping("/add")
    public Result addDept(DeptRequestVO deptRequestVO){
        log.info("新建部门 上级部门id = {}", deptRequestVO.getParentId());
        deptService.addDept(deptRequestVO);
        return Result.success();
    }

    @ApiOperation(value = "更新部门")
    @PostMapping("/update")
    public Result updateDept(DeptRequestVO deptRequestVO){
        deptService.updateDept(deptRequestVO);
        return Result.success();
    }

    @ApiOperation(value = "获取部门树")
    @GetMapping("/tree")
    public Result deptTree() {
        List<DeptLevelDTO> dtoList = treeService.deptTree();
        List<DeptTreeViewDTO> deptTreeViewDTOS = treeService.toTreeView(dtoList);
        return Result.success(deptTreeViewDTOS);
    }

    @ApiOperation(value = "获取部门列表")
    @GetMapping("/table")
    public TableInfo deptTable(DeptTableVO deptTableVO) {
        log.info("查询部门列表入参 {}，{}，{}", deptTableVO.getPageNumber(), deptTableVO.getPageSize(), deptTableVO.getId());
        SysDept sysDept = deptService.selectDeptById(deptTableVO.getId());
        String selectLevel = DeptLevelUtil.getLevel(sysDept == null ? "" : sysDept.getLevel(), deptTableVO.getId());
        deptTableVO.setLevel(selectLevel);
        PageHelper.startPage(deptTableVO.getPageNumber(),deptTableVO.getPageSize());
        List<DeptTableVO> deptList = deptService.selectDeptTable(deptTableVO);
        PageInfo<DeptTableVO> page=new PageInfo<>(deptList);
        return TableInfo.tableInfo(page);
    }

}
