# 《基于16进制的WebAPI框架XXL-HEX》
## 一、简介

#### 1.1 概述
XXL-HEX 是一个简单易用的WebAPI框架, 拥有 "数据加密、面向对象、跨语言" 的特点。现已开放源代码，开箱即用。

#### 1.2 特性
- 1、数据加密: 通讯数据以16进制数据的形式存在, 数据天然加密; 同时, 底层为API接口预留了API校验接口, 可方便的扩展数据加密逻辑, 进一步校验数据安全性;
- 1、面向对象: 一个API接口对应 "一个Handler" 和 "Requset对象/Response对象"; Web API开发不再需要繁琐的获取和校验HttpRequest中的参数, 采用面向对象的思维去开发 Web API接口, 提高开发效率;
- 2、跨语言: 一个API接口, 开发一次, 支持任何语言调用(系统开放底层通信协议, 任何语言可灵活定制自己语言的Client端实现), 无论Client端是Android、IOS、C#开发的U3D游戏等等;

#### 1.3 背景

**数据加密**

常规API接口服务数据以明文格式存在, 数据易暴露业务信息, 可能会带来意料之外的风险。

XXL-HEX 的API接口通讯数据以HEX的格式存在, 天然加密, 安全性相对较高。

即使数据加密算法被破解, 

**面向对象**

当我们为APP(安卓、IOS等)开发API接口时, 我们可能采用类似 RESTFUL 等方案, 但是此时API接口请求参数和响应数据比较零散, 需要针对多个参数进行繁琐的参数获取赋值等操作, 维护和使用比较繁琐。

因此, 我们考虑上述Web API接口是否可以换一种面向对象的开发方式。在新系统中, 开发每一个API接口需要定义一个Handler类, 同时绑定Request对象和Response对象, 系统底层会自动把请求对象赋值给Request对象, 我们只需要调用Request对象中属性值即可。同样的, 我们只需要把响应数据赋值给Response对象即可; 


#### 1.4 下载
源码地址 (将会在两个git仓库同步发布最新代码)
- [github地址](https://github.com/xuxueli/xxl-hex)
- [git.oschina地址](https://git.oschina.net/xuxueli0323/xxl-hex)

博客地址
- [cnblogs地址](http://www.cnblogs.com/xuxueli/p/5003305.html)

技术交流群(仅作技术交流)：367260654    [![image](http://pub.idqqimg.com/wpa/images/group.png)](http://shang.qq.com/wpa/qunwpa?idkey=4686e3fe01118445c75673a66b4cc6b2c7ce0641528205b6f403c179062b0a52 )

#### 1.5 环境
- Maven3+
- Jdk1.7+
- Tomcat7+

## 二、总体设计

#### 2.1 架构图

#### 2.2 "消息结构" 设计

## 三、快速入门

#### 3.1 接入XXL-HEX的 "WebAPI" 项目配置


## 四、历史版本
#### 4.1 版本1.0.0新特性

#### 规划中
数据完整性校验, 校验失败, 异常抛出, 异常处理
文档补全

## 五、其他

#### 5.1 报告问题
项目托管在Github上，如有问题可在 [ISSUES](https://github.com/xuxueli/xxl-hex/issues) 上提问，也可以加入技术交流群(仅作技术交流)：367260654

#### 5.2 接入登记
更多接入公司，欢迎在github [登记](https://github.com/xuxueli/xxl-hex/issues/1 )