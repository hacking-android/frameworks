/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi.hotspot2;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IProvisioningCallback
extends IInterface {
    public void onProvisioningComplete() throws RemoteException;

    public void onProvisioningFailure(int var1) throws RemoteException;

    public void onProvisioningStatus(int var1) throws RemoteException;

    public static class Default
    implements IProvisioningCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onProvisioningComplete() throws RemoteException {
        }

        @Override
        public void onProvisioningFailure(int n) throws RemoteException {
        }

        @Override
        public void onProvisioningStatus(int n) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IProvisioningCallback {
        private static final String DESCRIPTOR = "android.net.wifi.hotspot2.IProvisioningCallback";
        static final int TRANSACTION_onProvisioningComplete = 3;
        static final int TRANSACTION_onProvisioningFailure = 1;
        static final int TRANSACTION_onProvisioningStatus = 2;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IProvisioningCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IProvisioningCallback) {
                return (IProvisioningCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IProvisioningCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        return null;
                    }
                    return "onProvisioningComplete";
                }
                return "onProvisioningStatus";
            }
            return "onProvisioningFailure";
        }

        public static boolean setDefaultImpl(IProvisioningCallback iProvisioningCallback) {
            if (Proxy.sDefaultImpl == null && iProvisioningCallback != null) {
                Proxy.sDefaultImpl = iProvisioningCallback;
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
                    this.onProvisioningComplete();
                    return true;
                }
                parcel.enforceInterface(DESCRIPTOR);
                this.onProvisioningStatus(parcel.readInt());
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            this.onProvisioningFailure(parcel.readInt());
            return true;
        }

        private static class Proxy
        implements IProvisioningCallback {
            public static IProvisioningCallback sDefaultImpl;
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
            public void onProvisioningComplete() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onProvisioningComplete();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onProvisioningFailure(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onProvisioningFailure(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onProvisioningStatus(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onProvisioningStatus(n);
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

