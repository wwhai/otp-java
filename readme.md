# ErlangJavaInterface
## 1.背景
近期在用JInterface实现一些功能，发现爱立信提供的Java库已经很老了，最后一个版本是2004年写的，至今已经无人维护。
有很多蹩脚的语法和写法，现在看来非常奇怪。据说是开源的但是我找不到源码地址，有知道的朋友可以联系一下我。于是我用了一个
极端的办法：反编译代码，修改部分问题。好在源码数量不是很多，如果熟悉Erlang和Otp的一些基础概念，还是能看得懂的。我改的东西不多，
主要还是一些模棱两可的命名，还有写了一些Demo给大家学习。
> 注意：本仓库非官方支持，可能存在版权问题，清谨慎使用，建议用于学习用途。
## 2. 基础教程
JInterface的主要功能就是用Java来模拟ErlangNode节点，实现一个“假装节点”，从而和真正的Erlang节点或者集群通信。所以这个库适用于想
用Java来扩展Erlang的项目，比如用Java实现一些Erlang不支持的驱动，或者是库。当前我主要用在用Java来连接Mongodb4.0以上的版本。
### 3. Demo
Java代码
```java
public class Main {
    public static void main(String[] args) throws IOException {

        ErlangOtp erlangOtp = new ErlangOtp();
        // connect
        erlangOtp.connect("java@127.0.0.1", "node1@127.0.0.1", "test", new OtpConnectListener() {
            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }

            @Override
            public void onConnect() {
                System.out.println("Connect success");
            }

            @Override
            public void onData(OtpErlangObject message) {
                System.out.println("Message received:" + message.toString());
            }
        });
        // send to erlang node
        erlangOtp.send("pname",new OtpErlangAtom("hello"));
    }
}

```

Erlang端，首先在shell执行：
```erlang
erl -name "node1@127.0.0.1" -setcookie test
```

然后发送消息：
```shell script
Eshell V10.7.2.1  (abort with ^G)
(node1@127.0.0.1)1> Java = 'java@127.0.0.1'.
'java@127.0.0.1'
(node1@127.0.0.1)2> {Java , Java} ! {self()}.
{<0.87.0>}
```

Java端输出：
```shell script
Message received:{#Pid<node1@127.0.0.1.87.0>}
```
### 4. 社区
- QQ群：475512169
