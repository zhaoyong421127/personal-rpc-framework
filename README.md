# personal-rpc-framework
个人RPC框架是和spring框架集成，配置简单易懂，采用zookeeper作为服务的注册和发现中心。&lt;br/&gt;
框架中还有许多待完善的地方，比如协议的扩展和注册中心多元化支持等。
&lt;br/&gt;
使用方式：&lt;br/&gt;
1.服务提供方配置：&lt;br/&gt;
&lt;?xml version="1.0" encoding="UTF-8"?&gt;  &lt;br/&gt;
&lt;beans xmlns="http://www.springframework.org/schema/beans" &lt;br/&gt;
xmlns:context="http://www.springframework.org/schema/context" &lt;br/&gt;
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" &lt;br/&gt;
  xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd &lt;br/&gt;
  http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd"&gt;&lt;br/&gt;

 &lt;context:property-placeholder location="classpath:/config.properties"/&gt;&lt;br/&gt;
 &lt;bean class="com.zy.rpc.bootstrap.NettyServerBootstrap"&gt;&lt;br/&gt;
     &lt;constructor-arg index="0" value="${registry.address}"&gt;&lt;/constructor-arg&gt;&lt;br/&gt;
     &lt;property name="packages" value="com.rpc.service"&gt;&lt;br/&gt;
     &lt;/property&gt;&lt;br/&gt;
     &lt;property name="port" value="${service.port}"&gt;&lt;/property&gt;&lt;br/&gt;
 &lt;/bean&gt; &lt;br/&gt;
&lt;/beans&gt;&lt;br/&gt;
&lt;br/&gt;
2.服务消费方配置：&lt;br/&gt;
&lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;br/&gt;
&lt;beans xmlns="http://www.springframework.org/schema/beans"&lt;br/&gt;
xmlns:context="http://www.springframework.org/schema/context"&lt;br/&gt;
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"&lt;br/&gt;
  xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd &lt;br/&gt;
  http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd"&gt;  &lt;br/&gt;

&lt;context:property-placeholder location="classpath:/config.properties"/&gt;&lt;br/&gt;
 &lt;bean id="proxyFactory" class="com.zy.rpc.proxy.JdkDynamicProxyFactory"&gt;&lt;br/&gt;
     &lt;constructor-arg index="0" value="${registry.address}"&gt;&lt;/constructor-arg&gt;&lt;br/&gt;
     &lt;property name="targetClass"&gt;&lt;br/&gt;
        &lt;array&gt;&lt;br/&gt;
           &lt;value&gt;com.rpc.service.IUserService&lt;/value&gt;&lt;br/&gt;
        &lt;/array&gt;&lt;br/&gt;
     &lt;/property&gt;&lt;br/&gt;
 &lt;/bean&gt; &lt;br/&gt;
&lt;/beans&gt;&lt;br/&gt;

3.所有注册中心的和服务端口配置信息在config.properties文件中&lt;br/&gt;
registry.address=127.0.0.1:2181  &lt;br/&gt;
service.port=21880


