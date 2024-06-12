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