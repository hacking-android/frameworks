/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.app.backup.IBackupCallback;
import android.app.backup.IBackupManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.RemoteException;

public interface IBackupAgent
extends IInterface {
    public void doBackup(ParcelFileDescriptor var1, ParcelFileDescriptor var2, ParcelFileDescriptor var3, long var4, IBackupCallback var6, int var7) throws RemoteException;

    public void doFullBackup(ParcelFileDescriptor var1, long var2, int var4, IBackupManager var5, int var6) throws RemoteException;

    public void doMeasureFullBackup(long var1, int var3, IBackupManager var4, int var5) throws RemoteException;

    public void doQuotaExceeded(long var1, long var3, IBackupCallback var5) throws RemoteException;

    public void doRestore(ParcelFileDescriptor var1, long var2, ParcelFileDescriptor var4, int var5, IBackupManager var6) throws RemoteException;

    public void doRestoreFile(ParcelFileDescriptor var1, long var2, int var4, String var5, String var6, long var7, long var9, int var11, IBackupManager var12) throws RemoteException;

    public void doRestoreFinished(int var1, IBackupManager var2) throws RemoteException;

    public void fail(String var1) throws RemoteException;

    public static class Default
    implements IBackupAgent {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void doBackup(ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2, ParcelFileDescriptor parcelFileDescriptor3, long l, IBackupCallback iBackupCallback, int n) throws RemoteException {
        }

        @Override
        public void doFullBackup(ParcelFileDescriptor parcelFileDescriptor, long l, int n, IBackupManager iBackupManager, int n2) throws RemoteException {
        }

        @Override
        public void doMeasureFullBackup(long l, int n, IBackupManager iBackupManager, int n2) throws RemoteException {
        }

        @Override
        public void doQuotaExceeded(long l, long l2, IBackupCallback iBackupCallback) throws RemoteException {
        }

        @Override
        public void doRestore(ParcelFileDescriptor parcelFileDescriptor, long l, ParcelFileDescriptor parcelFileDescriptor2, int n, IBackupManager iBackupManager) throws RemoteException {
        }

        @Override
        public void doRestoreFile(ParcelFileDescriptor parcelFileDescriptor, long l, int n, String string2, String string3, long l2, long l3, int n2, IBackupManager iBackupManager) throws RemoteException {
        }

        @Override
        public void doRestoreFinished(int n, IBackupManager iBackupManager) throws RemoteException {
        }

        @Override
        public void fail(String string2) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IBackupAgent {
        private static final String DESCRIPTOR = "android.app.IBackupAgent";
        static final int TRANSACTION_doBackup = 1;
        static final int TRANSACTION_doFullBackup = 3;
        static final int TRANSACTION_doMeasureFullBackup = 4;
        static final int TRANSACTION_doQuotaExceeded = 5;
        static final int TRANSACTION_doRestore = 2;
        static final int TRANSACTION_doRestoreFile = 6;
        static final int TRANSACTION_doRestoreFinished = 7;
        static final int TRANSACTION_fail = 8;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IBackupAgent asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IBackupAgent) {
                return (IBackupAgent)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IBackupAgent getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 8: {
                    return "fail";
                }
                case 7: {
                    return "doRestoreFinished";
                }
                case 6: {
                    return "doRestoreFile";
                }
                case 5: {
                    return "doQuotaExceeded";
                }
                case 4: {
                    return "doMeasureFullBackup";
                }
                case 3: {
                    return "doFullBackup";
                }
                case 2: {
                    return "doRestore";
                }
                case 1: 
            }
            return "doBackup";
        }

        public static boolean setDefaultImpl(IBackupAgent iBackupAgent) {
            if (Proxy.sDefaultImpl == null && iBackupAgent != null) {
                Proxy.sDefaultImpl = iBackupAgent;
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
        public boolean onTransact(int n, Parcel parcel, Parcel object, int n2) throws RemoteException {
            if (n != 1598968902) {
                switch (n) {
                    default: {
                        return super.onTransact(n, parcel, (Parcel)object, n2);
                    }
                    case 8: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.fail(parcel.readString());
                        return true;
                    }
                    case 7: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.doRestoreFinished(parcel.readInt(), IBackupManager.Stub.asInterface(parcel.readStrongBinder()));
                        return true;
                    }
                    case 6: {
                        parcel.enforceInterface(DESCRIPTOR);
                        object = parcel.readInt() != 0 ? ParcelFileDescriptor.CREATOR.createFromParcel(parcel) : null;
                        this.doRestoreFile((ParcelFileDescriptor)object, parcel.readLong(), parcel.readInt(), parcel.readString(), parcel.readString(), parcel.readLong(), parcel.readLong(), parcel.readInt(), IBackupManager.Stub.asInterface(parcel.readStrongBinder()));
                        return true;
                    }
                    case 5: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.doQuotaExceeded(parcel.readLong(), parcel.readLong(), IBackupCallback.Stub.asInterface(parcel.readStrongBinder()));
                        return true;
                    }
                    case 4: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.doMeasureFullBackup(parcel.readLong(), parcel.readInt(), IBackupManager.Stub.asInterface(parcel.readStrongBinder()), parcel.readInt());
                        return true;
                    }
                    case 3: {
                        parcel.enforceInterface(DESCRIPTOR);
                        object = parcel.readInt() != 0 ? ParcelFileDescriptor.CREATOR.createFromParcel(parcel) : null;
                        this.doFullBackup((ParcelFileDescriptor)object, parcel.readLong(), parcel.readInt(), IBackupManager.Stub.asInterface(parcel.readStrongBinder()), parcel.readInt());
                        return true;
                    }
                    case 2: {
                        parcel.enforceInterface(DESCRIPTOR);
                        object = parcel.readInt() != 0 ? ParcelFileDescriptor.CREATOR.createFromParcel(parcel) : null;
                        long l = parcel.readLong();
                        ParcelFileDescriptor parcelFileDescriptor = parcel.readInt() != 0 ? ParcelFileDescriptor.CREATOR.createFromParcel(parcel) : null;
                        this.doRestore((ParcelFileDescriptor)object, l, parcelFileDescriptor, parcel.readInt(), IBackupManager.Stub.asInterface(parcel.readStrongBinder()));
                        return true;
                    }
                    case 1: 
                }
                parcel.enforceInterface(DESCRIPTOR);
                object = parcel.readInt() != 0 ? ParcelFileDescriptor.CREATOR.createFromParcel(parcel) : null;
                ParcelFileDescriptor parcelFileDescriptor = parcel.readInt() != 0 ? ParcelFileDescriptor.CREATOR.createFromParcel(parcel) : null;
                ParcelFileDescriptor parcelFileDescriptor2 = parcel.readInt() != 0 ? ParcelFileDescriptor.CREATOR.createFromParcel(parcel) : null;
                this.doBackup((ParcelFileDescriptor)object, parcelFileDescriptor, parcelFileDescriptor2, parcel.readLong(), IBackupCallback.Stub.asInterface(parcel.readStrongBinder()), parcel.readInt());
                return true;
            }
            ((Parcel)object).writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IBackupAgent {
            public static IBackupAgent sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            /*
             * Loose catch block
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public void doBackup(ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2, ParcelFileDescriptor parcelFileDescriptor3, long l, IBackupCallback iBackupCallback, int n) throws RemoteException {
                Parcel parcel;
                void var1_5;
                block13 : {
                    block12 : {
                        parcel = Parcel.obtain();
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (parcelFileDescriptor != null) {
                            parcel.writeInt(1);
                            parcelFileDescriptor.writeToParcel(parcel, 0);
                        } else {
                            parcel.writeInt(0);
                        }
                        if (parcelFileDescriptor2 != null) {
                            parcel.writeInt(1);
                            parcelFileDescriptor2.writeToParcel(parcel, 0);
                        } else {
                            parcel.writeInt(0);
                        }
                        if (parcelFileDescriptor3 != null) {
                            parcel.writeInt(1);
                            parcelFileDescriptor3.writeToParcel(parcel, 0);
                            break block12;
                        }
                        parcel.writeInt(0);
                    }
                    try {
                        parcel.writeLong(l);
                        IBinder iBinder = iBackupCallback != null ? iBackupCallback.asBinder() : null;
                        parcel.writeStrongBinder(iBinder);
                    }
                    catch (Throwable throwable) {}
                    try {
                        parcel.writeInt(n);
                        if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().doBackup(parcelFileDescriptor, parcelFileDescriptor2, parcelFileDescriptor3, l, iBackupCallback, n);
                            parcel.recycle();
                            return;
                        }
                        parcel.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block13;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                throw var1_5;
            }

            /*
             * Loose catch block
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public void doFullBackup(ParcelFileDescriptor parcelFileDescriptor, long l, int n, IBackupManager iBackupManager, int n2) throws RemoteException {
                Parcel parcel;
                void var1_7;
                block13 : {
                    block12 : {
                        parcel = Parcel.obtain();
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (parcelFileDescriptor != null) {
                            parcel.writeInt(1);
                            parcelFileDescriptor.writeToParcel(parcel, 0);
                            break block12;
                        }
                        parcel.writeInt(0);
                    }
                    try {
                        parcel.writeLong(l);
                    }
                    catch (Throwable throwable) {
                        break block13;
                    }
                    try {
                        parcel.writeInt(n);
                        IBinder iBinder = iBackupManager != null ? iBackupManager.asBinder() : null;
                        parcel.writeStrongBinder(iBinder);
                    }
                    catch (Throwable throwable) {}
                    try {
                        parcel.writeInt(n2);
                    }
                    catch (Throwable throwable) {
                        break block13;
                    }
                    try {
                        if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().doFullBackup(parcelFileDescriptor, l, n, iBackupManager, n2);
                            parcel.recycle();
                            return;
                        }
                        parcel.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block13;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                throw var1_7;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void doMeasureFullBackup(long l, int n, IBackupManager iBackupManager, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeLong(l);
                    parcel.writeInt(n);
                    IBinder iBinder = iBackupManager != null ? iBackupManager.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n2);
                    if (this.mRemote.transact(4, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().doMeasureFullBackup(l, n, iBackupManager, n2);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void doQuotaExceeded(long l, long l2, IBackupCallback iBackupCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeLong(l);
                    parcel.writeLong(l2);
                    IBinder iBinder = iBackupCallback != null ? iBackupCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(5, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().doQuotaExceeded(l, l2, iBackupCallback);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Loose catch block
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public void doRestore(ParcelFileDescriptor parcelFileDescriptor, long l, ParcelFileDescriptor parcelFileDescriptor2, int n, IBackupManager iBackupManager) throws RemoteException {
                Parcel parcel;
                void var1_6;
                block13 : {
                    block12 : {
                        block11 : {
                            parcel = Parcel.obtain();
                            parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                            if (parcelFileDescriptor != null) {
                                parcel.writeInt(1);
                                parcelFileDescriptor.writeToParcel(parcel, 0);
                                break block11;
                            }
                            parcel.writeInt(0);
                        }
                        try {
                            parcel.writeLong(l);
                            if (parcelFileDescriptor2 != null) {
                                parcel.writeInt(1);
                                parcelFileDescriptor2.writeToParcel(parcel, 0);
                                break block12;
                            }
                            parcel.writeInt(0);
                        }
                        catch (Throwable throwable) {}
                    }
                    try {
                        parcel.writeInt(n);
                        IBinder iBinder = iBackupManager != null ? iBackupManager.asBinder() : null;
                        parcel.writeStrongBinder(iBinder);
                    }
                    catch (Throwable throwable) {}
                    try {
                        if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().doRestore(parcelFileDescriptor, l, parcelFileDescriptor2, n, iBackupManager);
                            parcel.recycle();
                            return;
                        }
                        parcel.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block13;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                throw var1_6;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void doRestoreFile(ParcelFileDescriptor parcelFileDescriptor, long l, int n, String string2, String string3, long l2, long l3, int n2, IBackupManager iBackupManager) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelFileDescriptor != null) {
                        parcel.writeInt(1);
                        parcelFileDescriptor.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeLong(l);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    parcel.writeLong(l2);
                    parcel.writeLong(l3);
                    parcel.writeInt(n2);
                    IBinder iBinder = iBackupManager != null ? iBackupManager.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(6, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().doRestoreFile(parcelFileDescriptor, l, n, string2, string3, l2, l3, n2, iBackupManager);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void doRestoreFinished(int n, IBackupManager iBackupManager) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    IBinder iBinder = iBackupManager != null ? iBackupManager.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(7, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().doRestoreFinished(n, iBackupManager);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void fail(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(8, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().fail(string2);
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
        }

    }

}

