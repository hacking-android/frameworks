/*
 * Decompiled with CFR 0.145.
 */
package android.app.timezone;

import android.app.timezone.Callback;
import android.app.timezone.ICallback;
import android.app.timezone.IRulesManager;
import android.app.timezone.RulesState;
import android.app.timezone._$$Lambda$RulesManager$CallbackWrapper$t7a48uTTxaRuSo3YBKxBIbPQznY;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.os.ServiceManager;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;

public final class RulesManager {
    public static final String ACTION_RULES_UPDATE_OPERATION = "com.android.intent.action.timezone.RULES_UPDATE_OPERATION";
    private static final boolean DEBUG = false;
    public static final int ERROR_OPERATION_IN_PROGRESS = 1;
    public static final int ERROR_UNKNOWN_FAILURE = 2;
    public static final String EXTRA_OPERATION_STAGED = "staged";
    public static final int SUCCESS = 0;
    private static final String TAG = "timezone.RulesManager";
    private final Context mContext;
    private final IRulesManager mIRulesManager;

    public RulesManager(Context context) {
        this.mContext = context;
        this.mIRulesManager = IRulesManager.Stub.asInterface(ServiceManager.getService("timezone"));
    }

    static void logDebug(String string2) {
    }

    public RulesState getRulesState() {
        try {
            RulesManager.logDebug("mIRulesManager.getRulesState()");
            RulesState rulesState = this.mIRulesManager.getRulesState();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("mIRulesManager.getRulesState() returned ");
            stringBuilder.append(rulesState);
            RulesManager.logDebug(stringBuilder.toString());
            return rulesState;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public int requestInstall(ParcelFileDescriptor parcelFileDescriptor, byte[] arrby, Callback object) throws IOException {
        object = new CallbackWrapper(this.mContext, (Callback)object);
        try {
            RulesManager.logDebug("mIRulesManager.requestInstall()");
            int n = this.mIRulesManager.requestInstall(parcelFileDescriptor, arrby, (ICallback)object);
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void requestNothing(byte[] arrby, boolean bl) {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("mIRulesManager.requestNothing() with token=");
            stringBuilder.append(Arrays.toString(arrby));
            RulesManager.logDebug(stringBuilder.toString());
            this.mIRulesManager.requestNothing(arrby, bl);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public int requestUninstall(byte[] arrby, Callback object) {
        object = new CallbackWrapper(this.mContext, (Callback)object);
        try {
            RulesManager.logDebug("mIRulesManager.requestUninstall()");
            int n = this.mIRulesManager.requestUninstall(arrby, (ICallback)object);
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    private class CallbackWrapper
    extends ICallback.Stub {
        final Callback mCallback;
        final Handler mHandler;

        CallbackWrapper(Context context, Callback callback) {
            this.mCallback = callback;
            this.mHandler = new Handler(context.getMainLooper());
        }

        public /* synthetic */ void lambda$onFinished$0$RulesManager$CallbackWrapper(int n) {
            this.mCallback.onFinished(n);
        }

        @Override
        public void onFinished(int n) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("mCallback.onFinished(status), status=");
            stringBuilder.append(n);
            RulesManager.logDebug(stringBuilder.toString());
            this.mHandler.post(new _$$Lambda$RulesManager$CallbackWrapper$t7a48uTTxaRuSo3YBKxBIbPQznY(this, n));
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ResultCode {
    }

}

