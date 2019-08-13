/*
 * Decompiled with CFR 0.145.
 */
package android.os.image;

import android.annotation.SystemApi;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.BaseBundle;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcelable;
import android.os.ParcelableException;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.os.image._$$Lambda$DynamicSystemClient$DynSystemServiceConnection$Q_VWaYUew87mkpsE47b33p5XLa8;
import android.os.image._$$Lambda$DynamicSystemClient$j9BjPR3q6kOr_cwQrk0KAsVFWNQ;
import android.util.Slog;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.concurrent.Executor;

@SystemApi
public class DynamicSystemClient {
    public static final String ACTION_NOTIFY_IF_IN_USE = "android.os.image.action.NOTIFY_IF_IN_USE";
    public static final String ACTION_START_INSTALL = "android.os.image.action.START_INSTALL";
    public static final int CAUSE_ERROR_EXCEPTION = 6;
    public static final int CAUSE_ERROR_INVALID_URL = 4;
    public static final int CAUSE_ERROR_IO = 3;
    public static final int CAUSE_ERROR_IPC = 5;
    public static final int CAUSE_INSTALL_CANCELLED = 2;
    public static final int CAUSE_INSTALL_COMPLETED = 1;
    public static final int CAUSE_NOT_SPECIFIED = 0;
    private static final long DEFAULT_USERDATA_SIZE = 10737418240L;
    public static final String KEY_EXCEPTION_DETAIL = "KEY_EXCEPTION_DETAIL";
    public static final String KEY_INSTALLED_SIZE = "KEY_INSTALLED_SIZE";
    public static final String KEY_SYSTEM_SIZE = "KEY_SYSTEM_SIZE";
    public static final String KEY_USERDATA_SIZE = "KEY_USERDATA_SIZE";
    public static final int MSG_POST_STATUS = 3;
    public static final int MSG_REGISTER_LISTENER = 1;
    public static final int MSG_UNREGISTER_LISTENER = 2;
    public static final int STATUS_IN_PROGRESS = 2;
    public static final int STATUS_IN_USE = 4;
    public static final int STATUS_NOT_STARTED = 1;
    public static final int STATUS_READY = 3;
    public static final int STATUS_UNKNOWN = 0;
    private static final String TAG = "DynSystemClient";
    private boolean mBound;
    private final DynSystemServiceConnection mConnection;
    private final Context mContext;
    private Executor mExecutor;
    private OnStatusChangedListener mListener;
    private final Messenger mMessenger;
    private Messenger mService;

    @SystemApi
    public DynamicSystemClient(Context context) {
        this.mContext = context;
        this.mConnection = new DynSystemServiceConnection();
        this.mMessenger = new Messenger(new IncomingHandler(this));
    }

    private boolean featureFlagEnabled() {
        return SystemProperties.getBoolean("persist.sys.fflag.override.settings_dynamic_system", false);
    }

    private void handleMessage(Message object) {
        if (((Message)object).what == 3) {
            int n = ((Message)object).arg1;
            int n2 = ((Message)object).arg2;
            object = (Bundle)((Message)object).obj;
            long l = ((BaseBundle)object).getLong(KEY_INSTALLED_SIZE);
            object = (object = (ParcelableException)((Bundle)object).getSerializable(KEY_EXCEPTION_DETAIL)) == null ? null : ((Throwable)object).getCause();
            Executor executor = this.mExecutor;
            if (executor != null) {
                executor.execute(new _$$Lambda$DynamicSystemClient$j9BjPR3q6kOr_cwQrk0KAsVFWNQ(this, n, n2, l, (Throwable)object));
            } else {
                this.mListener.onStatusChanged(n, n2, l, (Throwable)object);
            }
        }
    }

    @SystemApi
    public void bind() {
        if (!this.featureFlagEnabled()) {
            Slog.w(TAG, "settings_dynamic_system not enabled; bind() aborted.");
            return;
        }
        Intent intent = new Intent();
        intent.setClassName("com.android.dynsystem", "com.android.dynsystem.DynamicSystemInstallationService");
        this.mContext.bindService(intent, this.mConnection, 1);
        this.mBound = true;
    }

    public /* synthetic */ void lambda$handleMessage$0$DynamicSystemClient(int n, int n2, long l, Throwable throwable) {
        this.mListener.onStatusChanged(n, n2, l, throwable);
    }

    public void setOnStatusChangedListener(OnStatusChangedListener onStatusChangedListener) {
        this.mListener = onStatusChangedListener;
        this.mExecutor = null;
    }

    public void setOnStatusChangedListener(Executor executor, OnStatusChangedListener onStatusChangedListener) {
        this.mListener = onStatusChangedListener;
        this.mExecutor = executor;
    }

    @SystemApi
    public void start(Uri uri, long l) {
        this.start(uri, l, 10737418240L);
    }

    public void start(Uri uri, long l, long l2) {
        if (!this.featureFlagEnabled()) {
            Slog.w(TAG, "settings_dynamic_system not enabled; start() aborted.");
            return;
        }
        Intent intent = new Intent();
        intent.setClassName("com.android.dynsystem", "com.android.dynsystem.VerificationActivity");
        intent.setData(uri);
        intent.setAction(ACTION_START_INSTALL);
        intent.putExtra(KEY_SYSTEM_SIZE, l);
        intent.putExtra(KEY_USERDATA_SIZE, l2);
        this.mContext.startActivity(intent);
    }

    @SystemApi
    public void unbind() {
        if (!this.mBound) {
            return;
        }
        if (this.mService != null) {
            try {
                Message message = Message.obtain(null, 2);
                message.replyTo = this.mMessenger;
                this.mService.send(message);
            }
            catch (RemoteException remoteException) {
                Slog.e(TAG, "Unable to unregister from installation service");
            }
        }
        this.mContext.unbindService(this.mConnection);
        this.mBound = false;
    }

    private class DynSystemServiceConnection
    implements ServiceConnection {
        private DynSystemServiceConnection() {
        }

        public /* synthetic */ void lambda$onServiceConnected$0$DynamicSystemClient$DynSystemServiceConnection(RemoteException remoteException) {
            DynamicSystemClient.this.mListener.onStatusChanged(0, 5, 0L, remoteException);
        }

        @Override
        public void onServiceConnected(ComponentName parcelable, IBinder iBinder) {
            Slog.v(DynamicSystemClient.TAG, "DynSystemService connected");
            DynamicSystemClient.this.mService = new Messenger(iBinder);
            try {
                parcelable = Message.obtain(null, 1);
                ((Message)parcelable).replyTo = DynamicSystemClient.this.mMessenger;
                DynamicSystemClient.this.mService.send((Message)parcelable);
            }
            catch (RemoteException remoteException) {
                Slog.e(DynamicSystemClient.TAG, "Unable to get status from installation service");
                DynamicSystemClient.this.mExecutor.execute(new _$$Lambda$DynamicSystemClient$DynSystemServiceConnection$Q_VWaYUew87mkpsE47b33p5XLa8(this, remoteException));
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Slog.v(DynamicSystemClient.TAG, "DynSystemService disconnected");
            DynamicSystemClient.this.mService = null;
        }
    }

    private static class IncomingHandler
    extends Handler {
        private final WeakReference<DynamicSystemClient> mWeakClient;

        IncomingHandler(DynamicSystemClient dynamicSystemClient) {
            super(Looper.getMainLooper());
            this.mWeakClient = new WeakReference<DynamicSystemClient>(dynamicSystemClient);
        }

        @Override
        public void handleMessage(Message message) {
            DynamicSystemClient dynamicSystemClient = (DynamicSystemClient)this.mWeakClient.get();
            if (dynamicSystemClient != null) {
                dynamicSystemClient.handleMessage(message);
            }
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface InstallationStatus {
    }

    public static interface OnStatusChangedListener {
        public void onStatusChanged(int var1, int var2, long var3, Throwable var5);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface StatusChangedCause {
    }

}

