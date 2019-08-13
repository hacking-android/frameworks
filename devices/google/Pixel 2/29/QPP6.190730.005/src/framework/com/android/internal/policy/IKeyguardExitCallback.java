/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.policy;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IKeyguardExitCallback
extends IInterface {
    public void onKeyguardExitResult(boolean var1) throws RemoteException;

    public static class Default
    implements IKeyguardExitCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onKeyguardExitResult(boolean bl) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IKeyguardExitCallback {
        private static final String DESCRIPTOR = "com.android.internal.policy.IKeyguardExitCallback";
        static final int TRANSACTION_onKeyguardExitResult = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IKeyguardExitCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IKeyguardExitCallback) {
                return (IKeyguardExitCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IKeyguardExitCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onKeyguardExitResult";
        }

        public static boolean setDefaultImpl(IKeyguardExitCallback iKeyguardExitCallback) {
            if (Proxy.sDefaultImpl == null && iKeyguardExitCallback != null) {
                Proxy.sDefaultImpl = iKeyguardExitCallback;
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
            boolean bl = parcel.readInt() != 0;
            this.onKeyguardExitResult(bl);
            return true;
        }

        private static class Proxy
        implements IKeyguardExitCallback {
            public static IKeyguardExitCallback sDefaultImpl;
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
            public void onKeyguardExitResult(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onKeyguardExitResult(bl);
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

