/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.ch;

import java.io.IOException;
import java.nio.channels.AsynchronousChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import sun.nio.ch.Cancellable;

final class PendingFuture<V, A>
implements Future<V> {
    private static final CancellationException CANCELLED = new CancellationException();
    private final A attachment;
    private final AsynchronousChannel channel;
    private volatile Object context;
    private volatile Throwable exc;
    private final CompletionHandler<V, ? super A> handler;
    private volatile boolean haveResult;
    private CountDownLatch latch;
    private volatile V result;
    private Future<?> timeoutTask;

    PendingFuture(AsynchronousChannel asynchronousChannel) {
        this(asynchronousChannel, null, null);
    }

    PendingFuture(AsynchronousChannel asynchronousChannel, Object object) {
        this(asynchronousChannel, null, null, object);
    }

    PendingFuture(AsynchronousChannel asynchronousChannel, CompletionHandler<V, ? super A> completionHandler, A a) {
        this.channel = asynchronousChannel;
        this.handler = completionHandler;
        this.attachment = a;
    }

    PendingFuture(AsynchronousChannel asynchronousChannel, CompletionHandler<V, ? super A> completionHandler, A a, Object object) {
        this.channel = asynchronousChannel;
        this.handler = completionHandler;
        this.attachment = a;
        this.context = object;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean prepareForWait() {
        synchronized (this) {
            if (this.haveResult) {
                return false;
            }
            if (this.latch == null) {
                CountDownLatch countDownLatch;
                this.latch = countDownLatch = new CountDownLatch(1);
            }
            return true;
        }
    }

    A attachment() {
        return this.attachment;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public boolean cancel(boolean bl) {
        CountDownLatch countDownLatch;
        // MONITORENTER : this
        if (this.haveResult) {
            // MONITOREXIT : this
            return false;
        }
        if (this.channel() instanceof Cancellable) {
            ((Cancellable)((Object)this.channel())).onCancel(this);
        }
        this.exc = CANCELLED;
        this.haveResult = true;
        if (this.timeoutTask != null) {
            this.timeoutTask.cancel(false);
        }
        // MONITOREXIT : this
        if (bl) {
            try {
                this.channel().close();
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
        if ((countDownLatch = this.latch) == null) return true;
        countDownLatch.countDown();
        return true;
    }

    AsynchronousChannel channel() {
        return this.channel;
    }

    Throwable exception() {
        Throwable throwable = this.exc != CANCELLED ? this.exc : null;
        return throwable;
    }

    @Override
    public V get() throws ExecutionException, InterruptedException {
        if (!this.haveResult && this.prepareForWait()) {
            this.latch.await();
        }
        if (this.exc != null) {
            if (this.exc == CANCELLED) {
                throw new CancellationException();
            }
            throw new ExecutionException(this.exc);
        }
        return this.result;
    }

    @Override
    public V get(long l, TimeUnit timeUnit) throws ExecutionException, InterruptedException, TimeoutException {
        if (!this.haveResult && this.prepareForWait() && !this.latch.await(l, timeUnit)) {
            throw new TimeoutException();
        }
        if (this.exc != null) {
            if (this.exc == CANCELLED) {
                throw new CancellationException();
            }
            throw new ExecutionException(this.exc);
        }
        return this.result;
    }

    Object getContext() {
        return this.context;
    }

    CompletionHandler<V, ? super A> handler() {
        return this.handler;
    }

    @Override
    public boolean isCancelled() {
        boolean bl = this.exc == CANCELLED;
        return bl;
    }

    @Override
    public boolean isDone() {
        return this.haveResult;
    }

    void setContext(Object object) {
        this.context = object;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void setFailure(Throwable throwable) {
        Throwable throwable2 = throwable;
        if (!(throwable instanceof IOException)) {
            throwable2 = throwable;
            if (!(throwable instanceof SecurityException)) {
                throwable2 = new IOException(throwable);
            }
        }
        synchronized (this) {
            if (this.haveResult) {
                return;
            }
            this.exc = throwable2;
            this.haveResult = true;
            if (this.timeoutTask != null) {
                this.timeoutTask.cancel(false);
            }
            if (this.latch != null) {
                this.latch.countDown();
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void setResult(V v) {
        synchronized (this) {
            if (this.haveResult) {
                return;
            }
            this.result = v;
            this.haveResult = true;
            if (this.timeoutTask != null) {
                this.timeoutTask.cancel(false);
            }
            if (this.latch != null) {
                this.latch.countDown();
            }
            return;
        }
    }

    void setResult(V v, Throwable throwable) {
        if (throwable == null) {
            this.setResult(v);
        } else {
            this.setFailure(throwable);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void setTimeoutTask(Future<?> future) {
        synchronized (this) {
            if (this.haveResult) {
                future.cancel(false);
            } else {
                this.timeoutTask = future;
            }
            return;
        }
    }

    V value() {
        return this.result;
    }
}

