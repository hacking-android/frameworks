/*
 * Decompiled with CFR 0.145.
 */
package android.system;

import java.net.InetAddress;
import libcore.util.Objects;

public final class StructGroupReq {
    public final InetAddress gr_group;
    public final int gr_interface;

    public StructGroupReq(int n, InetAddress inetAddress) {
        this.gr_interface = n;
        this.gr_group = inetAddress;
    }

    public String toString() {
        return Objects.toString(this);
    }
}

