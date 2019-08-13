/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.mbms;

import android.os.Binder;
import android.os.RemoteException;
import android.telephony.mbms.IStreamingServiceCallback;
import android.telephony.mbms.StreamingServiceCallback;
import java.util.concurrent.Executor;

public class InternalStreamingServiceCallback
extends IStreamingServiceCallback.Stub {
    private final StreamingServiceCallback mAppCallback;
    private final Executor mExecutor;
    private volatile boolean mIsStopped = false;

    public InternalStreamingServiceCallback(StreamingServiceCallback streamingServiceCallback, Executor executor) {
        this.mAppCallback = streamingServiceCallback;
        this.mExecutor = executor;
    }

    @Override
    public void onBroadcastSignalStrengthUpdated(final int n) throws RemoteException {
        if (this.mIsStopped) {
            return;
        }
        long l = Binder.clearCallingIdentity();
        try {
            Executor executor = this.mExecutor;
            Runnable runnable = new Runnable(){

                @Override
                public void run() {
                    InternalStreamingServiceCallback.this.mAppCallback.onBroadcastSignalStrengthUpdated(n);
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
                    InternalStreamingServiceCallback.this.mAppCallback.onError(n, string2);
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
    public void onMediaDescriptionUpdated() throws RemoteException {
        if (this.mIsStopped) {
            return;
        }
        long l = Binder.clearCallingIdentity();
        try {
            Executor executor = this.mExecutor;
            Runnable runnable = new Runnable(){

                @Override
                public void run() {
                    InternalStreamingServiceCallback.this.mAppCallback.onMediaDescriptionUpdated();
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
    public void onStreamMethodUpdated(final int n) throws RemoteException {
        if (this.mIsStopped) {
            return;
        }
        long l = Binder.clearCallingIdentity();
        try {
            Executor executor = this.mExecutor;
            Runnable runnable = new Runnable(){

                @Override
                public void run() {
                    InternalStreamingServiceCallback.this.mAppCallback.onStreamMethodUpdated(n);
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
    public void onStreamStateUpdated(final int n, final int n2) throws RemoteException {
        if (this.mIsStopped) {
            return;
        }
        long l = Binder.clearCallingIdentity();
        try {
            Executor executor = this.mExecutor;
            Runnable runnable = new Runnable(){

                @Override
                public void run() {
                    InternalStreamingServiceCallback.this.mAppCallback.onStreamStateUpdated(n, n2);
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

