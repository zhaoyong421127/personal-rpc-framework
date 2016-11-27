package com.zy.rpc;

import com.zy.rpc.bootstrap.NettyServerBootstrap;



public class App {

	public static void main(String[] args) {
		NettyServerBootstrap server = new NettyServerBootstrap("127.0.0.1:2181");
		server.setPort(8888);
		server.setPackages(new String[]{"com.zy"});
		server.startServer();
	}
}
