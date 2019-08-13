/*
 * Decompiled with CFR 0.145.
 */
package android.media.projection;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IMediaProjectionCallback
extends IInterface {
    public void onStop() throws RemoteException;

    public static class Default
    implements IMediaProjectionCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onStop() throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IMediaProjectionCallback {
        private static final String DESCRIPTOR = "android.media.projection.IMediaProjectionCallback";
        static final int TRANSACTION_onStop = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IMediaProjectionCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IMediaProjectionCallback) {
                return (IMediaProjectionCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IMediaProjectionCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onStop";
        }

        public static boolean setDefaultImpl(IMediaProjectionCallback iMediaProjectionCallback) {
            if (Proxy.sDefaultImpl == null && iMediaProjectionCallback != null) {
                Proxy.sDefaultImpl = iMediaProjectionCallback;
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
            this.onStop();
            return true;
        }

        private static class Proxy
        implements IMediaProjectionCallback {
            public static IMediaProjectionCallback sDefaultImpl;
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
            public void onStop() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onStop();
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

