package com.anjoy.cloud.component.token;

import com.alibaba.fastjson.JSON;
import com.anjoy.cloud.component.exception.ServiceException;
import com.anjoy.cloud.component.result.JsonResultCode;
import com.anjoy.cloud.component.utils.CryptoHelper;
import com.anjoy.cloud.component.utils.DateUtil;
import org.apache.commons.lang.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * wuhy 20181129
 * token基本服务提供类
 *
 * */
public class TokenService {


    private static long tockenLifeTime = (((((long) 90 * 24) * 60) * 60) * 1000); // 默认令牌的生命周期为90天

    /*
     *
     * 新建token   wuhy
     * */
    public static ApiToken newApiToken(String LoginName, String userId, String endTime){
        ApiToken apiToken = new ApiToken();
        apiToken.setLoginTime(new SimpleDateFormat(DateUtil.FMT_DATETIME_COMPACT).format(new Date()));
        apiToken.setUserId(userId);
        apiToken.setLoginName(LoginName);
        if (endTime != null){
            apiToken.setEndTime(endTime);
        }
        return apiToken;
    }

    /*
     *  生成token  wuhy
     * */
    public static String generateToken(ApiToken apiToken) throws Exception {
        //生成发放到客户端的token
        String tokenStr = "";
        //拼接规则：用户id+syscode+uuid，中间用预定义的分隔符分离
        tokenStr = apiToken.toJSONString();
        //aes256md5加密
        tokenStr = encryptTokenStr(tokenStr);

        return tokenStr;
    }

    /*
     *
     * 校验token  wuhy
     * */

    public static String verifyToken(String tokenStr) throws Exception {
        String apiTokenStr = null;
        try {
            apiTokenStr = decryptTokenStr(tokenStr);
            if (apiTokenStr == null){
                throw new ServiceException(JsonResultCode.NEEDLOGIN,"您的登陆信息有误，请重新登陆");
            }
        } catch (Exception e) {
            throw new ServiceException(JsonResultCode.NEEDLOGIN,"您的登陆信息有误，请重新登陆");
        }
        ApiToken apiToken = JSON.parseObject(apiTokenStr,ApiToken.class);

        //校验登陆时间
        Date logindate = new SimpleDateFormat(DateUtil.FMT_DATETIME_COMPACT).parse(apiToken.getLoginTime());
        Date currentdate = new Date();
        if (!StringUtils.isBlank(apiToken.getEndTime()) ){
            //按照签发token时候的许可日期来判断
            if (new SimpleDateFormat(DateUtil.FMT_DATETIME_COMPACT).parse(apiToken.getEndTime()).before(currentdate)) {
                //登陆过期，需要重新登陆
                throw new ServiceException(JsonResultCode.NEEDLOGIN, "您的登陆已过期，请重新登陆");
            }
        }else {
            //按照当前日期来判断
            if (tockenLifeTime <= (currentdate.getTime() - logindate.getTime())) {
                //登陆过期，需要重新登陆
                throw new ServiceException(JsonResultCode.NEEDLOGIN, "您的登陆已过期，请重新登陆");
            }
        }
        return apiToken.getUserId();

    }

    //token加密
    private static String encryptTokenStr(String sourcestr) throws Exception {
        return CryptoHelper.aesMd5Encrypt(sourcestr);
    }

    //token解密
    private static String decryptTokenStr(String sourcestr) throws Exception {
        return CryptoHelper.aesMd5Decrypt(sourcestr);
    }
}
