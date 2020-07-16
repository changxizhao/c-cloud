package com.chang.ccloud.common.utils;

import java.util.Random;

/**
 * @Author changxizhao
 * @Date 2020/7/16 9:20
 * @Description
 */
public class PasswordUtil {

    private static final String[] words = {
            "a", "b", "c", "d", "e", "f",
            "g", "h", "j", "k", "m", "n",
            "p", "q", "r", "s", "t", "u",
            "v", "w", "x", "y", "z", "A",
            "B", "C", "D", "E", "F", "G",
            "H", "J", "K", "M", "N", "P",
            "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z"
    };

    private static final String[] nums = {
            "2", "3", "4", "5",
            "6", "7", "8", "9"
    };

    public  static String randomPwd() {
        StringBuffer stringBuffer = new StringBuffer();
        Random random = new Random(DateUtil.getNowDate().getTime());
        int length = random.nextInt(3) + 8;
        boolean flag = false;
        for (int i = 0; i < length; i++) {
            if(flag) {
                stringBuffer.append(nums[random.nextInt(nums.length)]);
            }else {
                stringBuffer.append(words[random.nextInt(words.length)]);
            }
            flag = !flag;
        }
        return stringBuffer.toString();
    }

    public static void main(String[] args) {
        System.out.println(randomPwd());
    }
}
