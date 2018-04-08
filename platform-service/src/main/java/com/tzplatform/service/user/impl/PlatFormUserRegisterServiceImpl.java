package com.tzplatform.service.user.impl;

import com.tzplatform.dao.user.PlatFormUserDao;
import com.tzplatform.dao.user.PlatFormUserRegisterDao;
import com.tzplatform.entity.common.BaseResultDto;
import com.tzplatform.entity.common.CommonEnum;
import com.tzplatform.entity.common.ResultMessage;
import com.tzplatform.entity.user.PlatFormSendMail;
import com.tzplatform.entity.user.PlatFormUser;
import com.tzplatform.service.common.RedisClusterHelper;
import com.tzplatform.service.sendmail.SendEmailHelper;
import com.tzplatform.service.user.PlatFormUserRegisterService;
import com.tzplatform.service.user.PlatFormUserService;
import com.tzplatform.utils.aoplog.AopLog;
import com.tzplatform.utils.common.*;
import com.tzplatform.utils.jwtauth.JwtUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.List;
import java.util.Map;

@Service(value = "platFromUserRegisterService")
public class PlatFormUserRegisterServiceImpl implements PlatFormUserRegisterService {


    Logger logger = Logger.getLogger(PlatFormUserRegisterServiceImpl.class);

    @Resource
    private PlatFormUserDao platFormUserDao;

    @Resource
    private PlatFormUserRegisterDao platFormUserRegisterDao;

    @Resource
    private RedisClusterHelper redisClusterHelper;

    @Value("${tmp.file.path}")
    private String tmppath;

    @Resource
    private SendEmailHelper sendEmailHelper;

    @Resource
    private PlatFormUserService platFormUserService;

    @Value("${email.subject}")
    private String emailSubject;

    @Value("${email.overtime}")
    private String emailOvertime;

    @Value("${email.expireTime}")
    private String emailExpireTime;

    @Value("${email.chenckurl}")
    private String emailCheckUrl;

    private final String VALIDATA_CODE = "validateCode";

    /**
     * 添加开发者
     *
     * @param platFormUser
     * @param idcardfile
     * @param leaderidcardfile
     * @param companycardfile
     * @return
     */
    @Override
    @Transactional
    @AopLog(description = "添加开发者", menuname = "开发者管理")
    public BaseResultDto addUser(PlatFormUser platFormUser, CommonsMultipartFile idcardfile, CommonsMultipartFile leaderidcardfile, CommonsMultipartFile companycardfile) {
        BaseResultDto baseResultDto = new BaseResultDto();
        String userid = UidUtils.getId();
        platFormUser.setId(userid);
        platFormUser.setUsertype(CommonEnum.USER_TYPE.厂商用户.getValue());
        platFormUser.setUserstatus(CommonEnum.USER_STATUS.已激活未审核.getValue());
        String account = platFormUser.getAccount();
        String passsalt = UidUtils.getId();
        String email = platFormUser.getEmail();
        String password = platFormUser.getPassword();

        //判断身份证文件是否为空
        if (null != idcardfile && !idcardfile.isEmpty()) {
            Map<String, Object> resultMap = CommonUtils.upLoadFile(idcardfile, tmppath);
            File file = (File) resultMap.get("file");
            String filePath = resultMap.get("filepath").toString();
            if (null != file) {
                byte[] idcardpic = ByteObjUtils.fileToBytes(file);
                platFormUser.setIdcardpicture(idcardpic);
                platFormUser.setIdcardpicturepath(filePath);
            }
        }

        //判断法人身份证文件是否为空
        if (null != leaderidcardfile && !leaderidcardfile.isEmpty()) {
            Map<String, Object> resultMap = CommonUtils.upLoadFile(leaderidcardfile, tmppath);
            File file = (File) resultMap.get("file");
            String filePath = resultMap.get("filepath").toString();
            if (null != file) {
                byte[] leaderidcardpic = ByteObjUtils.fileToBytes(file);
                platFormUser.setCompanyleadercardtype(leaderidcardpic);
                platFormUser.setCompanyleadercardtypepath(filePath);
            }
        }

        //判断法人身份证文件是否为空
        if (null != companycardfile && !companycardfile.isEmpty()) {
            Map<String, Object> resultMap = CommonUtils.upLoadFile(companycardfile, tmppath);
            File file = (File) resultMap.get("file");
            String filePath = resultMap.get("filepath").toString();
            if (null != file) {
                byte[] cpmpanycardpic = ByteObjUtils.fileToBytes(file);
                platFormUser.setCompanycard(cpmpanycardpic);
                platFormUser.setCompanycardpath(filePath);
            }
        }

        if (StringUtils.isNotBlank(email)) {
            if (StringUtils.isNotBlank(account)) {
                account = email;
            }
            if (StringUtils.isBlank(password)) {
                password = account;
            }
            platFormUser.setPassword(SHA1Util.hex_sha1(password + passsalt));
            platFormUser.setPasssalt(passsalt);
            platFormUser.setAccount(account);
            boolean checkuser = platFormUserService.checkUserInSystem(account, "");
            Integer result = 0;
            if (checkuser) {
                result = platFormUserDao.addUser(platFormUser);
            }
            if (1 == result) {
                baseResultDto.setMsg(ResultMessage.SUCCESS_INSERT_MESSAGE);
                baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
            } else {
                baseResultDto.setCode(ResultMessage.FAILED_CODE);
                baseResultDto.setMsg(ResultMessage.FAILED_INSERT_MESSAGE);
            }
        } else {
            baseResultDto.setCode(ResultMessage.FAILED_CODE);
            baseResultDto.setMsg(ResultMessage.FAILED_EMAIL_NOT_EMPTY);
        }
        return baseResultDto;
    }

    /**
     * 修改开发者
     *
     * @param platFormUser
     * @param idcardfile
     * @param leaderidcardfile
     * @param companycardfile
     * @return
     */
    @Override
    public BaseResultDto editUser(PlatFormUser platFormUser, CommonsMultipartFile idcardfile, CommonsMultipartFile leaderidcardfile, CommonsMultipartFile companycardfile) {
        BaseResultDto baseResultDto = new BaseResultDto();

        //帐号不允许修改
        platFormUser.setAccount("");

        //判断身份证文件是否为空
        if (null != idcardfile && !idcardfile.isEmpty()) {
            Map<String, Object> resultMap = CommonUtils.upLoadFile(idcardfile, tmppath);
            File file = (File) resultMap.get("file");
            String filePath = resultMap.get("filepath").toString();
            if (null != file) {
                byte[] idcardpic = ByteObjUtils.fileToBytes(file);
                platFormUser.setIdcardpicture(idcardpic);
                platFormUser.setIdcardpicturepath(filePath);
            }
        }

        //判断法人身份证文件是否为空
        if (null != leaderidcardfile && !leaderidcardfile.isEmpty()) {
            Map<String, Object> resultMap = CommonUtils.upLoadFile(leaderidcardfile, tmppath);
            File file = (File) resultMap.get("file");
            String filePath = resultMap.get("filepath").toString();
            if (null != file) {
                byte[] leaderidcardpic = ByteObjUtils.fileToBytes(file);
                platFormUser.setCompanyleadercardtype(leaderidcardpic);
                platFormUser.setCompanyleadercardtypepath(filePath);
            }
        }

        //判断法人身份证文件是否为空
        if (null != companycardfile && !companycardfile.isEmpty()) {
            Map<String, Object> resultMap = CommonUtils.upLoadFile(companycardfile, tmppath);
            File file = (File) resultMap.get("file");
            String filePath = resultMap.get("filepath").toString();
            if (null != file) {
                byte[] cpmpanycardpic = ByteObjUtils.fileToBytes(file);
                platFormUser.setCompanycard(cpmpanycardpic);
                platFormUser.setCompanycardpath(filePath);
            }
        }

        Integer result = platFormUserDao.updateUser(platFormUser);
        if (1 == result) {
            baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
            baseResultDto.setMsg(ResultMessage.SUCCESS_INSERT_MESSAGE);
        } else {
            baseResultDto.setCode(ResultMessage.FAILED_CODE);
            baseResultDto.setMsg(ResultMessage.FAILED_INSERT_MESSAGE);
        }
        return baseResultDto;
    }

    /**
     * 查询注册开发者列表
     *
     * @param platFormUser
     * @return
     */
    @Override
    public BaseResultDto listUser(PlatFormUser platFormUser) {
        BaseResultDto baseResultDto = new BaseResultDto();
        List<PlatFormUser> resultList = platFormUserDao.userList(platFormUser);
        Integer resultCount = platFormUserDao.userListCount(platFormUser);
        baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
        baseResultDto.setData(resultList);
        baseResultDto.setTotal(resultCount);
        return baseResultDto;
    }

    /**
     * 开发者详情信息
     *
     * @param platFormUser
     * @return
     */
    @Override
    public BaseResultDto detailUser(PlatFormUser platFormUser) {
        BaseResultDto baseResultDto = new BaseResultDto();
        List<PlatFormUser> resultList = platFormUserDao.userList(platFormUser);
        if (resultList.size() > 0) {

            PlatFormUser resultUser = resultList.get(0);

            //身份证返回信息
            byte[] idcardinfo = resultUser.getIdcardpicture();
            resultUser.setIdcardbase("data:image/png;base64," + ByteObjUtils.byte2Base64StringFun(idcardinfo));

            //营业执照信息
            byte[] companycardinfo = resultUser.getCompanycard();
            resultUser.setCompanycardbase("data:image/png;base64," + ByteObjUtils.byte2Base64StringFun(companycardinfo));

            //法人身份证信息
            byte[] leaderinfo = resultUser.getCompanyleadercardtype();
            resultUser.setCompanyleadercardbase("data:image/png;base64," + ByteObjUtils.byte2Base64StringFun(leaderinfo));

            baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
            baseResultDto.setData(resultUser);
        } else {
            baseResultDto.setCode(ResultMessage.FAILED_CODE);
        }
        return baseResultDto;
    }

    /**
     * 开发者修改密码
     *
     * @param userid
     * @param passwordOld
     * @param passwordNew
     * @return
     */
    @Override
    @AopLog(menuname = "开发者个人中心", description = "修改密码")
    public BaseResultDto updatePassWord(String userid, String passwordOld, String passwordNew) {
        BaseResultDto baseResultDto = new BaseResultDto();
        PlatFormUser user = platFormUserRegisterDao.getUserByUserId(userid, CommonEnum.USER_TYPE.厂商用户.getValue());
        if (user != null) {
            String passsalt = user.getPasssalt();
            String password = user.getPassword();
            String accountid = user.getId();
            //旧密码加密
            String passwordOldSHA = SHA1Util.hex_sha1(passwordOld + passsalt);
            //校验旧密码
            if (password.equals(passwordOldSHA)) {
                String passsaltNew = UidUtils.getId();
                Integer integer = platFormUserDao.changePwdByAccount(SHA1Util.hex_sha1(passwordNew + passsaltNew), passsaltNew, accountid);
                if (1 == integer) {
                    baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
                    baseResultDto.setMsg(ResultMessage.SUCCESS_MESSAGE);
                } else {
                    baseResultDto.setCode(ResultMessage.FAILED_CODE);
                    baseResultDto.setMsg(ResultMessage.FAILED_MESSAGE);
                }
            } else {
                baseResultDto.setCode(ResultMessage.FAILED_CODE);
                baseResultDto.setMsg(ResultMessage.FAILED_USER_OLDPASSWORD_MESSAGE);
            }
        } else {
            baseResultDto.setCode(ResultMessage.FAILED_CODE);
            baseResultDto.setMsg(ResultMessage.FAILED_MESSAGE);
        }
        return baseResultDto;
    }

    /**
     * 快速注册记录发送邮件信息
     *
     * @param sendMail
     * @return
     */
    @Override
    public BaseResultDto addSendMail(PlatFormSendMail sendMail) {
        BaseResultDto baseResultDto = new BaseResultDto();
        Integer count = platFormUserRegisterDao.addSendMail(sendMail);
        if (1 == count) {
            baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
            baseResultDto.setMsg(ResultMessage.SUCCESS_MESSAGE);
        } else {
            baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
            baseResultDto.setMsg(ResultMessage.FAILED_MESSAGE);
        }
        return baseResultDto;
    }

    /**
     * 开发者快速注册--第一步
     *
     * @param platFormUser
     * @param request
     * @return
     */
    @Override
    @AopLog(menuname = "开发者个人中心", description = "快速注册第一步")
    public BaseResultDto registerStepOne(PlatFormUser platFormUser, HttpServletRequest request, String validateCode) {
        BaseResultDto baseResultDto = new BaseResultDto();
        HttpSession session = request.getSession();
        String getValidateCode = (String) session.getAttribute(VALIDATA_CODE);

        logger.debug("register code is " + getValidateCode);

        boolean validatecodeResult = validateCode.equalsIgnoreCase(getValidateCode);

        if (validatecodeResult) {
            String email = platFormUser.getEmail();
            String password = platFormUser.getPassword();
            String passsalt = UidUtils.getId();
            //设置用户信息
            platFormUser.setId(UidUtils.getId());
            platFormUser.setPasssalt(passsalt);
            platFormUser.setPassword(SHA1Util.hex_sha1(password + passsalt));
            platFormUser.setUsertype(CommonEnum.USER_TYPE.厂商用户.getValue());
            platFormUser.setUserstatus(CommonEnum.USER_STATUS.已注册未激活.getValue());
            //用户账号默认为邮箱
            if (StringUtils.isNotBlank(email)) {
                platFormUser.setAccount(email);
            }
            boolean checkuser = platFormUserService.checkUserInSystemStatus(email, null);
            if (checkuser) {
                Integer integer = platFormUserDao.addUser(platFormUser);
                if (1 == integer) {
                    //添加用户成功以后,发送邮箱激活
                    baseResultDto = sendMail(email, request, platFormUser.getId());
                } else {
                    baseResultDto.setCode(ResultMessage.FAILED_CODE);
                    baseResultDto.setMsg(ResultMessage.FAILED_INSERT_MESSAGE);
                }
            }
        } else {
            baseResultDto.setCode(ResultMessage.FAILED_CODE);
            baseResultDto.setMsg(ResultMessage.FAILED_VALIDATECODE_NOTUSER_MESSAGE);
        }
        return baseResultDto;
    }

    /**
     * 开发者快速注册--第二步
     *
     * @param request
     * @return
     */
    @Override
    @AopLog(menuname = "开发者个人中心", description = "快速注册第二步")
    public BaseResultDto registerStepTwo(HttpServletRequest request) {
        BaseResultDto baseResultDto = new BaseResultDto();
        String ticket = request.getParameter("ticket");
        String emailandid = JwtUtils.veriftToken(ticket);
        String accesstoken = redisClusterHelper.getKey(ticket);
        String[] split;
        if (StringUtils.isNotBlank(accesstoken)) {
            split = emailandid.split("-");
            PlatFormUser platFormUser = new PlatFormUser();
            platFormUser.setEmail(split[0]);
            platFormUser.setId(split[1]);
            platFormUser.setUserstatus(CommonEnum.USER_STATUS.已激活未审核.getValue());
            Integer integer = platFormUserDao.updateUser(platFormUser);
            if (1 == integer) {
                //更新激活成功
                redisClusterHelper.delKey(accesstoken);
                baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
                baseResultDto.setMsg(ResultMessage.SUCCESS_MESSAGE);
            } else {
                baseResultDto.setCode(ResultMessage.FAILED_CODE);
                baseResultDto.setMsg(ResultMessage.FAILED_UPDATE_MESSAGE);
            }
        } else {
            //一种超时，一种二次点击
            if (StringUtils.isNotBlank(emailandid)) {
                split = emailandid.split("-");
                //true表示用户不存在已激活为审核的用户,则重发邮件，否则属于二次点击不发送邮件
                boolean checkuser = platFormUserService.checkUserInSystemStatus(split[0], CommonEnum.USER_STATUS.已激活未审核.getValue());
                if (checkuser) {
                    baseResultDto.setCode(ResultMessage.FAILED_CODE);
                    baseResultDto.setMsg(ResultMessage.FAILED_REGISTER_EXPIRETIME_MESSAGE);
                } else {
                    baseResultDto.setCode(ResultMessage.CHECKED_CODE);
                    baseResultDto.setMsg(ResultMessage.FAILED_REGISTER_AREADY_MESSAGE);
                }
            }
        }
        return baseResultDto;
    }

    /**
     * 快速注册--发送邮箱
     *
     * @param platFormUser
     * @param request
     * @return
     */
    @Override
    public BaseResultDto registerSendMail(PlatFormUser platFormUser, HttpServletRequest request) {
        BaseResultDto baseResultDto = new BaseResultDto();
        boolean checkuser = platFormUserService.checkUserInSystemStatus(platFormUser.getEmail(), null);
        //true表示用户不存在
        if (checkuser) {
            baseResultDto.setCode(ResultMessage.FAILED_CODE);
            baseResultDto.setMsg(ResultMessage.FAILED_NO_REGISTER_MESSAGE);
        } else {
            boolean flag = platFormUserService.checkUserInSystemStatus(platFormUser.getEmail(), CommonEnum.USER_STATUS.已注册未激活.getValue());
            //true表示不是已经注册未激活用户,不发送邮件
            if (flag) {
                baseResultDto.setCode(ResultMessage.FAILED_CODE);
                baseResultDto.setMsg(ResultMessage.FAILED_REGISTER_MESSAGE);
            } else {
                List<PlatFormUser> userList = platFormUserDao.userList(platFormUser);
                PlatFormUser user = new PlatFormUser();
                if (userList != null && userList.size() > 0) {
                    user = userList.get(0);
                }
                baseResultDto = this.sendMail(platFormUser.getEmail(), request, user.getId());
            }
        }
        return baseResultDto;
    }

    /**
     * 判断是否可以给用户发送邮件--（不存在/处于已注册未激活状态）可以发送
     *
     * @param platFormUser
     * @return
     */
    @Override
    public BaseResultDto isRegisterSendEmail(PlatFormUser platFormUser) {
        BaseResultDto baseResultDto = new BaseResultDto();
        boolean checkuser = platFormUserService.checkUserInSystemStatus(platFormUser.getEmail(), null);
        //如果为true,则表示用户不存在
        if (checkuser) {
            baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
            baseResultDto.setMsg(ResultMessage.SUCCESS_MESSAGE);
        } else {
            boolean flag = platFormUserService.checkUserInSystemStatus(platFormUser.getEmail(), CommonEnum.USER_STATUS.已注册未激活.getValue());
            //如果为true,则表示用户不是处于已经注册未激活状态
            if (flag) {
                baseResultDto.setCode(ResultMessage.FAILED_CODE);
                baseResultDto.setMsg(ResultMessage.FAILED_REGISTER_MESSAGE);
            } else {
                baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
                baseResultDto.setMsg(ResultMessage.SUCCESS_MESSAGE);
            }
        }
        return baseResultDto;
    }

    /**
     * 判断厂商是否被审核
     *
     * @param email
     * @return
     */
    @Override
    public BaseResultDto isVerifyUser(String email) {
        BaseResultDto baseResultDto = new BaseResultDto();
        boolean flag = platFormUserService.checkUserInSystemStatus(email, CommonEnum.USER_STATUS.启用.getValue());
        //如果为true，则表示还未被审核
        if (flag) {
            baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
        } else {
            baseResultDto.setCode(ResultMessage.FAILED_CODE);
        }
        return baseResultDto;
    }

    @Override
    public BaseResultDto isRegisterUser(String account, String email) {
        BaseResultDto baseResultDto = new BaseResultDto();
        boolean checkuser = platFormUserService.checkUserInSystem(account, email);
        if(checkuser){
            baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
        }else{
            baseResultDto.setCode(ResultMessage.FAILED_CODE);
        }
        return baseResultDto;
    }

    /**
     * 发送邮箱
     *
     * @param email
     * @param request
     * @param id
     * @return
     */
    private BaseResultDto sendMail(String email, HttpServletRequest request, String id) {
        BaseResultDto baseResultDto = new BaseResultDto();
        if (StringUtils.isNotBlank(email)) {
            String access_token = JwtUtils.createToken(email + "-" + id);
            //将token存入缓存中
            Integer expireTime = Integer.parseInt(emailExpireTime);
            boolean result = redisClusterHelper.setKey(access_token, access_token, expireTime);
            if (result) {
                //发送邮件
                String resetPassHref = emailCheckUrl + "?ticket=" + access_token;
                StringBuffer stringBuffer = new StringBuffer("尊敬的开发者您好,感谢您注册开放平帐号<br/>");
                stringBuffer.append("您的登录账号为:").append(email + "<br/>");
                stringBuffer.append("请点击以下链接激活帐号<br/>");
                stringBuffer.append("<a href='" + resetPassHref + "' target='_blank'>点击此处激活开放平台帐号</a><br/>");
                stringBuffer.append("如果以上链接无法点击，请将上面的地址复制到您的浏览器(如IE)的地址栏进入通州云开放平台。");
                stringBuffer.append("(本邮件超过'" + Integer.parseInt(emailOvertime) + "'分钟,链接将会失效，需要重新申请)");

                String content = "";
                sendEmailHelper.sendEmail(email, emailSubject, content, stringBuffer.toString());
                baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
                baseResultDto.setMsg(ResultMessage.SUCCESS_MESSAGE);
            } else {
                baseResultDto.setCode(ResultMessage.FAILED_CODE);
                baseResultDto.setMsg(ResultMessage.FAILED_MESSAGE);
            }
        }
        return baseResultDto;
    }
}
