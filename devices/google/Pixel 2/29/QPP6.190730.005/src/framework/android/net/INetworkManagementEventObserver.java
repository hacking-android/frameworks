/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.net.LinkAddress;
import android.net.RouteInfo;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface INetworkManagementEventObserver
extends IInterface {
    public void addressRemoved(String var1, LinkAddress var2) throws RemoteException;

    public void addressUpdated(String var1, LinkAddress var2) throws RemoteException;

    public void interfaceAdded(String var1) throws RemoteException;

    public void interfaceClassDataActivityChanged(String var1, boolean var2, long var3) throws RemoteException;

    public void interfaceDnsServerInfo(String var1, long var2, String[] var4) throws RemoteException;

    public void interfaceLinkStateChanged(String var1, boolean var2) throws RemoteException;

    public void interfaceRemoved(String var1) throws RemoteException;

    public void interfaceStatusChanged(String var1, boolean var2) throws RemoteException;

    public void limitReached(String var1, String var2) throws RemoteException;

    public void routeRemoved(RouteInfo var1) throws RemoteException;

    public void routeUpdated(RouteInfo var1) throws RemoteException;

    public static class Default
    implements INetworkManagementEventObserver {
        @Override
        public void addressRemoved(String string2, LinkAddress linkAddress) throws RemoteException {
        }

        @Override
        public void addressUpdated(String string2, LinkAddress linkAddress) throws RemoteException {
        }

        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void interfaceAdded(String string2) throws RemoteException {
        }

        @Override
        public void interfaceClassDataActivityChanged(String string2, boolean bl, long l) throws RemoteException {
        }

        @Override
        public void interfaceDnsServerInfo(String string2, long l, String[] arrstring) throws RemoteException {
        }

        @Override
        public void interfaceLinkStateChanged(String string2, boolean bl) throws RemoteException {
        }

        @Override
        public void interfaceRemoved(String string2) throws RemoteException {
        }

        @Override
        public void interfaceStatusChanged(String string2, boolean bl) throws RemoteException {
        }

        @Override
        public void limitReached(String string2, String string3) throws RemoteException {
        }

        @Override
        public void routeRemoved(RouteInfo routeInfo) throws RemoteException {
        }

        @Override
        public void routeUpdated(RouteInfo routeInfo) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements INetworkManagementEventObserver {
        private static final String DESCRIPTOR = "android.net.INetworkManagementEventObserver";
        static final int TRANSACTION_addressRemoved = 6;
        static final int TRANSACTION_addressUpdated = 5;
        static final int TRANSACTION_interfaceAdded = 3;
        static final int TRANSACTION_interfaceClassDataActivityChanged = 8;
        static final int TRANSACTION_interfaceDnsServerInfo = 9;
        static final int TRANSACTION_interfaceLinkStateChanged = 2;
        static final int TRANSACTION_interfaceRemoved = 4;
        static final int TRANSACTION_interfaceStatusChanged = 1;
        static final int TRANSACTION_limitReached = 7;
        static final int TRANSACTION_routeRemoved = 11;
        static final int TRANSACTION_routeUpdated = 10;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static INetworkManagementEventObserver asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof INetworkManagementEventObserver) {
                return (INetworkManagementEventObserver)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static INetworkManagementEventObserver getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 11: {
                    return "routeRemoved";
                }
                case 10: {
                    return "routeUpdated";
                }
                case 9: {
                    return "interfaceDnsServerInfo";
                }
                case 8: {
                    return "interfaceClassDataActivityChanged";
                }
                case 7: {
                    return "limitReached";
                }
                case 6: {
                    return "addressRemoved";
                }
                case 5: {
                    return "addressUpdated";
                }
                case 4: {
                    return "interfaceRemoved";
                }
                case 3: {
                    return "interfaceAdded";
                }
                case 2: {
                    return "interfaceLinkStateChanged";
                }
                case 1: 
            }
            return "interfaceStatusChanged";
        }

        public static boolean setDefaultImpl(INetworkManagementEventObserver iNetworkManagementEventObserver) {
            if (Proxy.sDefaultImpl == null && iNetworkManagementEventObserver != null) {
                Proxy.sDefaultImpl = iNetworkManagementEventObserver;
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
        public boolean onTransact(int n, Parcel object, Parcel object2, int n2) throws RemoteException {
            if (n != 1598968902) {
                boolean bl = false;
                boolean bl2 = false;
                boolean bl3 = false;
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, (Parcel)object2, n2);
                    }
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? RouteInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        this.routeRemoved((RouteInfo)object);
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? RouteInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        this.routeUpdated((RouteInfo)object);
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.interfaceDnsServerInfo(((Parcel)object).readString(), ((Parcel)object).readLong(), ((Parcel)object).createStringArray());
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readString();
                        if (((Parcel)object).readInt() != 0) {
                            bl3 = true;
                        }
                        this.interfaceClassDataActivityChanged((String)object2, bl3, ((Parcel)object).readLong());
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.limitReached(((Parcel)object).readString(), ((Parcel)object).readString());
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? LinkAddress.CREATOR.createFromParcel((Parcel)object) : null;
                        this.addressRemoved((String)object2, (LinkAddress)object);
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? LinkAddress.CREATOR.createFromParcel((Parcel)object) : null;
                        this.addressUpdated((String)object2, (LinkAddress)object);
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.interfaceRemoved(((Parcel)object).readString());
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.interfaceAdded(((Parcel)object).readString());
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readString();
                        bl3 = bl;
                        if (((Parcel)object).readInt() != 0) {
                            bl3 = true;
                        }
                        this.interfaceLinkStateChanged((String)object2, bl3);
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                object2 = ((Parcel)object).readString();
                bl3 = bl2;
                if (((Parcel)object).readInt() != 0) {
                    bl3 = true;
                }
                this.interfaceStatusChanged((String)object2, bl3);
                return true;
            }
            ((Parcel)object2).writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements INetworkManagementEventObserver {
            public static INetworkManagementEventObserver sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public void addressRemoved(String string2, LinkAddress linkAddress) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (linkAddress != null) {
                        parcel.writeInt(1);
                        linkAddress.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(6, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addressRemoved(string2, linkAddress);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void addressUpdated(String string2, LinkAddress linkAddress) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (linkAddress != null) {
                        parcel.writeInt(1);
                        linkAddress.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(5, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addressUpdated(string2, linkAddress);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public void interfaceAdded(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().interfaceAdded(string2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void interfaceClassDataActivityChanged(String string2, boolean bl, long l) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeString(string2);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    parcel.writeLong(l);
                    if (!this.mRemote.transact(8, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().interfaceClassDataActivityChanged(string2, bl, l);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void interfaceDnsServerInfo(String string2, long l, String[] arrstring) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeLong(l);
                    parcel.writeStringArray(arrstring);
                    if (!this.mRemote.transact(9, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().interfaceDnsServerInfo(string2, l, arrstring);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void interfaceLinkStateChanged(String string2, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeString(string2);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().interfaceLinkStateChanged(string2, bl);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void interfaceRemoved(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().interfaceRemoved(string2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void interfaceStatusChanged(String string2, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeString(string2);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().interfaceStatusChanged(string2, bl);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void limitReached(String string2, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(7, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().limitReached(string2, string3);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void routeRemoved(RouteInfo routeInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (routeInfo != null) {
                        parcel.writeInt(1);
                        routeInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(11, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().routeRemoved(routeInfo);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void routeUpdated(RouteInfo routeInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (routeInfo != null) {
                        parcel.writeInt(1);
                        routeInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(10, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().routeUpdated(routeInfo);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }
        }

    }

}

