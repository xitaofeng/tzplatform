package com.tzplatform.entity.system;

import com.tzplatform.entity.common.PageDto;

import java.io.Serializable;
import java.util.Date;

/**
 * 角色对象
 *
 * @author leijie
 */
public class PlatFormRole extends PageDto implements Serializable {

    private static final long serialVersionUID = -3070613408224903671L;

    private String id;//主键
    private String rolename;//角色名称
    private String rolecode;//角色代码
    private String rolestate;//角色状态
    private String roletype;//角色类型
    private String rolenote;//备注
    private Date updatetime;//更新时间
    private Date createtime;//创建时间
    private String updateuser;//更新人
    private String createuser;//创建人
    private String delflag;//删除标记

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public String getRolecode() {
        return rolecode;
    }

    public void setRolecode(String rolecode) {
        this.rolecode = rolecode;
    }

    public String getRolestate() {
        return rolestate;
    }

    public void setRolestate(String rolestate) {
        this.rolestate = rolestate;
    }

    public String getRoletype() {
        return roletype;
    }

    public void setRoletype(String roletype) {
        this.roletype = roletype;
    }

    public String getRolenote() {
        return rolenote;
    }

    public void setRolenote(String rolenote) {
        this.rolenote = rolenote;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getUpdateuser() {
        return updateuser;
    }

    public void setUpdateuser(String updateuser) {
        this.updateuser = updateuser;
    }

    public String getCreateuser() {
        return createuser;
    }

    public void setCreateuser(String createuser) {
        this.createuser = createuser;
    }

    public String getDelflag() {
        return delflag;
    }

    public void setDelflag(String delflag) {
        this.delflag = delflag;
    }
}
