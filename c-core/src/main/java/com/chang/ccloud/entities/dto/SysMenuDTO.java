package com.chang.ccloud.entities.dto;

import com.chang.ccloud.model.SysMenu;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import java.util.List;

/**
 * @Author changxizhao
 * @Date 2020/8/5 8:44
 * @Description
 */
public class SysMenuDTO extends SysMenu {

    private List<SysMenuDTO> menuList = Lists.newArrayList();

    public static SysMenuDTO adept(SysMenu sysMenu) {
        SysMenuDTO dto = new SysMenuDTO();
        BeanUtils.copyProperties(sysMenu, dto);
        return dto;
    }

    public List<SysMenuDTO> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<SysMenuDTO> menuList) {
        this.menuList = menuList;
    }
}
