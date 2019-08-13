/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.backup;

import android.app.backup.RestoreDescription;
import android.app.backup.RestoreSet;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.RemoteException;
import android.text.TextUtils;

public interface IBackupTransport
extends IInterface {
    public int abortFullRestore() throws RemoteException;

    public void cancelFullBackup() throws RemoteException;

    public int checkFullBackupSize(long var1) throws RemoteException;

    public int clearBackupData(PackageInfo var1) throws RemoteException;

    public Intent configurationIntent() throws RemoteException;

    public String currentDestinationString() throws RemoteException;

    public Intent dataManagementIntent() throws RemoteException;

    public CharSequence dataManagementIntentLabel() throws RemoteException;

    public int finishBackup() throws RemoteException;

    public void finishRestore() throws RemoteException;

    public RestoreSet[] getAvailableRestoreSets() throws RemoteException;

    public long getBackupQuota(String var1, boolean var2) throws RemoteException;

    public long getCurrentRestoreSet() throws RemoteException;

    public int getNextFullRestoreDataChunk(ParcelFileDescriptor var1) throws RemoteException;

    public int getRestoreData(ParcelFileDescriptor var1) throws RemoteException;

    public int getTransportFlags() throws RemoteException;

    public int initializeDevice() throws RemoteException;

    public boolean isAppEligibleForBackup(PackageInfo var1, boolean var2) throws RemoteException;

    public String name() throws RemoteException;

    public RestoreDescription nextRestorePackage() throws RemoteException;

    public int performBackup(PackageInfo var1, ParcelFileDescriptor var2, int var3) throws RemoteException;

    public int performFullBackup(PackageInfo var1, ParcelFileDescriptor var2, int var3) throws RemoteException;

    public long requestBackupTime() throws RemoteException;

    public long requestFullBackupTime() throws RemoteException;

    public int sendBackupData(int var1) throws RemoteException;

    public int startRestore(long var1, PackageInfo[] var3) throws RemoteException;

    public String transportDirName() throws RemoteException;

    public static class Default
    implements IBackupTransport {
        @Override
        public int abortFullRestore() throws RemoteException {
            return 0;
        }

        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void cancelFullBackup() throws RemoteException {
        }

        @Override
        public int checkFullBackupSize(long l) throws RemoteException {
            return 0;
        }

        @Override
        public int clearBackupData(PackageInfo packageInfo) throws RemoteException {
            return 0;
        }

        @Override
        public Intent configurationIntent() throws RemoteException {
            return null;
        }

        @Override
        public String currentDestinationString() throws RemoteException {
            return null;
        }

        @Override
        public Intent dataManagementIntent() throws RemoteException {
            return null;
        }

        @Override
        public CharSequence dataManagementIntentLabel() throws RemoteException {
            return null;
        }

        @Override
        public int finishBackup() throws RemoteException {
            return 0;
        }

        @Override
        public void finishRestore() throws RemoteException {
        }

        @Override
        public RestoreSet[] getAvailableRestoreSets() throws RemoteException {
            return null;
        }

        @Override
        public long getBackupQuota(String string2, boolean bl) throws RemoteException {
            return 0L;
        }

        @Override
        public long getCurrentRestoreSet() throws RemoteException {
            return 0L;
        }

        @Override
        public int getNextFullRestoreDataChunk(ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
            return 0;
        }

        @Override
        public int getRestoreData(ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
            return 0;
        }

        @Override
        public int getTransportFlags() throws RemoteException {
            return 0;
        }

        @Override
        public int initializeDevice() throws RemoteException {
            return 0;
        }

        @Override
        public boolean isAppEligibleForBackup(PackageInfo packageInfo, boolean bl) throws RemoteException {
            return false;
        }

        @Override
        public String name() throws RemoteException {
            return null;
        }

        @Override
        public RestoreDescription nextRestorePackage() throws RemoteException {
            return null;
        }

        @Override
        public int performBackup(PackageInfo packageInfo, ParcelFileDescriptor parcelFileDescriptor, int n) throws RemoteException {
            return 0;
        }

        @Override
        public int performFullBackup(PackageInfo packageInfo, ParcelFileDescriptor parcelFileDescriptor, int n) throws RemoteException {
            return 0;
        }

        @Override
        public long requestBackupTime() throws RemoteException {
            return 0L;
        }

        @Override
        public long requestFullBackupTime() throws RemoteException {
            return 0L;
        }

        @Override
        public int sendBackupData(int n) throws RemoteException {
            return 0;
        }

        @Override
        public int startRestore(long l, PackageInfo[] arrpackageInfo) throws RemoteException {
            return 0;
        }

        @Override
        public String transportDirName() throws RemoteException {
            return null;
        }
    }

    public static abstract class Stub
    extends Binder
    implements IBackupTransport {
        private static final String DESCRIPTOR = "com.android.internal.backup.IBackupTransport";
        static final int TRANSACTION_abortFullRestore = 26;
        static final int TRANSACTION_cancelFullBackup = 22;
        static final int TRANSACTION_checkFullBackupSize = 20;
        static final int TRANSACTION_clearBackupData = 10;
        static final int TRANSACTION_configurationIntent = 2;
        static final int TRANSACTION_currentDestinationString = 3;
        static final int TRANSACTION_dataManagementIntent = 4;
        static final int TRANSACTION_dataManagementIntentLabel = 5;
        static final int TRANSACTION_finishBackup = 11;
        static final int TRANSACTION_finishRestore = 17;
        static final int TRANSACTION_getAvailableRestoreSets = 12;
        static final int TRANSACTION_getBackupQuota = 24;
        static final int TRANSACTION_getCurrentRestoreSet = 13;
        static final int TRANSACTION_getNextFullRestoreDataChunk = 25;
        static final int TRANSACTION_getRestoreData = 16;
        static final int TRANSACTION_getTransportFlags = 27;
        static final int TRANSACTION_initializeDevice = 8;
        static final int TRANSACTION_isAppEligibleForBackup = 23;
        static final int TRANSACTION_name = 1;
        static final int TRANSACTION_nextRestorePackage = 15;
        static final int TRANSACTION_performBackup = 9;
        static final int TRANSACTION_performFullBackup = 19;
        static final int TRANSACTION_requestBackupTime = 7;
        static final int TRANSACTION_requestFullBackupTime = 18;
        static final int TRANSACTION_sendBackupData = 21;
        static final int TRANSACTION_startRestore = 14;
        static final int TRANSACTION_transportDirName = 6;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IBackupTransport asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IBackupTransport) {
                return (IBackupTransport)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IBackupTransport getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 27: {
                    return "getTransportFlags";
                }
                case 26: {
                    return "abortFullRestore";
                }
                case 25: {
                    return "getNextFullRestoreDataChunk";
                }
                case 24: {
                    return "getBackupQuota";
                }
                case 23: {
                    return "isAppEligibleForBackup";
                }
                case 22: {
                    return "cancelFullBackup";
                }
                case 21: {
                    return "sendBackupData";
                }
                case 20: {
                    return "checkFullBackupSize";
                }
                case 19: {
                    return "performFullBackup";
                }
                case 18: {
                    return "requestFullBackupTime";
                }
                case 17: {
                    return "finishRestore";
                }
                case 16: {
                    return "getRestoreData";
                }
                case 15: {
                    return "nextRestorePackage";
                }
                case 14: {
                    return "startRestore";
                }
                case 13: {
                    return "getCurrentRestoreSet";
                }
                case 12: {
                    return "getAvailableRestoreSets";
                }
                case 11: {
                    return "finishBackup";
                }
                case 10: {
                    return "clearBackupData";
                }
                case 9: {
                    return "performBackup";
                }
                case 8: {
                    return "initializeDevice";
                }
                case 7: {
                    return "requestBackupTime";
                }
                case 6: {
                    return "transportDirName";
                }
                case 5: {
                    return "dataManagementIntentLabel";
                }
                case 4: {
                    return "dataManagementIntent";
                }
                case 3: {
                    return "currentDestinationString";
                }
                case 2: {
                    return "configurationIntent";
                }
                case 1: 
            }
            return "name";
        }

        public static boolean setDefaultImpl(IBackupTransport iBackupTransport) {
            if (Proxy.sDefaultImpl == null && iBackupTransport != null) {
                Proxy.sDefaultImpl = iBackupTransport;
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
        public boolean onTransact(int n, Parcel object, Parcel parcel, int n2) throws RemoteException {
            if (n != 1598968902) {
                boolean bl = false;
                boolean bl2 = false;
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, parcel, n2);
                    }
                    case 27: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getTransportFlags();
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 26: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.abortFullRestore();
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 25: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ParcelFileDescriptor.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.getNextFullRestoreDataChunk((ParcelFileDescriptor)object);
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 24: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string2 = ((Parcel)object).readString();
                        if (((Parcel)object).readInt() != 0) {
                            bl2 = true;
                        }
                        long l = this.getBackupQuota(string2, bl2);
                        parcel.writeNoException();
                        parcel.writeLong(l);
                        return true;
                    }
                    case 23: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        PackageInfo packageInfo = ((Parcel)object).readInt() != 0 ? PackageInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        bl2 = bl;
                        if (((Parcel)object).readInt() != 0) {
                            bl2 = true;
                        }
                        n = this.isAppEligibleForBackup(packageInfo, bl2) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 22: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.cancelFullBackup();
                        parcel.writeNoException();
                        return true;
                    }
                    case 21: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.sendBackupData(((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 20: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.checkFullBackupSize(((Parcel)object).readLong());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 19: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        PackageInfo packageInfo = ((Parcel)object).readInt() != 0 ? PackageInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        ParcelFileDescriptor parcelFileDescriptor = ((Parcel)object).readInt() != 0 ? ParcelFileDescriptor.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.performFullBackup(packageInfo, parcelFileDescriptor, ((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 18: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        long l = this.requestFullBackupTime();
                        parcel.writeNoException();
                        parcel.writeLong(l);
                        return true;
                    }
                    case 17: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.finishRestore();
                        parcel.writeNoException();
                        return true;
                    }
                    case 16: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ParcelFileDescriptor.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.getRestoreData((ParcelFileDescriptor)object);
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 15: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.nextRestorePackage();
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((RestoreDescription)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 14: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.startRestore(((Parcel)object).readLong(), ((Parcel)object).createTypedArray(PackageInfo.CREATOR));
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 13: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        long l = this.getCurrentRestoreSet();
                        parcel.writeNoException();
                        parcel.writeLong(l);
                        return true;
                    }
                    case 12: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getAvailableRestoreSets();
                        parcel.writeNoException();
                        parcel.writeTypedArray((Parcelable[])object, 1);
                        return true;
                    }
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.finishBackup();
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? PackageInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.clearBackupData((PackageInfo)object);
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        PackageInfo packageInfo = ((Parcel)object).readInt() != 0 ? PackageInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        ParcelFileDescriptor parcelFileDescriptor = ((Parcel)object).readInt() != 0 ? ParcelFileDescriptor.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.performBackup(packageInfo, parcelFileDescriptor, ((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.initializeDevice();
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        long l = this.requestBackupTime();
                        parcel.writeNoException();
                        parcel.writeLong(l);
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.transportDirName();
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.dataManagementIntentLabel();
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            TextUtils.writeToParcel((CharSequence)object, parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.dataManagementIntent();
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((Intent)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.currentDestinationString();
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.configurationIntent();
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((Intent)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                object = this.name();
                parcel.writeNoException();
                parcel.writeString((String)object);
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IBackupTransport {
            public static IBackupTransport sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public int abortFullRestore() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(26, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().abortFullRestore();
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

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void cancelFullBackup() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(22, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().cancelFullBackup();
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
            public int checkFullBackupSize(long l) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeLong(l);
                    if (!this.mRemote.transact(20, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().checkFullBackupSize(l);
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

            @Override
            public int clearBackupData(PackageInfo packageInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (packageInfo != null) {
                        parcel.writeInt(1);
                        packageInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(10, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().clearBackupData(packageInfo);
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

            @Override
            public Intent configurationIntent() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(2, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        Intent intent = Stub.getDefaultImpl().configurationIntent();
                        parcel.recycle();
                        parcel2.recycle();
                        return intent;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                Intent intent = parcel.readInt() != 0 ? Intent.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return intent;
            }

            @Override
            public String currentDestinationString() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String string2 = Stub.getDefaultImpl().currentDestinationString();
                        return string2;
                    }
                    parcel2.readException();
                    String string3 = parcel2.readString();
                    return string3;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public Intent dataManagementIntent() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(4, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        Intent intent = Stub.getDefaultImpl().dataManagementIntent();
                        parcel.recycle();
                        parcel2.recycle();
                        return intent;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                Intent intent = parcel.readInt() != 0 ? Intent.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return intent;
            }

            @Override
            public CharSequence dataManagementIntentLabel() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(5, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        CharSequence charSequence = Stub.getDefaultImpl().dataManagementIntentLabel();
                        parcel.recycle();
                        parcel2.recycle();
                        return charSequence;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                CharSequence charSequence = parcel.readInt() != 0 ? TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return charSequence;
            }

            @Override
            public int finishBackup() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(11, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().finishBackup();
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

            @Override
            public void finishRestore() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(17, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().finishRestore();
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
            public RestoreSet[] getAvailableRestoreSets() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(12, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        RestoreSet[] arrrestoreSet = Stub.getDefaultImpl().getAvailableRestoreSets();
                        return arrrestoreSet;
                    }
                    parcel2.readException();
                    RestoreSet[] arrrestoreSet = parcel2.createTypedArray(RestoreSet.CREATOR);
                    return arrrestoreSet;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public long getBackupQuota(String string2, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeString(string2);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(24, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        long l = Stub.getDefaultImpl().getBackupQuota(string2, bl);
                        return l;
                    }
                    parcel2.readException();
                    long l = parcel2.readLong();
                    return l;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public long getCurrentRestoreSet() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(13, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        long l = Stub.getDefaultImpl().getCurrentRestoreSet();
                        return l;
                    }
                    parcel2.readException();
                    long l = parcel2.readLong();
                    return l;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public int getNextFullRestoreDataChunk(ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelFileDescriptor != null) {
                        parcel.writeInt(1);
                        parcelFileDescriptor.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(25, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getNextFullRestoreDataChunk(parcelFileDescriptor);
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

            @Override
            public int getRestoreData(ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelFileDescriptor != null) {
                        parcel.writeInt(1);
                        parcelFileDescriptor.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(16, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getRestoreData(parcelFileDescriptor);
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

            @Override
            public int getTransportFlags() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(27, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getTransportFlags();
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

            @Override
            public int initializeDevice() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(8, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().initializeDevice();
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
            public boolean isAppEligibleForBackup(PackageInfo packageInfo, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean bl2 = true;
                    if (packageInfo != null) {
                        parcel.writeInt(1);
                        packageInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    int n = bl ? 1 : 0;
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(23, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().isAppEligibleForBackup(packageInfo, bl);
                        parcel2.recycle();
                        parcel.recycle();
                        return bl;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    bl = n != 0 ? bl2 : false;
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            @Override
            public String name() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String string2 = Stub.getDefaultImpl().name();
                        return string2;
                    }
                    parcel2.readException();
                    String string3 = parcel2.readString();
                    return string3;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public RestoreDescription nextRestorePackage() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(15, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        RestoreDescription restoreDescription = Stub.getDefaultImpl().nextRestorePackage();
                        parcel.recycle();
                        parcel2.recycle();
                        return restoreDescription;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                RestoreDescription restoreDescription = parcel.readInt() != 0 ? RestoreDescription.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return restoreDescription;
            }

            @Override
            public int performBackup(PackageInfo packageInfo, ParcelFileDescriptor parcelFileDescriptor, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (packageInfo != null) {
                        parcel.writeInt(1);
                        packageInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (parcelFileDescriptor != null) {
                        parcel.writeInt(1);
                        parcelFileDescriptor.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(9, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().performBackup(packageInfo, parcelFileDescriptor, n);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int performFullBackup(PackageInfo packageInfo, ParcelFileDescriptor parcelFileDescriptor, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (packageInfo != null) {
                        parcel.writeInt(1);
                        packageInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (parcelFileDescriptor != null) {
                        parcel.writeInt(1);
                        parcelFileDescriptor.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(19, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().performFullBackup(packageInfo, parcelFileDescriptor, n);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public long requestBackupTime() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(7, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        long l = Stub.getDefaultImpl().requestBackupTime();
                        return l;
                    }
                    parcel2.readException();
                    long l = parcel2.readLong();
                    return l;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public long requestFullBackupTime() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(18, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        long l = Stub.getDefaultImpl().requestFullBackupTime();
                        return l;
                    }
                    parcel2.readException();
                    long l = parcel2.readLong();
                    return l;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int sendBackupData(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(21, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().sendBackupData(n);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int startRestore(long l, PackageInfo[] arrpackageInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeLong(l);
                    parcel.writeTypedArray((Parcelable[])arrpackageInfo, 0);
                    if (!this.mRemote.transact(14, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().startRestore(l, arrpackageInfo);
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

            @Override
            public String transportDirName() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String string2 = Stub.getDefaultImpl().transportDirName();
                        return string2;
                    }
                    parcel2.readException();
                    String string3 = parcel2.readString();
                    return string3;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }
        }

    }

}

