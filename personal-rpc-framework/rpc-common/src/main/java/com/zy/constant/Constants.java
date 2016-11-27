package com.zy.constant;

import java.nio.charset.Charset;

/**
 * 常量类
 * @author zy   
 * @date 2016年9月11日 下午7:53:14
 */
public class Constants {

	/**
	 * rpc服务默认注册路径
	 */
	public static final String REGISTRY_DEFAULT_PATH="/rpc";
	
	/**
	 * zookeeper数据节点路径
	 */
	public static final String ZOOKEEPER_DATA_PATH=REGISTRY_DEFAULT_PATH+"/data";
	
	/**
	 * 默认的编码为UTF-8
	 */
	public static final String DEFAULT_CHARSET=Charset.forName("UTF-8").name();
	
}
