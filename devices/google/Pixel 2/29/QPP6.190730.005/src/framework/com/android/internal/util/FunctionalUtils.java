/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.util;

import android.os.RemoteException;
import android.util.ExceptionUtils;
import com.android.internal.util._$$Lambda$FunctionalUtils$koCSI8D7Nu5vOJTVTEj0m3leo_U;
import java.util.function.Consumer;

public class FunctionalUtils {
    private FunctionalUtils() {
    }

    public static Runnable handleExceptions(ThrowingRunnable throwingRunnable, Consumer<Throwable> consumer) {
        return new _$$Lambda$FunctionalUtils$koCSI8D7Nu5vOJTVTEj0m3leo_U(throwingRunnable, consumer);
    }

    public static <T> Consumer<T> ignoreRemoteException(RemoteExceptionIgnoringConsumer<T> remoteExceptionIgnoringConsumer) {
        return remoteExceptionIgnoringConsumer;
    }

    static /* synthetic */ void lambda$handleExceptions$0(ThrowingRunnable throwingRunnable, Consumer consumer) {
        try {
            throwingRunnable.run();
        }
        catch (Throwable throwable) {
            consumer.accept(throwable);
        }
    }

    public static <T> Consumer<T> uncheckExceptions(ThrowingConsumer<T> throwingConsumer) {
        return throwingConsumer;
    }

    @FunctionalInterface
    public static interface RemoteExceptionIgnoringConsumer<T>
    extends Consumer<T> {
        @Override
        default public void accept(T t) {
            try {
                this.acceptOrThrow(t);
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }

        public void acceptOrThrow(T var1) throws RemoteException;
    }

    @FunctionalInterface
    public static interface ThrowingConsumer<T>
    extends Consumer<T> {
        @Override
        default public void accept(T t) {
            try {
                this.acceptOrThrow(t);
                return;
            }
            catch (Exception exception) {
                throw ExceptionUtils.propagate(exception);
            }
        }

        public void acceptOrThrow(T var1) throws Exception;
    }

    @FunctionalInterface
    public static interface ThrowingRunnable
    extends Runnable {
        @Override
        default public void run() {
            try {
                this.runOrThrow();
                return;
            }
            catch (Exception exception) {
                throw ExceptionUtils.propagate(exception);
            }
        }

        public void runOrThrow() throws Exception;
    }

    @FunctionalInterface
    public static interface ThrowingSupplier<T> {
        public T getOrThrow() throws Exception;
    }

}

