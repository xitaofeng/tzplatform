package com.tzplatform.web.system;

import com.tzplatform.entity.common.BaseResultDto;
import com.tzplatform.entity.system.PlatFormMenu;
import com.tzplatform.service.system.PlatFormMenuService;
import com.tzplatform.service.tokenfilter.AccessToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/platformMenu")
public class PlatFormMenuController {

    @Resource
    private PlatFormMenuService platFormMenuService;

    /**
     * 添加菜单
     * @param platFormMenu
     * @return
     */
    @AccessToken
    @RequestMapping(method = RequestMethod.POST,value = "/addMenu", produces = "application/json; charset=utf-8")
    public BaseResultDto addMenu(PlatFormMenu platFormMenu) {
        return platFormMenuService.addMenu(platFormMenu);
    }

    /**
     * 修改菜单
     * @param platFormMenu
     * @return
     */
    @AccessToken
    @RequestMapping(method = RequestMethod.POST,value = "/editMenu", produces = "application/json; charset=utf-8")
    public BaseResultDto editMenu(PlatFormMenu platFormMenu) {
        return platFormMenuService.editMenu(platFormMenu);
    }

    /**
     * 删除菜单
     * @param id
     * @return
     */
    @AccessToken
    @RequestMapping(method = RequestMethod.POST,value = "/delMenu", produces = "application/json; charset=utf-8")
    public BaseResultDto delMenu(String id) {
        return platFormMenuService.delMenu(id);
    }

    /**
     * 查询菜单
     * @param platFormMenu
     * @return
     */
    @AccessToken
    @RequestMapping(method = RequestMethod.POST,value = "/menuList", produces = "application/json; charset=utf-8")
    public BaseResultDto menuList(PlatFormMenu platFormMenu) {
        return platFormMenuService.menuList(platFormMenu);
    }

    /**
     * 查询一级菜单
     * @param platFormMenu
     * @return
     */
    @AccessToken
    @RequestMapping(method = RequestMethod.POST,value = "/rootMenuList", produces = "application/json; charset=utf-8")
    public BaseResultDto rootMenuList(PlatFormMenu platFormMenu) {
        return platFormMenuService.rootMenuList(platFormMenu);
    }

    /**
     * 查询树菜单
     * @param platFormMenu
     * @return
     */
    @AccessToken
    @RequestMapping(method = RequestMethod.POST,value = "/menuTree", produces = "application/json; charset=utf-8")
    public BaseResultDto menuTree(PlatFormMenu platFormMenu) {
        return platFormMenuService.menuTree(platFormMenu);
    }
}
