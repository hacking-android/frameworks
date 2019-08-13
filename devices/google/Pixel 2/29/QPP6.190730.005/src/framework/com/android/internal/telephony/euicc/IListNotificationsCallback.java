/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.euicc;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.telephony.euicc.EuiccNotification;

public interface IListNotificationsCallback
extends IInterface {
    public void onComplete(int var1, EuiccNotification[] var2) throws RemoteException;

    public static class Default
    implements IListNotificationsCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onComplete(int n, EuiccNotification[] arreuiccNotification) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IListNotificationsCallback {
        private static final String DESCRIPTOR = "com.android.internal.telephony.euicc.IListNotificationsCallback";
        static final int TRANSACTION_onComplete = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IListNotificationsCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IListNotificationsCallback) {
                return (IListNotificationsCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IListNotificationsCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onComplete";
        }

        public static boolean setDefaultImpl(IListNotificationsCallback iListNotificationsCallback) {
            if (Proxy.sDefaultImpl == null && iListNotificationsCallback != null) {
                Proxy.sDefaultImpl = iListNotificationsCallback;
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
            this.onComplete(parcel.readInt(), parcel.createTypedArray(EuiccNotification.CREATOR));
            return true;
        }

        private static class Proxy
        implements IListNotificationsCallback {
            public static IListNotificationsCallback sDefaultImpl;
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
            public void onComplete(int n, EuiccNotification[] arreuiccNotification) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeTypedArray((Parcelable[])arreuiccNotification, 0);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onComplete(n, arreuiccNotification);
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

