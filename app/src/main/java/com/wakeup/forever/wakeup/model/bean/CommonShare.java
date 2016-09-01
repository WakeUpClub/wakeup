package com.wakeup.forever.wakeup.model.bean;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;

public class CommonShare extends DataSupport{
	
	private Integer id;

	private String userPhone;
	
	private String content;

	private User user;

	private String imageDesc;
	
	private Integer viewCount;
	
	private Long publishTime;
	
	private ArrayList<CommonShareLike> likedList;
	
	private ArrayList<CommonShareComment> commentList;
	
	
	
	public CommonShare() {
		// TODO Auto-generated constructor stub
	}



	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	public String getUserPhone() {
		return userPhone;
	}



	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}



	public String getContent() {
		return content;
	}



	public void setContent(String content) {
		this.content = content;
	}



	public String getImageDesc() {
		return imageDesc;
	}



	public void setImageDesc(String imageDesc) {
		this.imageDesc = imageDesc;
	}



	public Integer getViewCount() {
		return viewCount;
	}



	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}


	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Long publishTime) {
		this.publishTime = publishTime;
	}

	public ArrayList<CommonShareLike> getLikedList() {
		return likedList;
	}



	public void setLikedList(ArrayList<CommonShareLike> likedList) {
		this.likedList = likedList;
	}



	public ArrayList<CommonShareComment> getCommentList() {
		return commentList;
	}



	public void setCommentList(ArrayList<CommonShareComment> commentList) {
		this.commentList = commentList;
	}


}
