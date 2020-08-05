package com.chang.ccloud.controller;

import com.chang.ccloud.common.Result;
import com.chang.ccloud.common.TableInfo;
import com.chang.ccloud.common.utils.LevelUtil;
import com.chang.ccloud.common.utils.JsonConvertUtil;
import com.chang.ccloud.entities.vo.UserRequestVO;
import com.chang.ccloud.entities.vo.UserTableVO;
import com.chang.ccloud.model.SysDept;
import com.chang.ccloud.service.SysDeptService;
import com.chang.ccloud.service.SysUserService;
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
 * @Date 2020/7/16 8:37
 * @Description
 */
@Api(tags = "系统用户相关接口")
@RestController
@RequestMapping("/api/sys/user")
public class SysUserController {

    private static Logger log = LoggerFactory.getLogger(SysUserController.class);

    @Autowired
    private SysUserService userService;

    @Autowired
    private SysDeptService deptService;

    @ApiOperation(value = "新增用户")
    @PostMapping("/add")
    public Result addUser(UserRequestVO userRequestVO){
        log.info("新增用户入参：{}", JsonConvertUtil.obj2String(userRequestVO));
        userService.addUser(userRequestVO);
        return Result.success();
    }

    @ApiOperation(value = "更新用户")
    @PostMapping("/update")
    public Result updateUser(UserRequestVO userRequestVO){
        log.info("更新用户入参：{}", JsonConvertUtil.obj2String(userRequestVO));
        userService.updateUser(userRequestVO);
        return Result.success();
    }

    @ApiOperation(value = "获取用户列表")
    @GetMapping("/table")
    public TableInfo userList(UserTableVO userTableVO){
        log.info("查询用户列表入参 {}，{}", userTableVO.getPageNumber(), userTableVO.getPageSize());

        SysDept sysDept = deptService.selectDeptById(userTableVO.getDeptId());
        String selectLevel = LevelUtil.getLevel(sysDept == null ? "" : sysDept.getLevel(), userTableVO.getDeptId());
        userTableVO.setDeptLevel(selectLevel);

        PageHelper.startPage(userTableVO.getPageNumber(),userTableVO.getPageSize());
        List<UserTableVO> usersList = userService.selectUserTable(userTableVO);
        PageInfo<UserTableVO> page=new PageInfo<>(usersList);
        return TableInfo.tableInfo(page);
    }
}
