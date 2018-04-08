package com.tzplatform.service.user.impl;

import com.tzplatform.dao.user.PlatFormSysUserLoginDao;
import com.tzplatform.dao.user.PlatFormUserDao;
import com.tzplatform.entity.common.BaseResultDto;
import com.tzplatform.entity.common.CommonEnum;
import com.tzplatform.entity.common.ResultMessage;
import com.tzplatform.entity.system.PlatFormNormalLog;
import com.tzplatform.entity.user.PlatFormUser;
import com.tzplatform.service.common.RedisClusterHelper;
import com.tzplatform.service.sendmail.SendEmailHelper;
import com.tzplatform.service.system.PlatFormLogService;
import com.tzplatform.service.user.PlatFormWebUserLoginService;
import com.tzplatform.utils.aoplog.AopLog;
import com.tzplatform.utils.common.CommonUtils;
import com.tzplatform.utils.common.SHA1Util;
import com.tzplatform.utils.common.UidUtils;
import com.tzplatform.utils.jwtauth.JwtUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service(value = "platFormWebUserLoginService")
public class PlatFormWebUserLoginServiceImpl implements PlatFormWebUserLoginService {

    @Resource
    private PlatFormSysUserLoginDao platFormSysUserLoginDao;

    @Resource
    private PlatFormUserDao platFormUserDao;

    @Resource
    private RedisClusterHelper redisClusterHelper;

    @Resource
    private SendEmailHelper sendEmailHelper;


    @Value("${email.overtime}")
    private String emailOvertime;

    @Value("${email.expireTime}")
    private String emailExpireTime;

    @Value("${email.subjectpwd}")
    private String emailSubject;

    @Value("${email.chenckurlpwd}")
    private String emailCheckUrl;
    @Resource
    private PlatFormLogService platFormLogService;

    @Value("${user.token.expireTime}")
    private String loginExpireTime;

    private final String USER_TOKEN_KEY = "token";

    private final String USER_TOKEN_ACCOUNT = "accountid";

    private final String VALIDATA_CODE = "validateCode";

    private final String USER_EMAIL="useremail";


    /**
     * 用户登录逻辑
     *
     * @param account
     * @param password
     * @return
     */
    @Override
    public BaseResultDto webUserLogin(String account, String password, HttpServletRequest request) {
        BaseResultDto baseResultDto = new BaseResultDto();
        if (StringUtils.isNotBlank(account) && StringUtils.isNotBlank(password)) {
            PlatFormUser platFormUser = platFormSysUserLoginDao.getUserByAccount(account, CommonEnum.USER_TYPE.厂商用户.getValue());
            if (null != platFormUser) {
                String passdatabase = platFormUser.getPassword();
                String passsalt = platFormUser.getPasssalt();
                String getpassword = SHA1Util.hex_sha1(password + passsalt);
                if (passdatabase.equals(getpassword)) {
                    String accountid = platFormUser.getId();
                    //密码验证成功生成token
                    String token = JwtUtils.createUserToken(account,accountid);
                    //向缓存写入token 并设置有效时间
                    Integer expireTime = Integer.parseInt(loginExpireTime);

                    boolean setkeyresult = redisClusterHelper.setKey(token, accountid, expireTime);
                    if (setkeyresult) {
                        Map<String, Object> resultMap = new HashMap<>();
                        resultMap.put(USER_TOKEN_KEY, token);
                        resultMap.put(USER_TOKEN_ACCOUNT, accountid);
                        resultMap.put(USER_EMAIL, account);
                        baseResultDto.setData(resultMap);
                        baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
                        baseResultDto.setMsg(ResultMessage.SUCCESS_LOGIN_MESSAGE);
                    } else {
                        baseResultDto.setCode(ResultMessage.FAILED_CODE);
                        baseResultDto.setMsg(ResultMessage.FAILED_LOGIN_TOKENERROR_MESSAGE);
                    }

                    /*boolean findKeyResult = redisClusterHelper.exists(accountid);
                    if (findKeyResult) {
                        token = redisClusterHelper.getKey(accountid);
                        redisClusterHelper.setKeyExpire(accountid, Integer.parseInt(loginExpireTime));
                        Map<String, Object> resultMap = new HashMap<>();
                        resultMap.put(USER_TOKEN_KEY, token);
                        resultMap.put(USER_TOKEN_ACCOUNT, accountid);
                        baseResultDto.setData(resultMap);
                        baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
                        baseResultDto.setMsg(ResultMessage.SUCCESS_LOGIN_MESSAGE);
                    } else {
                        token = JwtUtils.createToken(accountid);
                        boolean result = redisClusterHelper.setKey(accountid, token, expireTime);
                        if (result) {
                            Map<String, Object> resultMap = new HashMap<>();
                            resultMap.put(USER_TOKEN_KEY, token);
                            resultMap.put(USER_TOKEN_ACCOUNT, accountid);
                            baseResultDto.setData(resultMap);
                            baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
                            baseResultDto.setMsg(ResultMessage.SUCCESS_LOGIN_MESSAGE);
                        } else {
                            baseResultDto.setCode(ResultMessage.FAILED_CODE);
                            baseResultDto.setMsg(ResultMessage.FAILED_LOGIN_TOKENERROR_MESSAGE);
                        }
                    }*/
                } else {
                    baseResultDto.setCode(ResultMessage.FAILED_CODE);
                    baseResultDto.setMsg(ResultMessage.FAILED_LOGIN_PWDERROR_MESSAGE);
                }
                String handleresult = "";
                if ("1".equals(baseResultDto.getCode())) {
                    handleresult = CommonEnum.HANDLE_REUSLT.success.getValue();
                } else {
                    handleresult = CommonEnum.HANDLE_REUSLT.failed.getValue();
                }
                String handleip = CommonUtils.getRemoteAddress(request);
                PlatFormNormalLog platFormNormalLog = new PlatFormNormalLog(UidUtils.getId(), account, "", "用户登录", handleip, "开发者登录", handleresult, new Date(), new Date());
                platFormLogService.insertNormalLog(platFormNormalLog);
            } else {
                baseResultDto.setCode(ResultMessage.FAILED_CODE);
                baseResultDto.setMsg(ResultMessage.FAILED_LOGIN_NOTUSER_MESSAGE);
            }
        } else {
            baseResultDto.setCode(ResultMessage.FAILED_CODE);
            baseResultDto.setMsg(ResultMessage.FAILED_LOGIN_NULL_MESSAGE);
        }

        return baseResultDto;
    }

    /**
     * 校验用户登录token
     *
     * @param token
     * @param accountid
     * @return
     */
    @Override
    public BaseResultDto verifyUserToken(String token, String accountid) {
        BaseResultDto baseResultDto = new BaseResultDto();
        if (StringUtils.isNotBlank(token) && StringUtils.isNotBlank(accountid)) {
            if (redisClusterHelper.exists(accountid)) {
                String getToken = redisClusterHelper.getKey(accountid);
                if (token.equals(getToken)) {
                    baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
                    baseResultDto.setMsg(ResultMessage.SUCCESS_TOKEN_VERIFY_MESSAGE);
                } else {
                    baseResultDto.setCode(ResultMessage.FAILED_CODE);
                    baseResultDto.setMsg(ResultMessage.FAILED_TOKEN_NOTMATCH_MESSAGE);
                }
            } else {
                baseResultDto.setCode(ResultMessage.FAILED_CODE);
                baseResultDto.setMsg(ResultMessage.FAILED_TOKEN_EXPIRE_MESSAGE);
            }
        } else {
            baseResultDto.setCode(ResultMessage.FAILED_CODE);
            baseResultDto.setMsg(ResultMessage.FAILED_TOKEN_VERIFY_MESSAGE);
        }
        return baseResultDto;
    }

    @Override
    @AopLog(description = "开发者登出", menuname = "用户登出")
    public BaseResultDto webUserLogout(HttpServletRequest request) {
        BaseResultDto baseResultDto = new BaseResultDto();
        String token = request.getHeader(CommonEnum.HEAD_PARAM.ACCOUNT.getValue());
        boolean logoutResult = redisClusterHelper.delKey(token);
        if (!logoutResult) {
            baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
            baseResultDto.setMsg(ResultMessage.SUCCESS_MESSAGE);
        } else {
            baseResultDto.setCode(ResultMessage.FAILED_CODE);
            baseResultDto.setMsg(ResultMessage.FAILED_MESSAGE);
        }
        return baseResultDto;
    }

    @Override
    public BaseResultDto isWebUser(String account) {
        BaseResultDto baseResultDto = new BaseResultDto();
        PlatFormUser user = platFormSysUserLoginDao.getUserByAccount(account, CommonEnum.USER_TYPE.厂商用户.getValue());
        if (user != null) {
            baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
            baseResultDto.setMsg(ResultMessage.SUCCESS_MESSAGE);
        } else {
            baseResultDto.setCode(ResultMessage.FAILED_CODE);
            baseResultDto.setMsg(ResultMessage.FAILED_LOGIN_NOTUSER_MESSAGE);
        }
        return baseResultDto;
    }

    @Override
    public BaseResultDto webSendEmail(String account, String validateCode, HttpServletRequest request) {
        BaseResultDto baseResultDto = new BaseResultDto();
        HttpSession session = request.getSession();
        String getValidateCode = (String) session.getAttribute(VALIDATA_CODE);


        boolean validatecodeResult = validateCode.equalsIgnoreCase(getValidateCode);

        if (validatecodeResult) {
            if (StringUtils.isNotBlank(account)) {
                baseResultDto = this.isWebUser(account);
                if ("1".equals(baseResultDto.getCode())) {
                    String access_token = JwtUtils.createToken(account);
                    //将token存入缓存中
                    Integer expireTime = Integer.parseInt(emailExpireTime);
                    boolean result = redisClusterHelper.setKey(access_token, access_token, expireTime);
                    if (result) {
                        //发送邮件
                        String resetPassHref = emailCheckUrl + "?ticket=" + access_token;
                        StringBuffer stringBuffer = new StringBuffer("尊敬的开发者您好<br/>");
                        stringBuffer.append("您的登录账号为:").append(account + "<br/>");
                        stringBuffer.append("请点击以下链接完成修改密码操作<br/>");
                        stringBuffer.append("<a href='" + resetPassHref + "' target='_blank'>点击此处修改密码</a><br/>");
                        stringBuffer.append("如果以上链接无法点击，请将上面的地址复制到您的浏览器(如IE)的地址栏进入通州云开放平台。");
                        stringBuffer.append("(本邮件超过'" + Integer.parseInt(emailOvertime) + "'分钟,链接将会失效，需要重新申请)");

                        String content = "";
                        sendEmailHelper.sendEmail(account, emailSubject, content, stringBuffer.toString());
                        baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
                        baseResultDto.setMsg(ResultMessage.SUCCESS_MESSAGE);
                    } else {
                        baseResultDto.setCode(ResultMessage.FAILED_CODE);
                        baseResultDto.setMsg(ResultMessage.FAILED_MESSAGE);
                    }
                } else {
                    baseResultDto.setCode(ResultMessage.FAILED_CODE);
                    baseResultDto.setMsg(ResultMessage.FAILED_LOGIN_NOTUSER_MESSAGE);
                }
            } else {
                baseResultDto.setCode(ResultMessage.FAILED_CODE);
                baseResultDto.setMsg(ResultMessage.FAILED_EMAIL_NOT_EMPTY);
            }
        } else {
            baseResultDto.setCode(ResultMessage.FAILED_CODE);
            baseResultDto.setMsg(ResultMessage.FAILED_VALIDATECODE_NOTUSER_MESSAGE);
        }
        return baseResultDto;
    }

    @Override
    public BaseResultDto webUpdatePassword(String account, String passwordOld, String passwordNew, String ticket) {
        BaseResultDto baseResultDto = new BaseResultDto();
        if (StringUtils.isNotBlank(ticket)) {
            String accesstoken = redisClusterHelper.getKey(ticket);
            if (StringUtils.isNotBlank(accesstoken)) {
                //如果从缓存中获取的token不等于传过来的ticket
                if (accesstoken.equals(ticket)) {
                    PlatFormUser user = platFormSysUserLoginDao.getUserByAccount(account, CommonEnum.USER_TYPE.厂商用户.getValue());

                    //去掉缓存中token  防止二次点击
                    redisClusterHelper.delKey(ticket);

                    if (user != null) {
                        String accountid = user.getId();
                        //校验旧密码
                        if (StringUtils.isNotBlank(passwordNew)) {
                            String passsaltNew = UidUtils.getId();
                            String pwd = SHA1Util.hex_sha1(passwordNew + passsaltNew);
                            Integer integer = platFormUserDao.changePwdByAccount(pwd, passsaltNew, accountid);
                            if (integer == 1) {
                                baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
                                baseResultDto.setMsg(ResultMessage.SUCCESS_UPDATE_MESSAGE);
                            } else {
                                baseResultDto.setCode(ResultMessage.FAILED_CODE);
                                baseResultDto.setMsg(ResultMessage.FAILED_UPDATE_MESSAGE);
                            }
                        } else {
                            baseResultDto.setCode(ResultMessage.FAILED_CODE);
                            baseResultDto.setMsg(ResultMessage.FAILED_LOGIN_NULL_MESSAGE);
                        }
                    } else {
                        baseResultDto.setCode(ResultMessage.FAILED_CODE);
                        baseResultDto.setMsg(ResultMessage.FAILED_LOGIN_NOTUSER_MESSAGE);
                    }
                } else {
                    baseResultDto.setCode(ResultMessage.FAILED_CODE);
                    baseResultDto.setMsg(ResultMessage.FAILED_TICKET_EXPIRE_MESSAGE);
                }
            } else {
                baseResultDto.setCode(ResultMessage.FAILED_CODE);
                baseResultDto.setMsg(ResultMessage.FAILED_TICKET_EXPIRE_MESSAGE);
            }
        } else {
            baseResultDto.setCode(ResultMessage.FAILED_CODE);
            baseResultDto.setMsg(ResultMessage.FAILED_TICKET_EXPIRE_MESSAGE);
        }
        return baseResultDto;
    }
}
