package com.app.dictionary.templet.model;

import java.io.Serializable;
import java.util.List;
/**
 * 数据库信息
 * @author bobo
 *
 */
public class StoreRoom implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6763641313890304527L;
	private String storeName;
	private List<Table> tables;
	
	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public StoreRoom(){}
	public StoreRoom(String storeName){
		this.storeName = storeName;
	}

	public List<Table> getTables() {
		return tables;
	}

	public void setTables(List<Table> tables) {
		this.tables = tables;
	}
	
}
