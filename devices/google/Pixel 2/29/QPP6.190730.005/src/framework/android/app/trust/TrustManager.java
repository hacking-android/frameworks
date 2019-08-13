/*
 * Decompiled with CFR 0.145.
 */
package android.app.trust;

import android.annotation.UnsupportedAppUsage;
import android.app.trust.ITrustListener;
import android.app.trust.ITrustManager;
import android.hardware.biometrics.BiometricSourceType;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.ArrayMap;

public class TrustManager {
    private static final String DATA_FLAGS = "initiatedByUser";
    private static final String DATA_MESSAGE = "message";
    private static final int MSG_TRUST_CHANGED = 1;
    private static final int MSG_TRUST_ERROR = 3;
    private static final int MSG_TRUST_MANAGED_CHANGED = 2;
    private static final String TAG = "TrustManager";
    private final Handler mHandler = new Handler(Looper.getMainLooper()){

        @Override
        public void handleMessage(Message message) {
            int n = message.what;
            boolean bl = false;
            boolean bl2 = false;
            if (n != 1) {
                if (n != 2) {
                    if (n == 3) {
                        CharSequence charSequence = message.peekData().getCharSequence(TrustManager.DATA_MESSAGE);
                        ((TrustListener)message.obj).onTrustError(charSequence);
                    }
                } else {
                    TrustListener trustListener = (TrustListener)message.obj;
                    if (message.arg1 != 0) {
                        bl2 = true;
                    }
                    trustListener.onTrustManagedChanged(bl2, message.arg2);
                }
            } else {
                n = message.peekData() != null ? message.peekData().getInt(TrustManager.DATA_FLAGS) : 0;
                TrustListener trustListener = (TrustListener)message.obj;
                bl2 = bl;
                if (message.arg1 != 0) {
                    bl2 = true;
                }
                trustListener.onTrustChanged(bl2, message.arg2, n);
            }
        }
    };
    private final ITrustManager mService;
    private final ArrayMap<TrustListener, ITrustListener> mTrustListeners;

    public TrustManager(IBinder iBinder) {
        this.mService = ITrustManager.Stub.asInterface(iBinder);
        this.mTrustListeners = new ArrayMap();
    }

    public void clearAllBiometricRecognized(BiometricSourceType biometricSourceType) {
        try {
            this.mService.clearAllBiometricRecognized(biometricSourceType);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean isTrustUsuallyManaged(int n) {
        try {
            boolean bl = this.mService.isTrustUsuallyManaged(n);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void registerTrustListener(final TrustListener trustListener) {
        try {
            ITrustListener.Stub stub = new ITrustListener.Stub(){

                @Override
                public void onTrustChanged(boolean bl, int n, int n2) {
                    Message message = TrustManager.this.mHandler.obtainMessage(1, (int)bl, n, trustListener);
                    if (n2 != 0) {
                        message.getData().putInt(TrustManager.DATA_FLAGS, n2);
                    }
                    message.sendToTarget();
                }

                @Override
                public void onTrustError(CharSequence charSequence) {
                    Message message = TrustManager.this.mHandler.obtainMessage(3);
                    message.getData().putCharSequence(TrustManager.DATA_MESSAGE, charSequence);
                    message.sendToTarget();
                }

                @Override
                public void onTrustManagedChanged(boolean bl, int n) {
                    TrustManager.this.mHandler.obtainMessage(2, (int)bl, n, trustListener).sendToTarget();
                }
            };
            this.mService.registerTrustListener(stub);
            this.mTrustListeners.put(trustListener, stub);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void reportEnabledTrustAgentsChanged(int n) {
        try {
            this.mService.reportEnabledTrustAgentsChanged(n);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void reportKeyguardShowingChanged() {
        try {
            this.mService.reportKeyguardShowingChanged();
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public void reportUnlockAttempt(boolean bl, int n) {
        try {
            this.mService.reportUnlockAttempt(bl, n);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void reportUnlockLockout(int n, int n2) {
        try {
            this.mService.reportUnlockLockout(n, n2);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void setDeviceLockedForUser(int n, boolean bl) {
        try {
            this.mService.setDeviceLockedForUser(n, bl);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void unlockedByBiometricForUser(int n, BiometricSourceType biometricSourceType) {
        try {
            this.mService.unlockedByBiometricForUser(n, biometricSourceType);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void unregisterTrustListener(TrustListener object) {
        if ((object = this.mTrustListeners.remove(object)) != null) {
            try {
                this.mService.unregisterTrustListener((ITrustListener)object);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    public static interface TrustListener {
        public void onTrustChanged(boolean var1, int var2, int var3);

        public void onTrustError(CharSequence var1);

        public void onTrustManagedChanged(boolean var1, int var2);
    }

}

