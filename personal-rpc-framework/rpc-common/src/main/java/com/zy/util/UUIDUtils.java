package com.zy.util;

import java.util.UUID;

/**
 * UUID生成器
 * @author zy   
 * @date 2016年10月23日 下午12:05:04
 */
public class UUIDUtils {

	public static String randomUUID(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
