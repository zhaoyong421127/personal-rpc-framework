package com.zy.serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * JDK中对象序列化器
 * @author zy   
 * @date 2016年10月22日 下午9:17:02
 */
public class JDKObjectSerializer implements ObjectSerializer{

	public byte[] serialize(Object obj) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(bos);
			oos.writeObject(obj);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(oos != null){
				try {
					oos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return bos.toByteArray();
	}

	public Object derialize(byte[] msg) {
		ByteArrayInputStream bis = new ByteArrayInputStream(msg);
		ObjectInputStream ois = null;
		Object object = null;
		try {
			ois = new ObjectInputStream(bis);
			object = ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(ois != null){
				try {
					ois.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return object;
	}

}
