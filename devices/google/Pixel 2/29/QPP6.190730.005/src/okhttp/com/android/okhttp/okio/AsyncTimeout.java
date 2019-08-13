/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp.okio;

import com.android.okhttp.okio.Buffer;
import com.android.okhttp.okio.Sink;
import com.android.okhttp.okio.Source;
import com.android.okhttp.okio.Timeout;
import java.io.IOException;
import java.io.InterruptedIOException;

public class AsyncTimeout
extends Timeout {
    private static AsyncTimeout head;
    private boolean inQueue;
    private AsyncTimeout next;
    private long timeoutAt;

    private static AsyncTimeout awaitTimeout() throws InterruptedException {
        synchronized (AsyncTimeout.class) {
            AsyncTimeout asyncTimeout = AsyncTimeout.head.next;
            if (asyncTimeout == null) {
                AsyncTimeout.class.wait();
                return null;
            }
            long l = asyncTimeout.remainingNanos(System.nanoTime());
            if (l > 0L) {
                long l2 = l / 1000000L;
                AsyncTimeout.class.wait(l2, (int)(l - 1000000L * l2));
                return null;
            }
            AsyncTimeout.head.next = asyncTimeout.next;
            asyncTimeout.next = null;
            return asyncTimeout;
        }
    }

    private static boolean cancelScheduledTimeout(AsyncTimeout asyncTimeout) {
        synchronized (AsyncTimeout.class) {
            AsyncTimeout asyncTimeout2;
            try {
                asyncTimeout2 = head;
            }
            catch (Throwable throwable) {
                throw throwable;
            }
            finally {
            }
            while (asyncTimeout2 != null) {
                AsyncTimeout asyncTimeout3;
                block7 : {
                    asyncTimeout3 = asyncTimeout2.next;
                    if (asyncTimeout3 != asyncTimeout) break block7;
                    asyncTimeout2.next = asyncTimeout.next;
                    asyncTimeout.next = null;
                    return false;
                }
                asyncTimeout2 = asyncTimeout3;
            }
            return true;
        }
    }

    private long remainingNanos(long l) {
        return this.timeoutAt - l;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static void scheduleTimeout(AsyncTimeout object, long l, boolean bl) {
        synchronized (AsyncTimeout.class) {
            Object object2;
            long l2;
            void var3_2;
            if (head == null) {
                object2 = new AsyncTimeout();
                head = object2;
                object2 = new Watchdog();
                ((Thread)object2).start();
            }
            long l3 = System.nanoTime();
            if (l2 != 0L && var3_2 != false) {
                ((AsyncTimeout)object).timeoutAt = Math.min(l2, ((Timeout)object).deadlineNanoTime() - l3) + l3;
            } else if (l2 != 0L) {
                ((AsyncTimeout)object).timeoutAt = l3 + l2;
            } else {
                if (var3_2 == false) {
                    object = new AssertionError();
                    throw object;
                }
                ((AsyncTimeout)object).timeoutAt = ((Timeout)object).deadlineNanoTime();
            }
            l2 = AsyncTimeout.super.remainingNanos(l3);
            object2 = head;
            while (((AsyncTimeout)object2).next != null && l2 >= ((AsyncTimeout)object2).next.remainingNanos(l3)) {
                object2 = ((AsyncTimeout)object2).next;
            }
            ((AsyncTimeout)object).next = ((AsyncTimeout)object2).next;
            ((AsyncTimeout)object2).next = object;
            if (object2 == head) {
                AsyncTimeout.class.notify();
            }
            return;
        }
    }

    public final void enter() {
        if (!this.inQueue) {
            long l = this.timeoutNanos();
            boolean bl = this.hasDeadline();
            if (l == 0L && !bl) {
                return;
            }
            this.inQueue = true;
            AsyncTimeout.scheduleTimeout(this, l, bl);
            return;
        }
        throw new IllegalStateException("Unbalanced enter/exit");
    }

    final IOException exit(IOException iOException) throws IOException {
        if (!this.exit()) {
            return iOException;
        }
        return this.newTimeoutException(iOException);
    }

    final void exit(boolean bl) throws IOException {
        if (this.exit() && bl) {
            throw this.newTimeoutException(null);
        }
    }

    public final boolean exit() {
        if (!this.inQueue) {
            return false;
        }
        this.inQueue = false;
        return AsyncTimeout.cancelScheduledTimeout(this);
    }

    protected IOException newTimeoutException(IOException iOException) {
        InterruptedIOException interruptedIOException = new InterruptedIOException("timeout");
        if (iOException != null) {
            interruptedIOException.initCause(iOException);
        }
        return interruptedIOException;
    }

    public final Sink sink(final Sink sink) {
        return new Sink(){

            /*
             * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
             * Loose catch block
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public void close() throws IOException {
                Throwable throwable2222;
                AsyncTimeout.this.enter();
                sink.close();
                AsyncTimeout.this.exit(true);
                return;
                {
                    catch (Throwable throwable2222) {
                    }
                    catch (IOException iOException) {}
                    {
                        throw AsyncTimeout.this.exit(iOException);
                    }
                }
                AsyncTimeout.this.exit(false);
                throw throwable2222;
            }

            /*
             * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
             * Loose catch block
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public void flush() throws IOException {
                Throwable throwable2222;
                AsyncTimeout.this.enter();
                sink.flush();
                AsyncTimeout.this.exit(true);
                return;
                {
                    catch (Throwable throwable2222) {
                    }
                    catch (IOException iOException) {}
                    {
                        throw AsyncTimeout.this.exit(iOException);
                    }
                }
                AsyncTimeout.this.exit(false);
                throw throwable2222;
            }

            @Override
            public Timeout timeout() {
                return AsyncTimeout.this;
            }

            public String toString() {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("AsyncTimeout.sink(");
                stringBuilder.append(sink);
                stringBuilder.append(")");
                return stringBuilder.toString();
            }

            /*
             * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
             * Loose catch block
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public void write(Buffer buffer, long l) throws IOException {
                Throwable throwable2222;
                AsyncTimeout.this.enter();
                sink.write(buffer, l);
                AsyncTimeout.this.exit(true);
                return;
                {
                    catch (Throwable throwable2222) {
                    }
                    catch (IOException iOException) {}
                    {
                        throw AsyncTimeout.this.exit(iOException);
                    }
                }
                AsyncTimeout.this.exit(false);
                throw throwable2222;
            }
        };
    }

    public final Source source(final Source source) {
        return new Source(){

            /*
             * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
             * Loose catch block
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public void close() throws IOException {
                Throwable throwable2222;
                source.close();
                AsyncTimeout.this.exit(true);
                return;
                {
                    catch (Throwable throwable2222) {
                    }
                    catch (IOException iOException) {}
                    {
                        throw AsyncTimeout.this.exit(iOException);
                    }
                }
                AsyncTimeout.this.exit(false);
                throw throwable2222;
            }

            /*
             * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
             * Loose catch block
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public long read(Buffer buffer, long l) throws IOException {
                Throwable throwable2222;
                AsyncTimeout.this.enter();
                l = source.read(buffer, l);
                AsyncTimeout.this.exit(true);
                return l;
                {
                    catch (Throwable throwable2222) {
                    }
                    catch (IOException iOException) {}
                    {
                        throw AsyncTimeout.this.exit(iOException);
                    }
                }
                AsyncTimeout.this.exit(false);
                throw throwable2222;
            }

            @Override
            public Timeout timeout() {
                return AsyncTimeout.this;
            }

            public String toString() {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("AsyncTimeout.source(");
                stringBuilder.append(source);
                stringBuilder.append(")");
                return stringBuilder.toString();
            }
        };
    }

    protected void timedOut() {
    }

    private static final class Watchdog
    extends Thread {
        public Watchdog() {
            super("Okio Watchdog");
            this.setDaemon(true);
        }

        @Override
        public void run() {
            do {
                AsyncTimeout asyncTimeout = AsyncTimeout.awaitTimeout();
                if (asyncTimeout == null) continue;
                try {
                    asyncTimeout.timedOut();
                }
                catch (InterruptedException interruptedException) {
                }
            } while (true);
        }
    }

}

