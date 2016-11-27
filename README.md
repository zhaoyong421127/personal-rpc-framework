# personal-rpc-framework
个人RPC框架是和spring框架集成，配置简单易懂，采用zookeeper作为服务的注册和发现中心。<br/>
框架中还有许多待完善的地方，比如协议的扩展和注册中心多元化支持等。
<br/>
使用方式：<br/>
1.服务提供方配置：<br/>
<?xml version="1.0" encoding="UTF-8"?>  <br/>
<beans xmlns="http://www.springframework.org/schema/beans" <br/>
xmlns:context="http://www.springframework.org/schema/context" <br/>
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" <br/>
  xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd <br/>
  http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd"><br/>
<context:property-placeholder location="classpath:/config.properties"/><br/>
 <bean class="com.zy.rpc.bootstrap.NettyServerBootstrap"><br/>
     <constructor-arg index="0" value="${registry.address}"></constructor-arg><br/>
     <property name="packages" value="com.rpc.service"><br/>
     </property><br/>
     <property name="port" value="${service.port}"></property><br/>
 </bean> <br/>
</beans><br/>
<br/>
2.服务消费方配置：<br/>
<?xml version="1.0" encoding="UTF-8"?><br/>
<beans xmlns="http://www.springframework.org/schema/beans"<br/>
xmlns:context="http://www.springframework.org/schema/context"<br/>
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"<br/>
  xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd <br/>
  http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd"<br/>
>
<context:property-placeholder location="classpath:/config.properties"/><br/>
 <bean id="proxyFactory" class="com.zy.rpc.proxy.JdkDynamicProxyFactory"><br/>
     <constructor-arg index="0" value="${registry.address}"></constructor-arg><br/>
     <property name="targetClass"><br/>
        <array><br/>
           <value>com.rpc.service.IUserService</value><br/>
        </array><br/>
     </property><br/>
 </bean> <br/>
</beans><br/>

3.所有注册中心的和服务端口配置信息在config.properties文件中<br/>
registry.address=127.0.0.1:2181  <br/>
service.port=21880


