/*
 * Decompiled with CFR 0.145.
 */
package java.nio.channels;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.nio.channels.MulticastChannel;

public abstract class MembershipKey {
    protected MembershipKey() {
    }

    public abstract MembershipKey block(InetAddress var1) throws IOException;

    public abstract MulticastChannel channel();

    public abstract void drop();

    public abstract InetAddress group();

    public abstract boolean isValid();

    public abstract NetworkInterface networkInterface();

    public abstract InetAddress sourceAddress();

    public abstract MembershipKey unblock(InetAddress var1);
}

