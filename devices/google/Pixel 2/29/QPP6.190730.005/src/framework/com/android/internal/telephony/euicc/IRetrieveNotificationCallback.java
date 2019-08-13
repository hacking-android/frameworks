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

public interface IRetrieveNotificationCallback
extends IInterface {
    public void onComplete(int var1, EuiccNotification var2) throws RemoteException;

    public static class Default
    implements IRetrieveNotificationCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onComplete(int n, EuiccNotification euiccNotification) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IRetrieveNotificationCallback {
        private static final String DESCRIPTOR = "com.android.internal.telephony.euicc.IRetrieveNotificationCallback";
        static final int TRANSACTION_onComplete = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IRetrieveNotificationCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IRetrieveNotificationCallback) {
                return (IRetrieveNotificationCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IRetrieveNotificationCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onComplete";
        }

        public static boolean setDefaultImpl(IRetrieveNotificationCallback iRetrieveNotificationCallback) {
            if (Proxy.sDefaultImpl == null && iRetrieveNotificationCallback != null) {
                Proxy.sDefaultImpl = iRetrieveNotificationCallback;
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
        public boolean onTransact(int n, Parcel object, Parcel parcel, int n2) throws RemoteException {
            if (n != 1) {
                if (n != 1598968902) {
                    return super.onTransact(n, (Parcel)object, parcel, n2);
                }
                parcel.writeString(DESCRIPTOR);
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            n = ((Parcel)object).readInt();
            object = ((Parcel)object).readInt() != 0 ? EuiccNotification.CREATOR.createFromParcel((Parcel)object) : null;
            this.onComplete(n, (EuiccNotification)object);
            return true;
        }

        private static class Proxy
        implements IRetrieveNotificationCallback {
            public static IRetrieveNotificationCallback sDefaultImpl;
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
            public void onComplete(int n, EuiccNotification euiccNotification) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (euiccNotification != null) {
                        parcel.writeInt(1);
                        euiccNotification.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onComplete(n, euiccNotification);
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

