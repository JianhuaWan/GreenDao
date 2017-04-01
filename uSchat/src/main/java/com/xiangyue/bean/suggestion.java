package com.xiangyue.bean;

import cn.bmob.v3.BmobObject;

@SuppressWarnings("serial")
public class suggestion extends BmobObject {
	private String username;
	private String content;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
