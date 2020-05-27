package sdu.cislc.shadow.transport.base;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: bin
 * @Date: 2020/5/20 11:17
 * @Description:
 * 理想情况：此类只有一个id，其他内容有用户继承后定义。
 */
public abstract class ShadowSocket {

//    // 设备/服务器的ip地址，如果没有可以传空 TODO 推荐以otherProp的方式传入
//    InetAddress ipAddr;
    // 设备/服务器的id，必须有
    String id;

    public String getId() {
        return id;
    }

    // 从配置文件中读取/ 从影子中读取 的其他配置
    public static List<String> otherPropNames = new ArrayList<>();

    public static List<String> getOtherPropNames() {

        return otherPropNames;
    }

    public ShadowSocket() {
    }

    public ShadowSocket(String id) {
        this.id = id;
    }
    public ShadowSocket(String id, List<String> otherProp) {
        this.id = id;
        setOtherProp(otherProp);
    }
    public abstract void setOtherProp(List<String> otherProp);
    static ShadowSocket tempSocket;  // 当前设备的身份标识
    public static ShadowSocket getTempSocket() {
        return tempSocket;
    }
//    public abstract static ShadowSocket newShadowSocket(String id, List<String> otherProp);

    static ShadowSocket serverSocket;  // 当前设备的身份标识
    public static ShadowSocket getServerSocket() {
        return serverSocket;
    }

}
