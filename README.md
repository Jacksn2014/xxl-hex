# 基于16进制数据传输的服务框架xxl-hex
github：https://github.com/xuxueli/xxl-hex

git.osc：http://git.oschina.net/xuxueli0323/xxl-hex

博客地址（内附使用教程）：http://www.cnblogs.com/xuxueli/p/5003305.html

##简介：
	xxl-hex是一个轻量级的remoting onhttp工具，使用简单的方法提供了rpc的功能。 采用的是16进制编码协议，拥有以下特点：1、数据加密；2、跨语言；
	
## 跨语言，数据传输方案：
	JSON：一种轻量级的数据交换格式，完全独立于语言（Bean对象 》》》 Json字符串）；
	XML：可扩展标记语言，设计宗旨是传输数据（Bean对象 》》》 XML字符串）；
	xxl-hex（本方案）：一种需要预定规则的Hex数据传输方案（Bean对象 》》》 Hex字符串）；

## 通讯方案：HttpClient + Servlet
	出于kiss考虑，使用最方便系统集成的HttpClient+Servlet方式；

## 实现原理，部分逻辑点介绍：
	1、总调用逻辑为：Client端封装IRequest编码为Hex后Post给Server端，Server端解码后匹配Handler执行handle逻辑后分装IResponse响应给Client端，Client接收后，一次请求Finish。
	2、IRequest消息结构：IRequest消息首部存放该消息的ClassName，服务端可以根据ClassName反射实例化IRequest，并且匹配Handler执行handle逻辑；
	3、IRequest匹配Handler：服务端维护一张消息哈希表，根据请求消息的ClassName可以定位到处理的Handler，例如：handlerMap.put("com.xxl.demo.msg.request.DemoRequest", "com.xxl.service.impl.DemoHandler");
	4、Server端注册Handler：业务Handler必须为IHandler的子类并且需要被启动时初始化（可通过声明为Spring的Service搞定），无参构造里调用super.registry(XRequest.class);即可完成与XRequest的关系匹配；
	