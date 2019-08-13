/*
 * Decompiled with CFR 0.145.
 */
package java.net;

public interface SocketOption<T> {
    public String name();

    public Class<T> type();
}

