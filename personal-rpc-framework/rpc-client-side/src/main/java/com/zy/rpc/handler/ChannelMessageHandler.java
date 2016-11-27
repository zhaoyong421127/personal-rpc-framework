package com.zy.rpc.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import com.zy.model.RpcRequest;
import com.zy.model.RpcResponse;
import com.zy.rpc.transport.NettyClientTransport;
import com.zy.serializer.ObjectSerializer;
import com.zy.serializer.SerializeFactory;

/**
 * 客户端消息处理器
 * @author zy   
 * @date 2016年10月23日 上午11:44:50
 */
public class ChannelMessageHandler extends ChannelHandlerAdapter {
	
	private RpcRequest rpcRequest;
	
	private String serializeMode;
	
	private NettyClientTransport transport;
	
	private ObjectSerializer serializer;
	public ChannelMessageHandler() {
	}

	public ChannelMessageHandler(NettyClientTransport transport,RpcRequest rpcRequest,String serializeMode) {
		this.rpcRequest = rpcRequest;
		this.serializeMode = serializeMode;
		this.transport = transport;
		this.serializer = SerializeFactory.getSerializeInstance(serializeMode);
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		
		byte[] reqMsg = serializer.serialize(rpcRequest);
		ByteBuf data = Unpooled.wrappedBuffer(reqMsg);
		ctx.writeAndFlush(data);
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		ByteBuf respMsg = (ByteBuf)msg;
		byte[] data = new byte[respMsg.readableBytes()];
		respMsg.readBytes(data);
		RpcResponse rpcResponse = (RpcResponse) serializer.derialize(data);
		transport.setRpcResponse(rpcResponse);
	}
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.channel().close();
	}
}
