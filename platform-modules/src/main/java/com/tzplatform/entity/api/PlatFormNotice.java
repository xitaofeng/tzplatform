package com.tzplatform.entity.api;

import com.tzplatform.entity.common.PageDto;

import java.io.Serializable;
import java.util.Date;

public class PlatFormNotice extends PageDto implements Serializable {

    private static final long serialVersionUID = 6871485624534195973L;
    private String id;
    private String title;
    private String content;
    private String showstatus;
    private Date createtime;
    private Date updatetime;
    private String createuser;
    //公告类型
    private String noticetype;

    public String getNoticetype() {
        return noticetype;
    }

    public void setNoticetype(String noticetype) {
        this.noticetype = noticetype;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getShowstatus() {
        return showstatus;
    }

    public void setShowstatus(String showstatus) {
        this.showstatus = showstatus;
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

    public String getCreateuser() {
        return createuser;
    }

    public void setCreateuser(String createuser) {
        this.createuser = createuser;
    }
}
