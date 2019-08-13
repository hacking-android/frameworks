/*
 * Decompiled with CFR 0.145.
 */
package jdk.net;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.DatagramSocket;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketOption;
import java.net.StandardSocketOptions;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import jdk.net.ExtendedSocketOptions;
import jdk.net.SocketFlow;
import sun.net.ExtendedOptionsImpl;

public class Sockets {
    private static Method dsiGetOption;
    private static Method dsiSetOption;
    private static final HashMap<Class<?>, Set<SocketOption<?>>> options;
    private static Method siGetOption;
    private static Method siSetOption;

    static {
        options = new HashMap();
        Sockets.initOptionSets();
        AccessController.doPrivileged(new PrivilegedAction<Void>(){

            @Override
            public Void run() {
                Sockets.initMethods();
                return null;
            }
        });
    }

    private Sockets() {
    }

    public static <T> T getOption(DatagramSocket datagramSocket, SocketOption<T> socketOption) throws IOException {
        if (Sockets.isSupported(datagramSocket.getClass(), socketOption)) {
            return Sockets.invokeGet(dsiGetOption, datagramSocket, socketOption);
        }
        throw new UnsupportedOperationException(socketOption.name());
    }

    public static <T> T getOption(ServerSocket serverSocket, SocketOption<T> socketOption) throws IOException {
        if (Sockets.isSupported(ServerSocket.class, socketOption)) {
            return Sockets.invokeGet(siGetOption, serverSocket, socketOption);
        }
        throw new UnsupportedOperationException(socketOption.name());
    }

    public static <T> T getOption(Socket socket, SocketOption<T> socketOption) throws IOException {
        if (Sockets.isSupported(Socket.class, socketOption)) {
            return Sockets.invokeGet(siGetOption, socket, socketOption);
        }
        throw new UnsupportedOperationException(socketOption.name());
    }

    private static void initMethods() {
        try {
            Class<?> class_ = Class.forName("java.net.SocketSecrets");
            siSetOption = class_.getDeclaredMethod("setOption", Object.class, SocketOption.class, Object.class);
            siSetOption.setAccessible(true);
            siGetOption = class_.getDeclaredMethod("getOption", Object.class, SocketOption.class);
            siGetOption.setAccessible(true);
            dsiSetOption = class_.getDeclaredMethod("setOption", DatagramSocket.class, SocketOption.class, Object.class);
            dsiSetOption.setAccessible(true);
            dsiGetOption = class_.getDeclaredMethod("getOption", DatagramSocket.class, SocketOption.class);
            dsiGetOption.setAccessible(true);
            return;
        }
        catch (ReflectiveOperationException reflectiveOperationException) {
            throw new InternalError(reflectiveOperationException);
        }
    }

    private static void initOptionSets() {
        boolean bl = ExtendedOptionsImpl.flowSupported();
        Set<SocketOption<Boolean>> set = new HashSet<SocketOption<Object>>();
        set.add(StandardSocketOptions.SO_KEEPALIVE);
        set.add(StandardSocketOptions.SO_SNDBUF);
        set.add(StandardSocketOptions.SO_RCVBUF);
        set.add(StandardSocketOptions.SO_REUSEADDR);
        set.add(StandardSocketOptions.SO_LINGER);
        set.add(StandardSocketOptions.IP_TOS);
        set.add(StandardSocketOptions.TCP_NODELAY);
        if (bl) {
            set.add(ExtendedSocketOptions.SO_FLOW_SLA);
        }
        set = Collections.unmodifiableSet(set);
        options.put(Socket.class, set);
        set = new HashSet();
        set.add(StandardSocketOptions.SO_RCVBUF);
        set.add(StandardSocketOptions.SO_REUSEADDR);
        set.add(StandardSocketOptions.IP_TOS);
        set = Collections.unmodifiableSet(set);
        options.put(ServerSocket.class, set);
        set = new HashSet();
        set.add(StandardSocketOptions.SO_SNDBUF);
        set.add(StandardSocketOptions.SO_RCVBUF);
        set.add(StandardSocketOptions.SO_REUSEADDR);
        set.add(StandardSocketOptions.IP_TOS);
        if (bl) {
            set.add(ExtendedSocketOptions.SO_FLOW_SLA);
        }
        set = Collections.unmodifiableSet(set);
        options.put(DatagramSocket.class, set);
        set = new HashSet();
        set.add(StandardSocketOptions.SO_SNDBUF);
        set.add(StandardSocketOptions.SO_RCVBUF);
        set.add(StandardSocketOptions.SO_REUSEADDR);
        set.add(StandardSocketOptions.IP_TOS);
        set.add(StandardSocketOptions.IP_MULTICAST_IF);
        set.add(StandardSocketOptions.IP_MULTICAST_TTL);
        set.add(StandardSocketOptions.IP_MULTICAST_LOOP);
        if (bl) {
            set.add(ExtendedSocketOptions.SO_FLOW_SLA);
        }
        set = Collections.unmodifiableSet(set);
        options.put(MulticastSocket.class, set);
    }

    private static <T> T invokeGet(Method object, Object object2, SocketOption<T> socketOption) throws IOException {
        try {
            object = ((Method)object).invoke(null, object2, socketOption);
        }
        catch (Exception exception) {
            if (exception instanceof InvocationTargetException) {
                object2 = ((InvocationTargetException)exception).getTargetException();
                if (!(object2 instanceof IOException)) {
                    if (object2 instanceof RuntimeException) {
                        throw (RuntimeException)object2;
                    }
                } else {
                    throw (IOException)object2;
                }
            }
            throw new RuntimeException(exception);
        }
        return (T)object;
    }

    private static <T> void invokeSet(Method object, Object object2, SocketOption<T> socketOption, T t) throws IOException {
        try {
            ((Method)object).invoke(null, object2, socketOption, t);
            return;
        }
        catch (Exception exception) {
            if (exception instanceof InvocationTargetException) {
                object = ((InvocationTargetException)exception).getTargetException();
                if (!(object instanceof IOException)) {
                    if (object instanceof RuntimeException) {
                        throw (RuntimeException)object;
                    }
                } else {
                    throw (IOException)object;
                }
            }
            throw new RuntimeException(exception);
        }
    }

    private static boolean isSupported(Class<?> class_, SocketOption<?> socketOption) {
        return Sockets.supportedOptions(class_).contains(socketOption);
    }

    public static <T> void setOption(DatagramSocket datagramSocket, SocketOption<T> socketOption, T t) throws IOException {
        if (Sockets.isSupported(datagramSocket.getClass(), socketOption)) {
            Sockets.invokeSet(dsiSetOption, datagramSocket, socketOption, t);
            return;
        }
        throw new UnsupportedOperationException(socketOption.name());
    }

    public static <T> void setOption(ServerSocket serverSocket, SocketOption<T> socketOption, T t) throws IOException {
        if (Sockets.isSupported(ServerSocket.class, socketOption)) {
            Sockets.invokeSet(siSetOption, serverSocket, socketOption, t);
            return;
        }
        throw new UnsupportedOperationException(socketOption.name());
    }

    public static <T> void setOption(Socket socket, SocketOption<T> socketOption, T t) throws IOException {
        if (Sockets.isSupported(Socket.class, socketOption)) {
            Sockets.invokeSet(siSetOption, socket, socketOption, t);
            return;
        }
        throw new UnsupportedOperationException(socketOption.name());
    }

    public static Set<SocketOption<?>> supportedOptions(Class<?> object) {
        if ((object = options.get(object)) != null) {
            return object;
        }
        throw new IllegalArgumentException("unknown socket type");
    }

}

