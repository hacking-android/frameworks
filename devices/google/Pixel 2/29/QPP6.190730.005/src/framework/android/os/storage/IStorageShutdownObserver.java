/*
 * Decompiled with CFR 0.145.
 */
package android.os.storage;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IStorageShutdownObserver
extends IInterface {
    public void onShutDownComplete(int var1) throws RemoteException;

    public static class Default
    implements IStorageShutdownObserver {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onShutDownComplete(int n) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IStorageShutdownObserver {
        private static final String DESCRIPTOR = "android.os.storage.IStorageShutdownObserver";
        static final int TRANSACTION_onShutDownComplete = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IStorageShutdownObserver asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IStorageShutdownObserver) {
                return (IStorageShutdownObserver)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IStorageShutdownObserver getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onShutDownComplete";
        }

        public static boolean setDefaultImpl(IStorageShutdownObserver iStorageShutdownObserver) {
            if (Proxy.sDefaultImpl == null && iStorageShutdownObserver != null) {
                Proxy.sDefaultImpl = iStorageShutdownObserver;
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
            this.onShutDownComplete(parcel.readInt());
            parcel2.writeNoException();
            return true;
        }

        private static class Proxy
        implements IStorageShutdownObserver {
            public static IStorageShutdownObserver sDefaultImpl;
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
            public void onShutDownComplete(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onShutDownComplete(n);
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

