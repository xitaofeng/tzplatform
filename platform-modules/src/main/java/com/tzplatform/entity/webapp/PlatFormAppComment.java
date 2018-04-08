package com.tzplatform.entity.webapp;

import com.tzplatform.entity.common.PageDto;

import java.io.Serializable;
import java.util.Date;
/**
 * 平台应用评论对象
 * @author  leijie
 */
public class PlatFormAppComment extends PageDto implements Serializable {

    private static final long serialVersionUID = 6171600858984284326L;

    private String id;//主键
    private String appid;//应用id
    private String commenttype;//评论类型
    private String commentcontent;//评论内容
    private String commentuser;//评论人
    private String commentstart;//评论星级
    private Date createtime;//创建时间
    private Date updatetime;//更新时间

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

    public String getCommenttype() {
        return commenttype;
    }

    public void setCommenttype(String commenttype) {
        this.commenttype = commenttype;
    }

    public String getCommentcontent() {
        return commentcontent;
    }

    public void setCommentcontent(String commentcontent) {
        this.commentcontent = commentcontent;
    }

    public String getCommentuser() {
        return commentuser;
    }

    public void setCommentuser(String commentuser) {
        this.commentuser = commentuser;
    }

    public String getCommentstart() {
        return commentstart;
    }

    public void setCommentstart(String commentstart) {
        this.commentstart = commentstart;
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
}
