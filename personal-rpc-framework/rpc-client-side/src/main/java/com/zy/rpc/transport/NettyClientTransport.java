package com.zy.rpc.transport;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.List;

import org.springframework.util.CollectionUtils;

import com.zy.exception.NotFoundServiceException;
import com.zy.model.RpcRequest;
import com.zy.model.RpcResponse;
import com.zy.registry.ServiceDiscovery;
import com.zy.registry.ServiceRegister;
import com.zy.rpc.handler.ChannelMessageHandler;
import com.zy.rpc.loadbalance.RandomLoadStrategy;
import com.zy.rpc.loadbalance.ServiceLoadStrategy;

/**
 * 客户端传输层
 * @author zy   
 * @date 2016年10月23日 上午11:39:52
 */
public class NettyClientTransport {

	/**
	 * 服务发现者
	 */
	private ServiceDiscovery serviceDiscovery;
	private ServiceRegister ServiceRegister;
	private String serializeMode;
	/**
	 * 服务加载策略
	 */
	private ServiceLoadStrategy serviceLoadStrategy;
	
	/**
	 * Rpc请求的结果
	 */
	private RpcResponse rpcResponse;
	public NettyClientTransport() {
	}
	public NettyClientTransport(ServiceDiscovery serviceDiscovery,ServiceRegister serviceRegistry){
		this.serviceDiscovery =  serviceDiscovery;
		this.ServiceRegister = serviceRegistry;
	}
	/**
	 * 启动客户端
	 */
	public void startClient(final RpcRequest rpcRequest){
		NioEventLoopGroup group = new NioEventLoopGroup();
		final NettyClientTransport transport = this;
		try {
			Bootstrap bs = new Bootstrap();
			bs.group(group)
			  .channel(NioSocketChannel.class)
			  .option(ChannelOption.TCP_NODELAY, true)
			  .handler(new ChannelInitializer<NioSocketChannel>() {
				  @Override
				protected void initChannel(NioSocketChannel ch) throws Exception {
			          		ch.pipeline().addLast(new ChannelMessageHandler(transport,rpcRequest,getSerializeMode()));		
				}
			});
			
			String[] serviceUrl = fetchServiceUrl().split(":");
			ChannelFuture future = bs.connect(serviceUrl[0], Integer.parseInt(serviceUrl[1])).sync();
			future.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			group.shutdownGracefully();
		}
		
	}
	
	
	/**
	 * 获取一个服务的提供者地址
	 * @return
	 */
	private String fetchServiceUrl(){
		List<String> serviceUrlList = serviceDiscovery.discoverServiceUrl(ServiceRegister);
		if(CollectionUtils.isEmpty(serviceUrlList)){
			throw new NotFoundServiceException("调用失败,没有找到服务提供者,请检查服务是否启动!");
		}
		
		//默认采用随机服务加载策略
		if(serviceLoadStrategy == null){
			serviceLoadStrategy = new RandomLoadStrategy();
		}
		return serviceLoadStrategy.fetchServiceUrl(serviceUrlList);
	}
	public ServiceLoadStrategy getServiceLoadStrategy() {
		return serviceLoadStrategy;
	}
	public void setServiceLoadStrategy(ServiceLoadStrategy serviceLoadStrategy) {
		this.serviceLoadStrategy = serviceLoadStrategy;
	}
	public String getSerializeMode() {
		return serializeMode;
	}
	public void setSerializeMode(String serializeMode) {
		this.serializeMode = serializeMode;
	}
	public RpcResponse getRpcResponse() {
		return rpcResponse;
	}
	public void setRpcResponse(RpcResponse rpcResponse) {
		this.rpcResponse = rpcResponse;
	}
	
}
