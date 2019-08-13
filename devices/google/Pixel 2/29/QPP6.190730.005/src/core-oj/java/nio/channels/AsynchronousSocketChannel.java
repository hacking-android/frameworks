/*
 * Decompiled with CFR 0.145.
 */
package java.nio.channels;

import java.io.IOException;
import java.net.SocketAddress;
import java.net.SocketOption;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousByteChannel;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.CompletionHandler;
import java.nio.channels.NetworkChannel;
import java.nio.channels.spi.AsynchronousChannelProvider;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public abstract class AsynchronousSocketChannel
implements AsynchronousByteChannel,
NetworkChannel {
    private final AsynchronousChannelProvider provider;

    protected AsynchronousSocketChannel(AsynchronousChannelProvider asynchronousChannelProvider) {
        this.provider = asynchronousChannelProvider;
    }

    public static AsynchronousSocketChannel open() throws IOException {
        return AsynchronousSocketChannel.open(null);
    }

    public static AsynchronousSocketChannel open(AsynchronousChannelGroup asynchronousChannelGroup) throws IOException {
        AsynchronousChannelProvider asynchronousChannelProvider = asynchronousChannelGroup == null ? AsynchronousChannelProvider.provider() : asynchronousChannelGroup.provider();
        return asynchronousChannelProvider.openAsynchronousSocketChannel(asynchronousChannelGroup);
    }

    @Override
    public abstract AsynchronousSocketChannel bind(SocketAddress var1) throws IOException;

    public abstract Future<Void> connect(SocketAddress var1);

    public abstract <A> void connect(SocketAddress var1, A var2, CompletionHandler<Void, ? super A> var3);

    @Override
    public abstract SocketAddress getLocalAddress() throws IOException;

    public abstract SocketAddress getRemoteAddress() throws IOException;

    public final AsynchronousChannelProvider provider() {
        return this.provider;
    }

    @Override
    public abstract Future<Integer> read(ByteBuffer var1);

    public abstract <A> void read(ByteBuffer var1, long var2, TimeUnit var4, A var5, CompletionHandler<Integer, ? super A> var6);

    @Override
    public final <A> void read(ByteBuffer byteBuffer, A a, CompletionHandler<Integer, ? super A> completionHandler) {
        this.read(byteBuffer, 0L, TimeUnit.MILLISECONDS, a, completionHandler);
    }

    public abstract <A> void read(ByteBuffer[] var1, int var2, int var3, long var4, TimeUnit var6, A var7, CompletionHandler<Long, ? super A> var8);

    @Override
    public abstract <T> AsynchronousSocketChannel setOption(SocketOption<T> var1, T var2) throws IOException;

    public abstract AsynchronousSocketChannel shutdownInput() throws IOException;

    public abstract AsynchronousSocketChannel shutdownOutput() throws IOException;

    @Override
    public abstract Future<Integer> write(ByteBuffer var1);

    public abstract <A> void write(ByteBuffer var1, long var2, TimeUnit var4, A var5, CompletionHandler<Integer, ? super A> var6);

    @Override
    public final <A> void write(ByteBuffer byteBuffer, A a, CompletionHandler<Integer, ? super A> completionHandler) {
        this.write(byteBuffer, 0L, TimeUnit.MILLISECONDS, a, completionHandler);
    }

    public abstract <A> void write(ByteBuffer[] var1, int var2, int var3, long var4, TimeUnit var6, A var7, CompletionHandler<Long, ? super A> var8);
}

