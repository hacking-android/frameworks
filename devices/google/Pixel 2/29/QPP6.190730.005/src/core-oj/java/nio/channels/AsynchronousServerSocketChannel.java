/*
 * Decompiled with CFR 0.145.
 */
package java.nio.channels;

import java.io.IOException;
import java.net.SocketAddress;
import java.net.SocketOption;
import java.nio.channels.AsynchronousChannel;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.channels.NetworkChannel;
import java.nio.channels.spi.AsynchronousChannelProvider;
import java.util.concurrent.Future;

public abstract class AsynchronousServerSocketChannel
implements AsynchronousChannel,
NetworkChannel {
    private final AsynchronousChannelProvider provider;

    protected AsynchronousServerSocketChannel(AsynchronousChannelProvider asynchronousChannelProvider) {
        this.provider = asynchronousChannelProvider;
    }

    public static AsynchronousServerSocketChannel open() throws IOException {
        return AsynchronousServerSocketChannel.open(null);
    }

    public static AsynchronousServerSocketChannel open(AsynchronousChannelGroup asynchronousChannelGroup) throws IOException {
        AsynchronousChannelProvider asynchronousChannelProvider = asynchronousChannelGroup == null ? AsynchronousChannelProvider.provider() : asynchronousChannelGroup.provider();
        return asynchronousChannelProvider.openAsynchronousServerSocketChannel(asynchronousChannelGroup);
    }

    public abstract Future<AsynchronousSocketChannel> accept();

    public abstract <A> void accept(A var1, CompletionHandler<AsynchronousSocketChannel, ? super A> var2);

    @Override
    public final AsynchronousServerSocketChannel bind(SocketAddress socketAddress) throws IOException {
        return this.bind(socketAddress, 0);
    }

    public abstract AsynchronousServerSocketChannel bind(SocketAddress var1, int var2) throws IOException;

    @Override
    public abstract SocketAddress getLocalAddress() throws IOException;

    public final AsynchronousChannelProvider provider() {
        return this.provider;
    }

    @Override
    public abstract <T> AsynchronousServerSocketChannel setOption(SocketOption<T> var1, T var2) throws IOException;
}

