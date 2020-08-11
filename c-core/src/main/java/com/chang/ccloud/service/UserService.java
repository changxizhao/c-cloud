package com.chang.ccloud.service;

import com.chang.ccloud.entities.vo.UserMenuVO;
import com.chang.ccloud.model.SysUser;

import java.util.List;

/**
 * @Author changxizhao
 * @Date 2020/7/16 12:19
 * @Description
 */
public interface UserService {

    SysUser login(String username, String password);

    List<UserMenuVO> selectUserMenusByUserId(long userId);

    List<UserMenuVO> selectAllMenus();

}
