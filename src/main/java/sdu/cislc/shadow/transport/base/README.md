分为服务端与客户端两种情况
服务端配置文件中配置为server，含有 影子功能。
客户端配置文件中配置为client，并有配置服务端的shadowSocket
理想情况：服务端配置有根据id或其他内容请求shadowsocket的接口，供client使用。（如果没有，client之间无法通信）

配置内容


```
shadow:
  communicate:
    device: server / client
    type: mqtt / sdu.bin.comm.rabbitmq.MQTTMessageSender
    encrypt: none / sdu.bin.comm.base.DefaultEncoder / 
    aes:
        key: adjflasjdlfs
    rsa:
        publicKey: D:\home\test\public.txt
        privateKey: D:\home\test\private.txt
    shadowSocket: default / sdu.bin.comm.base.*** 
  rabbitmq:
    host: 192.168.1.8
    port: 5672
    username: root
    password: root
    
```