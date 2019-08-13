/*
 * Decompiled with CFR 0.145.
 */
package android.system;

import dalvik.annotation.compat.UnsupportedAppUsage;
import java.net.SocketAddress;
import libcore.util.Objects;

public final class NetlinkSocketAddress
extends SocketAddress {
    private final int nlGroupsMask;
    private final int nlPortId;

    public NetlinkSocketAddress() {
        this(0, 0);
    }

    public NetlinkSocketAddress(int n) {
        this(n, 0);
    }

    @UnsupportedAppUsage
    public NetlinkSocketAddress(int n, int n2) {
        this.nlPortId = n;
        this.nlGroupsMask = n2;
    }

    public int getGroupsMask() {
        return this.nlGroupsMask;
    }

    public int getPortId() {
        return this.nlPortId;
    }

    public String toString() {
        return Objects.toString(this);
    }
}

