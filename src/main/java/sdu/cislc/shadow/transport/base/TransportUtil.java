package sdu.cislc.shadow.transport.base;

import sdu.cislc.shadow.transport.mqtt.MQTTMessageHandler;
import sdu.cislc.shadow.transport.mqtt.MQTTShadowSocket;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @Author: bin
 * @Date: 2020/5/20 11:45
 * @Description:
 * 可以将 TransportUtil 作为 MessageHandler 的一个单例，可扩展性会高些，
 * TransportUtil的属性移到 MessageHandler 中。
 */
public class TransportUtil {

//
//    private static ShadowSocket tempSocket;  // 当前设备的身份标识

    public static ShadowSocket getTempSocket() {
        return ShadowSocket.getTempSocket();
    }

    private static Encryption encryption; // 加密方法

    public static Encryption getEncryption() {
        if (encryption == null)
            encryption = new DefaultEncoder();
        return encryption;
    }
    private static AbstractMessageHandler transport; // 通信方式

    public static AbstractMessageHandler getMessageHandler() {
        return transport;
    }

    /**
     * 设置通讯方式，并取得该通讯方式需要读取的其他配置。
     * @param transport 通讯方式 mqtt / 包名+类名
     * @return 需要从 配置文件 / 影子 中读取的其他信息
     */
    public static List<String> setTransport(String transport) {
        TransportUtil.transport = getMessageHandler(transport);
        return TransportUtil.transport.getOtherPropNames();
    }
    public static void setTransportProps(List<String> props) {
        transport.setOtherProps(props);
    }
    /**
     * 设置加密方式，并读取该加密方式需要读取的其他配置
     * @param encryption 加密方式 none / 包名+类名
     * @return 需要从 配置文件 / 影子 中读取的其他信息  比如mqtt的服务器信息
     */
    public static List<String> setEncryption(String encryption) {
        TransportUtil.encryption = getEncryption(encryption);
        return Encryption.getOtherPropNames();
    }

    // 比如mqtt的服务器信息，与上个方法对应
    public static void setEncryptionProps(List<String> props) {
        encryption.setOtherProps(props);
    }

    // 设置收到消息的回调函数
    public static CallBack callBack;
    public static void setCallBack(CallBack callBack) {
        TransportUtil.callBack = callBack;
    }


    public static List<String> setShadowSocket(String shadowSocket) {

        ShadowSocket.tempSocket = getShadowSocket(shadowSocket);
        return ShadowSocket.getOtherPropNames();
    }
    // 设置当前设备的 ShadowSocket
    public static void setShadowSocketProps(String id, List<String> others) {
        ShadowSocket.tempSocket.id = id;
        ShadowSocket.tempSocket.setOtherProp(others);

    }

    // 设置 通信的工作模式 server /（client + server ShadowSocket）
    public static void setWorkMode(WorkMode mode) {
        transport.workMode = mode;
        if (mode == WorkMode.SERVER) {
            ShadowSocket.serverSocket = ShadowSocket.tempSocket;
        } else {
            throw new TransportException("当前方法仅支持设置 server 工作模式");
        }
    }
    //, String... serverSocket
    public static void setWorkMode(String id, List<String> others) {
        transport.workMode = WorkMode.CLIENT;
        try {
            ShadowSocket.serverSocket = (ShadowSocket)getInstance(ShadowSocket.tempSocket);
            ShadowSocket.serverSocket.id = id;
            ShadowSocket.serverSocket.setOtherProp(others);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            throw new TransportException("Client工作模式设置失败 NoSuchMethodException");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new TransportException("Client工作模式设置失败 IllegalAccessException");
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            throw new TransportException("Client工作模式设置失败 InvocationTargetException");
        } catch (InstantiationException e) {
            e.printStackTrace();
            throw new TransportException("Client工作模式设置失败 InstantiationException");
        }
    }


    /**
     * 根据现存实例，使用无参构造方法得到一个新实例
     * @param object
     * @return
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InstantiationException
     */
    private static Object getInstance(Object object) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        return object.getClass().getConstructor().newInstance();
    }




    public static ShadowSocket getServerSocket() {
        return ShadowSocket.getServerSocket();
    }






    private static Encryption getEncryption(String encrypt) {
        System.out.println("加密方式：" + encrypt);
        switch (encrypt.toUpperCase()) {
            case "NONE":
                // 不使用加密
                return new DefaultEncoder();
            default:
                // 使用用户自定义的加密方式
                try {
                    Class clazz = Class.forName(encrypt);
                    Encryption e = (Encryption)clazz.newInstance();
                    return e;
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                throw new TransportException("未找到类：" + encrypt);
        }
    }

    private static AbstractMessageHandler getMessageHandler(String type) {
        switch (type.toUpperCase()) {
            case "MQTT":
                // 使用MQTT通信
                return new MQTTMessageHandler();
            default:
                try {
                    return (AbstractMessageHandler)(Class.forName(type).getConstructor().newInstance());
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                throw new TransportException("未找到类：" + type);

        }
    }


    private static ShadowSocket getShadowSocket(String type) {
        switch (type.toUpperCase()) {
            case "DEFAULT":
                // 使用默认Socket
                return new DefaultSocket();
            case "MQTT":
                return new MQTTShadowSocket();
            default:
                try {
                    return (ShadowSocket)(Class.forName(type).getConstructor().newInstance());
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                throw new TransportException("未找到类：" + type);

        }
    }
}