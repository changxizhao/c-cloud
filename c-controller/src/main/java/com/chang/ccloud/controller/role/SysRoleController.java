package com.chang.ccloud.controller.role;

import com.chang.ccloud.common.Result;
import com.chang.ccloud.common.TableInfo;
import com.chang.ccloud.common.utils.JsonConvertUtil;
import com.chang.ccloud.entities.vo.RoleVO;
import com.chang.ccloud.service.SysRoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * @Author changxizhao
 * @Date 2020/8/7 16:47
 * @Description
 */
@RestController
@RequestMapping("/api/sys/role")
public class SysRoleController {

    private static Logger log = LoggerFactory.getLogger(SysRoleController.class);

    @Autowired
    private SysRoleService roleService;

    @ApiOperation(value = "新增角色")
    @PostMapping("/add")
    public Result addRole(RoleVO roleVO){
        roleService.add(roleVO);
        return Result.success();
    }

    @ApiOperation(value = "更新角色")
    @PostMapping("/update")
    public Result updateRole(RoleVO roleVO) {
        roleService.update(roleVO);
        return Result.success();
    }

    @ApiOperation(value = "获取角色列表")
    @GetMapping("/table")
    public TableInfo deptTable(RoleVO roleVO) {
        log.info("查询角色列表入参: {}", JsonConvertUtil.obj2String(roleVO));
        PageHelper.startPage(roleVO.getPageNumber(),roleVO.getPageSize());
        List<RoleVO> roleVOList = roleService.selectAllRoles(roleVO);
        PageInfo<RoleVO> page=new PageInfo<>(roleVOList);
        return TableInfo.tableInfo(page);
    }

}
