package com.tzplatform.entity.channel;

import com.tzplatform.entity.common.PageDto;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 栏目下内容对象
 *
 * @author leijie
 */
public class PlatFormChannelContent extends PageDto implements Serializable {

    private static final long serialVersionUID = 2970581972709754907L;

    private String id;//主键
    private String channelid;//栏目id
    private String contenttitle;//标题名称
    private String content;//内容
    private String author;//作者
    private Date createtime;//创建时间
    private Date updatetime;//更新时间
    private Integer sort;//排序

    private List<HashMap<String,String>> filelist;

    public List<HashMap<String, String>> getFilelist() {
        return filelist;
    }

    public void setFilelist(List<HashMap<String, String>> filelist) {
        this.filelist = filelist;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

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

    public String getContenttitle() {
        return contenttitle;
    }

    public void setContenttitle(String contenttitle) {
        this.contenttitle = contenttitle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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
