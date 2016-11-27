package com.zy.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 日志工厂类
 * @author zy   
 * @date 2016年9月11日 下午8:32:19
 */
public final class LoggerFactoryUtil  {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	public void debug(String message,Object... args){
		 logger.debug(message, args);
	}
	
	public void info(String message,Object... args){
		 logger.info(message, args);
	}
	
	public void warn(String message,Object... args){
		 logger.warn(message, args);
	}
	
	public void error(String message,Object... args){
		 logger.error(message, args);
	}
}
