/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface ISearchManagerCallback
extends IInterface {
    public void onCancel() throws RemoteException;

    public void onDismiss() throws RemoteException;

    public static class Default
    implements ISearchManagerCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onCancel() throws RemoteException {
        }

        @Override
        public void onDismiss() throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ISearchManagerCallback {
        private static final String DESCRIPTOR = "android.app.ISearchManagerCallback";
        static final int TRANSACTION_onCancel = 2;
        static final int TRANSACTION_onDismiss = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ISearchManagerCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ISearchManagerCallback) {
                return (ISearchManagerCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ISearchManagerCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    return null;
                }
                return "onCancel";
            }
            return "onDismiss";
        }

        public static boolean setDefaultImpl(ISearchManagerCallback iSearchManagerCallback) {
            if (Proxy.sDefaultImpl == null && iSearchManagerCallback != null) {
                Proxy.sDefaultImpl = iSearchManagerCallback;
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
                this.onCancel();
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            this.onDismiss();
            return true;
        }

        private static class Proxy
        implements ISearchManagerCallback {
            public static ISearchManagerCallback sDefaultImpl;
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
            public void onCancel() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onCancel();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onDismiss() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onDismiss();
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

