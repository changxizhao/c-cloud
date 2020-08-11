package com.chang.ccloud.service.impl;

import com.chang.ccloud.common.utils.MD5Util;
import com.chang.ccloud.common.utils.Util;
import com.chang.ccloud.dao.*;
import com.chang.ccloud.entities.dto.SysMenuDTO;
import com.chang.ccloud.entities.vo.UserMenuVO;
import com.chang.ccloud.exception.LoginException;
import com.chang.ccloud.model.SysMenu;
import com.chang.ccloud.model.SysRole;
import com.chang.ccloud.model.SysUser;
import com.chang.ccloud.service.SysRoleUserService;
import com.chang.ccloud.service.SysTreeService;
import com.chang.ccloud.service.UserService;
import com.google.common.base.Preconditions;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author changxizhao
 * @Date 2020/7/16 12:27
 * @Description
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private SysRoleUserMapper roleUserMapper;

    @Autowired
    private SysRoleAclMapper roleAclMapper;

    @Autowired
    private SysTreeService treeService;

    @Autowired
    private SysMenuMapper menuMapper;

    @Override
    public SysUser login(String username, String password) {
        if(StringUtils.isBlank(username)) {
            throw new LoginException("用户名不能为空");
        }
        if(StringUtils.isBlank(password)) {
            throw new LoginException("密码不能为空");
        }
        SysUser sysUser = userMapper.selectUserByUsername(username);
        Preconditions.checkNotNull(sysUser, "此用户不存在");
        if(!MD5Util.verify(password, sysUser.getPassword())) {
            throw new LoginException("用户名或密码错误");
        }
        if(sysUser.getStatus() != 1) {
            throw new LoginException("此账号已冻结，请联系系统管理员");
        }
        return sysUser;
    }

    @Override
    public List<UserMenuVO> selectUserMenusByUserId(long userId) {
        List<Long> roleIds = roleUserMapper.selectUserRoleIdsByUserId(userId);
        if(CollectionUtils.isEmpty(roleIds)) {
            return new ArrayList<>();
        }
        List<Long> aclIds = roleAclMapper.selectMenuIdListByRoleIdList(roleIds);
        List<SysMenuDTO> menuDTOS = treeService.menuTree(aclIds);

        return transformMenuToUserMenuTree(menuDTOS, 0L);
    }

    @Override
    public List<UserMenuVO> selectAllMenus() {
        SysMenu sysMenu = new SysMenu();
        sysMenu.setType(0);
        List<SysMenuDTO> menuDTOS = treeService.menuTree(sysMenu);
        return transformMenuToUserMenuTree(menuDTOS, 0L);
    }

    public List<UserMenuVO> transformMenuToUserMenuTree(List<SysMenuDTO> list, Long parentId) {
        if(CollectionUtils.isEmpty(list)) {
            return new ArrayList<>();
        }
        List<UserMenuVO> result = new ArrayList<>();
        for (SysMenuDTO dto : list) {
            UserMenuVO userMenuVO = new UserMenuVO();
            userMenuVO.setMenuId(dto.getId());
            userMenuVO.setParentId(parentId);
            userMenuVO.setUrl(dto.getUrl());
            userMenuVO.setName(dto.getName());
            if(Util.equalsTo(parentId, 0L)) {
                userMenuVO.setType(0);
            }else {
                userMenuVO.setType(1);
            }
            userMenuVO.setIcon("fa fa-cog");
            userMenuVO.setList(transformMenuToUserMenuTree(dto.getMenuList(), dto.getId()));
            result.add(userMenuVO);
        }
        return result;
    }
}
