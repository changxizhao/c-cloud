package com.chang.ccloud.common.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * 部门级别生成工具类
 * @Author changxizhao
 * @Date 2020/7/5 17:45
 * @Description
 */
public class LevelUtil {

    public static final String SEPARATOR = ".";

    public static final String ROOT = "0";

    public static String getLevel(String parentLevel, Long parentId) {
        if(StringUtils.isBlank(parentLevel)) {
            return ROOT;
        }else {
            return StringUtils.join(parentLevel, SEPARATOR, parentId);
        }
    }
}
