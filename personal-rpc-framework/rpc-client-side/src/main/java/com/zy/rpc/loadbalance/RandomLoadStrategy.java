package com.zy.rpc.loadbalance;

import java.util.List;
import java.util.Random;

/**
 * 随机加载策略
 * @author zy   
 * @date 2016年10月23日 下午12:23:27
 */
public class RandomLoadStrategy implements ServiceLoadStrategy{

	private Random r =  new Random();
	public String fetchServiceUrl(List<String> serviceUrlList) {
		int index = r.nextInt(serviceUrlList.size());
		return serviceUrlList.get(index);
	}
	
}
