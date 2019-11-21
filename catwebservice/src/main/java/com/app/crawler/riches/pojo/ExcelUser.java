package com.app.crawler.riches.pojo;

import com.bobo.annotation.RName;

public class ExcelUser{

	@RName("唯一值")
	private String id;
	@RName("用户名称")
	private String userName;
	@RName("密码")
	private String passWord;
	@RName("账号")
	private String account;
	@RName("邮件")
	private String email;
	@RName("是否禁用")
	private String forbidden;
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
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getForbidden() {
		return forbidden;
	}
	public void setForbidden(String forbidden) {
		this.forbidden = forbidden;
	}
	@Override
	public String toString() {
		return "ExcelUser [id=" + id + ", userName=" + userName + ", passWord=" + passWord + ", account=" + account
				+ ", email=" + email + ", forbidden=" + forbidden + "]";
	}
	
}
