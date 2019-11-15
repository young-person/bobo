package com.app.crawler.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/** 
 * @Description: TODO
 * @date 2019年6月29日 下午10:12:03 
 * @ClassName: CNode 
 */
public class CNode implements Serializable{
	
	/** 
	 * @Fields serialVersionUID : TODO
	 * @data 2019年6月29日 下午10:14:35 
	 */ 
	private static final long serialVersionUID = 677650248680036712L;

	private String url;
	
	private String code;
	private String name;
	/**
	 * 子节点
	 */
	private List<CNode> children = new ArrayList<>();

	private List<CNode> pNodes = new ArrayList<>();// 保存所有父节点和当前节点

	/**
	 * 父节点
	 */
	private CNode pNode;

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

	public List<CNode> getChildren() {
		return children;
	}

	public void setChildren(List<CNode> children) {
		this.children = children;
	}

	public CNode getpNode() {
		return pNode;
	}

	public void setpNode(CNode pNode) {
		this.pNode = pNode;
	}

	public List<CNode> getpNodes() {
		return pNodes;
	}

	public void setpNodes(List<CNode> pNodes) {
		this.pNodes = pNodes;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
