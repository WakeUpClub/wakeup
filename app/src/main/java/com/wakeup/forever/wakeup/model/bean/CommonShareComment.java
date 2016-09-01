package com.wakeup.forever.wakeup.model.bean;

import org.litepal.crud.DataSupport;

public class CommonShareComment extends DataSupport {
	
	private Integer id;
	
	private Integer commonShareId;
	
	private String userPhone;
	
	private String comment;
	
	private Long createTime;

	public CommonShareComment() {
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCommonShareId() {
		return commonShareId;
	}

	public void setCommonShareId(Integer commonShareId) {
		this.commonShareId = commonShareId;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
}
