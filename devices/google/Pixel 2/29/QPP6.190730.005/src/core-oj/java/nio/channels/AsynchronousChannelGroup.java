/*
 * Decompiled with CFR 0.145.
 */
package java.nio.channels;

import java.io.IOException;
import java.nio.channels.spi.AsynchronousChannelProvider;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public abstract class AsynchronousChannelGroup {
    private final AsynchronousChannelProvider provider;

    protected AsynchronousChannelGroup(AsynchronousChannelProvider asynchronousChannelProvider) {
        this.provider = asynchronousChannelProvider;
    }

    public static AsynchronousChannelGroup withCachedThreadPool(ExecutorService executorService, int n) throws IOException {
        return AsynchronousChannelProvider.provider().openAsynchronousChannelGroup(executorService, n);
    }

    public static AsynchronousChannelGroup withFixedThreadPool(int n, ThreadFactory threadFactory) throws IOException {
        return AsynchronousChannelProvider.provider().openAsynchronousChannelGroup(n, threadFactory);
    }

    public static AsynchronousChannelGroup withThreadPool(ExecutorService executorService) throws IOException {
        return AsynchronousChannelProvider.provider().openAsynchronousChannelGroup(executorService, 0);
    }

    public abstract boolean awaitTermination(long var1, TimeUnit var3) throws InterruptedException;

    public abstract boolean isShutdown();

    public abstract boolean isTerminated();

    public final AsynchronousChannelProvider provider() {
        return this.provider;
    }

    public abstract void shutdown();

    public abstract void shutdownNow() throws IOException;
}

