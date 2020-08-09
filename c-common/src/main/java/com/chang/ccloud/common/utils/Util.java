package com.chang.ccloud.common.utils;

import com.google.common.base.Splitter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author changxizhao
 * @Date 2020/8/7 9:38
 * @Description
 */
public class Util {

    public static boolean equalsTo(Integer a, Integer b) {
        if(a == null || a == null) {
            return false;
        }
        return a - b == 0;
    }

    public static boolean greaterThan(Integer a, Integer b) {
        if(a == null || a == null) {
            return false;
        }
        return a - b > 0;
    }

    public static boolean lessThan(Integer a, Integer b) {
        if(a == null || a == null) {
            return false;
        }
        return a - b < 0;
    }


    public static List<Integer> stringSplitToListInt(String str) {
        List<String> strList = Splitter.on(",").trimResults().omitEmptyStrings().splitToList(str);
        List<Integer> list = strList.stream().map(strStream -> Integer.parseInt(str)).collect(Collectors.toList());
        return list;
    }

    public static boolean equalsTo(Long a, Long b) {
        if(a == null || a == null) {
            return false;
        }
        return a - b == 0;
    }

}
