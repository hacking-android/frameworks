/*
 * Decompiled with CFR 0.145.
 */
package com.android.ims.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IImsStreamMediaSession
extends IInterface {
    public void close() throws RemoteException;

    public static class Default
    implements IImsStreamMediaSession {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void close() throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IImsStreamMediaSession {
        private static final String DESCRIPTOR = "com.android.ims.internal.IImsStreamMediaSession";
        static final int TRANSACTION_close = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IImsStreamMediaSession asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IImsStreamMediaSession) {
                return (IImsStreamMediaSession)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IImsStreamMediaSession getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "close";
        }

        public static boolean setDefaultImpl(IImsStreamMediaSession iImsStreamMediaSession) {
            if (Proxy.sDefaultImpl == null && iImsStreamMediaSession != null) {
                Proxy.sDefaultImpl = iImsStreamMediaSession;
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
            this.close();
            parcel2.writeNoException();
            return true;
        }

        private static class Proxy
        implements IImsStreamMediaSession {
            public static IImsStreamMediaSession sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void close() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().close();
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

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }
        }

    }

}

