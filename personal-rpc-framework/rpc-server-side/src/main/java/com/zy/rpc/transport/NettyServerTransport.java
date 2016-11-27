package com.zy.rpc.transport;


import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

/**
 * Netty作为服务器底层通信框架
 * @author zy   
 * @date 2016年10月22日 下午9:07:55
 */
public class NettyServerTransport {
  
	/**
	 * 发送响应消息
	 * @param ctx
	 */
	public static void sendResposeMessage(ChannelHandlerContext ctx,ByteBuf respMsg){
		ctx.writeAndFlush(respMsg);
	}
}
