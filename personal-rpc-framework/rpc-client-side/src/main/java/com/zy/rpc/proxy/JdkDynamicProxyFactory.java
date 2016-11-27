package com.zy.rpc.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;

import com.zy.model.RpcRequest;
import com.zy.registry.ServiceDiscovery;
import com.zy.registry.ServiceRegister;
import com.zy.registry.ZookeeperServiceDiscovery;
import com.zy.registry.ZookeeperServiceRegister;
import com.zy.rpc.transport.NettyClientTransport;
import com.zy.serializer.SerializeModeEnum;
import com.zy.util.UUIDUtils;

public class JdkDynamicProxyFactory implements ServiceProxyFactory,InitializingBean,BeanFactoryAware {

	private DefaultListableBeanFactory bf ;
	
	
    /*
	 * 服务发现者
	 */
	private ServiceDiscovery serviceDiscovery;
	private ServiceRegister ServiceRegister;
	
	private Class<?>[] targetClass;
	
	/**
	 * 序列化模式
	 */
	private String serializeMode;
	
	private Object proxyObject;
	
	
	public JdkDynamicProxyFactory() {
	}
	public JdkDynamicProxyFactory(String registryAddress) {
		this(registryAddress,SerializeModeEnum.JDK.name());
		//proxyService(targetClass);
	}
	
	public JdkDynamicProxyFactory(
			String registryAddress,
			String serializeMode) {
		ServiceRegister = new ZookeeperServiceRegister(registryAddress);
		this.serializeMode = serializeMode;
	}

	public ServiceRegister getServiceRegister() {
		return ServiceRegister;
	}

	public void setServiceRegister(ServiceRegister serviceRegister) {
		ServiceRegister = serviceRegister;
	}

	public Object getProxyObject() {
		return proxyObject;
	}
	public Class<?>[] getTargetClass() {
		return targetClass;
	}
	public void setTargetClass(Class<?>[] targetClass) {
		this.targetClass = targetClass;
	}
	public <T> T proxyService() {
		Object object = Proxy.newProxyInstance(JdkDynamicProxyFactory.class.getClassLoader(),targetClass, new JdkInvocationHandler());
		return (T) object;
	}

 private class JdkInvocationHandler implements InvocationHandler{
		public Object invoke(Object proxy, Method method, Object[] args)
				throws Throwable {
			RpcRequest rpcRequest = new RpcRequest();
			rpcRequest.setRequestId(UUIDUtils.randomUUID());
			rpcRequest.setClassName(method.getDeclaringClass().getName());
			rpcRequest.setArguments(args);
			rpcRequest.setMethodName(method.getName());
			rpcRequest.setParamTypes(method.getParameterTypes());
			NettyClientTransport transport = buildTransport();
			transport.startClient(rpcRequest);
			return transport.getRpcResponse().getResult();
		}
		
		private NettyClientTransport buildTransport(){
			serviceDiscovery = new ZookeeperServiceDiscovery();
			NettyClientTransport transport = new NettyClientTransport(serviceDiscovery,ServiceRegister);
			transport.setSerializeMode(serializeMode);
			return transport;
		}
	}
 
 public void afterPropertiesSet() throws Exception {
	 proxyObject = proxyService();
	 if(targetClass != null){
		 for (Class<?> targetClazz  : targetClass) {
			 String className =targetClazz.getName();
			 String beanName = className.substring(className.lastIndexOf(".")+1);
			 bf.registerSingleton(beanName, proxyObject);
		 }
	 }
}
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
	  this.bf = (DefaultListableBeanFactory) beanFactory;
	}

}
