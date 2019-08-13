/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.ch;

import java.io.Closeable;
import java.io.FileDescriptor;
import java.io.IOException;
import java.nio.channels.Channel;
import java.nio.channels.ShutdownChannelGroupException;
import java.nio.channels.spi.AsynchronousChannelProvider;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import sun.nio.ch.AsynchronousChannelGroupImpl;
import sun.nio.ch.IOUtil;
import sun.nio.ch.ThreadPool;

abstract class Port
extends AsynchronousChannelGroupImpl {
    protected final Map<Integer, PollableChannel> fdToChannel = new HashMap<Integer, PollableChannel>();
    protected final ReadWriteLock fdToChannelLock = new ReentrantReadWriteLock();

    Port(AsynchronousChannelProvider asynchronousChannelProvider, ThreadPool threadPool) {
        super(asynchronousChannelProvider, threadPool);
    }

    @Override
    final Object attachForeignChannel(final Channel channel, FileDescriptor fileDescriptor) {
        int n = IOUtil.fdVal(fileDescriptor);
        this.register(n, new PollableChannel(){

            @Override
            public void close() throws IOException {
                channel.close();
            }

            @Override
            public void onEvent(int n, boolean bl) {
            }
        });
        return n;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    final void closeAllChannels() {
        int n;
        PollableChannel[] arrpollableChannel = new PollableChannel[128];
        do {
            int n2;
            block10 : {
                void var1_4;
                block9 : {
                    Iterator<Integer> iterator;
                    this.fdToChannelLock.writeLock().lock();
                    n2 = 0;
                    try {
                        iterator = this.fdToChannel.keySet().iterator();
                    }
                    catch (Throwable throwable) {
                        // empty catch block
                        break block9;
                    }
                    do {
                        n = n2;
                        if (!iterator.hasNext()) break;
                        Integer n3 = iterator.next();
                        n = n2 + 1;
                        try {
                            arrpollableChannel[n2] = this.fdToChannel.get(n3);
                            if (n >= 128) break;
                            n2 = n;
                        }
                        catch (Throwable throwable) {
                            break block9;
                        }
                    } while (true);
                    this.fdToChannelLock.writeLock().unlock();
                    break block10;
                }
                this.fdToChannelLock.writeLock().unlock();
                throw var1_4;
            }
            for (n2 = 0; n2 < n; ++n2) {
                try {
                    arrpollableChannel[n2].close();
                    continue;
                }
                catch (IOException iOException) {
                    // empty catch block
                }
            }
        } while (n > 0);
    }

    @Override
    final void detachForeignChannel(Object object) {
        this.unregister((Integer)object);
    }

    @Override
    final boolean isEmpty() {
        this.fdToChannelLock.writeLock().lock();
        try {
            boolean bl = this.fdToChannel.isEmpty();
            return bl;
        }
        finally {
            this.fdToChannelLock.writeLock().unlock();
        }
    }

    protected void preUnregister(int n) {
    }

    final void register(int n, PollableChannel object) {
        this.fdToChannelLock.writeLock().lock();
        try {
            if (!this.isShutdown()) {
                this.fdToChannel.put(n, (PollableChannel)object);
                return;
            }
            object = new ShutdownChannelGroupException();
            throw object;
        }
        finally {
            this.fdToChannelLock.writeLock().unlock();
        }
    }

    abstract void startPoll(int var1, int var2);

    final void unregister(int n) {
        int n2 = 0;
        this.preUnregister(n);
        this.fdToChannelLock.writeLock().lock();
        try {
            this.fdToChannel.remove(n);
            boolean bl = this.fdToChannel.isEmpty();
            n = n2;
            if (bl) {
                n = 1;
            }
            this.fdToChannelLock.writeLock().unlock();
        }
        catch (Throwable throwable) {
            this.fdToChannelLock.writeLock().unlock();
            throw throwable;
        }
        if (n != 0 && this.isShutdown()) {
            try {
                this.shutdownNow();
            }
            catch (IOException iOException) {}
        }
    }

    static interface PollableChannel
    extends Closeable {
        public void onEvent(int var1, boolean var2);
    }

}

