package com.tzplatform.web.user;

import com.tzplatform.entity.common.BaseResultDto;
import com.tzplatform.entity.common.CommonEnum;
import com.tzplatform.entity.user.PlatFormUser;
import com.tzplatform.service.tokenfilter.WebAccessToken;
import com.tzplatform.service.user.PlatFormUserRegisterService;
import com.tzplatform.utils.jwtauth.JwtUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/platformUserRegister")
public class PlatFromUserRegisterController {
    @Resource
    private PlatFormUserRegisterService platFromUserRegisterService;


    /**
     * 查询开发者详情
     *
     * @param platFormUser
     * @return
     */
    @WebAccessToken
    @RequestMapping(method = RequestMethod.POST, value = "/registerDetailUser", produces = "application/json;charset=utf-8")
    public BaseResultDto registerDetailUser(PlatFormUser platFormUser, HttpServletRequest request) {
        String userid = request.getHeader(CommonEnum.HEAD_PARAM.ACCOUNT.getValue());
        platFormUser.setUsertype(CommonEnum.USER_TYPE.厂商用户.getValue());
        platFormUser.setId(userid);
        return platFromUserRegisterService.detailUser(platFormUser);
    }

    /**
     * 修改开发者详情
     *
     * @param platFormUser
     * @return
     */
    @WebAccessToken
    @RequestMapping(method = RequestMethod.POST, value = "/editRegisterUser", produces = "application/json;charset=utf-8")
    public BaseResultDto editRegisterUser(PlatFormUser platFormUser, HttpServletRequest request,
                                          @RequestParam(value = "idcardfile", required = false) CommonsMultipartFile idcardfile,
                                          @RequestParam(value = "leaderidcardfile", required = false) CommonsMultipartFile leaderidcardfile,
                                          @RequestParam(value = "companycardfile", required = false) CommonsMultipartFile companycardfile) {
        String userid = request.getHeader(CommonEnum.HEAD_PARAM.ACCOUNT.getValue());
        platFormUser.setId(userid);
        return platFromUserRegisterService.editUser(platFormUser, idcardfile, leaderidcardfile, companycardfile);
    }

    /**
     * 修改用户密码
     *
     * @param passwordOld
     * @param passwordNew
     * @return
     */
    @WebAccessToken
    @RequestMapping(value = "/updateUserPassword", produces = "application/json;charset=utf-8")
    public BaseResultDto updateUserPassword(String passwordOld, String passwordNew, HttpServletRequest request) {
        String userid = request.getHeader(CommonEnum.HEAD_PARAM.ACCOUNT.getValue());
        return platFromUserRegisterService.updatePassWord(userid, passwordOld, passwordNew);
    }

    /**
     * 判断厂商是否审核过
     * @param request
     * @return
     */
    @WebAccessToken
    @RequestMapping(value = "/isVerifyUser",produces = "application/json;charset=utf-8")
    public BaseResultDto isVerifyUser(HttpServletRequest request){
        String token=request.getHeader(CommonEnum.HEAD_PARAM.TOKEN.getValue());
        String email = JwtUtils.veriftToken(token);
        return platFromUserRegisterService.isVerifyUser(email);
    }


    /**
     * 开发者注册--快速注册第一步
     *
     * @param platFormUser
     * @param request
     * @return
     */
    @RequestMapping(value = "/registerStepOne", produces = "application/json;charset=utf-8")
    public BaseResultDto registerStepOne(PlatFormUser platFormUser, HttpServletRequest request, String validateCode) {
        return platFromUserRegisterService.registerStepOne(platFormUser, request, validateCode);
    }
    /**
     * 开发者注册--快速注册第二步
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/registerStepTwo", produces = "application/json;charset=utf-8")
    public BaseResultDto registerStepTwo(HttpServletRequest request) {
        return platFromUserRegisterService.registerStepTwo(request);
    }

    /**
     * 开发者注册--快速注册发送邮箱验证
     * @param platFormUser
     * @param request
     * @return
     */
    @RequestMapping(value = "/registerSendMail",produces = "application/json;charset=utf-8")
    public BaseResultDto registerSendMail(PlatFormUser platFormUser,HttpServletRequest request){
        return platFromUserRegisterService.registerSendMail(platFormUser,request);
    }

    /**
     * 开发者注册--快速注册判断是否已经存在的用户
     * @return
     */
    @RequestMapping(value = "/isRegisterSendEmail",produces = "application/json;charset=utf-8")
    public BaseResultDto isRegisterSendEmail(PlatFormUser platFormUser){
        return platFromUserRegisterService.isRegisterSendEmail(platFormUser);
    }
}
