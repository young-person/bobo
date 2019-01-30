package com.mybatis.pojo;

import com.bobo.annotation.Tree;

public class China {
	@Tree(text="text")
	private String name;
	@Tree(id="id")
	private String code;
	@Tree(pid="pid")
	private String parentcode;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code == null ? null : code.trim();
	}

	public String getParentcode() {
		return parentcode;
	}

	public void setParentcode(String parentcode) {
		this.parentcode = parentcode == null ? null : parentcode.trim();
	}
}