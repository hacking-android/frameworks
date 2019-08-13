/*
 * Decompiled with CFR 0.145.
 */
package android.app.backup;

import android.app.backup.IBackupManagerMonitor;
import android.app.backup.IRestoreObserver;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IRestoreSession
extends IInterface {
    public void endRestoreSession() throws RemoteException;

    public int getAvailableRestoreSets(IRestoreObserver var1, IBackupManagerMonitor var2) throws RemoteException;

    public int restoreAll(long var1, IRestoreObserver var3, IBackupManagerMonitor var4) throws RemoteException;

    public int restorePackage(String var1, IRestoreObserver var2, IBackupManagerMonitor var3) throws RemoteException;

    public int restorePackages(long var1, IRestoreObserver var3, String[] var4, IBackupManagerMonitor var5) throws RemoteException;

    public static class Default
    implements IRestoreSession {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void endRestoreSession() throws RemoteException {
        }

        @Override
        public int getAvailableRestoreSets(IRestoreObserver iRestoreObserver, IBackupManagerMonitor iBackupManagerMonitor) throws RemoteException {
            return 0;
        }

        @Override
        public int restoreAll(long l, IRestoreObserver iRestoreObserver, IBackupManagerMonitor iBackupManagerMonitor) throws RemoteException {
            return 0;
        }

        @Override
        public int restorePackage(String string2, IRestoreObserver iRestoreObserver, IBackupManagerMonitor iBackupManagerMonitor) throws RemoteException {
            return 0;
        }

        @Override
        public int restorePackages(long l, IRestoreObserver iRestoreObserver, String[] arrstring, IBackupManagerMonitor iBackupManagerMonitor) throws RemoteException {
            return 0;
        }
    }

    public static abstract class Stub
    extends Binder
    implements IRestoreSession {
        private static final String DESCRIPTOR = "android.app.backup.IRestoreSession";
        static final int TRANSACTION_endRestoreSession = 5;
        static final int TRANSACTION_getAvailableRestoreSets = 1;
        static final int TRANSACTION_restoreAll = 2;
        static final int TRANSACTION_restorePackage = 4;
        static final int TRANSACTION_restorePackages = 3;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IRestoreSession asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IRestoreSession) {
                return (IRestoreSession)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IRestoreSession getDefaultImpl() {
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
                            return "endRestoreSession";
                        }
                        return "restorePackage";
                    }
                    return "restorePackages";
                }
                return "restoreAll";
            }
            return "getAvailableRestoreSets";
        }

        public static boolean setDefaultImpl(IRestoreSession iRestoreSession) {
            if (Proxy.sDefaultImpl == null && iRestoreSession != null) {
                Proxy.sDefaultImpl = iRestoreSession;
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
                        if (n != 4) {
                            if (n != 5) {
                                if (n != 1598968902) {
                                    return super.onTransact(n, parcel, parcel2, n2);
                                }
                                parcel2.writeString(DESCRIPTOR);
                                return true;
                            }
                            parcel.enforceInterface(DESCRIPTOR);
                            this.endRestoreSession();
                            parcel2.writeNoException();
                            return true;
                        }
                        parcel.enforceInterface(DESCRIPTOR);
                        n = this.restorePackage(parcel.readString(), IRestoreObserver.Stub.asInterface(parcel.readStrongBinder()), IBackupManagerMonitor.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        parcel2.writeInt(n);
                        return true;
                    }
                    parcel.enforceInterface(DESCRIPTOR);
                    n = this.restorePackages(parcel.readLong(), IRestoreObserver.Stub.asInterface(parcel.readStrongBinder()), parcel.createStringArray(), IBackupManagerMonitor.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    parcel2.writeInt(n);
                    return true;
                }
                parcel.enforceInterface(DESCRIPTOR);
                n = this.restoreAll(parcel.readLong(), IRestoreObserver.Stub.asInterface(parcel.readStrongBinder()), IBackupManagerMonitor.Stub.asInterface(parcel.readStrongBinder()));
                parcel2.writeNoException();
                parcel2.writeInt(n);
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            n = this.getAvailableRestoreSets(IRestoreObserver.Stub.asInterface(parcel.readStrongBinder()), IBackupManagerMonitor.Stub.asInterface(parcel.readStrongBinder()));
            parcel2.writeNoException();
            parcel2.writeInt(n);
            return true;
        }

        private static class Proxy
        implements IRestoreSession {
            public static IRestoreSession sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void endRestoreSession() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().endRestoreSession();
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public int getAvailableRestoreSets(IRestoreObserver iRestoreObserver, IBackupManagerMonitor iBackupManagerMonitor) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    Object var5_6 = null;
                    IBinder iBinder = iRestoreObserver != null ? iRestoreObserver.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    iBinder = var5_6;
                    if (iBackupManagerMonitor != null) {
                        iBinder = iBackupManagerMonitor.asBinder();
                    }
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getAvailableRestoreSets(iRestoreObserver, iBackupManagerMonitor);
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public int restoreAll(long l, IRestoreObserver iRestoreObserver, IBackupManagerMonitor iBackupManagerMonitor) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeLong(l);
                    Object var7_7 = null;
                    IBinder iBinder = iRestoreObserver != null ? iRestoreObserver.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    iBinder = var7_7;
                    if (iBackupManagerMonitor != null) {
                        iBinder = iBackupManagerMonitor.asBinder();
                    }
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().restoreAll(l, iRestoreObserver, iBackupManagerMonitor);
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public int restorePackage(String string2, IRestoreObserver iRestoreObserver, IBackupManagerMonitor iBackupManagerMonitor) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    Object var6_7 = null;
                    IBinder iBinder = iRestoreObserver != null ? iRestoreObserver.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    iBinder = var6_7;
                    if (iBackupManagerMonitor != null) {
                        iBinder = iBackupManagerMonitor.asBinder();
                    }
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().restorePackage(string2, iRestoreObserver, iBackupManagerMonitor);
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public int restorePackages(long l, IRestoreObserver iRestoreObserver, String[] arrstring, IBackupManagerMonitor iBackupManagerMonitor) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeLong(l);
                    Object var8_8 = null;
                    IBinder iBinder = iRestoreObserver != null ? iRestoreObserver.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeStringArray(arrstring);
                    iBinder = var8_8;
                    if (iBackupManagerMonitor != null) {
                        iBinder = iBackupManagerMonitor.asBinder();
                    }
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().restorePackages(l, iRestoreObserver, arrstring, iBackupManagerMonitor);
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }
        }

    }

}

