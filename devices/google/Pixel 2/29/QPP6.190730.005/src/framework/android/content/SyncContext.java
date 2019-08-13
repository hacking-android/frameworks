/*
 * Decompiled with CFR 0.145.
 */
package android.content;

import android.annotation.UnsupportedAppUsage;
import android.content.ISyncContext;
import android.content.SyncResult;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.SystemClock;

public class SyncContext {
    private static final long HEARTBEAT_SEND_INTERVAL_IN_MS = 1000L;
    private long mLastHeartbeatSendTime;
    private ISyncContext mSyncContext;

    @UnsupportedAppUsage
    public SyncContext(ISyncContext iSyncContext) {
        this.mSyncContext = iSyncContext;
        this.mLastHeartbeatSendTime = 0L;
    }

    private void updateHeartbeat() {
        long l = SystemClock.elapsedRealtime();
        if (l < this.mLastHeartbeatSendTime + 1000L) {
            return;
        }
        try {
            this.mLastHeartbeatSendTime = l;
            if (this.mSyncContext != null) {
                this.mSyncContext.sendHeartbeat();
            }
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public IBinder getSyncContextBinder() {
        Object object = this.mSyncContext;
        object = object == null ? null : object.asBinder();
        return object;
    }

    public void onFinished(SyncResult syncResult) {
        try {
            if (this.mSyncContext != null) {
                this.mSyncContext.onFinished(syncResult);
            }
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    @UnsupportedAppUsage
    public void setStatusText(String string2) {
        this.updateHeartbeat();
    }
}

