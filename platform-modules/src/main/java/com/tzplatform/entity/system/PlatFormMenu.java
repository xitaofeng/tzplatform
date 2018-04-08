package com.tzplatform.entity.system;

import com.tzplatform.entity.common.PageDto;

import java.io.Serializable;

/**
 * 菜单对象
 *
 * @author leijie
 */
public class PlatFormMenu extends PageDto implements Serializable {

    private static final long serialVersionUID = 8895853667002763219L;

    private String id;//主键
    private String menuname;//菜单名称
    private String menuurl;//菜单路径
    private String parentid;//父级菜单名称
    private Integer sort;//排序

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMenuname() {
        return menuname;
    }

    public void setMenuname(String menuname) {
        this.menuname = menuname;
    }

    public String getMenuurl() {
        return menuurl;
    }

    public void setMenuurl(String menuurl) {
        this.menuurl = menuurl;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
