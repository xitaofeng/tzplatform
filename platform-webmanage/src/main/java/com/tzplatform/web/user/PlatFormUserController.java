package com.tzplatform.web.user;

import com.tzplatform.entity.common.BaseResultDto;
import com.tzplatform.entity.common.ResultMessage;
import com.tzplatform.entity.user.PlatFormUser;
import com.tzplatform.service.tokenfilter.AccessToken;
import com.tzplatform.service.user.PlatFormUserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/platformUser")
public class PlatFormUserController {

    @Resource
    private PlatFormUserService platFormUserService;

    /**
     * 添加系统用户
     *
     * @param platFormUser
     * @return
     */
    @AccessToken
    @RequestMapping(method = RequestMethod.POST,value = "/addSysUser", produces = "application/json;charset=utf-8")
    public BaseResultDto addSysUser(PlatFormUser platFormUser, String roleids) {
        return platFormUserService.addSysUser(platFormUser, roleids);
    }

    /**
     * 更新系统用户
     *
     * @param platFormUser
     * @return
     */
    @AccessToken
    @RequestMapping(method = RequestMethod.POST,value = "/updateSysUser", produces = "application/json;charset=utf-8")
    public BaseResultDto updateSysUser(PlatFormUser platFormUser, String roleids) {
        return platFormUserService.updateSysUser(platFormUser, roleids);
    }

    /**
     * 删除系统用户
     *
     * @param platFormUser
     * @return
     */
    @AccessToken
    @RequestMapping(method = RequestMethod.POST,value = "/delSysUser", produces = "application/json;charset=utf-8")
    public BaseResultDto delSysUser(PlatFormUser platFormUser) {
        return platFormUserService.delSysUser(platFormUser);
    }

    /**
     * 查询用户列表
     *
     * @param platFormUser
     * @return
     */
    @AccessToken
    @RequestMapping(method = RequestMethod.POST,value = "/userList", produces = "application/json;charset=utf-8")
    public BaseResultDto userList(PlatFormUser platFormUser) {
        return platFormUserService.userList(platFormUser);
    }

    /**
     * 校验用户状态
     *
     * @param account
     * @param email
     * @return
     */
    @AccessToken
    @RequestMapping(value = "/checkUserInSystem", produces = "application/json;charset=utf-8")
    public BaseResultDto checkUserInSystem(String account, String email) {
        BaseResultDto baseResultDto = new BaseResultDto();
        baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
        baseResultDto.setData(platFormUserService.checkUserInSystem(account, email));
        return baseResultDto;
    }

    /**
     * 获取用户对应的角色
     *
     * @param userid
     * @return
     */
    @AccessToken
    @RequestMapping(method = RequestMethod.POST,value = "/getRoleByUser", produces = "application/json;charset=utf-8")
    public BaseResultDto getRoleByUser(String userid) {
        return platFormUserService.getRoleByUser(userid);
    }

    /**
     * 重置密码
     *
     * @param accountid
     * @return
     */
    @AccessToken
    @RequestMapping(method = RequestMethod.POST,value = "/resetPwd", produces = "application/json;charset=utf-8")
    public BaseResultDto resetPassWordByAccount(String accountid) {
        return platFormUserService.resetPassWordByAccount(accountid);
    }

    /**
     * 获取用户对应的资源
     *
     * @param accountid
     * @return
     */
    @AccessToken
    @RequestMapping(method = RequestMethod.POST,value = "/getResourcesByUser", produces = "application/json;charset=utf-8")
    public BaseResultDto getResourcesByUser(String accountid) {
        return platFormUserService.getResourcesByUser(accountid);
    }

    @AccessToken
    @RequestMapping(value = "/updateUserPassword", produces = "application/json;charset=utf-8")
    public BaseResultDto updateUserPassword(String account,String passwordOld,String passwordNew) {
        return platFormUserService.updateUserPassword(account,passwordOld,passwordNew);
    }
}
