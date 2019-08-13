/*
 * Decompiled with CFR 0.145.
 */
package android.service.trust;

import android.annotation.SystemApi;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.os.BaseBundle;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.os.RemoteException;
import android.os.UserHandle;
import android.os.UserManager;
import android.service.trust.ITrustAgentService;
import android.service.trust.ITrustAgentServiceCallback;
import android.util.Log;
import android.util.Slog;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

@SystemApi
public class TrustAgentService
extends Service {
    private static final boolean DEBUG = false;
    private static final String EXTRA_TOKEN = "token";
    private static final String EXTRA_TOKEN_HANDLE = "token_handle";
    private static final String EXTRA_TOKEN_REMOVED_RESULT = "token_removed_result";
    private static final String EXTRA_TOKEN_STATE = "token_state";
    private static final String EXTRA_USER_HANDLE = "user_handle";
    public static final int FLAG_GRANT_TRUST_DISMISS_KEYGUARD = 2;
    public static final int FLAG_GRANT_TRUST_INITIATED_BY_USER = 1;
    private static final int MSG_CONFIGURE = 2;
    private static final int MSG_DEVICE_LOCKED = 4;
    private static final int MSG_DEVICE_UNLOCKED = 5;
    private static final int MSG_ESCROW_TOKEN_ADDED = 7;
    private static final int MSG_ESCROW_TOKEN_REMOVED = 9;
    private static final int MSG_ESCROW_TOKEN_STATE_RECEIVED = 8;
    private static final int MSG_TRUST_TIMEOUT = 3;
    private static final int MSG_UNLOCK_ATTEMPT = 1;
    private static final int MSG_UNLOCK_LOCKOUT = 6;
    public static final String SERVICE_INTERFACE = "android.service.trust.TrustAgentService";
    public static final int TOKEN_STATE_ACTIVE = 1;
    public static final int TOKEN_STATE_INACTIVE = 0;
    public static final String TRUST_AGENT_META_DATA = "android.service.trust.trustagent";
    private final String TAG;
    private ITrustAgentServiceCallback mCallback;
    private Handler mHandler;
    private final Object mLock;
    private boolean mManagingTrust;
    private Runnable mPendingGrantTrustTask;

    public TrustAgentService() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(TrustAgentService.class.getSimpleName());
        stringBuilder.append("[");
        stringBuilder.append(this.getClass().getSimpleName());
        stringBuilder.append("]");
        this.TAG = stringBuilder.toString();
        this.mLock = new Object();
        this.mHandler = new Handler(){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Converted monitor instructions to comments
             * Lifted jumps to return sites
             */
            @Override
            public void handleMessage(Message object) {
                int n = ((Message)object).what;
                boolean bl = false;
                switch (n) {
                    default: {
                        return;
                    }
                    case 9: {
                        object = ((Message)object).getData();
                        long l = ((BaseBundle)object).getLong(TrustAgentService.EXTRA_TOKEN_HANDLE);
                        bl = ((BaseBundle)object).getBoolean(TrustAgentService.EXTRA_TOKEN_REMOVED_RESULT);
                        TrustAgentService.this.onEscrowTokenRemoved(l, bl);
                        return;
                    }
                    case 8: {
                        object = ((Message)object).getData();
                        long l = ((BaseBundle)object).getLong(TrustAgentService.EXTRA_TOKEN_HANDLE);
                        n = ((BaseBundle)object).getInt(TrustAgentService.EXTRA_TOKEN_STATE, 0);
                        TrustAgentService.this.onEscrowTokenStateReceived(l, n);
                        return;
                    }
                    case 7: {
                        Parcelable parcelable = ((Message)object).getData();
                        object = parcelable.getByteArray(TrustAgentService.EXTRA_TOKEN);
                        long l = parcelable.getLong(TrustAgentService.EXTRA_TOKEN_HANDLE);
                        parcelable = (UserHandle)parcelable.getParcelable(TrustAgentService.EXTRA_USER_HANDLE);
                        TrustAgentService.this.onEscrowTokenAdded((byte[])object, l, (UserHandle)parcelable);
                        return;
                    }
                    case 6: {
                        TrustAgentService.this.onDeviceUnlockLockout(((Message)object).arg1);
                        return;
                    }
                    case 5: {
                        TrustAgentService.this.onDeviceUnlocked();
                        return;
                    }
                    case 4: {
                        TrustAgentService.this.onDeviceLocked();
                        return;
                    }
                    case 3: {
                        TrustAgentService.this.onTrustTimeout();
                        return;
                    }
                    case 2: {
                        ConfigurationData configurationData = (ConfigurationData)((Message)object).obj;
                        bl = TrustAgentService.this.onConfigure(configurationData.options);
                        if (configurationData.token == null) return;
                        try {
                            object = TrustAgentService.this.mLock;
                            // MONITORENTER : object
                        }
                        catch (RemoteException remoteException) {
                            TrustAgentService.this.onError("calling onSetTrustAgentFeaturesEnabledCompleted()");
                            return;
                        }
                        TrustAgentService.this.mCallback.onConfigureCompleted(bl, configurationData.token);
                        // MONITOREXIT : object
                        return;
                    }
                    case 1: 
                }
                TrustAgentService trustAgentService = TrustAgentService.this;
                if (((Message)object).arg1 != 0) {
                    bl = true;
                }
                trustAgentService.onUnlockAttempt(bl);
            }
        };
    }

    private void onError(String string2) {
        String string3 = this.TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Remote exception while ");
        stringBuilder.append(string2);
        Slog.v(string3, stringBuilder.toString());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void addEscrowToken(byte[] object, UserHandle userHandle) {
        Object object2 = this.mLock;
        synchronized (object2) {
            ITrustAgentServiceCallback iTrustAgentServiceCallback = this.mCallback;
            if (iTrustAgentServiceCallback == null) {
                Slog.w(this.TAG, "Cannot add escrow token if the agent is not connecting to framework");
                object = new IllegalStateException("Trust agent is not connected");
                throw object;
            }
            try {
                this.mCallback.addEscrowToken((byte[])object, userHandle.getIdentifier());
            }
            catch (RemoteException remoteException) {
                this.onError("calling addEscrowToken");
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void grantTrust(CharSequence object, long l, int n) {
        Object object2 = this.mLock;
        synchronized (object2) {
            if (!this.mManagingTrust) {
                object = new IllegalStateException("Cannot grant trust if agent is not managing trust. Call setManagingTrust(true) first.");
                throw object;
            }
            Object object3 = this.mCallback;
            if (object3 != null) {
                try {
                    this.mCallback.grantTrust(object.toString(), l, n);
                }
                catch (RemoteException remoteException) {
                    this.onError("calling enableTrust()");
                }
            } else {
                object3 = new Runnable((CharSequence)object, l, n){
                    final /* synthetic */ long val$durationMs;
                    final /* synthetic */ int val$flags;
                    final /* synthetic */ CharSequence val$message;
                    {
                        this.val$message = charSequence;
                        this.val$durationMs = l;
                        this.val$flags = n;
                    }

                    @Override
                    public void run() {
                        TrustAgentService.this.grantTrust(this.val$message, this.val$durationMs, this.val$flags);
                    }
                };
                this.mPendingGrantTrustTask = object3;
            }
            return;
        }
    }

    @Deprecated
    public final void grantTrust(CharSequence charSequence, long l, boolean bl) {
        this.grantTrust(charSequence, l, (int)bl);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void isEscrowTokenActive(long l, UserHandle object) {
        Object object2 = this.mLock;
        synchronized (object2) {
            ITrustAgentServiceCallback iTrustAgentServiceCallback = this.mCallback;
            if (iTrustAgentServiceCallback == null) {
                Slog.w(this.TAG, "Cannot add escrow token if the agent is not connecting to framework");
                object = new IllegalStateException("Trust agent is not connected");
                throw object;
            }
            try {
                this.mCallback.isEscrowTokenActive(l, ((UserHandle)object).getIdentifier());
            }
            catch (RemoteException remoteException) {
                this.onError("calling isEscrowTokenActive");
            }
            return;
        }
    }

    @Override
    public final IBinder onBind(Intent intent) {
        return new TrustAgentServiceWrapper();
    }

    public boolean onConfigure(List<PersistableBundle> list) {
        return false;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ComponentName componentName = new ComponentName((Context)this, this.getClass());
        try {
            if (!"android.permission.BIND_TRUST_AGENT".equals(this.getPackageManager().getServiceInfo((ComponentName)componentName, (int)0).permission)) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(componentName.flattenToShortString());
                stringBuilder.append(" is not declared with the permission \"");
                stringBuilder.append("android.permission.BIND_TRUST_AGENT");
                stringBuilder.append("\"");
                IllegalStateException illegalStateException = new IllegalStateException(stringBuilder.toString());
                throw illegalStateException;
            }
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            String string2 = this.TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Can't get ServiceInfo for ");
            stringBuilder.append(componentName.toShortString());
            Log.e(string2, stringBuilder.toString());
        }
    }

    public void onDeviceLocked() {
    }

    public void onDeviceUnlockLockout(long l) {
    }

    public void onDeviceUnlocked() {
    }

    public void onEscrowTokenAdded(byte[] arrby, long l, UserHandle userHandle) {
    }

    public void onEscrowTokenRemoved(long l, boolean bl) {
    }

    public void onEscrowTokenStateReceived(long l, int n) {
    }

    public void onTrustTimeout() {
    }

    public void onUnlockAttempt(boolean bl) {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void removeEscrowToken(long l, UserHandle object) {
        Object object2 = this.mLock;
        synchronized (object2) {
            ITrustAgentServiceCallback iTrustAgentServiceCallback = this.mCallback;
            if (iTrustAgentServiceCallback == null) {
                Slog.w(this.TAG, "Cannot add escrow token if the agent is not connecting to framework");
                object = new IllegalStateException("Trust agent is not connected");
                throw object;
            }
            try {
                this.mCallback.removeEscrowToken(l, ((UserHandle)object).getIdentifier());
            }
            catch (RemoteException remoteException) {
                this.onError("callling removeEscrowToken");
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void revokeTrust() {
        Object object = this.mLock;
        synchronized (object) {
            ITrustAgentServiceCallback iTrustAgentServiceCallback;
            if (this.mPendingGrantTrustTask != null) {
                this.mPendingGrantTrustTask = null;
            }
            if ((iTrustAgentServiceCallback = this.mCallback) != null) {
                try {
                    this.mCallback.revokeTrust();
                }
                catch (RemoteException remoteException) {
                    this.onError("calling revokeTrust()");
                }
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void setManagingTrust(boolean bl) {
        Object object = this.mLock;
        synchronized (object) {
            if (this.mManagingTrust != bl) {
                this.mManagingTrust = bl;
                ITrustAgentServiceCallback iTrustAgentServiceCallback = this.mCallback;
                if (iTrustAgentServiceCallback != null) {
                    try {
                        this.mCallback.setManagingTrust(bl);
                    }
                    catch (RemoteException remoteException) {
                        this.onError("calling setManagingTrust()");
                    }
                }
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void showKeyguardErrorMessage(CharSequence object) {
        if (object == null) {
            throw new IllegalArgumentException("message cannot be null");
        }
        Object object2 = this.mLock;
        synchronized (object2) {
            ITrustAgentServiceCallback iTrustAgentServiceCallback = this.mCallback;
            if (iTrustAgentServiceCallback == null) {
                Slog.w(this.TAG, "Cannot show message because service is not connected to framework.");
                object = new IllegalStateException("Trust agent is not connected");
                throw object;
            }
            try {
                this.mCallback.showKeyguardErrorMessage((CharSequence)object);
            }
            catch (RemoteException remoteException) {
                this.onError("calling showKeyguardErrorMessage");
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void unlockUserWithToken(long l, byte[] object, UserHandle userHandle) {
        if (((UserManager)this.getSystemService("user")).isUserUnlocked(userHandle)) {
            Slog.i(this.TAG, "User already unlocked");
            return;
        }
        Object object2 = this.mLock;
        synchronized (object2) {
            ITrustAgentServiceCallback iTrustAgentServiceCallback = this.mCallback;
            if (iTrustAgentServiceCallback == null) {
                Slog.w(this.TAG, "Cannot add escrow token if the agent is not connecting to framework");
                object = new IllegalStateException("Trust agent is not connected");
                throw object;
            }
            try {
                this.mCallback.unlockUserWithToken(l, (byte[])object, userHandle.getIdentifier());
            }
            catch (RemoteException remoteException) {
                this.onError("calling unlockUserWithToken");
            }
            return;
        }
    }

    private static final class ConfigurationData {
        final List<PersistableBundle> options;
        final IBinder token;

        ConfigurationData(List<PersistableBundle> list, IBinder iBinder) {
            this.options = list;
            this.token = iBinder;
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface GrantTrustFlags {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface TokenState {
    }

    private final class TrustAgentServiceWrapper
    extends ITrustAgentService.Stub {
        private TrustAgentServiceWrapper() {
        }

        @Override
        public void onConfigure(List<PersistableBundle> list, IBinder iBinder) {
            TrustAgentService.this.mHandler.obtainMessage(2, new ConfigurationData(list, iBinder)).sendToTarget();
        }

        @Override
        public void onDeviceLocked() throws RemoteException {
            TrustAgentService.this.mHandler.obtainMessage(4).sendToTarget();
        }

        @Override
        public void onDeviceUnlocked() throws RemoteException {
            TrustAgentService.this.mHandler.obtainMessage(5).sendToTarget();
        }

        @Override
        public void onEscrowTokenAdded(byte[] arrby, long l, UserHandle userHandle) {
            Message message = TrustAgentService.this.mHandler.obtainMessage(7);
            message.getData().putByteArray(TrustAgentService.EXTRA_TOKEN, arrby);
            message.getData().putLong(TrustAgentService.EXTRA_TOKEN_HANDLE, l);
            message.getData().putParcelable(TrustAgentService.EXTRA_USER_HANDLE, userHandle);
            message.sendToTarget();
        }

        @Override
        public void onEscrowTokenRemoved(long l, boolean bl) {
            Message message = TrustAgentService.this.mHandler.obtainMessage(9);
            message.getData().putLong(TrustAgentService.EXTRA_TOKEN_HANDLE, l);
            message.getData().putBoolean(TrustAgentService.EXTRA_TOKEN_REMOVED_RESULT, bl);
            message.sendToTarget();
        }

        @Override
        public void onTokenStateReceived(long l, int n) {
            Message message = TrustAgentService.this.mHandler.obtainMessage(8);
            message.getData().putLong(TrustAgentService.EXTRA_TOKEN_HANDLE, l);
            message.getData().putInt(TrustAgentService.EXTRA_TOKEN_STATE, n);
            message.sendToTarget();
        }

        @Override
        public void onTrustTimeout() {
            TrustAgentService.this.mHandler.sendEmptyMessage(3);
        }

        @Override
        public void onUnlockAttempt(boolean bl) {
            TrustAgentService.this.mHandler.obtainMessage(1, (int)bl, 0).sendToTarget();
        }

        @Override
        public void onUnlockLockout(int n) {
            TrustAgentService.this.mHandler.obtainMessage(6, n, 0).sendToTarget();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void setCallback(ITrustAgentServiceCallback iTrustAgentServiceCallback) {
            Object object = TrustAgentService.this.mLock;
            synchronized (object) {
                TrustAgentService.this.mCallback = iTrustAgentServiceCallback;
                boolean bl = TrustAgentService.this.mManagingTrust;
                if (bl) {
                    try {
                        TrustAgentService.this.mCallback.setManagingTrust(TrustAgentService.this.mManagingTrust);
                    }
                    catch (RemoteException remoteException) {
                        TrustAgentService.this.onError("calling setManagingTrust()");
                    }
                }
                if (TrustAgentService.this.mPendingGrantTrustTask != null) {
                    TrustAgentService.this.mPendingGrantTrustTask.run();
                    TrustAgentService.this.mPendingGrantTrustTask = null;
                }
                return;
            }
        }
    }

}

