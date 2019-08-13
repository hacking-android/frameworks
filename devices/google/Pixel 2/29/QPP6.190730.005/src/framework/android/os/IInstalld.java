/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.io.FileDescriptor;

public interface IInstalld
extends IInterface {
    public static final int FLAG_CLEAR_CACHE_ONLY = 16;
    public static final int FLAG_CLEAR_CODE_CACHE_ONLY = 32;
    public static final int FLAG_FORCE = 8192;
    public static final int FLAG_FREE_CACHE_NOOP = 1024;
    public static final int FLAG_FREE_CACHE_V2 = 256;
    public static final int FLAG_FREE_CACHE_V2_DEFY_QUOTA = 512;
    public static final int FLAG_STORAGE_CE = 2;
    public static final int FLAG_STORAGE_DE = 1;
    public static final int FLAG_STORAGE_EXTERNAL = 4;
    public static final int FLAG_USE_QUOTA = 4096;

    public void assertFsverityRootHashMatches(String var1, byte[] var2) throws RemoteException;

    public void clearAppData(String var1, String var2, int var3, int var4, long var5) throws RemoteException;

    public void clearAppProfiles(String var1, String var2) throws RemoteException;

    public boolean compileLayouts(String var1, String var2, String var3, int var4) throws RemoteException;

    public boolean copySystemProfile(String var1, int var2, String var3, String var4) throws RemoteException;

    public long createAppData(String var1, String var2, int var3, int var4, int var5, String var6, int var7) throws RemoteException;

    public void createOatDir(String var1, String var2) throws RemoteException;

    public boolean createProfileSnapshot(int var1, String var2, String var3, String var4) throws RemoteException;

    public void createUserData(String var1, int var2, int var3, int var4) throws RemoteException;

    public void deleteOdex(String var1, String var2, String var3) throws RemoteException;

    public void destroyAppData(String var1, String var2, int var3, int var4, long var5) throws RemoteException;

    public void destroyAppDataSnapshot(String var1, String var2, int var3, long var4, int var6, int var7) throws RemoteException;

    public void destroyAppProfiles(String var1) throws RemoteException;

    public void destroyProfileSnapshot(String var1, String var2) throws RemoteException;

    public void destroyUserData(String var1, int var2, int var3) throws RemoteException;

    public void dexopt(String var1, int var2, String var3, String var4, int var5, String var6, int var7, String var8, String var9, String var10, String var11, boolean var12, int var13, String var14, String var15, String var16) throws RemoteException;

    public boolean dumpProfiles(int var1, String var2, String var3, String var4) throws RemoteException;

    public void fixupAppData(String var1, int var2) throws RemoteException;

    public void freeCache(String var1, long var2, long var4, int var6) throws RemoteException;

    public long[] getAppSize(String var1, String[] var2, int var3, int var4, int var5, long[] var6, String[] var7) throws RemoteException;

    public long[] getExternalSize(String var1, int var2, int var3, int[] var4) throws RemoteException;

    public long[] getUserSize(String var1, int var2, int var3, int[] var4) throws RemoteException;

    public byte[] hashSecondaryDexFile(String var1, String var2, int var3, String var4, int var5) throws RemoteException;

    public void idmap(String var1, String var2, int var3) throws RemoteException;

    public void installApkVerity(String var1, FileDescriptor var2, int var3) throws RemoteException;

    public void invalidateMounts() throws RemoteException;

    public boolean isQuotaSupported(String var1) throws RemoteException;

    public void linkFile(String var1, String var2, String var3) throws RemoteException;

    public void linkNativeLibraryDirectory(String var1, String var2, String var3, int var4) throws RemoteException;

    public void markBootComplete(String var1) throws RemoteException;

    public boolean mergeProfiles(int var1, String var2, String var3) throws RemoteException;

    public void migrateAppData(String var1, String var2, int var3, int var4) throws RemoteException;

    public void migrateLegacyObbData() throws RemoteException;

    public void moveAb(String var1, String var2, String var3) throws RemoteException;

    public void moveCompleteApp(String var1, String var2, String var3, String var4, int var5, String var6, int var7) throws RemoteException;

    public boolean prepareAppProfile(String var1, int var2, int var3, String var4, String var5, String var6) throws RemoteException;

    public boolean reconcileSecondaryDexFile(String var1, String var2, int var3, String[] var4, String var5, int var6) throws RemoteException;

    public void removeIdmap(String var1) throws RemoteException;

    public void restoreAppDataSnapshot(String var1, String var2, int var3, String var4, int var5, int var6, int var7) throws RemoteException;

    public void restoreconAppData(String var1, String var2, int var3, int var4, int var5, String var6) throws RemoteException;

    public void rmPackageDir(String var1) throws RemoteException;

    public void rmdex(String var1, String var2) throws RemoteException;

    public void setAppQuota(String var1, int var2, int var3, long var4) throws RemoteException;

    public long snapshotAppData(String var1, String var2, int var3, int var4, int var5) throws RemoteException;

    public static class Default
    implements IInstalld {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void assertFsverityRootHashMatches(String string2, byte[] arrby) throws RemoteException {
        }

        @Override
        public void clearAppData(String string2, String string3, int n, int n2, long l) throws RemoteException {
        }

        @Override
        public void clearAppProfiles(String string2, String string3) throws RemoteException {
        }

        @Override
        public boolean compileLayouts(String string2, String string3, String string4, int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean copySystemProfile(String string2, int n, String string3, String string4) throws RemoteException {
            return false;
        }

        @Override
        public long createAppData(String string2, String string3, int n, int n2, int n3, String string4, int n4) throws RemoteException {
            return 0L;
        }

        @Override
        public void createOatDir(String string2, String string3) throws RemoteException {
        }

        @Override
        public boolean createProfileSnapshot(int n, String string2, String string3, String string4) throws RemoteException {
            return false;
        }

        @Override
        public void createUserData(String string2, int n, int n2, int n3) throws RemoteException {
        }

        @Override
        public void deleteOdex(String string2, String string3, String string4) throws RemoteException {
        }

        @Override
        public void destroyAppData(String string2, String string3, int n, int n2, long l) throws RemoteException {
        }

        @Override
        public void destroyAppDataSnapshot(String string2, String string3, int n, long l, int n2, int n3) throws RemoteException {
        }

        @Override
        public void destroyAppProfiles(String string2) throws RemoteException {
        }

        @Override
        public void destroyProfileSnapshot(String string2, String string3) throws RemoteException {
        }

        @Override
        public void destroyUserData(String string2, int n, int n2) throws RemoteException {
        }

        @Override
        public void dexopt(String string2, int n, String string3, String string4, int n2, String string5, int n3, String string6, String string7, String string8, String string9, boolean bl, int n4, String string10, String string11, String string12) throws RemoteException {
        }

        @Override
        public boolean dumpProfiles(int n, String string2, String string3, String string4) throws RemoteException {
            return false;
        }

        @Override
        public void fixupAppData(String string2, int n) throws RemoteException {
        }

        @Override
        public void freeCache(String string2, long l, long l2, int n) throws RemoteException {
        }

        @Override
        public long[] getAppSize(String string2, String[] arrstring, int n, int n2, int n3, long[] arrl, String[] arrstring2) throws RemoteException {
            return null;
        }

        @Override
        public long[] getExternalSize(String string2, int n, int n2, int[] arrn) throws RemoteException {
            return null;
        }

        @Override
        public long[] getUserSize(String string2, int n, int n2, int[] arrn) throws RemoteException {
            return null;
        }

        @Override
        public byte[] hashSecondaryDexFile(String string2, String string3, int n, String string4, int n2) throws RemoteException {
            return null;
        }

        @Override
        public void idmap(String string2, String string3, int n) throws RemoteException {
        }

        @Override
        public void installApkVerity(String string2, FileDescriptor fileDescriptor, int n) throws RemoteException {
        }

        @Override
        public void invalidateMounts() throws RemoteException {
        }

        @Override
        public boolean isQuotaSupported(String string2) throws RemoteException {
            return false;
        }

        @Override
        public void linkFile(String string2, String string3, String string4) throws RemoteException {
        }

        @Override
        public void linkNativeLibraryDirectory(String string2, String string3, String string4, int n) throws RemoteException {
        }

        @Override
        public void markBootComplete(String string2) throws RemoteException {
        }

        @Override
        public boolean mergeProfiles(int n, String string2, String string3) throws RemoteException {
            return false;
        }

        @Override
        public void migrateAppData(String string2, String string3, int n, int n2) throws RemoteException {
        }

        @Override
        public void migrateLegacyObbData() throws RemoteException {
        }

        @Override
        public void moveAb(String string2, String string3, String string4) throws RemoteException {
        }

        @Override
        public void moveCompleteApp(String string2, String string3, String string4, String string5, int n, String string6, int n2) throws RemoteException {
        }

        @Override
        public boolean prepareAppProfile(String string2, int n, int n2, String string3, String string4, String string5) throws RemoteException {
            return false;
        }

        @Override
        public boolean reconcileSecondaryDexFile(String string2, String string3, int n, String[] arrstring, String string4, int n2) throws RemoteException {
            return false;
        }

        @Override
        public void removeIdmap(String string2) throws RemoteException {
        }

        @Override
        public void restoreAppDataSnapshot(String string2, String string3, int n, String string4, int n2, int n3, int n4) throws RemoteException {
        }

        @Override
        public void restoreconAppData(String string2, String string3, int n, int n2, int n3, String string4) throws RemoteException {
        }

        @Override
        public void rmPackageDir(String string2) throws RemoteException {
        }

        @Override
        public void rmdex(String string2, String string3) throws RemoteException {
        }

        @Override
        public void setAppQuota(String string2, int n, int n2, long l) throws RemoteException {
        }

        @Override
        public long snapshotAppData(String string2, String string3, int n, int n2, int n3) throws RemoteException {
            return 0L;
        }
    }

    public static abstract class Stub
    extends Binder
    implements IInstalld {
        private static final String DESCRIPTOR = "android.os.IInstalld";
        static final int TRANSACTION_assertFsverityRootHashMatches = 35;
        static final int TRANSACTION_clearAppData = 6;
        static final int TRANSACTION_clearAppProfiles = 20;
        static final int TRANSACTION_compileLayouts = 15;
        static final int TRANSACTION_copySystemProfile = 19;
        static final int TRANSACTION_createAppData = 3;
        static final int TRANSACTION_createOatDir = 30;
        static final int TRANSACTION_createProfileSnapshot = 22;
        static final int TRANSACTION_createUserData = 1;
        static final int TRANSACTION_deleteOdex = 33;
        static final int TRANSACTION_destroyAppData = 7;
        static final int TRANSACTION_destroyAppDataSnapshot = 43;
        static final int TRANSACTION_destroyAppProfiles = 21;
        static final int TRANSACTION_destroyProfileSnapshot = 23;
        static final int TRANSACTION_destroyUserData = 2;
        static final int TRANSACTION_dexopt = 14;
        static final int TRANSACTION_dumpProfiles = 18;
        static final int TRANSACTION_fixupAppData = 8;
        static final int TRANSACTION_freeCache = 28;
        static final int TRANSACTION_getAppSize = 9;
        static final int TRANSACTION_getExternalSize = 11;
        static final int TRANSACTION_getUserSize = 10;
        static final int TRANSACTION_hashSecondaryDexFile = 37;
        static final int TRANSACTION_idmap = 24;
        static final int TRANSACTION_installApkVerity = 34;
        static final int TRANSACTION_invalidateMounts = 38;
        static final int TRANSACTION_isQuotaSupported = 39;
        static final int TRANSACTION_linkFile = 31;
        static final int TRANSACTION_linkNativeLibraryDirectory = 29;
        static final int TRANSACTION_markBootComplete = 27;
        static final int TRANSACTION_mergeProfiles = 17;
        static final int TRANSACTION_migrateAppData = 5;
        static final int TRANSACTION_migrateLegacyObbData = 44;
        static final int TRANSACTION_moveAb = 32;
        static final int TRANSACTION_moveCompleteApp = 13;
        static final int TRANSACTION_prepareAppProfile = 40;
        static final int TRANSACTION_reconcileSecondaryDexFile = 36;
        static final int TRANSACTION_removeIdmap = 25;
        static final int TRANSACTION_restoreAppDataSnapshot = 42;
        static final int TRANSACTION_restoreconAppData = 4;
        static final int TRANSACTION_rmPackageDir = 26;
        static final int TRANSACTION_rmdex = 16;
        static final int TRANSACTION_setAppQuota = 12;
        static final int TRANSACTION_snapshotAppData = 41;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IInstalld asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IInstalld) {
                return (IInstalld)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IInstalld getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 44: {
                    return "migrateLegacyObbData";
                }
                case 43: {
                    return "destroyAppDataSnapshot";
                }
                case 42: {
                    return "restoreAppDataSnapshot";
                }
                case 41: {
                    return "snapshotAppData";
                }
                case 40: {
                    return "prepareAppProfile";
                }
                case 39: {
                    return "isQuotaSupported";
                }
                case 38: {
                    return "invalidateMounts";
                }
                case 37: {
                    return "hashSecondaryDexFile";
                }
                case 36: {
                    return "reconcileSecondaryDexFile";
                }
                case 35: {
                    return "assertFsverityRootHashMatches";
                }
                case 34: {
                    return "installApkVerity";
                }
                case 33: {
                    return "deleteOdex";
                }
                case 32: {
                    return "moveAb";
                }
                case 31: {
                    return "linkFile";
                }
                case 30: {
                    return "createOatDir";
                }
                case 29: {
                    return "linkNativeLibraryDirectory";
                }
                case 28: {
                    return "freeCache";
                }
                case 27: {
                    return "markBootComplete";
                }
                case 26: {
                    return "rmPackageDir";
                }
                case 25: {
                    return "removeIdmap";
                }
                case 24: {
                    return "idmap";
                }
                case 23: {
                    return "destroyProfileSnapshot";
                }
                case 22: {
                    return "createProfileSnapshot";
                }
                case 21: {
                    return "destroyAppProfiles";
                }
                case 20: {
                    return "clearAppProfiles";
                }
                case 19: {
                    return "copySystemProfile";
                }
                case 18: {
                    return "dumpProfiles";
                }
                case 17: {
                    return "mergeProfiles";
                }
                case 16: {
                    return "rmdex";
                }
                case 15: {
                    return "compileLayouts";
                }
                case 14: {
                    return "dexopt";
                }
                case 13: {
                    return "moveCompleteApp";
                }
                case 12: {
                    return "setAppQuota";
                }
                case 11: {
                    return "getExternalSize";
                }
                case 10: {
                    return "getUserSize";
                }
                case 9: {
                    return "getAppSize";
                }
                case 8: {
                    return "fixupAppData";
                }
                case 7: {
                    return "destroyAppData";
                }
                case 6: {
                    return "clearAppData";
                }
                case 5: {
                    return "migrateAppData";
                }
                case 4: {
                    return "restoreconAppData";
                }
                case 3: {
                    return "createAppData";
                }
                case 2: {
                    return "destroyUserData";
                }
                case 1: 
            }
            return "createUserData";
        }

        public static boolean setDefaultImpl(IInstalld iInstalld) {
            if (Proxy.sDefaultImpl == null && iInstalld != null) {
                Proxy.sDefaultImpl = iInstalld;
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
        public boolean onTransact(int n, Parcel arrl, Parcel parcel, int n2) throws RemoteException {
            if (n != 1598968902) {
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)arrl, parcel, n2);
                    }
                    case 44: {
                        arrl.enforceInterface(DESCRIPTOR);
                        this.migrateLegacyObbData();
                        parcel.writeNoException();
                        return true;
                    }
                    case 43: {
                        arrl.enforceInterface(DESCRIPTOR);
                        this.destroyAppDataSnapshot(arrl.readString(), arrl.readString(), arrl.readInt(), arrl.readLong(), arrl.readInt(), arrl.readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 42: {
                        arrl.enforceInterface(DESCRIPTOR);
                        this.restoreAppDataSnapshot(arrl.readString(), arrl.readString(), arrl.readInt(), arrl.readString(), arrl.readInt(), arrl.readInt(), arrl.readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 41: {
                        arrl.enforceInterface(DESCRIPTOR);
                        long l = this.snapshotAppData(arrl.readString(), arrl.readString(), arrl.readInt(), arrl.readInt(), arrl.readInt());
                        parcel.writeNoException();
                        parcel.writeLong(l);
                        return true;
                    }
                    case 40: {
                        arrl.enforceInterface(DESCRIPTOR);
                        n = this.prepareAppProfile(arrl.readString(), arrl.readInt(), arrl.readInt(), arrl.readString(), arrl.readString(), arrl.readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 39: {
                        arrl.enforceInterface(DESCRIPTOR);
                        n = this.isQuotaSupported(arrl.readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 38: {
                        arrl.enforceInterface(DESCRIPTOR);
                        this.invalidateMounts();
                        parcel.writeNoException();
                        return true;
                    }
                    case 37: {
                        arrl.enforceInterface(DESCRIPTOR);
                        arrl = this.hashSecondaryDexFile(arrl.readString(), arrl.readString(), arrl.readInt(), arrl.readString(), arrl.readInt());
                        parcel.writeNoException();
                        parcel.writeByteArray((byte[])arrl);
                        return true;
                    }
                    case 36: {
                        arrl.enforceInterface(DESCRIPTOR);
                        n = this.reconcileSecondaryDexFile(arrl.readString(), arrl.readString(), arrl.readInt(), arrl.createStringArray(), arrl.readString(), arrl.readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 35: {
                        arrl.enforceInterface(DESCRIPTOR);
                        this.assertFsverityRootHashMatches(arrl.readString(), arrl.createByteArray());
                        parcel.writeNoException();
                        return true;
                    }
                    case 34: {
                        arrl.enforceInterface(DESCRIPTOR);
                        this.installApkVerity(arrl.readString(), arrl.readRawFileDescriptor(), arrl.readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 33: {
                        arrl.enforceInterface(DESCRIPTOR);
                        this.deleteOdex(arrl.readString(), arrl.readString(), arrl.readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 32: {
                        arrl.enforceInterface(DESCRIPTOR);
                        this.moveAb(arrl.readString(), arrl.readString(), arrl.readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 31: {
                        arrl.enforceInterface(DESCRIPTOR);
                        this.linkFile(arrl.readString(), arrl.readString(), arrl.readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 30: {
                        arrl.enforceInterface(DESCRIPTOR);
                        this.createOatDir(arrl.readString(), arrl.readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 29: {
                        arrl.enforceInterface(DESCRIPTOR);
                        this.linkNativeLibraryDirectory(arrl.readString(), arrl.readString(), arrl.readString(), arrl.readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 28: {
                        arrl.enforceInterface(DESCRIPTOR);
                        this.freeCache(arrl.readString(), arrl.readLong(), arrl.readLong(), arrl.readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 27: {
                        arrl.enforceInterface(DESCRIPTOR);
                        this.markBootComplete(arrl.readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 26: {
                        arrl.enforceInterface(DESCRIPTOR);
                        this.rmPackageDir(arrl.readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 25: {
                        arrl.enforceInterface(DESCRIPTOR);
                        this.removeIdmap(arrl.readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 24: {
                        arrl.enforceInterface(DESCRIPTOR);
                        this.idmap(arrl.readString(), arrl.readString(), arrl.readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 23: {
                        arrl.enforceInterface(DESCRIPTOR);
                        this.destroyProfileSnapshot(arrl.readString(), arrl.readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 22: {
                        arrl.enforceInterface(DESCRIPTOR);
                        n = this.createProfileSnapshot(arrl.readInt(), arrl.readString(), arrl.readString(), arrl.readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 21: {
                        arrl.enforceInterface(DESCRIPTOR);
                        this.destroyAppProfiles(arrl.readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 20: {
                        arrl.enforceInterface(DESCRIPTOR);
                        this.clearAppProfiles(arrl.readString(), arrl.readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 19: {
                        arrl.enforceInterface(DESCRIPTOR);
                        n = this.copySystemProfile(arrl.readString(), arrl.readInt(), arrl.readString(), arrl.readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 18: {
                        arrl.enforceInterface(DESCRIPTOR);
                        n = this.dumpProfiles(arrl.readInt(), arrl.readString(), arrl.readString(), arrl.readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 17: {
                        arrl.enforceInterface(DESCRIPTOR);
                        n = this.mergeProfiles(arrl.readInt(), arrl.readString(), arrl.readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 16: {
                        arrl.enforceInterface(DESCRIPTOR);
                        this.rmdex(arrl.readString(), arrl.readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 15: {
                        arrl.enforceInterface(DESCRIPTOR);
                        n = this.compileLayouts(arrl.readString(), arrl.readString(), arrl.readString(), arrl.readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 14: {
                        arrl.enforceInterface(DESCRIPTOR);
                        String string2 = arrl.readString();
                        n = arrl.readInt();
                        String string3 = arrl.readString();
                        String string4 = arrl.readString();
                        int n3 = arrl.readInt();
                        String string5 = arrl.readString();
                        n2 = arrl.readInt();
                        String string6 = arrl.readString();
                        String string7 = arrl.readString();
                        String string8 = arrl.readString();
                        String string9 = arrl.readString();
                        boolean bl = arrl.readInt() != 0;
                        this.dexopt(string2, n, string3, string4, n3, string5, n2, string6, string7, string8, string9, bl, arrl.readInt(), arrl.readString(), arrl.readString(), arrl.readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 13: {
                        arrl.enforceInterface(DESCRIPTOR);
                        this.moveCompleteApp(arrl.readString(), arrl.readString(), arrl.readString(), arrl.readString(), arrl.readInt(), arrl.readString(), arrl.readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 12: {
                        arrl.enforceInterface(DESCRIPTOR);
                        this.setAppQuota(arrl.readString(), arrl.readInt(), arrl.readInt(), arrl.readLong());
                        parcel.writeNoException();
                        return true;
                    }
                    case 11: {
                        arrl.enforceInterface(DESCRIPTOR);
                        arrl = this.getExternalSize(arrl.readString(), arrl.readInt(), arrl.readInt(), arrl.createIntArray());
                        parcel.writeNoException();
                        parcel.writeLongArray(arrl);
                        return true;
                    }
                    case 10: {
                        arrl.enforceInterface(DESCRIPTOR);
                        arrl = this.getUserSize(arrl.readString(), arrl.readInt(), arrl.readInt(), arrl.createIntArray());
                        parcel.writeNoException();
                        parcel.writeLongArray(arrl);
                        return true;
                    }
                    case 9: {
                        arrl.enforceInterface(DESCRIPTOR);
                        arrl = this.getAppSize(arrl.readString(), arrl.createStringArray(), arrl.readInt(), arrl.readInt(), arrl.readInt(), arrl.createLongArray(), arrl.createStringArray());
                        parcel.writeNoException();
                        parcel.writeLongArray(arrl);
                        return true;
                    }
                    case 8: {
                        arrl.enforceInterface(DESCRIPTOR);
                        this.fixupAppData(arrl.readString(), arrl.readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 7: {
                        arrl.enforceInterface(DESCRIPTOR);
                        this.destroyAppData(arrl.readString(), arrl.readString(), arrl.readInt(), arrl.readInt(), arrl.readLong());
                        parcel.writeNoException();
                        return true;
                    }
                    case 6: {
                        arrl.enforceInterface(DESCRIPTOR);
                        this.clearAppData(arrl.readString(), arrl.readString(), arrl.readInt(), arrl.readInt(), arrl.readLong());
                        parcel.writeNoException();
                        return true;
                    }
                    case 5: {
                        arrl.enforceInterface(DESCRIPTOR);
                        this.migrateAppData(arrl.readString(), arrl.readString(), arrl.readInt(), arrl.readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 4: {
                        arrl.enforceInterface(DESCRIPTOR);
                        this.restoreconAppData(arrl.readString(), arrl.readString(), arrl.readInt(), arrl.readInt(), arrl.readInt(), arrl.readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 3: {
                        arrl.enforceInterface(DESCRIPTOR);
                        long l = this.createAppData(arrl.readString(), arrl.readString(), arrl.readInt(), arrl.readInt(), arrl.readInt(), arrl.readString(), arrl.readInt());
                        parcel.writeNoException();
                        parcel.writeLong(l);
                        return true;
                    }
                    case 2: {
                        arrl.enforceInterface(DESCRIPTOR);
                        this.destroyUserData(arrl.readString(), arrl.readInt(), arrl.readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 1: 
                }
                arrl.enforceInterface(DESCRIPTOR);
                this.createUserData(arrl.readString(), arrl.readInt(), arrl.readInt(), arrl.readInt());
                parcel.writeNoException();
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IInstalld {
            public static IInstalld sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void assertFsverityRootHashMatches(String string2, byte[] arrby) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeByteArray(arrby);
                    if (!this.mRemote.transact(35, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().assertFsverityRootHashMatches(string2, arrby);
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
             * Loose catch block
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public void clearAppData(String string2, String string3, int n, int n2, long l) throws RemoteException {
                Parcel parcel;
                void var1_8;
                Parcel parcel2;
                block14 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel2.writeString(string2);
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                    try {
                        parcel2.writeString(string3);
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                    try {
                        parcel2.writeInt(n);
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                    try {
                        parcel2.writeInt(n2);
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                    try {
                        parcel2.writeLong(l);
                        if (!this.mRemote.transact(6, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().clearAppData(string2, string3, n, n2, l);
                            parcel.recycle();
                            parcel2.recycle();
                            return;
                        }
                        parcel.readException();
                        parcel.recycle();
                        parcel2.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block14;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                throw var1_8;
            }

            @Override
            public void clearAppProfiles(String string2, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(20, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().clearAppProfiles(string2, string3);
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
            public boolean compileLayouts(String string2, String string3, String string4, int n) throws RemoteException {
                boolean bl;
                Parcel parcel;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeString(string2);
                        parcel2.writeString(string3);
                        parcel2.writeString(string4);
                        parcel2.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(15, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().compileLayouts(string2, string3, string4, n);
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public boolean copySystemProfile(String string2, int n, String string3, String string4) throws RemoteException {
                boolean bl;
                Parcel parcel;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeString(string2);
                        parcel2.writeInt(n);
                        parcel2.writeString(string3);
                        parcel2.writeString(string4);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(19, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().copySystemProfile(string2, n, string3, string4);
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
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
            public long createAppData(String string2, String string3, int n, int n2, int n3, String string4, int n4) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                void var1_8;
                block14 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel.writeString(string2);
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                    try {
                        parcel.writeString(string3);
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                    try {
                        parcel.writeInt(n);
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                    try {
                        parcel.writeInt(n2);
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                    try {
                        parcel.writeInt(n3);
                        parcel.writeString(string4);
                        parcel.writeInt(n4);
                        if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                            long l = Stub.getDefaultImpl().createAppData(string2, string3, n, n2, n3, string4, n4);
                            parcel2.recycle();
                            parcel.recycle();
                            return l;
                        }
                        parcel2.readException();
                        long l = parcel2.readLong();
                        parcel2.recycle();
                        parcel.recycle();
                        return l;
                    }
                    catch (Throwable throwable) {}
                    break block14;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel2.recycle();
                parcel.recycle();
                throw var1_8;
            }

            @Override
            public void createOatDir(String string2, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(30, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().createOatDir(string2, string3);
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
            public boolean createProfileSnapshot(int n, String string2, String string3, String string4) throws RemoteException {
                boolean bl;
                Parcel parcel;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeInt(n);
                        parcel2.writeString(string2);
                        parcel2.writeString(string3);
                        parcel2.writeString(string4);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(22, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().createProfileSnapshot(n, string2, string3, string4);
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public void createUserData(String string2, int n, int n2, int n3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().createUserData(string2, n, n2, n3);
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
            public void deleteOdex(String string2, String string3, String string4) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    parcel.writeString(string4);
                    if (!this.mRemote.transact(33, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().deleteOdex(string2, string3, string4);
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
             * Loose catch block
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public void destroyAppData(String string2, String string3, int n, int n2, long l) throws RemoteException {
                Parcel parcel;
                void var1_8;
                Parcel parcel2;
                block14 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel2.writeString(string2);
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                    try {
                        parcel2.writeString(string3);
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                    try {
                        parcel2.writeInt(n);
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                    try {
                        parcel2.writeInt(n2);
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                    try {
                        parcel2.writeLong(l);
                        if (!this.mRemote.transact(7, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().destroyAppData(string2, string3, n, n2, l);
                            parcel.recycle();
                            parcel2.recycle();
                            return;
                        }
                        parcel.readException();
                        parcel.recycle();
                        parcel2.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block14;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                throw var1_8;
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
            public void destroyAppDataSnapshot(String string2, String string3, int n, long l, int n2, int n3) throws RemoteException {
                Parcel parcel;
                void var1_7;
                Parcel parcel2;
                block12 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel.writeString(string2);
                    }
                    catch (Throwable throwable) {
                        break block12;
                    }
                    try {
                        parcel.writeString(string3);
                    }
                    catch (Throwable throwable) {
                        break block12;
                    }
                    try {
                        parcel.writeInt(n);
                    }
                    catch (Throwable throwable) {
                        break block12;
                    }
                    try {
                        parcel.writeLong(l);
                        parcel.writeInt(n2);
                        parcel.writeInt(n3);
                        if (!this.mRemote.transact(43, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().destroyAppDataSnapshot(string2, string3, n, l, n2, n3);
                            parcel2.recycle();
                            parcel.recycle();
                            return;
                        }
                        parcel2.readException();
                        parcel2.recycle();
                        parcel.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block12;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel2.recycle();
                parcel.recycle();
                throw var1_7;
            }

            @Override
            public void destroyAppProfiles(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(21, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().destroyAppProfiles(string2);
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
            public void destroyProfileSnapshot(String string2, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(23, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().destroyProfileSnapshot(string2, string3);
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
            public void destroyUserData(String string2, int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().destroyUserData(string2, n, n2);
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
            public void dexopt(String string2, int n, String string3, String string4, int n2, String string5, int n3, String string6, String string7, String string8, String string9, boolean bl, int n4, String string10, String string11, String string12) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeString(string2);
                parcel.writeInt(n);
                parcel.writeString(string3);
                parcel.writeString(string4);
                parcel.writeInt(n2);
                parcel.writeString(string5);
                parcel.writeInt(n3);
                parcel.writeString(string6);
                parcel.writeString(string7);
                parcel.writeString(string8);
                parcel.writeString(string9);
                int n5 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n5);
                    parcel.writeInt(n4);
                    parcel.writeString(string10);
                    parcel.writeString(string11);
                    parcel.writeString(string12);
                    if (!this.mRemote.transact(14, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().dexopt(string2, n, string3, string4, n2, string5, n3, string6, string7, string8, string9, bl, n4, string10, string11, string12);
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
            public boolean dumpProfiles(int n, String string2, String string3, String string4) throws RemoteException {
                boolean bl;
                Parcel parcel;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeInt(n);
                        parcel2.writeString(string2);
                        parcel2.writeString(string3);
                        parcel2.writeString(string4);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(18, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().dumpProfiles(n, string2, string3, string4);
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public void fixupAppData(String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(8, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().fixupAppData(string2, n);
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
             * Loose catch block
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public void freeCache(String string2, long l, long l2, int n) throws RemoteException {
                Parcel parcel;
                void var1_7;
                Parcel parcel2;
                block12 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel2.writeString(string2);
                    }
                    catch (Throwable throwable) {
                        break block12;
                    }
                    try {
                        parcel2.writeLong(l);
                    }
                    catch (Throwable throwable) {
                        break block12;
                    }
                    try {
                        parcel2.writeLong(l2);
                    }
                    catch (Throwable throwable) {
                        break block12;
                    }
                    try {
                        parcel2.writeInt(n);
                        if (!this.mRemote.transact(28, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().freeCache(string2, l, l2, n);
                            parcel.recycle();
                            parcel2.recycle();
                            return;
                        }
                        parcel.readException();
                        parcel.recycle();
                        parcel2.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block12;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                throw var1_7;
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
            public long[] getAppSize(String arrl, String[] arrstring, int n, int n2, int n3, long[] arrl2, String[] arrstring2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                void var1_8;
                block14 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel.writeString((String)arrl);
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                    try {
                        parcel.writeStringArray(arrstring);
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                    try {
                        parcel.writeInt(n);
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                    try {
                        parcel.writeInt(n2);
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                    try {
                        parcel.writeInt(n3);
                        parcel.writeLongArray(arrl2);
                        parcel.writeStringArray(arrstring2);
                        if (!this.mRemote.transact(9, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                            arrl = Stub.getDefaultImpl().getAppSize((String)arrl, arrstring, n, n2, n3, arrl2, arrstring2);
                            parcel2.recycle();
                            parcel.recycle();
                            return arrl;
                        }
                        parcel2.readException();
                        arrl = parcel2.createLongArray();
                        parcel2.recycle();
                        parcel.recycle();
                        return arrl;
                    }
                    catch (Throwable throwable) {}
                    break block14;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel2.recycle();
                parcel.recycle();
                throw var1_8;
            }

            @Override
            public long[] getExternalSize(String arrl, int n, int n2, int[] arrn) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString((String)arrl);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeIntArray(arrn);
                    if (!this.mRemote.transact(11, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrl = Stub.getDefaultImpl().getExternalSize((String)arrl, n, n2, arrn);
                        return arrl;
                    }
                    parcel2.readException();
                    arrl = parcel2.createLongArray();
                    return arrl;
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
            public long[] getUserSize(String arrl, int n, int n2, int[] arrn) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString((String)arrl);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeIntArray(arrn);
                    if (!this.mRemote.transact(10, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrl = Stub.getDefaultImpl().getUserSize((String)arrl, n, n2, arrn);
                        return arrl;
                    }
                    parcel2.readException();
                    arrl = parcel2.createLongArray();
                    return arrl;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public byte[] hashSecondaryDexFile(String arrby, String string2, int n, String string3, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString((String)arrby);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    parcel.writeString(string3);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(37, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrby = Stub.getDefaultImpl().hashSecondaryDexFile((String)arrby, string2, n, string3, n2);
                        return arrby;
                    }
                    parcel2.readException();
                    arrby = parcel2.createByteArray();
                    return arrby;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void idmap(String string2, String string3, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(24, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().idmap(string2, string3, n);
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
            public void installApkVerity(String string2, FileDescriptor fileDescriptor, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeRawFileDescriptor(fileDescriptor);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(34, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().installApkVerity(string2, fileDescriptor, n);
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
            public void invalidateMounts() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(38, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().invalidateMounts();
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
            public boolean isQuotaSupported(String string2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString(string2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(39, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isQuotaSupported(string2);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                int n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public void linkFile(String string2, String string3, String string4) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    parcel.writeString(string4);
                    if (!this.mRemote.transact(31, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().linkFile(string2, string3, string4);
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
            public void linkNativeLibraryDirectory(String string2, String string3, String string4, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    parcel.writeString(string4);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(29, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().linkNativeLibraryDirectory(string2, string3, string4, n);
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
            public void markBootComplete(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(27, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().markBootComplete(string2);
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
            public boolean mergeProfiles(int n, String string2, String string3) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        parcel.writeString(string2);
                        parcel.writeString(string3);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(17, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().mergeProfiles(n, string2, string3);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public void migrateAppData(String string2, String string3, int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().migrateAppData(string2, string3, n, n2);
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
            public void migrateLegacyObbData() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(44, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().migrateLegacyObbData();
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
            public void moveAb(String string2, String string3, String string4) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    parcel.writeString(string4);
                    if (!this.mRemote.transact(32, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().moveAb(string2, string3, string4);
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
             * Loose catch block
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public void moveCompleteApp(String string2, String string3, String string4, String string5, int n, String string6, int n2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                void var1_8;
                block14 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel.writeString(string2);
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                    try {
                        parcel.writeString(string3);
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                    try {
                        parcel.writeString(string4);
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                    try {
                        parcel.writeString(string5);
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                    try {
                        parcel.writeInt(n);
                        parcel.writeString(string6);
                        parcel.writeInt(n2);
                        if (!this.mRemote.transact(13, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().moveCompleteApp(string2, string3, string4, string5, n, string6, n2);
                            parcel2.recycle();
                            parcel.recycle();
                            return;
                        }
                        parcel2.readException();
                        parcel2.recycle();
                        parcel.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block14;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel2.recycle();
                parcel.recycle();
                throw var1_8;
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
            public boolean prepareAppProfile(String string2, int n, int n2, String string3, String string4, String string5) throws RemoteException {
                Parcel parcel;
                void var1_9;
                Parcel parcel2;
                block17 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel2.writeString(string2);
                    }
                    catch (Throwable throwable) {
                        break block17;
                    }
                    try {
                        parcel2.writeInt(n);
                    }
                    catch (Throwable throwable) {
                        break block17;
                    }
                    try {
                        parcel2.writeInt(n2);
                    }
                    catch (Throwable throwable) {
                        break block17;
                    }
                    try {
                        parcel2.writeString(string3);
                    }
                    catch (Throwable throwable) {
                        break block17;
                    }
                    try {
                        parcel2.writeString(string4);
                    }
                    catch (Throwable throwable) {
                        break block17;
                    }
                    try {
                        parcel2.writeString(string5);
                        IBinder iBinder = this.mRemote;
                        boolean bl = false;
                        if (!iBinder.transact(40, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            bl = Stub.getDefaultImpl().prepareAppProfile(string2, n, n2, string3, string4, string5);
                            parcel.recycle();
                            parcel2.recycle();
                            return bl;
                        }
                        parcel.readException();
                        n = parcel.readInt();
                        if (n != 0) {
                            bl = true;
                        }
                        parcel.recycle();
                        parcel2.recycle();
                        return bl;
                    }
                    catch (Throwable throwable) {}
                    break block17;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                throw var1_9;
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
            public boolean reconcileSecondaryDexFile(String string2, String string3, int n, String[] arrstring, String string4, int n2) throws RemoteException {
                Parcel parcel;
                void var1_9;
                Parcel parcel2;
                block17 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel2.writeString(string2);
                    }
                    catch (Throwable throwable) {
                        break block17;
                    }
                    try {
                        parcel2.writeString(string3);
                    }
                    catch (Throwable throwable) {
                        break block17;
                    }
                    try {
                        parcel2.writeInt(n);
                    }
                    catch (Throwable throwable) {
                        break block17;
                    }
                    try {
                        parcel2.writeStringArray(arrstring);
                    }
                    catch (Throwable throwable) {
                        break block17;
                    }
                    try {
                        parcel2.writeString(string4);
                    }
                    catch (Throwable throwable) {
                        break block17;
                    }
                    try {
                        parcel2.writeInt(n2);
                        IBinder iBinder = this.mRemote;
                        boolean bl = false;
                        if (!iBinder.transact(36, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            bl = Stub.getDefaultImpl().reconcileSecondaryDexFile(string2, string3, n, arrstring, string4, n2);
                            parcel.recycle();
                            parcel2.recycle();
                            return bl;
                        }
                        parcel.readException();
                        n = parcel.readInt();
                        if (n != 0) {
                            bl = true;
                        }
                        parcel.recycle();
                        parcel2.recycle();
                        return bl;
                    }
                    catch (Throwable throwable) {}
                    break block17;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                throw var1_9;
            }

            @Override
            public void removeIdmap(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(25, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeIdmap(string2);
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
             * Loose catch block
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public void restoreAppDataSnapshot(String string2, String string3, int n, String string4, int n2, int n3, int n4) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                void var1_8;
                block14 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel.writeString(string2);
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                    try {
                        parcel.writeString(string3);
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                    try {
                        parcel.writeInt(n);
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                    try {
                        parcel.writeString(string4);
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                    try {
                        parcel.writeInt(n2);
                        parcel.writeInt(n3);
                        parcel.writeInt(n4);
                        if (!this.mRemote.transact(42, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().restoreAppDataSnapshot(string2, string3, n, string4, n2, n3, n4);
                            parcel2.recycle();
                            parcel.recycle();
                            return;
                        }
                        parcel2.readException();
                        parcel2.recycle();
                        parcel.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block14;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel2.recycle();
                parcel.recycle();
                throw var1_8;
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
            public void restoreconAppData(String string2, String string3, int n, int n2, int n3, String string4) throws RemoteException {
                Parcel parcel;
                void var1_9;
                Parcel parcel2;
                block16 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel2.writeString(string2);
                    }
                    catch (Throwable throwable) {
                        break block16;
                    }
                    try {
                        parcel2.writeString(string3);
                    }
                    catch (Throwable throwable) {
                        break block16;
                    }
                    try {
                        parcel2.writeInt(n);
                    }
                    catch (Throwable throwable) {
                        break block16;
                    }
                    try {
                        parcel2.writeInt(n2);
                    }
                    catch (Throwable throwable) {
                        break block16;
                    }
                    try {
                        parcel2.writeInt(n3);
                    }
                    catch (Throwable throwable) {
                        break block16;
                    }
                    try {
                        parcel2.writeString(string4);
                        if (!this.mRemote.transact(4, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().restoreconAppData(string2, string3, n, n2, n3, string4);
                            parcel.recycle();
                            parcel2.recycle();
                            return;
                        }
                        parcel.readException();
                        parcel.recycle();
                        parcel2.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block16;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                throw var1_9;
            }

            @Override
            public void rmPackageDir(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(26, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().rmPackageDir(string2);
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
            public void rmdex(String string2, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(16, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().rmdex(string2, string3);
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
            public void setAppQuota(String string2, int n, int n2, long l) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeLong(l);
                    if (!this.mRemote.transact(12, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setAppQuota(string2, n, n2, l);
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
            public long snapshotAppData(String string2, String string3, int n, int n2, int n3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    if (!this.mRemote.transact(41, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        long l = Stub.getDefaultImpl().snapshotAppData(string2, string3, n, n2, n3);
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
        }

    }

}

