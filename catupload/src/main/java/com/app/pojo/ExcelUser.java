package com.app.pojo;

import java.io.Serializable;

public class ExcelUser implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1329484220787134419L;
	private String id;
	private String userName;
	private String passWord;
	private String account;
	private String email;
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
