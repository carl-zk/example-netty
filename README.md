# <<Netty 权威指南>>

## 理解 ChannelHandler In/Out 顺序
参考 ChannelPipeline 的注释

futher more: 

ctx.write() 是从当前位置往前找
ctx.channel().write() 是从 tail 往前找
