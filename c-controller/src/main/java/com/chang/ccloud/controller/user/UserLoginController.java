package com.chang.ccloud.controller.user;

import com.chang.ccloud.common.Result;
import com.chang.ccloud.holder.RequestHolder;
import com.chang.ccloud.model.SysUser;
import com.chang.ccloud.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author changxizhao
 * @Date 2020/7/16 10:26
 * @Description
 */
@Api(tags = "用户相关接口")
@RestController
@RequestMapping("/user")
public class UserLoginController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户登录")
    @PostMapping("/login")
    public Result login(String username, String password, HttpServletRequest request) {
        SysUser sysUser = userService.login(username, password);
        request.getSession().setAttribute("user", sysUser);
        request.setAttribute("username", sysUser.getUsername());
        return Result.success();
    }

    @ApiOperation(value = "用户登录")
    @GetMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().invalidate();
        RequestHolder.remove();
        response.sendRedirect("/login.html");
    }

}
