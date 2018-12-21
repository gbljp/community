package com.anjoy.cloud.component.token;

import com.alibaba.fastjson.JSONObject;

/*
 * wuhy 20180509
 * apiToken实例结构，用于验证通过后存储用户的基本信息
 * 为了实现便捷访问与序列化存储redis的平衡点，整个对象基于阿里巴巴的jsonobject之上进行独立封装
 * 对象既可以方便的进行序列化存储redis，也可以方便的进行扩展和访问
 * */
public class ApiToken extends JSONObject {

    // 登录名
    private static String loginNameKey = "loginName";

    // 用户ID
    private static String userIdKey = "userId";

    //登陆时间
    private static String loginTimeKey = "loginTime";

    //失效时间
    private static String endTimeKey = "endTime";


    public String getLoginName() {
        return getString(loginNameKey);
    }

    public void setLoginName(String loginNameValue) {
        put(loginNameKey, loginNameValue);
    }

    public String getUserId() {
        return getString(userIdKey);
    }

    public void setUserId(String userIdValue) {
        put(userIdKey, userIdValue);
    }

    public String getLoginTime() {
        return getString(loginTimeKey);
    }

    public void setLoginTime(String loginTimeValue) {
        put(loginTimeKey, loginTimeValue);
    }

    public String getEndTime() {
        return getString(endTimeKey);
    }

    public void setEndTime(String endTimeValue) {
        put(endTimeKey, endTimeValue);
    }

}
