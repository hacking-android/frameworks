/*
 * Decompiled with CFR 0.145.
 */
package sun.net;

import java.io.FileDescriptor;
import java.net.SocketOption;
import java.security.Permission;
import jdk.net.NetworkPermission;
import jdk.net.SocketFlow;

public class ExtendedOptionsImpl {
    private ExtendedOptionsImpl() {
    }

    public static void checkGetOptionPermission(SocketOption<?> socketOption) {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager == null) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("getOption.");
        stringBuilder.append(socketOption.name());
        securityManager.checkPermission(new NetworkPermission(stringBuilder.toString()));
    }

    public static void checkSetOptionPermission(SocketOption<?> socketOption) {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager == null) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("setOption.");
        stringBuilder.append(socketOption.name());
        securityManager.checkPermission(new NetworkPermission(stringBuilder.toString()));
    }

    public static void checkValueType(Object object, Class<?> class_) {
        if (class_.isAssignableFrom(object.getClass())) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Found: ");
        stringBuilder.append(object.getClass().toString());
        stringBuilder.append(" Expected: ");
        stringBuilder.append(class_.toString());
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public static boolean flowSupported() {
        return false;
    }

    public static void getFlowOption(FileDescriptor fileDescriptor, SocketFlow socketFlow) {
        throw new UnsupportedOperationException("unsupported socket option");
    }

    public static void setFlowOption(FileDescriptor fileDescriptor, SocketFlow socketFlow) {
        throw new UnsupportedOperationException("unsupported socket option");
    }
}

