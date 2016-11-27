package com.zy.exception;

/**
 * 没有找到服务异常
 * @author zy   
 * @date 2016年10月23日 下午12:14:56
 */
public class NotFoundServiceException extends RuntimeException {
	
	private String message;

	public NotFoundServiceException(String message) {
		super(message);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
