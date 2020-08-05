package com.chang.ccloud.service.impl;

import com.chang.ccloud.common.utils.DateUtil;
import com.chang.ccloud.common.utils.IpUtil;
import com.chang.ccloud.dao.SysMenuMapper;
import com.chang.ccloud.entities.vo.SysMenuVO;
import com.chang.ccloud.exception.ParamsException;
import com.chang.ccloud.holder.RequestHolder;
import com.chang.ccloud.model.SysMenu;
import com.chang.ccloud.service.SysMenuService;
import com.chang.ccloud.validator.BeanValidator;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
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

    @Override
    public void addMenu(SysMenuVO sysMenuVO) {
        BeanValidator.checkObject(sysMenuVO);
        if(sysMenuVO.getParentId() == null) {
            sysMenuVO.setParentId(0L);
        }
        if(menuService.checkSysMenuExist(sysMenuVO.getParentId(), sysMenuVO.getName())) {
            throw new ParamsException("权限名称已存在");
        }

        SysMenu sysMenu = new SysMenu();
        BeanUtils.copyProperties(sysMenuVO, sysMenu);
        sysMenu.setOperator(IpUtil.getUserIP(RequestHolder.getCurrentRequest()));
        sysMenu.setOperateIp(RequestHolder.getCurrentRequest().getRemoteAddr());
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

        SysMenu after = new SysMenu();
        BeanUtils.copyProperties(sysMenuVO, after);
        after.setOperator(IpUtil.getUserIP(RequestHolder.getCurrentRequest()));
        after.setOperateIp(RequestHolder.getCurrentRequest().getRemoteAddr());
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
}
