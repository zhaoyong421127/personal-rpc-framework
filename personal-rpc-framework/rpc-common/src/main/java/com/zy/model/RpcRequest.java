package com.zy.model;

import java.io.Serializable;
import java.util.Arrays;

/**
 * RPC请求包装类
 * @author zy
 *
 */
public class RpcRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 492047532237210172L;

	/**
	 * 请求ID
	 */
	private String requestId;
	
	/**
	 * 请求接口名
	 */
	private String className;
	
	/**
	 * 请求方法名
	 */
	private String methodName;
	
	/**
	 * 请求参数类型
	 */
	private Class<?>[]  paramTypes;
	
	/**
	 * 请求参数
	 */
	private Object[]  arguments;

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public Class<?>[] getParamTypes() {
		return paramTypes;
	}

	public void setParamTypes(Class<?>[] paramTypes) {
		this.paramTypes = paramTypes;
	}

	public Object[] getArguments() {
		return arguments;
	}

	public void setArguments(Object[] arguments) {
		this.arguments = arguments;
	}

	@Override
	public String toString() {
		return "RpcRequest [requestId=" + requestId + ", className="
				+ className + ", methodName=" + methodName + ", paramTypes="
				+ Arrays.toString(paramTypes) + ", arguments="
				+ Arrays.toString(arguments) + "]";
	}
	
	
}
