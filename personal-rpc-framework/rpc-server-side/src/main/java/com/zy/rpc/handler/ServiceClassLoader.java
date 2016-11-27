package com.zy.rpc.handler;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zy.rpc.annotations.RpcService;

public class ServiceClassLoader {

	public static Map<String,Object> loadClass(Class<? extends Annotation> annotationClass,String... packages) throws Exception{
		Map<String,Object> clazzMap = new HashMap<String,Object>();
		if(packages != null && packages.length>0){
			List<String> pathList = new ArrayList<String>();
			//获取对应的类文件路径
			for(String pkg : packages){
				checkFileOrDir(getFilePath(pkg),pathList);
			}
			
			//根据文件路径去加载对应的类
			for(String path : pathList){
				Class<?> clazz = Class.forName(path);
				if(clazz.isAnnotation() || clazz.isInterface() || clazz.isAnonymousClass()){
					continue;
				}
				
				if(clazz.isAnnotationPresent(annotationClass)){
					Object instance =  clazz.newInstance();
					clazzMap.put(clazz.getInterfaces()[0].getName(), instance);
				}
				
			}
		}
		return clazzMap;
	}
	
	
	
	private static ClassLoader getClassLoader(){
		return ServiceClassLoader.class.getClassLoader();
	}
	
	private static void checkFileOrDir(String filePath,List<String> pathList) throws IOException{
		File file = new File(filePath);
		if(file.isFile()){
			String canonicalPath = file.getCanonicalPath();
			canonicalPath = canonicalPath.substring(0, canonicalPath.lastIndexOf("."));
			String packageName = canonicalPath.replace(getRootPath(), "").replaceAll("\\\\", ".");
			pathList.add(packageName);
		}else{
			File[] files = file.listFiles();
			if(files != null && files.length>0){
				for(File subFile : files){
					checkFileOrDir(subFile.getCanonicalPath(),pathList);
				}
			}
		}
	}
	
	
	private static String getRootPath(){
		return getClassLoader().getResource("").getPath().substring(1).replace("/", "\\");
	}
	
	private static String getFilePath(String pkg){
		String path = pkg.replaceAll("\\.", "/");
		String rootPath = getRootPath(); 
		String filePath = rootPath+File.separator+path;
		return filePath;
	}
}
