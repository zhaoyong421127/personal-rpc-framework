package com.zy.serializer;

/**
 * 对象序列化接口
 * @author zy   
 * @date 2016年10月22日 下午9:12:18
 */
public interface ObjectSerializer {

	/**
	 * 序列化方法
	 * @param obj
	 * @return
	 */
	byte[] serialize(Object obj);
	
	/**
	 * 反序列化
	 * @param msg
	 * @return
	 */
	Object derialize(byte[] msg);
}
