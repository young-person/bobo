package com.mybatis.pojo;

public class Dbs {
	/**
	 * 唯一id
	 */
	private String id;
	/**
	 * 所属创建用户
	 */
	private String user;
	/**
	 * 数据库名称唯一值
	 */
	private String dbname;
	/**
	 * 数据库类型
	 */
	private String type;


	/**
	 * 数据库驱动
	 */
	private String driver;
	/**
	 * 
	 * 数据库链接
	 */
	private String url;

	/**
	 * 数据库链接用户
	 */
	private String username;
	/**
	 * 链接密码
	 */
	private String password;
	/**
	 * 简介
	 */
	private String commont;


	public Dbs(String id, String user, String dbname, String type, String url, String username, String password) {
		this.id = id;
		this.user = user;
		this.dbname= dbname;
		this.type= type;
		this.url = url;
		this.username = username;
		this.password = password;
	}
	public Dbs(String id, String user, String dbname, String type, String driver, String url, String username, String password) {
		this.id = id;
		this.user = user;
		this.dbname= dbname;
		this.type= type;
		this.driver = driver;
		this.url = url;
		this.username = username;
		this.password = password;
	}
	public Dbs() {
	}

	public Dbs(String driver, String url, String username, String password) {
		this.driver = driver;
		this.url = url;
		this.username = username;
		this.password = password;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user == null ? null : user.trim();
	}

	public String getDbname() {
		return dbname;
	}

	public void setDbname(String dbname) {
		this.dbname = dbname == null ? null : dbname.trim();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type == null ? null : type.trim();
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url == null ? null : url.trim();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username == null ? null : username.trim();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password == null ? null : password.trim();
	}

	public String getCommont() {
		return commont;
	}

	public void setCommont(String commont) {
		this.commont = commont == null ? null : commont.trim();
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver == null ? null : driver.trim();
	}
}