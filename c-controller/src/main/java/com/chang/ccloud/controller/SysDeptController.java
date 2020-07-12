package com.chang.ccloud.controller;

import com.chang.ccloud.common.Result;
import com.chang.ccloud.common.TableInfo;
import com.chang.ccloud.common.utils.JsonConvertUtil;
import com.chang.ccloud.entities.bo.DeptBO;
import com.chang.ccloud.entities.dto.DeptLevelDTO;
import com.chang.ccloud.entities.dto.DeptTreeViewDTO;
import com.chang.ccloud.service.SysDeptService;
import com.chang.ccloud.service.SysTreeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping("/sys/dept")
public class SysDeptController {

    @Autowired
    private SysDeptService deptService;

    @Autowired
    private SysTreeService treeService;

    @ApiOperation(value = "新建部门")
    @PostMapping("/add")
    public Result addDept(DeptBO deptBO){
        deptService.addDept(deptBO);
        return Result.success();
    }

    @ApiOperation(value = "更新部门")
    @PostMapping("/update")
    public Result updateDept(DeptBO deptBO){
        deptService.updateDept(deptBO);
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
    public TableInfo deptTable(@RequestParam int pageNumber, int pageSize) {
//        List<DeptLevelDTO> dtoList = treeService.deptTree();
//        return Result.success(dtoList);
        PageHelper.startPage(pageNumber,pageSize);
        List<DeptLevelDTO> dtoList = treeService.deptTree();
        PageInfo<DeptLevelDTO> page=new PageInfo<>(dtoList);

        return TableInfo.tableInfo(page);
    }

}
