package com.chang.ccloud.controller.role;

import com.chang.ccloud.common.Result;
import com.chang.ccloud.common.utils.JsonConvertUtil;
import com.chang.ccloud.entities.vo.RoleAclVO;
import com.chang.ccloud.service.SysRoleAclService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author changxizhao
 * @Date 2020/8/9 11:20
 * @Description
 */
@Api(tags = "角色权限相关接口")
@RestController
@RequestMapping("/api/sys/role/acl")
public class SysRoleAclController {

    private static Logger log = LoggerFactory.getLogger(SysRoleAclController.class);

    @Autowired
    private SysRoleAclService roleAclService;

    @ApiOperation(value = "角色授权")
    @PostMapping("/change")
    public Result updateRoleAcl(@RequestBody RoleAclVO roleAclVO) {
        log.info("角色授权入参：{}", JsonConvertUtil.obj2String(roleAclVO));
        roleAclService.changeRoleAcl(roleAclVO);
        return Result.success();
    }

}
