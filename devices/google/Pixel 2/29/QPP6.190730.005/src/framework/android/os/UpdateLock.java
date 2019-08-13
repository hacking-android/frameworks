/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.annotation.UnsupportedAppUsage;
import android.os.Binder;
import android.os.IBinder;
import android.os.IUpdateLock;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;

public class UpdateLock {
    private static final boolean DEBUG = false;
    @UnsupportedAppUsage
    public static final String NOW_IS_CONVENIENT = "nowisconvenient";
    private static final String TAG = "UpdateLock";
    @UnsupportedAppUsage
    public static final String TIMESTAMP = "timestamp";
    @UnsupportedAppUsage
    public static final String UPDATE_LOCK_CHANGED = "android.os.UpdateLock.UPDATE_LOCK_CHANGED";
    private static IUpdateLock sService;
    int mCount = 0;
    boolean mHeld = false;
    boolean mRefCounted = true;
    final String mTag;
    IBinder mToken;

    public UpdateLock(String string2) {
        this.mTag = string2;
        this.mToken = new Binder();
    }

    private void acquireLocked() {
        block5 : {
            IUpdateLock iUpdateLock;
            block4 : {
                if (!this.mRefCounted) break block4;
                int n = this.mCount;
                this.mCount = n + 1;
                if (n != 0) break block5;
            }
            if ((iUpdateLock = sService) != null) {
                try {
                    iUpdateLock.acquireUpdateLock(this.mToken, this.mTag);
                }
                catch (RemoteException remoteException) {
                    Log.e(TAG, "Unable to contact service to acquire");
                }
            }
            this.mHeld = true;
        }
    }

    private static void checkService() {
        if (sService == null) {
            sService = IUpdateLock.Stub.asInterface(ServiceManager.getService("updatelock"));
        }
    }

    private void releaseLocked() {
        block6 : {
            IUpdateLock iUpdateLock;
            block5 : {
                int n;
                if (!this.mRefCounted) break block5;
                this.mCount = n = this.mCount - 1;
                if (n != 0) break block6;
            }
            if ((iUpdateLock = sService) != null) {
                try {
                    iUpdateLock.releaseUpdateLock(this.mToken);
                }
                catch (RemoteException remoteException) {
                    Log.e(TAG, "Unable to contact service to release");
                }
            }
            this.mHeld = false;
        }
        if (this.mCount >= 0) {
            return;
        }
        throw new RuntimeException("UpdateLock under-locked");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public void acquire() {
        UpdateLock.checkService();
        IBinder iBinder = this.mToken;
        synchronized (iBinder) {
            this.acquireLocked();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void finalize() throws Throwable {
        IBinder iBinder = this.mToken;
        synchronized (iBinder) {
            if (this.mHeld) {
                Log.wtf(TAG, "UpdateLock finalized while still held");
                try {
                    sService.releaseUpdateLock(this.mToken);
                }
                catch (RemoteException remoteException) {
                    Log.e(TAG, "Unable to contact service to release");
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
    @UnsupportedAppUsage
    public boolean isHeld() {
        IBinder iBinder = this.mToken;
        synchronized (iBinder) {
            return this.mHeld;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public void release() {
        UpdateLock.checkService();
        IBinder iBinder = this.mToken;
        synchronized (iBinder) {
            this.releaseLocked();
            return;
        }
    }

    public void setReferenceCounted(boolean bl) {
        this.mRefCounted = bl;
    }
}

