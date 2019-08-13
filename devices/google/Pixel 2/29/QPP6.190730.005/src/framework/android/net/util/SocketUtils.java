/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.system.ErrnoException
 *  android.system.NetlinkSocketAddress
 *  android.system.Os
 *  android.system.OsConstants
 *  android.system.PacketSocketAddress
 *  libcore.io.IoBridge
 */
package android.net.util;

import android.annotation.SystemApi;
import android.net.NetworkUtils;
import android.system.ErrnoException;
import android.system.NetlinkSocketAddress;
import android.system.Os;
import android.system.OsConstants;
import android.system.PacketSocketAddress;
import java.io.FileDescriptor;
import java.io.IOException;
import java.net.SocketAddress;
import libcore.io.IoBridge;

@SystemApi
public final class SocketUtils {
    private SocketUtils() {
    }

    public static void bindSocketToInterface(FileDescriptor fileDescriptor, String string2) throws ErrnoException {
        Os.setsockoptIfreq((FileDescriptor)fileDescriptor, (int)OsConstants.SOL_SOCKET, (int)OsConstants.SO_BINDTODEVICE, (String)string2);
        NetworkUtils.protectFromVpn(fileDescriptor);
    }

    public static void closeSocket(FileDescriptor fileDescriptor) throws IOException {
        IoBridge.closeAndSignalBlockedThreads((FileDescriptor)fileDescriptor);
    }

    public static SocketAddress makeNetlinkSocketAddress(int n, int n2) {
        return new NetlinkSocketAddress(n, n2);
    }

    public static SocketAddress makePacketSocketAddress(int n, int n2) {
        return new PacketSocketAddress((short)n, n2);
    }

    public static SocketAddress makePacketSocketAddress(int n, byte[] arrby) {
        return new PacketSocketAddress(n, arrby);
    }
}

