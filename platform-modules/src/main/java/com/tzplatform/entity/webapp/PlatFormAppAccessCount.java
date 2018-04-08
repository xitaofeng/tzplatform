package com.tzplatform.entity.webapp;

import com.tzplatform.entity.common.PageDto;

import java.io.Serializable;
import java.util.Date;

/**
 * 应用访问记录对象
 *
 * @author leijie
 */
public class PlatFormAppAccessCount extends PageDto implements Serializable {

    private static final long serialVersionUID = 6709348871867856803L;

    private String id;//主键
    private String appid;//应用id
    private Integer accesscount;//访问总数
    private Date startusetime;//上架时间
    private Integer useappcount;//使用人数
    private String appsuitobj;//适用对象
    private String appsuiltxd;//适用学段
    private String introduce;//简介
    private String appstart;//星级
    //add by qxk 17/10/13
    private Integer collectcount;//收藏人数
    private String defaultflag;//是否是默认应用  0否 1是

    //add by qxk 17/10/30
    private String webappname;

    public String getWebappname() {
        return webappname;
    }

    public void setWebappname(String webappname) {
        this.webappname = webappname;
    }

    public String getDefaultflag() {
        return defaultflag;
    }

    public void setDefaultflag(String defaultflag) {
        this.defaultflag = defaultflag;
    }

    public Integer getCollectcount() {
        return collectcount;
    }

    public void setCollectcount(Integer collectcount) {
        this.collectcount = collectcount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public Integer getAccesscount() {
        return accesscount;
    }

    public void setAccesscount(Integer accesscount) {
        this.accesscount = accesscount;
    }

    public Date getStartusetime() {
        return startusetime;
    }

    public void setStartusetime(Date startusetime) {
        this.startusetime = startusetime;
    }

    public Integer getUseappcount() {
        return useappcount;
    }

    public void setUseappcount(Integer useappcount) {
        this.useappcount = useappcount;
    }

    public String getAppsuitobj() {
        return appsuitobj;
    }

    public void setAppsuitobj(String appsuitobj) {
        this.appsuitobj = appsuitobj;
    }

    public String getAppsuiltxd() {
        return appsuiltxd;
    }

    public void setAppsuiltxd(String appsuiltxd) {
        this.appsuiltxd = appsuiltxd;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getAppstart() {
        return appstart;
    }

    public void setAppstart(String appstart) {
        this.appstart = appstart;
    }
}
