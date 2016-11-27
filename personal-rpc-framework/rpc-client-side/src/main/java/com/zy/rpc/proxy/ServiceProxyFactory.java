package com.zy.rpc.proxy;

/**
 * 服务代理工厂
 * @author zy   
 * @date 2016年10月23日 上午11:55:07
 */
public interface ServiceProxyFactory {

	<T> T proxyService();
}
