package com.tzplatform.entity.system;

import com.tzplatform.entity.common.PageDto;

import java.io.Serializable;
import java.util.Date;

public class PlatFormNormalLog extends PageDto implements Serializable {
    private static final long serialVersionUID = -7583256172442810817L;
    private String id;
    private String handleaccount;
    private String handlename;
    private String handlemenu;
    private String handlepoint;
    private String handledescription;
    private String handleresult;
    private Date handletime;
    private Date createtime;
    //用户账号
    private String account;
    private String name;

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

    public PlatFormNormalLog() {
    }

    public PlatFormNormalLog(String id, String handleaccount, String handlename, String handlemenu, String handlepoint, String handledescription, String handleresult, Date handletime, Date createtime) {
        this.id = id;
        this.handleaccount = handleaccount;
        this.handlename = handlename;
        this.handlemenu = handlemenu;
        this.handlepoint = handlepoint;
        this.handledescription = handledescription;
        this.handleresult = handleresult;
        this.handletime = handletime;
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

    public String getHandlename() {
        return handlename;
    }

    public void setHandlename(String handlename) {
        this.handlename = handlename;
    }

    public String getHandlemenu() {
        return handlemenu;
    }

    public void setHandlemenu(String handlemenu) {
        this.handlemenu = handlemenu;
    }

    public String getHandlepoint() {
        return handlepoint;
    }

    public void setHandlepoint(String handlepoint) {
        this.handlepoint = handlepoint;
    }

    public String getHandledescription() {
        return handledescription;
    }

    public void setHandledescription(String handledescription) {
        this.handledescription = handledescription;
    }

    public String getHandleresult() {
        return handleresult;
    }

    public void setHandleresult(String handleresult) {
        this.handleresult = handleresult;
    }

    public Date getHandletime() {
        return handletime;
    }

    public void setHandletime(Date handletime) {
        this.handletime = handletime;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}
