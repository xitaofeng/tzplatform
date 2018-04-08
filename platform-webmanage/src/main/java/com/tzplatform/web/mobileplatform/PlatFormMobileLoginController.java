package com.tzplatform.web.mobileplatform;

import com.tzplatform.entity.common.BaseResultDto;
import com.tzplatform.service.tokenIpfilter.AccessTokenIp;
import com.tzplatform.service.tokenfilter.AccessToken;
import com.tzplatform.service.user.PlatFormMobileService;
import com.tzplatform.service.user.PlatFormSysUserLoginService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 移动端登录
 */
@RestController
@RequestMapping(value = "/platformMobileLogin")
public class PlatFormMobileLoginController {

    @Resource
    private PlatFormSysUserLoginService platFormSysUserLoginService;

    @Resource
    private PlatFormMobileService platFormMobileService;

    /**
     * 移动端登录
     *
     * @param account
     * @param password
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/mobileLogin", produces = "application/json;charset=utf-8")
    public BaseResultDto mobileLogin(String account, String password, HttpServletRequest request) {
        return platFormSysUserLoginService.mobileLogin(account, password, request);
    }

    /**
     * 微信登录
     *
     * @param weixin
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/mobieUserByWeiXin", produces = "application/json;charset=utf-8")
    public BaseResultDto mobieUserByWeiXin(String weixin, HttpServletRequest request) {
        return platFormSysUserLoginService.mobieUserByWeiXin(weixin, request);
    }

    /**
     * qq登录
     *
     * @param qq
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/mobieUserByQQ", produces = "application/json;charset=utf-8")
    public BaseResultDto mobieUserByQQ(String qq, HttpServletRequest request) {
        return platFormSysUserLoginService.mobieUserByQQ(qq, request);
    }

    /**
     * 微博登录
     *
     * @param weibo
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/mobieUserByWeiBo", produces = "application/json;charset=utf-8")
    public BaseResultDto mobieUserByWeiBo(String weibo, HttpServletRequest request) {
        return platFormSysUserLoginService.mobieUserByWeiBo(weibo, request);
    }

    /**
     * 移动端修改密码
     *
     * @param account
     * @param passwordold
     * @param passwordnew
     * @return
     */
    @AccessToken
    @RequestMapping(method = RequestMethod.POST, value = "/updateUserPassword", produces = "application/json;charset=utf-8")
    public BaseResultDto updateUserPassword(String account, String passwordold, String passwordnew) {
        return platFormMobileService.updateUserPassword(account, passwordold, passwordnew);
    }

    /**
     * 教育云修改密码
     *
     * @param account
     * @param passwordold
     * @param passwordnew
     * @return
     */
    @AccessTokenIp
    @RequestMapping(method = RequestMethod.POST, value = "/jyychangepwd", produces = "application/json;charset=utf-8")
    public BaseResultDto updateTzjyyUserPassword(String account, String passwordold, String passwordnew) {
        return platFormMobileService.updateTzjyyUserPassword(account, passwordold, passwordnew);
    }

    /**
     * 发送短信
     *
     * @param telphone
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/sendSms", produces = "application/json;charset=utf-8")
    public BaseResultDto sendSms(String telphone) {
        return platFormSysUserLoginService.sendSms(telphone);
    }

    /**
     * 手机登录
     *
     * @param telphone
     * @param captcha
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/telphoneLogin", produces = "application/json;charset=utf-8")
    public BaseResultDto telphoneLogin(String telphone, String captcha) {
        return platFormSysUserLoginService.telphoneLogin(telphone, captcha);
    }


    /**
     * 后台和移动端登出
     *
     * @param request
     * @return
     */
    @AccessToken
    @RequestMapping(method = RequestMethod.POST, value = "/userLogout", produces = "application/json;charset=utf-8")
    public BaseResultDto userLogout(HttpServletRequest request) {
        return platFormSysUserLoginService.userLogout(request);
    }



    /**
     * 校验token合法性
     * @param token
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/checkToken", produces = "application/json;charset=utf-8")
    public BaseResultDto checkTokenResult(String token) {
        return platFormMobileService.checkTokenResult(token);
    }


}
