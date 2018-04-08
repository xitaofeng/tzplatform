package com.tzplatform.entity.system;

import com.tzplatform.entity.common.PageDto;

import java.io.Serializable;
import java.util.Date;

public class PlatFormExceptionLog extends PageDto implements Serializable {
    private static final long serialVersionUID = 8888487178311783006L;
    private String id;
    private String handleaccount;
    private String handleip;
    private String applicationname;
    private String menuname;
    private String description;
    private String methodname;
    private String errorinfo;
    private Date createtime;
    //用户名称账号
    private String account;
    private String name;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PlatFormExceptionLog() {
    }

    public PlatFormExceptionLog(String id, String handleaccount, String handleip, String applicationname, String menuname, String description, String methodname, String errorinfo, Date createtime) {
        this.id = id;
        this.handleaccount = handleaccount;
        this.handleip = handleip;
        this.applicationname = applicationname;
        this.menuname = menuname;
        this.description = description;
        this.methodname = methodname;
        this.errorinfo = errorinfo;
        this.createtime = createtime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHandleaccount() {
        return handleaccount;
    }

    public void setHandleaccount(String handleaccount) {
        this.handleaccount = handleaccount;
    }

    public String getHandleip() {
        return handleip;
    }

    public void setHandleip(String handleip) {
        this.handleip = handleip;
    }

    public String getApplicationname() {
        return applicationname;
    }

    public void setApplicationname(String applicationname) {
        this.applicationname = applicationname;
    }

    public String getMenuname() {
        return menuname;
    }

    public void setMenuname(String menuname) {
        this.menuname = menuname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMethodname() {
        return methodname;
    }

    public void setMethodname(String methodname) {
        this.methodname = methodname;
    }

    public String getErrorinfo() {
        return errorinfo;
    }

    public void setErrorinfo(String errorinfo) {
        this.errorinfo = errorinfo;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}
