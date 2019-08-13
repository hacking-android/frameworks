/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IUidObserver
extends IInterface {
    public void onUidActive(int var1) throws RemoteException;

    public void onUidCachedChanged(int var1, boolean var2) throws RemoteException;

    public void onUidGone(int var1, boolean var2) throws RemoteException;

    public void onUidIdle(int var1, boolean var2) throws RemoteException;

    public void onUidStateChanged(int var1, int var2, long var3) throws RemoteException;

    public static class Default
    implements IUidObserver {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onUidActive(int n) throws RemoteException {
        }

        @Override
        public void onUidCachedChanged(int n, boolean bl) throws RemoteException {
        }

        @Override
        public void onUidGone(int n, boolean bl) throws RemoteException {
        }

        @Override
        public void onUidIdle(int n, boolean bl) throws RemoteException {
        }

        @Override
        public void onUidStateChanged(int n, int n2, long l) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IUidObserver {
        private static final String DESCRIPTOR = "android.app.IUidObserver";
        static final int TRANSACTION_onUidActive = 2;
        static final int TRANSACTION_onUidCachedChanged = 5;
        static final int TRANSACTION_onUidGone = 1;
        static final int TRANSACTION_onUidIdle = 3;
        static final int TRANSACTION_onUidStateChanged = 4;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IUidObserver asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IUidObserver) {
                return (IUidObserver)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IUidObserver getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            if (n != 5) {
                                return null;
                            }
                            return "onUidCachedChanged";
                        }
                        return "onUidStateChanged";
                    }
                    return "onUidIdle";
                }
                return "onUidActive";
            }
            return "onUidGone";
        }

        public static boolean setDefaultImpl(IUidObserver iUidObserver) {
            if (Proxy.sDefaultImpl == null && iUidObserver != null) {
                Proxy.sDefaultImpl = iUidObserver;
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
            boolean bl = false;
            boolean bl2 = false;
            boolean bl3 = false;
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            if (n != 5) {
                                if (n != 1598968902) {
                                    return super.onTransact(n, parcel, parcel2, n2);
                                }
                                parcel2.writeString(DESCRIPTOR);
                                return true;
                            }
                            parcel.enforceInterface(DESCRIPTOR);
                            n = parcel.readInt();
                            if (parcel.readInt() != 0) {
                                bl3 = true;
                            }
                            this.onUidCachedChanged(n, bl3);
                            return true;
                        }
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onUidStateChanged(parcel.readInt(), parcel.readInt(), parcel.readLong());
                        return true;
                    }
                    parcel.enforceInterface(DESCRIPTOR);
                    n = parcel.readInt();
                    bl3 = bl;
                    if (parcel.readInt() != 0) {
                        bl3 = true;
                    }
                    this.onUidIdle(n, bl3);
                    return true;
                }
                parcel.enforceInterface(DESCRIPTOR);
                this.onUidActive(parcel.readInt());
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            n = parcel.readInt();
            bl3 = bl2;
            if (parcel.readInt() != 0) {
                bl3 = true;
            }
            this.onUidGone(n, bl3);
            return true;
        }

        private static class Proxy
        implements IUidObserver {
            public static IUidObserver sDefaultImpl;
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
            public void onUidActive(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onUidActive(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onUidCachedChanged(int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(5, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onUidCachedChanged(n, bl);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onUidGone(int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onUidGone(n, bl);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onUidIdle(int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onUidIdle(n, bl);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onUidStateChanged(int n, int n2, long l) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeLong(l);
                    if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onUidStateChanged(n, n2, l);
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

