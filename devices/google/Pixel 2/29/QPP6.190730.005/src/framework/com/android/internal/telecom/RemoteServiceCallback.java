/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telecom;

import android.content.ComponentName;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface RemoteServiceCallback
extends IInterface {
    public void onError() throws RemoteException;

    public void onResult(List<ComponentName> var1, List<IBinder> var2) throws RemoteException;

    public static class Default
    implements RemoteServiceCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onError() throws RemoteException {
        }

        @Override
        public void onResult(List<ComponentName> list, List<IBinder> list2) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements RemoteServiceCallback {
        private static final String DESCRIPTOR = "com.android.internal.telecom.RemoteServiceCallback";
        static final int TRANSACTION_onError = 1;
        static final int TRANSACTION_onResult = 2;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static RemoteServiceCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof RemoteServiceCallback) {
                return (RemoteServiceCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static RemoteServiceCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    return null;
                }
                return "onResult";
            }
            return "onError";
        }

        public static boolean setDefaultImpl(RemoteServiceCallback remoteServiceCallback) {
            if (Proxy.sDefaultImpl == null && remoteServiceCallback != null) {
                Proxy.sDefaultImpl = remoteServiceCallback;
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
                this.onResult(parcel.createTypedArrayList(ComponentName.CREATOR), parcel.createBinderArrayList());
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            this.onError();
            return true;
        }

        private static class Proxy
        implements RemoteServiceCallback {
            public static RemoteServiceCallback sDefaultImpl;
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
            public void onError() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onError();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onResult(List<ComponentName> list, List<IBinder> list2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeTypedList(list);
                    parcel.writeBinderList(list2);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onResult(list, list2);
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

