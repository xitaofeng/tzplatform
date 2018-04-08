package com.tzplatform.entity.api;

import com.tzplatform.entity.common.PageDto;

import java.io.Serializable;
import java.util.Date;

/**
 * 平台接口对象
 * @author  leijie
 */
public class PlatFormApi extends PageDto implements Serializable {

    private static final long serialVersionUID = 6948043889415581462L;

    private String id;//主键
    private String apittypeid;//所属类型id
    private String apiname;//接口名称
    private String apiintrouduce;//接口简介
    private String apicondition;//前置条件
    private String apiurl;//请求地址
    private String apireqway;//请求方式
    private String apireqtype;//请求格式
    private String apiauth;//是否需要授权
    private String apireqnumner;//请求次数限制
    private String apireqparam;//请求参数
    private String apireqresult;//请求结果
    private String apireqexample;//请求示例
    private Integer apiaccessnumtoday;//当日请求次数
    private Integer apiaccessall;//总请求次数
    private Date createtime;//创建时间
    private Date updatetime;//修改时间
    private String handleuser;//操作人

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApittypeid() {
        return apittypeid;
    }

    public void setApittypeid(String apittypeid) {
        this.apittypeid = apittypeid;
    }

    public String getApiname() {
        return apiname;
    }

    public void setApiname(String apiname) {
        this.apiname = apiname;
    }

    public String getApiintrouduce() {
        return apiintrouduce;
    }

    public void setApiintrouduce(String apiintrouduce) {
        this.apiintrouduce = apiintrouduce;
    }

    public String getApicondition() {
        return apicondition;
    }

    public void setApicondition(String apicondition) {
        this.apicondition = apicondition;
    }

    public String getApiurl() {
        return apiurl;
    }

    public void setApiurl(String apiurl) {
        this.apiurl = apiurl;
    }

    public String getApireqway() {
        return apireqway;
    }

    public void setApireqway(String apireqway) {
        this.apireqway = apireqway;
    }

    public String getApireqtype() {
        return apireqtype;
    }

    public void setApireqtype(String apireqtype) {
        this.apireqtype = apireqtype;
    }

    public String getApiauth() {
        return apiauth;
    }

    public void setApiauth(String apiauth) {
        this.apiauth = apiauth;
    }

    public String getApireqnumner() {
        return apireqnumner;
    }

    public void setApireqnumner(String apireqnumner) {
        this.apireqnumner = apireqnumner;
    }

    public String getApireqparam() {
        return apireqparam;
    }

    public void setApireqparam(String apireqparam) {
        this.apireqparam = apireqparam;
    }

    public String getApireqresult() {
        return apireqresult;
    }

    public void setApireqresult(String apireqresult) {
        this.apireqresult = apireqresult;
    }

    public String getApireqexample() {
        return apireqexample;
    }

    public void setApireqexample(String apireqexample) {
        this.apireqexample = apireqexample;
    }

    public Integer getApiaccessnumtoday() {
        return apiaccessnumtoday;
    }

    public void setApiaccessnumtoday(Integer apiaccessnumtoday) {
        this.apiaccessnumtoday = apiaccessnumtoday;
    }

    public Integer getApiaccessall() {
        return apiaccessall;
    }

    public void setApiaccessall(Integer apiaccessall) {
        this.apiaccessall = apiaccessall;
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
}
