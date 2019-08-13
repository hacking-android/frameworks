/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi.aware;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IWifiAwareEventCallback
extends IInterface {
    public void onConnectFail(int var1) throws RemoteException;

    public void onConnectSuccess(int var1) throws RemoteException;

    public void onIdentityChanged(byte[] var1) throws RemoteException;

    public static class Default
    implements IWifiAwareEventCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onConnectFail(int n) throws RemoteException {
        }

        @Override
        public void onConnectSuccess(int n) throws RemoteException {
        }

        @Override
        public void onIdentityChanged(byte[] arrby) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IWifiAwareEventCallback {
        private static final String DESCRIPTOR = "android.net.wifi.aware.IWifiAwareEventCallback";
        static final int TRANSACTION_onConnectFail = 2;
        static final int TRANSACTION_onConnectSuccess = 1;
        static final int TRANSACTION_onIdentityChanged = 3;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IWifiAwareEventCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IWifiAwareEventCallback) {
                return (IWifiAwareEventCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IWifiAwareEventCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        return null;
                    }
                    return "onIdentityChanged";
                }
                return "onConnectFail";
            }
            return "onConnectSuccess";
        }

        public static boolean setDefaultImpl(IWifiAwareEventCallback iWifiAwareEventCallback) {
            if (Proxy.sDefaultImpl == null && iWifiAwareEventCallback != null) {
                Proxy.sDefaultImpl = iWifiAwareEventCallback;
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
                if (n != 2) {
                    if (n != 3) {
                        if (n != 1598968902) {
                            return super.onTransact(n, parcel, parcel2, n2);
                        }
                        parcel2.writeString(DESCRIPTOR);
                        return true;
                    }
                    parcel.enforceInterface(DESCRIPTOR);
                    this.onIdentityChanged(parcel.createByteArray());
                    return true;
                }
                parcel.enforceInterface(DESCRIPTOR);
                this.onConnectFail(parcel.readInt());
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            this.onConnectSuccess(parcel.readInt());
            return true;
        }

        private static class Proxy
        implements IWifiAwareEventCallback {
            public static IWifiAwareEventCallback sDefaultImpl;
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
            public void onConnectFail(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onConnectFail(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onConnectSuccess(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onConnectSuccess(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onIdentityChanged(byte[] arrby) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeByteArray(arrby);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onIdentityChanged(arrby);
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

