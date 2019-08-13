/*
 * Decompiled with CFR 0.145.
 */
package android.service.attention;

import android.annotation.SystemApi;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.service.attention.IAttentionCallback;
import android.service.attention.IAttentionService;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SystemApi
public abstract class AttentionService
extends Service {
    public static final int ATTENTION_FAILURE_CAMERA_PERMISSION_ABSENT = 6;
    public static final int ATTENTION_FAILURE_CANCELLED = 3;
    public static final int ATTENTION_FAILURE_PREEMPTED = 4;
    public static final int ATTENTION_FAILURE_TIMED_OUT = 5;
    public static final int ATTENTION_FAILURE_UNKNOWN = 2;
    public static final int ATTENTION_SUCCESS_ABSENT = 0;
    public static final int ATTENTION_SUCCESS_PRESENT = 1;
    public static final String SERVICE_INTERFACE = "android.service.attention.AttentionService";
    private final IAttentionService.Stub mBinder = new IAttentionService.Stub(){

        @Override
        public void cancelAttentionCheck(IAttentionCallback iAttentionCallback) {
            Preconditions.checkNotNull(iAttentionCallback);
            AttentionService.this.onCancelAttentionCheck(new AttentionCallback(iAttentionCallback));
        }

        @Override
        public void checkAttention(IAttentionCallback iAttentionCallback) {
            Preconditions.checkNotNull(iAttentionCallback);
            AttentionService.this.onCheckAttention(new AttentionCallback(iAttentionCallback));
        }
    };

    @Override
    public final IBinder onBind(Intent intent) {
        if (SERVICE_INTERFACE.equals(intent.getAction())) {
            return this.mBinder;
        }
        return null;
    }

    public abstract void onCancelAttentionCheck(AttentionCallback var1);

    public abstract void onCheckAttention(AttentionCallback var1);

    public static final class AttentionCallback {
        private final IAttentionCallback mCallback;

        private AttentionCallback(IAttentionCallback iAttentionCallback) {
            this.mCallback = iAttentionCallback;
        }

        public void onFailure(int n) {
            try {
                this.mCallback.onFailure(n);
            }
            catch (RemoteException remoteException) {
                remoteException.rethrowFromSystemServer();
            }
        }

        public void onSuccess(int n, long l) {
            try {
                this.mCallback.onSuccess(n, l);
            }
            catch (RemoteException remoteException) {
                remoteException.rethrowFromSystemServer();
            }
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface AttentionFailureCodes {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface AttentionSuccessCodes {
    }

}

