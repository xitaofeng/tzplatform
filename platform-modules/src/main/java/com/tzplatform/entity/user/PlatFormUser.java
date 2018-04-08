package com.tzplatform.entity.user;

import com.tzplatform.entity.common.PageDto;

import java.io.File;
import java.io.Serializable;
import java.sql.Blob;
import java.util.Date;

public class PlatFormUser extends PageDto implements Serializable {
    private static final long serialVersionUID = -3168497668060618509L;

    private String id;//主键
    private String name;//开发者姓名
    private String account;//登录帐号
    private String sex;//开发者性别
    private String password;//登录密码
    private String passsalt;//密码盐值
    private String usertype;//用户类型
    private String telphone;//手机号
    private String address;//开发者联系地址
    private String email;//开发者邮箱
    private String idcard;//身份证号
    private transient byte[] idcardpicture;//身份证扫描件
    private transient String idcardpicturepath;//身份证扫描件磁盘地址
    private String companyname;//公司名称
    private String companyaddress;//公司地址
    private transient byte[] companycard;//营业执照扫描件
    private transient String companycardpath;//营业执照扫描件磁盘地址
    private String companyleader;//公司法人姓名
    private String companyleaderidcard;//法人身份证号
    private transient byte[] companyleadercardtype;//法人身份证扫描件
    private transient String companyleadercardtypepath;//法人身份证扫描件磁盘地址
    private String companyleaderphone;//法人手机号
    private String companywebsite;//公司网站
    private String companyidcard;//公司营业执照号
    private String companyfax;//公司传真号
    private Date createtime;//创建时间
    private Date updatetime;//更新时间
    private String handleuser;//操作人
    private String userstatus;//开发者注册状态 0账号注册; 1邮箱激活; 2信息登记完成

    //辅助字段
    private String idcardbase;//身份证base64
    private String companycardbase;//营业执照base64
    private String companyleadercardbase;//法人身份证base

    public String getIdcardbase() {
        return idcardbase;
    }

    public void setIdcardbase(String idcardbase) {
        this.idcardbase = idcardbase;
    }

    public String getCompanycardbase() {
        return companycardbase;
    }

    public void setCompanycardbase(String companycardbase) {
        this.companycardbase = companycardbase;
    }

    public String getCompanyleadercardbase() {
        return companyleadercardbase;
    }

    public void setCompanyleadercardbase(String companyleadercardbase) {
        this.companyleadercardbase = companyleadercardbase;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasssalt() {
        return passsalt;
    }

    public void setPasssalt(String passsalt) {
        this.passsalt = passsalt;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public byte[] getIdcardpicture() {
        return idcardpicture;
    }

    public void setIdcardpicture(byte[] idcardpicture) {
        this.idcardpicture = idcardpicture;
    }

    public String getIdcardpicturepath() {
        return idcardpicturepath;
    }

    public void setIdcardpicturepath(String idcardpicturepath) {
        this.idcardpicturepath = idcardpicturepath;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public String getCompanyaddress() {
        return companyaddress;
    }

    public void setCompanyaddress(String companyaddress) {
        this.companyaddress = companyaddress;
    }

    public byte[] getCompanycard() {
        return companycard;
    }

    public void setCompanycard(byte[] companycard) {
        this.companycard = companycard;
    }

    public String getCompanycardpath() {
        return companycardpath;
    }

    public void setCompanycardpath(String companycardpath) {
        this.companycardpath = companycardpath;
    }

    public String getCompanyleader() {
        return companyleader;
    }

    public void setCompanyleader(String companyleader) {
        this.companyleader = companyleader;
    }

    public String getCompanyleaderidcard() {
        return companyleaderidcard;
    }

    public void setCompanyleaderidcard(String companyleaderidcard) {
        this.companyleaderidcard = companyleaderidcard;
    }

    public byte[] getCompanyleadercardtype() {
        return companyleadercardtype;
    }

    public void setCompanyleadercardtype(byte[] companyleadercardtype) {
        this.companyleadercardtype = companyleadercardtype;
    }

    public String getCompanyleadercardtypepath() {
        return companyleadercardtypepath;
    }

    public void setCompanyleadercardtypepath(String companyleadercardtypepath) {
        this.companyleadercardtypepath = companyleadercardtypepath;
    }

    public String getCompanyleaderphone() {
        return companyleaderphone;
    }

    public void setCompanyleaderphone(String companyleaderphone) {
        this.companyleaderphone = companyleaderphone;
    }

    public String getCompanywebsite() {
        return companywebsite;
    }

    public void setCompanywebsite(String companywebsite) {
        this.companywebsite = companywebsite;
    }

    public String getCompanyidcard() {
        return companyidcard;
    }

    public void setCompanyidcard(String companyidcard) {
        this.companyidcard = companyidcard;
    }

    public String getCompanyfax() {
        return companyfax;
    }

    public void setCompanyfax(String companyfax) {
        this.companyfax = companyfax;
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

    public String getUserstatus() {
        return userstatus;
    }

    public void setUserstatus(String userstatus) {
        this.userstatus = userstatus;
    }
}
