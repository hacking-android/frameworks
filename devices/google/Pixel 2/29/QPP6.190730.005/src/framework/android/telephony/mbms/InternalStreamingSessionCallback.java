/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.mbms;

import android.os.Binder;
import android.os.RemoteException;
import android.telephony.mbms.IMbmsStreamingSessionCallback;
import android.telephony.mbms.MbmsStreamingSessionCallback;
import android.telephony.mbms.StreamingServiceInfo;
import java.util.List;
import java.util.concurrent.Executor;

public class InternalStreamingSessionCallback
extends IMbmsStreamingSessionCallback.Stub {
    private final MbmsStreamingSessionCallback mAppCallback;
    private final Executor mExecutor;
    private volatile boolean mIsStopped = false;

    public InternalStreamingSessionCallback(MbmsStreamingSessionCallback mbmsStreamingSessionCallback, Executor executor) {
        this.mAppCallback = mbmsStreamingSessionCallback;
        this.mExecutor = executor;
    }

    @Override
    public void onError(final int n, final String string2) throws RemoteException {
        if (this.mIsStopped) {
            return;
        }
        long l = Binder.clearCallingIdentity();
        try {
            Executor executor = this.mExecutor;
            Runnable runnable = new Runnable(){

                @Override
                public void run() {
                    InternalStreamingSessionCallback.this.mAppCallback.onError(n, string2);
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
    public void onMiddlewareReady() throws RemoteException {
        if (this.mIsStopped) {
            return;
        }
        long l = Binder.clearCallingIdentity();
        try {
            Executor executor = this.mExecutor;
            Runnable runnable = new Runnable(){

                @Override
                public void run() {
                    InternalStreamingSessionCallback.this.mAppCallback.onMiddlewareReady();
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
    public void onStreamingServicesUpdated(final List<StreamingServiceInfo> list) throws RemoteException {
        if (this.mIsStopped) {
            return;
        }
        long l = Binder.clearCallingIdentity();
        try {
            Executor executor = this.mExecutor;
            Runnable runnable = new Runnable(){

                @Override
                public void run() {
                    InternalStreamingSessionCallback.this.mAppCallback.onStreamingServicesUpdated(list);
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

