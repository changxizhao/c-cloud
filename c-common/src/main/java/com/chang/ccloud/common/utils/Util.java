package com.chang.ccloud.common.utils;

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



}
