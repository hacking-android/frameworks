/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.util;

import android.os.Process;
import android.util.Slog;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ConcurrentUtils {
    private ConcurrentUtils() {
    }

    public static ExecutorService newFixedThreadPool(int n, String string2, final int n2) {
        return Executors.newFixedThreadPool(n, new ThreadFactory(){
            private final AtomicInteger threadNum = new AtomicInteger(0);

            @Override
            public Thread newThread(final Runnable runnable) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(String.this);
                stringBuilder.append(this.threadNum.incrementAndGet());
                return new Thread(stringBuilder.toString()){

                    @Override
                    public void run() {
                        Process.setThreadPriority(n2);
                        runnable.run();
                    }
                };
            }

        });
    }

    public static void waitForCountDownNoInterrupt(CountDownLatch object, long l, String string2) {
        try {
            if (((CountDownLatch)object).await(l, TimeUnit.MILLISECONDS)) {
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append(" timed out.");
            IllegalStateException illegalStateException = new IllegalStateException(((StringBuilder)object).toString());
            throw illegalStateException;
        }
        catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append(" interrupted.");
            throw new IllegalStateException(stringBuilder.toString());
        }
    }

    public static <T> T waitForFutureNoInterrupt(Future<T> future, String string2) {
        try {
            future = future.get();
        }
        catch (ExecutionException executionException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append(" failed");
            throw new RuntimeException(stringBuilder.toString(), executionException);
        }
        catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append(" interrupted");
            throw new IllegalStateException(stringBuilder.toString());
        }
        return (T)future;
    }

    public static void wtfIfLockHeld(String string2, Object object) {
        if (Thread.holdsLock(object)) {
            Slog.wtf(string2, "Lock mustn't be held");
        }
    }

    public static void wtfIfLockNotHeld(String string2, Object object) {
        if (!Thread.holdsLock(object)) {
            Slog.wtf(string2, "Lock must be held");
        }
    }

}

