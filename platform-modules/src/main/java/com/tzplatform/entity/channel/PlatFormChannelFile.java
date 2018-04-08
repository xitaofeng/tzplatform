package com.tzplatform.entity.channel;

import com.tzplatform.entity.common.PageDto;

import java.io.Serializable;
import java.util.Date;

/**
 * 栏目内容附件
 *
 * @author leijie
 */
public class PlatFormChannelFile extends PageDto implements Serializable {

    private static final long serialVersionUID = 3807966291357191764L;

    private String id;//主键
    private String channelid;//内容id
    private String filename;//文件名称
    private String fileoldname;//文件原名称
    private String filepath;//文件路径
    private Date updatetime;//更新时间
    private Date createtime;//创建时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChannelid() {
        return channelid;
    }

    public void setChannelid(String channelid) {
        this.channelid = channelid;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFileoldname() {
        return fileoldname;
    }

    public void setFileoldname(String fileoldname) {
        this.fileoldname = fileoldname;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
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
}
