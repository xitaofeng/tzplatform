package com.tzplatform.web.user;

import com.tzplatform.entity.common.BaseResultDto;
import com.tzplatform.service.tokenfilter.AccessToken;
import com.tzplatform.service.user.PlatFormWebUserLoginService;
import com.tzplatform.utils.jwtauth.JwtUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/platformWebUserLogin")
public class PlatFormWebUserLoginController {


    @Resource
    private PlatFormWebUserLoginService platFormWebUserLoginService;

    /**
     * 开发者登录
     *
     * @param account
     * @param password
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/webUserLogin", produces = "application/json;charset=utf-8")
    public BaseResultDto webUserLogin(String account, String password, HttpServletRequest request) {
        return platFormWebUserLoginService.webUserLogin(account, password, request);
    }

    /**
     * 验证用户token
     *
     * @param token
     * @param accountid
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/verifyUserToken", produces = "application/json;charset=utf-8")
    public BaseResultDto verifyUserToken(String token, String accountid) {
        return platFormWebUserLoginService.verifyUserToken(token, accountid);
    }


    /**
     * 用户登出
     *
     * @param request
     * @return
     */
    @AccessToken
    @RequestMapping(method = RequestMethod.POST, value = "/webUserLogout", produces = "application/json;charset=utf-8")
    public BaseResultDto webUserLogout(HttpServletRequest request) {
        return platFormWebUserLoginService.webUserLogout(request);
    }

    /**
     * 判断用户是否存在
     *
     * @param account
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/isWebUser", produces = "application/json;charset=utf-8")
    public BaseResultDto isWebUser(String account) {
        return platFormWebUserLoginService.isWebUser(account);
    }

    /**
     * 修改密码发送邮件
     */
    @RequestMapping(method = RequestMethod.POST, value = "/webSendEmail", produces = "application/json;charset=utf-8")
    public BaseResultDto webSendEmail(String account, String validateCode, HttpServletRequest request) {
        return platFormWebUserLoginService.webSendEmail(account, validateCode, request);
    }

    /**
     * 修改密码
     *
     * @param ticket
     * @param passwordOld
     * @param passwordNew
     * @param ticket
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/webUpdatePassword", produces = "application/json;charset=utf-8")
    public BaseResultDto webUpdatePassword(String passwordOld, String passwordNew, String ticket) {
        String account = JwtUtils.veriftToken(ticket);
        return platFormWebUserLoginService.webUpdatePassword(account, passwordOld, passwordNew, ticket);
    }
}
