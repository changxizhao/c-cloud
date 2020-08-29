package com.chang.ccloud.service;

import com.chang.ccloud.entities.vo.RoleUserVO;
import com.chang.ccloud.entities.vo.UserVO;
import com.chang.ccloud.model.SysRole;

import java.util.List;

/**
 * @Author changxizhao
 * @Date 2020/8/11 8:58
 * @Description
 */
public interface SysRoleUserService {

    List<UserVO> selectUserIdListByRoleId(long roleId);

    void changeRoleUser(RoleUserVO roleUserVO);

    void deleteRoleByUserId(long id);

    List<Long> selectRoleListByUserId(long userId);
}
