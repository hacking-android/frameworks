/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IRequestFinishCallback
extends IInterface {
    public void requestFinish() throws RemoteException;

    public static class Default
    implements IRequestFinishCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void requestFinish() throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IRequestFinishCallback {
        private static final String DESCRIPTOR = "android.app.IRequestFinishCallback";
        static final int TRANSACTION_requestFinish = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IRequestFinishCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IRequestFinishCallback) {
                return (IRequestFinishCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IRequestFinishCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "requestFinish";
        }

        public static boolean setDefaultImpl(IRequestFinishCallback iRequestFinishCallback) {
            if (Proxy.sDefaultImpl == null && iRequestFinishCallback != null) {
                Proxy.sDefaultImpl = iRequestFinishCallback;
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
            this.requestFinish();
            return true;
        }

        private static class Proxy
        implements IRequestFinishCallback {
            public static IRequestFinishCallback sDefaultImpl;
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
            public void requestFinish() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().requestFinish();
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

