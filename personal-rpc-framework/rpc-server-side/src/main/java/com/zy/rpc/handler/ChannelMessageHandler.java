package com.zy.rpc.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import com.zy.model.RpcRequest;
import com.zy.model.RpcResponse;
import com.zy.rpc.transport.NettyServerTransport;
import com.zy.serializer.ObjectSerializer;
import com.zy.serializer.SerializeFactory;

/**
 * 通道消息处理器
 * @author zy   
 * @date 2016年10月22日 下午9:32:39
 */
public class ChannelMessageHandler extends ChannelHandlerAdapter {

	
	/**
	 * 对象序列化
	 */
	private ObjectSerializer serializer;
	public ChannelMessageHandler() {
	}
	
	public ChannelMessageHandler(String serializeMode) {
		this.serializer = SerializeFactory.getSerializeInstance(serializeMode);
	}
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		ByteBuf data = (ByteBuf)msg;
		byte[] reqMessage = new byte[data.readableBytes()];
		data.readBytes(reqMessage);
		RpcRequest rpcRequest = (RpcRequest)serializer.derialize(reqMessage);
		//调用服务
		Object result = ServiceInvoker.invokeService(rpcRequest);
		
		//响应消息
		sendRespMessage(ctx,result,rpcRequest,null);
		
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		System.out.println(cause.getMessage());
		sendRespMessage(ctx,null,null,cause);
	}
	
	
	private void sendRespMessage(ChannelHandlerContext ctx,Object object,RpcRequest rpcRequest,Throwable err){
		RpcResponse resp = new RpcResponse();
		resp.setResponseId(rpcRequest.getRequestId());
		resp.setResult(object);
		resp.setError(err);
		byte[] data = serializer.serialize(resp);
		ByteBuf buffer = Unpooled.wrappedBuffer(data);
		NettyServerTransport.sendResposeMessage(ctx, buffer);
	}
	
}
