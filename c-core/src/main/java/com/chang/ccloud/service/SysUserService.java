package com.chang.ccloud.service;


import com.chang.ccloud.entities.vo.UserRequestVO;
import com.chang.ccloud.entities.vo.UserTableVO;
import com.chang.ccloud.model.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author changxizhao
 * @Date 2020/7/3 14:05
 * @Description
 */
public interface SysUserService {

    void addUser(UserRequestVO userRequestVO);

    void updateUser(UserRequestVO userRequestVO);

    SysUser findUserByUsername(Long id);

    List<UserTableVO> selectUserTable(UserTableVO userTableVO);

    void deleteUserById(@Param("id") long id);
}
