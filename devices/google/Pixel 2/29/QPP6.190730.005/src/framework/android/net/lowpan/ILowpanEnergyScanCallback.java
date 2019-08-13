/*
 * Decompiled with CFR 0.145.
 */
package android.net.lowpan;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface ILowpanEnergyScanCallback
extends IInterface {
    public void onEnergyScanFinished() throws RemoteException;

    public void onEnergyScanResult(int var1, int var2) throws RemoteException;

    public static class Default
    implements ILowpanEnergyScanCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onEnergyScanFinished() throws RemoteException {
        }

        @Override
        public void onEnergyScanResult(int n, int n2) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ILowpanEnergyScanCallback {
        private static final String DESCRIPTOR = "android.net.lowpan.ILowpanEnergyScanCallback";
        static final int TRANSACTION_onEnergyScanFinished = 2;
        static final int TRANSACTION_onEnergyScanResult = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ILowpanEnergyScanCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ILowpanEnergyScanCallback) {
                return (ILowpanEnergyScanCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ILowpanEnergyScanCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    return null;
                }
                return "onEnergyScanFinished";
            }
            return "onEnergyScanResult";
        }

        public static boolean setDefaultImpl(ILowpanEnergyScanCallback iLowpanEnergyScanCallback) {
            if (Proxy.sDefaultImpl == null && iLowpanEnergyScanCallback != null) {
                Proxy.sDefaultImpl = iLowpanEnergyScanCallback;
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
                this.onEnergyScanFinished();
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            this.onEnergyScanResult(parcel.readInt(), parcel.readInt());
            return true;
        }

        private static class Proxy
        implements ILowpanEnergyScanCallback {
            public static ILowpanEnergyScanCallback sDefaultImpl;
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
            public void onEnergyScanFinished() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onEnergyScanFinished();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onEnergyScanResult(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onEnergyScanResult(n, n2);
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

