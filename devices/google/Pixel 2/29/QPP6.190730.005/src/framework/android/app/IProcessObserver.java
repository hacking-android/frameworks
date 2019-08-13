/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IProcessObserver
extends IInterface {
    public void onForegroundActivitiesChanged(int var1, int var2, boolean var3) throws RemoteException;

    public void onForegroundServicesChanged(int var1, int var2, int var3) throws RemoteException;

    public void onProcessDied(int var1, int var2) throws RemoteException;

    public static class Default
    implements IProcessObserver {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onForegroundActivitiesChanged(int n, int n2, boolean bl) throws RemoteException {
        }

        @Override
        public void onForegroundServicesChanged(int n, int n2, int n3) throws RemoteException {
        }

        @Override
        public void onProcessDied(int n, int n2) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IProcessObserver {
        private static final String DESCRIPTOR = "android.app.IProcessObserver";
        static final int TRANSACTION_onForegroundActivitiesChanged = 1;
        static final int TRANSACTION_onForegroundServicesChanged = 2;
        static final int TRANSACTION_onProcessDied = 3;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IProcessObserver asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IProcessObserver) {
                return (IProcessObserver)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IProcessObserver getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        return null;
                    }
                    return "onProcessDied";
                }
                return "onForegroundServicesChanged";
            }
            return "onForegroundActivitiesChanged";
        }

        public static boolean setDefaultImpl(IProcessObserver iProcessObserver) {
            if (Proxy.sDefaultImpl == null && iProcessObserver != null) {
                Proxy.sDefaultImpl = iProcessObserver;
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
                    this.onProcessDied(parcel.readInt(), parcel.readInt());
                    return true;
                }
                parcel.enforceInterface(DESCRIPTOR);
                this.onForegroundServicesChanged(parcel.readInt(), parcel.readInt(), parcel.readInt());
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            n2 = parcel.readInt();
            n = parcel.readInt();
            boolean bl = parcel.readInt() != 0;
            this.onForegroundActivitiesChanged(n2, n, bl);
            return true;
        }

        private static class Proxy
        implements IProcessObserver {
            public static IProcessObserver sDefaultImpl;
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
            public void onForegroundActivitiesChanged(int n, int n2, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                parcel.writeInt(n2);
                int n3 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n3);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onForegroundActivitiesChanged(n, n2, bl);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onForegroundServicesChanged(int n, int n2, int n3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onForegroundServicesChanged(n, n2, n3);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onProcessDied(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onProcessDied(n, n2);
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

