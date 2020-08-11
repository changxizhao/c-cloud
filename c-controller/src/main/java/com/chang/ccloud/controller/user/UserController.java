package com.chang.ccloud.controller.user;

import com.chang.ccloud.common.Result;
import com.chang.ccloud.entities.vo.UserMenuVO;
import com.chang.ccloud.entities.vo.UserVO;
import com.chang.ccloud.holder.RequestHolder;
import com.chang.ccloud.model.SysUser;
import com.chang.ccloud.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author changxizhao
 * @Date 2020/8/3 11:42
 * @Description
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/current")
    public Result getCurrent() {
        SysUser currentUser = RequestHolder.getCurrentUser();
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(currentUser, userVO);
        return Result.success(userVO);
    }

    @GetMapping("/menu")
    public Result getMenu() {
        SysUser sysUser = RequestHolder.getCurrentUser();
        if(sysUser.getIsAdmin() == 1) {
            List<UserMenuVO> userMenuVOS = userService.selectAllMenus();
            return Result.success(userMenuVOS);
        }
        List<UserMenuVO> userMenuVOS = userService.selectUserMenusByUserId(sysUser.getId());
        return Result.success(userMenuVOS);
    }

}
