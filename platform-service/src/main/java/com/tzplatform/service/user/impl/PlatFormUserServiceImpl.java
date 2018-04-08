package com.tzplatform.service.user.impl;

import com.tzplatform.dao.system.PlatFormMenuRoleDao;
import com.tzplatform.dao.user.PlatFormSysUserLoginDao;
import com.tzplatform.dao.user.PlatFormUserDao;
import com.tzplatform.dao.user.PlatUserRoleDao;
import com.tzplatform.entity.common.BaseResultDto;
import com.tzplatform.entity.common.CommonEnum;
import com.tzplatform.entity.common.ResultMessage;
import com.tzplatform.entity.user.PlatFormUser;
import com.tzplatform.service.user.PlatFormUserService;
import com.tzplatform.utils.aoplog.AopLog;
import com.tzplatform.utils.common.SHA1Util;
import com.tzplatform.utils.common.UidUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service(value = "platFormUserService")
public class PlatFormUserServiceImpl implements PlatFormUserService {

    @Resource
    private PlatFormUserDao platFormUserDao;

    @Resource
    private PlatUserRoleDao platUserRoleDao;

    @Resource
    private PlatFormMenuRoleDao platFormMenuRoleDao;

    @Resource
    private PlatFormSysUserLoginDao platFormSysUserLoginDao;

    /**
     * 添加用户
     *
     * @param platFormUser
     * @return
     */
    @Override
    @AopLog(menuname = "用户管理", description = "添加用户")
    @Transactional
    public BaseResultDto addSysUser(PlatFormUser platFormUser, String roleids) {
        BaseResultDto baseResultDto = new BaseResultDto();
        String userid = UidUtils.getId();
        platFormUser.setId(userid);
        platFormUser.setUsertype(CommonEnum.USER_TYPE.系统用户.getValue());
        String account = platFormUser.getAccount();
        String passsalt = UidUtils.getId();
        String password = platFormUser.getPassword();
        if (StringUtils.isNotBlank(account)) {
            if (StringUtils.isBlank(password)) {
                password = account;
            }
            platFormUser.setPassword(SHA1Util.hex_sha1(password + passsalt));
            platFormUser.setPasssalt(passsalt);
            platFormUser.setAccount(account);

            boolean checkuser = checkUserInSystem(platFormUser.getAccount(), platFormUser.getEmail());
            Integer result = 0;
            if (checkuser) {
                result = platFormUserDao.addUser(platFormUser);

                //添加授权的角色
                String[] newids = roleids.split(",");
                for (int i = 0; i < newids.length; i++) {
                    if (StringUtils.isNotBlank(newids[i])) {
                        platUserRoleDao.addUserToRole(UidUtils.getId(), userid, newids[i]);
                    }
                }
            }
            if (1 == result) {
                baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
                baseResultDto.setMsg(ResultMessage.SUCCESS_INSERT_MESSAGE);
            } else {
                baseResultDto.setCode(ResultMessage.FAILED_CODE);
                baseResultDto.setMsg(ResultMessage.FAILED_INSERT_MESSAGE);
            }
        } else {
            baseResultDto.setCode(ResultMessage.FAILED_CODE);
            baseResultDto.setMsg(ResultMessage.FAILED_INSERT_MESSAGE);
        }
        return baseResultDto;
    }

    /**
     * 修改用户
     *
     * @param platFormUser
     * @return
     */
    @Override
    @AopLog(menuname = "用户管理",description = "修改用户")
    @Transactional
    public BaseResultDto updateSysUser(PlatFormUser platFormUser, String roleids) {
        BaseResultDto baseResultDto = new BaseResultDto();

        //帐号不允许修改
        platFormUser.setAccount("");

        String userid = platFormUser.getId();
        platUserRoleDao.delUserToRole(userid);
        //添加授权的角色
        String[] newids = roleids.split(",");
        for (int i = 0; i < newids.length; i++) {
            if (StringUtils.isNotBlank(newids[i])) {
                platUserRoleDao.addUserToRole(UidUtils.getId(), userid, newids[i]);
            }
        }

        Integer result = platFormUserDao.updateUser(platFormUser);
        if (1 == result) {
            baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
            baseResultDto.setMsg(ResultMessage.SUCCESS_UPDATE_MESSAGE);
        } else {
            baseResultDto.setCode(ResultMessage.FAILED_CODE);
            baseResultDto.setMsg(ResultMessage.FAILED_UPDATE_MESSAGE);
        }
        return baseResultDto;
    }

    /**
     * 删除用户
     *
     * @param platFormUser
     * @return
     */
    @Override
    @AopLog(menuname = "用户管理",description = "删除用户")
    @Transactional
    public BaseResultDto delSysUser(PlatFormUser platFormUser) {
        BaseResultDto baseResultDto = new BaseResultDto();

        String userid = platFormUser.getId();
        platUserRoleDao.delUserToRole(userid);

        Integer result = platFormUserDao.delUser(platFormUser);
        if (1 == result) {
            baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
            baseResultDto.setMsg(ResultMessage.SUCCESS_DELETE_MESSAGE);
        } else {
            baseResultDto.setCode(ResultMessage.FAILED_CODE);
            baseResultDto.setMsg(ResultMessage.FAILED_DELETE_MESSAGE);
        }
        return baseResultDto;
    }

    /**
     * 查询用户列表
     *
     * @param platFormUser
     * @return
     */
    @Override
    //@AopLog(menuname = "用户管理", description = "查询用户")
    public BaseResultDto userList(PlatFormUser platFormUser) {
        BaseResultDto baseResultDto = new BaseResultDto();
        List<PlatFormUser> resultList = platFormUserDao.userList(platFormUser);
        Integer resultCount = platFormUserDao.userListCount(platFormUser);
        baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
        baseResultDto.setData(resultList);
        baseResultDto.setTotal(resultCount);
        return baseResultDto;
    }

    /**
     * 查询用户总数
     *
     * @param platFormUser
     * @return
     */
    @Override
    public Integer userListCount(PlatFormUser platFormUser) {
        return platFormUserDao.userListCount(platFormUser);
    }

    /**
     * 校验用户是否存在
     *
     * @param account
     * @param email
     * @return
     */
    @Override
    public boolean checkUserInSystem(String account, String email) {
        boolean checkresult = true;
        PlatFormUser platFormUser = new PlatFormUser();
        platFormUser.setAccount(account);
        platFormUser.setEmail(email);
        Integer resultList = platFormUserDao.userListCount(platFormUser);
        if (resultList > 0) {
            checkresult = false;
        }
        return checkresult;
    }

    /**
     * 获取用户对应的角色
     *
     * @param userid
     * @return
     */
    @Override
    public BaseResultDto getRoleByUser(String userid) {
        BaseResultDto baseResultDto = new BaseResultDto();
        List<String> resultList = platUserRoleDao.getRoleByUser(userid);
        baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
        baseResultDto.setData(resultList);
        return baseResultDto;
    }

    /**
     * 重置用户密码
     *
     * @param accountid
     * @return
     */
    @Override
    @AopLog(menuname = "用户管理",description = "密码重置")
    public BaseResultDto resetPassWordByAccount(String accountid) {
        BaseResultDto baseResultDto = new BaseResultDto();
        PlatFormUser findInfo = new PlatFormUser();
        findInfo.setId(accountid);
        List<PlatFormUser> resultList = platFormUserDao.userList(findInfo);
        if (resultList.size() > 0) {
            PlatFormUser platFormUser = resultList.get(0);
            String account = platFormUser.getAccount();
            String passsalt = UidUtils.getId();
            Integer result = platFormUserDao.changePwdByAccount(SHA1Util.hex_sha1(account + passsalt), passsalt, accountid);
            if (1 == result) {
                baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
                baseResultDto.setMsg(ResultMessage.SUCCESS_MESSAGE);
            } else {
                baseResultDto.setCode(ResultMessage.FAILED_CODE);
                baseResultDto.setMsg(ResultMessage.FAILED_MESSAGE);
            }
        } else {
            baseResultDto.setCode(ResultMessage.FAILED_CODE);
            baseResultDto.setMsg(ResultMessage.FAILED_MESSAGE);
        }
        return baseResultDto;
    }

    /**
     * 根据用户获取对应的资源列表
     *
     * @param userid
     * @return
     */
    @Override
    public BaseResultDto getResourcesByUser(String userid) {
        BaseResultDto baseResultDto = new BaseResultDto();
        List<String> roleids = platUserRoleDao.getRoleByUser(userid);
        if (roleids.size() > 0) {
            List<String> menuids = platFormMenuRoleDao.getResourceByRoleIds(roleids);
            if (menuids.size() > 0) {
                baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
                baseResultDto.setData(menuids);
            } else {
                baseResultDto.setCode(ResultMessage.FAILED_CODE);
                baseResultDto.setMsg(ResultMessage.FAILED_USERNOT_ROLE_MESSAGE);
            }
        } else {
            baseResultDto.setCode(ResultMessage.FAILED_CODE);
            baseResultDto.setMsg(ResultMessage.FAILED_USERNOT_ROLE_MESSAGE);
        }
        return baseResultDto;
    }

    /**
     * 用户修改密码
     *
     * @param account
     * @param passwordOld
     * @param passwordNew
     * @return
     */
    @Override
    @AopLog(menuname = "用户管理", description = "修改密码")
    public BaseResultDto updateUserPassword(String account, String passwordOld, String passwordNew) {
        BaseResultDto baseResultDto = new BaseResultDto();
        PlatFormUser user = platFormSysUserLoginDao.getUserByAccount(account,CommonEnum.USER_TYPE.系统用户.getValue());
        if (user != null) {
            String passsalt = user.getPasssalt();
            String password = user.getPassword();
            String accountid=user.getId();
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
     * 快速注册-判断邮箱账号是否已经激活
     * @param email
     * @param value
     * @return
     */
    @Override
    public boolean checkUserInSystemStatus(String email, String value) {
        boolean checkresult = true;
        PlatFormUser platFormUser = new PlatFormUser();
        platFormUser.setEmail(email);
        platFormUser.setUserstatus(value);
        Integer resultList = platFormUserDao.userListCount(platFormUser);
        if (resultList > 0) {
            checkresult = false;
        }
        return checkresult;
    }
}
