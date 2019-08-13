/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface ITrafficStateCallback
extends IInterface {
    public void onStateChanged(int var1) throws RemoteException;

    public static class Default
    implements ITrafficStateCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onStateChanged(int n) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ITrafficStateCallback {
        private static final String DESCRIPTOR = "android.net.wifi.ITrafficStateCallback";
        static final int TRANSACTION_onStateChanged = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ITrafficStateCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ITrafficStateCallback) {
                return (ITrafficStateCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ITrafficStateCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onStateChanged";
        }

        public static boolean setDefaultImpl(ITrafficStateCallback iTrafficStateCallback) {
            if (Proxy.sDefaultImpl == null && iTrafficStateCallback != null) {
                Proxy.sDefaultImpl = iTrafficStateCallback;
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
            this.onStateChanged(parcel.readInt());
            return true;
        }

        private static class Proxy
        implements ITrafficStateCallback {
            public static ITrafficStateCallback sDefaultImpl;
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
            public void onStateChanged(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onStateChanged(n);
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

