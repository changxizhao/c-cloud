package com.chang.ccloud.controller.user;

import com.chang.ccloud.common.Result;
import com.chang.ccloud.entities.vo.UserVO;
import com.chang.ccloud.holder.RequestHolder;
import com.chang.ccloud.model.SysUser;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author changxizhao
 * @Date 2020/8/3 11:42
 * @Description
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("/current")
    public Result getCurrent() {
        SysUser currentUser = RequestHolder.getCurrentUser();
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(currentUser, userVO);
        return Result.success(userVO);
    }

}
