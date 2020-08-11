package com.chang.ccloud.controller.role;

import com.chang.ccloud.common.Result;
import com.chang.ccloud.common.utils.JsonConvertUtil;
import com.chang.ccloud.entities.vo.RoleUserVO;
import com.chang.ccloud.entities.vo.UserTableVO;
import com.chang.ccloud.entities.vo.UserVO;
import com.chang.ccloud.service.SysRoleUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author changxizhao
 * @Date 2020/8/11 8:56
 * @Description
 */
@Api(tags = "角色人员相关接口")
@RestController
@RequestMapping("/api/sys/role/user")
public class SysRoleUserController {

    private static Logger log = LoggerFactory.getLogger(SysRoleUserController.class);

    @Autowired
    private SysRoleUserService roleUserService;

    @ApiOperation("获取当前添加的角色人员")
    @GetMapping("/roleUser/{roleId}")
    public Result getSelectedRoleUser(@PathVariable("roleId") Long roleId) {
        List<UserVO> userVOList = roleUserService.selectUserIdListByRoleId(roleId);
        return Result.success(userVOList);
    }

    @ApiOperation(value = "角色添加人员")
    @PostMapping("/change")
    public Result updateRoleAcl(@RequestBody RoleUserVO roleUserVO) {
        log.info("角色添加用户入参：{}", JsonConvertUtil.obj2String(roleUserVO));
        roleUserService.changeRoleUser(roleUserVO);
        return Result.success();
    }
}
