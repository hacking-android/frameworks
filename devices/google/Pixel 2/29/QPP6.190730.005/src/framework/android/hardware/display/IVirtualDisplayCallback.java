/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.display;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IVirtualDisplayCallback
extends IInterface {
    public void onPaused() throws RemoteException;

    public void onResumed() throws RemoteException;

    public void onStopped() throws RemoteException;

    public static class Default
    implements IVirtualDisplayCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onPaused() throws RemoteException {
        }

        @Override
        public void onResumed() throws RemoteException {
        }

        @Override
        public void onStopped() throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IVirtualDisplayCallback {
        private static final String DESCRIPTOR = "android.hardware.display.IVirtualDisplayCallback";
        static final int TRANSACTION_onPaused = 1;
        static final int TRANSACTION_onResumed = 2;
        static final int TRANSACTION_onStopped = 3;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IVirtualDisplayCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IVirtualDisplayCallback) {
                return (IVirtualDisplayCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IVirtualDisplayCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        return null;
                    }
                    return "onStopped";
                }
                return "onResumed";
            }
            return "onPaused";
        }

        public static boolean setDefaultImpl(IVirtualDisplayCallback iVirtualDisplayCallback) {
            if (Proxy.sDefaultImpl == null && iVirtualDisplayCallback != null) {
                Proxy.sDefaultImpl = iVirtualDisplayCallback;
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
                    this.onStopped();
                    return true;
                }
                parcel.enforceInterface(DESCRIPTOR);
                this.onResumed();
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            this.onPaused();
            return true;
        }

        private static class Proxy
        implements IVirtualDisplayCallback {
            public static IVirtualDisplayCallback sDefaultImpl;
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
            public void onPaused() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onPaused();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onResumed() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onResumed();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onStopped() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onStopped();
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

