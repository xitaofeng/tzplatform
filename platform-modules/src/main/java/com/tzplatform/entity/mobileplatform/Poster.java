package com.tzplatform.entity.mobileplatform;

import com.tzplatform.entity.common.PageDto;

import java.io.Serializable;
import java.util.Date;

/**
 * 海报实体类
 */
public class Poster extends PageDto implements Serializable {

    private String id;//主键
    private String title;//标题
    private String position;//显示位置
    private String content;//内容
    private String imagePath;//海报图片
    private transient byte[] image;//应用图标64*64
    private String status;//状态
    private Date createTime;//促案件
    private Date updateTime;//更新时间
    private String schoolId;//学校标识
    private String range;//范围 1 区级 2 校级

    private String baseImage;//base64图片
    public String getId() {
        return id;
    }

    public String getBaseImage() {
        return baseImage;
    }

    public void setBaseImage(String baseImage) {
        this.baseImage = baseImage;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
