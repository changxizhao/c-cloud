package com.chang.ccloud.controller.acl;

import com.chang.ccloud.common.Result;
import com.chang.ccloud.entities.dto.SysMenuDTO;
import com.chang.ccloud.entities.dto.TreeViewDTO;
import com.chang.ccloud.entities.vo.SysMenuVO;
import com.chang.ccloud.service.SysMenuService;
import com.chang.ccloud.service.SysTreeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author changxizhao
 * @Date 2020/8/3 13:50
 * @Description
 */
@Api(tags = "权限菜单相关接口")
@RequestMapping("/api/sys/menu")
@RestController
public class SysMenuController {

    @Autowired
    private SysMenuService menuService;

    @Autowired
    private SysTreeService treeService;

    @ApiOperation(value = "新建权限")
    @PostMapping("/add")
    public Result addDept(SysMenuVO sysMenuVO){
        menuService.addMenu(sysMenuVO);
        return Result.success();
    }

    @ApiOperation(value = "更新权限")
    @PostMapping("/update")
    public Result updateDept(SysMenuVO sysMenuVO){
        menuService.updateMenu(sysMenuVO);
        return Result.success();
    }

    @ApiOperation(value = "获取权限树形表")
    @GetMapping("/treegrid")
    public Result deptTree(SysMenuVO sysMenuVO) {
        List<SysMenuVO> treegrid = menuService.treegrid(sysMenuVO);
        return Result.success(treegrid);
    }

    @ApiOperation(value = "获取角色权限树")
    @GetMapping("/tree")
    public Result deptTree(Integer roleId) {
        List<SysMenuDTO> dtoList = treeService.menuTree();
        List<TreeViewDTO> treeViewDTOS = treeService.menuToTreeView(dtoList, roleId);
        return Result.success(treeViewDTOS);
    }

}
