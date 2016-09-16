# 《面向对象的WebAPI框架XXL-HEX》
## 一、简介

#### 1.1 概述
XXL-HEX 是一个简单易用的WebAPI框架, 拥有 "面向对象、数据加密、跨语言" 的特点。目标是: 提高Web API (如 Android、IOS 等APP接口, 或者 unity3d 等游戏服务端接口) 的开发体验以及开发效率。现已开放源代码，开箱即用。

#### 1.2 特性
- 1、面向对象: 一个API接口对应 "一个Handler" 和 "Requset对象/Response对象"; 针对Web API开发 (如 Android、IOS 等APP接口开发, 或者 unity3d 等游戏接口开发), 采用面向对象的思维去开发 Web API接口。提高API接口的开发效率以及开发体验;
- 2、数据加密: 通讯数据以16进制数据的形式存在, 数据天然加密; 同时, 底层为API接口预留了API校验接口, 可方便的扩展数据加密逻辑, 进一步校验数据安全性;
- 3、跨语言: 一个API接口, 开发一次, 支持任何语言调用(系统开放底层通信协议, 任何语言可灵活定制自己语言的Client端实现), 无论Client端是Android、IOS、C#开发的U3D游戏等等;

#### 1.3 背景

**面向对象**

当我们为APP(安卓、IOS等)开发API接口时, 我们可能采用类似 RESTFUL 等方案, 但是此时API接口请求参数和响应数据比较零散, 需要针对多个参数进行繁琐的参数获取赋值等操作, 维护和使用比较繁琐。

因此, 我们考虑上述Web API接口是否可以换一种面向对象的开发方式。在新系统中, 开发每一个API接口需要定义一个Handler类, 同时绑定Request对象和Response对象, 系统底层会自动把请求对象赋值给Request对象, 我们只需要调用Request对象中属性值即可。同样的, 我们只需要把响应数据赋值给Response对象即可; 自此, API接口的开发效率和开发体验将会大幅度提升; 

**数据加密**

常规API接口服务数据以明文格式存在, 数据易暴露业务信息, 如遭遇恶意爬虫或者DDOS攻击, 轻则加重服务器负担, 服务器处理了外部非法的接口请求; 重则篡改线上业务数据, 造成严重后果。

XXL-HEX 的API接口通讯数据以HEX的格式存在, 天然加密, 安全性相对较高。初次之外, 支持自由扩展API接口的校验逻辑, 进一步校验数据安全性, 提高体统的整体安全性;

**跨语言**

当我们提供API接口, 调用方千差万别, 如下:

- 1、Android (java)
- 2、IOS (object-c)
- 3、J2EE
- 4、PHP Web
- 5、.NET 
- 6、unity3d (C#)
- 7、PC客户端 (C++)

存在如此多的异构系统的情形下, 一种跨语言的 API 通讯方案显得尤为重要。

如果简单实现跨语言则 RESTFUL 等方案可简单实现, 但是如若要兼容上述 "面向对象" 和 "数据加密" 的特点, 同时保证系统简易且稳定则存在一定难度。XXL-HEX得益于其基于HEX的特性以及底层特殊数据结构, 在保证兼容上述特性的技术上, 天然支持跨语言。

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

![输入图片说明](https://static.oschina.net/uploads/img/201609/16200148_LoMR.jpg "在这里输入图片标题")

XXL-HEX作为一个API接口实现方案, 是个典型的CS模型。

Server端只要由以下三个组件组成:

- 1、HexServlet: 服务端API接口的统一入口, 承担API接口请求的路由功能; 
- 2、HexHandlerFactory: 服务端核心组件, 负责API接口请求的 "dispatch", 以及消息的编解码以及序列化; 同时作为Handler仓库, 汇总并维护服务端可用业务Handler;
- 3、HexHandler: 开发人员开发的具体业务Handler, 需要实现 "validate" 和 "handle" 接口, 前者负责业务校验, 后者执行具体的业务逻辑;

Client端主要由两个模块组成:

- 1、serialize/desecrialize: 负责对消息对象序列化以及编解码操作, 生成服务端可识别的hex消息;
- 2、remote: 负责和Server端之间的消息通信, 消息体为hex格式数据;

#### 2.2 "一次API接口请求时序图" 分析

![输入图片说明](https://static.oschina.net/uploads/img/201609/16191517_HpcK.jpg "在这里输入图片标题")

从上图可知, 一次API请求过程所经历的流程为:

- 1、Client端创建Request消息对象;
- 2、Request消息对象, 转换为 "HEX格式Request消息" (Server端只会识别HEX格式消息), POST发送到Server端;
- 3、HexServlet路由组件, 接收API请求, 将请求发送给HexHandlerFactory组件;
- 4、HexHandlerFactory负责解码 "HEX格式Request消息", 并反序列化为Request对象, 然后将API请求dispatch到指定的业务Handler上;
- 5、业务Handler首先会根据Request对象进行validate校验, 校验成功则执行业务handle方法, 执行具体的业务逻辑;
- 6、业务handle执行结束, 封装执行结果为Response对象;
- 7、HexHandlerFactory组件负责将Response对象序列化, 然后编码为 "HEX格式Response消息", 返回给HexServlet路由;
- 8、HexServlet路由组件, 响应 "HEX格式Response消息" 消息给Client端。Finish。

#### 2.3 "消息体结构(HEX数据结构)" 设计

![输入图片说明](https://static.oschina.net/uploads/img/201609/16193859_JIyu.jpg "在这里输入图片标题")

网络传输时, 请求响应数据以HEX(16进制字符串)的格式存在。HEX格式消息的数据来源如下 (以将请求参数转换为HEX为例) :

- 第一步: 封装 "Request对象" (不同开发语言, 对象可能不同, 这一步仅仅针对Java语言, 如其余开发语言实现Client, 可直接跳到 "第二步" ) 
- 第二步: 将 "Request对象" 序列化为 "JSON字符串" ; (JSON属性, 应该可服务端Request对象属性保持一致)
- 第三步: 将 "JSON字符串" 转换为 "特殊格式" 的 字节数据 "byte[]", "byte[]" 的组成:  "JSON字符串"长度(4字节) + "JSON字符串"字节内容;
- 第四步: 将 "byte[]" 转换为 HEX 数据。 Finish。

至此, 已经生成了服务端可以识别的HEX数据。至于服务端返回的HEX格式数据的解析, 安装上述步骤逆序操作即可。

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