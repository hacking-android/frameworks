/*
 * Decompiled with CFR 0.145.
 */
package android.app.backup;

import android.annotation.SystemApi;
import android.app.backup.RestoreDescription;
import android.app.backup.RestoreSet;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.IBinder;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import com.android.internal.backup.IBackupTransport;

@SystemApi
public class BackupTransport {
    public static final int AGENT_ERROR = -1003;
    public static final int AGENT_UNKNOWN = -1004;
    public static final String EXTRA_TRANSPORT_REGISTRATION = "android.app.backup.extra.TRANSPORT_REGISTRATION";
    public static final int FLAG_INCREMENTAL = 2;
    public static final int FLAG_NON_INCREMENTAL = 4;
    public static final int FLAG_USER_INITIATED = 1;
    public static final int NO_MORE_DATA = -1;
    public static final int TRANSPORT_ERROR = -1000;
    public static final int TRANSPORT_NON_INCREMENTAL_BACKUP_REQUIRED = -1006;
    public static final int TRANSPORT_NOT_INITIALIZED = -1001;
    public static final int TRANSPORT_OK = 0;
    public static final int TRANSPORT_PACKAGE_REJECTED = -1002;
    public static final int TRANSPORT_QUOTA_EXCEEDED = -1005;
    IBackupTransport mBinderImpl = new TransportImpl();

    public int abortFullRestore() {
        return 0;
    }

    public void cancelFullBackup() {
        throw new UnsupportedOperationException("Transport cancelFullBackup() not implemented");
    }

    public int checkFullBackupSize(long l) {
        return 0;
    }

    public int clearBackupData(PackageInfo packageInfo) {
        return -1000;
    }

    public Intent configurationIntent() {
        return null;
    }

    public String currentDestinationString() {
        throw new UnsupportedOperationException("Transport currentDestinationString() not implemented");
    }

    public Intent dataManagementIntent() {
        return null;
    }

    public CharSequence dataManagementIntentLabel() {
        return this.dataManagementLabel();
    }

    @Deprecated
    public String dataManagementLabel() {
        throw new UnsupportedOperationException("Transport dataManagementLabel() not implemented");
    }

    public int finishBackup() {
        return -1000;
    }

    public void finishRestore() {
        throw new UnsupportedOperationException("Transport finishRestore() not implemented");
    }

    public RestoreSet[] getAvailableRestoreSets() {
        return null;
    }

    public long getBackupQuota(String string2, boolean bl) {
        return Long.MAX_VALUE;
    }

    public IBinder getBinder() {
        return this.mBinderImpl.asBinder();
    }

    public long getCurrentRestoreSet() {
        return 0L;
    }

    public int getNextFullRestoreDataChunk(ParcelFileDescriptor parcelFileDescriptor) {
        return 0;
    }

    public int getRestoreData(ParcelFileDescriptor parcelFileDescriptor) {
        return -1000;
    }

    public int getTransportFlags() {
        return 0;
    }

    public int initializeDevice() {
        return -1000;
    }

    public boolean isAppEligibleForBackup(PackageInfo packageInfo, boolean bl) {
        return true;
    }

    public String name() {
        throw new UnsupportedOperationException("Transport name() not implemented");
    }

    public RestoreDescription nextRestorePackage() {
        return null;
    }

    public int performBackup(PackageInfo packageInfo, ParcelFileDescriptor parcelFileDescriptor) {
        return -1000;
    }

    public int performBackup(PackageInfo packageInfo, ParcelFileDescriptor parcelFileDescriptor, int n) {
        return this.performBackup(packageInfo, parcelFileDescriptor);
    }

    public int performFullBackup(PackageInfo packageInfo, ParcelFileDescriptor parcelFileDescriptor) {
        return -1002;
    }

    public int performFullBackup(PackageInfo packageInfo, ParcelFileDescriptor parcelFileDescriptor, int n) {
        return this.performFullBackup(packageInfo, parcelFileDescriptor);
    }

    public long requestBackupTime() {
        return 0L;
    }

    public long requestFullBackupTime() {
        return 0L;
    }

    public int sendBackupData(int n) {
        return -1000;
    }

    public int startRestore(long l, PackageInfo[] arrpackageInfo) {
        return -1000;
    }

    public String transportDirName() {
        throw new UnsupportedOperationException("Transport transportDirName() not implemented");
    }

    class TransportImpl
    extends IBackupTransport.Stub {
        TransportImpl() {
        }

        @Override
        public int abortFullRestore() {
            return BackupTransport.this.abortFullRestore();
        }

        @Override
        public void cancelFullBackup() throws RemoteException {
            BackupTransport.this.cancelFullBackup();
        }

        @Override
        public int checkFullBackupSize(long l) {
            return BackupTransport.this.checkFullBackupSize(l);
        }

        @Override
        public int clearBackupData(PackageInfo packageInfo) throws RemoteException {
            return BackupTransport.this.clearBackupData(packageInfo);
        }

        @Override
        public Intent configurationIntent() throws RemoteException {
            return BackupTransport.this.configurationIntent();
        }

        @Override
        public String currentDestinationString() throws RemoteException {
            return BackupTransport.this.currentDestinationString();
        }

        @Override
        public Intent dataManagementIntent() {
            return BackupTransport.this.dataManagementIntent();
        }

        @Override
        public CharSequence dataManagementIntentLabel() {
            return BackupTransport.this.dataManagementIntentLabel();
        }

        @Override
        public int finishBackup() throws RemoteException {
            return BackupTransport.this.finishBackup();
        }

        @Override
        public void finishRestore() throws RemoteException {
            BackupTransport.this.finishRestore();
        }

        @Override
        public RestoreSet[] getAvailableRestoreSets() throws RemoteException {
            return BackupTransport.this.getAvailableRestoreSets();
        }

        @Override
        public long getBackupQuota(String string2, boolean bl) {
            return BackupTransport.this.getBackupQuota(string2, bl);
        }

        @Override
        public long getCurrentRestoreSet() throws RemoteException {
            return BackupTransport.this.getCurrentRestoreSet();
        }

        @Override
        public int getNextFullRestoreDataChunk(ParcelFileDescriptor parcelFileDescriptor) {
            return BackupTransport.this.getNextFullRestoreDataChunk(parcelFileDescriptor);
        }

        @Override
        public int getRestoreData(ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
            return BackupTransport.this.getRestoreData(parcelFileDescriptor);
        }

        @Override
        public int getTransportFlags() {
            return BackupTransport.this.getTransportFlags();
        }

        @Override
        public int initializeDevice() throws RemoteException {
            return BackupTransport.this.initializeDevice();
        }

        @Override
        public boolean isAppEligibleForBackup(PackageInfo packageInfo, boolean bl) throws RemoteException {
            return BackupTransport.this.isAppEligibleForBackup(packageInfo, bl);
        }

        @Override
        public String name() throws RemoteException {
            return BackupTransport.this.name();
        }

        @Override
        public RestoreDescription nextRestorePackage() throws RemoteException {
            return BackupTransport.this.nextRestorePackage();
        }

        @Override
        public int performBackup(PackageInfo packageInfo, ParcelFileDescriptor parcelFileDescriptor, int n) throws RemoteException {
            return BackupTransport.this.performBackup(packageInfo, parcelFileDescriptor, n);
        }

        @Override
        public int performFullBackup(PackageInfo packageInfo, ParcelFileDescriptor parcelFileDescriptor, int n) throws RemoteException {
            return BackupTransport.this.performFullBackup(packageInfo, parcelFileDescriptor, n);
        }

        @Override
        public long requestBackupTime() throws RemoteException {
            return BackupTransport.this.requestBackupTime();
        }

        @Override
        public long requestFullBackupTime() throws RemoteException {
            return BackupTransport.this.requestFullBackupTime();
        }

        @Override
        public int sendBackupData(int n) throws RemoteException {
            return BackupTransport.this.sendBackupData(n);
        }

        @Override
        public int startRestore(long l, PackageInfo[] arrpackageInfo) throws RemoteException {
            return BackupTransport.this.startRestore(l, arrpackageInfo);
        }

        @Override
        public String transportDirName() throws RemoteException {
            return BackupTransport.this.transportDirName();
        }
    }

}

