/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.ch;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.nio.channels.MembershipKey;
import java.nio.channels.MulticastChannel;
import java.util.HashSet;
import sun.nio.ch.DatagramChannelImpl;

class MembershipKeyImpl
extends MembershipKey {
    private HashSet<InetAddress> blockedSet;
    private final MulticastChannel ch;
    private final InetAddress group;
    private final NetworkInterface interf;
    private final InetAddress source;
    private Object stateLock = new Object();
    private volatile boolean valid = true;

    private MembershipKeyImpl(MulticastChannel multicastChannel, InetAddress inetAddress, NetworkInterface networkInterface, InetAddress inetAddress2) {
        this.ch = multicastChannel;
        this.group = inetAddress;
        this.interf = networkInterface;
        this.source = inetAddress2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public MembershipKey block(InetAddress inetAddress) throws IOException {
        if (this.source != null) {
            throw new IllegalStateException("key is source-specific");
        }
        Object object = this.stateLock;
        synchronized (object) {
            if (this.blockedSet != null && this.blockedSet.contains(inetAddress)) {
                return this;
            }
            ((DatagramChannelImpl)this.ch).block(this, inetAddress);
            if (this.blockedSet == null) {
                HashSet hashSet = new HashSet();
                this.blockedSet = hashSet;
            }
            this.blockedSet.add(inetAddress);
            return this;
        }
    }

    @Override
    public MulticastChannel channel() {
        return this.ch;
    }

    @Override
    public void drop() {
        ((DatagramChannelImpl)this.ch).drop(this);
    }

    @Override
    public InetAddress group() {
        return this.group;
    }

    void invalidate() {
        this.valid = false;
    }

    @Override
    public boolean isValid() {
        return this.valid;
    }

    @Override
    public NetworkInterface networkInterface() {
        return this.interf;
    }

    @Override
    public InetAddress sourceAddress() {
        return this.source;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(64);
        stringBuilder.append('<');
        stringBuilder.append(this.group.getHostAddress());
        stringBuilder.append(',');
        stringBuilder.append(this.interf.getName());
        if (this.source != null) {
            stringBuilder.append(',');
            stringBuilder.append(this.source.getHostAddress());
        }
        stringBuilder.append('>');
        return stringBuilder.toString();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public MembershipKey unblock(InetAddress serializable) {
        Object object = this.stateLock;
        synchronized (object) {
            if (this.blockedSet != null && this.blockedSet.contains(serializable)) {
                ((DatagramChannelImpl)this.ch).unblock(this, (InetAddress)serializable);
                this.blockedSet.remove(serializable);
                return this;
            }
            serializable = new IllegalStateException("not blocked");
            throw serializable;
        }
    }

    static class Type4
    extends MembershipKeyImpl {
        private final int groupAddress;
        private final int interfAddress;
        private final int sourceAddress;

        Type4(MulticastChannel multicastChannel, InetAddress inetAddress, NetworkInterface networkInterface, InetAddress inetAddress2, int n, int n2, int n3) {
            super(multicastChannel, inetAddress, networkInterface, inetAddress2);
            this.groupAddress = n;
            this.interfAddress = n2;
            this.sourceAddress = n3;
        }

        int groupAddress() {
            return this.groupAddress;
        }

        int interfaceAddress() {
            return this.interfAddress;
        }

        int source() {
            return this.sourceAddress;
        }
    }

    static class Type6
    extends MembershipKeyImpl {
        private final byte[] groupAddress;
        private final int index;
        private final byte[] sourceAddress;

        Type6(MulticastChannel multicastChannel, InetAddress inetAddress, NetworkInterface networkInterface, InetAddress inetAddress2, byte[] arrby, int n, byte[] arrby2) {
            super(multicastChannel, inetAddress, networkInterface, inetAddress2);
            this.groupAddress = arrby;
            this.index = n;
            this.sourceAddress = arrby2;
        }

        byte[] groupAddress() {
            return this.groupAddress;
        }

        int index() {
            return this.index;
        }

        byte[] source() {
            return this.sourceAddress;
        }
    }

}

