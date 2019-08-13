/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.annotation.UnsupportedAppUsage;
import android.net.NetworkStats;
import android.net.NetworkStatsHistory;
import android.net.NetworkTemplate;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface INetworkStatsSession
extends IInterface {
    @UnsupportedAppUsage
    public void close() throws RemoteException;

    public NetworkStats getDeviceSummaryForNetwork(NetworkTemplate var1, long var2, long var4) throws RemoteException;

    @UnsupportedAppUsage
    public NetworkStatsHistory getHistoryForNetwork(NetworkTemplate var1, int var2) throws RemoteException;

    @UnsupportedAppUsage
    public NetworkStatsHistory getHistoryForUid(NetworkTemplate var1, int var2, int var3, int var4, int var5) throws RemoteException;

    public NetworkStatsHistory getHistoryIntervalForUid(NetworkTemplate var1, int var2, int var3, int var4, int var5, long var6, long var8) throws RemoteException;

    public int[] getRelevantUids() throws RemoteException;

    @UnsupportedAppUsage
    public NetworkStats getSummaryForAllUid(NetworkTemplate var1, long var2, long var4, boolean var6) throws RemoteException;

    @UnsupportedAppUsage
    public NetworkStats getSummaryForNetwork(NetworkTemplate var1, long var2, long var4) throws RemoteException;

    public static class Default
    implements INetworkStatsSession {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void close() throws RemoteException {
        }

        @Override
        public NetworkStats getDeviceSummaryForNetwork(NetworkTemplate networkTemplate, long l, long l2) throws RemoteException {
            return null;
        }

        @Override
        public NetworkStatsHistory getHistoryForNetwork(NetworkTemplate networkTemplate, int n) throws RemoteException {
            return null;
        }

        @Override
        public NetworkStatsHistory getHistoryForUid(NetworkTemplate networkTemplate, int n, int n2, int n3, int n4) throws RemoteException {
            return null;
        }

        @Override
        public NetworkStatsHistory getHistoryIntervalForUid(NetworkTemplate networkTemplate, int n, int n2, int n3, int n4, long l, long l2) throws RemoteException {
            return null;
        }

        @Override
        public int[] getRelevantUids() throws RemoteException {
            return null;
        }

        @Override
        public NetworkStats getSummaryForAllUid(NetworkTemplate networkTemplate, long l, long l2, boolean bl) throws RemoteException {
            return null;
        }

        @Override
        public NetworkStats getSummaryForNetwork(NetworkTemplate networkTemplate, long l, long l2) throws RemoteException {
            return null;
        }
    }

    public static abstract class Stub
    extends Binder
    implements INetworkStatsSession {
        private static final String DESCRIPTOR = "android.net.INetworkStatsSession";
        static final int TRANSACTION_close = 8;
        static final int TRANSACTION_getDeviceSummaryForNetwork = 1;
        static final int TRANSACTION_getHistoryForNetwork = 3;
        static final int TRANSACTION_getHistoryForUid = 5;
        static final int TRANSACTION_getHistoryIntervalForUid = 6;
        static final int TRANSACTION_getRelevantUids = 7;
        static final int TRANSACTION_getSummaryForAllUid = 4;
        static final int TRANSACTION_getSummaryForNetwork = 2;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static INetworkStatsSession asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof INetworkStatsSession) {
                return (INetworkStatsSession)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static INetworkStatsSession getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 8: {
                    return "close";
                }
                case 7: {
                    return "getRelevantUids";
                }
                case 6: {
                    return "getHistoryIntervalForUid";
                }
                case 5: {
                    return "getHistoryForUid";
                }
                case 4: {
                    return "getSummaryForAllUid";
                }
                case 3: {
                    return "getHistoryForNetwork";
                }
                case 2: {
                    return "getSummaryForNetwork";
                }
                case 1: 
            }
            return "getDeviceSummaryForNetwork";
        }

        public static boolean setDefaultImpl(INetworkStatsSession iNetworkStatsSession) {
            if (Proxy.sDefaultImpl == null && iNetworkStatsSession != null) {
                Proxy.sDefaultImpl = iNetworkStatsSession;
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
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, parcel, n2);
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.close();
                        parcel.writeNoException();
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getRelevantUids();
                        parcel.writeNoException();
                        parcel.writeIntArray((int[])object);
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        NetworkTemplate networkTemplate = ((Parcel)object).readInt() != 0 ? NetworkTemplate.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getHistoryIntervalForUid(networkTemplate, ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readLong(), ((Parcel)object).readLong());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((NetworkStatsHistory)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        NetworkTemplate networkTemplate = ((Parcel)object).readInt() != 0 ? NetworkTemplate.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getHistoryForUid(networkTemplate, ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((NetworkStatsHistory)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        NetworkTemplate networkTemplate = ((Parcel)object).readInt() != 0 ? NetworkTemplate.CREATOR.createFromParcel((Parcel)object) : null;
                        long l = ((Parcel)object).readLong();
                        long l2 = ((Parcel)object).readLong();
                        boolean bl = ((Parcel)object).readInt() != 0;
                        object = this.getSummaryForAllUid(networkTemplate, l, l2, bl);
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((NetworkStats)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        NetworkTemplate networkTemplate = ((Parcel)object).readInt() != 0 ? NetworkTemplate.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getHistoryForNetwork(networkTemplate, ((Parcel)object).readInt());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((NetworkStatsHistory)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        NetworkTemplate networkTemplate = ((Parcel)object).readInt() != 0 ? NetworkTemplate.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getSummaryForNetwork(networkTemplate, ((Parcel)object).readLong(), ((Parcel)object).readLong());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((NetworkStats)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                NetworkTemplate networkTemplate = ((Parcel)object).readInt() != 0 ? NetworkTemplate.CREATOR.createFromParcel((Parcel)object) : null;
                object = this.getDeviceSummaryForNetwork(networkTemplate, ((Parcel)object).readLong(), ((Parcel)object).readLong());
                parcel.writeNoException();
                if (object != null) {
                    parcel.writeInt(1);
                    ((NetworkStats)object).writeToParcel(parcel, 1);
                } else {
                    parcel.writeInt(0);
                }
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements INetworkStatsSession {
            public static INetworkStatsSession sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void close() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(8, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().close();
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
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public NetworkStats getDeviceSummaryForNetwork(NetworkTemplate parcelable, long l, long l2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    void var2_7;
                    void var4_8;
                    void var1_5;
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelable != null) {
                        parcel.writeInt(1);
                        ((NetworkTemplate)parcelable).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeLong((long)var2_7);
                    parcel.writeLong((long)var4_8);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        NetworkStats networkStats = Stub.getDefaultImpl().getDeviceSummaryForNetwork((NetworkTemplate)parcelable, (long)var2_7, (long)var4_8);
                        parcel2.recycle();
                        parcel.recycle();
                        return networkStats;
                    }
                    parcel2.readException();
                    if (parcel2.readInt() != 0) {
                        NetworkStats networkStats = NetworkStats.CREATOR.createFromParcel(parcel2);
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
            public NetworkStatsHistory getHistoryForNetwork(NetworkTemplate parcelable, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    void var2_7;
                    void var1_5;
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelable != null) {
                        parcel.writeInt(1);
                        ((NetworkTemplate)parcelable).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt((int)var2_7);
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        NetworkStatsHistory networkStatsHistory = Stub.getDefaultImpl().getHistoryForNetwork((NetworkTemplate)parcelable, (int)var2_7);
                        parcel2.recycle();
                        parcel.recycle();
                        return networkStatsHistory;
                    }
                    parcel2.readException();
                    if (parcel2.readInt() != 0) {
                        NetworkStatsHistory networkStatsHistory = NetworkStatsHistory.CREATOR.createFromParcel(parcel2);
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
            public NetworkStatsHistory getHistoryForUid(NetworkTemplate parcelable, int n, int n2, int n3, int n4) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    void var3_8;
                    void var2_7;
                    void var4_9;
                    void var5_10;
                    void var1_5;
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelable != null) {
                        parcel.writeInt(1);
                        ((NetworkTemplate)parcelable).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt((int)var2_7);
                    parcel.writeInt((int)var3_8);
                    parcel.writeInt((int)var4_9);
                    parcel.writeInt((int)var5_10);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        NetworkStatsHistory networkStatsHistory = Stub.getDefaultImpl().getHistoryForUid((NetworkTemplate)parcelable, (int)var2_7, (int)var3_8, (int)var4_9, (int)var5_10);
                        parcel2.recycle();
                        parcel.recycle();
                        return networkStatsHistory;
                    }
                    parcel2.readException();
                    if (parcel2.readInt() != 0) {
                        NetworkStatsHistory networkStatsHistory = NetworkStatsHistory.CREATOR.createFromParcel(parcel2);
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
             * Loose catch block
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public NetworkStatsHistory getHistoryIntervalForUid(NetworkTemplate parcelable, int n, int n2, int n3, int n4, long l, long l2) throws RemoteException {
                Parcel parcel;
                void var1_9;
                Parcel parcel2;
                block12 : {
                    void var2_10;
                    block11 : {
                        parcel2 = Parcel.obtain();
                        parcel = Parcel.obtain();
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (parcelable != null) {
                            parcel2.writeInt(1);
                            ((NetworkTemplate)parcelable).writeToParcel(parcel2, 0);
                            break block11;
                        }
                        parcel2.writeInt(0);
                    }
                    try {
                        parcel2.writeInt((int)var2_10);
                    }
                    catch (Throwable throwable) {
                        break block12;
                    }
                    try {
                        void var5_13;
                        void var8_15;
                        void var4_12;
                        void var1_5;
                        void var6_14;
                        void var3_11;
                        parcel2.writeInt((int)var3_11);
                        parcel2.writeInt((int)var4_12);
                        parcel2.writeInt((int)var5_13);
                        parcel2.writeLong((long)var6_14);
                        parcel2.writeLong((long)var8_15);
                        if (!this.mRemote.transact(6, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            NetworkStatsHistory networkStatsHistory = Stub.getDefaultImpl().getHistoryIntervalForUid((NetworkTemplate)parcelable, (int)var2_10, (int)var3_11, (int)var4_12, (int)var5_13, (long)var6_14, (long)var8_15);
                            parcel.recycle();
                            parcel2.recycle();
                            return networkStatsHistory;
                        }
                        parcel.readException();
                        if (parcel.readInt() != 0) {
                            NetworkStatsHistory networkStatsHistory = NetworkStatsHistory.CREATOR.createFromParcel(parcel);
                        } else {
                            Object var1_4 = null;
                        }
                        parcel.recycle();
                        parcel2.recycle();
                        return var1_5;
                    }
                    catch (Throwable throwable) {}
                    break block12;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                throw var1_9;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public int[] getRelevantUids() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(7, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int[] arrn = Stub.getDefaultImpl().getRelevantUids();
                        return arrn;
                    }
                    parcel2.readException();
                    int[] arrn = parcel2.createIntArray();
                    return arrn;
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
            public NetworkStats getSummaryForAllUid(NetworkTemplate parcelable, long l, long l2, boolean bl) throws RemoteException {
                void var1_10;
                Parcel parcel;
                Parcel parcel2;
                block15 : {
                    void var4_12;
                    void var6_13;
                    int n;
                    void var2_11;
                    block14 : {
                        parcel2 = Parcel.obtain();
                        parcel = Parcel.obtain();
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        n = 1;
                        if (parcelable != null) {
                            parcel2.writeInt(1);
                            ((NetworkTemplate)parcelable).writeToParcel(parcel2, 0);
                            break block14;
                        }
                        parcel2.writeInt(0);
                    }
                    try {
                        parcel2.writeLong((long)var2_11);
                    }
                    catch (Throwable throwable) {
                        break block15;
                    }
                    try {
                        parcel2.writeLong((long)var4_12);
                        if (var6_13 == false) {
                            n = 0;
                        }
                        parcel2.writeInt(n);
                    }
                    catch (Throwable throwable) {}
                    try {
                        void var1_5;
                        if (!this.mRemote.transact(4, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            NetworkStats networkStats = Stub.getDefaultImpl().getSummaryForAllUid((NetworkTemplate)parcelable, (long)var2_11, (long)var4_12, (boolean)var6_13);
                            parcel.recycle();
                            parcel2.recycle();
                            return networkStats;
                        }
                        parcel.readException();
                        if (parcel.readInt() != 0) {
                            NetworkStats networkStats = NetworkStats.CREATOR.createFromParcel(parcel);
                        } else {
                            Object var1_4 = null;
                        }
                        parcel.recycle();
                        parcel2.recycle();
                        return var1_5;
                    }
                    catch (Throwable throwable) {}
                    break block15;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                throw var1_10;
            }

            /*
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public NetworkStats getSummaryForNetwork(NetworkTemplate parcelable, long l, long l2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    void var2_7;
                    void var4_8;
                    void var1_5;
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelable != null) {
                        parcel.writeInt(1);
                        ((NetworkTemplate)parcelable).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeLong((long)var2_7);
                    parcel.writeLong((long)var4_8);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        NetworkStats networkStats = Stub.getDefaultImpl().getSummaryForNetwork((NetworkTemplate)parcelable, (long)var2_7, (long)var4_8);
                        parcel2.recycle();
                        parcel.recycle();
                        return networkStats;
                    }
                    parcel2.readException();
                    if (parcel2.readInt() != 0) {
                        NetworkStats networkStats = NetworkStats.CREATOR.createFromParcel(parcel2);
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
        }

    }

}

