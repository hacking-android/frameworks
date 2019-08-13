/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.annotation.SystemApi;
import android.app.IInstantAppResolver;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.InstantAppResolveInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.IRemoteCallback;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Log;
import android.util.Slog;
import com.android.internal.os.SomeArgs;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SystemApi
public abstract class InstantAppResolverService
extends Service {
    private static final boolean DEBUG_INSTANT = Build.IS_DEBUGGABLE;
    public static final String EXTRA_RESOLVE_INFO = "android.app.extra.RESOLVE_INFO";
    public static final String EXTRA_SEQUENCE = "android.app.extra.SEQUENCE";
    private static final String TAG = "PackageManager";
    Handler mHandler;

    @Override
    public final void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        this.mHandler = new ServiceHandler(this.getLooper());
    }

    Looper getLooper() {
        return this.getBaseContext().getMainLooper();
    }

    @Override
    public final IBinder onBind(Intent intent) {
        return new IInstantAppResolver.Stub(){

            @Override
            public void getInstantAppIntentFilterList(Intent intent, int[] arrn, int n, String string2, IRemoteCallback iRemoteCallback) {
                Object object;
                if (DEBUG_INSTANT) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("[");
                    ((StringBuilder)object).append(string2);
                    ((StringBuilder)object).append("] Phase2 called; posting");
                    Slog.v(InstantAppResolverService.TAG, ((StringBuilder)object).toString());
                }
                object = SomeArgs.obtain();
                ((SomeArgs)object).arg1 = iRemoteCallback;
                ((SomeArgs)object).arg2 = arrn;
                ((SomeArgs)object).arg3 = n;
                ((SomeArgs)object).arg4 = string2;
                ((SomeArgs)object).arg5 = intent;
                InstantAppResolverService.this.mHandler.obtainMessage(2, object).sendToTarget();
            }

            @Override
            public void getInstantAppResolveInfoList(Intent intent, int[] arrn, int n, String string2, int n2, IRemoteCallback iRemoteCallback) {
                Object object;
                if (DEBUG_INSTANT) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("[");
                    ((StringBuilder)object).append(string2);
                    ((StringBuilder)object).append("] Phase1 called; posting");
                    Slog.v(InstantAppResolverService.TAG, ((StringBuilder)object).toString());
                }
                object = SomeArgs.obtain();
                ((SomeArgs)object).arg1 = iRemoteCallback;
                ((SomeArgs)object).arg2 = arrn;
                ((SomeArgs)object).arg3 = n;
                ((SomeArgs)object).arg4 = string2;
                ((SomeArgs)object).arg5 = intent;
                InstantAppResolverService.this.mHandler.obtainMessage(1, n2, 0, object).sendToTarget();
            }
        };
    }

    public void onGetInstantAppIntentFilter(Intent intent, int[] arrn, UserHandle userHandle, String string2, InstantAppResolutionCallback instantAppResolutionCallback) {
        this.onGetInstantAppIntentFilter(intent, arrn, string2, instantAppResolutionCallback);
    }

    @Deprecated
    public void onGetInstantAppIntentFilter(Intent intent, int[] arrn, String string2, InstantAppResolutionCallback instantAppResolutionCallback) {
        Log.e(TAG, "New onGetInstantAppIntentFilter is not overridden");
        if (intent.isWebIntent()) {
            this.onGetInstantAppIntentFilter(arrn, string2, instantAppResolutionCallback);
        } else {
            instantAppResolutionCallback.onInstantAppResolveInfo(Collections.<InstantAppResolveInfo>emptyList());
        }
    }

    @Deprecated
    public void onGetInstantAppIntentFilter(int[] arrn, String string2, InstantAppResolutionCallback instantAppResolutionCallback) {
        throw new IllegalStateException("Must define onGetInstantAppIntentFilter");
    }

    public void onGetInstantAppResolveInfo(Intent intent, int[] arrn, UserHandle userHandle, String string2, InstantAppResolutionCallback instantAppResolutionCallback) {
        this.onGetInstantAppResolveInfo(intent, arrn, string2, instantAppResolutionCallback);
    }

    @Deprecated
    public void onGetInstantAppResolveInfo(Intent intent, int[] arrn, String string2, InstantAppResolutionCallback instantAppResolutionCallback) {
        if (intent.isWebIntent()) {
            this.onGetInstantAppResolveInfo(arrn, string2, instantAppResolutionCallback);
        } else {
            instantAppResolutionCallback.onInstantAppResolveInfo(Collections.<InstantAppResolveInfo>emptyList());
        }
    }

    @Deprecated
    public void onGetInstantAppResolveInfo(int[] arrn, String string2, InstantAppResolutionCallback instantAppResolutionCallback) {
        throw new IllegalStateException("Must define onGetInstantAppResolveInfo");
    }

    public static final class InstantAppResolutionCallback {
        private final IRemoteCallback mCallback;
        private final int mSequence;

        InstantAppResolutionCallback(int n, IRemoteCallback iRemoteCallback) {
            this.mCallback = iRemoteCallback;
            this.mSequence = n;
        }

        public void onInstantAppResolveInfo(List<InstantAppResolveInfo> list) {
            Bundle bundle = new Bundle();
            bundle.putParcelableList(InstantAppResolverService.EXTRA_RESOLVE_INFO, list);
            bundle.putInt(InstantAppResolverService.EXTRA_SEQUENCE, this.mSequence);
            try {
                this.mCallback.sendResult(bundle);
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
    }

    private final class ServiceHandler
    extends Handler {
        public static final int MSG_GET_INSTANT_APP_INTENT_FILTER = 2;
        public static final int MSG_GET_INSTANT_APP_RESOLVE_INFO = 1;

        public ServiceHandler(Looper looper) {
            super(looper, null, true);
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void handleMessage(Message object) {
            int n = ((Message)object).what;
            if (n != 1) {
                if (n != 2) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Unknown message: ");
                    ((StringBuilder)object).append(n);
                    throw new IllegalArgumentException(((StringBuilder)object).toString());
                }
                Object object2 = (SomeArgs)((Message)object).obj;
                IRemoteCallback iRemoteCallback = (IRemoteCallback)((SomeArgs)object2).arg1;
                object = (int[])((SomeArgs)object2).arg2;
                n = (Integer)((SomeArgs)object2).arg3;
                String string2 = (String)((SomeArgs)object2).arg4;
                Intent intent = (Intent)((SomeArgs)object2).arg5;
                if (DEBUG_INSTANT) {
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("[");
                    ((StringBuilder)object2).append(string2);
                    ((StringBuilder)object2).append("] Phase2 request; prefix: ");
                    ((StringBuilder)object2).append(Arrays.toString((int[])object));
                    ((StringBuilder)object2).append(", userId: ");
                    ((StringBuilder)object2).append(n);
                    Slog.d(InstantAppResolverService.TAG, ((StringBuilder)object2).toString());
                }
                InstantAppResolverService.this.onGetInstantAppIntentFilter(intent, (int[])object, UserHandle.of(n), string2, new InstantAppResolutionCallback(-1, iRemoteCallback));
                return;
            }
            Object object3 = (SomeArgs)((Message)object).obj;
            IRemoteCallback iRemoteCallback = (IRemoteCallback)((SomeArgs)object3).arg1;
            int[] arrn = (int[])((SomeArgs)object3).arg2;
            n = (Integer)((SomeArgs)object3).arg3;
            String string3 = (String)((SomeArgs)object3).arg4;
            object3 = (Intent)((SomeArgs)object3).arg5;
            int n2 = ((Message)object).arg1;
            if (DEBUG_INSTANT) {
                object = new StringBuilder();
                ((StringBuilder)object).append("[");
                ((StringBuilder)object).append(string3);
                ((StringBuilder)object).append("] Phase1 request; prefix: ");
                ((StringBuilder)object).append(Arrays.toString(arrn));
                ((StringBuilder)object).append(", userId: ");
                ((StringBuilder)object).append(n);
                Slog.d(InstantAppResolverService.TAG, ((StringBuilder)object).toString());
            }
            InstantAppResolverService.this.onGetInstantAppResolveInfo((Intent)object3, arrn, UserHandle.of(n), string3, new InstantAppResolutionCallback(n2, iRemoteCallback));
        }
    }

}

