package com.zy.model;

import java.io.Serializable;

public class RpcResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5929029764419078850L;

	/**
	 * 响应ID
	 */
	private String responseId;
	
	/**
	 * 返回的异常
	 */
	private Throwable error;
	
	/**
	 * 返回的结果信息
	 */
	private Object result;

	public String getResponseId() {
		return responseId;
	}

	public void setResponseId(String responseId) {
		this.responseId = responseId;
	}

	public Throwable getError() {
		return error;
	}

	public void setError(Throwable error) {
		this.error = error;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "RpcResponse [responseId=" + responseId + ", result=" + result + "]";
	}
	
	
}
