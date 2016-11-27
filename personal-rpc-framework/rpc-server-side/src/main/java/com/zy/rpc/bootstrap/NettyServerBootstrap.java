package com.zy.rpc.bootstrap;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.beans.factory.InitializingBean;

import com.zy.registry.ServiceRegister;
import com.zy.registry.ZookeeperServiceRegister;
import com.zy.rpc.handler.ChannelMessageHandler;
import com.zy.rpc.registry.ServiceRegistryProvider;

public class NettyServerBootstrap implements InitializingBean{

	/**
	 * 扫描包名
	 */
	private String[] packages;
	/**
	 * 启动时指定序列化方式
	 */
	private String serializer;
	
	/**
	 * 指定启动的端口
	 */
	private int port;
	
	/**
	 * 注册中心地址
	 */
	private String registryAddress;
	
	/**
	 * 默认采用Zookeeper作为服务注册中心
	 */
	private ServiceRegister serviceRegistry;
	public NettyServerBootstrap(String registryAddress) {
		this.serviceRegistry = new ZookeeperServiceRegister(registryAddress);
	}
	
	
	public String[] getPackages() {
		return packages;
	}

	public void setPackages(String[] packages) {
		this.packages = packages;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getSerializer() {
		return serializer;
	}

	public void setSerializer(String serializer) {
		this.serializer = serializer;
	}
	

	public String getRegistryAddress() {
		return registryAddress;
	}

	public void setRegistryAddress(String registryAddress) {
		this.registryAddress = registryAddress;
	}

	/**
	 * 启动服务器
	 * @throws InterruptedException 
	 */
	public void startServer(){
		NioEventLoopGroup boosGroup = new NioEventLoopGroup();  //这里可以设置线程池的大小，默认线程池的大小为CPU核数的2倍
		NioEventLoopGroup workGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap sb = new ServerBootstrap();
			sb.group(boosGroup,workGroup)
			  .channel(NioServerSocketChannel.class)
			  .option(ChannelOption.SO_BACKLOG, 1024)
			  .childHandler(new ChannelInitializer<NioSocketChannel>() {
				  protected void initChannel(NioSocketChannel ch) throws Exception {
					 
					  ch.pipeline().addLast(new ChannelMessageHandler(getSerializer()));
				  };
			});
			//启动时服务注册
			ChannelFuture channelFuture = sb.bind(port).sync();
			ServiceRegistryProvider.registerService(packages);
			registryServiceAddress();
			System.out.println("在"+port+"端口启动服务提供者...");
			channelFuture.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			boosGroup.shutdownGracefully();
			workGroup.shutdownGracefully();
		}
	}
	
	/**
	 * 注册服务地址
	 */
	private void registryServiceAddress(){
		try {
			String host = InetAddress.getLocalHost().getHostAddress();
			serviceRegistry.register(host+":"+port);
		} catch (UnknownHostException e) {
			System.out.println("获取服务端IP异常！");
			e.printStackTrace();
		}
		
	}
	
	public void afterPropertiesSet() throws Exception {
		startServer();		
	}
}
