/*
 * Decompiled with CFR 0.145.
 */
package android.app.backup;

import android.app.backup.BackupProgress;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface IBackupObserver
extends IInterface {
    public void backupFinished(int var1) throws RemoteException;

    public void onResult(String var1, int var2) throws RemoteException;

    public void onUpdate(String var1, BackupProgress var2) throws RemoteException;

    public static class Default
    implements IBackupObserver {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void backupFinished(int n) throws RemoteException {
        }

        @Override
        public void onResult(String string2, int n) throws RemoteException {
        }

        @Override
        public void onUpdate(String string2, BackupProgress backupProgress) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IBackupObserver {
        private static final String DESCRIPTOR = "android.app.backup.IBackupObserver";
        static final int TRANSACTION_backupFinished = 3;
        static final int TRANSACTION_onResult = 2;
        static final int TRANSACTION_onUpdate = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IBackupObserver asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IBackupObserver) {
                return (IBackupObserver)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IBackupObserver getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        return null;
                    }
                    return "backupFinished";
                }
                return "onResult";
            }
            return "onUpdate";
        }

        public static boolean setDefaultImpl(IBackupObserver iBackupObserver) {
            if (Proxy.sDefaultImpl == null && iBackupObserver != null) {
                Proxy.sDefaultImpl = iBackupObserver;
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
        public boolean onTransact(int n, Parcel object, Parcel object2, int n2) throws RemoteException {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 1598968902) {
                            return super.onTransact(n, (Parcel)object, (Parcel)object2, n2);
                        }
                        ((Parcel)object2).writeString(DESCRIPTOR);
                        return true;
                    }
                    ((Parcel)object).enforceInterface(DESCRIPTOR);
                    this.backupFinished(((Parcel)object).readInt());
                    return true;
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                this.onResult(((Parcel)object).readString(), ((Parcel)object).readInt());
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            object2 = ((Parcel)object).readString();
            object = ((Parcel)object).readInt() != 0 ? BackupProgress.CREATOR.createFromParcel((Parcel)object) : null;
            this.onUpdate((String)object2, (BackupProgress)object);
            return true;
        }

        private static class Proxy
        implements IBackupObserver {
            public static IBackupObserver sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void backupFinished(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().backupFinished(n);
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
            public void onResult(String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onResult(string2, n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onUpdate(String string2, BackupProgress backupProgress) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (backupProgress != null) {
                        parcel.writeInt(1);
                        backupProgress.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onUpdate(string2, backupProgress);
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

