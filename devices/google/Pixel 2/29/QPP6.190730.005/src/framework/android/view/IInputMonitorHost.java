/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IInputMonitorHost
extends IInterface {
    public void dispose() throws RemoteException;

    public void pilferPointers() throws RemoteException;

    public static class Default
    implements IInputMonitorHost {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void dispose() throws RemoteException {
        }

        @Override
        public void pilferPointers() throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IInputMonitorHost {
        private static final String DESCRIPTOR = "android.view.IInputMonitorHost";
        static final int TRANSACTION_dispose = 2;
        static final int TRANSACTION_pilferPointers = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IInputMonitorHost asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IInputMonitorHost) {
                return (IInputMonitorHost)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IInputMonitorHost getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    return null;
                }
                return "dispose";
            }
            return "pilferPointers";
        }

        public static boolean setDefaultImpl(IInputMonitorHost iInputMonitorHost) {
            if (Proxy.sDefaultImpl == null && iInputMonitorHost != null) {
                Proxy.sDefaultImpl = iInputMonitorHost;
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
                this.dispose();
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            this.pilferPointers();
            return true;
        }

        private static class Proxy
        implements IInputMonitorHost {
            public static IInputMonitorHost sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void dispose() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().dispose();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public void pilferPointers() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().pilferPointers();
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

