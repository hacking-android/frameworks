/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.view;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IDragAndDropPermissions
extends IInterface {
    public void release() throws RemoteException;

    public void take(IBinder var1) throws RemoteException;

    public void takeTransient(IBinder var1) throws RemoteException;

    public static class Default
    implements IDragAndDropPermissions {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void release() throws RemoteException {
        }

        @Override
        public void take(IBinder iBinder) throws RemoteException {
        }

        @Override
        public void takeTransient(IBinder iBinder) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IDragAndDropPermissions {
        private static final String DESCRIPTOR = "com.android.internal.view.IDragAndDropPermissions";
        static final int TRANSACTION_release = 3;
        static final int TRANSACTION_take = 1;
        static final int TRANSACTION_takeTransient = 2;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IDragAndDropPermissions asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IDragAndDropPermissions) {
                return (IDragAndDropPermissions)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IDragAndDropPermissions getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        return null;
                    }
                    return "release";
                }
                return "takeTransient";
            }
            return "take";
        }

        public static boolean setDefaultImpl(IDragAndDropPermissions iDragAndDropPermissions) {
            if (Proxy.sDefaultImpl == null && iDragAndDropPermissions != null) {
                Proxy.sDefaultImpl = iDragAndDropPermissions;
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
                    this.release();
                    parcel2.writeNoException();
                    return true;
                }
                parcel.enforceInterface(DESCRIPTOR);
                this.takeTransient(parcel.readStrongBinder());
                parcel2.writeNoException();
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            this.take(parcel.readStrongBinder());
            parcel2.writeNoException();
            return true;
        }

        private static class Proxy
        implements IDragAndDropPermissions {
            public static IDragAndDropPermissions sDefaultImpl;
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
            public void release() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().release();
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

            @Override
            public void take(IBinder iBinder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().take(iBinder);
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

            @Override
            public void takeTransient(IBinder iBinder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().takeTransient(iBinder);
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
        }

    }

}

