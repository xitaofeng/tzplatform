package com.tzplatform.service.system.impl;

import com.tzplatform.dao.system.PlatFormMenuDao;
import com.tzplatform.entity.common.BaseResultDto;
import com.tzplatform.entity.common.ResultMessage;
import com.tzplatform.entity.common.TreeDto;
import com.tzplatform.entity.system.PlatFormMenu;
import com.tzplatform.service.system.PlatFormMenuService;
import com.tzplatform.utils.aoplog.AopLog;
import com.tzplatform.utils.common.UidUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单实现接口
 *
 * @author leijie
 */
@Service(value = "platFormMenuService")
public class PlatFormMenuServiceImpl implements PlatFormMenuService {

    @Resource
    private PlatFormMenuDao platFormMenuDao;

    /**
     * 添加菜单
     *
     * @param platFormMenu
     * @return
     */
    @Override
    @AopLog(menuname = "菜单管理",description = "添加菜单")
    public BaseResultDto addMenu(PlatFormMenu platFormMenu) {
        BaseResultDto baseResultDto = new BaseResultDto();
        platFormMenu.setId(UidUtils.getId());
        Integer result = platFormMenuDao.addMenu(platFormMenu);
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
     * 更新菜单
     *
     * @param platFormMenu
     * @return
     */
    @Override
    @AopLog(menuname = "菜单管理",description = "更新菜单")
    public BaseResultDto editMenu(PlatFormMenu platFormMenu) {
        BaseResultDto baseResultDto = new BaseResultDto();
        Integer result = platFormMenuDao.editMenu(platFormMenu);
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
     * 删除菜单
     *
     * @param id
     * @return
     */
    @Override
    @AopLog(menuname = "菜单管理",description = "删除菜单")
    public BaseResultDto delMenu(String id) {
        BaseResultDto baseResultDto = new BaseResultDto();
        Integer result = platFormMenuDao.delMenu(id);
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
     * 查询菜单总数
     *
     * @param platFormMenu
     * @return
     */
    @Override
    public Integer menuListCount(PlatFormMenu platFormMenu) {
        return platFormMenuDao.menuListCount(platFormMenu);
    }

    /**
     * 查询菜单列表
     *
     * @param platFormMenu
     * @return
     */
    @Override
    public BaseResultDto menuList(PlatFormMenu platFormMenu) {
        BaseResultDto baseResultDto = new BaseResultDto();
        List<PlatFormMenu> resultList = platFormMenuDao.menuList(platFormMenu);
        Integer resultCount = platFormMenuDao.menuListCount(platFormMenu);
        baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
        baseResultDto.setData(resultList);
        baseResultDto.setTotal(resultCount);
        return baseResultDto;
    }

    /**
     * 获取一级菜单
     * @param platFormMenu
     * @return
     */
    @Override
    public BaseResultDto rootMenuList(PlatFormMenu platFormMenu) {
        BaseResultDto baseResultDto = new BaseResultDto();
        platFormMenu.setPageSize(0);
        baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
        baseResultDto.setData(platFormMenuDao.rootMenuList(platFormMenu));
        return baseResultDto;
    }

    /**
     * 递归查询树方法
     * @param platFormMenu
     * @return
     */
    @Override
    public BaseResultDto menuTree(PlatFormMenu platFormMenu) {
        BaseResultDto baseResultDto = new BaseResultDto();
        List<TreeDto> getParentTree = platFormMenuDao.rootMenuList(platFormMenu).stream().map(menuDto -> {
            TreeDto getTree = new TreeDto();
            getTree.setLabel(menuDto.getMenuname());
            getTree.setId(menuDto.getId());
            return getTree;
        }).collect(Collectors.toList());
        List<TreeDto> getTreeList = getChildTree(getParentTree);
        baseResultDto.setCode(ResultMessage.SUCCESS_CODE);
        baseResultDto.setData(getTreeList);
        return baseResultDto;
    }


    /**
     * 检查当前节点是否有子节点
     * @param platFormMenu
     * @return
     */
    public boolean checkHasChildren (PlatFormMenu platFormMenu) {
        boolean checkreuslt = false;
        if (platFormMenuDao.menuListCount(platFormMenu) > 0) {
            checkreuslt = true;
        }
        return checkreuslt;
    }


    /**
     * 递归查询树菜单公共方法
     * @param topMenuList
     * @return
     */
    public List<TreeDto> getChildTree(List<TreeDto> topMenuList) {
        for (TreeDto treeDto : topMenuList) {
            PlatFormMenu queryInfo = new PlatFormMenu();
            queryInfo.setParentid(treeDto.getId());
            if (!checkHasChildren(queryInfo)) {
                continue;
            }
            queryInfo.setPageSize(0);
            List<TreeDto> getChildTree = platFormMenuDao.menuList(queryInfo).stream().map(menuDto -> {
                TreeDto getTree = new TreeDto();
                getTree.setLabel(menuDto.getMenuname());
                getTree.setId(menuDto.getId());
                return getTree;
            }).collect(Collectors.toList());
            treeDto.setChildren(getChildTree);
            getChildTree(getChildTree);
        }
        return topMenuList;
    }



}
