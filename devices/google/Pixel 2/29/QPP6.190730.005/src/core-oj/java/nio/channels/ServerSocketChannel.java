/*
 * Decompiled with CFR 0.145.
 */
package java.nio.channels;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketAddress;
import java.net.SocketOption;
import java.nio.channels.NetworkChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.AbstractSelectableChannel;
import java.nio.channels.spi.SelectorProvider;

public abstract class ServerSocketChannel
extends AbstractSelectableChannel
implements NetworkChannel {
    protected ServerSocketChannel(SelectorProvider selectorProvider) {
        super(selectorProvider);
    }

    public static ServerSocketChannel open() throws IOException {
        return SelectorProvider.provider().openServerSocketChannel();
    }

    public abstract SocketChannel accept() throws IOException;

    @Override
    public final ServerSocketChannel bind(SocketAddress socketAddress) throws IOException {
        return this.bind(socketAddress, 0);
    }

    public abstract ServerSocketChannel bind(SocketAddress var1, int var2) throws IOException;

    @Override
    public abstract SocketAddress getLocalAddress() throws IOException;

    @Override
    public abstract <T> ServerSocketChannel setOption(SocketOption<T> var1, T var2) throws IOException;

    public abstract ServerSocket socket();

    @Override
    public final int validOps() {
        return 16;
    }
}

