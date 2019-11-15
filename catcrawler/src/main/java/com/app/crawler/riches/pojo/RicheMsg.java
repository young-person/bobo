package com.app.crawler.riches.pojo;

import java.io.Serializable;

/** 
 * @Description: TODO
 * @date 2019年11月1日 下午11:02:39 
 * @ClassName: RicheMsg 
 */
public abstract class RicheMsg<T> implements Serializable{
	/** 
	 * @Fields serialVersionUID : TODO
	 * @data 2019年11月1日 下午11:04:16 
	 */ 
	protected static final long serialVersionUID = -663026528170573505L;
	protected String errmsg;
	protected Integer status;
	
	protected T results;
	
	public String getErrmsg() {
		return errmsg;
	}
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public T getResults() {
		return results;
	}
	public void setResults(T results) {
		this.results = results;
	}
	@Override
	public String toString() {
		return "RicheMsg [errmsg=" + errmsg + ", status=" + status + ", results=" + results + "]";
	}
	
}
