/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.ch;

import java.io.IOException;
import java.net.ProtocolFamily;
import java.nio.channels.DatagramChannel;
import java.nio.channels.Pipe;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.AbstractSelector;
import java.nio.channels.spi.SelectorProvider;
import sun.nio.ch.DatagramChannelImpl;
import sun.nio.ch.PipeImpl;
import sun.nio.ch.ServerSocketChannelImpl;
import sun.nio.ch.SocketChannelImpl;

public abstract class SelectorProviderImpl
extends SelectorProvider {
    @Override
    public DatagramChannel openDatagramChannel() throws IOException {
        return new DatagramChannelImpl(this);
    }

    @Override
    public DatagramChannel openDatagramChannel(ProtocolFamily protocolFamily) throws IOException {
        return new DatagramChannelImpl((SelectorProvider)this, protocolFamily);
    }

    @Override
    public Pipe openPipe() throws IOException {
        return new PipeImpl(this);
    }

    @Override
    public abstract AbstractSelector openSelector() throws IOException;

    @Override
    public ServerSocketChannel openServerSocketChannel() throws IOException {
        return new ServerSocketChannelImpl(this);
    }

    @Override
    public SocketChannel openSocketChannel() throws IOException {
        return new SocketChannelImpl(this);
    }
}

