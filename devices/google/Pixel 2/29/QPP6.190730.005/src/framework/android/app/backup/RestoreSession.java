/*
 * Decompiled with CFR 0.145.
 */
package android.app.backup;

import android.annotation.SystemApi;
import android.app.backup.BackupManagerMonitor;
import android.app.backup.IBackupManagerMonitor;
import android.app.backup.IRestoreObserver;
import android.app.backup.IRestoreSession;
import android.app.backup.RestoreObserver;
import android.app.backup.RestoreSet;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@SystemApi
public class RestoreSession {
    static final String TAG = "RestoreSession";
    IRestoreSession mBinder;
    final Context mContext;
    RestoreObserverWrapper mObserver = null;

    RestoreSession(Context context, IRestoreSession iRestoreSession) {
        this.mContext = context;
        this.mBinder = iRestoreSession;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void endRestoreSession() {
        this.mBinder.endRestoreSession();
        this.mBinder = null;
        return;
        {
            catch (RemoteException var1_2) {}
            {
                Log.d("RestoreSession", "Can't contact server to get available sets");
            }
        }
        ** finally { 
lbl9: // 1 sources:
        this.mBinder = null;
        throw var1_1;
    }

    public int getAvailableRestoreSets(RestoreObserver restoreObserver) {
        return this.getAvailableRestoreSets(restoreObserver, null);
    }

    public int getAvailableRestoreSets(RestoreObserver object, BackupManagerMonitor backupManagerMonitor) {
        int n = -1;
        RestoreObserverWrapper restoreObserverWrapper = new RestoreObserverWrapper(this.mContext, (RestoreObserver)object);
        object = backupManagerMonitor == null ? null : new BackupManagerMonitorWrapper(backupManagerMonitor);
        try {
            int n2;
            n = n2 = this.mBinder.getAvailableRestoreSets(restoreObserverWrapper, (IBackupManagerMonitor)object);
        }
        catch (RemoteException remoteException) {
            Log.d(TAG, "Can't contact server to get available sets");
        }
        return n;
    }

    public int restoreAll(long l, RestoreObserver restoreObserver) {
        return this.restoreAll(l, restoreObserver, null);
    }

    public int restoreAll(long l, RestoreObserver object, BackupManagerMonitor backupManagerMonitor) {
        int n = -1;
        if (this.mObserver != null) {
            Log.d(TAG, "restoreAll() called during active restore");
            return -1;
        }
        this.mObserver = new RestoreObserverWrapper(this.mContext, (RestoreObserver)object);
        object = backupManagerMonitor == null ? null : new BackupManagerMonitorWrapper(backupManagerMonitor);
        try {
            int n2;
            n = n2 = this.mBinder.restoreAll(l, this.mObserver, (IBackupManagerMonitor)object);
        }
        catch (RemoteException remoteException) {
            Log.d(TAG, "Can't contact server to restore");
        }
        return n;
    }

    public int restorePackage(String string2, RestoreObserver restoreObserver) {
        return this.restorePackage(string2, restoreObserver, null);
    }

    public int restorePackage(String string2, RestoreObserver object, BackupManagerMonitor backupManagerMonitor) {
        int n;
        int n2 = -1;
        if (this.mObserver != null) {
            Log.d(TAG, "restorePackage() called during active restore");
            return -1;
        }
        this.mObserver = new RestoreObserverWrapper(this.mContext, (RestoreObserver)object);
        object = backupManagerMonitor == null ? null : new BackupManagerMonitorWrapper(backupManagerMonitor);
        try {
            n = this.mBinder.restorePackage(string2, this.mObserver, (IBackupManagerMonitor)object);
        }
        catch (RemoteException remoteException) {
            Log.d(TAG, "Can't contact server to restore package");
            n = n2;
        }
        return n;
    }

    public int restorePackages(long l, RestoreObserver restoreObserver, Set<String> set) {
        return this.restorePackages(l, restoreObserver, set, null);
    }

    public int restorePackages(long l, RestoreObserver object, Set<String> set, BackupManagerMonitor backupManagerMonitor) {
        int n;
        int n2 = -1;
        if (this.mObserver != null) {
            Log.d(TAG, "restoreAll() called during active restore");
            return -1;
        }
        this.mObserver = new RestoreObserverWrapper(this.mContext, (RestoreObserver)object);
        object = backupManagerMonitor == null ? null : new BackupManagerMonitorWrapper(backupManagerMonitor);
        try {
            n = this.mBinder.restorePackages(l, this.mObserver, set.toArray(new String[0]), (IBackupManagerMonitor)object);
        }
        catch (RemoteException remoteException) {
            Log.d(TAG, "Can't contact server to restore packages");
            n = n2;
        }
        return n;
    }

    @Deprecated
    public int restoreSome(long l, RestoreObserver restoreObserver, BackupManagerMonitor backupManagerMonitor, String[] arrstring) {
        return this.restorePackages(l, restoreObserver, new HashSet<String>(Arrays.asList(arrstring)), backupManagerMonitor);
    }

    @Deprecated
    public int restoreSome(long l, RestoreObserver restoreObserver, String[] arrstring) {
        return this.restoreSome(l, restoreObserver, null, arrstring);
    }

    private class BackupManagerMonitorWrapper
    extends IBackupManagerMonitor.Stub {
        final BackupManagerMonitor mMonitor;

        BackupManagerMonitorWrapper(BackupManagerMonitor backupManagerMonitor) {
            this.mMonitor = backupManagerMonitor;
        }

        @Override
        public void onEvent(Bundle bundle) throws RemoteException {
            this.mMonitor.onEvent(bundle);
        }
    }

    private class RestoreObserverWrapper
    extends IRestoreObserver.Stub {
        static final int MSG_RESTORE_FINISHED = 3;
        static final int MSG_RESTORE_SETS_AVAILABLE = 4;
        static final int MSG_RESTORE_STARTING = 1;
        static final int MSG_UPDATE = 2;
        final RestoreObserver mAppObserver;
        final Handler mHandler;

        RestoreObserverWrapper(Context context, RestoreObserver restoreObserver) {
            this.mHandler = new Handler(context.getMainLooper()){

                @Override
                public void handleMessage(Message message) {
                    int n = message.what;
                    if (n != 1) {
                        if (n != 2) {
                            if (n != 3) {
                                if (n == 4) {
                                    RestoreObserverWrapper.this.mAppObserver.restoreSetsAvailable((RestoreSet[])message.obj);
                                }
                            } else {
                                RestoreObserverWrapper.this.mAppObserver.restoreFinished(message.arg1);
                            }
                        } else {
                            RestoreObserverWrapper.this.mAppObserver.onUpdate(message.arg1, (String)message.obj);
                        }
                    } else {
                        RestoreObserverWrapper.this.mAppObserver.restoreStarting(message.arg1);
                    }
                }
            };
            this.mAppObserver = restoreObserver;
        }

        @Override
        public void onUpdate(int n, String string2) {
            Handler handler = this.mHandler;
            handler.sendMessage(handler.obtainMessage(2, n, 0, string2));
        }

        @Override
        public void restoreFinished(int n) {
            Handler handler = this.mHandler;
            handler.sendMessage(handler.obtainMessage(3, n, 0));
        }

        @Override
        public void restoreSetsAvailable(RestoreSet[] arrrestoreSet) {
            Handler handler = this.mHandler;
            handler.sendMessage(handler.obtainMessage(4, arrrestoreSet));
        }

        @Override
        public void restoreStarting(int n) {
            Handler handler = this.mHandler;
            handler.sendMessage(handler.obtainMessage(1, n, 0));
        }

    }

}

