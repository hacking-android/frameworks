/*
 * Decompiled with CFR 0.145.
 */
package sun.net;

import java.io.FileDescriptor;
import java.io.IOException;
import java.net.InetAddress;

public final class NetHooks {
    public static void beforeTcpBind(FileDescriptor fileDescriptor, InetAddress inetAddress, int n) throws IOException {
    }

    public static void beforeTcpConnect(FileDescriptor fileDescriptor, InetAddress inetAddress, int n) throws IOException {
    }
}

