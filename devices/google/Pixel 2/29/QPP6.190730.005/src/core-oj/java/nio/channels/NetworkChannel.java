/*
 * Decompiled with CFR 0.145.
 */
package java.nio.channels;

import java.io.IOException;
import java.net.SocketAddress;
import java.net.SocketOption;
import java.nio.channels.Channel;
import java.util.Set;

public interface NetworkChannel
extends Channel {
    public NetworkChannel bind(SocketAddress var1) throws IOException;

    public SocketAddress getLocalAddress() throws IOException;

    public <T> T getOption(SocketOption<T> var1) throws IOException;

    public <T> NetworkChannel setOption(SocketOption<T> var1, T var2) throws IOException;

    public Set<SocketOption<?>> supportedOptions();
}

