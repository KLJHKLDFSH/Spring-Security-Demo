package com.example.demo.db.model;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName("userinfo")
public class UserInfo {
	private String userId;
	private String userName;

	public UserInfo(String userId, String userName) {
		this.userId = userId;
		this.userName = userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
