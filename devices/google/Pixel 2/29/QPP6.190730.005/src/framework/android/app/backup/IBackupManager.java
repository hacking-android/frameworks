/*
 * Decompiled with CFR 0.145.
 */
package android.app.backup;

import android.annotation.UnsupportedAppUsage;
import android.app.backup.IBackupManagerMonitor;
import android.app.backup.IBackupObserver;
import android.app.backup.IFullBackupRestoreObserver;
import android.app.backup.IRestoreSession;
import android.app.backup.ISelectBackupTransportCallback;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.UserHandle;
import android.text.TextUtils;

public interface IBackupManager
extends IInterface {
    @UnsupportedAppUsage
    public void acknowledgeFullBackupOrRestore(int var1, boolean var2, String var3, String var4, IFullBackupRestoreObserver var5) throws RemoteException;

    public void acknowledgeFullBackupOrRestoreForUser(int var1, int var2, boolean var3, String var4, String var5, IFullBackupRestoreObserver var6) throws RemoteException;

    public void adbBackup(int var1, ParcelFileDescriptor var2, boolean var3, boolean var4, boolean var5, boolean var6, boolean var7, boolean var8, boolean var9, boolean var10, String[] var11) throws RemoteException;

    public void adbRestore(int var1, ParcelFileDescriptor var2) throws RemoteException;

    public void agentConnected(String var1, IBinder var2) throws RemoteException;

    public void agentConnectedForUser(int var1, String var2, IBinder var3) throws RemoteException;

    public void agentDisconnected(String var1) throws RemoteException;

    public void agentDisconnectedForUser(int var1, String var2) throws RemoteException;

    public void backupNow() throws RemoteException;

    public void backupNowForUser(int var1) throws RemoteException;

    public IRestoreSession beginRestoreSessionForUser(int var1, String var2, String var3) throws RemoteException;

    public void cancelBackups() throws RemoteException;

    public void cancelBackupsForUser(int var1) throws RemoteException;

    @UnsupportedAppUsage
    public void clearBackupData(String var1, String var2) throws RemoteException;

    public void clearBackupDataForUser(int var1, String var2, String var3) throws RemoteException;

    @UnsupportedAppUsage
    public void dataChanged(String var1) throws RemoteException;

    public void dataChangedForUser(int var1, String var2) throws RemoteException;

    public String[] filterAppsEligibleForBackupForUser(int var1, String[] var2) throws RemoteException;

    public void fullTransportBackupForUser(int var1, String[] var2) throws RemoteException;

    public long getAvailableRestoreTokenForUser(int var1, String var2) throws RemoteException;

    public Intent getConfigurationIntent(String var1) throws RemoteException;

    public Intent getConfigurationIntentForUser(int var1, String var2) throws RemoteException;

    @UnsupportedAppUsage
    public String getCurrentTransport() throws RemoteException;

    public ComponentName getCurrentTransportComponentForUser(int var1) throws RemoteException;

    public String getCurrentTransportForUser(int var1) throws RemoteException;

    public Intent getDataManagementIntent(String var1) throws RemoteException;

    public Intent getDataManagementIntentForUser(int var1, String var2) throws RemoteException;

    public CharSequence getDataManagementLabelForUser(int var1, String var2) throws RemoteException;

    public String getDestinationString(String var1) throws RemoteException;

    public String getDestinationStringForUser(int var1, String var2) throws RemoteException;

    public String[] getTransportWhitelist() throws RemoteException;

    public UserHandle getUserForAncestralSerialNumber(long var1) throws RemoteException;

    public boolean hasBackupPassword() throws RemoteException;

    public void initializeTransportsForUser(int var1, String[] var2, IBackupObserver var3) throws RemoteException;

    public boolean isAppEligibleForBackupForUser(int var1, String var2) throws RemoteException;

    @UnsupportedAppUsage
    public boolean isBackupEnabled() throws RemoteException;

    public boolean isBackupEnabledForUser(int var1) throws RemoteException;

    @UnsupportedAppUsage
    public boolean isBackupServiceActive(int var1) throws RemoteException;

    public ComponentName[] listAllTransportComponentsForUser(int var1) throws RemoteException;

    @UnsupportedAppUsage
    public String[] listAllTransports() throws RemoteException;

    public String[] listAllTransportsForUser(int var1) throws RemoteException;

    public void opComplete(int var1, long var2) throws RemoteException;

    public void opCompleteForUser(int var1, int var2, long var3) throws RemoteException;

    public int requestBackup(String[] var1, IBackupObserver var2, IBackupManagerMonitor var3, int var4) throws RemoteException;

    public int requestBackupForUser(int var1, String[] var2, IBackupObserver var3, IBackupManagerMonitor var4, int var5) throws RemoteException;

    public void restoreAtInstall(String var1, int var2) throws RemoteException;

    public void restoreAtInstallForUser(int var1, String var2, int var3) throws RemoteException;

    @UnsupportedAppUsage
    public String selectBackupTransport(String var1) throws RemoteException;

    public void selectBackupTransportAsyncForUser(int var1, ComponentName var2, ISelectBackupTransportCallback var3) throws RemoteException;

    public String selectBackupTransportForUser(int var1, String var2) throws RemoteException;

    public void setAncestralSerialNumber(long var1) throws RemoteException;

    @UnsupportedAppUsage
    public void setAutoRestore(boolean var1) throws RemoteException;

    public void setAutoRestoreForUser(int var1, boolean var2) throws RemoteException;

    @UnsupportedAppUsage
    public void setBackupEnabled(boolean var1) throws RemoteException;

    public void setBackupEnabledForUser(int var1, boolean var2) throws RemoteException;

    public boolean setBackupPassword(String var1, String var2) throws RemoteException;

    public void setBackupServiceActive(int var1, boolean var2) throws RemoteException;

    public void updateTransportAttributesForUser(int var1, ComponentName var2, String var3, Intent var4, String var5, Intent var6, CharSequence var7) throws RemoteException;

    public static class Default
    implements IBackupManager {
        @Override
        public void acknowledgeFullBackupOrRestore(int n, boolean bl, String string2, String string3, IFullBackupRestoreObserver iFullBackupRestoreObserver) throws RemoteException {
        }

        @Override
        public void acknowledgeFullBackupOrRestoreForUser(int n, int n2, boolean bl, String string2, String string3, IFullBackupRestoreObserver iFullBackupRestoreObserver) throws RemoteException {
        }

        @Override
        public void adbBackup(int n, ParcelFileDescriptor parcelFileDescriptor, boolean bl, boolean bl2, boolean bl3, boolean bl4, boolean bl5, boolean bl6, boolean bl7, boolean bl8, String[] arrstring) throws RemoteException {
        }

        @Override
        public void adbRestore(int n, ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
        }

        @Override
        public void agentConnected(String string2, IBinder iBinder) throws RemoteException {
        }

        @Override
        public void agentConnectedForUser(int n, String string2, IBinder iBinder) throws RemoteException {
        }

        @Override
        public void agentDisconnected(String string2) throws RemoteException {
        }

        @Override
        public void agentDisconnectedForUser(int n, String string2) throws RemoteException {
        }

        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void backupNow() throws RemoteException {
        }

        @Override
        public void backupNowForUser(int n) throws RemoteException {
        }

        @Override
        public IRestoreSession beginRestoreSessionForUser(int n, String string2, String string3) throws RemoteException {
            return null;
        }

        @Override
        public void cancelBackups() throws RemoteException {
        }

        @Override
        public void cancelBackupsForUser(int n) throws RemoteException {
        }

        @Override
        public void clearBackupData(String string2, String string3) throws RemoteException {
        }

        @Override
        public void clearBackupDataForUser(int n, String string2, String string3) throws RemoteException {
        }

        @Override
        public void dataChanged(String string2) throws RemoteException {
        }

        @Override
        public void dataChangedForUser(int n, String string2) throws RemoteException {
        }

        @Override
        public String[] filterAppsEligibleForBackupForUser(int n, String[] arrstring) throws RemoteException {
            return null;
        }

        @Override
        public void fullTransportBackupForUser(int n, String[] arrstring) throws RemoteException {
        }

        @Override
        public long getAvailableRestoreTokenForUser(int n, String string2) throws RemoteException {
            return 0L;
        }

        @Override
        public Intent getConfigurationIntent(String string2) throws RemoteException {
            return null;
        }

        @Override
        public Intent getConfigurationIntentForUser(int n, String string2) throws RemoteException {
            return null;
        }

        @Override
        public String getCurrentTransport() throws RemoteException {
            return null;
        }

        @Override
        public ComponentName getCurrentTransportComponentForUser(int n) throws RemoteException {
            return null;
        }

        @Override
        public String getCurrentTransportForUser(int n) throws RemoteException {
            return null;
        }

        @Override
        public Intent getDataManagementIntent(String string2) throws RemoteException {
            return null;
        }

        @Override
        public Intent getDataManagementIntentForUser(int n, String string2) throws RemoteException {
            return null;
        }

        @Override
        public CharSequence getDataManagementLabelForUser(int n, String string2) throws RemoteException {
            return null;
        }

        @Override
        public String getDestinationString(String string2) throws RemoteException {
            return null;
        }

        @Override
        public String getDestinationStringForUser(int n, String string2) throws RemoteException {
            return null;
        }

        @Override
        public String[] getTransportWhitelist() throws RemoteException {
            return null;
        }

        @Override
        public UserHandle getUserForAncestralSerialNumber(long l) throws RemoteException {
            return null;
        }

        @Override
        public boolean hasBackupPassword() throws RemoteException {
            return false;
        }

        @Override
        public void initializeTransportsForUser(int n, String[] arrstring, IBackupObserver iBackupObserver) throws RemoteException {
        }

        @Override
        public boolean isAppEligibleForBackupForUser(int n, String string2) throws RemoteException {
            return false;
        }

        @Override
        public boolean isBackupEnabled() throws RemoteException {
            return false;
        }

        @Override
        public boolean isBackupEnabledForUser(int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean isBackupServiceActive(int n) throws RemoteException {
            return false;
        }

        @Override
        public ComponentName[] listAllTransportComponentsForUser(int n) throws RemoteException {
            return null;
        }

        @Override
        public String[] listAllTransports() throws RemoteException {
            return null;
        }

        @Override
        public String[] listAllTransportsForUser(int n) throws RemoteException {
            return null;
        }

        @Override
        public void opComplete(int n, long l) throws RemoteException {
        }

        @Override
        public void opCompleteForUser(int n, int n2, long l) throws RemoteException {
        }

        @Override
        public int requestBackup(String[] arrstring, IBackupObserver iBackupObserver, IBackupManagerMonitor iBackupManagerMonitor, int n) throws RemoteException {
            return 0;
        }

        @Override
        public int requestBackupForUser(int n, String[] arrstring, IBackupObserver iBackupObserver, IBackupManagerMonitor iBackupManagerMonitor, int n2) throws RemoteException {
            return 0;
        }

        @Override
        public void restoreAtInstall(String string2, int n) throws RemoteException {
        }

        @Override
        public void restoreAtInstallForUser(int n, String string2, int n2) throws RemoteException {
        }

        @Override
        public String selectBackupTransport(String string2) throws RemoteException {
            return null;
        }

        @Override
        public void selectBackupTransportAsyncForUser(int n, ComponentName componentName, ISelectBackupTransportCallback iSelectBackupTransportCallback) throws RemoteException {
        }

        @Override
        public String selectBackupTransportForUser(int n, String string2) throws RemoteException {
            return null;
        }

        @Override
        public void setAncestralSerialNumber(long l) throws RemoteException {
        }

        @Override
        public void setAutoRestore(boolean bl) throws RemoteException {
        }

        @Override
        public void setAutoRestoreForUser(int n, boolean bl) throws RemoteException {
        }

        @Override
        public void setBackupEnabled(boolean bl) throws RemoteException {
        }

        @Override
        public void setBackupEnabledForUser(int n, boolean bl) throws RemoteException {
        }

        @Override
        public boolean setBackupPassword(String string2, String string3) throws RemoteException {
            return false;
        }

        @Override
        public void setBackupServiceActive(int n, boolean bl) throws RemoteException {
        }

        @Override
        public void updateTransportAttributesForUser(int n, ComponentName componentName, String string2, Intent intent, String string3, Intent intent2, CharSequence charSequence) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IBackupManager {
        private static final String DESCRIPTOR = "android.app.backup.IBackupManager";
        static final int TRANSACTION_acknowledgeFullBackupOrRestore = 26;
        static final int TRANSACTION_acknowledgeFullBackupOrRestoreForUser = 25;
        static final int TRANSACTION_adbBackup = 22;
        static final int TRANSACTION_adbRestore = 24;
        static final int TRANSACTION_agentConnected = 7;
        static final int TRANSACTION_agentConnectedForUser = 6;
        static final int TRANSACTION_agentDisconnected = 9;
        static final int TRANSACTION_agentDisconnectedForUser = 8;
        static final int TRANSACTION_backupNow = 21;
        static final int TRANSACTION_backupNowForUser = 20;
        static final int TRANSACTION_beginRestoreSessionForUser = 45;
        static final int TRANSACTION_cancelBackups = 56;
        static final int TRANSACTION_cancelBackupsForUser = 55;
        static final int TRANSACTION_clearBackupData = 4;
        static final int TRANSACTION_clearBackupDataForUser = 3;
        static final int TRANSACTION_dataChanged = 2;
        static final int TRANSACTION_dataChangedForUser = 1;
        static final int TRANSACTION_filterAppsEligibleForBackupForUser = 52;
        static final int TRANSACTION_fullTransportBackupForUser = 23;
        static final int TRANSACTION_getAvailableRestoreTokenForUser = 50;
        static final int TRANSACTION_getConfigurationIntent = 39;
        static final int TRANSACTION_getConfigurationIntentForUser = 38;
        static final int TRANSACTION_getCurrentTransport = 29;
        static final int TRANSACTION_getCurrentTransportComponentForUser = 30;
        static final int TRANSACTION_getCurrentTransportForUser = 28;
        static final int TRANSACTION_getDataManagementIntent = 43;
        static final int TRANSACTION_getDataManagementIntentForUser = 42;
        static final int TRANSACTION_getDataManagementLabelForUser = 44;
        static final int TRANSACTION_getDestinationString = 41;
        static final int TRANSACTION_getDestinationStringForUser = 40;
        static final int TRANSACTION_getTransportWhitelist = 34;
        static final int TRANSACTION_getUserForAncestralSerialNumber = 57;
        static final int TRANSACTION_hasBackupPassword = 19;
        static final int TRANSACTION_initializeTransportsForUser = 5;
        static final int TRANSACTION_isAppEligibleForBackupForUser = 51;
        static final int TRANSACTION_isBackupEnabled = 17;
        static final int TRANSACTION_isBackupEnabledForUser = 16;
        static final int TRANSACTION_isBackupServiceActive = 49;
        static final int TRANSACTION_listAllTransportComponentsForUser = 33;
        static final int TRANSACTION_listAllTransports = 32;
        static final int TRANSACTION_listAllTransportsForUser = 31;
        static final int TRANSACTION_opComplete = 47;
        static final int TRANSACTION_opCompleteForUser = 46;
        static final int TRANSACTION_requestBackup = 54;
        static final int TRANSACTION_requestBackupForUser = 53;
        static final int TRANSACTION_restoreAtInstall = 11;
        static final int TRANSACTION_restoreAtInstallForUser = 10;
        static final int TRANSACTION_selectBackupTransport = 36;
        static final int TRANSACTION_selectBackupTransportAsyncForUser = 37;
        static final int TRANSACTION_selectBackupTransportForUser = 35;
        static final int TRANSACTION_setAncestralSerialNumber = 58;
        static final int TRANSACTION_setAutoRestore = 15;
        static final int TRANSACTION_setAutoRestoreForUser = 14;
        static final int TRANSACTION_setBackupEnabled = 13;
        static final int TRANSACTION_setBackupEnabledForUser = 12;
        static final int TRANSACTION_setBackupPassword = 18;
        static final int TRANSACTION_setBackupServiceActive = 48;
        static final int TRANSACTION_updateTransportAttributesForUser = 27;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IBackupManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IBackupManager) {
                return (IBackupManager)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IBackupManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 58: {
                    return "setAncestralSerialNumber";
                }
                case 57: {
                    return "getUserForAncestralSerialNumber";
                }
                case 56: {
                    return "cancelBackups";
                }
                case 55: {
                    return "cancelBackupsForUser";
                }
                case 54: {
                    return "requestBackup";
                }
                case 53: {
                    return "requestBackupForUser";
                }
                case 52: {
                    return "filterAppsEligibleForBackupForUser";
                }
                case 51: {
                    return "isAppEligibleForBackupForUser";
                }
                case 50: {
                    return "getAvailableRestoreTokenForUser";
                }
                case 49: {
                    return "isBackupServiceActive";
                }
                case 48: {
                    return "setBackupServiceActive";
                }
                case 47: {
                    return "opComplete";
                }
                case 46: {
                    return "opCompleteForUser";
                }
                case 45: {
                    return "beginRestoreSessionForUser";
                }
                case 44: {
                    return "getDataManagementLabelForUser";
                }
                case 43: {
                    return "getDataManagementIntent";
                }
                case 42: {
                    return "getDataManagementIntentForUser";
                }
                case 41: {
                    return "getDestinationString";
                }
                case 40: {
                    return "getDestinationStringForUser";
                }
                case 39: {
                    return "getConfigurationIntent";
                }
                case 38: {
                    return "getConfigurationIntentForUser";
                }
                case 37: {
                    return "selectBackupTransportAsyncForUser";
                }
                case 36: {
                    return "selectBackupTransport";
                }
                case 35: {
                    return "selectBackupTransportForUser";
                }
                case 34: {
                    return "getTransportWhitelist";
                }
                case 33: {
                    return "listAllTransportComponentsForUser";
                }
                case 32: {
                    return "listAllTransports";
                }
                case 31: {
                    return "listAllTransportsForUser";
                }
                case 30: {
                    return "getCurrentTransportComponentForUser";
                }
                case 29: {
                    return "getCurrentTransport";
                }
                case 28: {
                    return "getCurrentTransportForUser";
                }
                case 27: {
                    return "updateTransportAttributesForUser";
                }
                case 26: {
                    return "acknowledgeFullBackupOrRestore";
                }
                case 25: {
                    return "acknowledgeFullBackupOrRestoreForUser";
                }
                case 24: {
                    return "adbRestore";
                }
                case 23: {
                    return "fullTransportBackupForUser";
                }
                case 22: {
                    return "adbBackup";
                }
                case 21: {
                    return "backupNow";
                }
                case 20: {
                    return "backupNowForUser";
                }
                case 19: {
                    return "hasBackupPassword";
                }
                case 18: {
                    return "setBackupPassword";
                }
                case 17: {
                    return "isBackupEnabled";
                }
                case 16: {
                    return "isBackupEnabledForUser";
                }
                case 15: {
                    return "setAutoRestore";
                }
                case 14: {
                    return "setAutoRestoreForUser";
                }
                case 13: {
                    return "setBackupEnabled";
                }
                case 12: {
                    return "setBackupEnabledForUser";
                }
                case 11: {
                    return "restoreAtInstall";
                }
                case 10: {
                    return "restoreAtInstallForUser";
                }
                case 9: {
                    return "agentDisconnected";
                }
                case 8: {
                    return "agentDisconnectedForUser";
                }
                case 7: {
                    return "agentConnected";
                }
                case 6: {
                    return "agentConnectedForUser";
                }
                case 5: {
                    return "initializeTransportsForUser";
                }
                case 4: {
                    return "clearBackupData";
                }
                case 3: {
                    return "clearBackupDataForUser";
                }
                case 2: {
                    return "dataChanged";
                }
                case 1: 
            }
            return "dataChangedForUser";
        }

        public static boolean setDefaultImpl(IBackupManager iBackupManager) {
            if (Proxy.sDefaultImpl == null && iBackupManager != null) {
                Proxy.sDefaultImpl = iBackupManager;
                return true;
            }
            return false;
        }

        @Override
        public IBinder asBinder() {
            return this;
        }

        @Override
        public String getTransactionName(int n) {
            return Stub.getDefaultTransactionName(n);
        }

        @Override
        public boolean onTransact(int n, Parcel object, Parcel parcel, int n2) throws RemoteException {
            if (n != 1598968902) {
                boolean bl = false;
                boolean bl2 = false;
                boolean bl3 = false;
                boolean bl4 = false;
                boolean bl5 = false;
                boolean bl6 = false;
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, parcel, n2);
                    }
                    case 58: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setAncestralSerialNumber(((Parcel)object).readLong());
                        parcel.writeNoException();
                        return true;
                    }
                    case 57: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getUserForAncestralSerialNumber(((Parcel)object).readLong());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((UserHandle)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 56: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.cancelBackups();
                        parcel.writeNoException();
                        return true;
                    }
                    case 55: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.cancelBackupsForUser(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 54: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.requestBackup(((Parcel)object).createStringArray(), IBackupObserver.Stub.asInterface(((Parcel)object).readStrongBinder()), IBackupManagerMonitor.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 53: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.requestBackupForUser(((Parcel)object).readInt(), ((Parcel)object).createStringArray(), IBackupObserver.Stub.asInterface(((Parcel)object).readStrongBinder()), IBackupManagerMonitor.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 52: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.filterAppsEligibleForBackupForUser(((Parcel)object).readInt(), ((Parcel)object).createStringArray());
                        parcel.writeNoException();
                        parcel.writeStringArray((String[])object);
                        return true;
                    }
                    case 51: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isAppEligibleForBackupForUser(((Parcel)object).readInt(), ((Parcel)object).readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 50: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        long l = this.getAvailableRestoreTokenForUser(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeLong(l);
                        return true;
                    }
                    case 49: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isBackupServiceActive(((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 48: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        if (((Parcel)object).readInt() != 0) {
                            bl6 = true;
                        }
                        this.setBackupServiceActive(n, bl6);
                        parcel.writeNoException();
                        return true;
                    }
                    case 47: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.opComplete(((Parcel)object).readInt(), ((Parcel)object).readLong());
                        parcel.writeNoException();
                        return true;
                    }
                    case 46: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.opCompleteForUser(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readLong());
                        parcel.writeNoException();
                        return true;
                    }
                    case 45: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.beginRestoreSessionForUser(((Parcel)object).readInt(), ((Parcel)object).readString(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        object = object != null ? object.asBinder() : null;
                        parcel.writeStrongBinder((IBinder)object);
                        return true;
                    }
                    case 44: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getDataManagementLabelForUser(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            TextUtils.writeToParcel((CharSequence)object, parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 43: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getDataManagementIntent(((Parcel)object).readString());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((Intent)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 42: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getDataManagementIntentForUser(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((Intent)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 41: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getDestinationString(((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 40: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getDestinationStringForUser(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 39: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getConfigurationIntent(((Parcel)object).readString());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((Intent)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 38: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getConfigurationIntentForUser(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((Intent)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 37: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        this.selectBackupTransportAsyncForUser(n, componentName, ISelectBackupTransportCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 36: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.selectBackupTransport(((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 35: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.selectBackupTransportForUser(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 34: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getTransportWhitelist();
                        parcel.writeNoException();
                        parcel.writeStringArray((String[])object);
                        return true;
                    }
                    case 33: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.listAllTransportComponentsForUser(((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeTypedArray((Parcelable[])object, 1);
                        return true;
                    }
                    case 32: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.listAllTransports();
                        parcel.writeNoException();
                        parcel.writeStringArray((String[])object);
                        return true;
                    }
                    case 31: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.listAllTransportsForUser(((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeStringArray((String[])object);
                        return true;
                    }
                    case 30: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getCurrentTransportComponentForUser(((Parcel)object).readInt());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((ComponentName)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 29: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getCurrentTransport();
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 28: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getCurrentTransportForUser(((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 27: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        String string2 = ((Parcel)object).readString();
                        Intent intent = ((Parcel)object).readInt() != 0 ? Intent.CREATOR.createFromParcel((Parcel)object) : null;
                        String string3 = ((Parcel)object).readString();
                        Intent intent2 = ((Parcel)object).readInt() != 0 ? Intent.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel((Parcel)object) : null;
                        this.updateTransportAttributesForUser(n, componentName, string2, intent, string3, intent2, (CharSequence)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 26: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        bl6 = ((Parcel)object).readInt() != 0;
                        this.acknowledgeFullBackupOrRestore(n, bl6, ((Parcel)object).readString(), ((Parcel)object).readString(), IFullBackupRestoreObserver.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 25: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n2 = ((Parcel)object).readInt();
                        n = ((Parcel)object).readInt();
                        bl6 = ((Parcel)object).readInt() != 0;
                        this.acknowledgeFullBackupOrRestoreForUser(n2, n, bl6, ((Parcel)object).readString(), ((Parcel)object).readString(), IFullBackupRestoreObserver.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 24: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? ParcelFileDescriptor.CREATOR.createFromParcel((Parcel)object) : null;
                        this.adbRestore(n, (ParcelFileDescriptor)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 23: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.fullTransportBackupForUser(((Parcel)object).readInt(), ((Parcel)object).createStringArray());
                        parcel.writeNoException();
                        return true;
                    }
                    case 22: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        ParcelFileDescriptor parcelFileDescriptor = ((Parcel)object).readInt() != 0 ? ParcelFileDescriptor.CREATOR.createFromParcel((Parcel)object) : null;
                        bl6 = ((Parcel)object).readInt() != 0;
                        bl2 = ((Parcel)object).readInt() != 0;
                        bl3 = ((Parcel)object).readInt() != 0;
                        bl4 = ((Parcel)object).readInt() != 0;
                        bl5 = ((Parcel)object).readInt() != 0;
                        boolean bl7 = ((Parcel)object).readInt() != 0;
                        boolean bl8 = ((Parcel)object).readInt() != 0;
                        if (((Parcel)object).readInt() != 0) {
                            bl = true;
                        }
                        this.adbBackup(n, parcelFileDescriptor, bl6, bl2, bl3, bl4, bl5, bl7, bl8, bl, ((Parcel)object).createStringArray());
                        parcel.writeNoException();
                        return true;
                    }
                    case 21: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.backupNow();
                        parcel.writeNoException();
                        return true;
                    }
                    case 20: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.backupNowForUser(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 19: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.hasBackupPassword() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 18: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.setBackupPassword(((Parcel)object).readString(), ((Parcel)object).readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 17: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isBackupEnabled() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 16: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isBackupEnabledForUser(((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 15: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl6 = bl2;
                        if (((Parcel)object).readInt() != 0) {
                            bl6 = true;
                        }
                        this.setAutoRestore(bl6);
                        parcel.writeNoException();
                        return true;
                    }
                    case 14: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        bl6 = bl3;
                        if (((Parcel)object).readInt() != 0) {
                            bl6 = true;
                        }
                        this.setAutoRestoreForUser(n, bl6);
                        parcel.writeNoException();
                        return true;
                    }
                    case 13: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl6 = bl4;
                        if (((Parcel)object).readInt() != 0) {
                            bl6 = true;
                        }
                        this.setBackupEnabled(bl6);
                        parcel.writeNoException();
                        return true;
                    }
                    case 12: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        bl6 = bl5;
                        if (((Parcel)object).readInt() != 0) {
                            bl6 = true;
                        }
                        this.setBackupEnabledForUser(n, bl6);
                        parcel.writeNoException();
                        return true;
                    }
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.restoreAtInstall(((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.restoreAtInstallForUser(((Parcel)object).readInt(), ((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.agentDisconnected(((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.agentDisconnectedForUser(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.agentConnected(((Parcel)object).readString(), ((Parcel)object).readStrongBinder());
                        parcel.writeNoException();
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.agentConnectedForUser(((Parcel)object).readInt(), ((Parcel)object).readString(), ((Parcel)object).readStrongBinder());
                        parcel.writeNoException();
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.initializeTransportsForUser(((Parcel)object).readInt(), ((Parcel)object).createStringArray(), IBackupObserver.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.clearBackupData(((Parcel)object).readString(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.clearBackupDataForUser(((Parcel)object).readInt(), ((Parcel)object).readString(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.dataChanged(((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                this.dataChangedForUser(((Parcel)object).readInt(), ((Parcel)object).readString());
                parcel.writeNoException();
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IBackupManager {
            public static IBackupManager sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void acknowledgeFullBackupOrRestore(int n, boolean bl, String string2, String string3, IFullBackupRestoreObserver iFullBackupRestoreObserver) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    int n2 = bl ? 1 : 0;
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    IBinder iBinder = iFullBackupRestoreObserver != null ? iFullBackupRestoreObserver.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(26, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().acknowledgeFullBackupOrRestore(n, bl, string2, string3, iFullBackupRestoreObserver);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Loose catch block
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public void acknowledgeFullBackupOrRestoreForUser(int n, int n2, boolean bl, String string2, String string3, IFullBackupRestoreObserver iFullBackupRestoreObserver) throws RemoteException {
                void var4_11;
                Parcel parcel;
                Parcel parcel2;
                block14 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel2.writeInt(n);
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                    try {
                        parcel2.writeInt(n2);
                        int n3 = bl ? 1 : 0;
                        parcel2.writeInt(n3);
                    }
                    catch (Throwable throwable) {}
                    try {
                        parcel2.writeString(string2);
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                    try {
                        parcel2.writeString(string3);
                        IBinder iBinder = iFullBackupRestoreObserver != null ? iFullBackupRestoreObserver.asBinder() : null;
                        parcel2.writeStrongBinder(iBinder);
                    }
                    catch (Throwable throwable) {}
                    try {
                        if (!this.mRemote.transact(25, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().acknowledgeFullBackupOrRestoreForUser(n, n2, bl, string2, string3, iFullBackupRestoreObserver);
                            parcel.recycle();
                            parcel2.recycle();
                            return;
                        }
                        parcel.readException();
                        parcel.recycle();
                        parcel2.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block14;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                throw var4_11;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void adbBackup(int n, ParcelFileDescriptor parcelFileDescriptor, boolean bl, boolean bl2, boolean bl3, boolean bl4, boolean bl5, boolean bl6, boolean bl7, boolean bl8, String[] arrstring) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    int n2 = 1;
                    if (parcelFileDescriptor != null) {
                        parcel.writeInt(1);
                        parcelFileDescriptor.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    int n3 = bl ? 1 : 0;
                    parcel.writeInt(n3);
                    n3 = bl2 ? 1 : 0;
                    parcel.writeInt(n3);
                    n3 = bl3 ? 1 : 0;
                    parcel.writeInt(n3);
                    n3 = bl4 ? 1 : 0;
                    parcel.writeInt(n3);
                    n3 = bl5 ? 1 : 0;
                    parcel.writeInt(n3);
                    n3 = bl6 ? 1 : 0;
                    parcel.writeInt(n3);
                    n3 = bl7 ? 1 : 0;
                    parcel.writeInt(n3);
                    n3 = bl8 ? n2 : 0;
                    parcel.writeInt(n3);
                    parcel.writeStringArray(arrstring);
                    if (!this.mRemote.transact(22, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().adbBackup(n, parcelFileDescriptor, bl, bl2, bl3, bl4, bl5, bl6, bl7, bl8, arrstring);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void adbRestore(int n, ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (parcelFileDescriptor != null) {
                        parcel.writeInt(1);
                        parcelFileDescriptor.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(24, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().adbRestore(n, parcelFileDescriptor);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void agentConnected(String string2, IBinder iBinder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(7, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().agentConnected(string2, iBinder);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void agentConnectedForUser(int n, String string2, IBinder iBinder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().agentConnectedForUser(n, string2, iBinder);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void agentDisconnected(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(9, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().agentDisconnected(string2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void agentDisconnectedForUser(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(8, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().agentDisconnectedForUser(n, string2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void backupNow() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(21, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().backupNow();
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void backupNowForUser(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(20, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().backupNowForUser(n);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public IRestoreSession beginRestoreSessionForUser(int n, String object, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString((String)object);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(45, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().beginRestoreSessionForUser(n, (String)object, string2);
                        return object;
                    }
                    parcel2.readException();
                    object = IRestoreSession.Stub.asInterface(parcel2.readStrongBinder());
                    return object;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void cancelBackups() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(56, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().cancelBackups();
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void cancelBackupsForUser(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(55, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().cancelBackupsForUser(n);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void clearBackupData(String string2, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().clearBackupData(string2, string3);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void clearBackupDataForUser(int n, String string2, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().clearBackupDataForUser(n, string2, string3);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void dataChanged(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().dataChanged(string2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void dataChangedForUser(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().dataChangedForUser(n, string2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String[] filterAppsEligibleForBackupForUser(int n, String[] arrstring) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeStringArray(arrstring);
                    if (!this.mRemote.transact(52, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrstring = Stub.getDefaultImpl().filterAppsEligibleForBackupForUser(n, arrstring);
                        return arrstring;
                    }
                    parcel2.readException();
                    arrstring = parcel2.createStringArray();
                    return arrstring;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void fullTransportBackupForUser(int n, String[] arrstring) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeStringArray(arrstring);
                    if (!this.mRemote.transact(23, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().fullTransportBackupForUser(n, arrstring);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public long getAvailableRestoreTokenForUser(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(50, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        long l = Stub.getDefaultImpl().getAvailableRestoreTokenForUser(n, string2);
                        return l;
                    }
                    parcel2.readException();
                    long l = parcel2.readLong();
                    return l;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public Intent getConfigurationIntent(String object) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString((String)object);
                        if (this.mRemote.transact(39, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().getConfigurationIntent((String)object);
                        parcel2.recycle();
                        parcel.recycle();
                        return object;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                object = parcel2.readInt() != 0 ? Intent.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return object;
            }

            @Override
            public Intent getConfigurationIntentForUser(int n, String object) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeInt(n);
                        parcel2.writeString((String)object);
                        if (this.mRemote.transact(38, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().getConfigurationIntentForUser(n, (String)object);
                        parcel.recycle();
                        parcel2.recycle();
                        return object;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                object = parcel.readInt() != 0 ? Intent.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return object;
            }

            @Override
            public String getCurrentTransport() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(29, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String string2 = Stub.getDefaultImpl().getCurrentTransport();
                        return string2;
                    }
                    parcel2.readException();
                    String string3 = parcel2.readString();
                    return string3;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public ComponentName getCurrentTransportComponentForUser(int n) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        if (this.mRemote.transact(30, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        ComponentName componentName = Stub.getDefaultImpl().getCurrentTransportComponentForUser(n);
                        parcel2.recycle();
                        parcel.recycle();
                        return componentName;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                ComponentName componentName = parcel2.readInt() != 0 ? ComponentName.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return componentName;
            }

            @Override
            public String getCurrentTransportForUser(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(28, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String string2 = Stub.getDefaultImpl().getCurrentTransportForUser(n);
                        return string2;
                    }
                    parcel2.readException();
                    String string3 = parcel2.readString();
                    return string3;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public Intent getDataManagementIntent(String object) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString((String)object);
                        if (this.mRemote.transact(43, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().getDataManagementIntent((String)object);
                        parcel2.recycle();
                        parcel.recycle();
                        return object;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                object = parcel2.readInt() != 0 ? Intent.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return object;
            }

            @Override
            public Intent getDataManagementIntentForUser(int n, String object) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeInt(n);
                        parcel2.writeString((String)object);
                        if (this.mRemote.transact(42, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().getDataManagementIntentForUser(n, (String)object);
                        parcel.recycle();
                        parcel2.recycle();
                        return object;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                object = parcel.readInt() != 0 ? Intent.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return object;
            }

            @Override
            public CharSequence getDataManagementLabelForUser(int n, String charSequence) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeInt(n);
                        parcel2.writeString((String)charSequence);
                        if (this.mRemote.transact(44, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        charSequence = Stub.getDefaultImpl().getDataManagementLabelForUser(n, (String)charSequence);
                        parcel.recycle();
                        parcel2.recycle();
                        return charSequence;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                charSequence = parcel.readInt() != 0 ? TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return charSequence;
            }

            @Override
            public String getDestinationString(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(41, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        string2 = Stub.getDefaultImpl().getDestinationString(string2);
                        return string2;
                    }
                    parcel2.readException();
                    string2 = parcel2.readString();
                    return string2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String getDestinationStringForUser(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(40, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        string2 = Stub.getDefaultImpl().getDestinationStringForUser(n, string2);
                        return string2;
                    }
                    parcel2.readException();
                    string2 = parcel2.readString();
                    return string2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public String[] getTransportWhitelist() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(34, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String[] arrstring = Stub.getDefaultImpl().getTransportWhitelist();
                        return arrstring;
                    }
                    parcel2.readException();
                    String[] arrstring = parcel2.createStringArray();
                    return arrstring;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public UserHandle getUserForAncestralSerialNumber(long l) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeLong(l);
                        if (this.mRemote.transact(57, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        UserHandle userHandle = Stub.getDefaultImpl().getUserForAncestralSerialNumber(l);
                        parcel2.recycle();
                        parcel.recycle();
                        return userHandle;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                UserHandle userHandle = parcel2.readInt() != 0 ? UserHandle.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return userHandle;
            }

            @Override
            public boolean hasBackupPassword() throws RemoteException {
                boolean bl;
                Parcel parcel;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(19, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().hasBackupPassword();
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                int n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void initializeTransportsForUser(int n, String[] arrstring, IBackupObserver iBackupObserver) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeStringArray(arrstring);
                    IBinder iBinder = iBackupObserver != null ? iBackupObserver.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().initializeTransportsForUser(n, arrstring, iBackupObserver);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean isAppEligibleForBackupForUser(int n, String string2) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeInt(n);
                        parcel2.writeString(string2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(51, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isAppEligibleForBackupForUser(n, string2);
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public boolean isBackupEnabled() throws RemoteException {
                boolean bl;
                Parcel parcel;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(17, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isBackupEnabled();
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                int n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public boolean isBackupEnabledForUser(int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(16, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isBackupEnabledForUser(n);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public boolean isBackupServiceActive(int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(49, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isBackupServiceActive(n);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public ComponentName[] listAllTransportComponentsForUser(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(33, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        ComponentName[] arrcomponentName = Stub.getDefaultImpl().listAllTransportComponentsForUser(n);
                        return arrcomponentName;
                    }
                    parcel2.readException();
                    ComponentName[] arrcomponentName = parcel2.createTypedArray(ComponentName.CREATOR);
                    return arrcomponentName;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String[] listAllTransports() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(32, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String[] arrstring = Stub.getDefaultImpl().listAllTransports();
                        return arrstring;
                    }
                    parcel2.readException();
                    String[] arrstring = parcel2.createStringArray();
                    return arrstring;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String[] listAllTransportsForUser(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(31, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String[] arrstring = Stub.getDefaultImpl().listAllTransportsForUser(n);
                        return arrstring;
                    }
                    parcel2.readException();
                    String[] arrstring = parcel2.createStringArray();
                    return arrstring;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void opComplete(int n, long l) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeLong(l);
                    if (!this.mRemote.transact(47, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().opComplete(n, l);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void opCompleteForUser(int n, int n2, long l) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeLong(l);
                    if (!this.mRemote.transact(46, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().opCompleteForUser(n, n2, l);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public int requestBackup(String[] arrstring, IBackupObserver iBackupObserver, IBackupManagerMonitor iBackupManagerMonitor, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStringArray(arrstring);
                    Object var7_8 = null;
                    IBinder iBinder = iBackupObserver != null ? iBackupObserver.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    iBinder = var7_8;
                    if (iBackupManagerMonitor != null) {
                        iBinder = iBackupManagerMonitor.asBinder();
                    }
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(54, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().requestBackup(arrstring, iBackupObserver, iBackupManagerMonitor, n);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public int requestBackupForUser(int n, String[] arrstring, IBackupObserver iBackupObserver, IBackupManagerMonitor iBackupManagerMonitor, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeStringArray(arrstring);
                    Object var8_9 = null;
                    IBinder iBinder = iBackupObserver != null ? iBackupObserver.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    iBinder = var8_9;
                    if (iBackupManagerMonitor != null) {
                        iBinder = iBackupManagerMonitor.asBinder();
                    }
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(53, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().requestBackupForUser(n, arrstring, iBackupObserver, iBackupManagerMonitor, n2);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void restoreAtInstall(String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(11, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().restoreAtInstall(string2, n);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void restoreAtInstallForUser(int n, String string2, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(10, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().restoreAtInstallForUser(n, string2, n2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String selectBackupTransport(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(36, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        string2 = Stub.getDefaultImpl().selectBackupTransport(string2);
                        return string2;
                    }
                    parcel2.readException();
                    string2 = parcel2.readString();
                    return string2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void selectBackupTransportAsyncForUser(int n, ComponentName componentName, ISelectBackupTransportCallback iSelectBackupTransportCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    IBinder iBinder = iSelectBackupTransportCallback != null ? iSelectBackupTransportCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(37, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().selectBackupTransportAsyncForUser(n, componentName, iSelectBackupTransportCallback);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String selectBackupTransportForUser(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(35, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        string2 = Stub.getDefaultImpl().selectBackupTransportForUser(n, string2);
                        return string2;
                    }
                    parcel2.readException();
                    string2 = parcel2.readString();
                    return string2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setAncestralSerialNumber(long l) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeLong(l);
                    if (!this.mRemote.transact(58, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setAncestralSerialNumber(l);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setAutoRestore(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(15, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setAutoRestore(bl);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setAutoRestoreForUser(int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(14, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setAutoRestoreForUser(n, bl);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setBackupEnabled(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(13, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setBackupEnabled(bl);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setBackupEnabledForUser(int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(12, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setBackupEnabledForUser(n, bl);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean setBackupPassword(String string2, String string3) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeString(string2);
                        parcel2.writeString(string3);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(18, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().setBackupPassword(string2, string3);
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                int n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public void setBackupServiceActive(int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(48, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setBackupServiceActive(n, bl);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Loose catch block
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public void updateTransportAttributesForUser(int n, ComponentName componentName, String string2, Intent intent, String string3, Intent intent2, CharSequence charSequence) throws RemoteException {
                void var2_5;
                Parcel parcel2;
                Parcel parcel;
                block14 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel2.writeInt(n);
                        if (componentName != null) {
                            parcel2.writeInt(1);
                            componentName.writeToParcel(parcel2, 0);
                        } else {
                            parcel2.writeInt(0);
                        }
                        parcel2.writeString(string2);
                        if (intent != null) {
                            parcel2.writeInt(1);
                            intent.writeToParcel(parcel2, 0);
                        } else {
                            parcel2.writeInt(0);
                        }
                        parcel2.writeString(string3);
                        if (intent2 != null) {
                            parcel2.writeInt(1);
                            intent2.writeToParcel(parcel2, 0);
                        } else {
                            parcel2.writeInt(0);
                        }
                        if (charSequence != null) {
                            parcel2.writeInt(1);
                            TextUtils.writeToParcel(charSequence, parcel2, 0);
                        } else {
                            parcel2.writeInt(0);
                        }
                        if (!this.mRemote.transact(27, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().updateTransportAttributesForUser(n, componentName, string2, intent, string3, intent2, charSequence);
                            parcel.recycle();
                            parcel2.recycle();
                            return;
                        }
                        parcel.readException();
                        parcel.recycle();
                        parcel2.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block14;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                throw var2_5;
            }
        }

    }

}

