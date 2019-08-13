/*
 * Decompiled with CFR 0.145.
 */
package android.app.backup;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IFullBackupRestoreObserver
extends IInterface {
    public void onBackupPackage(String var1) throws RemoteException;

    public void onEndBackup() throws RemoteException;

    public void onEndRestore() throws RemoteException;

    public void onRestorePackage(String var1) throws RemoteException;

    public void onStartBackup() throws RemoteException;

    public void onStartRestore() throws RemoteException;

    public void onTimeout() throws RemoteException;

    public static class Default
    implements IFullBackupRestoreObserver {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onBackupPackage(String string2) throws RemoteException {
        }

        @Override
        public void onEndBackup() throws RemoteException {
        }

        @Override
        public void onEndRestore() throws RemoteException {
        }

        @Override
        public void onRestorePackage(String string2) throws RemoteException {
        }

        @Override
        public void onStartBackup() throws RemoteException {
        }

        @Override
        public void onStartRestore() throws RemoteException {
        }

        @Override
        public void onTimeout() throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IFullBackupRestoreObserver {
        private static final String DESCRIPTOR = "android.app.backup.IFullBackupRestoreObserver";
        static final int TRANSACTION_onBackupPackage = 2;
        static final int TRANSACTION_onEndBackup = 3;
        static final int TRANSACTION_onEndRestore = 6;
        static final int TRANSACTION_onRestorePackage = 5;
        static final int TRANSACTION_onStartBackup = 1;
        static final int TRANSACTION_onStartRestore = 4;
        static final int TRANSACTION_onTimeout = 7;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IFullBackupRestoreObserver asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IFullBackupRestoreObserver) {
                return (IFullBackupRestoreObserver)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IFullBackupRestoreObserver getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 7: {
                    return "onTimeout";
                }
                case 6: {
                    return "onEndRestore";
                }
                case 5: {
                    return "onRestorePackage";
                }
                case 4: {
                    return "onStartRestore";
                }
                case 3: {
                    return "onEndBackup";
                }
                case 2: {
                    return "onBackupPackage";
                }
                case 1: 
            }
            return "onStartBackup";
        }

        public static boolean setDefaultImpl(IFullBackupRestoreObserver iFullBackupRestoreObserver) {
            if (Proxy.sDefaultImpl == null && iFullBackupRestoreObserver != null) {
                Proxy.sDefaultImpl = iFullBackupRestoreObserver;
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
            if (n != 1598968902) {
                switch (n) {
                    default: {
                        return super.onTransact(n, parcel, parcel2, n2);
                    }
                    case 7: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onTimeout();
                        return true;
                    }
                    case 6: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onEndRestore();
                        return true;
                    }
                    case 5: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onRestorePackage(parcel.readString());
                        return true;
                    }
                    case 4: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onStartRestore();
                        return true;
                    }
                    case 3: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onEndBackup();
                        return true;
                    }
                    case 2: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onBackupPackage(parcel.readString());
                        return true;
                    }
                    case 1: 
                }
                parcel.enforceInterface(DESCRIPTOR);
                this.onStartBackup();
                return true;
            }
            parcel2.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IFullBackupRestoreObserver {
            public static IFullBackupRestoreObserver sDefaultImpl;
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
            public void onBackupPackage(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onBackupPackage(string2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onEndBackup() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onEndBackup();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onEndRestore() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(6, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onEndRestore();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onRestorePackage(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(5, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onRestorePackage(string2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onStartBackup() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onStartBackup();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onStartRestore() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onStartRestore();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onTimeout() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(7, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onTimeout();
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

