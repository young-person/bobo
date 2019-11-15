package com.app.crawler.riches.pojo;

import java.util.ArrayList;
import java.util.List;

/** 
 * @Description: TODO
 * @date 2019年11月3日 下午5:02:36 
 * @ClassName: ShareBean 每个股票的信息数据
 */
public class ShareBean {

	
	/**
	 * 股票代码
	 */
	private String code;
	/**
	 * 股票名称
	 */
	private String name;
	/**
	 * 股票类型
	 */
	private String codeType;
	/**
	 * 股票类型名称
	 */
	private String typeName;
	
	/**
	 * 股票历史信息
	 */
	private List<ShareInfo> datas = new ArrayList<>();

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCodeType() {
		return codeType;
	}

	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public List<ShareInfo> getDatas() {
		return datas;
	}

	public void setDatas(List<ShareInfo> datas) {
		this.datas = datas;
	}

	@Override
	public String toString() {
		return "ShareBean [code=" + code + ", name=" + name + ", codeType=" + codeType + ", typeName=" + typeName
				+ ", datas=" + datas + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((codeType == null) ? 0 : codeType.hashCode());
		result = prime * result + ((datas == null) ? 0 : datas.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((typeName == null) ? 0 : typeName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ShareBean other = (ShareBean) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (codeType == null) {
			if (other.codeType != null)
				return false;
		} else if (!codeType.equals(other.codeType))
			return false;
		if (datas == null) {
			if (other.datas != null)
				return false;
		} else if (!datas.equals(other.datas))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (typeName == null) {
			if (other.typeName != null)
				return false;
		} else if (!typeName.equals(other.typeName))
			return false;
		return true;
	}
	
	
}
