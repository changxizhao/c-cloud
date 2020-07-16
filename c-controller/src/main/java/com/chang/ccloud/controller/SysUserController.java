package com.chang.ccloud.controller;

import com.chang.ccloud.common.Result;
import com.chang.ccloud.common.TableInfo;
import com.chang.ccloud.common.utils.DeptLevelUtil;
import com.chang.ccloud.common.utils.JsonConvertUtil;
import com.chang.ccloud.entities.vo.DeptTableVO;
import com.chang.ccloud.entities.vo.UserRequestVO;
import com.chang.ccloud.model.SysDept;
import com.chang.ccloud.model.SysUser;
import com.chang.ccloud.service.SysUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author changxizhao
 * @Date 2020/7/16 8:37
 * @Description
 */
@Api(tags = "系统用户相关接口")
@RestController
@RequestMapping("/sys/user")
public class SysUserController {

    private static Logger log = LoggerFactory.getLogger(SysUserController.class);

    @Autowired
    private SysUserService userService;

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
    @PostMapping("/table")
    public TableInfo userList(@RequestParam int pageNumber, @RequestParam int pageSize){
        log.info("查询用户列表入参 {}，{}，{}", pageNumber, pageSize);
        PageHelper.startPage(pageNumber,pageSize);
        List<SysUser> usersList = Lists.newArrayList();
        PageInfo<SysUser> page=new PageInfo<>(usersList);
        return TableInfo.tableInfo(page);
    }
}
