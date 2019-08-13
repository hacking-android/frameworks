/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface ISoftApCallback
extends IInterface {
    public void onNumClientsChanged(int var1) throws RemoteException;

    public void onStateChanged(int var1, int var2) throws RemoteException;

    public static class Default
    implements ISoftApCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onNumClientsChanged(int n) throws RemoteException {
        }

        @Override
        public void onStateChanged(int n, int n2) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ISoftApCallback {
        private static final String DESCRIPTOR = "android.net.wifi.ISoftApCallback";
        static final int TRANSACTION_onNumClientsChanged = 2;
        static final int TRANSACTION_onStateChanged = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ISoftApCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ISoftApCallback) {
                return (ISoftApCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ISoftApCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    return null;
                }
                return "onNumClientsChanged";
            }
            return "onStateChanged";
        }

        public static boolean setDefaultImpl(ISoftApCallback iSoftApCallback) {
            if (Proxy.sDefaultImpl == null && iSoftApCallback != null) {
                Proxy.sDefaultImpl = iSoftApCallback;
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
                    if (n != 1598968902) {
                        return super.onTransact(n, parcel, parcel2, n2);
                    }
                    parcel2.writeString(DESCRIPTOR);
                    return true;
                }
                parcel.enforceInterface(DESCRIPTOR);
                this.onNumClientsChanged(parcel.readInt());
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            this.onStateChanged(parcel.readInt(), parcel.readInt());
            return true;
        }

        private static class Proxy
        implements ISoftApCallback {
            public static ISoftApCallback sDefaultImpl;
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
            public void onNumClientsChanged(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onNumClientsChanged(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onStateChanged(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onStateChanged(n, n2);
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

