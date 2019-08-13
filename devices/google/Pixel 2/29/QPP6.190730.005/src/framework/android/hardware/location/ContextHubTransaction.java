/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.location;

import android.annotation.SystemApi;
import android.hardware.location._$$Lambda$ContextHubTransaction$7a5H6DrY_dOy9M3qnYHhlmDHRNQ;
import android.hardware.location._$$Lambda$ContextHubTransaction$RNVGnle3xCUm9u68syzn6_2znnU;
import android.os.Handler;
import android.os.HandlerExecutor;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@SystemApi
public class ContextHubTransaction<T> {
    public static final int RESULT_FAILED_AT_HUB = 5;
    public static final int RESULT_FAILED_BAD_PARAMS = 2;
    public static final int RESULT_FAILED_BUSY = 4;
    public static final int RESULT_FAILED_HAL_UNAVAILABLE = 8;
    public static final int RESULT_FAILED_SERVICE_INTERNAL_FAILURE = 7;
    public static final int RESULT_FAILED_TIMEOUT = 6;
    public static final int RESULT_FAILED_UNINITIALIZED = 3;
    public static final int RESULT_FAILED_UNKNOWN = 1;
    public static final int RESULT_SUCCESS = 0;
    private static final String TAG = "ContextHubTransaction";
    public static final int TYPE_DISABLE_NANOAPP = 3;
    public static final int TYPE_ENABLE_NANOAPP = 2;
    public static final int TYPE_LOAD_NANOAPP = 0;
    public static final int TYPE_QUERY_NANOAPPS = 4;
    public static final int TYPE_UNLOAD_NANOAPP = 1;
    private final CountDownLatch mDoneSignal = new CountDownLatch(1);
    private Executor mExecutor = null;
    private boolean mIsResponseSet = false;
    private OnCompleteListener<T> mListener = null;
    private Response<T> mResponse;
    private int mTransactionType;

    ContextHubTransaction(int n) {
        this.mTransactionType = n;
    }

    public static String typeToString(int n, boolean bl) {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            String string2 = bl ? "Unknown" : "unknown";
                            return string2;
                        }
                        String string3 = bl ? "Query" : "query";
                        return string3;
                    }
                    String string4 = bl ? "Disable" : "disable";
                    return string4;
                }
                String string5 = bl ? "Enable" : "enable";
                return string5;
            }
            String string6 = bl ? "Unload" : "unload";
            return string6;
        }
        String string7 = bl ? "Load" : "load";
        return string7;
    }

    public int getType() {
        return this.mTransactionType;
    }

    public /* synthetic */ void lambda$setOnCompleteListener$0$ContextHubTransaction() {
        this.mListener.onComplete(this, this.mResponse);
    }

    public /* synthetic */ void lambda$setResponse$1$ContextHubTransaction() {
        this.mListener.onComplete(this, this.mResponse);
    }

    public void setOnCompleteListener(OnCompleteListener<T> onCompleteListener) {
        this.setOnCompleteListener(onCompleteListener, new HandlerExecutor(Handler.getMain()));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setOnCompleteListener(OnCompleteListener<T> object, Executor executor) {
        synchronized (this) {
            Preconditions.checkNotNull(object, "OnCompleteListener cannot be null");
            Preconditions.checkNotNull(executor, "Executor cannot be null");
            if (this.mListener != null) {
                object = new IllegalStateException("Cannot set ContextHubTransaction listener multiple times");
                throw object;
            }
            this.mListener = object;
            this.mExecutor = executor;
            if (this.mDoneSignal.getCount() == 0L) {
                executor = this.mExecutor;
                object = new _$$Lambda$ContextHubTransaction$7a5H6DrY_dOy9M3qnYHhlmDHRNQ(this);
                executor.execute((Runnable)object);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void setResponse(Response<T> object) {
        synchronized (this) {
            Preconditions.checkNotNull(object, "Response cannot be null");
            if (this.mIsResponseSet) {
                object = new IllegalStateException("Cannot set response of ContextHubTransaction multiple times");
                throw object;
            }
            this.mResponse = object;
            this.mIsResponseSet = true;
            this.mDoneSignal.countDown();
            if (this.mListener != null) {
                object = this.mExecutor;
                _$$Lambda$ContextHubTransaction$RNVGnle3xCUm9u68syzn6_2znnU _$$Lambda$ContextHubTransaction$RNVGnle3xCUm9u68syzn6_2znnU = new _$$Lambda$ContextHubTransaction$RNVGnle3xCUm9u68syzn6_2znnU(this);
                object.execute(_$$Lambda$ContextHubTransaction$RNVGnle3xCUm9u68syzn6_2znnU);
            }
            return;
        }
    }

    public Response<T> waitForResponse(long l, TimeUnit timeUnit) throws InterruptedException, TimeoutException {
        if (this.mDoneSignal.await(l, timeUnit)) {
            return this.mResponse;
        }
        throw new TimeoutException("Timed out while waiting for transaction");
    }

    @FunctionalInterface
    public static interface OnCompleteListener<L> {
        public void onComplete(ContextHubTransaction<L> var1, Response<L> var2);
    }

    public static class Response<R> {
        private R mContents;
        private int mResult;

        Response(int n, R r) {
            this.mResult = n;
            this.mContents = r;
        }

        public R getContents() {
            return this.mContents;
        }

        public int getResult() {
            return this.mResult;
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface Result {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface Type {
    }

}

