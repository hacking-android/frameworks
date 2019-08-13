/*
 * Decompiled with CFR 0.145.
 */
package android.content;

import android.accounts.Account;
import android.annotation.UnsupportedAppUsage;
import android.content.ComponentName;
import android.content.ISyncStatusObserver;
import android.content.PeriodicSync;
import android.content.SyncAdapterType;
import android.content.SyncInfo;
import android.content.SyncRequest;
import android.content.SyncStatusInfo;
import android.database.IContentObserver;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface IContentService
extends IInterface {
    public void addPeriodicSync(Account var1, String var2, Bundle var3, long var4) throws RemoteException;

    public void addStatusChangeListener(int var1, ISyncStatusObserver var2) throws RemoteException;

    public void cancelRequest(SyncRequest var1) throws RemoteException;

    @UnsupportedAppUsage
    public void cancelSync(Account var1, String var2, ComponentName var3) throws RemoteException;

    public void cancelSyncAsUser(Account var1, String var2, ComponentName var3, int var4) throws RemoteException;

    public Bundle getCache(String var1, Uri var2, int var3) throws RemoteException;

    public List<SyncInfo> getCurrentSyncs() throws RemoteException;

    public List<SyncInfo> getCurrentSyncsAsUser(int var1) throws RemoteException;

    @UnsupportedAppUsage
    public int getIsSyncable(Account var1, String var2) throws RemoteException;

    public int getIsSyncableAsUser(Account var1, String var2, int var3) throws RemoteException;

    @UnsupportedAppUsage
    public boolean getMasterSyncAutomatically() throws RemoteException;

    public boolean getMasterSyncAutomaticallyAsUser(int var1) throws RemoteException;

    public List<PeriodicSync> getPeriodicSyncs(Account var1, String var2, ComponentName var3) throws RemoteException;

    public String[] getSyncAdapterPackagesForAuthorityAsUser(String var1, int var2) throws RemoteException;

    @UnsupportedAppUsage
    public SyncAdapterType[] getSyncAdapterTypes() throws RemoteException;

    public SyncAdapterType[] getSyncAdapterTypesAsUser(int var1) throws RemoteException;

    public boolean getSyncAutomatically(Account var1, String var2) throws RemoteException;

    public boolean getSyncAutomaticallyAsUser(Account var1, String var2, int var3) throws RemoteException;

    public SyncStatusInfo getSyncStatus(Account var1, String var2, ComponentName var3) throws RemoteException;

    public SyncStatusInfo getSyncStatusAsUser(Account var1, String var2, ComponentName var3, int var4) throws RemoteException;

    @UnsupportedAppUsage
    public boolean isSyncActive(Account var1, String var2, ComponentName var3) throws RemoteException;

    public boolean isSyncPending(Account var1, String var2, ComponentName var3) throws RemoteException;

    public boolean isSyncPendingAsUser(Account var1, String var2, ComponentName var3, int var4) throws RemoteException;

    public void notifyChange(Uri var1, IContentObserver var2, boolean var3, int var4, int var5, int var6, String var7) throws RemoteException;

    public void onDbCorruption(String var1, String var2, String var3) throws RemoteException;

    public void putCache(String var1, Uri var2, Bundle var3, int var4) throws RemoteException;

    public void registerContentObserver(Uri var1, boolean var2, IContentObserver var3, int var4, int var5) throws RemoteException;

    public void removePeriodicSync(Account var1, String var2, Bundle var3) throws RemoteException;

    public void removeStatusChangeListener(ISyncStatusObserver var1) throws RemoteException;

    public void requestSync(Account var1, String var2, Bundle var3, String var4) throws RemoteException;

    public void resetTodayStats() throws RemoteException;

    public void setIsSyncable(Account var1, String var2, int var3) throws RemoteException;

    public void setIsSyncableAsUser(Account var1, String var2, int var3, int var4) throws RemoteException;

    @UnsupportedAppUsage
    public void setMasterSyncAutomatically(boolean var1) throws RemoteException;

    public void setMasterSyncAutomaticallyAsUser(boolean var1, int var2) throws RemoteException;

    public void setSyncAutomatically(Account var1, String var2, boolean var3) throws RemoteException;

    public void setSyncAutomaticallyAsUser(Account var1, String var2, boolean var3, int var4) throws RemoteException;

    public void sync(SyncRequest var1, String var2) throws RemoteException;

    public void syncAsUser(SyncRequest var1, int var2, String var3) throws RemoteException;

    public void unregisterContentObserver(IContentObserver var1) throws RemoteException;

    public static class Default
    implements IContentService {
        @Override
        public void addPeriodicSync(Account account, String string2, Bundle bundle, long l) throws RemoteException {
        }

        @Override
        public void addStatusChangeListener(int n, ISyncStatusObserver iSyncStatusObserver) throws RemoteException {
        }

        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void cancelRequest(SyncRequest syncRequest) throws RemoteException {
        }

        @Override
        public void cancelSync(Account account, String string2, ComponentName componentName) throws RemoteException {
        }

        @Override
        public void cancelSyncAsUser(Account account, String string2, ComponentName componentName, int n) throws RemoteException {
        }

        @Override
        public Bundle getCache(String string2, Uri uri, int n) throws RemoteException {
            return null;
        }

        @Override
        public List<SyncInfo> getCurrentSyncs() throws RemoteException {
            return null;
        }

        @Override
        public List<SyncInfo> getCurrentSyncsAsUser(int n) throws RemoteException {
            return null;
        }

        @Override
        public int getIsSyncable(Account account, String string2) throws RemoteException {
            return 0;
        }

        @Override
        public int getIsSyncableAsUser(Account account, String string2, int n) throws RemoteException {
            return 0;
        }

        @Override
        public boolean getMasterSyncAutomatically() throws RemoteException {
            return false;
        }

        @Override
        public boolean getMasterSyncAutomaticallyAsUser(int n) throws RemoteException {
            return false;
        }

        @Override
        public List<PeriodicSync> getPeriodicSyncs(Account account, String string2, ComponentName componentName) throws RemoteException {
            return null;
        }

        @Override
        public String[] getSyncAdapterPackagesForAuthorityAsUser(String string2, int n) throws RemoteException {
            return null;
        }

        @Override
        public SyncAdapterType[] getSyncAdapterTypes() throws RemoteException {
            return null;
        }

        @Override
        public SyncAdapterType[] getSyncAdapterTypesAsUser(int n) throws RemoteException {
            return null;
        }

        @Override
        public boolean getSyncAutomatically(Account account, String string2) throws RemoteException {
            return false;
        }

        @Override
        public boolean getSyncAutomaticallyAsUser(Account account, String string2, int n) throws RemoteException {
            return false;
        }

        @Override
        public SyncStatusInfo getSyncStatus(Account account, String string2, ComponentName componentName) throws RemoteException {
            return null;
        }

        @Override
        public SyncStatusInfo getSyncStatusAsUser(Account account, String string2, ComponentName componentName, int n) throws RemoteException {
            return null;
        }

        @Override
        public boolean isSyncActive(Account account, String string2, ComponentName componentName) throws RemoteException {
            return false;
        }

        @Override
        public boolean isSyncPending(Account account, String string2, ComponentName componentName) throws RemoteException {
            return false;
        }

        @Override
        public boolean isSyncPendingAsUser(Account account, String string2, ComponentName componentName, int n) throws RemoteException {
            return false;
        }

        @Override
        public void notifyChange(Uri uri, IContentObserver iContentObserver, boolean bl, int n, int n2, int n3, String string2) throws RemoteException {
        }

        @Override
        public void onDbCorruption(String string2, String string3, String string4) throws RemoteException {
        }

        @Override
        public void putCache(String string2, Uri uri, Bundle bundle, int n) throws RemoteException {
        }

        @Override
        public void registerContentObserver(Uri uri, boolean bl, IContentObserver iContentObserver, int n, int n2) throws RemoteException {
        }

        @Override
        public void removePeriodicSync(Account account, String string2, Bundle bundle) throws RemoteException {
        }

        @Override
        public void removeStatusChangeListener(ISyncStatusObserver iSyncStatusObserver) throws RemoteException {
        }

        @Override
        public void requestSync(Account account, String string2, Bundle bundle, String string3) throws RemoteException {
        }

        @Override
        public void resetTodayStats() throws RemoteException {
        }

        @Override
        public void setIsSyncable(Account account, String string2, int n) throws RemoteException {
        }

        @Override
        public void setIsSyncableAsUser(Account account, String string2, int n, int n2) throws RemoteException {
        }

        @Override
        public void setMasterSyncAutomatically(boolean bl) throws RemoteException {
        }

        @Override
        public void setMasterSyncAutomaticallyAsUser(boolean bl, int n) throws RemoteException {
        }

        @Override
        public void setSyncAutomatically(Account account, String string2, boolean bl) throws RemoteException {
        }

        @Override
        public void setSyncAutomaticallyAsUser(Account account, String string2, boolean bl, int n) throws RemoteException {
        }

        @Override
        public void sync(SyncRequest syncRequest, String string2) throws RemoteException {
        }

        @Override
        public void syncAsUser(SyncRequest syncRequest, int n, String string2) throws RemoteException {
        }

        @Override
        public void unregisterContentObserver(IContentObserver iContentObserver) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IContentService {
        private static final String DESCRIPTOR = "android.content.IContentService";
        static final int TRANSACTION_addPeriodicSync = 15;
        static final int TRANSACTION_addStatusChangeListener = 35;
        static final int TRANSACTION_cancelRequest = 9;
        static final int TRANSACTION_cancelSync = 7;
        static final int TRANSACTION_cancelSyncAsUser = 8;
        static final int TRANSACTION_getCache = 38;
        static final int TRANSACTION_getCurrentSyncs = 25;
        static final int TRANSACTION_getCurrentSyncsAsUser = 26;
        static final int TRANSACTION_getIsSyncable = 17;
        static final int TRANSACTION_getIsSyncableAsUser = 18;
        static final int TRANSACTION_getMasterSyncAutomatically = 23;
        static final int TRANSACTION_getMasterSyncAutomaticallyAsUser = 24;
        static final int TRANSACTION_getPeriodicSyncs = 14;
        static final int TRANSACTION_getSyncAdapterPackagesForAuthorityAsUser = 29;
        static final int TRANSACTION_getSyncAdapterTypes = 27;
        static final int TRANSACTION_getSyncAdapterTypesAsUser = 28;
        static final int TRANSACTION_getSyncAutomatically = 10;
        static final int TRANSACTION_getSyncAutomaticallyAsUser = 11;
        static final int TRANSACTION_getSyncStatus = 31;
        static final int TRANSACTION_getSyncStatusAsUser = 32;
        static final int TRANSACTION_isSyncActive = 30;
        static final int TRANSACTION_isSyncPending = 33;
        static final int TRANSACTION_isSyncPendingAsUser = 34;
        static final int TRANSACTION_notifyChange = 3;
        static final int TRANSACTION_onDbCorruption = 40;
        static final int TRANSACTION_putCache = 37;
        static final int TRANSACTION_registerContentObserver = 2;
        static final int TRANSACTION_removePeriodicSync = 16;
        static final int TRANSACTION_removeStatusChangeListener = 36;
        static final int TRANSACTION_requestSync = 4;
        static final int TRANSACTION_resetTodayStats = 39;
        static final int TRANSACTION_setIsSyncable = 19;
        static final int TRANSACTION_setIsSyncableAsUser = 20;
        static final int TRANSACTION_setMasterSyncAutomatically = 21;
        static final int TRANSACTION_setMasterSyncAutomaticallyAsUser = 22;
        static final int TRANSACTION_setSyncAutomatically = 12;
        static final int TRANSACTION_setSyncAutomaticallyAsUser = 13;
        static final int TRANSACTION_sync = 5;
        static final int TRANSACTION_syncAsUser = 6;
        static final int TRANSACTION_unregisterContentObserver = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IContentService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IContentService) {
                return (IContentService)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IContentService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 40: {
                    return "onDbCorruption";
                }
                case 39: {
                    return "resetTodayStats";
                }
                case 38: {
                    return "getCache";
                }
                case 37: {
                    return "putCache";
                }
                case 36: {
                    return "removeStatusChangeListener";
                }
                case 35: {
                    return "addStatusChangeListener";
                }
                case 34: {
                    return "isSyncPendingAsUser";
                }
                case 33: {
                    return "isSyncPending";
                }
                case 32: {
                    return "getSyncStatusAsUser";
                }
                case 31: {
                    return "getSyncStatus";
                }
                case 30: {
                    return "isSyncActive";
                }
                case 29: {
                    return "getSyncAdapterPackagesForAuthorityAsUser";
                }
                case 28: {
                    return "getSyncAdapterTypesAsUser";
                }
                case 27: {
                    return "getSyncAdapterTypes";
                }
                case 26: {
                    return "getCurrentSyncsAsUser";
                }
                case 25: {
                    return "getCurrentSyncs";
                }
                case 24: {
                    return "getMasterSyncAutomaticallyAsUser";
                }
                case 23: {
                    return "getMasterSyncAutomatically";
                }
                case 22: {
                    return "setMasterSyncAutomaticallyAsUser";
                }
                case 21: {
                    return "setMasterSyncAutomatically";
                }
                case 20: {
                    return "setIsSyncableAsUser";
                }
                case 19: {
                    return "setIsSyncable";
                }
                case 18: {
                    return "getIsSyncableAsUser";
                }
                case 17: {
                    return "getIsSyncable";
                }
                case 16: {
                    return "removePeriodicSync";
                }
                case 15: {
                    return "addPeriodicSync";
                }
                case 14: {
                    return "getPeriodicSyncs";
                }
                case 13: {
                    return "setSyncAutomaticallyAsUser";
                }
                case 12: {
                    return "setSyncAutomatically";
                }
                case 11: {
                    return "getSyncAutomaticallyAsUser";
                }
                case 10: {
                    return "getSyncAutomatically";
                }
                case 9: {
                    return "cancelRequest";
                }
                case 8: {
                    return "cancelSyncAsUser";
                }
                case 7: {
                    return "cancelSync";
                }
                case 6: {
                    return "syncAsUser";
                }
                case 5: {
                    return "sync";
                }
                case 4: {
                    return "requestSync";
                }
                case 3: {
                    return "notifyChange";
                }
                case 2: {
                    return "registerContentObserver";
                }
                case 1: 
            }
            return "unregisterContentObserver";
        }

        public static boolean setDefaultImpl(IContentService iContentService) {
            if (Proxy.sDefaultImpl == null && iContentService != null) {
                Proxy.sDefaultImpl = iContentService;
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
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, parcel, n2);
                    }
                    case 40: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onDbCorruption(((Parcel)object).readString(), ((Parcel)object).readString(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 39: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.resetTodayStats();
                        parcel.writeNoException();
                        return true;
                    }
                    case 38: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string2 = ((Parcel)object).readString();
                        Uri uri = ((Parcel)object).readInt() != 0 ? Uri.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getCache(string2, uri, ((Parcel)object).readInt());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((Bundle)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 37: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string3 = ((Parcel)object).readString();
                        Uri uri = ((Parcel)object).readInt() != 0 ? Uri.CREATOR.createFromParcel((Parcel)object) : null;
                        Bundle bundle = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.putCache(string3, uri, bundle, ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 36: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.removeStatusChangeListener(ISyncStatusObserver.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 35: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.addStatusChangeListener(((Parcel)object).readInt(), ISyncStatusObserver.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 34: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        Account account = ((Parcel)object).readInt() != 0 ? Account.CREATOR.createFromParcel((Parcel)object) : null;
                        String string4 = ((Parcel)object).readString();
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.isSyncPendingAsUser(account, string4, componentName, ((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 33: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        Account account = ((Parcel)object).readInt() != 0 ? Account.CREATOR.createFromParcel((Parcel)object) : null;
                        String string5 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.isSyncPending(account, string5, (ComponentName)object) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 32: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        Account account = ((Parcel)object).readInt() != 0 ? Account.CREATOR.createFromParcel((Parcel)object) : null;
                        String string6 = ((Parcel)object).readString();
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getSyncStatusAsUser(account, string6, componentName, ((Parcel)object).readInt());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((SyncStatusInfo)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 31: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        Account account = ((Parcel)object).readInt() != 0 ? Account.CREATOR.createFromParcel((Parcel)object) : null;
                        String string7 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getSyncStatus(account, string7, (ComponentName)object);
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((SyncStatusInfo)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 30: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        Account account = ((Parcel)object).readInt() != 0 ? Account.CREATOR.createFromParcel((Parcel)object) : null;
                        String string8 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.isSyncActive(account, string8, (ComponentName)object) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 29: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getSyncAdapterPackagesForAuthorityAsUser(((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeStringArray((String[])object);
                        return true;
                    }
                    case 28: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getSyncAdapterTypesAsUser(((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeTypedArray((Parcelable[])object, 1);
                        return true;
                    }
                    case 27: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getSyncAdapterTypes();
                        parcel.writeNoException();
                        parcel.writeTypedArray((Parcelable[])object, 1);
                        return true;
                    }
                    case 26: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getCurrentSyncsAsUser(((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeTypedList(object);
                        return true;
                    }
                    case 25: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getCurrentSyncs();
                        parcel.writeNoException();
                        parcel.writeTypedList(object);
                        return true;
                    }
                    case 24: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getMasterSyncAutomaticallyAsUser(((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 23: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getMasterSyncAutomatically() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 22: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        if (((Parcel)object).readInt() != 0) {
                            bl4 = true;
                        }
                        this.setMasterSyncAutomaticallyAsUser(bl4, ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 21: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl4 = bl;
                        if (((Parcel)object).readInt() != 0) {
                            bl4 = true;
                        }
                        this.setMasterSyncAutomatically(bl4);
                        parcel.writeNoException();
                        return true;
                    }
                    case 20: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        Account account = ((Parcel)object).readInt() != 0 ? Account.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setIsSyncableAsUser(account, ((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 19: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        Account account = ((Parcel)object).readInt() != 0 ? Account.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setIsSyncable(account, ((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 18: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        Account account = ((Parcel)object).readInt() != 0 ? Account.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.getIsSyncableAsUser(account, ((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 17: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        Account account = ((Parcel)object).readInt() != 0 ? Account.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.getIsSyncable(account, ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 16: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        Account account = ((Parcel)object).readInt() != 0 ? Account.CREATOR.createFromParcel((Parcel)object) : null;
                        String string9 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.removePeriodicSync(account, string9, (Bundle)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 15: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        Account account = ((Parcel)object).readInt() != 0 ? Account.CREATOR.createFromParcel((Parcel)object) : null;
                        String string10 = ((Parcel)object).readString();
                        Bundle bundle = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.addPeriodicSync(account, string10, bundle, ((Parcel)object).readLong());
                        parcel.writeNoException();
                        return true;
                    }
                    case 14: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        Account account = ((Parcel)object).readInt() != 0 ? Account.CREATOR.createFromParcel((Parcel)object) : null;
                        String string11 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getPeriodicSyncs(account, string11, (ComponentName)object);
                        parcel.writeNoException();
                        parcel.writeTypedList(object);
                        return true;
                    }
                    case 13: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        Account account = ((Parcel)object).readInt() != 0 ? Account.CREATOR.createFromParcel((Parcel)object) : null;
                        String string12 = ((Parcel)object).readString();
                        bl4 = bl2;
                        if (((Parcel)object).readInt() != 0) {
                            bl4 = true;
                        }
                        this.setSyncAutomaticallyAsUser(account, string12, bl4, ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 12: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        Account account = ((Parcel)object).readInt() != 0 ? Account.CREATOR.createFromParcel((Parcel)object) : null;
                        String string13 = ((Parcel)object).readString();
                        bl4 = bl3;
                        if (((Parcel)object).readInt() != 0) {
                            bl4 = true;
                        }
                        this.setSyncAutomatically(account, string13, bl4);
                        parcel.writeNoException();
                        return true;
                    }
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        Account account = ((Parcel)object).readInt() != 0 ? Account.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.getSyncAutomaticallyAsUser(account, ((Parcel)object).readString(), ((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        Account account = ((Parcel)object).readInt() != 0 ? Account.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.getSyncAutomatically(account, ((Parcel)object).readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? SyncRequest.CREATOR.createFromParcel((Parcel)object) : null;
                        this.cancelRequest((SyncRequest)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        Account account = ((Parcel)object).readInt() != 0 ? Account.CREATOR.createFromParcel((Parcel)object) : null;
                        String string14 = ((Parcel)object).readString();
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        this.cancelSyncAsUser(account, string14, componentName, ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        Account account = ((Parcel)object).readInt() != 0 ? Account.CREATOR.createFromParcel((Parcel)object) : null;
                        String string15 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        this.cancelSync(account, string15, (ComponentName)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        SyncRequest syncRequest = ((Parcel)object).readInt() != 0 ? SyncRequest.CREATOR.createFromParcel((Parcel)object) : null;
                        this.syncAsUser(syncRequest, ((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        SyncRequest syncRequest = ((Parcel)object).readInt() != 0 ? SyncRequest.CREATOR.createFromParcel((Parcel)object) : null;
                        this.sync(syncRequest, ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        Account account = ((Parcel)object).readInt() != 0 ? Account.CREATOR.createFromParcel((Parcel)object) : null;
                        String string16 = ((Parcel)object).readString();
                        Bundle bundle = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.requestSync(account, string16, bundle, ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        Uri uri = ((Parcel)object).readInt() != 0 ? Uri.CREATOR.createFromParcel((Parcel)object) : null;
                        IContentObserver iContentObserver = IContentObserver.Stub.asInterface(((Parcel)object).readStrongBinder());
                        bl4 = ((Parcel)object).readInt() != 0;
                        this.notifyChange(uri, iContentObserver, bl4, ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        Uri uri = ((Parcel)object).readInt() != 0 ? Uri.CREATOR.createFromParcel((Parcel)object) : null;
                        bl4 = ((Parcel)object).readInt() != 0;
                        this.registerContentObserver(uri, bl4, IContentObserver.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                this.unregisterContentObserver(IContentObserver.Stub.asInterface(((Parcel)object).readStrongBinder()));
                parcel.writeNoException();
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IContentService {
            public static IContentService sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public void addPeriodicSync(Account account, String string2, Bundle bundle, long l) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (account != null) {
                        parcel.writeInt(1);
                        account.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeLong(l);
                    if (!this.mRemote.transact(15, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addPeriodicSync(account, string2, bundle, l);
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
            public void addStatusChangeListener(int n, ISyncStatusObserver iSyncStatusObserver) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    IBinder iBinder = iSyncStatusObserver != null ? iSyncStatusObserver.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(35, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addStatusChangeListener(n, iSyncStatusObserver);
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
            public void cancelRequest(SyncRequest syncRequest) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (syncRequest != null) {
                        parcel.writeInt(1);
                        syncRequest.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(9, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().cancelRequest(syncRequest);
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
            public void cancelSync(Account account, String string2, ComponentName componentName) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (account != null) {
                        parcel.writeInt(1);
                        account.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(7, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().cancelSync(account, string2, componentName);
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
            public void cancelSyncAsUser(Account account, String string2, ComponentName componentName, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (account != null) {
                        parcel.writeInt(1);
                        account.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(8, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().cancelSyncAsUser(account, string2, componentName, n);
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
            public Bundle getCache(String object, Uri uri, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString((String)object);
                    if (uri != null) {
                        parcel.writeInt(1);
                        uri.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(38, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().getCache((String)object, uri, n);
                        parcel2.recycle();
                        parcel.recycle();
                        return object;
                    }
                    parcel2.readException();
                    object = parcel2.readInt() != 0 ? Bundle.CREATOR.createFromParcel(parcel2) : null;
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

            @Override
            public List<SyncInfo> getCurrentSyncs() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(25, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        List<SyncInfo> list = Stub.getDefaultImpl().getCurrentSyncs();
                        return list;
                    }
                    parcel2.readException();
                    ArrayList<SyncInfo> arrayList = parcel2.createTypedArrayList(SyncInfo.CREATOR);
                    return arrayList;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public List<SyncInfo> getCurrentSyncsAsUser(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(26, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        List<SyncInfo> list = Stub.getDefaultImpl().getCurrentSyncsAsUser(n);
                        return list;
                    }
                    parcel2.readException();
                    ArrayList<SyncInfo> arrayList = parcel2.createTypedArrayList(SyncInfo.CREATOR);
                    return arrayList;
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
            public int getIsSyncable(Account account, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (account != null) {
                        parcel.writeInt(1);
                        account.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(17, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getIsSyncable(account, string2);
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getIsSyncableAsUser(Account account, String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (account != null) {
                        parcel.writeInt(1);
                        account.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(18, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getIsSyncableAsUser(account, string2, n);
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
            public boolean getMasterSyncAutomatically() throws RemoteException {
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
                    if (iBinder.transact(23, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().getMasterSyncAutomatically();
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
            public boolean getMasterSyncAutomaticallyAsUser(int n) throws RemoteException {
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
                    if (iBinder.transact(24, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().getMasterSyncAutomaticallyAsUser(n);
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
            public List<PeriodicSync> getPeriodicSyncs(Account list, String string2, ComponentName componentName) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (list != null) {
                        parcel.writeInt(1);
                        ((Account)((Object)list)).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(14, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        list = Stub.getDefaultImpl().getPeriodicSyncs((Account)((Object)list), string2, componentName);
                        return list;
                    }
                    parcel2.readException();
                    list = parcel2.createTypedArrayList(PeriodicSync.CREATOR);
                    return list;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String[] getSyncAdapterPackagesForAuthorityAsUser(String arrstring, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString((String)arrstring);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(29, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrstring = Stub.getDefaultImpl().getSyncAdapterPackagesForAuthorityAsUser((String)arrstring, n);
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
            public SyncAdapterType[] getSyncAdapterTypes() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(27, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        SyncAdapterType[] arrsyncAdapterType = Stub.getDefaultImpl().getSyncAdapterTypes();
                        return arrsyncAdapterType;
                    }
                    parcel2.readException();
                    SyncAdapterType[] arrsyncAdapterType = parcel2.createTypedArray(SyncAdapterType.CREATOR);
                    return arrsyncAdapterType;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public SyncAdapterType[] getSyncAdapterTypesAsUser(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(28, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        SyncAdapterType[] arrsyncAdapterType = Stub.getDefaultImpl().getSyncAdapterTypesAsUser(n);
                        return arrsyncAdapterType;
                    }
                    parcel2.readException();
                    SyncAdapterType[] arrsyncAdapterType = parcel2.createTypedArray(SyncAdapterType.CREATOR);
                    return arrsyncAdapterType;
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
            public boolean getSyncAutomatically(Account account, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean bl = true;
                    if (account != null) {
                        parcel.writeInt(1);
                        account.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(10, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().getSyncAutomatically(account, string2);
                        parcel2.recycle();
                        parcel.recycle();
                        return bl;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    if (n == 0) {
                        bl = false;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public boolean getSyncAutomaticallyAsUser(Account account, String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean bl = true;
                    if (account != null) {
                        parcel.writeInt(1);
                        account.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(11, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().getSyncAutomaticallyAsUser(account, string2, n);
                        parcel2.recycle();
                        parcel.recycle();
                        return bl;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    if (n == 0) {
                        bl = false;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            /*
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public SyncStatusInfo getSyncStatus(Account parcelable, String string2, ComponentName componentName) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    void var3_8;
                    void var2_7;
                    void var1_5;
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelable != null) {
                        parcel.writeInt(1);
                        ((Account)parcelable).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString((String)var2_7);
                    if (var3_8 != null) {
                        parcel.writeInt(1);
                        var3_8.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(31, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        SyncStatusInfo syncStatusInfo = Stub.getDefaultImpl().getSyncStatus((Account)parcelable, (String)var2_7, (ComponentName)var3_8);
                        parcel2.recycle();
                        parcel.recycle();
                        return syncStatusInfo;
                    }
                    parcel2.readException();
                    if (parcel2.readInt() != 0) {
                        SyncStatusInfo syncStatusInfo = SyncStatusInfo.CREATOR.createFromParcel(parcel2);
                    } else {
                        Object var1_4 = null;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return var1_5;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            /*
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public SyncStatusInfo getSyncStatusAsUser(Account parcelable, String string2, ComponentName componentName, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    void var3_8;
                    void var2_7;
                    void var4_9;
                    void var1_5;
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelable != null) {
                        parcel.writeInt(1);
                        ((Account)parcelable).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString((String)var2_7);
                    if (var3_8 != null) {
                        parcel.writeInt(1);
                        var3_8.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt((int)var4_9);
                    if (!this.mRemote.transact(32, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        SyncStatusInfo syncStatusInfo = Stub.getDefaultImpl().getSyncStatusAsUser((Account)parcelable, (String)var2_7, (ComponentName)var3_8, (int)var4_9);
                        parcel2.recycle();
                        parcel.recycle();
                        return syncStatusInfo;
                    }
                    parcel2.readException();
                    if (parcel2.readInt() != 0) {
                        SyncStatusInfo syncStatusInfo = SyncStatusInfo.CREATOR.createFromParcel(parcel2);
                    } else {
                        Object var1_4 = null;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return var1_5;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public boolean isSyncActive(Account account, String string2, ComponentName componentName) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean bl = true;
                    if (account != null) {
                        parcel.writeInt(1);
                        account.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(30, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().isSyncActive(account, string2, componentName);
                        parcel2.recycle();
                        parcel.recycle();
                        return bl;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    if (n == 0) {
                        bl = false;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public boolean isSyncPending(Account account, String string2, ComponentName componentName) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean bl = true;
                    if (account != null) {
                        parcel.writeInt(1);
                        account.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(33, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().isSyncPending(account, string2, componentName);
                        parcel2.recycle();
                        parcel.recycle();
                        return bl;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    if (n == 0) {
                        bl = false;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public boolean isSyncPendingAsUser(Account account, String string2, ComponentName componentName, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean bl = true;
                    if (account != null) {
                        parcel.writeInt(1);
                        account.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(34, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().isSyncPendingAsUser(account, string2, componentName, n);
                        parcel2.recycle();
                        parcel.recycle();
                        return bl;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    if (n == 0) {
                        bl = false;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
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
            public void notifyChange(Uri uri, IContentObserver iContentObserver, boolean bl, int n, int n2, int n3, String string2) throws RemoteException {
                Parcel parcel;
                void var1_7;
                Parcel parcel2;
                block15 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n4 = 1;
                    if (uri != null) {
                        parcel.writeInt(1);
                        uri.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    IBinder iBinder = iContentObserver != null ? iContentObserver.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!bl) {
                        n4 = 0;
                    }
                    parcel.writeInt(n4);
                    try {
                        parcel.writeInt(n);
                    }
                    catch (Throwable throwable) {
                        break block15;
                    }
                    try {
                        parcel.writeInt(n2);
                    }
                    catch (Throwable throwable) {
                        break block15;
                    }
                    try {
                        parcel.writeInt(n3);
                    }
                    catch (Throwable throwable) {
                        break block15;
                    }
                    try {
                        parcel.writeString(string2);
                        if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().notifyChange(uri, iContentObserver, bl, n, n2, n3, string2);
                            parcel2.recycle();
                            parcel.recycle();
                            return;
                        }
                        parcel2.readException();
                        parcel2.recycle();
                        parcel.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block15;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel2.recycle();
                parcel.recycle();
                throw var1_7;
            }

            @Override
            public void onDbCorruption(String string2, String string3, String string4) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    parcel.writeString(string4);
                    if (!this.mRemote.transact(40, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onDbCorruption(string2, string3, string4);
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
            public void putCache(String string2, Uri uri, Bundle bundle, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (uri != null) {
                        parcel.writeInt(1);
                        uri.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(37, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().putCache(string2, uri, bundle, n);
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
            public void registerContentObserver(Uri uri, boolean bl, IContentObserver iContentObserver, int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n3 = 1;
                    if (uri != null) {
                        parcel.writeInt(1);
                        uri.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!bl) {
                        n3 = 0;
                    }
                    parcel.writeInt(n3);
                    IBinder iBinder = iContentObserver != null ? iContentObserver.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerContentObserver(uri, bl, iContentObserver, n, n2);
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
            public void removePeriodicSync(Account account, String string2, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (account != null) {
                        parcel.writeInt(1);
                        account.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(16, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removePeriodicSync(account, string2, bundle);
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
            public void removeStatusChangeListener(ISyncStatusObserver iSyncStatusObserver) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iSyncStatusObserver != null ? iSyncStatusObserver.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(36, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeStatusChangeListener(iSyncStatusObserver);
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
            public void requestSync(Account account, String string2, Bundle bundle, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (account != null) {
                        parcel.writeInt(1);
                        account.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().requestSync(account, string2, bundle, string3);
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
            public void resetTodayStats() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(39, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().resetTodayStats();
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
            public void setIsSyncable(Account account, String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (account != null) {
                        parcel.writeInt(1);
                        account.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(19, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setIsSyncable(account, string2, n);
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
            public void setIsSyncableAsUser(Account account, String string2, int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (account != null) {
                        parcel.writeInt(1);
                        account.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(20, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setIsSyncableAsUser(account, string2, n, n2);
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
            public void setMasterSyncAutomatically(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(21, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setMasterSyncAutomatically(bl);
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
            public void setMasterSyncAutomaticallyAsUser(boolean bl, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(22, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setMasterSyncAutomaticallyAsUser(bl, n);
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
            public void setSyncAutomatically(Account account, String string2, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n = 1;
                    if (account != null) {
                        parcel.writeInt(1);
                        account.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    if (!bl) {
                        n = 0;
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(12, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setSyncAutomatically(account, string2, bl);
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
            public void setSyncAutomaticallyAsUser(Account account, String string2, boolean bl, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n2 = 1;
                    if (account != null) {
                        parcel.writeInt(1);
                        account.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    if (!bl) {
                        n2 = 0;
                    }
                    parcel.writeInt(n2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(13, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setSyncAutomaticallyAsUser(account, string2, bl, n);
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
            public void sync(SyncRequest syncRequest, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (syncRequest != null) {
                        parcel.writeInt(1);
                        syncRequest.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().sync(syncRequest, string2);
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
            public void syncAsUser(SyncRequest syncRequest, int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (syncRequest != null) {
                        parcel.writeInt(1);
                        syncRequest.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().syncAsUser(syncRequest, n, string2);
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
            public void unregisterContentObserver(IContentObserver iContentObserver) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iContentObserver != null ? iContentObserver.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregisterContentObserver(iContentObserver);
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
        }

    }

}

