package com.zy.rpc.registry;

import java.util.HashMap;
import java.util.Map;

import com.zy.rpc.annotations.RpcService;
import com.zy.rpc.handler.ServiceClassLoader;

/**
 * 服务注册中心
 * @author zy   
 * @date 2016年10月22日 下午9:55:26
 */
public class ServiceRegistryProvider {

	/**
	 * 缓存服务列表
	 */
	public static  Map<String,Object> serviceCache = new HashMap<String,Object>();
	public ServiceRegistryProvider() {
		
	}
	
	public static void registerService(String... packages){
		try {
			serviceCache = ServiceClassLoader.loadClass(RpcService.class, packages);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Object getServiceBean(String interfaceName){
		return serviceCache.get(interfaceName);
	}
	
}
