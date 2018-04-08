/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tzplatform.entity.mobileplatform;

import com.tzplatform.entity.common.PageDto;

import java.io.Serializable;
/**
 * 校园风光 类型
 *
 */
public class CampusSceneryType extends PageDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String id;
	private String typeName;		// 类型名称
	private String description;		// 类型描述
	private String image;		// 封面图片

	private String imageUrl;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}