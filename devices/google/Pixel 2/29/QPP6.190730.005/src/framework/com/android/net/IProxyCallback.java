/*
 * Decompiled with CFR 0.145.
 */
package com.android.net;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IProxyCallback
extends IInterface {
    public void getProxyPort(IBinder var1) throws RemoteException;

    public static class Default
    implements IProxyCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void getProxyPort(IBinder iBinder) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IProxyCallback {
        private static final String DESCRIPTOR = "com.android.net.IProxyCallback";
        static final int TRANSACTION_getProxyPort = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IProxyCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IProxyCallback) {
                return (IProxyCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IProxyCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "getProxyPort";
        }

        public static boolean setDefaultImpl(IProxyCallback iProxyCallback) {
            if (Proxy.sDefaultImpl == null && iProxyCallback != null) {
                Proxy.sDefaultImpl = iProxyCallback;
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
            this.getProxyPort(parcel.readStrongBinder());
            return true;
        }

        private static class Proxy
        implements IProxyCallback {
            public static IProxyCallback sDefaultImpl;
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
            public void getProxyPort(IBinder iBinder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().getProxyPort(iBinder);
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

