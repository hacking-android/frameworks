/*
 * Decompiled with CFR 0.145.
 */
package android.content.pm.dex;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.RemoteException;

public interface ISnapshotRuntimeProfileCallback
extends IInterface {
    public void onError(int var1) throws RemoteException;

    public void onSuccess(ParcelFileDescriptor var1) throws RemoteException;

    public static class Default
    implements ISnapshotRuntimeProfileCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onError(int n) throws RemoteException {
        }

        @Override
        public void onSuccess(ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ISnapshotRuntimeProfileCallback {
        private static final String DESCRIPTOR = "android.content.pm.dex.ISnapshotRuntimeProfileCallback";
        static final int TRANSACTION_onError = 2;
        static final int TRANSACTION_onSuccess = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ISnapshotRuntimeProfileCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ISnapshotRuntimeProfileCallback) {
                return (ISnapshotRuntimeProfileCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ISnapshotRuntimeProfileCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    return null;
                }
                return "onError";
            }
            return "onSuccess";
        }

        public static boolean setDefaultImpl(ISnapshotRuntimeProfileCallback iSnapshotRuntimeProfileCallback) {
            if (Proxy.sDefaultImpl == null && iSnapshotRuntimeProfileCallback != null) {
                Proxy.sDefaultImpl = iSnapshotRuntimeProfileCallback;
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
                if (n != 2) {
                    if (n != 1598968902) {
                        return super.onTransact(n, (Parcel)object, parcel, n2);
                    }
                    parcel.writeString(DESCRIPTOR);
                    return true;
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                this.onError(((Parcel)object).readInt());
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            object = ((Parcel)object).readInt() != 0 ? ParcelFileDescriptor.CREATOR.createFromParcel((Parcel)object) : null;
            this.onSuccess((ParcelFileDescriptor)object);
            return true;
        }

        private static class Proxy
        implements ISnapshotRuntimeProfileCallback {
            public static ISnapshotRuntimeProfileCallback sDefaultImpl;
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
            public void onError(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onError(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onSuccess(ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelFileDescriptor != null) {
                        parcel.writeInt(1);
                        parcelFileDescriptor.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onSuccess(parcelFileDescriptor);
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

