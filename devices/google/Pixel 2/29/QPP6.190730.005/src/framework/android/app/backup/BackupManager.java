/*
 * Decompiled with CFR 0.145.
 */
package android.app.backup;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.app.backup.BackupManagerMonitor;
import android.app.backup.BackupObserver;
import android.app.backup.BackupProgress;
import android.app.backup.IBackupManager;
import android.app.backup.IBackupManagerMonitor;
import android.app.backup.IBackupObserver;
import android.app.backup.IRestoreSession;
import android.app.backup.ISelectBackupTransportCallback;
import android.app.backup.RestoreObserver;
import android.app.backup.RestoreSession;
import android.app.backup.SelectBackupTransportCallback;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IInterface;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.util.Log;
import android.util.Pair;

public class BackupManager {
    @SystemApi
    public static final int ERROR_AGENT_FAILURE = -1003;
    @SystemApi
    public static final int ERROR_BACKUP_CANCELLED = -2003;
    @SystemApi
    public static final int ERROR_BACKUP_NOT_ALLOWED = -2001;
    @SystemApi
    public static final int ERROR_PACKAGE_NOT_FOUND = -2002;
    @SystemApi
    public static final int ERROR_TRANSPORT_ABORTED = -1000;
    @SystemApi
    public static final int ERROR_TRANSPORT_INVALID = -2;
    @SystemApi
    public static final int ERROR_TRANSPORT_PACKAGE_REJECTED = -1002;
    @SystemApi
    public static final int ERROR_TRANSPORT_QUOTA_EXCEEDED = -1005;
    @SystemApi
    public static final int ERROR_TRANSPORT_UNAVAILABLE = -1;
    public static final String EXTRA_BACKUP_SERVICES_AVAILABLE = "backup_services_available";
    @SystemApi
    public static final int FLAG_NON_INCREMENTAL_BACKUP = 1;
    @SystemApi
    public static final String PACKAGE_MANAGER_SENTINEL = "@pm@";
    @SystemApi
    public static final int SUCCESS = 0;
    private static final String TAG = "BackupManager";
    @UnsupportedAppUsage
    private static IBackupManager sService;
    private Context mContext;

    public BackupManager(Context context) {
        this.mContext = context;
    }

    @UnsupportedAppUsage
    private static void checkServiceBinder() {
        if (sService == null) {
            sService = IBackupManager.Stub.asInterface(ServiceManager.getService("backup"));
        }
    }

    public static void dataChanged(String string2) {
        BackupManager.checkServiceBinder();
        IBackupManager iBackupManager = sService;
        if (iBackupManager != null) {
            try {
                iBackupManager.dataChanged(string2);
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "dataChanged(pkg) couldn't connect");
            }
        }
    }

    @SystemApi
    public void backupNow() {
        BackupManager.checkServiceBinder();
        IBackupManager iBackupManager = sService;
        if (iBackupManager != null) {
            try {
                iBackupManager.backupNow();
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "backupNow() couldn't connect");
            }
        }
    }

    @SystemApi
    public RestoreSession beginRestoreSession() {
        RestoreSession restoreSession;
        block4 : {
            RestoreSession restoreSession2 = null;
            RestoreSession restoreSession3 = null;
            BackupManager.checkServiceBinder();
            IInterface iInterface = sService;
            restoreSession = restoreSession2;
            if (iInterface != null) {
                iInterface = iInterface.beginRestoreSessionForUser(this.mContext.getUserId(), null, null);
                restoreSession = restoreSession3;
                if (iInterface == null) break block4;
                try {
                    restoreSession = new RestoreSession(this.mContext, (IRestoreSession)iInterface);
                }
                catch (RemoteException remoteException) {
                    Log.e(TAG, "beginRestoreSession() couldn't connect");
                    restoreSession = restoreSession2;
                }
            }
        }
        return restoreSession;
    }

    @SystemApi
    public void cancelBackups() {
        BackupManager.checkServiceBinder();
        IBackupManager iBackupManager = sService;
        if (iBackupManager != null) {
            try {
                iBackupManager.cancelBackups();
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "cancelBackups() couldn't connect.");
            }
        }
    }

    public void dataChanged() {
        BackupManager.checkServiceBinder();
        IBackupManager iBackupManager = sService;
        if (iBackupManager != null) {
            try {
                iBackupManager.dataChanged(this.mContext.getPackageName());
            }
            catch (RemoteException remoteException) {
                Log.d(TAG, "dataChanged() couldn't connect");
            }
        }
    }

    @SystemApi
    public long getAvailableRestoreToken(String string2) {
        BackupManager.checkServiceBinder();
        IBackupManager iBackupManager = sService;
        if (iBackupManager != null) {
            try {
                long l = iBackupManager.getAvailableRestoreTokenForUser(this.mContext.getUserId(), string2);
                return l;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "getAvailableRestoreToken() couldn't connect");
            }
        }
        return 0L;
    }

    @SystemApi
    public Intent getConfigurationIntent(String object) {
        BackupManager.checkServiceBinder();
        IBackupManager iBackupManager = sService;
        if (iBackupManager != null) {
            try {
                object = iBackupManager.getConfigurationIntentForUser(this.mContext.getUserId(), (String)object);
                return object;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "getConfigurationIntent() couldn't connect");
            }
        }
        return null;
    }

    @SystemApi
    public String getCurrentTransport() {
        BackupManager.checkServiceBinder();
        Object object = sService;
        if (object != null) {
            try {
                object = object.getCurrentTransport();
                return object;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "getCurrentTransport() couldn't connect");
            }
        }
        return null;
    }

    @SystemApi
    public ComponentName getCurrentTransportComponent() {
        BackupManager.checkServiceBinder();
        Object object = sService;
        if (object != null) {
            try {
                object = object.getCurrentTransportComponentForUser(this.mContext.getUserId());
                return object;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "getCurrentTransportComponent() couldn't connect");
            }
        }
        return null;
    }

    @SystemApi
    public Intent getDataManagementIntent(String object) {
        BackupManager.checkServiceBinder();
        IBackupManager iBackupManager = sService;
        if (iBackupManager != null) {
            try {
                object = iBackupManager.getDataManagementIntentForUser(this.mContext.getUserId(), (String)object);
                return object;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "getDataManagementIntent() couldn't connect");
            }
        }
        return null;
    }

    @SystemApi
    public CharSequence getDataManagementIntentLabel(String charSequence) {
        BackupManager.checkServiceBinder();
        IBackupManager iBackupManager = sService;
        if (iBackupManager != null) {
            try {
                charSequence = iBackupManager.getDataManagementLabelForUser(this.mContext.getUserId(), (String)charSequence);
                return charSequence;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "getDataManagementIntentLabel() couldn't connect");
            }
        }
        return null;
    }

    @SystemApi
    @Deprecated
    public String getDataManagementLabel(String charSequence) {
        charSequence = (charSequence = this.getDataManagementIntentLabel((String)charSequence)) == null ? null : charSequence.toString();
        return charSequence;
    }

    @SystemApi
    public String getDestinationString(String string2) {
        BackupManager.checkServiceBinder();
        IBackupManager iBackupManager = sService;
        if (iBackupManager != null) {
            try {
                string2 = iBackupManager.getDestinationStringForUser(this.mContext.getUserId(), string2);
                return string2;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "getDestinationString() couldn't connect");
            }
        }
        return null;
    }

    public UserHandle getUserForAncestralSerialNumber(long l) {
        BackupManager.checkServiceBinder();
        Object object = sService;
        if (object != null) {
            try {
                object = object.getUserForAncestralSerialNumber(l);
                return object;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "getUserForAncestralSerialNumber() couldn't connect");
            }
        }
        return null;
    }

    @SystemApi
    public boolean isAppEligibleForBackup(String string2) {
        BackupManager.checkServiceBinder();
        IBackupManager iBackupManager = sService;
        if (iBackupManager != null) {
            try {
                boolean bl = iBackupManager.isAppEligibleForBackupForUser(this.mContext.getUserId(), string2);
                return bl;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "isAppEligibleForBackup(pkg) couldn't connect");
            }
        }
        return false;
    }

    @SystemApi
    public boolean isBackupEnabled() {
        BackupManager.checkServiceBinder();
        IBackupManager iBackupManager = sService;
        if (iBackupManager != null) {
            try {
                boolean bl = iBackupManager.isBackupEnabled();
                return bl;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "isBackupEnabled() couldn't connect");
            }
        }
        return false;
    }

    @SystemApi
    public boolean isBackupServiceActive(UserHandle userHandle) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.BACKUP", "isBackupServiceActive");
        BackupManager.checkServiceBinder();
        IBackupManager iBackupManager = sService;
        if (iBackupManager != null) {
            try {
                boolean bl = iBackupManager.isBackupServiceActive(userHandle.getIdentifier());
                return bl;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "isBackupEnabled() couldn't connect");
            }
        }
        return false;
    }

    @SystemApi
    public String[] listAllTransports() {
        BackupManager.checkServiceBinder();
        String[] arrstring = sService;
        if (arrstring != null) {
            try {
                arrstring = arrstring.listAllTransports();
                return arrstring;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "listAllTransports() couldn't connect");
            }
        }
        return null;
    }

    @SystemApi
    public int requestBackup(String[] arrstring, BackupObserver backupObserver) {
        return this.requestBackup(arrstring, backupObserver, null, 0);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @SystemApi
    public int requestBackup(String[] arrstring, BackupObserver object, BackupManagerMonitor object2, int n) {
        BackupManager.checkServiceBinder();
        if (sService == null) return -1;
        Object var5_6 = null;
        if (object == null) {
            object = null;
        } else {
            object = new BackupObserverWrapper(this.mContext, (BackupObserver)object);
        }
        if (object2 == null) {
            object2 = var5_6;
            return sService.requestBackup(arrstring, (IBackupObserver)object, (IBackupManagerMonitor)object2, n);
        }
        try {
            object2 = new BackupManagerMonitorWrapper((BackupManagerMonitor)object2);
            return sService.requestBackup(arrstring, (IBackupObserver)object, (IBackupManagerMonitor)object2, n);
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "requestBackup() couldn't connect");
        }
        return -1;
    }

    @Deprecated
    public int requestRestore(RestoreObserver restoreObserver) {
        return this.requestRestore(restoreObserver, null);
    }

    @SystemApi
    @Deprecated
    public int requestRestore(RestoreObserver restoreObserver, BackupManagerMonitor backupManagerMonitor) {
        Log.w(TAG, "requestRestore(): Since Android P app can no longer request restoring of its backup.");
        return -1;
    }

    @SystemApi
    @Deprecated
    public String selectBackupTransport(String string2) {
        BackupManager.checkServiceBinder();
        IBackupManager iBackupManager = sService;
        if (iBackupManager != null) {
            try {
                string2 = iBackupManager.selectBackupTransport(string2);
                return string2;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "selectBackupTransport() couldn't connect");
            }
        }
        return null;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @SystemApi
    public void selectBackupTransport(ComponentName var1_1, SelectBackupTransportCallback var2_3) {
        block2 : {
            BackupManager.checkServiceBinder();
            if (BackupManager.sService == null) return;
            if (var2_3 != null) break block2;
            var2_3 = null;
            ** GOTO lbl9
        }
        try {
            var2_3 = new SelectTransportListenerWrapper(this.mContext, (SelectBackupTransportCallback)var2_3);
lbl9: // 2 sources:
            BackupManager.sService.selectBackupTransportAsyncForUser(this.mContext.getUserId(), var1_1, (ISelectBackupTransportCallback)var2_3);
            return;
        }
        catch (RemoteException var1_2) {
            Log.e("BackupManager", "selectBackupTransportAsync() couldn't connect");
        }
    }

    @SystemApi
    public void setAncestralSerialNumber(long l) {
        BackupManager.checkServiceBinder();
        IBackupManager iBackupManager = sService;
        if (iBackupManager != null) {
            try {
                iBackupManager.setAncestralSerialNumber(l);
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "setAncestralSerialNumber() couldn't connect");
            }
        }
    }

    @SystemApi
    public void setAutoRestore(boolean bl) {
        BackupManager.checkServiceBinder();
        IBackupManager iBackupManager = sService;
        if (iBackupManager != null) {
            try {
                iBackupManager.setAutoRestore(bl);
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "setAutoRestore() couldn't connect");
            }
        }
    }

    @SystemApi
    public void setBackupEnabled(boolean bl) {
        BackupManager.checkServiceBinder();
        IBackupManager iBackupManager = sService;
        if (iBackupManager != null) {
            try {
                iBackupManager.setBackupEnabled(bl);
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "setBackupEnabled() couldn't connect");
            }
        }
    }

    @SystemApi
    public void updateTransportAttributes(ComponentName componentName, String string2, Intent intent, String string3, Intent intent2, CharSequence charSequence) {
        BackupManager.checkServiceBinder();
        IBackupManager iBackupManager = sService;
        if (iBackupManager != null) {
            try {
                iBackupManager.updateTransportAttributesForUser(this.mContext.getUserId(), componentName, string2, intent, string3, intent2, charSequence);
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "describeTransport() couldn't connect");
            }
        }
    }

    @SystemApi
    @Deprecated
    public void updateTransportAttributes(ComponentName componentName, String string2, Intent intent, String string3, Intent intent2, String string4) {
        this.updateTransportAttributes(componentName, string2, intent, string3, intent2, (CharSequence)string4);
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

    private class BackupObserverWrapper
    extends IBackupObserver.Stub {
        static final int MSG_FINISHED = 3;
        static final int MSG_RESULT = 2;
        static final int MSG_UPDATE = 1;
        final Handler mHandler;
        final BackupObserver mObserver;

        BackupObserverWrapper(Context context, BackupObserver backupObserver) {
            this.mHandler = new Handler(context.getMainLooper()){

                @Override
                public void handleMessage(Message object) {
                    int n = ((Message)object).what;
                    if (n != 1) {
                        if (n != 2) {
                            if (n != 3) {
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append("Unknown message: ");
                                stringBuilder.append(object);
                                Log.w(BackupManager.TAG, stringBuilder.toString());
                            } else {
                                BackupObserverWrapper.this.mObserver.backupFinished(((Message)object).arg1);
                            }
                        } else {
                            BackupObserverWrapper.this.mObserver.onResult((String)((Message)object).obj, ((Message)object).arg1);
                        }
                    } else {
                        object = (Pair)((Message)object).obj;
                        BackupObserverWrapper.this.mObserver.onUpdate((String)((Pair)object).first, (BackupProgress)((Pair)object).second);
                    }
                }
            };
            this.mObserver = backupObserver;
        }

        @Override
        public void backupFinished(int n) {
            Handler handler = this.mHandler;
            handler.sendMessage(handler.obtainMessage(3, n, 0));
        }

        @Override
        public void onResult(String string2, int n) {
            Handler handler = this.mHandler;
            handler.sendMessage(handler.obtainMessage(2, n, 0, string2));
        }

        @Override
        public void onUpdate(String string2, BackupProgress backupProgress) {
            Handler handler = this.mHandler;
            handler.sendMessage(handler.obtainMessage(1, Pair.create(string2, backupProgress)));
        }

    }

    private class SelectTransportListenerWrapper
    extends ISelectBackupTransportCallback.Stub {
        private final Handler mHandler;
        private final SelectBackupTransportCallback mListener;

        SelectTransportListenerWrapper(Context context, SelectBackupTransportCallback selectBackupTransportCallback) {
            this.mHandler = new Handler(context.getMainLooper());
            this.mListener = selectBackupTransportCallback;
        }

        @Override
        public void onFailure(final int n) {
            this.mHandler.post(new Runnable(){

                @Override
                public void run() {
                    SelectTransportListenerWrapper.this.mListener.onFailure(n);
                }
            });
        }

        @Override
        public void onSuccess(final String string2) {
            this.mHandler.post(new Runnable(){

                @Override
                public void run() {
                    SelectTransportListenerWrapper.this.mListener.onSuccess(string2);
                }
            });
        }

    }

}

