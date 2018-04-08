package com.tzplatform.entity.api;

import com.tzplatform.entity.common.PageDto;

import java.io.Serializable;
import java.util.Date;

/**
 * 接口访问记录日志
 *
 * @author leijie
 */
public class PlatFormApiHandleLog extends PageDto implements Serializable {

    private static final long serialVersionUID = 4947780908401353570L;

    private String id;//主键
    private String apiiid;//接口id
    private String apiname;//接口名称
    private String appkeyid;//请求开发者id
    private String appname;//访问应用名称
    private String appdevname;//开发者姓名
    private Date apireqtime;//请求时间
    private Long apireqcounttime;//请求时长
    private String apireqresult;//请求结果

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApiiid() {
        return apiiid;
    }

    public void setApiiid(String apiiid) {
        this.apiiid = apiiid;
    }

    public String getApiname() {
        return apiname;
    }

    public void setApiname(String apiname) {
        this.apiname = apiname;
    }

    public String getAppkeyid() {
        return appkeyid;
    }

    public void setAppkeyid(String appkeyid) {
        this.appkeyid = appkeyid;
    }

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public String getAppdevname() {
        return appdevname;
    }

    public void setAppdevname(String appdevname) {
        this.appdevname = appdevname;
    }

    public Date getApireqtime() {
        return apireqtime;
    }

    public void setApireqtime(Date apireqtime) {
        this.apireqtime = apireqtime;
    }

    public Long getApireqcounttime() {
        return apireqcounttime;
    }

    public void setApireqcounttime(Long apireqcounttime) {
        this.apireqcounttime = apireqcounttime;
    }

    public String getApireqresult() {
        return apireqresult;
    }

    public void setApireqresult(String apireqresult) {
        this.apireqresult = apireqresult;
    }
}
