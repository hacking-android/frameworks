/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi;

import android.net.wifi.WifiConfiguration;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface INetworkRequestUserSelectionCallback
extends IInterface {
    public void reject() throws RemoteException;

    public void select(WifiConfiguration var1) throws RemoteException;

    public static class Default
    implements INetworkRequestUserSelectionCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void reject() throws RemoteException {
        }

        @Override
        public void select(WifiConfiguration wifiConfiguration) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements INetworkRequestUserSelectionCallback {
        private static final String DESCRIPTOR = "android.net.wifi.INetworkRequestUserSelectionCallback";
        static final int TRANSACTION_reject = 2;
        static final int TRANSACTION_select = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static INetworkRequestUserSelectionCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof INetworkRequestUserSelectionCallback) {
                return (INetworkRequestUserSelectionCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static INetworkRequestUserSelectionCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    return null;
                }
                return "reject";
            }
            return "select";
        }

        public static boolean setDefaultImpl(INetworkRequestUserSelectionCallback iNetworkRequestUserSelectionCallback) {
            if (Proxy.sDefaultImpl == null && iNetworkRequestUserSelectionCallback != null) {
                Proxy.sDefaultImpl = iNetworkRequestUserSelectionCallback;
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
            if (n != 1) {
                if (n != 2) {
                    if (n != 1598968902) {
                        return super.onTransact(n, (Parcel)object, parcel, n2);
                    }
                    parcel.writeString(DESCRIPTOR);
                    return true;
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                this.reject();
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            object = ((Parcel)object).readInt() != 0 ? WifiConfiguration.CREATOR.createFromParcel((Parcel)object) : null;
            this.select((WifiConfiguration)object);
            return true;
        }

        private static class Proxy
        implements INetworkRequestUserSelectionCallback {
            public static INetworkRequestUserSelectionCallback sDefaultImpl;
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
            public void reject() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().reject();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void select(WifiConfiguration wifiConfiguration) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (wifiConfiguration != null) {
                        parcel.writeInt(1);
                        wifiConfiguration.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().select(wifiConfiguration);
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

