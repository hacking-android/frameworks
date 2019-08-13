/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.ch;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

final class CompletedFuture<V>
implements Future<V> {
    private final Throwable exc;
    private final V result;

    private CompletedFuture(V v, Throwable throwable) {
        this.result = v;
        this.exc = throwable;
    }

    static <V> CompletedFuture<V> withFailure(Throwable throwable) {
        Throwable throwable2 = throwable;
        if (!(throwable instanceof IOException)) {
            throwable2 = throwable;
            if (!(throwable instanceof SecurityException)) {
                throwable2 = new IOException(throwable);
            }
        }
        return new CompletedFuture<Object>(null, throwable2);
    }

    static <V> CompletedFuture<V> withResult(V v) {
        return new CompletedFuture<V>(v, null);
    }

    static <V> CompletedFuture<V> withResult(V v, Throwable throwable) {
        if (throwable == null) {
            return CompletedFuture.withResult(v);
        }
        return CompletedFuture.withFailure(throwable);
    }

    @Override
    public boolean cancel(boolean bl) {
        return false;
    }

    @Override
    public V get() throws ExecutionException {
        Throwable throwable = this.exc;
        if (throwable == null) {
            return this.result;
        }
        throw new ExecutionException(throwable);
    }

    @Override
    public V get(long l, TimeUnit object) throws ExecutionException {
        if (object != null) {
            object = this.exc;
            if (object == null) {
                return this.result;
            }
            throw new ExecutionException((Throwable)object);
        }
        throw new NullPointerException();
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return true;
    }
}

