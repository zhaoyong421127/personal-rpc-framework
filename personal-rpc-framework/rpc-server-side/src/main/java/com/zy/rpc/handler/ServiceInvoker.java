package com.zy.rpc.handler;

import java.lang.reflect.Method;

import com.zy.model.RpcRequest;
import com.zy.rpc.registry.ServiceRegistryProvider;

/**
 * 服务调用者
 * @author zy   
 * @date 2016年10月22日 下午9:52:19
 */
public class ServiceInvoker {

	/**
	 * 调用本地服务
	 * @param request
	 * @throws Exception 
	 */
	public static Object invokeService(RpcRequest request) throws Exception{
		String className = request.getClassName();
		String methodName = request.getMethodName();
		Object object = ServiceRegistryProvider.getServiceBean(className);
		Method method = object.getClass().getDeclaredMethod(methodName, request.getParamTypes());
		return method.invoke(object, request.getArguments());
	}
}
