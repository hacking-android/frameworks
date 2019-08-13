/*
 * Decompiled with CFR 0.145.
 */
package android.service.vr;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IPersistentVrStateCallbacks
extends IInterface {
    public void onPersistentVrStateChanged(boolean var1) throws RemoteException;

    public static class Default
    implements IPersistentVrStateCallbacks {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onPersistentVrStateChanged(boolean bl) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IPersistentVrStateCallbacks {
        private static final String DESCRIPTOR = "android.service.vr.IPersistentVrStateCallbacks";
        static final int TRANSACTION_onPersistentVrStateChanged = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IPersistentVrStateCallbacks asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IPersistentVrStateCallbacks) {
                return (IPersistentVrStateCallbacks)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IPersistentVrStateCallbacks getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onPersistentVrStateChanged";
        }

        public static boolean setDefaultImpl(IPersistentVrStateCallbacks iPersistentVrStateCallbacks) {
            if (Proxy.sDefaultImpl == null && iPersistentVrStateCallbacks != null) {
                Proxy.sDefaultImpl = iPersistentVrStateCallbacks;
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
            boolean bl = parcel.readInt() != 0;
            this.onPersistentVrStateChanged(bl);
            return true;
        }

        private static class Proxy
        implements IPersistentVrStateCallbacks {
            public static IPersistentVrStateCallbacks sDefaultImpl;
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
            public void onPersistentVrStateChanged(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onPersistentVrStateChanged(bl);
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

