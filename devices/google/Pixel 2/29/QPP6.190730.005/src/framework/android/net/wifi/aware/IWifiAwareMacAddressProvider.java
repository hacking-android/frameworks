/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi.aware;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.HashMap;
import java.util.Map;

public interface IWifiAwareMacAddressProvider
extends IInterface {
    public void macAddress(Map var1) throws RemoteException;

    public static class Default
    implements IWifiAwareMacAddressProvider {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void macAddress(Map map) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IWifiAwareMacAddressProvider {
        private static final String DESCRIPTOR = "android.net.wifi.aware.IWifiAwareMacAddressProvider";
        static final int TRANSACTION_macAddress = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IWifiAwareMacAddressProvider asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IWifiAwareMacAddressProvider) {
                return (IWifiAwareMacAddressProvider)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IWifiAwareMacAddressProvider getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "macAddress";
        }

        public static boolean setDefaultImpl(IWifiAwareMacAddressProvider iWifiAwareMacAddressProvider) {
            if (Proxy.sDefaultImpl == null && iWifiAwareMacAddressProvider != null) {
                Proxy.sDefaultImpl = iWifiAwareMacAddressProvider;
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
        public boolean onTransact(int n, Parcel parcel, Parcel parcel2, int n2) throws RemoteException {
            if (n != 1) {
                if (n != 1598968902) {
                    return super.onTransact(n, parcel, parcel2, n2);
                }
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            this.macAddress(parcel.readHashMap(this.getClass().getClassLoader()));
            return true;
        }

        private static class Proxy
        implements IWifiAwareMacAddressProvider {
            public static IWifiAwareMacAddressProvider sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public void macAddress(Map map) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeMap(map);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().macAddress(map);
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

