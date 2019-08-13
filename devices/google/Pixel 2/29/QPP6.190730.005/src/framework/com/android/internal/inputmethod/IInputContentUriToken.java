/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.inputmethod;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IInputContentUriToken
extends IInterface {
    public void release() throws RemoteException;

    public void take() throws RemoteException;

    public static class Default
    implements IInputContentUriToken {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void release() throws RemoteException {
        }

        @Override
        public void take() throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IInputContentUriToken {
        private static final String DESCRIPTOR = "com.android.internal.inputmethod.IInputContentUriToken";
        static final int TRANSACTION_release = 2;
        static final int TRANSACTION_take = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IInputContentUriToken asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IInputContentUriToken) {
                return (IInputContentUriToken)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IInputContentUriToken getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    return null;
                }
                return "release";
            }
            return "take";
        }

        public static boolean setDefaultImpl(IInputContentUriToken iInputContentUriToken) {
            if (Proxy.sDefaultImpl == null && iInputContentUriToken != null) {
                Proxy.sDefaultImpl = iInputContentUriToken;
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
                this.release();
                parcel2.writeNoException();
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            this.take();
            parcel2.writeNoException();
            return true;
        }

        private static class Proxy
        implements IInputContentUriToken {
            public static IInputContentUriToken sDefaultImpl;
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
            public void release() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().release();
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void take() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().take();
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }
        }

    }

}

