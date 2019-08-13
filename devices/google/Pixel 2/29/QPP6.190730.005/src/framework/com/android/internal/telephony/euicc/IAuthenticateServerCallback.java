/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.euicc;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IAuthenticateServerCallback
extends IInterface {
    public void onComplete(int var1, byte[] var2) throws RemoteException;

    public static class Default
    implements IAuthenticateServerCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onComplete(int n, byte[] arrby) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IAuthenticateServerCallback {
        private static final String DESCRIPTOR = "com.android.internal.telephony.euicc.IAuthenticateServerCallback";
        static final int TRANSACTION_onComplete = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IAuthenticateServerCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IAuthenticateServerCallback) {
                return (IAuthenticateServerCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IAuthenticateServerCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onComplete";
        }

        public static boolean setDefaultImpl(IAuthenticateServerCallback iAuthenticateServerCallback) {
            if (Proxy.sDefaultImpl == null && iAuthenticateServerCallback != null) {
                Proxy.sDefaultImpl = iAuthenticateServerCallback;
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
            this.onComplete(parcel.readInt(), parcel.createByteArray());
            return true;
        }

        private static class Proxy
        implements IAuthenticateServerCallback {
            public static IAuthenticateServerCallback sDefaultImpl;
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
            public void onComplete(int n, byte[] arrby) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeByteArray(arrby);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onComplete(n, arrby);
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

