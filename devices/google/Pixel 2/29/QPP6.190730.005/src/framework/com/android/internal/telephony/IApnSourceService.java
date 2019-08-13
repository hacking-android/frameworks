/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony;

import android.content.ContentValues;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface IApnSourceService
extends IInterface {
    public ContentValues[] getApns(int var1) throws RemoteException;

    public static class Default
    implements IApnSourceService {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public ContentValues[] getApns(int n) throws RemoteException {
            return null;
        }
    }

    public static abstract class Stub
    extends Binder
    implements IApnSourceService {
        private static final String DESCRIPTOR = "com.android.internal.telephony.IApnSourceService";
        static final int TRANSACTION_getApns = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IApnSourceService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IApnSourceService) {
                return (IApnSourceService)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IApnSourceService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "getApns";
        }

        public static boolean setDefaultImpl(IApnSourceService iApnSourceService) {
            if (Proxy.sDefaultImpl == null && iApnSourceService != null) {
                Proxy.sDefaultImpl = iApnSourceService;
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
        public boolean onTransact(int n, Parcel arrparcelable, Parcel parcel, int n2) throws RemoteException {
            if (n != 1) {
                if (n != 1598968902) {
                    return super.onTransact(n, (Parcel)arrparcelable, parcel, n2);
                }
                parcel.writeString(DESCRIPTOR);
                return true;
            }
            arrparcelable.enforceInterface(DESCRIPTOR);
            arrparcelable = this.getApns(arrparcelable.readInt());
            parcel.writeNoException();
            parcel.writeTypedArray(arrparcelable, 1);
            return true;
        }

        private static class Proxy
        implements IApnSourceService {
            public static IApnSourceService sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public ContentValues[] getApns(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        ContentValues[] arrcontentValues = Stub.getDefaultImpl().getApns(n);
                        return arrcontentValues;
                    }
                    parcel2.readException();
                    ContentValues[] arrcontentValues = parcel2.createTypedArray(ContentValues.CREATOR);
                    return arrcontentValues;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }
        }

    }

}

