package com.anjoy.frozen.seller.controller.login;

import com.anjoy.frozen.seller.controller.base.BaseController;
import com.anjoy.frozen.seller.entity.Users;
import com.anjoy.frozen.seller.result.JsonResult;
import com.anjoy.frozen.seller.result.JsonResultCode;
import com.anjoy.frozen.seller.service.UsersService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 *  卖家登陆
 */
@RestController
@RequestMapping("/seller")
public class LoginController extends BaseController
{

    private static final Logger logger=LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UsersService usersService;

    /**
     * 卖家登陆
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/login", method = { RequestMethod.GET, RequestMethod.POST })
    public JsonResult login(HttpServletRequest request, HttpServletResponse response, String account, String password)
    {
        logger.info("[LoginController][login] account："+ account +" password:"+password);

        //账号,目前用手机号码作为登陆的账号
        if(StringUtils.isBlank(account)||StringUtils.isBlank(password))
        {
            return new JsonResult(JsonResultCode.FAILURE,"账号或者密码为空","");
        }

        try
        {
            //获取对象
            Users u=this.usersService.findUserByAccount(account);

            if(u==null)
            {
                return new JsonResult(JsonResultCode.FAILURE,"账号不存在,请重新输入","");
            }

            String pwd=u.getPassword();

            //对比密码
            if(!password.equalsIgnoreCase(pwd))
            {
                return new JsonResult(JsonResultCode.FAILURE,"密码输入有误，请重试","");
            }

            return new JsonResult(JsonResultCode.SUCCESS,"登陆成功",u);

        }catch(Exception ex)
        {
            logger.error("[LoginController][login] exception :",ex);
            return new JsonResult(JsonResultCode.FAILURE, "系统错误,请稍后重试","");
        }
    }
}
