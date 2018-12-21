package com.anjoy.cloud.component.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class StringUtil {

    private static Log log = LogFactory.getLog(StringUtil.class.getName());


    private static final String hexDigits[] = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    /**
     * 返回字符串，如果为空，则返回“”
     *
     * @param str
     */
    public static String geStr(String str) {
        if (str == null) {
            return "";
        } else {
            return str;
        }
    }

    /**
     * 将字节串转化成
     *
     * @param b
     * @return
     */
    public static String byteArrayToHexString(byte b[]) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++)
            resultSb.append(byteToHexString(b[i]));
        return resultSb.toString();
    }


    public static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n += 256;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }


    public static boolean isLinux() {
        String sys = System.getProperties().getProperty("os.name").toLowerCase();
        if (sys.startsWith("windows")) {
            return false;
        } else {
            return true;
        }
    }
    //首字母转小写
    public static String toLowerCaseFirstOne(String s)
    {
        if(Character.isLowerCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
    }
    //首字母转大写
    public static String toUpperCaseFirstOne(String s)
    {
        if(Character.isUpperCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
    }
}
