package com.anjoy.cloud.component.result;

import java.io.Serializable;

/**
 * 服务层返回
 * 
 * @param <T>
 */
public class ServiceResponse<T> implements Serializable {

	private static final long serialVersionUID = 2488663702267110932L;

	private String code;

	private String msg;

	private String detail;

	private T result;

	public ServiceResponse(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public ServiceResponse() {
	}

	public ServiceResponse(String code, String msg, String detail) {
		this.code = code;
		this.msg = msg;
		this.detail = detail;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}
}
