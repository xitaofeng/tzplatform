package com.tzplatform.entity.system;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户角色对应关系
 *
 * @author leijie
 */
public class PlatFormUserRole implements Serializable {
    private static final long serialVersionUID = 1650646871068486612L;

    private String id;//主键
    private String userid;//用户id
    private String roleid;//角色id
    private Date createtime;//创建时间
    private String createuser;//创建人


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getCreateuser() {
        return createuser;
    }

    public void setCreateuser(String createuser) {
        this.createuser = createuser;
    }
}
