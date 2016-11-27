package com.zy.registry;

import java.util.List;

/**
 * 服务注册组件
 * @author zy
 *
 */
public interface ServiceRegister {
	/**
	 * 注册方法
	 * @param 服务的发布地址 serverAddress
	 */
	void register(String serverAddress);
	
	/**
	 * 设置注册地址
	 * @param registryAddress注册中心地址
	 */
	void setRegistryAddress(String registryAddress);
	
	/**
	 * 发现服务地址列表
	 * @return
	 */
	List<String> discoverServiceUrl();
	
}
