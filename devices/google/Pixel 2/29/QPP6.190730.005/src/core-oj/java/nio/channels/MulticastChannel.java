/*
 * Decompiled with CFR 0.145.
 */
package java.nio.channels;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.nio.channels.MembershipKey;
import java.nio.channels.NetworkChannel;

public interface MulticastChannel
extends NetworkChannel {
    @Override
    public void close() throws IOException;

    public MembershipKey join(InetAddress var1, NetworkInterface var2) throws IOException;

    public MembershipKey join(InetAddress var1, NetworkInterface var2, InetAddress var3) throws IOException;
}

