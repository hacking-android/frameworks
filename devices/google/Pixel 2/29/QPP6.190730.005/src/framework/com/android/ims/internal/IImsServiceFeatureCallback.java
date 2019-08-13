/*
 * Decompiled with CFR 0.145.
 */
package com.android.ims.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IImsServiceFeatureCallback
extends IInterface {
    public void imsFeatureCreated(int var1, int var2) throws RemoteException;

    public void imsFeatureRemoved(int var1, int var2) throws RemoteException;

    public void imsStatusChanged(int var1, int var2, int var3) throws RemoteException;

    public static class Default
    implements IImsServiceFeatureCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void imsFeatureCreated(int n, int n2) throws RemoteException {
        }

        @Override
        public void imsFeatureRemoved(int n, int n2) throws RemoteException {
        }

        @Override
        public void imsStatusChanged(int n, int n2, int n3) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IImsServiceFeatureCallback {
        private static final String DESCRIPTOR = "com.android.ims.internal.IImsServiceFeatureCallback";
        static final int TRANSACTION_imsFeatureCreated = 1;
        static final int TRANSACTION_imsFeatureRemoved = 2;
        static final int TRANSACTION_imsStatusChanged = 3;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IImsServiceFeatureCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IImsServiceFeatureCallback) {
                return (IImsServiceFeatureCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IImsServiceFeatureCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        return null;
                    }
                    return "imsStatusChanged";
                }
                return "imsFeatureRemoved";
            }
            return "imsFeatureCreated";
        }

        public static boolean setDefaultImpl(IImsServiceFeatureCallback iImsServiceFeatureCallback) {
            if (Proxy.sDefaultImpl == null && iImsServiceFeatureCallback != null) {
                Proxy.sDefaultImpl = iImsServiceFeatureCallback;
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
                    if (n != 3) {
                        if (n != 1598968902) {
                            return super.onTransact(n, parcel, parcel2, n2);
                        }
                        parcel2.writeString(DESCRIPTOR);
                        return true;
                    }
                    parcel.enforceInterface(DESCRIPTOR);
                    this.imsStatusChanged(parcel.readInt(), parcel.readInt(), parcel.readInt());
                    return true;
                }
                parcel.enforceInterface(DESCRIPTOR);
                this.imsFeatureRemoved(parcel.readInt(), parcel.readInt());
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            this.imsFeatureCreated(parcel.readInt(), parcel.readInt());
            return true;
        }

        private static class Proxy
        implements IImsServiceFeatureCallback {
            public static IImsServiceFeatureCallback sDefaultImpl;
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
            public void imsFeatureCreated(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().imsFeatureCreated(n, n2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void imsFeatureRemoved(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().imsFeatureRemoved(n, n2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void imsStatusChanged(int n, int n2, int n3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().imsStatusChanged(n, n2, n3);
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

