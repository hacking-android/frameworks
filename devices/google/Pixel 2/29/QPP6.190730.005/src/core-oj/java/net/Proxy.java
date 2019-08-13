/*
 * Decompiled with CFR 0.145.
 */
package java.net;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

public class Proxy {
    public static final Proxy NO_PROXY = new Proxy();
    private SocketAddress sa;
    private Type type;

    private Proxy() {
        this.type = Type.DIRECT;
        this.sa = null;
    }

    public Proxy(Type type, SocketAddress socketAddress) {
        if (type != Type.DIRECT && socketAddress instanceof InetSocketAddress) {
            this.type = type;
            this.sa = socketAddress;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("type ");
        stringBuilder.append((Object)type);
        stringBuilder.append(" is not compatible with address ");
        stringBuilder.append(socketAddress);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public SocketAddress address() {
        return this.sa;
    }

    public final boolean equals(Object object) {
        boolean bl = false;
        if (object != null && object instanceof Proxy) {
            if (((Proxy)(object = (Proxy)object)).type() == this.type()) {
                if (this.address() == null) {
                    if (((Proxy)object).address() == null) {
                        bl = true;
                    }
                    return bl;
                }
                return this.address().equals(((Proxy)object).address());
            }
            return false;
        }
        return false;
    }

    public final int hashCode() {
        if (this.address() == null) {
            return this.type().hashCode();
        }
        return this.type().hashCode() + this.address().hashCode();
    }

    public String toString() {
        if (this.type() == Type.DIRECT) {
            return "DIRECT";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append((Object)this.type());
        stringBuilder.append(" @ ");
        stringBuilder.append(this.address());
        return stringBuilder.toString();
    }

    public Type type() {
        return this.type;
    }

    public static enum Type {
        DIRECT,
        HTTP,
        SOCKS;
        
    }

}

