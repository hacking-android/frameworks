/*
 * Decompiled with CFR 0.145.
 */
package android.os.storage;

import android.content.pm.IPackageMoveObserver;
import android.content.res.ObbInfo;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.IVoldTaskListener;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.storage.DiskInfo;
import android.os.storage.IObbActionListener;
import android.os.storage.IStorageEventListener;
import android.os.storage.IStorageShutdownObserver;
import android.os.storage.StorageVolume;
import android.os.storage.VolumeInfo;
import android.os.storage.VolumeRecord;
import com.android.internal.os.AppFuseMount;

public interface IStorageManager
extends IInterface {
    public void abortChanges(String var1, boolean var2) throws RemoteException;

    public void abortIdleMaintenance() throws RemoteException;

    public void addUserKeyAuth(int var1, int var2, byte[] var3, byte[] var4) throws RemoteException;

    public void allocateBytes(String var1, long var2, int var4, String var5) throws RemoteException;

    public void benchmark(String var1, IVoldTaskListener var2) throws RemoteException;

    public int changeEncryptionPassword(int var1, String var2) throws RemoteException;

    public void clearPassword() throws RemoteException;

    public void commitChanges() throws RemoteException;

    public void createUserKey(int var1, int var2, boolean var3) throws RemoteException;

    public int decryptStorage(String var1) throws RemoteException;

    public void destroyUserKey(int var1) throws RemoteException;

    public void destroyUserStorage(String var1, int var2, int var3) throws RemoteException;

    public int encryptStorage(int var1, String var2) throws RemoteException;

    public void fixateNewestUserKeyAuth(int var1) throws RemoteException;

    public void forgetAllVolumes() throws RemoteException;

    public void forgetVolume(String var1) throws RemoteException;

    public void format(String var1) throws RemoteException;

    public void fstrim(int var1, IVoldTaskListener var2) throws RemoteException;

    public long getAllocatableBytes(String var1, int var2, String var3) throws RemoteException;

    public long getCacheQuotaBytes(String var1, int var2) throws RemoteException;

    public long getCacheSizeBytes(String var1, int var2) throws RemoteException;

    public DiskInfo[] getDisks() throws RemoteException;

    public int getEncryptionState() throws RemoteException;

    public String getField(String var1) throws RemoteException;

    public String getMountedObbPath(String var1) throws RemoteException;

    public String getPassword() throws RemoteException;

    public int getPasswordType() throws RemoteException;

    public String getPrimaryStorageUuid() throws RemoteException;

    public StorageVolume[] getVolumeList(int var1, String var2, int var3) throws RemoteException;

    public VolumeRecord[] getVolumeRecords(int var1) throws RemoteException;

    public VolumeInfo[] getVolumes(int var1) throws RemoteException;

    public boolean isConvertibleToFBE() throws RemoteException;

    public boolean isObbMounted(String var1) throws RemoteException;

    public boolean isUserKeyUnlocked(int var1) throws RemoteException;

    public long lastMaintenance() throws RemoteException;

    public void lockUserKey(int var1) throws RemoteException;

    public void mkdirs(String var1, String var2) throws RemoteException;

    public void mount(String var1) throws RemoteException;

    public void mountObb(String var1, String var2, String var3, IObbActionListener var4, int var5, ObbInfo var6) throws RemoteException;

    public AppFuseMount mountProxyFileDescriptorBridge() throws RemoteException;

    public boolean needsCheckpoint() throws RemoteException;

    public ParcelFileDescriptor openProxyFileDescriptor(int var1, int var2, int var3) throws RemoteException;

    public void partitionMixed(String var1, int var2) throws RemoteException;

    public void partitionPrivate(String var1) throws RemoteException;

    public void partitionPublic(String var1) throws RemoteException;

    public void prepareUserStorage(String var1, int var2, int var3, int var4) throws RemoteException;

    public void registerListener(IStorageEventListener var1) throws RemoteException;

    public void runIdleMaintenance() throws RemoteException;

    public void runMaintenance() throws RemoteException;

    public void setDebugFlags(int var1, int var2) throws RemoteException;

    public void setField(String var1, String var2) throws RemoteException;

    public void setPrimaryStorageUuid(String var1, IPackageMoveObserver var2) throws RemoteException;

    public void setVolumeNickname(String var1, String var2) throws RemoteException;

    public void setVolumeUserFlags(String var1, int var2, int var3) throws RemoteException;

    public void shutdown(IStorageShutdownObserver var1) throws RemoteException;

    public void startCheckpoint(int var1) throws RemoteException;

    public boolean supportsCheckpoint() throws RemoteException;

    public void unlockUserKey(int var1, int var2, byte[] var3, byte[] var4) throws RemoteException;

    public void unmount(String var1) throws RemoteException;

    public void unmountObb(String var1, boolean var2, IObbActionListener var3, int var4) throws RemoteException;

    public void unregisterListener(IStorageEventListener var1) throws RemoteException;

    public int verifyEncryptionPassword(String var1) throws RemoteException;

    public static class Default
    implements IStorageManager {
        @Override
        public void abortChanges(String string2, boolean bl) throws RemoteException {
        }

        @Override
        public void abortIdleMaintenance() throws RemoteException {
        }

        @Override
        public void addUserKeyAuth(int n, int n2, byte[] arrby, byte[] arrby2) throws RemoteException {
        }

        @Override
        public void allocateBytes(String string2, long l, int n, String string3) throws RemoteException {
        }

        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void benchmark(String string2, IVoldTaskListener iVoldTaskListener) throws RemoteException {
        }

        @Override
        public int changeEncryptionPassword(int n, String string2) throws RemoteException {
            return 0;
        }

        @Override
        public void clearPassword() throws RemoteException {
        }

        @Override
        public void commitChanges() throws RemoteException {
        }

        @Override
        public void createUserKey(int n, int n2, boolean bl) throws RemoteException {
        }

        @Override
        public int decryptStorage(String string2) throws RemoteException {
            return 0;
        }

        @Override
        public void destroyUserKey(int n) throws RemoteException {
        }

        @Override
        public void destroyUserStorage(String string2, int n, int n2) throws RemoteException {
        }

        @Override
        public int encryptStorage(int n, String string2) throws RemoteException {
            return 0;
        }

        @Override
        public void fixateNewestUserKeyAuth(int n) throws RemoteException {
        }

        @Override
        public void forgetAllVolumes() throws RemoteException {
        }

        @Override
        public void forgetVolume(String string2) throws RemoteException {
        }

        @Override
        public void format(String string2) throws RemoteException {
        }

        @Override
        public void fstrim(int n, IVoldTaskListener iVoldTaskListener) throws RemoteException {
        }

        @Override
        public long getAllocatableBytes(String string2, int n, String string3) throws RemoteException {
            return 0L;
        }

        @Override
        public long getCacheQuotaBytes(String string2, int n) throws RemoteException {
            return 0L;
        }

        @Override
        public long getCacheSizeBytes(String string2, int n) throws RemoteException {
            return 0L;
        }

        @Override
        public DiskInfo[] getDisks() throws RemoteException {
            return null;
        }

        @Override
        public int getEncryptionState() throws RemoteException {
            return 0;
        }

        @Override
        public String getField(String string2) throws RemoteException {
            return null;
        }

        @Override
        public String getMountedObbPath(String string2) throws RemoteException {
            return null;
        }

        @Override
        public String getPassword() throws RemoteException {
            return null;
        }

        @Override
        public int getPasswordType() throws RemoteException {
            return 0;
        }

        @Override
        public String getPrimaryStorageUuid() throws RemoteException {
            return null;
        }

        @Override
        public StorageVolume[] getVolumeList(int n, String string2, int n2) throws RemoteException {
            return null;
        }

        @Override
        public VolumeRecord[] getVolumeRecords(int n) throws RemoteException {
            return null;
        }

        @Override
        public VolumeInfo[] getVolumes(int n) throws RemoteException {
            return null;
        }

        @Override
        public boolean isConvertibleToFBE() throws RemoteException {
            return false;
        }

        @Override
        public boolean isObbMounted(String string2) throws RemoteException {
            return false;
        }

        @Override
        public boolean isUserKeyUnlocked(int n) throws RemoteException {
            return false;
        }

        @Override
        public long lastMaintenance() throws RemoteException {
            return 0L;
        }

        @Override
        public void lockUserKey(int n) throws RemoteException {
        }

        @Override
        public void mkdirs(String string2, String string3) throws RemoteException {
        }

        @Override
        public void mount(String string2) throws RemoteException {
        }

        @Override
        public void mountObb(String string2, String string3, String string4, IObbActionListener iObbActionListener, int n, ObbInfo obbInfo) throws RemoteException {
        }

        @Override
        public AppFuseMount mountProxyFileDescriptorBridge() throws RemoteException {
            return null;
        }

        @Override
        public boolean needsCheckpoint() throws RemoteException {
            return false;
        }

        @Override
        public ParcelFileDescriptor openProxyFileDescriptor(int n, int n2, int n3) throws RemoteException {
            return null;
        }

        @Override
        public void partitionMixed(String string2, int n) throws RemoteException {
        }

        @Override
        public void partitionPrivate(String string2) throws RemoteException {
        }

        @Override
        public void partitionPublic(String string2) throws RemoteException {
        }

        @Override
        public void prepareUserStorage(String string2, int n, int n2, int n3) throws RemoteException {
        }

        @Override
        public void registerListener(IStorageEventListener iStorageEventListener) throws RemoteException {
        }

        @Override
        public void runIdleMaintenance() throws RemoteException {
        }

        @Override
        public void runMaintenance() throws RemoteException {
        }

        @Override
        public void setDebugFlags(int n, int n2) throws RemoteException {
        }

        @Override
        public void setField(String string2, String string3) throws RemoteException {
        }

        @Override
        public void setPrimaryStorageUuid(String string2, IPackageMoveObserver iPackageMoveObserver) throws RemoteException {
        }

        @Override
        public void setVolumeNickname(String string2, String string3) throws RemoteException {
        }

        @Override
        public void setVolumeUserFlags(String string2, int n, int n2) throws RemoteException {
        }

        @Override
        public void shutdown(IStorageShutdownObserver iStorageShutdownObserver) throws RemoteException {
        }

        @Override
        public void startCheckpoint(int n) throws RemoteException {
        }

        @Override
        public boolean supportsCheckpoint() throws RemoteException {
            return false;
        }

        @Override
        public void unlockUserKey(int n, int n2, byte[] arrby, byte[] arrby2) throws RemoteException {
        }

        @Override
        public void unmount(String string2) throws RemoteException {
        }

        @Override
        public void unmountObb(String string2, boolean bl, IObbActionListener iObbActionListener, int n) throws RemoteException {
        }

        @Override
        public void unregisterListener(IStorageEventListener iStorageEventListener) throws RemoteException {
        }

        @Override
        public int verifyEncryptionPassword(String string2) throws RemoteException {
            return 0;
        }
    }

    public static abstract class Stub
    extends Binder
    implements IStorageManager {
        private static final String DESCRIPTOR = "android.os.storage.IStorageManager";
        static final int TRANSACTION_abortChanges = 88;
        static final int TRANSACTION_abortIdleMaintenance = 81;
        static final int TRANSACTION_addUserKeyAuth = 71;
        static final int TRANSACTION_allocateBytes = 79;
        static final int TRANSACTION_benchmark = 60;
        static final int TRANSACTION_changeEncryptionPassword = 29;
        static final int TRANSACTION_clearPassword = 38;
        static final int TRANSACTION_commitChanges = 84;
        static final int TRANSACTION_createUserKey = 62;
        static final int TRANSACTION_decryptStorage = 27;
        static final int TRANSACTION_destroyUserKey = 63;
        static final int TRANSACTION_destroyUserStorage = 68;
        static final int TRANSACTION_encryptStorage = 28;
        static final int TRANSACTION_fixateNewestUserKeyAuth = 72;
        static final int TRANSACTION_forgetAllVolumes = 57;
        static final int TRANSACTION_forgetVolume = 56;
        static final int TRANSACTION_format = 50;
        static final int TRANSACTION_fstrim = 73;
        static final int TRANSACTION_getAllocatableBytes = 78;
        static final int TRANSACTION_getCacheQuotaBytes = 76;
        static final int TRANSACTION_getCacheSizeBytes = 77;
        static final int TRANSACTION_getDisks = 45;
        static final int TRANSACTION_getEncryptionState = 32;
        static final int TRANSACTION_getField = 40;
        static final int TRANSACTION_getMountedObbPath = 25;
        static final int TRANSACTION_getPassword = 37;
        static final int TRANSACTION_getPasswordType = 36;
        static final int TRANSACTION_getPrimaryStorageUuid = 58;
        static final int TRANSACTION_getVolumeList = 30;
        static final int TRANSACTION_getVolumeRecords = 47;
        static final int TRANSACTION_getVolumes = 46;
        static final int TRANSACTION_isConvertibleToFBE = 69;
        static final int TRANSACTION_isObbMounted = 24;
        static final int TRANSACTION_isUserKeyUnlocked = 66;
        static final int TRANSACTION_lastMaintenance = 42;
        static final int TRANSACTION_lockUserKey = 65;
        static final int TRANSACTION_mkdirs = 35;
        static final int TRANSACTION_mount = 48;
        static final int TRANSACTION_mountObb = 22;
        static final int TRANSACTION_mountProxyFileDescriptorBridge = 74;
        static final int TRANSACTION_needsCheckpoint = 87;
        static final int TRANSACTION_openProxyFileDescriptor = 75;
        static final int TRANSACTION_partitionMixed = 53;
        static final int TRANSACTION_partitionPrivate = 52;
        static final int TRANSACTION_partitionPublic = 51;
        static final int TRANSACTION_prepareUserStorage = 67;
        static final int TRANSACTION_registerListener = 1;
        static final int TRANSACTION_runIdleMaintenance = 80;
        static final int TRANSACTION_runMaintenance = 43;
        static final int TRANSACTION_setDebugFlags = 61;
        static final int TRANSACTION_setField = 39;
        static final int TRANSACTION_setPrimaryStorageUuid = 59;
        static final int TRANSACTION_setVolumeNickname = 54;
        static final int TRANSACTION_setVolumeUserFlags = 55;
        static final int TRANSACTION_shutdown = 20;
        static final int TRANSACTION_startCheckpoint = 86;
        static final int TRANSACTION_supportsCheckpoint = 85;
        static final int TRANSACTION_unlockUserKey = 64;
        static final int TRANSACTION_unmount = 49;
        static final int TRANSACTION_unmountObb = 23;
        static final int TRANSACTION_unregisterListener = 2;
        static final int TRANSACTION_verifyEncryptionPassword = 33;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IStorageManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IStorageManager) {
                return (IStorageManager)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IStorageManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 20) {
                        if (n != 32) {
                            if (n != 33) {
                                if (n != 42) {
                                    if (n != 43) {
                                        switch (n) {
                                            default: {
                                                switch (n) {
                                                    default: {
                                                        switch (n) {
                                                            default: {
                                                                switch (n) {
                                                                    default: {
                                                                        switch (n) {
                                                                            default: {
                                                                                switch (n) {
                                                                                    default: {
                                                                                        return null;
                                                                                    }
                                                                                    case 88: {
                                                                                        return "abortChanges";
                                                                                    }
                                                                                    case 87: {
                                                                                        return "needsCheckpoint";
                                                                                    }
                                                                                    case 86: {
                                                                                        return "startCheckpoint";
                                                                                    }
                                                                                    case 85: {
                                                                                        return "supportsCheckpoint";
                                                                                    }
                                                                                    case 84: 
                                                                                }
                                                                                return "commitChanges";
                                                                            }
                                                                            case 81: {
                                                                                return "abortIdleMaintenance";
                                                                            }
                                                                            case 80: {
                                                                                return "runIdleMaintenance";
                                                                            }
                                                                            case 79: {
                                                                                return "allocateBytes";
                                                                            }
                                                                            case 78: {
                                                                                return "getAllocatableBytes";
                                                                            }
                                                                            case 77: {
                                                                                return "getCacheSizeBytes";
                                                                            }
                                                                            case 76: {
                                                                                return "getCacheQuotaBytes";
                                                                            }
                                                                            case 75: {
                                                                                return "openProxyFileDescriptor";
                                                                            }
                                                                            case 74: {
                                                                                return "mountProxyFileDescriptorBridge";
                                                                            }
                                                                            case 73: {
                                                                                return "fstrim";
                                                                            }
                                                                            case 72: {
                                                                                return "fixateNewestUserKeyAuth";
                                                                            }
                                                                            case 71: 
                                                                        }
                                                                        return "addUserKeyAuth";
                                                                    }
                                                                    case 69: {
                                                                        return "isConvertibleToFBE";
                                                                    }
                                                                    case 68: {
                                                                        return "destroyUserStorage";
                                                                    }
                                                                    case 67: {
                                                                        return "prepareUserStorage";
                                                                    }
                                                                    case 66: {
                                                                        return "isUserKeyUnlocked";
                                                                    }
                                                                    case 65: {
                                                                        return "lockUserKey";
                                                                    }
                                                                    case 64: {
                                                                        return "unlockUserKey";
                                                                    }
                                                                    case 63: {
                                                                        return "destroyUserKey";
                                                                    }
                                                                    case 62: {
                                                                        return "createUserKey";
                                                                    }
                                                                    case 61: {
                                                                        return "setDebugFlags";
                                                                    }
                                                                    case 60: {
                                                                        return "benchmark";
                                                                    }
                                                                    case 59: {
                                                                        return "setPrimaryStorageUuid";
                                                                    }
                                                                    case 58: {
                                                                        return "getPrimaryStorageUuid";
                                                                    }
                                                                    case 57: {
                                                                        return "forgetAllVolumes";
                                                                    }
                                                                    case 56: {
                                                                        return "forgetVolume";
                                                                    }
                                                                    case 55: {
                                                                        return "setVolumeUserFlags";
                                                                    }
                                                                    case 54: {
                                                                        return "setVolumeNickname";
                                                                    }
                                                                    case 53: {
                                                                        return "partitionMixed";
                                                                    }
                                                                    case 52: {
                                                                        return "partitionPrivate";
                                                                    }
                                                                    case 51: {
                                                                        return "partitionPublic";
                                                                    }
                                                                    case 50: {
                                                                        return "format";
                                                                    }
                                                                    case 49: {
                                                                        return "unmount";
                                                                    }
                                                                    case 48: {
                                                                        return "mount";
                                                                    }
                                                                    case 47: {
                                                                        return "getVolumeRecords";
                                                                    }
                                                                    case 46: {
                                                                        return "getVolumes";
                                                                    }
                                                                    case 45: 
                                                                }
                                                                return "getDisks";
                                                            }
                                                            case 40: {
                                                                return "getField";
                                                            }
                                                            case 39: {
                                                                return "setField";
                                                            }
                                                            case 38: {
                                                                return "clearPassword";
                                                            }
                                                            case 37: {
                                                                return "getPassword";
                                                            }
                                                            case 36: {
                                                                return "getPasswordType";
                                                            }
                                                            case 35: 
                                                        }
                                                        return "mkdirs";
                                                    }
                                                    case 30: {
                                                        return "getVolumeList";
                                                    }
                                                    case 29: {
                                                        return "changeEncryptionPassword";
                                                    }
                                                    case 28: {
                                                        return "encryptStorage";
                                                    }
                                                    case 27: 
                                                }
                                                return "decryptStorage";
                                            }
                                            case 25: {
                                                return "getMountedObbPath";
                                            }
                                            case 24: {
                                                return "isObbMounted";
                                            }
                                            case 23: {
                                                return "unmountObb";
                                            }
                                            case 22: 
                                        }
                                        return "mountObb";
                                    }
                                    return "runMaintenance";
                                }
                                return "lastMaintenance";
                            }
                            return "verifyEncryptionPassword";
                        }
                        return "getEncryptionState";
                    }
                    return "shutdown";
                }
                return "unregisterListener";
            }
            return "registerListener";
        }

        public static boolean setDefaultImpl(IStorageManager iStorageManager) {
            if (Proxy.sDefaultImpl == null && iStorageManager != null) {
                Proxy.sDefaultImpl = iStorageManager;
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
            if (n != 1) {
                if (n != 2) {
                    if (n != 20) {
                        if (n != 1598968902) {
                            if (n != 32) {
                                if (n != 33) {
                                    if (n != 42) {
                                        if (n != 43) {
                                            boolean bl = false;
                                            boolean bl2 = false;
                                            boolean bl3 = false;
                                            switch (n) {
                                                default: {
                                                    switch (n) {
                                                        default: {
                                                            switch (n) {
                                                                default: {
                                                                    switch (n) {
                                                                        default: {
                                                                            switch (n) {
                                                                                default: {
                                                                                    switch (n) {
                                                                                        default: {
                                                                                            return super.onTransact(n, (Parcel)object, parcel, n2);
                                                                                        }
                                                                                        case 88: {
                                                                                            ((Parcel)object).enforceInterface(DESCRIPTOR);
                                                                                            String string2 = ((Parcel)object).readString();
                                                                                            if (((Parcel)object).readInt() != 0) {
                                                                                                bl3 = true;
                                                                                            }
                                                                                            this.abortChanges(string2, bl3);
                                                                                            parcel.writeNoException();
                                                                                            return true;
                                                                                        }
                                                                                        case 87: {
                                                                                            ((Parcel)object).enforceInterface(DESCRIPTOR);
                                                                                            n = this.needsCheckpoint() ? 1 : 0;
                                                                                            parcel.writeNoException();
                                                                                            parcel.writeInt(n);
                                                                                            return true;
                                                                                        }
                                                                                        case 86: {
                                                                                            ((Parcel)object).enforceInterface(DESCRIPTOR);
                                                                                            this.startCheckpoint(((Parcel)object).readInt());
                                                                                            parcel.writeNoException();
                                                                                            return true;
                                                                                        }
                                                                                        case 85: {
                                                                                            ((Parcel)object).enforceInterface(DESCRIPTOR);
                                                                                            n = this.supportsCheckpoint() ? 1 : 0;
                                                                                            parcel.writeNoException();
                                                                                            parcel.writeInt(n);
                                                                                            return true;
                                                                                        }
                                                                                        case 84: 
                                                                                    }
                                                                                    ((Parcel)object).enforceInterface(DESCRIPTOR);
                                                                                    this.commitChanges();
                                                                                    parcel.writeNoException();
                                                                                    return true;
                                                                                }
                                                                                case 81: {
                                                                                    ((Parcel)object).enforceInterface(DESCRIPTOR);
                                                                                    this.abortIdleMaintenance();
                                                                                    parcel.writeNoException();
                                                                                    return true;
                                                                                }
                                                                                case 80: {
                                                                                    ((Parcel)object).enforceInterface(DESCRIPTOR);
                                                                                    this.runIdleMaintenance();
                                                                                    parcel.writeNoException();
                                                                                    return true;
                                                                                }
                                                                                case 79: {
                                                                                    ((Parcel)object).enforceInterface(DESCRIPTOR);
                                                                                    this.allocateBytes(((Parcel)object).readString(), ((Parcel)object).readLong(), ((Parcel)object).readInt(), ((Parcel)object).readString());
                                                                                    parcel.writeNoException();
                                                                                    return true;
                                                                                }
                                                                                case 78: {
                                                                                    ((Parcel)object).enforceInterface(DESCRIPTOR);
                                                                                    long l = this.getAllocatableBytes(((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readString());
                                                                                    parcel.writeNoException();
                                                                                    parcel.writeLong(l);
                                                                                    return true;
                                                                                }
                                                                                case 77: {
                                                                                    ((Parcel)object).enforceInterface(DESCRIPTOR);
                                                                                    long l = this.getCacheSizeBytes(((Parcel)object).readString(), ((Parcel)object).readInt());
                                                                                    parcel.writeNoException();
                                                                                    parcel.writeLong(l);
                                                                                    return true;
                                                                                }
                                                                                case 76: {
                                                                                    ((Parcel)object).enforceInterface(DESCRIPTOR);
                                                                                    long l = this.getCacheQuotaBytes(((Parcel)object).readString(), ((Parcel)object).readInt());
                                                                                    parcel.writeNoException();
                                                                                    parcel.writeLong(l);
                                                                                    return true;
                                                                                }
                                                                                case 75: {
                                                                                    ((Parcel)object).enforceInterface(DESCRIPTOR);
                                                                                    object = this.openProxyFileDescriptor(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                                                                                    parcel.writeNoException();
                                                                                    if (object != null) {
                                                                                        parcel.writeInt(1);
                                                                                        ((ParcelFileDescriptor)object).writeToParcel(parcel, 1);
                                                                                    } else {
                                                                                        parcel.writeInt(0);
                                                                                    }
                                                                                    return true;
                                                                                }
                                                                                case 74: {
                                                                                    ((Parcel)object).enforceInterface(DESCRIPTOR);
                                                                                    object = this.mountProxyFileDescriptorBridge();
                                                                                    parcel.writeNoException();
                                                                                    if (object != null) {
                                                                                        parcel.writeInt(1);
                                                                                        ((AppFuseMount)object).writeToParcel(parcel, 1);
                                                                                    } else {
                                                                                        parcel.writeInt(0);
                                                                                    }
                                                                                    return true;
                                                                                }
                                                                                case 73: {
                                                                                    ((Parcel)object).enforceInterface(DESCRIPTOR);
                                                                                    this.fstrim(((Parcel)object).readInt(), IVoldTaskListener.Stub.asInterface(((Parcel)object).readStrongBinder()));
                                                                                    parcel.writeNoException();
                                                                                    return true;
                                                                                }
                                                                                case 72: {
                                                                                    ((Parcel)object).enforceInterface(DESCRIPTOR);
                                                                                    this.fixateNewestUserKeyAuth(((Parcel)object).readInt());
                                                                                    parcel.writeNoException();
                                                                                    return true;
                                                                                }
                                                                                case 71: 
                                                                            }
                                                                            ((Parcel)object).enforceInterface(DESCRIPTOR);
                                                                            this.addUserKeyAuth(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).createByteArray(), ((Parcel)object).createByteArray());
                                                                            parcel.writeNoException();
                                                                            return true;
                                                                        }
                                                                        case 69: {
                                                                            ((Parcel)object).enforceInterface(DESCRIPTOR);
                                                                            n = this.isConvertibleToFBE() ? 1 : 0;
                                                                            parcel.writeNoException();
                                                                            parcel.writeInt(n);
                                                                            return true;
                                                                        }
                                                                        case 68: {
                                                                            ((Parcel)object).enforceInterface(DESCRIPTOR);
                                                                            this.destroyUserStorage(((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                                                                            parcel.writeNoException();
                                                                            return true;
                                                                        }
                                                                        case 67: {
                                                                            ((Parcel)object).enforceInterface(DESCRIPTOR);
                                                                            this.prepareUserStorage(((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                                                                            parcel.writeNoException();
                                                                            return true;
                                                                        }
                                                                        case 66: {
                                                                            ((Parcel)object).enforceInterface(DESCRIPTOR);
                                                                            n = this.isUserKeyUnlocked(((Parcel)object).readInt()) ? 1 : 0;
                                                                            parcel.writeNoException();
                                                                            parcel.writeInt(n);
                                                                            return true;
                                                                        }
                                                                        case 65: {
                                                                            ((Parcel)object).enforceInterface(DESCRIPTOR);
                                                                            this.lockUserKey(((Parcel)object).readInt());
                                                                            parcel.writeNoException();
                                                                            return true;
                                                                        }
                                                                        case 64: {
                                                                            ((Parcel)object).enforceInterface(DESCRIPTOR);
                                                                            this.unlockUserKey(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).createByteArray(), ((Parcel)object).createByteArray());
                                                                            parcel.writeNoException();
                                                                            return true;
                                                                        }
                                                                        case 63: {
                                                                            ((Parcel)object).enforceInterface(DESCRIPTOR);
                                                                            this.destroyUserKey(((Parcel)object).readInt());
                                                                            parcel.writeNoException();
                                                                            return true;
                                                                        }
                                                                        case 62: {
                                                                            ((Parcel)object).enforceInterface(DESCRIPTOR);
                                                                            n2 = ((Parcel)object).readInt();
                                                                            n = ((Parcel)object).readInt();
                                                                            bl3 = bl;
                                                                            if (((Parcel)object).readInt() != 0) {
                                                                                bl3 = true;
                                                                            }
                                                                            this.createUserKey(n2, n, bl3);
                                                                            parcel.writeNoException();
                                                                            return true;
                                                                        }
                                                                        case 61: {
                                                                            ((Parcel)object).enforceInterface(DESCRIPTOR);
                                                                            this.setDebugFlags(((Parcel)object).readInt(), ((Parcel)object).readInt());
                                                                            parcel.writeNoException();
                                                                            return true;
                                                                        }
                                                                        case 60: {
                                                                            ((Parcel)object).enforceInterface(DESCRIPTOR);
                                                                            this.benchmark(((Parcel)object).readString(), IVoldTaskListener.Stub.asInterface(((Parcel)object).readStrongBinder()));
                                                                            parcel.writeNoException();
                                                                            return true;
                                                                        }
                                                                        case 59: {
                                                                            ((Parcel)object).enforceInterface(DESCRIPTOR);
                                                                            this.setPrimaryStorageUuid(((Parcel)object).readString(), IPackageMoveObserver.Stub.asInterface(((Parcel)object).readStrongBinder()));
                                                                            parcel.writeNoException();
                                                                            return true;
                                                                        }
                                                                        case 58: {
                                                                            ((Parcel)object).enforceInterface(DESCRIPTOR);
                                                                            object = this.getPrimaryStorageUuid();
                                                                            parcel.writeNoException();
                                                                            parcel.writeString((String)object);
                                                                            return true;
                                                                        }
                                                                        case 57: {
                                                                            ((Parcel)object).enforceInterface(DESCRIPTOR);
                                                                            this.forgetAllVolumes();
                                                                            parcel.writeNoException();
                                                                            return true;
                                                                        }
                                                                        case 56: {
                                                                            ((Parcel)object).enforceInterface(DESCRIPTOR);
                                                                            this.forgetVolume(((Parcel)object).readString());
                                                                            parcel.writeNoException();
                                                                            return true;
                                                                        }
                                                                        case 55: {
                                                                            ((Parcel)object).enforceInterface(DESCRIPTOR);
                                                                            this.setVolumeUserFlags(((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                                                                            parcel.writeNoException();
                                                                            return true;
                                                                        }
                                                                        case 54: {
                                                                            ((Parcel)object).enforceInterface(DESCRIPTOR);
                                                                            this.setVolumeNickname(((Parcel)object).readString(), ((Parcel)object).readString());
                                                                            parcel.writeNoException();
                                                                            return true;
                                                                        }
                                                                        case 53: {
                                                                            ((Parcel)object).enforceInterface(DESCRIPTOR);
                                                                            this.partitionMixed(((Parcel)object).readString(), ((Parcel)object).readInt());
                                                                            parcel.writeNoException();
                                                                            return true;
                                                                        }
                                                                        case 52: {
                                                                            ((Parcel)object).enforceInterface(DESCRIPTOR);
                                                                            this.partitionPrivate(((Parcel)object).readString());
                                                                            parcel.writeNoException();
                                                                            return true;
                                                                        }
                                                                        case 51: {
                                                                            ((Parcel)object).enforceInterface(DESCRIPTOR);
                                                                            this.partitionPublic(((Parcel)object).readString());
                                                                            parcel.writeNoException();
                                                                            return true;
                                                                        }
                                                                        case 50: {
                                                                            ((Parcel)object).enforceInterface(DESCRIPTOR);
                                                                            this.format(((Parcel)object).readString());
                                                                            parcel.writeNoException();
                                                                            return true;
                                                                        }
                                                                        case 49: {
                                                                            ((Parcel)object).enforceInterface(DESCRIPTOR);
                                                                            this.unmount(((Parcel)object).readString());
                                                                            parcel.writeNoException();
                                                                            return true;
                                                                        }
                                                                        case 48: {
                                                                            ((Parcel)object).enforceInterface(DESCRIPTOR);
                                                                            this.mount(((Parcel)object).readString());
                                                                            parcel.writeNoException();
                                                                            return true;
                                                                        }
                                                                        case 47: {
                                                                            ((Parcel)object).enforceInterface(DESCRIPTOR);
                                                                            object = this.getVolumeRecords(((Parcel)object).readInt());
                                                                            parcel.writeNoException();
                                                                            parcel.writeTypedArray((Parcelable[])object, 1);
                                                                            return true;
                                                                        }
                                                                        case 46: {
                                                                            ((Parcel)object).enforceInterface(DESCRIPTOR);
                                                                            object = this.getVolumes(((Parcel)object).readInt());
                                                                            parcel.writeNoException();
                                                                            parcel.writeTypedArray((Parcelable[])object, 1);
                                                                            return true;
                                                                        }
                                                                        case 45: 
                                                                    }
                                                                    ((Parcel)object).enforceInterface(DESCRIPTOR);
                                                                    object = this.getDisks();
                                                                    parcel.writeNoException();
                                                                    parcel.writeTypedArray((Parcelable[])object, 1);
                                                                    return true;
                                                                }
                                                                case 40: {
                                                                    ((Parcel)object).enforceInterface(DESCRIPTOR);
                                                                    object = this.getField(((Parcel)object).readString());
                                                                    parcel.writeNoException();
                                                                    parcel.writeString((String)object);
                                                                    return true;
                                                                }
                                                                case 39: {
                                                                    ((Parcel)object).enforceInterface(DESCRIPTOR);
                                                                    this.setField(((Parcel)object).readString(), ((Parcel)object).readString());
                                                                    return true;
                                                                }
                                                                case 38: {
                                                                    ((Parcel)object).enforceInterface(DESCRIPTOR);
                                                                    this.clearPassword();
                                                                    return true;
                                                                }
                                                                case 37: {
                                                                    ((Parcel)object).enforceInterface(DESCRIPTOR);
                                                                    object = this.getPassword();
                                                                    parcel.writeNoException();
                                                                    parcel.writeString((String)object);
                                                                    return true;
                                                                }
                                                                case 36: {
                                                                    ((Parcel)object).enforceInterface(DESCRIPTOR);
                                                                    n = this.getPasswordType();
                                                                    parcel.writeNoException();
                                                                    parcel.writeInt(n);
                                                                    return true;
                                                                }
                                                                case 35: 
                                                            }
                                                            ((Parcel)object).enforceInterface(DESCRIPTOR);
                                                            this.mkdirs(((Parcel)object).readString(), ((Parcel)object).readString());
                                                            parcel.writeNoException();
                                                            return true;
                                                        }
                                                        case 30: {
                                                            ((Parcel)object).enforceInterface(DESCRIPTOR);
                                                            object = this.getVolumeList(((Parcel)object).readInt(), ((Parcel)object).readString(), ((Parcel)object).readInt());
                                                            parcel.writeNoException();
                                                            parcel.writeTypedArray((Parcelable[])object, 1);
                                                            return true;
                                                        }
                                                        case 29: {
                                                            ((Parcel)object).enforceInterface(DESCRIPTOR);
                                                            n = this.changeEncryptionPassword(((Parcel)object).readInt(), ((Parcel)object).readString());
                                                            parcel.writeNoException();
                                                            parcel.writeInt(n);
                                                            return true;
                                                        }
                                                        case 28: {
                                                            ((Parcel)object).enforceInterface(DESCRIPTOR);
                                                            n = this.encryptStorage(((Parcel)object).readInt(), ((Parcel)object).readString());
                                                            parcel.writeNoException();
                                                            parcel.writeInt(n);
                                                            return true;
                                                        }
                                                        case 27: 
                                                    }
                                                    ((Parcel)object).enforceInterface(DESCRIPTOR);
                                                    n = this.decryptStorage(((Parcel)object).readString());
                                                    parcel.writeNoException();
                                                    parcel.writeInt(n);
                                                    return true;
                                                }
                                                case 25: {
                                                    ((Parcel)object).enforceInterface(DESCRIPTOR);
                                                    object = this.getMountedObbPath(((Parcel)object).readString());
                                                    parcel.writeNoException();
                                                    parcel.writeString((String)object);
                                                    return true;
                                                }
                                                case 24: {
                                                    ((Parcel)object).enforceInterface(DESCRIPTOR);
                                                    n = this.isObbMounted(((Parcel)object).readString()) ? 1 : 0;
                                                    parcel.writeNoException();
                                                    parcel.writeInt(n);
                                                    return true;
                                                }
                                                case 23: {
                                                    ((Parcel)object).enforceInterface(DESCRIPTOR);
                                                    String string3 = ((Parcel)object).readString();
                                                    bl3 = bl2;
                                                    if (((Parcel)object).readInt() != 0) {
                                                        bl3 = true;
                                                    }
                                                    this.unmountObb(string3, bl3, IObbActionListener.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readInt());
                                                    parcel.writeNoException();
                                                    return true;
                                                }
                                                case 22: 
                                            }
                                            ((Parcel)object).enforceInterface(DESCRIPTOR);
                                            String string4 = ((Parcel)object).readString();
                                            String string5 = ((Parcel)object).readString();
                                            String string6 = ((Parcel)object).readString();
                                            IObbActionListener iObbActionListener = IObbActionListener.Stub.asInterface(((Parcel)object).readStrongBinder());
                                            n = ((Parcel)object).readInt();
                                            object = ((Parcel)object).readInt() != 0 ? ObbInfo.CREATOR.createFromParcel((Parcel)object) : null;
                                            this.mountObb(string4, string5, string6, iObbActionListener, n, (ObbInfo)object);
                                            parcel.writeNoException();
                                            return true;
                                        }
                                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                                        this.runMaintenance();
                                        parcel.writeNoException();
                                        return true;
                                    }
                                    ((Parcel)object).enforceInterface(DESCRIPTOR);
                                    long l = this.lastMaintenance();
                                    parcel.writeNoException();
                                    parcel.writeLong(l);
                                    return true;
                                }
                                ((Parcel)object).enforceInterface(DESCRIPTOR);
                                n = this.verifyEncryptionPassword(((Parcel)object).readString());
                                parcel.writeNoException();
                                parcel.writeInt(n);
                                return true;
                            }
                            ((Parcel)object).enforceInterface(DESCRIPTOR);
                            n = this.getEncryptionState();
                            parcel.writeNoException();
                            parcel.writeInt(n);
                            return true;
                        }
                        parcel.writeString(DESCRIPTOR);
                        return true;
                    }
                    ((Parcel)object).enforceInterface(DESCRIPTOR);
                    this.shutdown(IStorageShutdownObserver.Stub.asInterface(((Parcel)object).readStrongBinder()));
                    parcel.writeNoException();
                    return true;
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                this.unregisterListener(IStorageEventListener.Stub.asInterface(((Parcel)object).readStrongBinder()));
                parcel.writeNoException();
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            this.registerListener(IStorageEventListener.Stub.asInterface(((Parcel)object).readStrongBinder()));
            parcel.writeNoException();
            return true;
        }

        private static class Proxy
        implements IStorageManager {
            public static IStorageManager sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public void abortChanges(String string2, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeString(string2);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(88, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().abortChanges(string2, bl);
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
            public void abortIdleMaintenance() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(81, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().abortIdleMaintenance();
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
            public void addUserKeyAuth(int n, int n2, byte[] arrby, byte[] arrby2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeByteArray(arrby);
                    parcel.writeByteArray(arrby2);
                    if (!this.mRemote.transact(71, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addUserKeyAuth(n, n2, arrby, arrby2);
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
            public void allocateBytes(String string2, long l, int n, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeLong(l);
                    parcel.writeInt(n);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(79, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().allocateBytes(string2, l, n, string3);
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
            public IBinder asBinder() {
                return this.mRemote;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void benchmark(String string2, IVoldTaskListener iVoldTaskListener) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    IBinder iBinder = iVoldTaskListener != null ? iVoldTaskListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(60, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().benchmark(string2, iVoldTaskListener);
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
            public int changeEncryptionPassword(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(29, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().changeEncryptionPassword(n, string2);
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
            public void clearPassword() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(38, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().clearPassword();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void commitChanges() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(84, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().commitChanges();
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
            public void createUserKey(int n, int n2, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                parcel.writeInt(n2);
                int n3 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n3);
                    if (!this.mRemote.transact(62, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().createUserKey(n, n2, bl);
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
            public int decryptStorage(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(27, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().decryptStorage(string2);
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
            public void destroyUserKey(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(63, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().destroyUserKey(n);
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
            public void destroyUserStorage(String string2, int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(68, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().destroyUserStorage(string2, n, n2);
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
            public int encryptStorage(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(28, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().encryptStorage(n, string2);
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
            public void fixateNewestUserKeyAuth(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(72, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().fixateNewestUserKeyAuth(n);
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
            public void forgetAllVolumes() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(57, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().forgetAllVolumes();
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
            public void forgetVolume(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(56, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().forgetVolume(string2);
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
            public void format(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(50, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().format(string2);
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
            public void fstrim(int n, IVoldTaskListener iVoldTaskListener) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    IBinder iBinder = iVoldTaskListener != null ? iVoldTaskListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(73, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().fstrim(n, iVoldTaskListener);
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
            public long getAllocatableBytes(String string2, int n, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(78, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        long l = Stub.getDefaultImpl().getAllocatableBytes(string2, n, string3);
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
            public long getCacheQuotaBytes(String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(76, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        long l = Stub.getDefaultImpl().getCacheQuotaBytes(string2, n);
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
            public long getCacheSizeBytes(String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(77, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        long l = Stub.getDefaultImpl().getCacheSizeBytes(string2, n);
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
            public DiskInfo[] getDisks() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(45, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        DiskInfo[] arrdiskInfo = Stub.getDefaultImpl().getDisks();
                        return arrdiskInfo;
                    }
                    parcel2.readException();
                    DiskInfo[] arrdiskInfo = parcel2.createTypedArray(DiskInfo.CREATOR);
                    return arrdiskInfo;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getEncryptionState() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(32, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getEncryptionState();
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
            public String getField(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(40, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        string2 = Stub.getDefaultImpl().getField(string2);
                        return string2;
                    }
                    parcel2.readException();
                    string2 = parcel2.readString();
                    return string2;
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
            public String getMountedObbPath(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(25, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        string2 = Stub.getDefaultImpl().getMountedObbPath(string2);
                        return string2;
                    }
                    parcel2.readException();
                    string2 = parcel2.readString();
                    return string2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String getPassword() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(37, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String string2 = Stub.getDefaultImpl().getPassword();
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
            public int getPasswordType() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(36, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getPasswordType();
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
            public String getPrimaryStorageUuid() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(58, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String string2 = Stub.getDefaultImpl().getPrimaryStorageUuid();
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
            public StorageVolume[] getVolumeList(int n, String arrstorageVolume, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString((String)arrstorageVolume);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(30, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrstorageVolume = Stub.getDefaultImpl().getVolumeList(n, (String)arrstorageVolume, n2);
                        return arrstorageVolume;
                    }
                    parcel2.readException();
                    arrstorageVolume = parcel2.createTypedArray(StorageVolume.CREATOR);
                    return arrstorageVolume;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public VolumeRecord[] getVolumeRecords(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(47, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        VolumeRecord[] arrvolumeRecord = Stub.getDefaultImpl().getVolumeRecords(n);
                        return arrvolumeRecord;
                    }
                    parcel2.readException();
                    VolumeRecord[] arrvolumeRecord = parcel2.createTypedArray(VolumeRecord.CREATOR);
                    return arrvolumeRecord;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public VolumeInfo[] getVolumes(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(46, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        VolumeInfo[] arrvolumeInfo = Stub.getDefaultImpl().getVolumes(n);
                        return arrvolumeInfo;
                    }
                    parcel2.readException();
                    VolumeInfo[] arrvolumeInfo = parcel2.createTypedArray(VolumeInfo.CREATOR);
                    return arrvolumeInfo;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean isConvertibleToFBE() throws RemoteException {
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
                    if (iBinder.transact(69, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isConvertibleToFBE();
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
            public boolean isObbMounted(String string2) throws RemoteException {
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
                    if (iBinder.transact(24, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isObbMounted(string2);
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
            public boolean isUserKeyUnlocked(int n) throws RemoteException {
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
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(66, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isUserKeyUnlocked(n);
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
            public long lastMaintenance() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(42, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        long l = Stub.getDefaultImpl().lastMaintenance();
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
            public void lockUserKey(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(65, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().lockUserKey(n);
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
            public void mkdirs(String string2, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(35, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().mkdirs(string2, string3);
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
            public void mount(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(48, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().mount(string2);
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
            public void mountObb(String string2, String string3, String string4, IObbActionListener iObbActionListener, int n, ObbInfo obbInfo) throws RemoteException {
                Parcel parcel;
                void var1_8;
                Parcel parcel2;
                block16 : {
                    block15 : {
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
                            parcel2.writeString(string4);
                            IBinder iBinder = iObbActionListener != null ? iObbActionListener.asBinder() : null;
                            parcel2.writeStrongBinder(iBinder);
                        }
                        catch (Throwable throwable) {}
                        try {
                            parcel2.writeInt(n);
                            if (obbInfo != null) {
                                parcel2.writeInt(1);
                                obbInfo.writeToParcel(parcel2, 0);
                                break block15;
                            }
                            parcel2.writeInt(0);
                        }
                        catch (Throwable throwable) {}
                    }
                    try {
                        if (!this.mRemote.transact(22, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().mountObb(string2, string3, string4, iObbActionListener, n, obbInfo);
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
                throw var1_8;
            }

            @Override
            public AppFuseMount mountProxyFileDescriptorBridge() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(74, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        AppFuseMount appFuseMount = Stub.getDefaultImpl().mountProxyFileDescriptorBridge();
                        parcel.recycle();
                        parcel2.recycle();
                        return appFuseMount;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                AppFuseMount appFuseMount = parcel.readInt() != 0 ? AppFuseMount.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return appFuseMount;
            }

            @Override
            public boolean needsCheckpoint() throws RemoteException {
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
                    if (iBinder.transact(87, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().needsCheckpoint();
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
            public ParcelFileDescriptor openProxyFileDescriptor(int n, int n2, int n3) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        parcel.writeInt(n2);
                        parcel.writeInt(n3);
                        if (this.mRemote.transact(75, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        ParcelFileDescriptor parcelFileDescriptor = Stub.getDefaultImpl().openProxyFileDescriptor(n, n2, n3);
                        parcel2.recycle();
                        parcel.recycle();
                        return parcelFileDescriptor;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                ParcelFileDescriptor parcelFileDescriptor = parcel2.readInt() != 0 ? ParcelFileDescriptor.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return parcelFileDescriptor;
            }

            @Override
            public void partitionMixed(String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(53, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().partitionMixed(string2, n);
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
            public void partitionPrivate(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(52, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().partitionPrivate(string2);
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
            public void partitionPublic(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(51, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().partitionPublic(string2);
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
            public void prepareUserStorage(String string2, int n, int n2, int n3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    if (!this.mRemote.transact(67, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().prepareUserStorage(string2, n, n2, n3);
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
            public void registerListener(IStorageEventListener iStorageEventListener) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iStorageEventListener != null ? iStorageEventListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerListener(iStorageEventListener);
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
            public void runIdleMaintenance() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(80, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().runIdleMaintenance();
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
            public void runMaintenance() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(43, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().runMaintenance();
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
            public void setDebugFlags(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(61, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setDebugFlags(n, n2);
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
            public void setField(String string2, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(39, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setField(string2, string3);
                        return;
                    }
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
            public void setPrimaryStorageUuid(String string2, IPackageMoveObserver iPackageMoveObserver) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    IBinder iBinder = iPackageMoveObserver != null ? iPackageMoveObserver.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(59, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setPrimaryStorageUuid(string2, iPackageMoveObserver);
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
            public void setVolumeNickname(String string2, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(54, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setVolumeNickname(string2, string3);
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
            public void setVolumeUserFlags(String string2, int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(55, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setVolumeUserFlags(string2, n, n2);
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
            public void shutdown(IStorageShutdownObserver iStorageShutdownObserver) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iStorageShutdownObserver != null ? iStorageShutdownObserver.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(20, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().shutdown(iStorageShutdownObserver);
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
            public void startCheckpoint(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(86, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().startCheckpoint(n);
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
            public boolean supportsCheckpoint() throws RemoteException {
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
                    if (iBinder.transact(85, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().supportsCheckpoint();
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
            public void unlockUserKey(int n, int n2, byte[] arrby, byte[] arrby2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeByteArray(arrby);
                    parcel.writeByteArray(arrby2);
                    if (!this.mRemote.transact(64, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unlockUserKey(n, n2, arrby, arrby2);
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
            public void unmount(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(49, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unmount(string2);
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
            public void unmountObb(String string2, boolean bl, IObbActionListener iObbActionListener, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    int n2 = bl ? 1 : 0;
                    parcel.writeInt(n2);
                    IBinder iBinder = iObbActionListener != null ? iObbActionListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(23, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unmountObb(string2, bl, iObbActionListener, n);
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
            public void unregisterListener(IStorageEventListener iStorageEventListener) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iStorageEventListener != null ? iStorageEventListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregisterListener(iStorageEventListener);
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
            public int verifyEncryptionPassword(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(33, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().verifyEncryptionPassword(string2);
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

