/*
 * Decompiled with CFR 0.145.
 */
package com.android.ims.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IImsEcbmListener
extends IInterface {
    public void enteredECBM() throws RemoteException;

    public void exitedECBM() throws RemoteException;

    public static class Default
    implements IImsEcbmListener {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void enteredECBM() throws RemoteException {
        }

        @Override
        public void exitedECBM() throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IImsEcbmListener {
        private static final String DESCRIPTOR = "com.android.ims.internal.IImsEcbmListener";
        static final int TRANSACTION_enteredECBM = 1;
        static final int TRANSACTION_exitedECBM = 2;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IImsEcbmListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IImsEcbmListener) {
                return (IImsEcbmListener)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IImsEcbmListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    return null;
                }
                return "exitedECBM";
            }
            return "enteredECBM";
        }

        public static boolean setDefaultImpl(IImsEcbmListener iImsEcbmListener) {
            if (Proxy.sDefaultImpl == null && iImsEcbmListener != null) {
                Proxy.sDefaultImpl = iImsEcbmListener;
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
                this.exitedECBM();
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            this.enteredECBM();
            return true;
        }

        private static class Proxy
        implements IImsEcbmListener {
            public static IImsEcbmListener sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void enteredECBM() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().enteredECBM();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void exitedECBM() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().exitedECBM();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }
        }

    }

}

