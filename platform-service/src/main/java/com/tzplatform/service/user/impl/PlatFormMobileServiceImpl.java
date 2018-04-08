package com.tzplatform.service.user.impl;

import com.tzplatform.dao.tzuum.PlatFormUumUserDao;
import com.tzplatform.dao.user.PlatFormSysUserLoginDao;
import com.tzplatform.entity.common.BaseResultDto;
import com.tzplatform.entity.common.ResultMessage;
import com.tzplatform.entity.mobileplatform.MobileUser;
import com.tzplatform.service.common.RedisClusterHelper;
import com.tzplatform.service.ldap.LdapHelper;
import com.tzplatform.service.user.PlatFormMobileService;
import com.tzplatform.utils.aoplog.AopLog;
import com.tzplatform.utils.common.SHA1Util;
import com.tzplatform.utils.jwtauth.JwtUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service(value = "platFormMobileService")
public class PlatFormMobileServiceImpl implements PlatFormMobileService {

    @Resource
    private PlatFormSysUserLoginDao platFormSysUserLoginDao;

    @Resource
    private DataSourceTransactionManager transactionManager;

    @Resource
    private PlatFormUumUserDao platFormUumUserDao;

    @Value("${synchronousOpen}")
    private String ldapsynchronousOpen;

    @Resource
    private LdapHelper ldapHelper;

    @Resource
    private RedisClusterHelper redisClusterHelper;


    @Override
    @Transactional
    @AopLog(description = "外放接口修改密码", menuname = "移动平台")
    public BaseResultDto updateUserPassword(String account, String passwordOld, String passwordNew) {


        BaseResultDto baseResultDto = new BaseResultDto();
        if (StringUtils.isNotBlank(account) && StringUtils.isNotBlank(passwordOld) && StringUtils.isNotBlank(passwordNew)) {
            MobileUser mobileUser = platFormSysUserLoginDao.getMobieUserByAccount(account);
            if (null != mobileUser) {
                String passdatabase = mobileUser.getPassword();
                String getpassword = SHA1Util.hex_sha1(passwordOld);
                if (passdatabase.equals(getpassword) || passdatabase.equals(passwordOld)) {

                    String newpwd = SHA1Util.hex_sha1(passwordNew);
                    platFormUumUserDao.updateUserPwd(account, newpwd);
                    MobileUser getMobileUser = platFormSysUserLoginDao.getMobieUserByAccount(account);
                    if (newpwd.equals(getMobileUser.getPassword())) {
                        //密码修改成功再进行同步
                        if ("true".equals(ldapsynchronousOpen)) {
                            ldapHelper.modifyUserPwd(getMobileUser, passwordNew);
                            boolean ldapResult = ldapHelper.checkLdapPassWord(getMobileUser, passwordNew);
                            if (ldapResult) {
                                baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
                                baseResultDto.setMsg(ResultMessage.SUCCESS_MESSAGE);
                            } else {
                                //ldap查询失败 则进行事务回滚
                                baseResultDto.setCode(ResultMessage.FAILED_CODE);
                                baseResultDto.setMsg(ResultMessage.FAILED_MESSAGE);
                                throw new RuntimeException();
                            }
                        } else {
                            baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
                            baseResultDto.setMsg(ResultMessage.SUCCESS_MESSAGE);
                        }
                    } else {
                        baseResultDto.setCode(ResultMessage.FAILED_CODE);
                        baseResultDto.setMsg(ResultMessage.FAILED_MESSAGE);
                        throw new RuntimeException();
                    }
                } else {
                    baseResultDto.setCode(ResultMessage.FAILED_CODE);
                    baseResultDto.setMsg(ResultMessage.FAILED_EDITPWD_OLDPWDERROR_MESSAGE);
                }
            } else {
                baseResultDto.setCode(ResultMessage.FAILED_CODE);
                baseResultDto.setMsg(ResultMessage.FAILED_THIRD_LOGIN_ACCOUNTNOTEXIST_MESSAGE);
            }
        } else {
            baseResultDto.setCode(ResultMessage.FAILED_CODE);
            baseResultDto.setMsg(ResultMessage.FAILED_EDITPWD_NOTNULL_MESSAGE);
        }
        return baseResultDto;
    }


    @Override
    @Transactional
    public BaseResultDto updateTzjyyUserPassword(String account, String passwordOld, String passwordNew) {
        // 手动控制事务处理
        BaseResultDto baseResultDto = new BaseResultDto();
        if (StringUtils.isNotBlank(account) && StringUtils.isNotBlank(passwordOld) && StringUtils.isNotBlank(passwordNew)) {
            MobileUser mobileUser = platFormSysUserLoginDao.getMobieUserByAccount(account);
            if (null != mobileUser) {
                String passdatabase = mobileUser.getPassword();
                String getpassword = SHA1Util.hex_sha1(passwordOld);

                if (passdatabase.equals(getpassword) || passdatabase.equals(passwordOld)) {

                    String newpwd = SHA1Util.hex_sha1(passwordNew);
                    platFormUumUserDao.updateUserPwd(account, newpwd);
                    MobileUser getMobileUser = platFormSysUserLoginDao.getMobieUserByAccount(account);
                    if (newpwd.equals(getMobileUser.getPassword())) {
                        //密码修改成功再进行同步
                        if ("true".equals(ldapsynchronousOpen)) {
                            ldapHelper.modifyUserPwd(getMobileUser, passwordNew);
                            boolean ldapResult = ldapHelper.checkLdapPassWord(getMobileUser, passwordNew);
                            if (ldapResult) {
                                baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
                                baseResultDto.setMsg(ResultMessage.SUCCESS_MESSAGE);
                            } else {
                                baseResultDto.setCode(ResultMessage.FAILED_CODE);
                                baseResultDto.setMsg(ResultMessage.FAILED_MESSAGE);
                                throw new RuntimeException();
                            }
                        } else {
                            baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
                            baseResultDto.setMsg(ResultMessage.SUCCESS_MESSAGE);
                        }
                    } else {
                        baseResultDto.setCode(ResultMessage.FAILED_CODE);
                        baseResultDto.setMsg(ResultMessage.FAILED_MESSAGE);
                        throw new RuntimeException();
                    }
                } else {
                    baseResultDto.setCode(ResultMessage.FAILED_CODE);
                    baseResultDto.setMsg(ResultMessage.FAILED_EDITPWD_OLDPWDERROR_MESSAGE);
                }
            } else {
                baseResultDto.setCode(ResultMessage.FAILED_CODE);
                baseResultDto.setMsg(ResultMessage.FAILED_THIRD_LOGIN_ACCOUNTNOTEXIST_MESSAGE);
            }
        } else {
            baseResultDto.setCode(ResultMessage.FAILED_CODE);
            baseResultDto.setMsg(ResultMessage.FAILED_EDITPWD_NOTNULL_MESSAGE);
        }
        return baseResultDto;
    }

    @Override
    public BaseResultDto checkUserPassword(String account, String passwordOld, String passwordNew) {
        return null;
    }

    /**
     * 校验token合法性
     *
     * @param token
     * @return
     */
    @Override
    public BaseResultDto checkTokenResult(String token) {
        BaseResultDto baseResultDto = new BaseResultDto();
        if (StringUtils.isNotBlank(token)) {
            boolean existToken = redisClusterHelper.exists(token);
            if (existToken) {
                String userName = JwtUtils.veriftToken(token);
                Map<String, Object> resultMap = new HashMap<>();
                resultMap.put("userName", userName);
                baseResultDto.setData(resultMap);
                baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
                baseResultDto.setMsg(ResultMessage.SUCCESS_TOKEN_VERIFY_MESSAGE);
            } else {
                baseResultDto.setCode(ResultMessage.FAILED_CODE);
                baseResultDto.setMsg(ResultMessage.FAILED_TOKEN_NOTMATCH_MESSAGE);
            }
        } else {
            baseResultDto.setCode(ResultMessage.FAILED_CODE);
            baseResultDto.setMsg(ResultMessage.FAILED_TOKEN_VERIFY_MESSAGE);
        }
        return baseResultDto;
    }
}
