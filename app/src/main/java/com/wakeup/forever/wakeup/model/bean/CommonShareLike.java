package com.wakeup.forever.wakeup.model.bean;

import org.litepal.crud.DataSupport;

public class CommonShareLike extends DataSupport {

	private Integer id;
	
	private Integer commonShareId;
	
	private String userPhone;
	
	private Long createTime;
	
	private String userName;
	
	public CommonShareLike() {
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

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
}
