package com.chang.ccloud.service.impl;

import com.chang.ccloud.common.utils.LevelUtil;
import com.chang.ccloud.dao.SysAclModuleMapper;
import com.chang.ccloud.dao.SysDeptMapper;
import com.chang.ccloud.dao.SysMenuMapper;
import com.chang.ccloud.entities.dto.SysMenuDTO;
import com.chang.ccloud.entities.dto.DeptLevelDTO;
import com.chang.ccloud.entities.dto.TreeViewDTO;
import com.chang.ccloud.model.SysMenu;
import com.chang.ccloud.service.SysRoleAclService;
import com.chang.ccloud.service.SysTreeService;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author changxizhao
 * @Date 2020/7/6 9:19
 * @Description
 */
@Service
public class SysTreeServiceImpl implements SysTreeService {

    @Autowired
    private SysDeptMapper deptMapper;

    @Autowired
    private SysRoleAclService roleAclService;

    @Autowired
    private SysMenuMapper menuMapper;

    public static final String ROOT = "0";

    //生成部门树
    @Override
    public List<DeptLevelDTO> deptTree() {
        List<DeptLevelDTO> deptList = deptMapper.selectAllDepts();
        return deptListToTree(deptList);
    }

    // 把dtoList转成树形列表
    public List<DeptLevelDTO> deptListToTree(List<DeptLevelDTO> deptLevelList) {
        // 如果为空，返回一个空list
        if(CollectionUtils.isEmpty(deptLevelList)) {
            return Lists.newArrayList();
        }
        Multimap<String, DeptLevelDTO> levelDeptmap = ArrayListMultimap.create();
        List<DeptLevelDTO> rootList = Lists.newArrayList();
        for (DeptLevelDTO dto : deptLevelList) {
            levelDeptmap.put(dto.getLevel(), dto);
            // 取出一级部门
            if(LevelUtil.ROOT.equals(dto.getLevel())) {
                rootList.add(dto);
            }
        }
        // 按照seq从小到大排序
        Collections.sort(rootList, new Comparator<DeptLevelDTO>() {
            @Override
            public int compare(DeptLevelDTO o1, DeptLevelDTO o2) {
                return o1.getSeq() - o2.getSeq();
            }
        });

        // 递归生成树
        transformDeptTree(rootList, LevelUtil.ROOT, levelDeptmap);
        return rootList;
    }

    public void transformDeptTree(List<DeptLevelDTO> deptLevelList, String level, Multimap<String, DeptLevelDTO> levelDeptmap) {
        for (int i = 0; i < deptLevelList.size(); i++) {

            // 遍历每个元素
            DeptLevelDTO dto = deptLevelList.get(i);
            // 处理当前层级
            String nextLevel = LevelUtil.getLevel(level, dto.getId());
            // 处理下一层
            List<DeptLevelDTO> tempDeptList = (List<DeptLevelDTO>)levelDeptmap.get(nextLevel);
            if(CollectionUtils.isNotEmpty(tempDeptList)) {
                // 排序
                Collections.sort(tempDeptList, deptSeqComparator);
                // 设置下一层部门
                dto.setDeptList(tempDeptList);
                // 进入到下一层处理
                transformDeptTree(tempDeptList, nextLevel, levelDeptmap);
            }


        }
    }

    public Comparator<DeptLevelDTO> deptSeqComparator = new Comparator<DeptLevelDTO>() {
        @Override
        public int compare(DeptLevelDTO o1, DeptLevelDTO o2) {
            return o1.getSeq() - o2.getSeq();
        }
    };


    @Override
    public List<TreeViewDTO> deptToTreeView(List<DeptLevelDTO> list) {
        if(CollectionUtils.isEmpty(list)) {
            return Lists.newArrayList();
        }
        List<TreeViewDTO> result = new ArrayList<>();
        for (DeptLevelDTO dto : list) {

            TreeViewDTO treeViewDTO = new TreeViewDTO();
            treeViewDTO.setText(dto.getName());
            treeViewDTO.setHref("#"+dto.getName());
            treeViewDTO.setTags(Arrays.asList(dto.getId().toString()));
            treeViewDTO.setNodes(deptToTreeView(dto.getDeptList()));
            result.add(treeViewDTO);
        }
        return result;
    }


    // 生成权限树形列表
    @Override
    public List<SysMenuDTO> menuTree() {
//        List<SysMenuDTO> menuDTOS = menuMapper.selectAllMenu();
        return menuTree(new SysMenu());
    }

    // 生成权限树形列表
    @Override
    public List<SysMenuDTO> menuTree(SysMenu sysMenu) {
        List<SysMenuDTO> menuDTOS = menuMapper.selectAllMenu(sysMenu);
        return menuListToTree(menuDTOS);
    }

    // 生成权限树形列表
    @Override
    public List<SysMenuDTO> menuTree(List<Long> idList) {
        List<SysMenuDTO> menuDTOS = menuMapper.selectMenuByIdList(idList);
        return menuListToTree(menuDTOS);
    }

    @Override
    public List<TreeViewDTO> menuToTreeView(List<SysMenuDTO> list, long roleId) {

        List<Long> roleMenuList = roleAclService.selectMenuIdListByRoleId(roleId);
        if(CollectionUtils.isEmpty(list)) {
            return Lists.newArrayList();
        }

        return transformMenuToTreeView(list, roleMenuList);
    }


    public List<TreeViewDTO> transformMenuToTreeView(List<SysMenuDTO> list, List<Long> roleMenuList) {

        if(CollectionUtils.isEmpty(list)) {
            return Lists.newArrayList();
        }

        List<TreeViewDTO> result = new ArrayList<>();
        for (SysMenuDTO dto : list) {

            TreeViewDTO treeViewDTO = new TreeViewDTO();
            if(CollectionUtils.isNotEmpty(roleMenuList) && roleMenuList.contains(dto.getId())) {
                treeViewDTO.setChecked(true);
            }
            treeViewDTO.setText(dto.getName());
            treeViewDTO.setHref("#"+dto.getName());
            treeViewDTO.setTags(Arrays.asList(dto.getId().toString()));
            treeViewDTO.setNodes(transformMenuToTreeView(dto.getMenuList(), roleMenuList));
            result.add(treeViewDTO);
        }
        return result;
    }

    public List<SysMenuDTO> menuListToTree(List<SysMenuDTO> dtoList) {
        if(CollectionUtils.isEmpty(dtoList)){
            return Lists.newArrayList();
        }

        Multimap<String, SysMenuDTO> menuDTOMultimap = ArrayListMultimap.create();
        List<SysMenuDTO> rootList = Lists.newArrayList();
        for (SysMenuDTO dto : dtoList) {
            menuDTOMultimap.put(dto.getParentId().toString(), dto);
            // 取出一级权限模块
            if(ROOT.equals(dto.getParentId().toString())) {
                rootList.add(dto);
            }
        }
        transformAclModuleTree(rootList, menuDTOMultimap);
        return rootList;
    }

    public void transformAclModuleTree(List<SysMenuDTO> dtoList, Multimap<String, SysMenuDTO> menuMultimap) {
        for (int i = 0; i < dtoList.size(); i++) {
            SysMenuDTO dto = dtoList.get(i);
            List<SysMenuDTO> tempList = (List<SysMenuDTO>)menuMultimap.get(dto.getId().toString());
            if(CollectionUtils.isNotEmpty(tempList)) {
                dto.setMenuList(tempList);
                transformAclModuleTree(tempList, menuMultimap);
            }
        }
    }

}
