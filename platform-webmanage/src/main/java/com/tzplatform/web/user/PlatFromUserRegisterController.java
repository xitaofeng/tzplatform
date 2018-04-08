package com.tzplatform.web.user;

import com.tzplatform.entity.common.BaseResultDto;
import com.tzplatform.entity.common.CommonEnum;
import com.tzplatform.entity.common.ResultMessage;
import com.tzplatform.entity.user.PlatFormSendMail;
import com.tzplatform.entity.user.PlatFormUser;
import com.tzplatform.service.sendmail.SendEmailHelper;
import com.tzplatform.service.user.PlatFormUserRegisterService;
import com.tzplatform.service.user.PlatFormUserService;
import com.tzplatform.utils.common.InetAddressUtil;
import com.tzplatform.utils.common.SHA1Util;
import com.tzplatform.utils.common.UidUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;

@RestController
@RequestMapping(value = "/platformUserRegister")
public class PlatFromUserRegisterController {
    @Resource
    private PlatFormUserRegisterService platFromUserRegisterService;


    /**
     * 系统注册开发者
     *
     * @param platFormUser
     * @param idcardfile
     * @param leaderidcardfile
     * @param companycardfile
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/addRegisterUser", produces = "application/json;charset=utf-8")
    public BaseResultDto addRegisterUser(PlatFormUser platFormUser,
                                         @RequestParam(value = "idcardfile", required = false) CommonsMultipartFile idcardfile,
                                         @RequestParam(value = "leaderidcardfile", required = false) CommonsMultipartFile leaderidcardfile,
                                         @RequestParam(value = "companycardfile", required = false) CommonsMultipartFile companycardfile) {

        return platFromUserRegisterService.addUser(platFormUser, idcardfile, leaderidcardfile, companycardfile);
    }
    /**
     * 判断该账号是否已经注册过
     * @param account
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "/isRegisterUser",produces = "application/json;charset=utf-8")
    public BaseResultDto isRegisterUser(@RequestParam(value = "account",required = false) String account, @RequestParam(value = "email",required = false) String email){
        return platFromUserRegisterService.isRegisterUser(account,email);
    }

    /**
     * 查询开发者列表
     *
     * @param platFormUser
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/registerUserList", produces = "application/json;charset=utf-8")
    public BaseResultDto registerUserList(PlatFormUser platFormUser) {
        return platFromUserRegisterService.listUser(platFormUser);

    }

    /**
     * 查询开发者详情
     *
     * @param platFormUser
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/registerDetailUser", produces = "application/json;charset=utf-8")
    public BaseResultDto registerDetailUser(PlatFormUser platFormUser) {
        return platFromUserRegisterService.detailUser(platFormUser);

    }

    /**
     * 修改开发者详情
     *
     * @param platFormUser
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/editRegisterUser", produces = "application/json;charset=utf-8")
    public BaseResultDto editRegisterUser(PlatFormUser platFormUser,
                                          @RequestParam(value = "idcardfile", required = false) CommonsMultipartFile idcardfile,
                                          @RequestParam(value = "leaderidcardfile", required = false) CommonsMultipartFile leaderidcardfile,
                                          @RequestParam(value = "companycardfile", required = false) CommonsMultipartFile companycardfile) {
        return platFromUserRegisterService.editUser(platFormUser, idcardfile, leaderidcardfile, companycardfile);
    }

}
