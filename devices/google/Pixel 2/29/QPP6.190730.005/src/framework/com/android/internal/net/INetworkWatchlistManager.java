/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.net;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface INetworkWatchlistManager
extends IInterface {
    public byte[] getWatchlistConfigHash() throws RemoteException;

    public void reloadWatchlist() throws RemoteException;

    public void reportWatchlistIfNecessary() throws RemoteException;

    public boolean startWatchlistLogging() throws RemoteException;

    public boolean stopWatchlistLogging() throws RemoteException;

    public static class Default
    implements INetworkWatchlistManager {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public byte[] getWatchlistConfigHash() throws RemoteException {
            return null;
        }

        @Override
        public void reloadWatchlist() throws RemoteException {
        }

        @Override
        public void reportWatchlistIfNecessary() throws RemoteException {
        }

        @Override
        public boolean startWatchlistLogging() throws RemoteException {
            return false;
        }

        @Override
        public boolean stopWatchlistLogging() throws RemoteException {
            return false;
        }
    }

    public static abstract class Stub
    extends Binder
    implements INetworkWatchlistManager {
        private static final String DESCRIPTOR = "com.android.internal.net.INetworkWatchlistManager";
        static final int TRANSACTION_getWatchlistConfigHash = 5;
        static final int TRANSACTION_reloadWatchlist = 3;
        static final int TRANSACTION_reportWatchlistIfNecessary = 4;
        static final int TRANSACTION_startWatchlistLogging = 1;
        static final int TRANSACTION_stopWatchlistLogging = 2;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static INetworkWatchlistManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof INetworkWatchlistManager) {
                return (INetworkWatchlistManager)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static INetworkWatchlistManager getDefaultImpl() {
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
                            return "getWatchlistConfigHash";
                        }
                        return "reportWatchlistIfNecessary";
                    }
                    return "reloadWatchlist";
                }
                return "stopWatchlistLogging";
            }
            return "startWatchlistLogging";
        }

        public static boolean setDefaultImpl(INetworkWatchlistManager iNetworkWatchlistManager) {
            if (Proxy.sDefaultImpl == null && iNetworkWatchlistManager != null) {
                Proxy.sDefaultImpl = iNetworkWatchlistManager;
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
        public boolean onTransact(int n, Parcel arrby, Parcel parcel, int n2) throws RemoteException {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            if (n != 5) {
                                if (n != 1598968902) {
                                    return super.onTransact(n, (Parcel)arrby, parcel, n2);
                                }
                                parcel.writeString(DESCRIPTOR);
                                return true;
                            }
                            arrby.enforceInterface(DESCRIPTOR);
                            arrby = this.getWatchlistConfigHash();
                            parcel.writeNoException();
                            parcel.writeByteArray(arrby);
                            return true;
                        }
                        arrby.enforceInterface(DESCRIPTOR);
                        this.reportWatchlistIfNecessary();
                        parcel.writeNoException();
                        return true;
                    }
                    arrby.enforceInterface(DESCRIPTOR);
                    this.reloadWatchlist();
                    parcel.writeNoException();
                    return true;
                }
                arrby.enforceInterface(DESCRIPTOR);
                n = this.stopWatchlistLogging() ? 1 : 0;
                parcel.writeNoException();
                parcel.writeInt(n);
                return true;
            }
            arrby.enforceInterface(DESCRIPTOR);
            n = this.startWatchlistLogging() ? 1 : 0;
            parcel.writeNoException();
            parcel.writeInt(n);
            return true;
        }

        private static class Proxy
        implements INetworkWatchlistManager {
            public static INetworkWatchlistManager sDefaultImpl;
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
            public byte[] getWatchlistConfigHash() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        byte[] arrby = Stub.getDefaultImpl().getWatchlistConfigHash();
                        return arrby;
                    }
                    parcel2.readException();
                    byte[] arrby = parcel2.createByteArray();
                    return arrby;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void reloadWatchlist() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().reloadWatchlist();
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void reportWatchlistIfNecessary() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().reportWatchlistIfNecessary();
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean startWatchlistLogging() throws RemoteException {
                boolean bl;
                Parcel parcel;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(1, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().startWatchlistLogging();
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                int n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public boolean stopWatchlistLogging() throws RemoteException {
                boolean bl;
                Parcel parcel;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(2, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().stopWatchlistLogging();
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                int n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }
        }

    }

}

