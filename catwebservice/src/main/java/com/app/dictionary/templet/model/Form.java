package com.app.dictionary.templet.model;

import java.io.Serializable;
/**
 * 数据字典表格
 * @author bobo
 *
 */
public class Form implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7633104858609204285L;
	private String columnName;
	private String columnType;
	private String columnDetail;
	private String columnRemark;
	private String BASEID;
	private String length;
	private String non = "N";

	public Form() {}
	public Form(String columnName, String columnType, String columnDetail,
			String columnRemark, String BASEID) {
		super();
		this.columnName = columnName;
		this.columnType = columnType;
		this.columnDetail = columnDetail;
		this.columnRemark = columnRemark;
		this.BASEID = BASEID;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getColumnType() {
		return columnType;
	}
	@Override
	public String toString() {
		return "Form [columnName=" + columnName + ", columnType=" + columnType
				+ ", columnDetail=" + columnDetail + ", columnRemark="
				+ columnRemark + ", BASEID=" + BASEID + "]";
	}
	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}
	public String getColumnDetail() {
		return columnDetail;
	}
	public void setColumnDetail(String columnDetail) {
		this.columnDetail = columnDetail;
	}
	public String getColumnRemark() {
		return columnRemark;
	}
	public void setColumnRemark(String columnRemark) {
		this.columnRemark = columnRemark;
	}
	public String getBASEID() {
		return BASEID;
	}
	public void setBASEID(String bASEID) {
		BASEID = bASEID;
	}
	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}
	public String getNon() {
		return non;
	}

	public void setNon(String non) {
		this.non = non;
	}
}
