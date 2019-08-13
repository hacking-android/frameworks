/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IMediaRouterClient
extends IInterface {
    public void onRestoreRoute() throws RemoteException;

    public void onSelectedRouteChanged(String var1) throws RemoteException;

    public void onStateChanged() throws RemoteException;

    public static class Default
    implements IMediaRouterClient {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onRestoreRoute() throws RemoteException {
        }

        @Override
        public void onSelectedRouteChanged(String string2) throws RemoteException {
        }

        @Override
        public void onStateChanged() throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IMediaRouterClient {
        private static final String DESCRIPTOR = "android.media.IMediaRouterClient";
        static final int TRANSACTION_onRestoreRoute = 2;
        static final int TRANSACTION_onSelectedRouteChanged = 3;
        static final int TRANSACTION_onStateChanged = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IMediaRouterClient asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IMediaRouterClient) {
                return (IMediaRouterClient)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IMediaRouterClient getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        return null;
                    }
                    return "onSelectedRouteChanged";
                }
                return "onRestoreRoute";
            }
            return "onStateChanged";
        }

        public static boolean setDefaultImpl(IMediaRouterClient iMediaRouterClient) {
            if (Proxy.sDefaultImpl == null && iMediaRouterClient != null) {
                Proxy.sDefaultImpl = iMediaRouterClient;
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
                    if (n != 3) {
                        if (n != 1598968902) {
                            return super.onTransact(n, parcel, parcel2, n2);
                        }
                        parcel2.writeString(DESCRIPTOR);
                        return true;
                    }
                    parcel.enforceInterface(DESCRIPTOR);
                    this.onSelectedRouteChanged(parcel.readString());
                    return true;
                }
                parcel.enforceInterface(DESCRIPTOR);
                this.onRestoreRoute();
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            this.onStateChanged();
            return true;
        }

        private static class Proxy
        implements IMediaRouterClient {
            public static IMediaRouterClient sDefaultImpl;
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
            public void onRestoreRoute() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onRestoreRoute();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onSelectedRouteChanged(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onSelectedRouteChanged(string2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onStateChanged() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onStateChanged();
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

