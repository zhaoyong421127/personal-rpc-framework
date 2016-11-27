package com.zy.registry;

import java.util.List;

/**
 * 服务发现者接口
 * @author zy   
 * @date 2016年10月23日 上午11:13:17
 */
public interface ServiceDiscovery {

	/**
	 * 发现服务地址列表
	 * @param serviceRegistry 服务注册中心
	 * @return
	 */
	List<String> discoverServiceUrl(ServiceRegister serviceRegistry);
}
