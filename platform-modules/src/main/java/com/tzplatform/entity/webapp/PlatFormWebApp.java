package com.tzplatform.entity.webapp;

import com.tzplatform.entity.common.PageDto;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.Serializable;
import java.sql.Blob;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 平台应用对象
 *
 * @author leijie
 */
public class PlatFormWebApp extends PageDto implements Serializable {
    private static final long serialVersionUID = -2263788913861566728L;
    private String id; //主键
    private String webappid;//应用appkey
    private String webappsecret;//应用app密钥
    private String freestatus;//是否收费
    private String webappdevuser;//开发者id
    private String webappname;//应用名称
    private String webappenname;//应用英文名称
    private String webapptype;//应用类型
    private String webappkind;//应用分类
    private transient byte[] webapppicsmall;//应用图标64*64
    private transient String picsmallpath;//小图标磁盘路径
    private transient byte[] webapppic;//应用图标128*128
    private transient String picpath;//图标路径
    private String webappstatus;//应用状态
    private String webappwebsite;//应用网址
    private String webappip;//应用ip
    private String webappwebsitewh;//应用维护地址
    private String webappversion;//应用版本号
    private String webappversionname;//应用版本名称
    private String webappoauth;//oanth回调地址
    private String webappsso;//cas回调地址
    private String webappscope;//应用范围
    private transient byte[] webappregisterinfo;//应用注册登记表
    private transient String regestinfopath;//注册表磁盘路径
    private String webappos;//移动应用运行系统
    private String webappshowtype;//移动应用上架方式
    private String webappinstallurl;//移动应用安装地址
    private transient byte[] webappinstallorcode;//安装二维码地址
    private transient String orcodepath;//二维码磁盘路径
    private String webappsize;//应用大小
    private Date createtime;//创建时间
    private Date updatetime;//更新时间
    private String handleuser;//操作人
    //add by qxk 17/10/31
    private String newflag;//是否为精品 1：精品应用 0：非精品应用
    //add by qxk 17/12/04
    private String appOperationUrl;//操作手册路径
    private String appOperationName;//操作手册文件名
    //返回操作手册路径
    private List<HashMap<String,String>> appOperationList;
    //辅助字段
    private String webapppicsmallbase;//base64图片
    private String webapppicbase;//base64图片
    private String regestinfobase;//注册表base编码
    private String orcodebase;//二维码base64编码
    private String kindname;//分类代码
    private String kindvalue;//分类名称
    private String apiids;//选择授权接口
    private List<String> apiList;//已经接口列表
    private String schoolids;//所选择学校id
    private List<String> schoolList;//学校列表
    private String desciption;//应用描述
    private String webappsuitobj;//适用对象

    //付费是否可使用
    private String freeuse;
    //应用评论
    private List<PlatFormAppComment> platFormAppComments;
    //应用评分
    private String appstart;
    //应用人数
    private Integer useappcount;
    //收藏人数
    private Integer collectcount;
    //适用对象
    private String appsuitobj;
    //适用学段
    private String appsuiltxd;
    //应用简介
    private String introduce;
    //默认应用标示
    private String defaultflag;
    //厂商
    private String userappname;
    //返回图片path
    private List<HashMap<String,String>> appfilepath;

    //添加应用账号
    private String user;

    //是否已经添加 1.添加 0.未添加
    private Integer added;

    public List<HashMap<String, String>> getAppOperationList() {
        return appOperationList;
    }

    public void setAppOperationList(List<HashMap<String, String>> appOperationList) {
        this.appOperationList = appOperationList;
    }

    public String getAppOperationUrl() {
        return appOperationUrl;
    }

    public void setAppOperationUrl(String appOperationUrl) {
        this.appOperationUrl = appOperationUrl;
    }

    public String getAppOperationName() {
        return appOperationName;
    }

    public void setAppOperationName(String appOperationName) {
        this.appOperationName = appOperationName;
    }

    public Integer getAdded() {
        return added;
    }

    public void setAdded(Integer added) {
        this.added = added;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String[] getIdArray() {
        return idArray;
    }

    public void setIdArray(String[] idArray) {
        this.idArray = idArray;
    }

    //
    private String[] idArray;

    public String getNewflag() {
        return newflag;
    }

    public void setNewflag(String newflag) {
        this.newflag = newflag;
    }

    public List<HashMap<String, String>> getAppfilepath() {
        return appfilepath;
    }

    public void setAppfilepath(List<HashMap<String, String>> appfilepath) {
        this.appfilepath = appfilepath;
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

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getUserappname() {
        return userappname;
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

    public String getFreeuse() {
        return freeuse;
    }

    public void setFreeuse(String freeuse) {
        this.freeuse = freeuse;
    }

    public List<PlatFormAppComment> getPlatFormAppComments() {
        return platFormAppComments;
    }

    public void setPlatFormAppComments(List<PlatFormAppComment> platFormAppComments) {
        this.platFormAppComments = platFormAppComments;
    }

    public String getAppstart() {
        return appstart;
    }

    public void setAppstart(String appstart) {
        this.appstart = appstart;
    }

    public Integer getUseappcount() {
        return useappcount;
    }

    public void setUseappcount(Integer useappcount) {
        this.useappcount = useappcount;
    }

    public void setUserappname(String userappname) {
        this.userappname = userappname;
    }

    public String getWebappsuitobj() {
        return webappsuitobj;
    }

    public void setWebappsuitobj(String webappsuitobj) {
        this.webappsuitobj = webappsuitobj;
    }

    public String getDesciption() {
        return desciption;
    }

    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }

    public String getWebappip() {
        return webappip;
    }

    public void setWebappip(String webappip) {
        this.webappip = webappip;
    }

    public String getSchoolids() {
        return schoolids;
    }

    public void setSchoolids(String schoolids) {
        this.schoolids = schoolids;
    }

    public List<String> getSchoolList() {
        return schoolList;
    }

    public void setSchoolList(List<String> schoolList) {
        this.schoolList = schoolList;
    }

    public List<String> getApiList() {
        return apiList;
    }

    public void setApiList(List<String> apiList) {
        this.apiList = apiList;
    }

    public String getApiids() {
        return apiids;
    }

    public void setApiids(String apiids) {
        this.apiids = apiids;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWebappid() {
        return webappid;
    }

    public void setWebappid(String webappid) {
        this.webappid = webappid;
    }

    public String getWebappsecret() {
        return webappsecret;
    }

    public void setWebappsecret(String webappsecret) {
        this.webappsecret = webappsecret;
    }

    public String getFreestatus() {
        return freestatus;
    }

    public void setFreestatus(String freestatus) {
        this.freestatus = freestatus;
    }

    public String getWebappdevuser() {
        return webappdevuser;
    }

    public void setWebappdevuser(String webappdevuser) {
        this.webappdevuser = webappdevuser;
    }

    public String getWebappname() {
        return webappname;
    }

    public void setWebappname(String webappname) {
        this.webappname = webappname;
    }

    public String getWebappenname() {
        return webappenname;
    }

    public void setWebappenname(String webappenname) {
        this.webappenname = webappenname;
    }

    public String getWebapptype() {
        return webapptype;
    }

    public void setWebapptype(String webapptype) {
        this.webapptype = webapptype;
    }

    public byte[] getWebapppicsmall() {
        return webapppicsmall;
    }

    public void setWebapppicsmall(byte[] webapppicsmall) {
        this.webapppicsmall = webapppicsmall;
    }

    public byte[] getWebapppic() {
        return webapppic;
    }

    public void setWebapppic(byte[] webapppic) {
        this.webapppic = webapppic;
    }

    public String getWebappstatus() {
        return webappstatus;
    }

    public void setWebappstatus(String webappstatus) {
        this.webappstatus = webappstatus;
    }

    public String getWebappwebsite() {
        return webappwebsite;
    }

    public void setWebappwebsite(String webappwebsite) {
        this.webappwebsite = webappwebsite;
    }

    public String getWebappwebsitewh() {
        return webappwebsitewh;
    }

    public void setWebappwebsitewh(String webappwebsitewh) {
        this.webappwebsitewh = webappwebsitewh;
    }

    public String getWebappversion() {
        return webappversion;
    }

    public void setWebappversion(String webappversion) {
        this.webappversion = webappversion;
    }

    public String getWebappversionname() {
        return webappversionname;
    }

    public void setWebappversionname(String webappversionname) {
        this.webappversionname = webappversionname;
    }

    public String getWebappoauth() {
        return webappoauth;
    }

    public void setWebappoauth(String webappoauth) {
        this.webappoauth = webappoauth;
    }

    public String getWebappsso() {
        return webappsso;
    }

    public void setWebappsso(String webappsso) {
        this.webappsso = webappsso;
    }

    public String getWebappscope() {
        return webappscope;
    }

    public void setWebappscope(String webappscope) {
        this.webappscope = webappscope;
    }

    public byte[] getWebappregisterinfo() {
        return webappregisterinfo;
    }

    public void setWebappregisterinfo(byte[] webappregisterinfo) {
        this.webappregisterinfo = webappregisterinfo;
    }

    public String getWebappos() {
        return webappos;
    }

    public void setWebappos(String webappos) {
        this.webappos = webappos;
    }

    public String getWebappshowtype() {
        return webappshowtype;
    }

    public void setWebappshowtype(String webappshowtype) {
        this.webappshowtype = webappshowtype;
    }

    public String getWebappinstallurl() {
        return webappinstallurl;
    }

    public void setWebappinstallurl(String webappinstallurl) {
        this.webappinstallurl = webappinstallurl;
    }

    public byte[] getWebappinstallorcode() {
        return webappinstallorcode;
    }

    public void setWebappinstallorcode(byte[] webappinstallorcode) {
        this.webappinstallorcode = webappinstallorcode;
    }

    public String getWebappsize() {
        return webappsize;
    }

    public void setWebappsize(String webappsize) {
        this.webappsize = webappsize;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getHandleuser() {
        return handleuser;
    }

    public void setHandleuser(String handleuser) {
        this.handleuser = handleuser;
    }

    public String getWebappkind() {
        return webappkind;
    }

    public void setWebappkind(String webappkind) {
        this.webappkind = webappkind;
    }

    public String getPicsmallpath() {
        return picsmallpath;
    }

    public void setPicsmallpath(String picsmallpath) {
        this.picsmallpath = picsmallpath;
    }

    public String getPicpath() {
        return picpath;
    }

    public void setPicpath(String picpath) {
        this.picpath = picpath;
    }

    public String getRegestinfopath() {
        return regestinfopath;
    }

    public void setRegestinfopath(String regestinfopath) {
        this.regestinfopath = regestinfopath;
    }

    public String getOrcodepath() {
        return orcodepath;
    }

    public void setOrcodepath(String orcodepath) {
        this.orcodepath = orcodepath;
    }

    public String getWebapppicsmallbase() {
        return webapppicsmallbase;
    }

    public void setWebapppicsmallbase(String webapppicsmallbase) {
        this.webapppicsmallbase = webapppicsmallbase;
    }

    public String getKindname() {
        return kindname;
    }

    public void setKindname(String kindname) {
        this.kindname = kindname;
    }

    public String getKindvalue() {
        return kindvalue;
    }

    public void setKindvalue(String kindvalue) {
        this.kindvalue = kindvalue;
    }

    public String getRegestinfobase() {
        return regestinfobase;
    }

    public void setRegestinfobase(String regestinfobase) {
        this.regestinfobase = regestinfobase;
    }

    public String getWebapppicbase() {
        return webapppicbase;
    }

    public void setWebapppicbase(String webapppicbase) {
        this.webapppicbase = webapppicbase;
    }

    public String getOrcodebase() {
        return orcodebase;
    }

    public void setOrcodebase(String orcodebase) {
        this.orcodebase = orcodebase;
    }
}
