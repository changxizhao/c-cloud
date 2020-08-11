package com.chang.ccloud.service.impl;

import com.chang.ccloud.common.constants.IntConstants;
import com.chang.ccloud.common.utils.DateUtil;
import com.chang.ccloud.common.utils.IpUtil;
import com.chang.ccloud.common.utils.Util;
import com.chang.ccloud.dao.SysMenuMapper;
import com.chang.ccloud.dao.SysRoleAclMapper;
import com.chang.ccloud.entities.vo.SysMenuVO;
import com.chang.ccloud.exception.ParamsException;
import com.chang.ccloud.holder.RequestHolder;
import com.chang.ccloud.model.SysMenu;
import com.chang.ccloud.service.SysMenuService;
import com.chang.ccloud.validator.BeanValidator;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author changxizhao
 * @Date 2020/8/5 13:43
 * @Description
 */
@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    private SysMenuMapper menuMapper;

    @Autowired
    private SysMenuService menuService;

    @Autowired
    private SysRoleAclMapper roleAclMapper;

    @Override
    public void addMenu(SysMenuVO sysMenuVO) {
        BeanValidator.checkObject(sysMenuVO);
        if(sysMenuVO.getParentId() == null) {
            sysMenuVO.setParentId(0L);
        }
        if(menuService.checkSysMenuExist(sysMenuVO.getParentId(), sysMenuVO.getName())) {
            throw new ParamsException("权限名称已存在");
        }
        if(Util.equalsTo(IntConstants.MENU.getVal(), sysMenuVO.getType())) {
            if(StringUtils.isEmpty(sysMenuVO.getUrl()) || sysMenuVO.getVisible() == null) {
                throw new ParamsException("类型为菜单时,菜单地址和是否隐藏均不能为空");
            }
        }

        SysMenu sysMenu = new SysMenu();
        BeanUtils.copyProperties(sysMenuVO, sysMenu);
        sysMenu.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysMenu.setOperateIp(IpUtil.getUserIP(RequestHolder.getCurrentRequest()));
        sysMenu.setOperateTime(DateUtil.getNowDate());
        menuMapper.insertSelective(sysMenu);

    }

    @Override
    public void updateMenu(SysMenuVO sysMenuVO) {
        BeanValidator.checkObject(sysMenuVO);
        SysMenu before = menuMapper.selectByPrimaryKey(sysMenuVO.getId());
        if(!before.getName().equals(sysMenuVO.getName())) {
            if(menuService.checkSysMenuExist(sysMenuVO.getParentId(), sysMenuVO.getName())) {
                throw new ParamsException("权限名称已存在");
            }
        }
        if(Util.equalsTo(sysMenuVO.getType(), IntConstants.MENU.getVal())) {
            if(sysMenuVO.getVisible() == null || StringUtils.isEmpty(sysMenuVO.getUrl())) {
                throw new ParamsException("类型为菜单时,菜单地址和是否隐藏均不能为空");
            }
        }

        SysMenu after = new SysMenu();
        BeanUtils.copyProperties(sysMenuVO, after);
        after.setOperateIp(IpUtil.getUserIP(RequestHolder.getCurrentRequest()));
        after.setOperator(RequestHolder.getCurrentUser().getUsername());
        after.setOperateTime(DateUtil.getNowDate());
        menuMapper.updateByPrimaryKeySelective(after);

    }

    @Override
    public boolean checkSysMenuExist(Long parentId, String menuName) {
        return menuMapper.checkSysMenuExist(parentId, menuName) > 0;
    }

    @Override
    public List<SysMenuVO> treegrid(SysMenuVO sysMenuVO) {
        SysMenu sysMenu = new SysMenu();
        BeanUtils.copyProperties(sysMenuVO, sysMenu);
        List<SysMenu> menuList =  menuMapper.selectMenuTreegrid(sysMenu);
        if(CollectionUtils.isEmpty(menuList)) {
            return Lists.newArrayList();
        }
        List<SysMenuVO> voList = new ArrayList<>();
        for (SysMenu menu : menuList) {
            SysMenuVO vo = new SysMenuVO();
            BeanUtils.copyProperties(menu, vo);
            voList.add(vo);
        }
        return voList;
    }

    @Override
    public void deleteMenuById(long id) {
        SysMenu sysMenu = menuMapper.selectByPrimaryKey(id);
        Preconditions.checkNotNull(sysMenu, "菜单/权限不存在");
        // 校验是否有子菜单/权限
        if(menuMapper.selectCountMenuByParentId(id) > 0) {
            throw new ParamsException("存在子菜单/权限，不允许删除");
        }
        // 校验是否授权了角色
        if(roleAclMapper.selectCountRoleAclByAclId(id) > 0) {
            throw new ParamsException("已授权角色，不允许删除");
        }
        menuMapper.deleteByPrimaryKey(id);
    }

}
