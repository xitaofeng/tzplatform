/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tzplatform.entity.mobileplatform;


import com.tzplatform.entity.common.PageDto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 意见反馈
 */
public class Feedback extends PageDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String id;//
	private String name;		
	private String description;		// 描述
	private String delflag;		// 是否删除
	private String userId;//反馈用户标识
	private String opinionTypeId;
	private Date opinionTime;
	private String typeName;
	private String schoolId;//学校标识
	private String userName;
	private String schoolName;
	private String imagePath;//图片
	private String status;//是否解决
    private String account;//
    private String remark;//备注，及修改状态原因

	private List<String> imageUrl;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDelflag() {
		return delflag;
	}

	public void setDelflag(String delflag) {
		this.delflag = delflag;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOpinionTypeId() {
		return opinionTypeId;
	}

	public void setOpinionTypeId(String opinionTypeId) {
		this.opinionTypeId = opinionTypeId;
	}

	public Date getOpinionTime() {
		return opinionTime;
	}

	public void setOpinionTime(Date opinionTime) {
		this.opinionTime = opinionTime;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<String> getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(List<String> imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}