package com.chang.ccloud.common.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @Author changxizhao
 * @Date 2020/7/16 10:05
 * @Description
 */
public class MD5Util {

    private static final String SALT = "c-cloud";

    // 带秘钥加密
    public static String encode(String text, String salt) {
        return DigestUtils.md5Hex(text + salt);
    }

    public static String encode(String text) {
        return encode(text, SALT);
    }


    // 根据传入的密钥进行验证
    public static boolean verify(String text, String salt, String md5) {
        String md5str = encode(text, salt);
        if (md5str.equalsIgnoreCase(md5)) {
            return true;
        }
        return false;
    }

    public static boolean verify(String text, String md5) {
        String md5str = encode(text, SALT);
        if (md5str.equalsIgnoreCase(md5)) {
            return true;
        }
        return false;
    }

    // 测试
    public static void main(String[] args) {

        String str = "181115.040908.10.88.181.118.3013.1655327821_1";

        System.out.println(MD5Util.encode(str));
    }
}
