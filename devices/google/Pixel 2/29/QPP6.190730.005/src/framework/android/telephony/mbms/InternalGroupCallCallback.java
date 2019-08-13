/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.mbms;

import android.os.Binder;
import android.telephony.mbms.GroupCallCallback;
import android.telephony.mbms.IGroupCallCallback;
import java.util.concurrent.Executor;

public class InternalGroupCallCallback
extends IGroupCallCallback.Stub {
    private final GroupCallCallback mAppCallback;
    private final Executor mExecutor;
    private volatile boolean mIsStopped = false;

    public InternalGroupCallCallback(GroupCallCallback groupCallCallback, Executor executor) {
        this.mAppCallback = groupCallCallback;
        this.mExecutor = executor;
    }

    @Override
    public void onBroadcastSignalStrengthUpdated(final int n) {
        if (this.mIsStopped) {
            return;
        }
        long l = Binder.clearCallingIdentity();
        try {
            Executor executor = this.mExecutor;
            Runnable runnable = new Runnable(){

                @Override
                public void run() {
                    InternalGroupCallCallback.this.mAppCallback.onBroadcastSignalStrengthUpdated(n);
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
                    InternalGroupCallCallback.this.mAppCallback.onError(n, string2);
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
    public void onGroupCallStateChanged(final int n, final int n2) {
        if (this.mIsStopped) {
            return;
        }
        long l = Binder.clearCallingIdentity();
        try {
            Executor executor = this.mExecutor;
            Runnable runnable = new Runnable(){

                @Override
                public void run() {
                    InternalGroupCallCallback.this.mAppCallback.onGroupCallStateChanged(n, n2);
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

