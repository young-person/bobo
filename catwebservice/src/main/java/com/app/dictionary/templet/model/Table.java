package com.app.dictionary.templet.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
/**
 * 数据字典表格信息
 * @author bobo
 *
 */
public class Table implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8862482993532404069L;

	private String tableName;
	
	private List<Form> forms;
	
	public Table(String tableName){
		this.setTableName(tableName);
	}
	public Table(String tableName,List<Form> forms){
		this.setTableName(tableName);
		this.setForms(forms);
	}
	
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public List<Form> getForms() {
		return forms;
	}
	public void setForms(List<Form> forms) {
		Collections.sort(forms, new Comparator<Form>() {
			public int compare(Form o1, Form o2) {
				return o1.getColumnName().compareTo(o2.getColumnName());
			};
		});
		this.forms = forms;
	}
}
