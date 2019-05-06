package com.bobo.domain;
/**
 *  统一响应结构
 * @author bobo
 *
 */
public class ResultMeta {
	private static final String OK = "ok";
	private static final String ERROR = "error";
	private Object data;
	private boolean success = true;
	private String message = OK;

	public ResultMeta success() {
		return this;
	}

	public ResultMeta success(Object data) {
		this.data = data;
		return this;
	}

	public ResultMeta failure() {
		this.message = ERROR;
		this.success = false;
		return this;
	}

	public ResultMeta failure(String message) {
		this.message = message;
		this.success = false;
		return this;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Object getData() {
		return data;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
