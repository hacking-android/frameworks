/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface INetworkActivityListener
extends IInterface {
    public void onNetworkActive() throws RemoteException;

    public static class Default
    implements INetworkActivityListener {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onNetworkActive() throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements INetworkActivityListener {
        private static final String DESCRIPTOR = "android.os.INetworkActivityListener";
        static final int TRANSACTION_onNetworkActive = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static INetworkActivityListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof INetworkActivityListener) {
                return (INetworkActivityListener)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static INetworkActivityListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onNetworkActive";
        }

        public static boolean setDefaultImpl(INetworkActivityListener iNetworkActivityListener) {
            if (Proxy.sDefaultImpl == null && iNetworkActivityListener != null) {
                Proxy.sDefaultImpl = iNetworkActivityListener;
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
            this.onNetworkActive();
            return true;
        }

        private static class Proxy
        implements INetworkActivityListener {
            public static INetworkActivityListener sDefaultImpl;
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
            public void onNetworkActive() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onNetworkActive();
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

