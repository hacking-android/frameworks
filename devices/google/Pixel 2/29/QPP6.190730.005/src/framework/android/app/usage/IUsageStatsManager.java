/*
 * Decompiled with CFR 0.145.
 */
package android.app.usage;

import android.annotation.UnsupportedAppUsage;
import android.app.PendingIntent;
import android.app.usage.UsageEvents;
import android.content.pm.ParceledListSlice;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface IUsageStatsManager
extends IInterface {
    public void forceUsageSourceSettingRead() throws RemoteException;

    public int getAppStandbyBucket(String var1, String var2, int var3) throws RemoteException;

    public ParceledListSlice getAppStandbyBuckets(String var1, int var2) throws RemoteException;

    public int getUsageSource() throws RemoteException;

    @UnsupportedAppUsage
    public boolean isAppInactive(String var1, int var2) throws RemoteException;

    public void onCarrierPrivilegedAppsChanged() throws RemoteException;

    @UnsupportedAppUsage
    public ParceledListSlice queryConfigurationStats(int var1, long var2, long var4, String var6) throws RemoteException;

    public ParceledListSlice queryEventStats(int var1, long var2, long var4, String var6) throws RemoteException;

    public UsageEvents queryEvents(long var1, long var3, String var5) throws RemoteException;

    public UsageEvents queryEventsForPackage(long var1, long var3, String var5) throws RemoteException;

    public UsageEvents queryEventsForPackageForUser(long var1, long var3, int var5, String var6, String var7) throws RemoteException;

    public UsageEvents queryEventsForUser(long var1, long var3, int var5, String var6) throws RemoteException;

    @UnsupportedAppUsage
    public ParceledListSlice queryUsageStats(int var1, long var2, long var4, String var6) throws RemoteException;

    public void registerAppUsageLimitObserver(int var1, String[] var2, long var3, long var5, PendingIntent var7, String var8) throws RemoteException;

    public void registerAppUsageObserver(int var1, String[] var2, long var3, PendingIntent var5, String var6) throws RemoteException;

    public void registerUsageSessionObserver(int var1, String[] var2, long var3, long var5, PendingIntent var7, PendingIntent var8, String var9) throws RemoteException;

    public void reportChooserSelection(String var1, int var2, String var3, String[] var4, String var5) throws RemoteException;

    public void reportPastUsageStart(IBinder var1, String var2, long var3, String var5) throws RemoteException;

    public void reportUsageStart(IBinder var1, String var2, String var3) throws RemoteException;

    public void reportUsageStop(IBinder var1, String var2, String var3) throws RemoteException;

    @UnsupportedAppUsage
    public void setAppInactive(String var1, boolean var2, int var3) throws RemoteException;

    public void setAppStandbyBucket(String var1, int var2, int var3) throws RemoteException;

    public void setAppStandbyBuckets(ParceledListSlice var1, int var2) throws RemoteException;

    public void unregisterAppUsageLimitObserver(int var1, String var2) throws RemoteException;

    public void unregisterAppUsageObserver(int var1, String var2) throws RemoteException;

    public void unregisterUsageSessionObserver(int var1, String var2) throws RemoteException;

    public void whitelistAppTemporarily(String var1, long var2, int var4) throws RemoteException;

    public static class Default
    implements IUsageStatsManager {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void forceUsageSourceSettingRead() throws RemoteException {
        }

        @Override
        public int getAppStandbyBucket(String string2, String string3, int n) throws RemoteException {
            return 0;
        }

        @Override
        public ParceledListSlice getAppStandbyBuckets(String string2, int n) throws RemoteException {
            return null;
        }

        @Override
        public int getUsageSource() throws RemoteException {
            return 0;
        }

        @Override
        public boolean isAppInactive(String string2, int n) throws RemoteException {
            return false;
        }

        @Override
        public void onCarrierPrivilegedAppsChanged() throws RemoteException {
        }

        @Override
        public ParceledListSlice queryConfigurationStats(int n, long l, long l2, String string2) throws RemoteException {
            return null;
        }

        @Override
        public ParceledListSlice queryEventStats(int n, long l, long l2, String string2) throws RemoteException {
            return null;
        }

        @Override
        public UsageEvents queryEvents(long l, long l2, String string2) throws RemoteException {
            return null;
        }

        @Override
        public UsageEvents queryEventsForPackage(long l, long l2, String string2) throws RemoteException {
            return null;
        }

        @Override
        public UsageEvents queryEventsForPackageForUser(long l, long l2, int n, String string2, String string3) throws RemoteException {
            return null;
        }

        @Override
        public UsageEvents queryEventsForUser(long l, long l2, int n, String string2) throws RemoteException {
            return null;
        }

        @Override
        public ParceledListSlice queryUsageStats(int n, long l, long l2, String string2) throws RemoteException {
            return null;
        }

        @Override
        public void registerAppUsageLimitObserver(int n, String[] arrstring, long l, long l2, PendingIntent pendingIntent, String string2) throws RemoteException {
        }

        @Override
        public void registerAppUsageObserver(int n, String[] arrstring, long l, PendingIntent pendingIntent, String string2) throws RemoteException {
        }

        @Override
        public void registerUsageSessionObserver(int n, String[] arrstring, long l, long l2, PendingIntent pendingIntent, PendingIntent pendingIntent2, String string2) throws RemoteException {
        }

        @Override
        public void reportChooserSelection(String string2, int n, String string3, String[] arrstring, String string4) throws RemoteException {
        }

        @Override
        public void reportPastUsageStart(IBinder iBinder, String string2, long l, String string3) throws RemoteException {
        }

        @Override
        public void reportUsageStart(IBinder iBinder, String string2, String string3) throws RemoteException {
        }

        @Override
        public void reportUsageStop(IBinder iBinder, String string2, String string3) throws RemoteException {
        }

        @Override
        public void setAppInactive(String string2, boolean bl, int n) throws RemoteException {
        }

        @Override
        public void setAppStandbyBucket(String string2, int n, int n2) throws RemoteException {
        }

        @Override
        public void setAppStandbyBuckets(ParceledListSlice parceledListSlice, int n) throws RemoteException {
        }

        @Override
        public void unregisterAppUsageLimitObserver(int n, String string2) throws RemoteException {
        }

        @Override
        public void unregisterAppUsageObserver(int n, String string2) throws RemoteException {
        }

        @Override
        public void unregisterUsageSessionObserver(int n, String string2) throws RemoteException {
        }

        @Override
        public void whitelistAppTemporarily(String string2, long l, int n) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IUsageStatsManager {
        private static final String DESCRIPTOR = "android.app.usage.IUsageStatsManager";
        static final int TRANSACTION_forceUsageSourceSettingRead = 27;
        static final int TRANSACTION_getAppStandbyBucket = 13;
        static final int TRANSACTION_getAppStandbyBuckets = 15;
        static final int TRANSACTION_getUsageSource = 26;
        static final int TRANSACTION_isAppInactive = 9;
        static final int TRANSACTION_onCarrierPrivilegedAppsChanged = 11;
        static final int TRANSACTION_queryConfigurationStats = 2;
        static final int TRANSACTION_queryEventStats = 3;
        static final int TRANSACTION_queryEvents = 4;
        static final int TRANSACTION_queryEventsForPackage = 5;
        static final int TRANSACTION_queryEventsForPackageForUser = 7;
        static final int TRANSACTION_queryEventsForUser = 6;
        static final int TRANSACTION_queryUsageStats = 1;
        static final int TRANSACTION_registerAppUsageLimitObserver = 21;
        static final int TRANSACTION_registerAppUsageObserver = 17;
        static final int TRANSACTION_registerUsageSessionObserver = 19;
        static final int TRANSACTION_reportChooserSelection = 12;
        static final int TRANSACTION_reportPastUsageStart = 24;
        static final int TRANSACTION_reportUsageStart = 23;
        static final int TRANSACTION_reportUsageStop = 25;
        static final int TRANSACTION_setAppInactive = 8;
        static final int TRANSACTION_setAppStandbyBucket = 14;
        static final int TRANSACTION_setAppStandbyBuckets = 16;
        static final int TRANSACTION_unregisterAppUsageLimitObserver = 22;
        static final int TRANSACTION_unregisterAppUsageObserver = 18;
        static final int TRANSACTION_unregisterUsageSessionObserver = 20;
        static final int TRANSACTION_whitelistAppTemporarily = 10;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IUsageStatsManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IUsageStatsManager) {
                return (IUsageStatsManager)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IUsageStatsManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 27: {
                    return "forceUsageSourceSettingRead";
                }
                case 26: {
                    return "getUsageSource";
                }
                case 25: {
                    return "reportUsageStop";
                }
                case 24: {
                    return "reportPastUsageStart";
                }
                case 23: {
                    return "reportUsageStart";
                }
                case 22: {
                    return "unregisterAppUsageLimitObserver";
                }
                case 21: {
                    return "registerAppUsageLimitObserver";
                }
                case 20: {
                    return "unregisterUsageSessionObserver";
                }
                case 19: {
                    return "registerUsageSessionObserver";
                }
                case 18: {
                    return "unregisterAppUsageObserver";
                }
                case 17: {
                    return "registerAppUsageObserver";
                }
                case 16: {
                    return "setAppStandbyBuckets";
                }
                case 15: {
                    return "getAppStandbyBuckets";
                }
                case 14: {
                    return "setAppStandbyBucket";
                }
                case 13: {
                    return "getAppStandbyBucket";
                }
                case 12: {
                    return "reportChooserSelection";
                }
                case 11: {
                    return "onCarrierPrivilegedAppsChanged";
                }
                case 10: {
                    return "whitelistAppTemporarily";
                }
                case 9: {
                    return "isAppInactive";
                }
                case 8: {
                    return "setAppInactive";
                }
                case 7: {
                    return "queryEventsForPackageForUser";
                }
                case 6: {
                    return "queryEventsForUser";
                }
                case 5: {
                    return "queryEventsForPackage";
                }
                case 4: {
                    return "queryEvents";
                }
                case 3: {
                    return "queryEventStats";
                }
                case 2: {
                    return "queryConfigurationStats";
                }
                case 1: 
            }
            return "queryUsageStats";
        }

        public static boolean setDefaultImpl(IUsageStatsManager iUsageStatsManager) {
            if (Proxy.sDefaultImpl == null && iUsageStatsManager != null) {
                Proxy.sDefaultImpl = iUsageStatsManager;
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
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, parcel, n2);
                    }
                    case 27: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.forceUsageSourceSettingRead();
                        parcel.writeNoException();
                        return true;
                    }
                    case 26: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getUsageSource();
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 25: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.reportUsageStop(((Parcel)object).readStrongBinder(), ((Parcel)object).readString(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 24: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.reportPastUsageStart(((Parcel)object).readStrongBinder(), ((Parcel)object).readString(), ((Parcel)object).readLong(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 23: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.reportUsageStart(((Parcel)object).readStrongBinder(), ((Parcel)object).readString(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 22: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.unregisterAppUsageLimitObserver(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 21: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        String[] arrstring = ((Parcel)object).createStringArray();
                        long l = ((Parcel)object).readLong();
                        long l2 = ((Parcel)object).readLong();
                        PendingIntent pendingIntent = ((Parcel)object).readInt() != 0 ? PendingIntent.CREATOR.createFromParcel((Parcel)object) : null;
                        this.registerAppUsageLimitObserver(n, arrstring, l, l2, pendingIntent, ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 20: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.unregisterUsageSessionObserver(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 19: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        String[] arrstring = ((Parcel)object).createStringArray();
                        long l = ((Parcel)object).readLong();
                        long l3 = ((Parcel)object).readLong();
                        PendingIntent pendingIntent = ((Parcel)object).readInt() != 0 ? PendingIntent.CREATOR.createFromParcel((Parcel)object) : null;
                        PendingIntent pendingIntent2 = ((Parcel)object).readInt() != 0 ? PendingIntent.CREATOR.createFromParcel((Parcel)object) : null;
                        this.registerUsageSessionObserver(n, arrstring, l, l3, pendingIntent, pendingIntent2, ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 18: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.unregisterAppUsageObserver(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 17: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        String[] arrstring = ((Parcel)object).createStringArray();
                        long l = ((Parcel)object).readLong();
                        PendingIntent pendingIntent = ((Parcel)object).readInt() != 0 ? PendingIntent.CREATOR.createFromParcel((Parcel)object) : null;
                        this.registerAppUsageObserver(n, arrstring, l, pendingIntent, ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 16: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ParceledListSlice parceledListSlice = ((Parcel)object).readInt() != 0 ? (ParceledListSlice)ParceledListSlice.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setAppStandbyBuckets(parceledListSlice, ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 15: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getAppStandbyBuckets(((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((ParceledListSlice)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 14: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setAppStandbyBucket(((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 13: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getAppStandbyBucket(((Parcel)object).readString(), ((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 12: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.reportChooserSelection(((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readString(), ((Parcel)object).createStringArray(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onCarrierPrivilegedAppsChanged();
                        parcel.writeNoException();
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.whitelistAppTemporarily(((Parcel)object).readString(), ((Parcel)object).readLong(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isAppInactive(((Parcel)object).readString(), ((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string2 = ((Parcel)object).readString();
                        if (((Parcel)object).readInt() != 0) {
                            bl = true;
                        }
                        this.setAppInactive(string2, bl, ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.queryEventsForPackageForUser(((Parcel)object).readLong(), ((Parcel)object).readLong(), ((Parcel)object).readInt(), ((Parcel)object).readString(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((UsageEvents)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.queryEventsForUser(((Parcel)object).readLong(), ((Parcel)object).readLong(), ((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((UsageEvents)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.queryEventsForPackage(((Parcel)object).readLong(), ((Parcel)object).readLong(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((UsageEvents)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.queryEvents(((Parcel)object).readLong(), ((Parcel)object).readLong(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((UsageEvents)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.queryEventStats(((Parcel)object).readInt(), ((Parcel)object).readLong(), ((Parcel)object).readLong(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((ParceledListSlice)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.queryConfigurationStats(((Parcel)object).readInt(), ((Parcel)object).readLong(), ((Parcel)object).readLong(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((ParceledListSlice)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                object = this.queryUsageStats(((Parcel)object).readInt(), ((Parcel)object).readLong(), ((Parcel)object).readLong(), ((Parcel)object).readString());
                parcel.writeNoException();
                if (object != null) {
                    parcel.writeInt(1);
                    ((ParceledListSlice)object).writeToParcel(parcel, 1);
                } else {
                    parcel.writeInt(0);
                }
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IUsageStatsManager {
            public static IUsageStatsManager sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void forceUsageSourceSettingRead() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(27, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().forceUsageSourceSettingRead();
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
            public int getAppStandbyBucket(String string2, String string3, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(13, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getAppStandbyBucket(string2, string3, n);
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
            public ParceledListSlice getAppStandbyBuckets(String parceledListSlice, int n) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeString((String)((Object)parceledListSlice));
                        parcel2.writeInt(n);
                        if (this.mRemote.transact(15, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        parceledListSlice = Stub.getDefaultImpl().getAppStandbyBuckets((String)((Object)parceledListSlice), n);
                        parcel.recycle();
                        parcel2.recycle();
                        return parceledListSlice;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                parceledListSlice = parcel.readInt() != 0 ? (ParceledListSlice)ParceledListSlice.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return parceledListSlice;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public int getUsageSource() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(26, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getUsageSource();
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
            public boolean isAppInactive(String string2, int n) throws RemoteException {
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
                        parcel2.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(9, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isAppInactive(string2, n);
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
            public void onCarrierPrivilegedAppsChanged() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(11, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onCarrierPrivilegedAppsChanged();
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
            public ParceledListSlice queryConfigurationStats(int n, long l, long l2, String parceledListSlice) throws RemoteException {
                Parcel parcel;
                void var6_10;
                Parcel parcel2;
                block12 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel2.writeInt(n);
                    }
                    catch (Throwable throwable) {
                        break block12;
                    }
                    try {
                        parcel2.writeLong(l);
                    }
                    catch (Throwable throwable) {
                        break block12;
                    }
                    try {
                        parcel2.writeLong(l2);
                    }
                    catch (Throwable throwable) {
                        break block12;
                    }
                    try {
                        parcel2.writeString((String)((Object)parceledListSlice));
                        if (!this.mRemote.transact(2, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            parceledListSlice = Stub.getDefaultImpl().queryConfigurationStats(n, l, l2, (String)((Object)parceledListSlice));
                            parcel.recycle();
                            parcel2.recycle();
                            return parceledListSlice;
                        }
                        parcel.readException();
                        parceledListSlice = parcel.readInt() != 0 ? (ParceledListSlice)ParceledListSlice.CREATOR.createFromParcel(parcel) : null;
                        parcel.recycle();
                        parcel2.recycle();
                        return parceledListSlice;
                    }
                    catch (Throwable throwable) {}
                    break block12;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                throw var6_10;
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
            public ParceledListSlice queryEventStats(int n, long l, long l2, String parceledListSlice) throws RemoteException {
                Parcel parcel;
                void var6_10;
                Parcel parcel2;
                block12 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel2.writeInt(n);
                    }
                    catch (Throwable throwable) {
                        break block12;
                    }
                    try {
                        parcel2.writeLong(l);
                    }
                    catch (Throwable throwable) {
                        break block12;
                    }
                    try {
                        parcel2.writeLong(l2);
                    }
                    catch (Throwable throwable) {
                        break block12;
                    }
                    try {
                        parcel2.writeString((String)((Object)parceledListSlice));
                        if (!this.mRemote.transact(3, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            parceledListSlice = Stub.getDefaultImpl().queryEventStats(n, l, l2, (String)((Object)parceledListSlice));
                            parcel.recycle();
                            parcel2.recycle();
                            return parceledListSlice;
                        }
                        parcel.readException();
                        parceledListSlice = parcel.readInt() != 0 ? (ParceledListSlice)ParceledListSlice.CREATOR.createFromParcel(parcel) : null;
                        parcel.recycle();
                        parcel2.recycle();
                        return parceledListSlice;
                    }
                    catch (Throwable throwable) {}
                    break block12;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                throw var6_10;
            }

            @Override
            public UsageEvents queryEvents(long l, long l2, String object) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeLong(l);
                        parcel2.writeLong(l2);
                        parcel2.writeString((String)object);
                        if (this.mRemote.transact(4, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().queryEvents(l, l2, (String)object);
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
                object = parcel.readInt() != 0 ? UsageEvents.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return object;
            }

            @Override
            public UsageEvents queryEventsForPackage(long l, long l2, String object) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeLong(l);
                        parcel2.writeLong(l2);
                        parcel2.writeString((String)object);
                        if (this.mRemote.transact(5, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().queryEventsForPackage(l, l2, (String)object);
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
                object = parcel.readInt() != 0 ? UsageEvents.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return object;
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
            public UsageEvents queryEventsForPackageForUser(long l, long l2, int n, String object, String string2) throws RemoteException {
                void var6_9;
                Parcel parcel2;
                Parcel parcel;
                block10 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel2.writeLong(l);
                    }
                    catch (Throwable throwable) {
                        break block10;
                    }
                    try {
                        parcel2.writeLong(l2);
                    }
                    catch (Throwable throwable) {
                        break block10;
                    }
                    try {
                        parcel2.writeInt(n);
                        parcel2.writeString((String)object);
                        parcel2.writeString(string2);
                        if (!this.mRemote.transact(7, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            object = Stub.getDefaultImpl().queryEventsForPackageForUser(l, l2, n, (String)object, string2);
                            parcel.recycle();
                            parcel2.recycle();
                            return object;
                        }
                        parcel.readException();
                        object = parcel.readInt() != 0 ? UsageEvents.CREATOR.createFromParcel(parcel) : null;
                        parcel.recycle();
                        parcel2.recycle();
                        return object;
                    }
                    catch (Throwable throwable) {}
                    break block10;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                throw var6_9;
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
            public UsageEvents queryEventsForUser(long l, long l2, int n, String object) throws RemoteException {
                Parcel parcel;
                void var6_10;
                Parcel parcel2;
                block12 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel2.writeLong(l);
                    }
                    catch (Throwable throwable) {
                        break block12;
                    }
                    try {
                        parcel2.writeLong(l2);
                    }
                    catch (Throwable throwable) {
                        break block12;
                    }
                    try {
                        parcel2.writeInt(n);
                    }
                    catch (Throwable throwable) {
                        break block12;
                    }
                    try {
                        parcel2.writeString((String)object);
                        if (!this.mRemote.transact(6, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            object = Stub.getDefaultImpl().queryEventsForUser(l, l2, n, (String)object);
                            parcel.recycle();
                            parcel2.recycle();
                            return object;
                        }
                        parcel.readException();
                        object = parcel.readInt() != 0 ? UsageEvents.CREATOR.createFromParcel(parcel) : null;
                        parcel.recycle();
                        parcel2.recycle();
                        return object;
                    }
                    catch (Throwable throwable) {}
                    break block12;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                throw var6_10;
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
            public ParceledListSlice queryUsageStats(int n, long l, long l2, String parceledListSlice) throws RemoteException {
                Parcel parcel;
                void var6_10;
                Parcel parcel2;
                block12 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel2.writeInt(n);
                    }
                    catch (Throwable throwable) {
                        break block12;
                    }
                    try {
                        parcel2.writeLong(l);
                    }
                    catch (Throwable throwable) {
                        break block12;
                    }
                    try {
                        parcel2.writeLong(l2);
                    }
                    catch (Throwable throwable) {
                        break block12;
                    }
                    try {
                        parcel2.writeString((String)((Object)parceledListSlice));
                        if (!this.mRemote.transact(1, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            parceledListSlice = Stub.getDefaultImpl().queryUsageStats(n, l, l2, (String)((Object)parceledListSlice));
                            parcel.recycle();
                            parcel2.recycle();
                            return parceledListSlice;
                        }
                        parcel.readException();
                        parceledListSlice = parcel.readInt() != 0 ? (ParceledListSlice)ParceledListSlice.CREATOR.createFromParcel(parcel) : null;
                        parcel.recycle();
                        parcel2.recycle();
                        return parceledListSlice;
                    }
                    catch (Throwable throwable) {}
                    break block12;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                throw var6_10;
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
            public void registerAppUsageLimitObserver(int n, String[] arrstring, long l, long l2, PendingIntent pendingIntent, String string2) throws RemoteException {
                Parcel parcel2;
                void var2_7;
                Parcel parcel;
                block12 : {
                    block11 : {
                        parcel = Parcel.obtain();
                        parcel2 = Parcel.obtain();
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        try {
                            parcel.writeInt(n);
                        }
                        catch (Throwable throwable) {
                            break block12;
                        }
                        try {
                            parcel.writeStringArray(arrstring);
                            parcel.writeLong(l);
                            parcel.writeLong(l2);
                            if (pendingIntent != null) {
                                parcel.writeInt(1);
                                pendingIntent.writeToParcel(parcel, 0);
                                break block11;
                            }
                            parcel.writeInt(0);
                        }
                        catch (Throwable throwable) {}
                    }
                    try {
                        parcel.writeString(string2);
                        if (!this.mRemote.transact(21, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().registerAppUsageLimitObserver(n, arrstring, l, l2, pendingIntent, string2);
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
                    break block12;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel2.recycle();
                parcel.recycle();
                throw var2_7;
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
            public void registerAppUsageObserver(int n, String[] arrstring, long l, PendingIntent pendingIntent, String string2) throws RemoteException {
                Parcel parcel;
                void var2_8;
                Parcel parcel2;
                block14 : {
                    block13 : {
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
                            parcel2.writeStringArray(arrstring);
                        }
                        catch (Throwable throwable) {
                            break block14;
                        }
                        try {
                            parcel2.writeLong(l);
                            if (pendingIntent != null) {
                                parcel2.writeInt(1);
                                pendingIntent.writeToParcel(parcel2, 0);
                                break block13;
                            }
                            parcel2.writeInt(0);
                        }
                        catch (Throwable throwable) {}
                    }
                    try {
                        parcel2.writeString(string2);
                        if (!this.mRemote.transact(17, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().registerAppUsageObserver(n, arrstring, l, pendingIntent, string2);
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
                throw var2_8;
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
            public void registerUsageSessionObserver(int n, String[] arrstring, long l, long l2, PendingIntent pendingIntent, PendingIntent pendingIntent2, String string2) throws RemoteException {
                Parcel parcel2;
                void var2_5;
                Parcel parcel;
                block10 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel.writeInt(n);
                        parcel.writeStringArray(arrstring);
                        parcel.writeLong(l);
                        parcel.writeLong(l2);
                        if (pendingIntent != null) {
                            parcel.writeInt(1);
                            pendingIntent.writeToParcel(parcel, 0);
                        } else {
                            parcel.writeInt(0);
                        }
                        if (pendingIntent2 != null) {
                            parcel.writeInt(1);
                            pendingIntent2.writeToParcel(parcel, 0);
                        } else {
                            parcel.writeInt(0);
                        }
                        parcel.writeString(string2);
                        if (!this.mRemote.transact(19, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().registerUsageSessionObserver(n, arrstring, l, l2, pendingIntent, pendingIntent2, string2);
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
                    break block10;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel2.recycle();
                parcel.recycle();
                throw var2_5;
            }

            @Override
            public void reportChooserSelection(String string2, int n, String string3, String[] arrstring, String string4) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    parcel.writeString(string3);
                    parcel.writeStringArray(arrstring);
                    parcel.writeString(string4);
                    if (!this.mRemote.transact(12, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().reportChooserSelection(string2, n, string3, arrstring, string4);
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
            public void reportPastUsageStart(IBinder iBinder, String string2, long l, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string2);
                    parcel.writeLong(l);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(24, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().reportPastUsageStart(iBinder, string2, l, string3);
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
            public void reportUsageStart(IBinder iBinder, String string2, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(23, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().reportUsageStart(iBinder, string2, string3);
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
            public void reportUsageStop(IBinder iBinder, String string2, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(25, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().reportUsageStop(iBinder, string2, string3);
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
            public void setAppInactive(String string2, boolean bl, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeString(string2);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(8, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setAppInactive(string2, bl, n);
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
            public void setAppStandbyBucket(String string2, int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(14, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setAppStandbyBucket(string2, n, n2);
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
            public void setAppStandbyBuckets(ParceledListSlice parceledListSlice, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parceledListSlice != null) {
                        parcel.writeInt(1);
                        parceledListSlice.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(16, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setAppStandbyBuckets(parceledListSlice, n);
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
            public void unregisterAppUsageLimitObserver(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(22, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregisterAppUsageLimitObserver(n, string2);
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
            public void unregisterAppUsageObserver(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(18, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregisterAppUsageObserver(n, string2);
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
            public void unregisterUsageSessionObserver(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(20, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregisterUsageSessionObserver(n, string2);
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
            public void whitelistAppTemporarily(String string2, long l, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeLong(l);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(10, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().whitelistAppTemporarily(string2, l, n);
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

