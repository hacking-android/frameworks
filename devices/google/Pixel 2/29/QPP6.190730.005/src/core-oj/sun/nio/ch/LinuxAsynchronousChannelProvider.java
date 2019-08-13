/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.ch;

import java.io.IOException;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.IllegalChannelGroupException;
import java.nio.channels.spi.AsynchronousChannelProvider;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import sun.nio.ch.EPollPort;
import sun.nio.ch.Port;
import sun.nio.ch.ThreadPool;
import sun.nio.ch.UnixAsynchronousServerSocketChannelImpl;
import sun.nio.ch.UnixAsynchronousSocketChannelImpl;

public class LinuxAsynchronousChannelProvider
extends AsynchronousChannelProvider {
    private static volatile EPollPort defaultPort;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private EPollPort defaultEventPort() throws IOException {
        if (defaultPort != null) return defaultPort;
        synchronized (LinuxAsynchronousChannelProvider.class) {
            if (defaultPort != null) return defaultPort;
            EPollPort ePollPort = new EPollPort(this, ThreadPool.getDefault());
            defaultPort = ePollPort.start();
            return defaultPort;
        }
    }

    private Port toPort(AsynchronousChannelGroup asynchronousChannelGroup) throws IOException {
        if (asynchronousChannelGroup == null) {
            return this.defaultEventPort();
        }
        if (asynchronousChannelGroup instanceof EPollPort) {
            return (Port)asynchronousChannelGroup;
        }
        throw new IllegalChannelGroupException();
    }

    @Override
    public AsynchronousChannelGroup openAsynchronousChannelGroup(int n, ThreadFactory threadFactory) throws IOException {
        return new EPollPort(this, ThreadPool.create(n, threadFactory)).start();
    }

    @Override
    public AsynchronousChannelGroup openAsynchronousChannelGroup(ExecutorService executorService, int n) throws IOException {
        return new EPollPort(this, ThreadPool.wrap(executorService, n)).start();
    }

    @Override
    public AsynchronousServerSocketChannel openAsynchronousServerSocketChannel(AsynchronousChannelGroup asynchronousChannelGroup) throws IOException {
        return new UnixAsynchronousServerSocketChannelImpl(this.toPort(asynchronousChannelGroup));
    }

    @Override
    public AsynchronousSocketChannel openAsynchronousSocketChannel(AsynchronousChannelGroup asynchronousChannelGroup) throws IOException {
        return new UnixAsynchronousSocketChannelImpl(this.toPort(asynchronousChannelGroup));
    }
}

