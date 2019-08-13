/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.backup;

import android.app.backup.IBackupManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.RemoteException;

public interface IObbBackupService
extends IInterface {
    public void backupObbs(String var1, ParcelFileDescriptor var2, int var3, IBackupManager var4) throws RemoteException;

    public void restoreObbFile(String var1, ParcelFileDescriptor var2, long var3, int var5, String var6, long var7, long var9, int var11, IBackupManager var12) throws RemoteException;

    public static class Default
    implements IObbBackupService {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void backupObbs(String string2, ParcelFileDescriptor parcelFileDescriptor, int n, IBackupManager iBackupManager) throws RemoteException {
        }

        @Override
        public void restoreObbFile(String string2, ParcelFileDescriptor parcelFileDescriptor, long l, int n, String string3, long l2, long l3, int n2, IBackupManager iBackupManager) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IObbBackupService {
        private static final String DESCRIPTOR = "com.android.internal.backup.IObbBackupService";
        static final int TRANSACTION_backupObbs = 1;
        static final int TRANSACTION_restoreObbFile = 2;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IObbBackupService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IObbBackupService) {
                return (IObbBackupService)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IObbBackupService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    return null;
                }
                return "restoreObbFile";
            }
            return "backupObbs";
        }

        public static boolean setDefaultImpl(IObbBackupService iObbBackupService) {
            if (Proxy.sDefaultImpl == null && iObbBackupService != null) {
                Proxy.sDefaultImpl = iObbBackupService;
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
            if (n != 1) {
                if (n != 2) {
                    if (n != 1598968902) {
                        return super.onTransact(n, parcel, (Parcel)object, n2);
                    }
                    ((Parcel)object).writeString(DESCRIPTOR);
                    return true;
                }
                parcel.enforceInterface(DESCRIPTOR);
                String string2 = parcel.readString();
                object = parcel.readInt() != 0 ? ParcelFileDescriptor.CREATOR.createFromParcel(parcel) : null;
                this.restoreObbFile(string2, (ParcelFileDescriptor)object, parcel.readLong(), parcel.readInt(), parcel.readString(), parcel.readLong(), parcel.readLong(), parcel.readInt(), IBackupManager.Stub.asInterface(parcel.readStrongBinder()));
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            String string3 = parcel.readString();
            object = parcel.readInt() != 0 ? ParcelFileDescriptor.CREATOR.createFromParcel(parcel) : null;
            this.backupObbs(string3, (ParcelFileDescriptor)object, parcel.readInt(), IBackupManager.Stub.asInterface(parcel.readStrongBinder()));
            return true;
        }

        private static class Proxy
        implements IObbBackupService {
            public static IObbBackupService sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void backupObbs(String string2, ParcelFileDescriptor parcelFileDescriptor, int n, IBackupManager iBackupManager) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (parcelFileDescriptor != null) {
                        parcel.writeInt(1);
                        parcelFileDescriptor.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    IBinder iBinder = iBackupManager != null ? iBackupManager.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(1, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().backupObbs(string2, parcelFileDescriptor, n, iBackupManager);
                    return;
                }
                finally {
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
            public void restoreObbFile(String string2, ParcelFileDescriptor parcelFileDescriptor, long l, int n, String string3, long l2, long l3, int n2, IBackupManager iBackupManager) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (parcelFileDescriptor != null) {
                        parcel.writeInt(1);
                        parcelFileDescriptor.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeLong(l);
                    parcel.writeInt(n);
                    parcel.writeString(string3);
                    parcel.writeLong(l2);
                    parcel.writeLong(l3);
                    parcel.writeInt(n2);
                    IBinder iBinder = iBackupManager != null ? iBackupManager.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(2, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().restoreObbFile(string2, parcelFileDescriptor, l, n, string3, l2, l3, n2, iBackupManager);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }
        }

    }

}

