package com.zy.serializer;

/**
 * 序列化工厂，用于根据启动时用户传入的序列化创建对应序列化对象
 * @author zy   
 * @date 2016年10月22日 下午9:36:58
 */
public final class SerializeFactory {

	private SerializeFactory(){}
	
	/**
	 * 默认为JDK序列化
	 * @param serializeMode
	 * @return
	 */
	public static ObjectSerializer getSerializeInstance(String serializeMode){
		if(SerializeModeEnum.JDK.name().equalsIgnoreCase(serializeMode)){
			return new JDKObjectSerializer();
		}
		
		return new JDKObjectSerializer();
	}
}
