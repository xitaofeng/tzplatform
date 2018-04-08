package com.tzplatform.dao.system;

import com.tzplatform.entity.system.PlatFormMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 接口dao
 *
 * @author leijie
 */
public interface PlatFormMenuDao {

    Integer addMenu(PlatFormMenu platFormMenu);

    Integer editMenu(PlatFormMenu platFormMenu);

    Integer delMenu(@Param(value = "id") String id);

    Integer menuListCount(PlatFormMenu platFormMenu);

    List<PlatFormMenu> menuList(PlatFormMenu platFormMenu);

    List<PlatFormMenu> rootMenuList(PlatFormMenu platFormMenu);

}
