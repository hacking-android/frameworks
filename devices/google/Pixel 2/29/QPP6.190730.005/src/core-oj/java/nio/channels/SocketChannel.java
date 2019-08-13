/*
 * Decompiled with CFR 0.145.
 */
package java.nio.channels;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketOption;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.NetworkChannel;
import java.nio.channels.ScatteringByteChannel;
import java.nio.channels.spi.AbstractSelectableChannel;
import java.nio.channels.spi.SelectorProvider;

public abstract class SocketChannel
extends AbstractSelectableChannel
implements ByteChannel,
ScatteringByteChannel,
GatheringByteChannel,
NetworkChannel {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    protected SocketChannel(SelectorProvider selectorProvider) {
        super(selectorProvider);
    }

    public static SocketChannel open() throws IOException {
        return SelectorProvider.provider().openSocketChannel();
    }

    public static SocketChannel open(SocketAddress socketAddress) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        try {
            socketChannel.connect(socketAddress);
            return socketChannel;
        }
        catch (Throwable throwable) {
            try {
                socketChannel.close();
            }
            catch (Throwable throwable2) {
                throwable.addSuppressed(throwable2);
            }
            throw throwable;
        }
    }

    @Override
    public abstract SocketChannel bind(SocketAddress var1) throws IOException;

    public abstract boolean connect(SocketAddress var1) throws IOException;

    public abstract boolean finishConnect() throws IOException;

    @Override
    public abstract SocketAddress getLocalAddress() throws IOException;

    public abstract SocketAddress getRemoteAddress() throws IOException;

    public abstract boolean isConnected();

    public abstract boolean isConnectionPending();

    @Override
    public abstract int read(ByteBuffer var1) throws IOException;

    @Override
    public final long read(ByteBuffer[] arrbyteBuffer) throws IOException {
        return this.read(arrbyteBuffer, 0, arrbyteBuffer.length);
    }

    @Override
    public abstract long read(ByteBuffer[] var1, int var2, int var3) throws IOException;

    @Override
    public abstract <T> SocketChannel setOption(SocketOption<T> var1, T var2) throws IOException;

    public abstract SocketChannel shutdownInput() throws IOException;

    public abstract SocketChannel shutdownOutput() throws IOException;

    public abstract Socket socket();

    @Override
    public final int validOps() {
        return 13;
    }

    @Override
    public abstract int write(ByteBuffer var1) throws IOException;

    @Override
    public final long write(ByteBuffer[] arrbyteBuffer) throws IOException {
        return this.write(arrbyteBuffer, 0, arrbyteBuffer.length);
    }

    @Override
    public abstract long write(ByteBuffer[] var1, int var2, int var3) throws IOException;
}

