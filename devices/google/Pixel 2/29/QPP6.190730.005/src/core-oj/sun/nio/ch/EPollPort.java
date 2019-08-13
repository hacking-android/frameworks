/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.ch;

import java.io.IOException;
import java.nio.channels.spi.AsynchronousChannelProvider;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import sun.nio.ch.EPoll;
import sun.nio.ch.Invoker;
import sun.nio.ch.Net;
import sun.nio.ch.Port;
import sun.nio.ch.ThreadPool;

final class EPollPort
extends Port {
    private static final int ENOENT = 2;
    private static final int MAX_EPOLL_EVENTS = 512;
    private final Event EXECUTE_TASK_OR_SHUTDOWN = new Event(null, 0);
    private final Event NEED_TO_POLL = new Event(null, 0);
    private final long address;
    private boolean closed;
    private final int epfd = EPoll.epollCreate();
    private final ArrayBlockingQueue<Event> queue;
    private final int[] sp;
    private final AtomicInteger wakeupCount = new AtomicInteger();

    EPollPort(AsynchronousChannelProvider arrn, ThreadPool threadPool) throws IOException {
        super((AsynchronousChannelProvider)arrn, threadPool);
        arrn = new int[2];
        try {
            EPollPort.socketpair(arrn);
            EPoll.epollCtl(this.epfd, 1, arrn[0], Net.POLLIN);
            this.sp = arrn;
        }
        catch (IOException iOException) {
            EPollPort.close0(this.epfd);
            throw iOException;
        }
        this.address = EPoll.allocatePollArray(512);
        this.queue = new ArrayBlockingQueue(512);
        this.queue.offer(this.NEED_TO_POLL);
    }

    static /* synthetic */ int access$100(EPollPort ePollPort) {
        return ePollPort.epfd;
    }

    static /* synthetic */ long access$200(EPollPort ePollPort) {
        return ePollPort.address;
    }

    static /* synthetic */ int[] access$300(EPollPort ePollPort) {
        return ePollPort.sp;
    }

    static /* synthetic */ AtomicInteger access$400(EPollPort ePollPort) {
        return ePollPort.wakeupCount;
    }

    static /* synthetic */ void access$500(int n) throws IOException {
        EPollPort.drain1(n);
    }

    static /* synthetic */ Event access$600(EPollPort ePollPort) {
        return ePollPort.EXECUTE_TASK_OR_SHUTDOWN;
    }

    static /* synthetic */ ArrayBlockingQueue access$700(EPollPort ePollPort) {
        return ePollPort.queue;
    }

    static /* synthetic */ Event access$800(EPollPort ePollPort) {
        return ePollPort.NEED_TO_POLL;
    }

    static /* synthetic */ void access$900(EPollPort ePollPort) {
        ePollPort.implClose();
    }

    private static native void close0(int var0);

    private static native void drain1(int var0) throws IOException;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void implClose() {
        synchronized (this) {
            if (this.closed) {
                return;
            }
            this.closed = true;
        }
        EPoll.freePollArray(this.address);
        EPollPort.close0(this.sp[0]);
        EPollPort.close0(this.sp[1]);
        EPollPort.close0(this.epfd);
    }

    private static native void interrupt(int var0) throws IOException;

    private static native void socketpair(int[] var0) throws IOException;

    private void wakeup() {
        if (this.wakeupCount.incrementAndGet() == 1) {
            try {
                EPollPort.interrupt(this.sp[1]);
            }
            catch (IOException iOException) {
                throw new AssertionError(iOException);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    void executeOnHandlerTask(Runnable object) {
        synchronized (this) {
            if (!this.closed) {
                this.offerTask((Runnable)object);
                this.wakeup();
                return;
            }
            object = new RejectedExecutionException();
            throw object;
        }
    }

    @Override
    void shutdownHandlerTasks() {
        int n;
        if (n == 0) {
            this.implClose();
        } else {
            for (int i = n = this.threadCount(); i > 0; --i) {
                this.wakeup();
            }
        }
    }

    EPollPort start() {
        this.startThreads(new EventHandlerTask());
        return this;
    }

    @Override
    void startPoll(int n, int n2) {
        int n3;
        int n4 = n3 = EPoll.epollCtl(this.epfd, 3, n, n2 | 1073741824);
        if (n3 == 2) {
            n4 = EPoll.epollCtl(this.epfd, 1, n, 1073741824 | n2);
        }
        if (n4 == 0) {
            return;
        }
        throw new AssertionError();
    }

    static class Event {
        final Port.PollableChannel channel;
        final int events;

        Event(Port.PollableChannel pollableChannel, int n) {
            this.channel = pollableChannel;
            this.events = n;
        }

        Port.PollableChannel channel() {
            return this.channel;
        }

        int events() {
            return this.events;
        }
    }

    private class EventHandlerTask
    implements Runnable {
        private EventHandlerTask() {
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        private Event poll() throws IOException {
            do {
                var1_1 = EPoll.epollWait(EPollPort.access$100(EPollPort.this), EPollPort.access$200(EPollPort.this), 512);
                EPollPort.this.fdToChannelLock.readLock().lock();
                do {
                    block14 : {
                        block15 : {
                            block13 : {
                                var2_2 = var1_1 - 1;
                                if (var1_1 <= 0) ** GOTO lbl45
                                var3_3 = EPoll.getEvent(EPollPort.access$200(EPollPort.this), var2_2);
                                var1_1 = EPoll.getDescriptor(var3_3);
                                if (var1_1 != EPollPort.access$300(EPollPort.this)[0]) break block13;
                                if (EPollPort.access$400(EPollPort.this).decrementAndGet() == 0) {
                                    EPollPort.access$500(EPollPort.access$300(EPollPort.this)[0]);
                                }
                                if (var2_2 > 0) {
                                    EPollPort.access$700(EPollPort.this).offer(EPollPort.access$600(EPollPort.this));
                                    break block14;
                                }
                                var5_4 = EPollPort.access$600(EPollPort.this);
                                EPollPort.this.fdToChannelLock.readLock().unlock();
                                return var5_4;
                            }
                            var6_7 = (Port.PollableChannel)EPollPort.this.fdToChannel.get(var1_1);
                            if (var6_7 == null) ** GOTO lbl38
                            var1_1 = EPoll.getEvents(var3_3);
                            var5_4 = new Event(var6_7, var1_1);
                            if (var2_2 <= 0) break block15;
                            EPollPort.access$700(EPollPort.this).offer(var5_4);
                        }
                        EPollPort.this.fdToChannelLock.readLock().unlock();
                        EPollPort.access$700(EPollPort.this).offer(EPollPort.access$800(EPollPort.this));
                        return var5_4;
                    }
                    var1_1 = var2_2;
                } while (true);
                catch (Throwable var5_5) {
                    EPollPort.this.fdToChannelLock.readLock().unlock();
                    throw var5_5;
lbl45: // 1 sources:
                    EPollPort.this.fdToChannelLock.readLock().unlock();
                    continue;
                }
                break;
            } while (true);
            finally {
                EPollPort.access$700(EPollPort.this).offer(EPollPort.access$800(EPollPort.this));
            }
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        @Override
        public void run() {
            var1_1 = Invoker.getGroupAndInvokeCount();
            var2_2 = var1_1 != null;
            var3_3 = false;
            do {
                block14 : {
                    if (!var2_2) ** GOTO lbl8
                    try {
                        var1_1.resetInvokeCount();
lbl8: // 2 sources:
                        var4_4 = false;
                        var5_5 = false;
                        var3_3 = var4_4;
                        var6_6 = (Event)EPollPort.access$700(EPollPort.this).take();
                        var3_3 = var4_4;
                        var7_7 = EPollPort.access$800(EPollPort.this);
                        var8_8 = var6_6;
                        if (var6_6 != var7_7) break block13;
                        var3_3 = var4_4;
                    }
                    catch (Throwable var8_12) {
                        if (EPollPort.this.threadExit(this, var3_3) != 0) throw var8_12;
                        if (EPollPort.this.isShutdown() == false) throw var8_12;
                        EPollPort.access$900(EPollPort.this);
                        throw var8_12;
                    }
                    {
                        block13 : {
                            try {
                                var8_8 = this.poll();
                            }
                            catch (IOException var8_9) {
                                var3_3 = var4_4;
                                var8_9.printStackTrace();
                                if (EPollPort.this.threadExit(this, false) != 0) return;
                                if (EPollPort.this.isShutdown() == false) return;
                                EPollPort.access$900(EPollPort.this);
                                return;
                            }
                        }
                        var3_3 = var4_4;
                    }
                    if (var8_8 != EPollPort.access$600(EPollPort.this)) ** GOTO lbl51
                    var3_3 = var4_4;
                    var8_8 = EPollPort.this.pollTask();
                    if (var8_8 != null) break block14;
                    if (EPollPort.this.threadExit(this, false) != 0) return;
                    if (EPollPort.this.isShutdown() == false) return;
                    EPollPort.access$900(EPollPort.this);
                    return;
                }
                var3_3 = true;
                var5_5 = true;
                var8_8.run();
                var3_3 = var5_5;
                continue;
lbl51: // 1 sources:
                var3_3 = var4_4;
                try {
                    var8_8.channel().onEvent(var8_8.events(), var2_2);
                    var3_3 = var5_5;
                    continue;
                }
                catch (RuntimeException var8_10) {
                    var3_3 = true;
                    throw var8_10;
                }
                catch (Error var8_11) {
                    var3_3 = true;
                    throw var8_11;
                }
                catch (InterruptedException var8_13) {
                    var3_3 = var5_5;
                    continue;
                }
                break;
            } while (true);
        }
    }

}

