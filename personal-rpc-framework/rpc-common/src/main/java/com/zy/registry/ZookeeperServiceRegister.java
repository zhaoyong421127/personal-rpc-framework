package com.zy.registry;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.CuratorFrameworkFactory.Builder;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.data.Stat;

import com.zy.constant.Constants;

/**
 * 使用zookeeper注册中心注册服务
 * @author zy   
 * @date 2016年9月11日 下午7:51:57
 */
/**
 * @author zy   
 * @date 2016年10月23日 上午11:25:11
 */
public class ZookeeperServiceRegister implements ServiceRegister {
    /**
     * 服务注册地址
     */
    private String registryAddress;
    
    private Builder builder = CuratorFrameworkFactory.builder();
    /**
     * session超时时间，默认为1分钟
     */
    private Integer sessionTimeout = builder.getSessionTimeoutMs();
    
    /**
     * 客户端连接zookeeper超时时间，默认为15s
     */
    private Integer connectTimeout = builder.getConnectionTimeoutMs();
    
    /**
     * 客户端重试连接服务器的此时，默认3次
     */
    private Integer retryCount = 3;
    
    /**
     * 重试间隔时间，默认是3s
     */
    private static final Integer RETRY_BETWEEN_TIME = 3000;
    
    public ZookeeperServiceRegister() {
	}
    
	
	public ZookeeperServiceRegister(String registryAddress) {
		this.registryAddress = registryAddress;
	}


	public void register(String serverAddress) {
		
		//连接Zookeeper，创建连接对象
		CuratorFramework client = connectServer();
		creatDataNode(client,serverAddress);
	}
	
	public List<String> discoverServiceUrl() {
		//连接Zookeeper，创建连接对象
		CuratorFramework client = connectServer();
		List<String> serviceUrlList = null;
		try {
			serviceUrlList =  client.getZookeeperClient().getZooKeeper().getChildren(Constants.ZOOKEEPER_DATA_PATH, new Watcher(){
				public void process(WatchedEvent event) {
					if(event.getType().equals(Watcher.Event.EventType.NodeChildrenChanged)){
						discoverServiceUrl();
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return serviceUrlList;
	}
	private CuratorFramework connectServer(){
		CuratorFramework client = null;
		try {
		   client = CuratorFrameworkFactory.newClient(registryAddress,sessionTimeout,connectTimeout,new RetryNTimes(retryCount, RETRY_BETWEEN_TIME));
			//启动连接
			client.start();
			client.blockUntilConnected();
		} catch (Exception e) {
			e.printStackTrace();
			if(client != null){
				  client.close();
			}
		}
		return client;
	}
	
	/**
	 * 创建数据节点
	 */
	private void creatDataNode(CuratorFramework client,String serverAddress){
		try {
			checkNodeStatus(client);
			String dataNode = Constants.ZOOKEEPER_DATA_PATH+"/"+serverAddress;
			Stat stat = client.checkExists().forPath(dataNode);
			if(stat!=null){
				client.delete().forPath(dataNode);
			}
			//创建数据节点
			client.create().withMode(CreateMode.EPHEMERAL).forPath(Constants.ZOOKEEPER_DATA_PATH+"/"+serverAddress,serverAddress.getBytes(Constants.DEFAULT_CHARSET));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 检查节点状态
	 * @param client
	 * @throws Exception 
	 */
	private void checkNodeStatus(CuratorFramework client) throws Exception {
		if(client == null){
			throw new IllegalArgumentException("Zookeeper没有连接上，请检查配置参数！");
		}
		Stat registryStat = client.checkExists().forPath(Constants.REGISTRY_DEFAULT_PATH);
		if(registryStat == null){
			client.create().withMode(CreateMode.PERSISTENT).forPath(Constants.REGISTRY_DEFAULT_PATH);
		}
		Stat dataStat = client.checkExists().forPath(Constants.ZOOKEEPER_DATA_PATH);
		if(dataStat == null){
			client.create().withMode(CreateMode.PERSISTENT).forPath(Constants.ZOOKEEPER_DATA_PATH);
		}
	} 
	public void setRegistryAddress(String registryAddress) {
           this.registryAddress = registryAddress;
	}

	public static void main(String[] args) throws Exception {
		ZookeeperServiceRegister registry = new ZookeeperServiceRegister();
		registry.setRegistryAddress("127.0.0.1:2181");
		registry.register("127.0.0.1:8888");
		System.in.read();
	}
}
