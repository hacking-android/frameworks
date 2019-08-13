/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.fingerprint;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IFingerprintClientActiveCallback
extends IInterface {
    public void onClientActiveChanged(boolean var1) throws RemoteException;

    public static class Default
    implements IFingerprintClientActiveCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onClientActiveChanged(boolean bl) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IFingerprintClientActiveCallback {
        private static final String DESCRIPTOR = "android.hardware.fingerprint.IFingerprintClientActiveCallback";
        static final int TRANSACTION_onClientActiveChanged = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IFingerprintClientActiveCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IFingerprintClientActiveCallback) {
                return (IFingerprintClientActiveCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IFingerprintClientActiveCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onClientActiveChanged";
        }

        public static boolean setDefaultImpl(IFingerprintClientActiveCallback iFingerprintClientActiveCallback) {
            if (Proxy.sDefaultImpl == null && iFingerprintClientActiveCallback != null) {
                Proxy.sDefaultImpl = iFingerprintClientActiveCallback;
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
            this.onClientActiveChanged(bl);
            return true;
        }

        private static class Proxy
        implements IFingerprintClientActiveCallback {
            public static IFingerprintClientActiveCallback sDefaultImpl;
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
            public void onClientActiveChanged(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onClientActiveChanged(bl);
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

