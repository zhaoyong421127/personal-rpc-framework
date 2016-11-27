# personal-rpc-framework
个人RPC框架是和spring框架集成，配置简单易懂，采用zookeeper作为服务的注册和发现中心。
框架中还有许多待完善的地方，比如协议的扩展和注册中心多元化支持等。
使用方式：
1.服务提供方配置：
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
xmlns:context="http://www.springframework.org/schema/context"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
  http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd"
>
<context:property-placeholder location="classpath:/config.properties"/>
 <bean class="com.zy.rpc.bootstrap.NettyServerBootstrap">
     <constructor-arg index="0" value="${registry.address}"></constructor-arg>
     <property name="packages" value="com.rpc.service">
     </property>
     <property name="port" value="${service.port}"></property>
 </bean> 
</beans>

2.服务消费方配置：
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
xmlns:context="http://www.springframework.org/schema/context"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
  http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd"
>
<context:property-placeholder location="classpath:/config.properties"/>
 <bean id="proxyFactory" class="com.zy.rpc.proxy.JdkDynamicProxyFactory">
     <constructor-arg index="0" value="${registry.address}"></constructor-arg>
     <property name="targetClass">
        <array>
           <value>com.rpc.service.IUserService</value>
        </array>
     </property>
 </bean> 
</beans>

3.所有注册中心的和服务端口配置信息在config.properties文件中
registry.address=127.0.0.1:2181
service.port=21880


