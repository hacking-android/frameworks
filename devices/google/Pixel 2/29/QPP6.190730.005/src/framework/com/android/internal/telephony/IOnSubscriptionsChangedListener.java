/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IOnSubscriptionsChangedListener
extends IInterface {
    public void onSubscriptionsChanged() throws RemoteException;

    public static class Default
    implements IOnSubscriptionsChangedListener {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onSubscriptionsChanged() throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IOnSubscriptionsChangedListener {
        private static final String DESCRIPTOR = "com.android.internal.telephony.IOnSubscriptionsChangedListener";
        static final int TRANSACTION_onSubscriptionsChanged = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IOnSubscriptionsChangedListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IOnSubscriptionsChangedListener) {
                return (IOnSubscriptionsChangedListener)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IOnSubscriptionsChangedListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onSubscriptionsChanged";
        }

        public static boolean setDefaultImpl(IOnSubscriptionsChangedListener iOnSubscriptionsChangedListener) {
            if (Proxy.sDefaultImpl == null && iOnSubscriptionsChangedListener != null) {
                Proxy.sDefaultImpl = iOnSubscriptionsChangedListener;
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
            this.onSubscriptionsChanged();
            return true;
        }

        private static class Proxy
        implements IOnSubscriptionsChangedListener {
            public static IOnSubscriptionsChangedListener sDefaultImpl;
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
            public void onSubscriptionsChanged() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onSubscriptionsChanged();
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

