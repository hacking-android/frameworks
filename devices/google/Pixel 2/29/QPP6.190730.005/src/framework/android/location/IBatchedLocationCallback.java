/*
 * Decompiled with CFR 0.145.
 */
package android.location;

import android.location.Location;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface IBatchedLocationCallback
extends IInterface {
    public void onLocationBatch(List<Location> var1) throws RemoteException;

    public static class Default
    implements IBatchedLocationCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onLocationBatch(List<Location> list) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IBatchedLocationCallback {
        private static final String DESCRIPTOR = "android.location.IBatchedLocationCallback";
        static final int TRANSACTION_onLocationBatch = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IBatchedLocationCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IBatchedLocationCallback) {
                return (IBatchedLocationCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IBatchedLocationCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onLocationBatch";
        }

        public static boolean setDefaultImpl(IBatchedLocationCallback iBatchedLocationCallback) {
            if (Proxy.sDefaultImpl == null && iBatchedLocationCallback != null) {
                Proxy.sDefaultImpl = iBatchedLocationCallback;
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
            this.onLocationBatch(parcel.createTypedArrayList(Location.CREATOR));
            return true;
        }

        private static class Proxy
        implements IBatchedLocationCallback {
            public static IBatchedLocationCallback sDefaultImpl;
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
            public void onLocationBatch(List<Location> list) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeTypedList(list);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onLocationBatch(list);
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

