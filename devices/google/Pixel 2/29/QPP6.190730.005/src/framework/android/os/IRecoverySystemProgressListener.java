/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IRecoverySystemProgressListener
extends IInterface {
    public void onProgress(int var1) throws RemoteException;

    public static class Default
    implements IRecoverySystemProgressListener {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onProgress(int n) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IRecoverySystemProgressListener {
        private static final String DESCRIPTOR = "android.os.IRecoverySystemProgressListener";
        static final int TRANSACTION_onProgress = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IRecoverySystemProgressListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IRecoverySystemProgressListener) {
                return (IRecoverySystemProgressListener)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IRecoverySystemProgressListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onProgress";
        }

        public static boolean setDefaultImpl(IRecoverySystemProgressListener iRecoverySystemProgressListener) {
            if (Proxy.sDefaultImpl == null && iRecoverySystemProgressListener != null) {
                Proxy.sDefaultImpl = iRecoverySystemProgressListener;
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
            this.onProgress(parcel.readInt());
            return true;
        }

        private static class Proxy
        implements IRecoverySystemProgressListener {
            public static IRecoverySystemProgressListener sDefaultImpl;
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
            public void onProgress(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onProgress(n);
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

