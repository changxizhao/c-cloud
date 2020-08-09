package com.chang.ccloud.service;

import com.chang.ccloud.entities.vo.SysMenuVO;
import com.chang.ccloud.model.SysMenu;

import java.util.List;

/**
 * @Author changxizhao
 * @Date 2020/8/5 13:32
 * @Description
 */
public interface SysMenuService {

    void addMenu(SysMenuVO sysMenuVO);

    void updateMenu(SysMenuVO sysMenuVO);

    boolean checkSysMenuExist(Long parentId, String menuName);

    List<SysMenuVO> treegrid(SysMenuVO sysMenuVO);

}
