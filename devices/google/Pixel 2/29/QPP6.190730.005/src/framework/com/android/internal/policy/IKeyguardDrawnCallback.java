/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.policy;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IKeyguardDrawnCallback
extends IInterface {
    public void onDrawn() throws RemoteException;

    public static class Default
    implements IKeyguardDrawnCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onDrawn() throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IKeyguardDrawnCallback {
        private static final String DESCRIPTOR = "com.android.internal.policy.IKeyguardDrawnCallback";
        static final int TRANSACTION_onDrawn = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IKeyguardDrawnCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IKeyguardDrawnCallback) {
                return (IKeyguardDrawnCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IKeyguardDrawnCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onDrawn";
        }

        public static boolean setDefaultImpl(IKeyguardDrawnCallback iKeyguardDrawnCallback) {
            if (Proxy.sDefaultImpl == null && iKeyguardDrawnCallback != null) {
                Proxy.sDefaultImpl = iKeyguardDrawnCallback;
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
            this.onDrawn();
            return true;
        }

        private static class Proxy
        implements IKeyguardDrawnCallback {
            public static IKeyguardDrawnCallback sDefaultImpl;
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
            public void onDrawn() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onDrawn();
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

