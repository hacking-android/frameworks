/*
 * Decompiled with CFR 0.145.
 */
package com.android.net;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IProxyPortListener
extends IInterface {
    public void setProxyPort(int var1) throws RemoteException;

    public static class Default
    implements IProxyPortListener {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void setProxyPort(int n) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IProxyPortListener {
        private static final String DESCRIPTOR = "com.android.net.IProxyPortListener";
        static final int TRANSACTION_setProxyPort = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IProxyPortListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IProxyPortListener) {
                return (IProxyPortListener)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IProxyPortListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "setProxyPort";
        }

        public static boolean setDefaultImpl(IProxyPortListener iProxyPortListener) {
            if (Proxy.sDefaultImpl == null && iProxyPortListener != null) {
                Proxy.sDefaultImpl = iProxyPortListener;
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
            this.setProxyPort(parcel.readInt());
            return true;
        }

        private static class Proxy
        implements IProxyPortListener {
            public static IProxyPortListener sDefaultImpl;
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
            public void setProxyPort(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setProxyPort(n);
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

