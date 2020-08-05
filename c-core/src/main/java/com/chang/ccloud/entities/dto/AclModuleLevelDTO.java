package com.chang.ccloud.entities.dto;

import com.chang.ccloud.model.SysAclModule;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import java.util.List;

/**
 * @Author changxizhao
 * @Date 2020/8/5 8:44
 * @Description
 */
public class AclModuleLevelDTO extends SysAclModule {

    private List<AclModuleLevelDTO> aclModuleList = Lists.newArrayList();

    public static AclModuleLevelDTO adept(SysAclModule aclModule) {
        AclModuleLevelDTO dto = new AclModuleLevelDTO();
        BeanUtils.copyProperties(aclModule, dto);
        return dto;
    }

    public List<AclModuleLevelDTO> getAclModuleList() {
        return aclModuleList;
    }

    public void setAclModuleList(List<AclModuleLevelDTO> aclModuleList) {
        this.aclModuleList = aclModuleList;
    }
}
