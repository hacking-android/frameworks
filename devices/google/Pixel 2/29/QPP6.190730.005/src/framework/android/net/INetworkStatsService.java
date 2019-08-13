/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.annotation.UnsupportedAppUsage;
import android.net.DataUsageRequest;
import android.net.INetworkStatsSession;
import android.net.Network;
import android.net.NetworkState;
import android.net.NetworkStats;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Messenger;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.android.internal.net.VpnInfo;

public interface INetworkStatsService
extends IInterface {
    @UnsupportedAppUsage
    public void forceUpdate() throws RemoteException;

    public void forceUpdateIfaces(Network[] var1, VpnInfo[] var2, NetworkState[] var3, String var4) throws RemoteException;

    @UnsupportedAppUsage
    public NetworkStats getDataLayerSnapshotForUid(int var1) throws RemoteException;

    public NetworkStats getDetailedUidStats(String[] var1) throws RemoteException;

    public long getIfaceStats(String var1, int var2) throws RemoteException;

    @UnsupportedAppUsage
    public String[] getMobileIfaces() throws RemoteException;

    public long getTotalStats(int var1) throws RemoteException;

    public long getUidStats(int var1, int var2) throws RemoteException;

    public void incrementOperationCount(int var1, int var2, int var3) throws RemoteException;

    @UnsupportedAppUsage
    public INetworkStatsSession openSession() throws RemoteException;

    @UnsupportedAppUsage
    public INetworkStatsSession openSessionForUsageStats(int var1, String var2) throws RemoteException;

    public DataUsageRequest registerUsageCallback(String var1, DataUsageRequest var2, Messenger var3, IBinder var4) throws RemoteException;

    public void unregisterUsageRequest(DataUsageRequest var1) throws RemoteException;

    public static class Default
    implements INetworkStatsService {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void forceUpdate() throws RemoteException {
        }

        @Override
        public void forceUpdateIfaces(Network[] arrnetwork, VpnInfo[] arrvpnInfo, NetworkState[] arrnetworkState, String string2) throws RemoteException {
        }

        @Override
        public NetworkStats getDataLayerSnapshotForUid(int n) throws RemoteException {
            return null;
        }

        @Override
        public NetworkStats getDetailedUidStats(String[] arrstring) throws RemoteException {
            return null;
        }

        @Override
        public long getIfaceStats(String string2, int n) throws RemoteException {
            return 0L;
        }

        @Override
        public String[] getMobileIfaces() throws RemoteException {
            return null;
        }

        @Override
        public long getTotalStats(int n) throws RemoteException {
            return 0L;
        }

        @Override
        public long getUidStats(int n, int n2) throws RemoteException {
            return 0L;
        }

        @Override
        public void incrementOperationCount(int n, int n2, int n3) throws RemoteException {
        }

        @Override
        public INetworkStatsSession openSession() throws RemoteException {
            return null;
        }

        @Override
        public INetworkStatsSession openSessionForUsageStats(int n, String string2) throws RemoteException {
            return null;
        }

        @Override
        public DataUsageRequest registerUsageCallback(String string2, DataUsageRequest dataUsageRequest, Messenger messenger, IBinder iBinder) throws RemoteException {
            return null;
        }

        @Override
        public void unregisterUsageRequest(DataUsageRequest dataUsageRequest) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements INetworkStatsService {
        private static final String DESCRIPTOR = "android.net.INetworkStatsService";
        static final int TRANSACTION_forceUpdate = 8;
        static final int TRANSACTION_forceUpdateIfaces = 7;
        static final int TRANSACTION_getDataLayerSnapshotForUid = 3;
        static final int TRANSACTION_getDetailedUidStats = 4;
        static final int TRANSACTION_getIfaceStats = 12;
        static final int TRANSACTION_getMobileIfaces = 5;
        static final int TRANSACTION_getTotalStats = 13;
        static final int TRANSACTION_getUidStats = 11;
        static final int TRANSACTION_incrementOperationCount = 6;
        static final int TRANSACTION_openSession = 1;
        static final int TRANSACTION_openSessionForUsageStats = 2;
        static final int TRANSACTION_registerUsageCallback = 9;
        static final int TRANSACTION_unregisterUsageRequest = 10;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static INetworkStatsService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof INetworkStatsService) {
                return (INetworkStatsService)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static INetworkStatsService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 13: {
                    return "getTotalStats";
                }
                case 12: {
                    return "getIfaceStats";
                }
                case 11: {
                    return "getUidStats";
                }
                case 10: {
                    return "unregisterUsageRequest";
                }
                case 9: {
                    return "registerUsageCallback";
                }
                case 8: {
                    return "forceUpdate";
                }
                case 7: {
                    return "forceUpdateIfaces";
                }
                case 6: {
                    return "incrementOperationCount";
                }
                case 5: {
                    return "getMobileIfaces";
                }
                case 4: {
                    return "getDetailedUidStats";
                }
                case 3: {
                    return "getDataLayerSnapshotForUid";
                }
                case 2: {
                    return "openSessionForUsageStats";
                }
                case 1: 
            }
            return "openSession";
        }

        public static boolean setDefaultImpl(INetworkStatsService iNetworkStatsService) {
            if (Proxy.sDefaultImpl == null && iNetworkStatsService != null) {
                Proxy.sDefaultImpl = iNetworkStatsService;
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
                Object object2 = null;
                Object object3 = null;
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, parcel, n2);
                    }
                    case 13: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        long l = this.getTotalStats(((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeLong(l);
                        return true;
                    }
                    case 12: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        long l = this.getIfaceStats(((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeLong(l);
                        return true;
                    }
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        long l = this.getUidStats(((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeLong(l);
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? DataUsageRequest.CREATOR.createFromParcel((Parcel)object) : null;
                        this.unregisterUsageRequest((DataUsageRequest)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string2 = ((Parcel)object).readString();
                        object3 = ((Parcel)object).readInt() != 0 ? DataUsageRequest.CREATOR.createFromParcel((Parcel)object) : null;
                        object2 = ((Parcel)object).readInt() != 0 ? Messenger.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.registerUsageCallback(string2, (DataUsageRequest)object3, (Messenger)object2, ((Parcel)object).readStrongBinder());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((DataUsageRequest)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.forceUpdate();
                        parcel.writeNoException();
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.forceUpdateIfaces(((Parcel)object).createTypedArray(Network.CREATOR), ((Parcel)object).createTypedArray(VpnInfo.CREATOR), ((Parcel)object).createTypedArray(NetworkState.CREATOR), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.incrementOperationCount(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getMobileIfaces();
                        parcel.writeNoException();
                        parcel.writeStringArray((String[])object);
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getDetailedUidStats(((Parcel)object).createStringArray());
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
                        object = this.getDataLayerSnapshotForUid(((Parcel)object).readInt());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((NetworkStats)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = this.openSessionForUsageStats(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        object = object3;
                        if (object2 != null) {
                            object = object2.asBinder();
                        }
                        parcel.writeStrongBinder((IBinder)object);
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                object3 = this.openSession();
                parcel.writeNoException();
                object = object2;
                if (object3 != null) {
                    object = object3.asBinder();
                }
                parcel.writeStrongBinder((IBinder)object);
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements INetworkStatsService {
            public static INetworkStatsService sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void forceUpdate() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(8, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().forceUpdate();
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
            public void forceUpdateIfaces(Network[] arrnetwork, VpnInfo[] arrvpnInfo, NetworkState[] arrnetworkState, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeTypedArray((Parcelable[])arrnetwork, 0);
                    parcel.writeTypedArray((Parcelable[])arrvpnInfo, 0);
                    parcel.writeTypedArray((Parcelable[])arrnetworkState, 0);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(7, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().forceUpdateIfaces(arrnetwork, arrvpnInfo, arrnetworkState, string2);
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
            public NetworkStats getDataLayerSnapshotForUid(int n) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        if (this.mRemote.transact(3, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        NetworkStats networkStats = Stub.getDefaultImpl().getDataLayerSnapshotForUid(n);
                        parcel2.recycle();
                        parcel.recycle();
                        return networkStats;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                NetworkStats networkStats = parcel2.readInt() != 0 ? NetworkStats.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return networkStats;
            }

            @Override
            public NetworkStats getDetailedUidStats(String[] object) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeStringArray((String[])object);
                        if (this.mRemote.transact(4, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().getDetailedUidStats((String[])object);
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
                object = parcel2.readInt() != 0 ? NetworkStats.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return object;
            }

            @Override
            public long getIfaceStats(String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(12, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        long l = Stub.getDefaultImpl().getIfaceStats(string2, n);
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

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public String[] getMobileIfaces() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String[] arrstring = Stub.getDefaultImpl().getMobileIfaces();
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
            public long getTotalStats(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(13, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        long l = Stub.getDefaultImpl().getTotalStats(n);
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
            public long getUidStats(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(11, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        long l = Stub.getDefaultImpl().getUidStats(n, n2);
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
            public void incrementOperationCount(int n, int n2, int n3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    if (!this.mRemote.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().incrementOperationCount(n, n2, n3);
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
            public INetworkStatsSession openSession() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        INetworkStatsSession iNetworkStatsSession = Stub.getDefaultImpl().openSession();
                        return iNetworkStatsSession;
                    }
                    parcel2.readException();
                    INetworkStatsSession iNetworkStatsSession = INetworkStatsSession.Stub.asInterface(parcel2.readStrongBinder());
                    return iNetworkStatsSession;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public INetworkStatsSession openSessionForUsageStats(int n, String object) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString((String)object);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().openSessionForUsageStats(n, (String)object);
                        return object;
                    }
                    parcel2.readException();
                    object = INetworkStatsSession.Stub.asInterface(parcel2.readStrongBinder());
                    return object;
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
            public DataUsageRequest registerUsageCallback(String object, DataUsageRequest dataUsageRequest, Messenger messenger, IBinder iBinder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString((String)object);
                    if (dataUsageRequest != null) {
                        parcel.writeInt(1);
                        dataUsageRequest.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (messenger != null) {
                        parcel.writeInt(1);
                        messenger.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(9, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().registerUsageCallback((String)object, dataUsageRequest, messenger, iBinder);
                        parcel2.recycle();
                        parcel.recycle();
                        return object;
                    }
                    parcel2.readException();
                    object = parcel2.readInt() != 0 ? DataUsageRequest.CREATOR.createFromParcel(parcel2) : null;
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
            public void unregisterUsageRequest(DataUsageRequest dataUsageRequest) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (dataUsageRequest != null) {
                        parcel.writeInt(1);
                        dataUsageRequest.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(10, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregisterUsageRequest(dataUsageRequest);
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

