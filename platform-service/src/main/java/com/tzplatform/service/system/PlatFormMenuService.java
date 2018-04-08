package com.tzplatform.service.system;

import com.tzplatform.entity.common.BaseResultDto;
import com.tzplatform.entity.system.PlatFormMenu;

/**
 * 菜单接口
 *
 * @author leijie
 */
public interface PlatFormMenuService {

    BaseResultDto addMenu(PlatFormMenu platFormMenu);

    BaseResultDto editMenu(PlatFormMenu platFormMenu);

    BaseResultDto delMenu(String id);

    Integer menuListCount(PlatFormMenu platFormMenu);

    BaseResultDto menuList(PlatFormMenu platFormMenu);

    BaseResultDto rootMenuList(PlatFormMenu platFormMenu);

    BaseResultDto menuTree(PlatFormMenu platFormMenu);
}
