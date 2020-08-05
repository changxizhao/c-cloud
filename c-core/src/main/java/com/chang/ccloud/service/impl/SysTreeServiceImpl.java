package com.chang.ccloud.service.impl;

import com.chang.ccloud.common.utils.LevelUtil;
import com.chang.ccloud.dao.SysAclModuleMapper;
import com.chang.ccloud.dao.SysDeptMapper;
import com.chang.ccloud.entities.dto.AclModuleLevelDTO;
import com.chang.ccloud.entities.dto.DeptLevelDTO;
import com.chang.ccloud.entities.dto.DeptTreeViewDTO;
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
    private SysAclModuleMapper aclModuleMapper;

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
    public List<DeptTreeViewDTO> toTreeView(List<DeptLevelDTO> list) {

        if(CollectionUtils.isEmpty(list)) {
            return Lists.newArrayList();
        }

        List<DeptTreeViewDTO> result = new ArrayList<>();

        for (DeptLevelDTO dto : list) {

            DeptTreeViewDTO treeViewDTO = new DeptTreeViewDTO();
            treeViewDTO.setText(dto.getName());
            treeViewDTO.setHref("#"+dto.getName());
            treeViewDTO.setTags(Arrays.asList(dto.getId().toString()));
            treeViewDTO.setNodes(toTreeView(dto.getDeptList()));
            result.add(treeViewDTO);
        }
        return result;
    }


    // 生成权限树形列表
    @Override
    public List<AclModuleLevelDTO> aclModuleTree() {
        List<AclModuleLevelDTO> moduleLevelDTOS = aclModuleMapper.selectAllAclModule();
        return aclModuleListToTree(moduleLevelDTOS);
    }

    public List<AclModuleLevelDTO> aclModuleListToTree(List<AclModuleLevelDTO> dtoList) {
        if(CollectionUtils.isEmpty(dtoList)){
            return Lists.newArrayList();
        }

        Multimap<String, AclModuleLevelDTO> levelDTOMultimap = ArrayListMultimap.create();
        List<AclModuleLevelDTO> rootList = Lists.newArrayList();
        for (AclModuleLevelDTO dto : dtoList) {
            levelDTOMultimap.put(dto.getLevel(), dto);
            // 取出一级权限模块
            if(LevelUtil.ROOT.equals(dto.getLevel())) {
                rootList.add(dto);
            }
        }

        Collections.sort(rootList, aclModuleLevelDTOComparator);
        transformAclModuleTree(rootList, LevelUtil.ROOT, levelDTOMultimap);
        return rootList;
    }

    public void transformAclModuleTree(List<AclModuleLevelDTO> dtoList, String level, Multimap<String, AclModuleLevelDTO> aclMultimap) {
        for (int i = 0; i < dtoList.size(); i++) {
            AclModuleLevelDTO dto = dtoList.get(i);
            String nextLevel = LevelUtil.getLevel(level, dto.getId());
            List<AclModuleLevelDTO> tempList = (List<AclModuleLevelDTO>)aclMultimap.get(nextLevel);
            if(CollectionUtils.isNotEmpty(tempList)) {
                Collections.sort(tempList, aclModuleLevelDTOComparator);
                dto.setAclModuleList(tempList);
                transformAclModuleTree(tempList, nextLevel, aclMultimap);
            }
        }
    }

    public Comparator<AclModuleLevelDTO> aclModuleLevelDTOComparator = new Comparator<AclModuleLevelDTO>() {
        @Override
        public int compare(AclModuleLevelDTO o1, AclModuleLevelDTO o2) {
            return o1.getSeq() - o2.getSeq();
        }
    };

}
