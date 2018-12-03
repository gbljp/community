package com.anjoy.cloud.component.utils;

import org.apache.commons.lang.StringUtils;

/**
 * 验证工具
 */
public class ValidateUtil {

    /**
     * 验证是否是合乎规则要求的密码
     *
     * @param password 【密码】：6-16位（数字+字母
     * @return
     */
    public static boolean isPassword(String password) {
        if (StringUtils.isBlank(password)) {
            throw new IllegalArgumentException("The argument cannot be null");
        }
        //正则表达式
        String reg = "^[a-zA-Z0-9]{6,16}$";
        return password.matches(reg);
    }
}