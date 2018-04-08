/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tzplatform.entity.mobileplatform;


import com.tzplatform.entity.common.PageDto;

import java.io.Serializable;

/**
 * 意见类型Entity
 *
 */
public class FeedBackType extends PageDto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String id;//主键
	private String name;		// 名称
	private String description;		// 描述
	private String delflag;		// 是否删除

	private String userName;//用户名称

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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}