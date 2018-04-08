package com.tzplatform.web.system;

import com.tzplatform.entity.common.BaseResultDto;
import com.tzplatform.entity.system.PlatFormRole;
import com.tzplatform.service.system.PlatFormRoleService;
import com.tzplatform.service.tokenfilter.AccessToken;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/platformRole")
public class PlatFormRoleController {

    @Resource
    private PlatFormRoleService platFormRoleService;

    /**
     * 添加角色
     *
     * @param platFormRole
     * @return
     */
    @AccessToken
    @RequestMapping(method = RequestMethod.POST,value = "/addRole", produces = "application/json; charset=utf-8")
    public BaseResultDto addRole(PlatFormRole platFormRole, String menuids) {
        return platFormRoleService.addRole(platFormRole, menuids);
    }

    /**
     * 修改角色
     *
     * @param platFormRole
     * @return
     */
    @AccessToken
    @RequestMapping(method = RequestMethod.POST,value = "/editRole", produces = "application/json; charset=utf-8")
    public BaseResultDto editRole(PlatFormRole platFormRole, String menuids) {
        return platFormRoleService.editRole(platFormRole, menuids);
    }

    /**
     * 删除角色
     *
     * @param id
     * @return
     */
    @AccessToken
    @RequestMapping(method = RequestMethod.POST,value = "/delRole", produces = "application/json; charset=utf-8")
    public BaseResultDto delRole(String id) {
        return platFormRoleService.delRole(id);
    }

    /**
     * 查询角色
     *
     * @param platFormRole
     * @return
     */
    @AccessToken
    @RequestMapping(method = RequestMethod.POST,value = "/listRole", produces = "application/json; charset=utf-8")
    public BaseResultDto listRole(PlatFormRole platFormRole) {
        return platFormRoleService.listRole(platFormRole);
    }


    /**
     * 角色添加对应的菜单
     *
     * @param roleid
     * @param menuids
     * @return
     */
    @AccessToken
    @RequestMapping(method = RequestMethod.POST,value = "/addResourceToRole", produces = "application/json; charset=utf-8")
    public BaseResultDto addResourceToRole(String roleid, String menuids) {
        return platFormRoleService.addResourceToRole(roleid, menuids);
    }

    /**
     * 获取角色对应的菜单
     * @param roleid
     * @return
     */
    @AccessToken
    @RequestMapping(method = RequestMethod.POST,value = "/getResourceByRole", produces = "application/json; charset=utf-8")
    public BaseResultDto getResourceByRole(String roleid) {
        return platFormRoleService.getResourceByRole(roleid);
    }
}
