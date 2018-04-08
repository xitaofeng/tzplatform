package com.tzplatform.entity.webapp;

import com.tzplatform.entity.common.PageDto;

import java.io.Serializable;

public class PlatFormWepAppFile extends PageDto implements Serializable {
    private static final long serialVersionUID = -4937788018908858978L;
    private String id;
    private String appid;//应用id
    private String filepath;//附件路径
    private String filename;//附件名称
    private String fileorder;//附件排序

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

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFileorder() {
        return fileorder;
    }

    public void setFileorder(String fileorder) {
        this.fileorder = fileorder;
    }
}
