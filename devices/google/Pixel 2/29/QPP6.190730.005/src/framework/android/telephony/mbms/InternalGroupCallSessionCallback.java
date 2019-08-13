/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.mbms;

import android.os.Binder;
import android.telephony.mbms.IMbmsGroupCallSessionCallback;
import android.telephony.mbms.MbmsGroupCallSessionCallback;
import java.util.List;
import java.util.concurrent.Executor;

public class InternalGroupCallSessionCallback
extends IMbmsGroupCallSessionCallback.Stub {
    private final MbmsGroupCallSessionCallback mAppCallback;
    private final Executor mExecutor;
    private volatile boolean mIsStopped = false;

    public InternalGroupCallSessionCallback(MbmsGroupCallSessionCallback mbmsGroupCallSessionCallback, Executor executor) {
        this.mAppCallback = mbmsGroupCallSessionCallback;
        this.mExecutor = executor;
    }

    @Override
    public void onAvailableSaisUpdated(final List list, final List list2) {
        if (this.mIsStopped) {
            return;
        }
        long l = Binder.clearCallingIdentity();
        try {
            Executor executor = this.mExecutor;
            Runnable runnable = new Runnable(){

                @Override
                public void run() {
                    InternalGroupCallSessionCallback.this.mAppCallback.onAvailableSaisUpdated(list, list2);
                }
            };
            executor.execute(runnable);
            return;
        }
        finally {
            Binder.restoreCallingIdentity(l);
        }
    }

    @Override
    public void onError(final int n, final String string2) {
        if (this.mIsStopped) {
            return;
        }
        long l = Binder.clearCallingIdentity();
        try {
            Executor executor = this.mExecutor;
            Runnable runnable = new Runnable(){

                @Override
                public void run() {
                    InternalGroupCallSessionCallback.this.mAppCallback.onError(n, string2);
                }
            };
            executor.execute(runnable);
            return;
        }
        finally {
            Binder.restoreCallingIdentity(l);
        }
    }

    @Override
    public void onMiddlewareReady() {
        if (this.mIsStopped) {
            return;
        }
        long l = Binder.clearCallingIdentity();
        try {
            Executor executor = this.mExecutor;
            Runnable runnable = new Runnable(){

                @Override
                public void run() {
                    InternalGroupCallSessionCallback.this.mAppCallback.onMiddlewareReady();
                }
            };
            executor.execute(runnable);
            return;
        }
        finally {
            Binder.restoreCallingIdentity(l);
        }
    }

    @Override
    public void onServiceInterfaceAvailable(final String string2, final int n) {
        if (this.mIsStopped) {
            return;
        }
        long l = Binder.clearCallingIdentity();
        try {
            Executor executor = this.mExecutor;
            Runnable runnable = new Runnable(){

                @Override
                public void run() {
                    InternalGroupCallSessionCallback.this.mAppCallback.onServiceInterfaceAvailable(string2, n);
                }
            };
            executor.execute(runnable);
            return;
        }
        finally {
            Binder.restoreCallingIdentity(l);
        }
    }

    public void stop() {
        this.mIsStopped = true;
    }

}

