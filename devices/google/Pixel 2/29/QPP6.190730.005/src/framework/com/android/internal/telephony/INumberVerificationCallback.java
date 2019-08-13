/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface INumberVerificationCallback
extends IInterface {
    public void onCallReceived(String var1) throws RemoteException;

    public void onVerificationFailed(int var1) throws RemoteException;

    public static class Default
    implements INumberVerificationCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onCallReceived(String string2) throws RemoteException {
        }

        @Override
        public void onVerificationFailed(int n) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements INumberVerificationCallback {
        private static final String DESCRIPTOR = "com.android.internal.telephony.INumberVerificationCallback";
        static final int TRANSACTION_onCallReceived = 1;
        static final int TRANSACTION_onVerificationFailed = 2;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static INumberVerificationCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof INumberVerificationCallback) {
                return (INumberVerificationCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static INumberVerificationCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    return null;
                }
                return "onVerificationFailed";
            }
            return "onCallReceived";
        }

        public static boolean setDefaultImpl(INumberVerificationCallback iNumberVerificationCallback) {
            if (Proxy.sDefaultImpl == null && iNumberVerificationCallback != null) {
                Proxy.sDefaultImpl = iNumberVerificationCallback;
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
                this.onVerificationFailed(parcel.readInt());
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            this.onCallReceived(parcel.readString());
            return true;
        }

        private static class Proxy
        implements INumberVerificationCallback {
            public static INumberVerificationCallback sDefaultImpl;
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
            public void onCallReceived(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onCallReceived(string2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onVerificationFailed(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onVerificationFailed(n);
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

