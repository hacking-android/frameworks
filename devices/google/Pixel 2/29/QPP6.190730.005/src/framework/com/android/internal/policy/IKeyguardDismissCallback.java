/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.policy;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IKeyguardDismissCallback
extends IInterface {
    public void onDismissCancelled() throws RemoteException;

    public void onDismissError() throws RemoteException;

    public void onDismissSucceeded() throws RemoteException;

    public static class Default
    implements IKeyguardDismissCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onDismissCancelled() throws RemoteException {
        }

        @Override
        public void onDismissError() throws RemoteException {
        }

        @Override
        public void onDismissSucceeded() throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IKeyguardDismissCallback {
        private static final String DESCRIPTOR = "com.android.internal.policy.IKeyguardDismissCallback";
        static final int TRANSACTION_onDismissCancelled = 3;
        static final int TRANSACTION_onDismissError = 1;
        static final int TRANSACTION_onDismissSucceeded = 2;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IKeyguardDismissCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IKeyguardDismissCallback) {
                return (IKeyguardDismissCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IKeyguardDismissCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        return null;
                    }
                    return "onDismissCancelled";
                }
                return "onDismissSucceeded";
            }
            return "onDismissError";
        }

        public static boolean setDefaultImpl(IKeyguardDismissCallback iKeyguardDismissCallback) {
            if (Proxy.sDefaultImpl == null && iKeyguardDismissCallback != null) {
                Proxy.sDefaultImpl = iKeyguardDismissCallback;
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
                    this.onDismissCancelled();
                    return true;
                }
                parcel.enforceInterface(DESCRIPTOR);
                this.onDismissSucceeded();
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            this.onDismissError();
            return true;
        }

        private static class Proxy
        implements IKeyguardDismissCallback {
            public static IKeyguardDismissCallback sDefaultImpl;
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
            public void onDismissCancelled() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onDismissCancelled();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onDismissError() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onDismissError();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onDismissSucceeded() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onDismissSucceeded();
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

