/*
 * Decompiled with CFR 0.145.
 */
package com.android.ims.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IImsFeatureStatusCallback
extends IInterface {
    public void notifyImsFeatureStatus(int var1) throws RemoteException;

    public static class Default
    implements IImsFeatureStatusCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void notifyImsFeatureStatus(int n) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IImsFeatureStatusCallback {
        private static final String DESCRIPTOR = "com.android.ims.internal.IImsFeatureStatusCallback";
        static final int TRANSACTION_notifyImsFeatureStatus = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IImsFeatureStatusCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IImsFeatureStatusCallback) {
                return (IImsFeatureStatusCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IImsFeatureStatusCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "notifyImsFeatureStatus";
        }

        public static boolean setDefaultImpl(IImsFeatureStatusCallback iImsFeatureStatusCallback) {
            if (Proxy.sDefaultImpl == null && iImsFeatureStatusCallback != null) {
                Proxy.sDefaultImpl = iImsFeatureStatusCallback;
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
            this.notifyImsFeatureStatus(parcel.readInt());
            return true;
        }

        private static class Proxy
        implements IImsFeatureStatusCallback {
            public static IImsFeatureStatusCallback sDefaultImpl;
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
            public void notifyImsFeatureStatus(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyImsFeatureStatus(n);
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

