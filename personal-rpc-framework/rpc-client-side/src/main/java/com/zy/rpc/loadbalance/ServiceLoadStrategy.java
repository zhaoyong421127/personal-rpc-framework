package com.zy.rpc.loadbalance;

import java.util.List;

/**
 * 服务加载策略
 * @author zy   
 * @date 2016年10月23日 下午12:20:38
 */
public interface ServiceLoadStrategy {

	/**
	 * 获取服务Url地址
	 * @param serviceUrlList 服务地址列表
	 * @return
	 */
	String fetchServiceUrl(List<String> serviceUrlList);
}
