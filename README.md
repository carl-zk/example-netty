# <<Netty 权威指南>>

## 理解 ChannelHandler In/Out 顺序
参考 ChannelPipeline 的注释

futher more: 

ctx.write() 是从当前位置往前找
ctx.channel().write() 是从 tail 往前找

## protobuf
`protoc.exe --java_out=. ./SubscribeReq.proto ./SubscribeResp.proto`

## chapter10
1. 去[jibx官网](https://jibx.sourceforge.io/fromcode/bindgen-example1.html)下载[jibx-1.4.2](https://sourceforge.net/projects/jibx/files/)
2. 解压到D:\local\jibx
3. 编译pojo, bindGen

```sh
cd example-netty
mvnc
./bindGen.sh
```

4. 得到 binding.xml 后需要执行绑定 
java -jar /d/local/jibx/lib/jibx-bind.jar binding.xml
或者用maven plugin
5. 用jibx修改过的class来操作。

pojo与xml互相转换，emm，这个只有银行还在用吧。。。

机智如我，将pojo独立在另一个项目netty-pojo中，完美。

## Reference
[彻底搞懂Reactor模型和Proactor模型](https://cloud.tencent.com/developer/article/1488120) 比较完整，感觉最靠谱

[彻底搞懂epoll高效运行的原理 全菜工程师小辉](https://mp.weixin.qq.com/s?__biz=MzUyNzgyNzAwNg==&mid=2247483925&idx=1&sn=1ac3e863594745c7466b0e88a688b203&scene=21#wechat_redirect) 值得再度一遍

[Reactor 网络模型](https://juejin.cn/post/7092436770519777311)

[netty为何移除aio](http://www.52im.net/thread-4283-1-1.html)

[Netty中的Reactor和Proactor模型](https://juejin.cn/post/6896359324445376525)

[Netty 零拷贝](https://colobu.com/2014/11/13/java-aio-introduction/) 抽时间学习一下