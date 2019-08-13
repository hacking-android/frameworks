/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class SynchronousResultReceiver
extends ResultReceiver {
    private final CompletableFuture<Result> mFuture = new CompletableFuture();
    private final String mName;

    public SynchronousResultReceiver() {
        super((Handler)null);
        this.mName = null;
    }

    public SynchronousResultReceiver(String string2) {
        super((Handler)null);
        this.mName = string2;
    }

    public Result awaitResult(long l) throws TimeoutException {
        long l2 = System.currentTimeMillis();
        for (long i = l; i >= 0L; i -= l2 + l - System.currentTimeMillis()) {
            try {
                Result result = this.mFuture.get(i, TimeUnit.MILLISECONDS);
                return result;
            }
            catch (InterruptedException interruptedException) {
                continue;
            }
            catch (ExecutionException executionException) {
                throw new AssertionError("Error receiving response", executionException);
            }
        }
        throw new TimeoutException();
    }

    public String getName() {
        return this.mName;
    }

    @Override
    protected final void onReceiveResult(int n, Bundle bundle) {
        super.onReceiveResult(n, bundle);
        this.mFuture.complete(new Result(n, bundle));
    }

    public static class Result {
        public Bundle bundle;
        public int resultCode;

        public Result(int n, Bundle bundle) {
            this.resultCode = n;
            this.bundle = bundle;
        }
    }

}

