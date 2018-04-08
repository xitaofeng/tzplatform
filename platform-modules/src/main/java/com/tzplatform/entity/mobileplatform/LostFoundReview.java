/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tzplatform.entity.mobileplatform;

import com.tzplatform.entity.common.PageDto;

import java.io.Serializable;
import java.util.Date;
/***
 * 失物招领 评论
*
 */
public class LostFoundReview extends PageDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String id;//主键
	private String lostFoundId;		// 标识
    private String review;//回复内容
    private String userId;// 回复者标识
    private String toUserId;//被回执者标识
	private Date replyTime;//回复时间

    private String toUserName; //被回复者姓名
	private String userName; //回复者姓名

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getLostFoundId() {
		return lostFoundId;
	}

	public void setLostFoundId(String lostFoundId) {
		this.lostFoundId = lostFoundId;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getToUserId() {
		return toUserId;
	}

	public void setToUserId(String toUserId) {
		this.toUserId = toUserId;
	}

	public Date getReplyTime() {
		return replyTime;
	}

	public void setReplyTime(Date replyTime) {
		this.replyTime = replyTime;
	}

	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}