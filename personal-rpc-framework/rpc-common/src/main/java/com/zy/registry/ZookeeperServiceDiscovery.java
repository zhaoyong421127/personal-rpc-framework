package com.zy.registry;

import java.util.List;


/**
 * 基于Zookeeper实现的服务发现者
 * @author zy   
 * @date 2016年10月23日 上午11:15:35
 */
public class ZookeeperServiceDiscovery implements ServiceDiscovery {

	public List<String> discoverServiceUrl(ServiceRegister serviceRegistry) {
		return serviceRegistry.discoverServiceUrl();
	}
	
	
	public static void main(String[] args) {
		ServiceDiscovery sd = new ZookeeperServiceDiscovery();
		ServiceRegister sr = new ZookeeperServiceRegister();
		sr.setRegistryAddress("127.0.0.1:2181");
		List<String> list = sd.discoverServiceUrl(sr);
		System.out.println(list);
		
	}
}
