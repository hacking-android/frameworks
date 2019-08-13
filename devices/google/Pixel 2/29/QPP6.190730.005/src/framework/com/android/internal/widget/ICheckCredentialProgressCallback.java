/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.widget;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface ICheckCredentialProgressCallback
extends IInterface {
    public void onCredentialVerified() throws RemoteException;

    public static class Default
    implements ICheckCredentialProgressCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onCredentialVerified() throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ICheckCredentialProgressCallback {
        private static final String DESCRIPTOR = "com.android.internal.widget.ICheckCredentialProgressCallback";
        static final int TRANSACTION_onCredentialVerified = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ICheckCredentialProgressCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ICheckCredentialProgressCallback) {
                return (ICheckCredentialProgressCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ICheckCredentialProgressCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onCredentialVerified";
        }

        public static boolean setDefaultImpl(ICheckCredentialProgressCallback iCheckCredentialProgressCallback) {
            if (Proxy.sDefaultImpl == null && iCheckCredentialProgressCallback != null) {
                Proxy.sDefaultImpl = iCheckCredentialProgressCallback;
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
            this.onCredentialVerified();
            return true;
        }

        private static class Proxy
        implements ICheckCredentialProgressCallback {
            public static ICheckCredentialProgressCallback sDefaultImpl;
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
            public void onCredentialVerified() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onCredentialVerified();
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

