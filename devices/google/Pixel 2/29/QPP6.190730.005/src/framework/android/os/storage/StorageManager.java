/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.system.ErrnoException
 *  android.system.Os
 *  android.system.OsConstants
 *  android.system.StructStat
 *  dalvik.system.BlockGuard
 */
package android.os.storage;

import android.annotation.SuppressLint;
import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.app.ActivityThread;
import android.app.AppGlobals;
import android.app.AppOpsManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageMoveObserver;
import android.content.res.ObbInfo;
import android.content.res.ObbScanner;
import android.net.Uri;
import android.os.Binder;
import android.os.Environment;
import android.os.FileUtils;
import android.os.Handler;
import android.os.IVoldTaskListener;
import android.os.Looper;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.os.ParcelableException;
import android.os.PersistableBundle;
import android.os.Process;
import android.os.ProxyFileDescriptorCallback;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.storage.DiskInfo;
import android.os.storage.IObbActionListener;
import android.os.storage.IStorageEventListener;
import android.os.storage.IStorageManager;
import android.os.storage.OnObbStateChangeListener;
import android.os.storage.StorageEventListener;
import android.os.storage.StorageVolume;
import android.os.storage.VolumeInfo;
import android.os.storage.VolumeRecord;
import android.provider.MediaStore;
import android.provider.Settings;
import android.sysprop.VoldProperties;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.system.StructStat;
import android.text.TextUtils;
import android.util.DataUnit;
import android.util.Log;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.os.AppFuseMount;
import com.android.internal.os.FuseAppLoop;
import com.android.internal.os.FuseUnavailableMountException;
import com.android.internal.os.RoSystemProperties;
import com.android.internal.os.SomeArgs;
import com.android.internal.util.Preconditions;
import dalvik.system.BlockGuard;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class StorageManager {
    public static final String ACTION_MANAGE_STORAGE = "android.os.storage.action.MANAGE_STORAGE";
    @UnsupportedAppUsage
    public static final int CRYPT_TYPE_DEFAULT = 1;
    @UnsupportedAppUsage
    public static final int CRYPT_TYPE_PASSWORD = 0;
    public static final int CRYPT_TYPE_PATTERN = 2;
    public static final int CRYPT_TYPE_PIN = 3;
    public static final int DEBUG_ADOPTABLE_FORCE_OFF = 2;
    public static final int DEBUG_ADOPTABLE_FORCE_ON = 1;
    public static final int DEBUG_EMULATE_FBE = 4;
    public static final int DEBUG_ISOLATED_STORAGE_FORCE_OFF = 128;
    public static final int DEBUG_ISOLATED_STORAGE_FORCE_ON = 64;
    public static final int DEBUG_SDCARDFS_FORCE_OFF = 16;
    public static final int DEBUG_SDCARDFS_FORCE_ON = 8;
    public static final int DEBUG_VIRTUAL_DISK = 32;
    private static final long DEFAULT_CACHE_MAX_BYTES;
    private static final int DEFAULT_CACHE_PERCENTAGE = 10;
    private static final long DEFAULT_FULL_THRESHOLD_BYTES;
    private static final long DEFAULT_THRESHOLD_MAX_BYTES;
    private static final int DEFAULT_THRESHOLD_PERCENTAGE = 5;
    public static final int ENCRYPTION_STATE_ERROR_CORRUPT = -4;
    public static final int ENCRYPTION_STATE_ERROR_INCOMPLETE = -2;
    public static final int ENCRYPTION_STATE_ERROR_INCONSISTENT = -3;
    public static final int ENCRYPTION_STATE_ERROR_UNKNOWN = -1;
    @UnsupportedAppUsage
    public static final int ENCRYPTION_STATE_NONE = 1;
    public static final int ENCRYPTION_STATE_OK = 0;
    public static final String EXTRA_REQUESTED_BYTES = "android.os.storage.extra.REQUESTED_BYTES";
    public static final String EXTRA_UUID = "android.os.storage.extra.UUID";
    @SystemApi
    public static final int FLAG_ALLOCATE_AGGRESSIVE = 1;
    public static final int FLAG_ALLOCATE_DEFY_ALL_RESERVED = 2;
    public static final int FLAG_ALLOCATE_DEFY_HALF_RESERVED = 4;
    public static final int FLAG_FOR_WRITE = 256;
    public static final int FLAG_INCLUDE_INVISIBLE = 1024;
    public static final int FLAG_REAL_STATE = 512;
    public static final int FLAG_STORAGE_CE = 2;
    public static final int FLAG_STORAGE_DE = 1;
    public static final int FLAG_STORAGE_EXTERNAL = 4;
    public static final int FSTRIM_FLAG_DEEP = 1;
    private static final boolean LOCAL_LOGV;
    public static final String OWNER_INFO_KEY = "OwnerInfo";
    public static final String PASSWORD_VISIBLE_KEY = "PasswordVisible";
    public static final String PATTERN_VISIBLE_KEY = "PatternVisible";
    public static final String PROP_ADOPTABLE = "persist.sys.adoptable";
    public static final String PROP_EMULATE_FBE = "persist.sys.emulate_fbe";
    public static final String PROP_HAS_ADOPTABLE = "vold.has_adoptable";
    public static final String PROP_HAS_RESERVED = "vold.has_reserved";
    public static final String PROP_ISOLATED_STORAGE = "persist.sys.isolated_storage";
    public static final String PROP_ISOLATED_STORAGE_SNAPSHOT = "sys.isolated_storage_snapshot";
    public static final String PROP_PRIMARY_PHYSICAL = "ro.vold.primary_physical";
    public static final String PROP_SDCARDFS = "persist.sys.sdcardfs";
    public static final String PROP_VIRTUAL_DISK = "persist.sys.virtual_disk";
    public static final String SYSTEM_LOCALE_KEY = "SystemLocale";
    private static final String TAG = "StorageManager";
    public static final UUID UUID_DEFAULT;
    public static final String UUID_PRIMARY_PHYSICAL = "primary_physical";
    public static final UUID UUID_PRIMARY_PHYSICAL_;
    public static final String UUID_PRIVATE_INTERNAL;
    public static final String UUID_SYSTEM = "system";
    public static final UUID UUID_SYSTEM_;
    private static final String XATTR_CACHE_GROUP = "user.cache_group";
    private static final String XATTR_CACHE_TOMBSTONE = "user.cache_tombstone";
    private static volatile IStorageManager sStorageManager;
    private final AppOpsManager mAppOps;
    private final Context mContext;
    private final ArrayList<StorageEventListenerDelegate> mDelegates = new ArrayList();
    @GuardedBy(value={"mFuseAppLoopLock"})
    private FuseAppLoop mFuseAppLoop = null;
    private final Object mFuseAppLoopLock = new Object();
    private final Looper mLooper;
    private final AtomicInteger mNextNonce = new AtomicInteger(0);
    private final ObbActionListener mObbActionListener = new ObbActionListener();
    private final ContentResolver mResolver;
    private final IStorageManager mStorageManager;

    static {
        LOCAL_LOGV = Log.isLoggable(TAG, 2);
        UUID_PRIVATE_INTERNAL = null;
        UUID_DEFAULT = UUID.fromString("41217664-9172-527a-b3d5-edabb50a7d69");
        UUID_PRIMARY_PHYSICAL_ = UUID.fromString("0f95a519-dae7-5abf-9519-fbd6209e05fd");
        UUID_SYSTEM_ = UUID.fromString("5d258386-e60d-59e3-826d-0089cdd42cc0");
        sStorageManager = null;
        DEFAULT_THRESHOLD_MAX_BYTES = DataUnit.MEBIBYTES.toBytes(500L);
        DEFAULT_CACHE_MAX_BYTES = DataUnit.GIBIBYTES.toBytes(5L);
        DEFAULT_FULL_THRESHOLD_BYTES = DataUnit.MEBIBYTES.toBytes(1L);
    }

    @UnsupportedAppUsage
    public StorageManager(Context context, Looper looper) throws ServiceManager.ServiceNotFoundException {
        this.mContext = context;
        this.mResolver = context.getContentResolver();
        this.mLooper = looper;
        this.mStorageManager = IStorageManager.Stub.asInterface(ServiceManager.getServiceOrThrow("mount"));
        this.mAppOps = this.mContext.getSystemService(AppOpsManager.class);
    }

    public static boolean checkPermissionAndAppOp(Context context, boolean bl, int n, int n2, String string2, String string3, int n3) {
        return StorageManager.checkPermissionAndAppOp(context, bl, n, n2, string2, string3, n3, true);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static boolean checkPermissionAndAppOp(Context object, boolean bl, int n, int n2, String string2, String string3, int n3, boolean bl2) {
        if (((Context)object).checkPermission(string3, n, n2) != 0) {
            if (!bl) {
                return false;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Permission ");
            ((StringBuilder)object).append(string3);
            ((StringBuilder)object).append(" denied for package ");
            ((StringBuilder)object).append(string2);
            throw new SecurityException(((StringBuilder)object).toString());
        }
        object = ((Context)object).getSystemService(AppOpsManager.class);
        if (bl2) {
            n = ((AppOpsManager)object).noteOpNoThrow(n3, n2, string2);
        } else {
            ((AppOpsManager)object).checkPackage(n2, string2);
            n = ((AppOpsManager)object).checkOpNoThrow(n3, n2, string2);
        }
        if (n == 0) return true;
        if (n != 1 && n != 2 && n != 3) {
            object = new StringBuilder();
            ((StringBuilder)object).append(AppOpsManager.opToName(n3));
            ((StringBuilder)object).append(" has unknown mode ");
            ((StringBuilder)object).append(AppOpsManager.modeToName(n));
            throw new IllegalStateException(((StringBuilder)object).toString());
        }
        if (!bl) {
            return false;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Op ");
        ((StringBuilder)object).append(AppOpsManager.opToName(n3));
        ((StringBuilder)object).append(" ");
        ((StringBuilder)object).append(AppOpsManager.modeToName(n));
        ((StringBuilder)object).append(" for package ");
        ((StringBuilder)object).append(string2);
        throw new SecurityException(((StringBuilder)object).toString());
        catch (SecurityException securityException) {
            if (bl) throw securityException;
            return false;
        }
    }

    private boolean checkPermissionAndAppOp(boolean bl, int n, int n2, String string2, String string3, int n3) {
        return StorageManager.checkPermissionAndAppOp(this.mContext, bl, n, n2, string2, string3, n3);
    }

    public static boolean checkPermissionAndCheckOp(Context context, boolean bl, int n, int n2, String string2, String string3, int n3) {
        return StorageManager.checkPermissionAndAppOp(context, bl, n, n2, string2, string3, n3, false);
    }

    public static String convert(UUID uUID) {
        if (UUID_DEFAULT.equals(uUID)) {
            return UUID_PRIVATE_INTERNAL;
        }
        if (UUID_PRIMARY_PHYSICAL_.equals(uUID)) {
            return UUID_PRIMARY_PHYSICAL;
        }
        if (UUID_SYSTEM_.equals(uUID)) {
            return UUID_SYSTEM;
        }
        return uUID.toString();
    }

    public static UUID convert(String string2) {
        if (Objects.equals(string2, UUID_PRIVATE_INTERNAL)) {
            return UUID_DEFAULT;
        }
        if (Objects.equals(string2, UUID_PRIMARY_PHYSICAL)) {
            return UUID_PRIMARY_PHYSICAL_;
        }
        if (Objects.equals(string2, UUID_SYSTEM)) {
            return UUID_SYSTEM_;
        }
        return UUID.fromString(string2);
    }

    @Deprecated
    @UnsupportedAppUsage
    public static StorageManager from(Context context) {
        return context.getSystemService(StorageManager.class);
    }

    private int getNextNonce() {
        return this.mNextNonce.getAndIncrement();
    }

    private ObbInfo getObbInfo(String string2) {
        try {
            ObbInfo obbInfo = ObbScanner.getObbInfo(string2);
            return obbInfo;
        }
        catch (IOException iOException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Couldn't get OBB info for ");
            stringBuilder.append(string2);
            throw new IllegalArgumentException(stringBuilder.toString(), iOException);
        }
    }

    public static Pair<String, Long> getPrimaryStoragePathAndSize() {
        return Pair.create(null, FileUtils.roundStorageSize(Environment.getDataDirectory().getTotalSpace() + Environment.getRootDirectory().getTotalSpace()));
    }

    public static StorageVolume getPrimaryVolume(StorageVolume[] arrstorageVolume) {
        for (StorageVolume storageVolume : arrstorageVolume) {
            if (!storageVolume.isPrimary()) continue;
            return storageVolume;
        }
        throw new IllegalStateException("Missing primary storage");
    }

    public static StorageVolume getStorageVolume(File file, int n) {
        return StorageManager.getStorageVolume(StorageManager.getVolumeList(n, 0), file);
    }

    /*
     * WARNING - void declaration
     */
    @UnsupportedAppUsage
    private static StorageVolume getStorageVolume(StorageVolume[] object, File object22) {
        void iOException;
        if (iOException == null) {
            return null;
        }
        Object object2 = iOException.getAbsolutePath();
        if (((String)object2).startsWith("/mnt/content/")) {
            Uri iOException2 = ContentResolver.translateDeprecatedDataPath((String)object2);
            return AppGlobals.getInitialApplication().getSystemService(StorageManager.class).getStorageVolume(iOException2);
        }
        try {
            object2 = iOException.getCanonicalFile();
        }
        catch (IOException stringBuilder) {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("Could not get canonical path for ");
            stringBuilder2.append(iOException);
            Slog.d(TAG, stringBuilder2.toString());
            return null;
        }
        for (Object object3 : object) {
            File file = ((StorageVolume)object3).getPathFile();
            try {
                file = file.getCanonicalFile();
            }
            catch (IOException iOException3) {
                // empty catch block
            }
            if (!FileUtils.contains(file, (File)object2)) continue;
            return object3;
        }
        return null;
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public static StorageVolume[] getVolumeList(int n, int n2) {
        IStorageManager iStorageManager = IStorageManager.Stub.asInterface(ServiceManager.getService("mount"));
        try {
            String string2;
            void var4_7;
            String string3 = string2 = ActivityThread.currentOpPackageName();
            if (string2 == null) {
                String[] arrstring = ActivityThread.getPackageManager().getPackagesForUid(Process.myUid());
                if (arrstring == null) return new StorageVolume[0];
                if (arrstring.length <= 0) {
                    return new StorageVolume[0];
                }
                String string4 = arrstring[0];
            }
            if ((n = ActivityThread.getPackageManager().getPackageUid((String)var4_7, 268435456, n)) > 0) return iStorageManager.getVolumeList(n, (String)var4_7, n2);
            return new StorageVolume[0];
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public static boolean hasAdoptable() {
        return SystemProperties.getBoolean(PROP_HAS_ADOPTABLE, false);
    }

    @SystemApi
    public static boolean hasIsolatedStorage() {
        return SystemProperties.getBoolean(PROP_ISOLATED_STORAGE_SNAPSHOT, SystemProperties.getBoolean(PROP_ISOLATED_STORAGE, true));
    }

    public static boolean inCryptKeeperBounce() {
        return "trigger_restart_min_framework".equals(VoldProperties.decrypt().orElse(""));
    }

    public static boolean isBlockEncrypted() {
        if (!StorageManager.isEncrypted()) {
            return false;
        }
        return RoSystemProperties.CRYPTO_BLOCK_ENCRYPTED;
    }

    public static boolean isBlockEncrypting() {
        return "".equalsIgnoreCase(VoldProperties.encrypt_progress().orElse("")) ^ true;
    }

    private static boolean isCacheBehavior(File file, String string2) throws IOException {
        try {
            Os.getxattr((String)file.getAbsolutePath(), (String)string2);
            return true;
        }
        catch (ErrnoException errnoException) {
            if (errnoException.errno == OsConstants.ENODATA) {
                return false;
            }
            throw errnoException.rethrowAsIOException();
        }
    }

    public static boolean isEncryptable() {
        return RoSystemProperties.CRYPTO_ENCRYPTABLE;
    }

    public static boolean isEncrypted() {
        return RoSystemProperties.CRYPTO_ENCRYPTED;
    }

    public static boolean isFileEncryptedEmulatedOnly() {
        return SystemProperties.getBoolean(PROP_EMULATE_FBE, false);
    }

    @UnsupportedAppUsage
    public static boolean isFileEncryptedNativeOnly() {
        if (!StorageManager.isEncrypted()) {
            return false;
        }
        return RoSystemProperties.CRYPTO_FILE_ENCRYPTED;
    }

    public static boolean isFileEncryptedNativeOrEmulated() {
        boolean bl = StorageManager.isFileEncryptedNativeOnly() || StorageManager.isFileEncryptedEmulatedOnly();
        return bl;
    }

    public static boolean isNonDefaultBlockEncrypted() {
        boolean bl = StorageManager.isBlockEncrypted();
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        try {
            int n = IStorageManager.Stub.asInterface(ServiceManager.getService("mount")).getPasswordType();
            if (n != 1) {
                bl2 = true;
            }
            return bl2;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Error getting encryption type");
            return false;
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static boolean isUserKeyUnlocked(int n) {
        Throwable throwable2222;
        if (sStorageManager == null) {
            sStorageManager = IStorageManager.Stub.asInterface(ServiceManager.getService("mount"));
        }
        if (sStorageManager == null) {
            Slog.w(TAG, "Early during boot, assuming locked");
            return false;
        }
        long l = Binder.clearCallingIdentity();
        boolean bl = sStorageManager.isUserKeyUnlocked(n);
        Binder.restoreCallingIdentity(l);
        return bl;
        {
            catch (Throwable throwable2222) {
            }
            catch (RemoteException remoteException) {}
            {
                throw remoteException.rethrowAsRuntimeException();
            }
        }
        Binder.restoreCallingIdentity(l);
        throw throwable2222;
    }

    @Deprecated
    public static File maybeTranslateEmulatedPathToInternal(File file) {
        return file;
    }

    private boolean noteAppOpAllowingLegacy(boolean bl, int n, int n2, String charSequence, int n3) {
        n = this.mAppOps.noteOpNoThrow(n3, n2, (String)charSequence);
        if (n != 0) {
            if (n != 1 && n != 2 && n != 3) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append(AppOpsManager.opToName(n3));
                ((StringBuilder)charSequence).append(" has unknown mode ");
                ((StringBuilder)charSequence).append(AppOpsManager.modeToName(n));
                throw new IllegalStateException(((StringBuilder)charSequence).toString());
            }
            if (this.mAppOps.checkOpNoThrow(87, n2, (String)charSequence) == 0) {
                return true;
            }
            if (!bl) {
                return false;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Op ");
            stringBuilder.append(AppOpsManager.opToName(n3));
            stringBuilder.append(" ");
            stringBuilder.append(AppOpsManager.modeToName(n));
            stringBuilder.append(" for package ");
            stringBuilder.append((String)charSequence);
            throw new SecurityException(stringBuilder.toString());
        }
        return true;
    }

    private static void setCacheBehavior(File file, String string2, boolean bl) throws IOException {
        if (file.isDirectory()) {
            ErrnoException errnoException3;
            block6 : {
                if (bl) {
                    try {
                        Os.setxattr((String)file.getAbsolutePath(), (String)string2, (byte[])"1".getBytes(StandardCharsets.UTF_8), (int)0);
                    }
                    catch (ErrnoException errnoException2) {
                        throw errnoException2.rethrowAsIOException();
                    }
                }
                try {
                    Os.removexattr((String)file.getAbsolutePath(), (String)string2);
                }
                catch (ErrnoException errnoException3) {
                    if (errnoException3.errno != OsConstants.ENODATA) break block6;
                }
                return;
            }
            throw errnoException3.rethrowAsIOException();
        }
        throw new IOException("Cache behavior can only be set on directories");
    }

    public void allocateBytes(FileDescriptor fileDescriptor, long l) throws IOException {
        this.allocateBytes(fileDescriptor, l, 0);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @SystemApi
    @SuppressLint(value={"Doclava125"})
    public void allocateBytes(FileDescriptor object, long l, int n) throws IOException {
        File file = ParcelFileDescriptor.getFile((FileDescriptor)object);
        UUID uUID = this.getUuidForPath(file);
        int i = 0;
        do {
            block8 : {
                if (i >= 3) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Well this is embarassing; we can't allocate ");
                    ((StringBuilder)object).append(l);
                    ((StringBuilder)object).append(" for ");
                    ((StringBuilder)object).append(file);
                    throw new IOException(((StringBuilder)object).toString());
                }
                long l2 = l - Os.fstat((FileDescriptor)object).st_blocks * 512L;
                if (l2 <= 0L) break block8;
                this.allocateBytes(uUID, l2, n);
                {
                    catch (ErrnoException errnoException) {
                        if (errnoException.errno != OsConstants.ENOSPC) throw errnoException.rethrowAsIOException();
                        Log.w(TAG, "Odd, not enough space; let's try again?");
                        ++i;
                        continue;
                    }
                }
            }
            try {
                Os.posix_fallocate((FileDescriptor)object, (long)0L, (long)l);
                return;
            }
            catch (ErrnoException errnoException) {
                if (errnoException.errno != OsConstants.ENOSYS) {
                    if (errnoException.errno != OsConstants.ENOTSUP) throw errnoException;
                }
                Log.w(TAG, "fallocate() not supported; falling back to ftruncate()");
                Os.ftruncate((FileDescriptor)object, (long)l);
                return;
            }
            break;
        } while (true);
    }

    public void allocateBytes(UUID uUID, long l) throws IOException {
        this.allocateBytes(uUID, l, 0);
    }

    @SystemApi
    @SuppressLint(value={"Doclava125"})
    public void allocateBytes(UUID uUID, long l, int n) throws IOException {
        try {
            this.mStorageManager.allocateBytes(StorageManager.convert(uUID), l, n, this.mContext.getOpPackageName());
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
        catch (ParcelableException parcelableException) {
            parcelableException.maybeRethrow(IOException.class);
        }
    }

    @Deprecated
    public long benchmark(String string2) {
        long l;
        final CompletableFuture completableFuture = new CompletableFuture();
        this.benchmark(string2, new IVoldTaskListener.Stub(){

            @Override
            public void onFinished(int n, PersistableBundle persistableBundle) {
                completableFuture.complete(persistableBundle);
            }

            @Override
            public void onStatus(int n, PersistableBundle persistableBundle) {
            }
        });
        try {
            l = ((PersistableBundle)completableFuture.get(3L, TimeUnit.MINUTES)).getLong("run", Long.MAX_VALUE);
        }
        catch (Exception exception) {
            return Long.MAX_VALUE;
        }
        return l * 1000000L;
    }

    public void benchmark(String string2, IVoldTaskListener iVoldTaskListener) {
        try {
            this.mStorageManager.benchmark(string2, iVoldTaskListener);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean checkPermissionReadAudio(boolean bl, int n, int n2, String string2) {
        if (!this.checkPermissionAndAppOp(bl, n, n2, string2, "android.permission.READ_EXTERNAL_STORAGE", 59)) {
            return false;
        }
        return this.noteAppOpAllowingLegacy(bl, n, n2, string2, 81);
    }

    public boolean checkPermissionReadImages(boolean bl, int n, int n2, String string2) {
        if (!this.checkPermissionAndAppOp(bl, n, n2, string2, "android.permission.READ_EXTERNAL_STORAGE", 59)) {
            return false;
        }
        return this.noteAppOpAllowingLegacy(bl, n, n2, string2, 85);
    }

    public boolean checkPermissionReadVideo(boolean bl, int n, int n2, String string2) {
        if (!this.checkPermissionAndAppOp(bl, n, n2, string2, "android.permission.READ_EXTERNAL_STORAGE", 59)) {
            return false;
        }
        return this.noteAppOpAllowingLegacy(bl, n, n2, string2, 83);
    }

    public boolean checkPermissionWriteAudio(boolean bl, int n, int n2, String string2) {
        if (!this.checkPermissionAndAppOp(bl, n, n2, string2, "android.permission.WRITE_EXTERNAL_STORAGE", 60)) {
            return false;
        }
        return this.noteAppOpAllowingLegacy(bl, n, n2, string2, 82);
    }

    public boolean checkPermissionWriteImages(boolean bl, int n, int n2, String string2) {
        if (!this.checkPermissionAndAppOp(bl, n, n2, string2, "android.permission.WRITE_EXTERNAL_STORAGE", 60)) {
            return false;
        }
        return this.noteAppOpAllowingLegacy(bl, n, n2, string2, 86);
    }

    public boolean checkPermissionWriteVideo(boolean bl, int n, int n2, String string2) {
        if (!this.checkPermissionAndAppOp(bl, n, n2, string2, "android.permission.WRITE_EXTERNAL_STORAGE", 60)) {
            return false;
        }
        return this.noteAppOpAllowingLegacy(bl, n, n2, string2, 84);
    }

    public void createUserKey(int n, int n2, boolean bl) {
        try {
            this.mStorageManager.createUserKey(n, n2, bl);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void destroyUserKey(int n) {
        try {
            this.mStorageManager.destroyUserKey(n);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void destroyUserStorage(String string2, int n, int n2) {
        try {
            this.mStorageManager.destroyUserStorage(string2, n, n2);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Deprecated
    @UnsupportedAppUsage
    public void disableUsbMassStorage() {
    }

    @Deprecated
    @UnsupportedAppUsage
    public void enableUsbMassStorage() {
    }

    @UnsupportedAppUsage
    public DiskInfo findDiskById(String string2) {
        Preconditions.checkNotNull(string2);
        for (DiskInfo diskInfo : this.getDisks()) {
            if (!Objects.equals(diskInfo.id, string2)) continue;
            return diskInfo;
        }
        return null;
    }

    @UnsupportedAppUsage
    public VolumeInfo findEmulatedForPrivate(VolumeInfo volumeInfo) {
        if (volumeInfo != null) {
            return this.findVolumeById(volumeInfo.getId().replace("private", "emulated"));
        }
        return null;
    }

    public File findPathForUuid(String string2) throws FileNotFoundException {
        Object object = this.findVolumeByQualifiedUuid(string2);
        if (object != null) {
            return ((VolumeInfo)object).getPath();
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Failed to find a storage device for ");
        ((StringBuilder)object).append(string2);
        throw new FileNotFoundException(((StringBuilder)object).toString());
    }

    public VolumeInfo findPrivateForEmulated(VolumeInfo volumeInfo) {
        if (volumeInfo != null) {
            return this.findVolumeById(volumeInfo.getId().replace("emulated", "private"));
        }
        return null;
    }

    public VolumeRecord findRecordByUuid(String string2) {
        Preconditions.checkNotNull(string2);
        for (VolumeRecord volumeRecord : this.getVolumeRecords()) {
            if (!Objects.equals(volumeRecord.fsUuid, string2)) continue;
            return volumeRecord;
        }
        return null;
    }

    @UnsupportedAppUsage
    public VolumeInfo findVolumeById(String string2) {
        Preconditions.checkNotNull(string2);
        for (VolumeInfo volumeInfo : this.getVolumes()) {
            if (!Objects.equals(volumeInfo.id, string2)) continue;
            return volumeInfo;
        }
        return null;
    }

    public VolumeInfo findVolumeByQualifiedUuid(String string2) {
        if (Objects.equals(UUID_PRIVATE_INTERNAL, string2)) {
            return this.findVolumeById("private");
        }
        if (Objects.equals(UUID_PRIMARY_PHYSICAL, string2)) {
            return this.getPrimaryPhysicalVolume();
        }
        return this.findVolumeByUuid(string2);
    }

    @UnsupportedAppUsage
    public VolumeInfo findVolumeByUuid(String string2) {
        Preconditions.checkNotNull(string2);
        for (VolumeInfo volumeInfo : this.getVolumes()) {
            if (!Objects.equals(volumeInfo.fsUuid, string2)) continue;
            return volumeInfo;
        }
        return null;
    }

    public void forgetVolume(String string2) {
        try {
            this.mStorageManager.forgetVolume(string2);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public void format(String string2) {
        try {
            this.mStorageManager.format(string2);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public long getAllocatableBytes(UUID uUID) throws IOException {
        return this.getAllocatableBytes(uUID, 0);
    }

    @SystemApi
    @SuppressLint(value={"Doclava125"})
    public long getAllocatableBytes(UUID uUID, int n) throws IOException {
        try {
            long l = this.mStorageManager.getAllocatableBytes(StorageManager.convert(uUID), n, this.mContext.getOpPackageName());
            return l;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
        catch (ParcelableException parcelableException) {
            parcelableException.maybeRethrow(IOException.class);
            throw new RuntimeException(parcelableException);
        }
    }

    @UnsupportedAppUsage
    public String getBestVolumeDescription(VolumeInfo volumeInfo) {
        VolumeRecord volumeRecord;
        if (volumeInfo == null) {
            return null;
        }
        if (!TextUtils.isEmpty(volumeInfo.fsUuid) && (volumeRecord = this.findRecordByUuid(volumeInfo.fsUuid)) != null && !TextUtils.isEmpty(volumeRecord.nickname)) {
            return volumeRecord.nickname;
        }
        if (!TextUtils.isEmpty(volumeInfo.getDescription())) {
            return volumeInfo.getDescription();
        }
        if (volumeInfo.disk != null) {
            return volumeInfo.disk.getDescription();
        }
        return null;
    }

    public long getCacheQuotaBytes(UUID uUID) throws IOException {
        try {
            ApplicationInfo applicationInfo = this.mContext.getApplicationInfo();
            long l = this.mStorageManager.getCacheQuotaBytes(StorageManager.convert(uUID), applicationInfo.uid);
            return l;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
        catch (ParcelableException parcelableException) {
            parcelableException.maybeRethrow(IOException.class);
            throw new RuntimeException(parcelableException);
        }
    }

    public long getCacheSizeBytes(UUID uUID) throws IOException {
        try {
            ApplicationInfo applicationInfo = this.mContext.getApplicationInfo();
            long l = this.mStorageManager.getCacheSizeBytes(StorageManager.convert(uUID), applicationInfo.uid);
            return l;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
        catch (ParcelableException parcelableException) {
            parcelableException.maybeRethrow(IOException.class);
            throw new RuntimeException(parcelableException);
        }
    }

    @UnsupportedAppUsage
    public List<DiskInfo> getDisks() {
        try {
            List<DiskInfo> list = Arrays.asList(this.mStorageManager.getDisks());
            return list;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public String getMountedObbPath(String string2) {
        Preconditions.checkNotNull(string2, "rawPath cannot be null");
        try {
            string2 = this.mStorageManager.getMountedObbPath(string2);
            return string2;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public VolumeInfo getPrimaryPhysicalVolume() {
        for (VolumeInfo volumeInfo : this.getVolumes()) {
            if (!volumeInfo.isPrimaryPhysical()) continue;
            return volumeInfo;
        }
        return null;
    }

    public long getPrimaryStorageSize() {
        return FileUtils.roundStorageSize(Environment.getDataDirectory().getTotalSpace() + Environment.getRootDirectory().getTotalSpace());
    }

    public String getPrimaryStorageUuid() {
        try {
            String string2 = this.mStorageManager.getPrimaryStorageUuid();
            return string2;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public StorageVolume getPrimaryStorageVolume() {
        return StorageManager.getVolumeList(this.mContext.getUserId(), 1536)[0];
    }

    public StorageVolume getPrimaryVolume() {
        return StorageManager.getPrimaryVolume(this.getVolumeList());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @VisibleForTesting
    public int getProxyFileDescriptorMountPointId() {
        Object object = this.mFuseAppLoopLock;
        synchronized (object) {
            if (this.mFuseAppLoop == null) return -1;
            return this.mFuseAppLoop.getMountPointId();
        }
    }

    @UnsupportedAppUsage
    public long getStorageBytesUntilLow(File file) {
        return file.getUsableSpace() - this.getStorageFullBytes(file);
    }

    public long getStorageCacheBytes(File file, int n) {
        long l = Settings.Global.getInt(this.mResolver, "sys_storage_cache_percentage", 10);
        l = Math.min(file.getTotalSpace() * l / 100L, Settings.Global.getLong(this.mResolver, "sys_storage_cache_max_bytes", DEFAULT_CACHE_MAX_BYTES));
        if ((n & 1) != 0) {
            return 0L;
        }
        if ((n & 2) != 0) {
            return 0L;
        }
        if ((n & 4) != 0) {
            return l / 2L;
        }
        return l;
    }

    @UnsupportedAppUsage
    public long getStorageFullBytes(File file) {
        return Settings.Global.getLong(this.mResolver, "sys_storage_full_threshold_bytes", DEFAULT_FULL_THRESHOLD_BYTES);
    }

    @UnsupportedAppUsage
    public long getStorageLowBytes(File file) {
        long l = Settings.Global.getInt(this.mResolver, "sys_storage_threshold_percentage", 5);
        return Math.min(file.getTotalSpace() * l / 100L, Settings.Global.getLong(this.mResolver, "sys_storage_threshold_max_bytes", DEFAULT_THRESHOLD_MAX_BYTES));
    }

    public StorageVolume getStorageVolume(Uri uri) {
        CharSequence charSequence = MediaStore.getVolumeName(uri);
        int n = ((String)charSequence).hashCode() == -1921573490 && ((String)charSequence).equals("external_primary") ? 0 : -1;
        if (n != 0) {
            for (StorageVolume storageVolume : this.getStorageVolumes()) {
                if (!Objects.equals(storageVolume.getNormalizedUuid(), charSequence)) continue;
                return storageVolume;
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Unknown volume for ");
            ((StringBuilder)charSequence).append(uri);
            throw new IllegalStateException(((StringBuilder)charSequence).toString());
        }
        return this.getPrimaryStorageVolume();
    }

    public StorageVolume getStorageVolume(File file) {
        return StorageManager.getStorageVolume(this.getVolumeList(), file);
    }

    public List<StorageVolume> getStorageVolumes() {
        ArrayList<StorageVolume> arrayList = new ArrayList<StorageVolume>();
        Collections.addAll(arrayList, StorageManager.getVolumeList(this.mContext.getUserId(), 1536));
        return arrayList;
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public UUID getUuidForPath(File file) throws IOException {
        int n;
        VolumeInfo[] arrvolumeInfo;
        Preconditions.checkNotNull(file);
        CharSequence charSequence = file.getCanonicalPath();
        if (FileUtils.contains(Environment.getDataDirectory().getAbsolutePath(), (String)charSequence)) {
            return UUID_DEFAULT;
        }
        try {
            arrvolumeInfo = this.mStorageManager;
            arrvolumeInfo = arrvolumeInfo.getVolumes(0);
            n = arrvolumeInfo.length;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
        for (int i = 0; i < n; ++i) {
            int n2;
            VolumeInfo volumeInfo = arrvolumeInfo[i];
            if (volumeInfo.path == null || !FileUtils.contains(volumeInfo.path, (String)charSequence) || volumeInfo.type == 0 || (n2 = volumeInfo.type) == 5) continue;
            try {
                return StorageManager.convert(volumeInfo.fsUuid);
            }
            catch (IllegalArgumentException illegalArgumentException) {
                // empty catch block
            }
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Failed to find a storage device for ");
        ((StringBuilder)charSequence).append(file);
        throw new FileNotFoundException(((StringBuilder)charSequence).toString());
    }

    public StorageVolume[] getVolumeList() {
        return StorageManager.getVolumeList(this.mContext.getUserId(), 0);
    }

    @Deprecated
    @UnsupportedAppUsage
    public String[] getVolumePaths() {
        StorageVolume[] arrstorageVolume = this.getVolumeList();
        int n = arrstorageVolume.length;
        String[] arrstring = new String[n];
        for (int i = 0; i < n; ++i) {
            arrstring[i] = arrstorageVolume[i].getPath();
        }
        return arrstring;
    }

    public List<VolumeRecord> getVolumeRecords() {
        try {
            List<VolumeRecord> list = Arrays.asList(this.mStorageManager.getVolumeRecords(0));
            return list;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Deprecated
    @UnsupportedAppUsage
    public String getVolumeState(String object) {
        if ((object = this.getStorageVolume(new File((String)object))) != null) {
            return ((StorageVolume)object).getState();
        }
        return "unknown";
    }

    @UnsupportedAppUsage
    public List<VolumeInfo> getVolumes() {
        try {
            List<VolumeInfo> list = Arrays.asList(this.mStorageManager.getVolumes(0));
            return list;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public List<VolumeInfo> getWritablePrivateVolumes() {
        try {
            ArrayList<VolumeInfo> arrayList = new ArrayList<VolumeInfo>();
            IStorageManager iStorageManager = this.mStorageManager;
            int n = 0;
            VolumeInfo[] arrvolumeInfo = iStorageManager.getVolumes(0);
            int n2 = arrvolumeInfo.length;
            do {
                if (n >= n2) {
                    return arrayList;
                }
                VolumeInfo volumeInfo = arrvolumeInfo[n];
                if (volumeInfo.getType() == 1 && volumeInfo.isMountedWritable()) {
                    arrayList.add(volumeInfo);
                }
                ++n;
            } while (true);
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean isAllocationSupported(FileDescriptor fileDescriptor) {
        try {
            this.getUuidForPath(ParcelFileDescriptor.getFile(fileDescriptor));
            return true;
        }
        catch (IOException iOException) {
            return false;
        }
    }

    public boolean isCacheBehaviorGroup(File file) throws IOException {
        return StorageManager.isCacheBehavior(file, XATTR_CACHE_GROUP);
    }

    public boolean isCacheBehaviorTombstone(File file) throws IOException {
        return StorageManager.isCacheBehavior(file, XATTR_CACHE_TOMBSTONE);
    }

    public boolean isEncrypted(File file) {
        if (FileUtils.contains(Environment.getDataDirectory(), file)) {
            return StorageManager.isEncrypted();
        }
        return FileUtils.contains(Environment.getExpandDirectory(), file);
    }

    public boolean isObbMounted(String string2) {
        Preconditions.checkNotNull(string2, "rawPath cannot be null");
        try {
            boolean bl = this.mStorageManager.isObbMounted(string2);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Deprecated
    @UnsupportedAppUsage
    public boolean isUsbMassStorageConnected() {
        return false;
    }

    @Deprecated
    @UnsupportedAppUsage
    public boolean isUsbMassStorageEnabled() {
        return false;
    }

    public void lockUserKey(int n) {
        try {
            this.mStorageManager.lockUserKey(n);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void mkdirs(File file) {
        BlockGuard.getVmPolicy().onPathAccess(file.getAbsolutePath());
        try {
            this.mStorageManager.mkdirs(this.mContext.getOpPackageName(), file.getAbsolutePath());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void mount(String string2) {
        try {
            this.mStorageManager.mount(string2);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean mountObb(String string2, String charSequence, OnObbStateChangeListener onObbStateChangeListener) {
        Preconditions.checkNotNull(string2, "rawPath cannot be null");
        Preconditions.checkNotNull(onObbStateChangeListener, "listener cannot be null");
        try {
            Object object = new File(string2);
            object = ((File)object).getCanonicalPath();
            int n = this.mObbActionListener.addListener(onObbStateChangeListener);
            this.mStorageManager.mountObb(string2, (String)object, (String)charSequence, this.mObbActionListener, n, this.getObbInfo((String)object));
            return true;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
        catch (IOException iOException) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Failed to resolve path: ");
            ((StringBuilder)charSequence).append(string2);
            throw new IllegalArgumentException(((StringBuilder)charSequence).toString(), iOException);
        }
    }

    public ParcelFileDescriptor openProxyFileDescriptor(int n, ProxyFileDescriptorCallback proxyFileDescriptorCallback) throws IOException {
        return this.openProxyFileDescriptor(n, proxyFileDescriptorCallback, null, null);
    }

    public ParcelFileDescriptor openProxyFileDescriptor(int n, ProxyFileDescriptorCallback proxyFileDescriptorCallback, Handler handler) throws IOException {
        Preconditions.checkNotNull(handler);
        return this.openProxyFileDescriptor(n, proxyFileDescriptorCallback, handler, null);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @VisibleForTesting
    public ParcelFileDescriptor openProxyFileDescriptor(int n, ProxyFileDescriptorCallback object, Handler object2, ThreadFactory threadFactory) throws IOException {
        Preconditions.checkNotNull(object);
        MetricsLogger.count(this.mContext, "storage_open_proxy_file_descriptor", 1);
        Object object3 = object2;
        do {
            int n2;
            block13 : {
                boolean bl;
                block12 : {
                    Object object4 = this.mFuseAppLoopLock;
                    // MONITORENTER : object4
                    bl = false;
                    if (this.mFuseAppLoop != null) break block12;
                    AppFuseMount appFuseMount = this.mStorageManager.mountProxyFileDescriptorBridge();
                    if (appFuseMount == null) {
                        object = new IOException("Failed to mount proxy bridge");
                        throw object;
                    }
                    this.mFuseAppLoop = object2 = new FuseAppLoop(appFuseMount.mountPointId, appFuseMount.fd, threadFactory);
                    bl = true;
                }
                object2 = object3;
                if (object3 == null) {
                    object2 = new Handler(Looper.getMainLooper());
                }
                try {
                    n2 = this.mFuseAppLoop.registerCallback((ProxyFileDescriptorCallback)object, (Handler)object2);
                    object3 = this.mStorageManager.openProxyFileDescriptor(this.mFuseAppLoop.getMountPointId(), n2, n);
                    if (object3 == null) break block13;
                }
                catch (FuseUnavailableMountException fuseUnavailableMountException) {
                    if (bl) {
                        object = new IOException(fuseUnavailableMountException);
                        throw object;
                    }
                    this.mFuseAppLoop = null;
                    // MONITOREXIT : object4
                    object3 = object2;
                    continue;
                }
                return object3;
            }
            this.mFuseAppLoop.unregisterCallback(n2);
            object3 = new FuseUnavailableMountException(this.mFuseAppLoop.getMountPointId());
            throw object3;
            break;
        } while (true);
        catch (RemoteException remoteException) {
            throw new IOException(remoteException);
        }
    }

    public void partitionMixed(String string2, int n) {
        try {
            this.mStorageManager.partitionMixed(string2, n);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void partitionPrivate(String string2) {
        try {
            this.mStorageManager.partitionPrivate(string2);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public void partitionPublic(String string2) {
        try {
            this.mStorageManager.partitionPublic(string2);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void prepareUserStorage(String string2, int n, int n2, int n3) {
        try {
            this.mStorageManager.prepareUserStorage(string2, n, n2, n3);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public void registerListener(StorageEventListener storageEventListener) {
        ArrayList<StorageEventListenerDelegate> arrayList = this.mDelegates;
        synchronized (arrayList) {
            StorageEventListenerDelegate storageEventListenerDelegate = new StorageEventListenerDelegate(storageEventListener, this.mLooper);
            try {
                this.mStorageManager.registerListener(storageEventListenerDelegate);
                this.mDelegates.add(storageEventListenerDelegate);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
            return;
        }
    }

    public void setCacheBehaviorGroup(File file, boolean bl) throws IOException {
        StorageManager.setCacheBehavior(file, XATTR_CACHE_GROUP, bl);
    }

    public void setCacheBehaviorTombstone(File file, boolean bl) throws IOException {
        StorageManager.setCacheBehavior(file, XATTR_CACHE_TOMBSTONE, bl);
    }

    public void setPrimaryStorageUuid(String string2, IPackageMoveObserver iPackageMoveObserver) {
        try {
            this.mStorageManager.setPrimaryStorageUuid(string2, iPackageMoveObserver);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void setVolumeInited(String string2, boolean bl) {
        IStorageManager iStorageManager;
        int n;
        try {
            iStorageManager = this.mStorageManager;
            n = bl ? 1 : 0;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
        iStorageManager.setVolumeUserFlags(string2, n, 1);
    }

    public void setVolumeNickname(String string2, String string3) {
        try {
            this.mStorageManager.setVolumeNickname(string2, string3);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void setVolumeSnoozed(String string2, boolean bl) {
        IStorageManager iStorageManager;
        int n;
        try {
            iStorageManager = this.mStorageManager;
            n = bl ? 2 : 0;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
        iStorageManager.setVolumeUserFlags(string2, n, 2);
    }

    public File translateAppToSystem(File file, int n, int n2) {
        return file;
    }

    public File translateSystemToApp(File file, int n, int n2) {
        return file;
    }

    public void unlockUserKey(int n, int n2, byte[] arrby, byte[] arrby2) {
        try {
            this.mStorageManager.unlockUserKey(n, n2, arrby, arrby2);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public void unmount(String string2) {
        try {
            this.mStorageManager.unmount(string2);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean unmountObb(String string2, boolean bl, OnObbStateChangeListener onObbStateChangeListener) {
        Preconditions.checkNotNull(string2, "rawPath cannot be null");
        Preconditions.checkNotNull(onObbStateChangeListener, "listener cannot be null");
        try {
            int n = this.mObbActionListener.addListener(onObbStateChangeListener);
            this.mStorageManager.unmountObb(string2, bl, this.mObbActionListener, n);
            return true;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public void unregisterListener(StorageEventListener storageEventListener) {
        ArrayList<StorageEventListenerDelegate> arrayList = this.mDelegates;
        synchronized (arrayList) {
            Iterator<StorageEventListenerDelegate> iterator = this.mDelegates.iterator();
            while (iterator.hasNext()) {
                StorageEventListenerDelegate storageEventListenerDelegate = iterator.next();
                StorageEventListener storageEventListener2 = storageEventListenerDelegate.mCallback;
                if (storageEventListener2 != storageEventListener) continue;
                try {
                    this.mStorageManager.unregisterListener(storageEventListenerDelegate);
                    iterator.remove();
                }
                catch (RemoteException remoteException) {
                    throw remoteException.rethrowFromSystemServer();
                }
            }
            return;
        }
    }

    public void wipeAdoptableDisks() {
        for (DiskInfo diskInfo : this.getDisks()) {
            CharSequence charSequence = diskInfo.getId();
            if (diskInfo.isAdoptable()) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Found adoptable ");
                stringBuilder.append((String)charSequence);
                stringBuilder.append("; wiping");
                Slog.d(TAG, stringBuilder.toString());
                try {
                    this.mStorageManager.partitionPublic((String)charSequence);
                }
                catch (Exception exception) {
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("Failed to wipe ");
                    stringBuilder2.append((String)charSequence);
                    stringBuilder2.append(", but soldiering onward");
                    Slog.w(TAG, stringBuilder2.toString(), exception);
                }
                continue;
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Ignorning non-adoptable disk ");
            ((StringBuilder)charSequence).append(diskInfo.getId());
            Slog.d(TAG, ((StringBuilder)charSequence).toString());
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface AllocateFlags {
    }

    private class ObbActionListener
    extends IObbActionListener.Stub {
        private SparseArray<ObbListenerDelegate> mListeners = new SparseArray();

        private ObbActionListener() {
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public int addListener(OnObbStateChangeListener object) {
            ObbListenerDelegate obbListenerDelegate = new ObbListenerDelegate((OnObbStateChangeListener)object);
            object = this.mListeners;
            synchronized (object) {
                this.mListeners.put(obbListenerDelegate.nonce, obbListenerDelegate);
                return obbListenerDelegate.nonce;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        @Override
        public void onObbResult(String string2, int n, int n2) {
            SparseArray<ObbListenerDelegate> sparseArray = this.mListeners;
            // MONITORENTER : sparseArray
            ObbListenerDelegate obbListenerDelegate = this.mListeners.get(n);
            if (obbListenerDelegate != null) {
                this.mListeners.remove(n);
            }
            // MONITOREXIT : sparseArray
            if (obbListenerDelegate == null) return;
            obbListenerDelegate.sendObbStateChanged(string2, n2);
        }
    }

    private class ObbListenerDelegate {
        private final Handler mHandler;
        private final WeakReference<OnObbStateChangeListener> mObbEventListenerRef;
        private final int nonce;

        ObbListenerDelegate(OnObbStateChangeListener onObbStateChangeListener) {
            this.nonce = StorageManager.this.getNextNonce();
            this.mObbEventListenerRef = new WeakReference<OnObbStateChangeListener>(onObbStateChangeListener);
            this.mHandler = new Handler(StorageManager.this.mLooper){

                @Override
                public void handleMessage(Message message) {
                    OnObbStateChangeListener onObbStateChangeListener = ObbListenerDelegate.this.getListener();
                    if (onObbStateChangeListener == null) {
                        return;
                    }
                    onObbStateChangeListener.onObbStateChange((String)message.obj, message.arg1);
                }
            };
        }

        OnObbStateChangeListener getListener() {
            WeakReference<OnObbStateChangeListener> weakReference = this.mObbEventListenerRef;
            if (weakReference == null) {
                return null;
            }
            return (OnObbStateChangeListener)weakReference.get();
        }

        void sendObbStateChanged(String string2, int n) {
            this.mHandler.obtainMessage(0, n, 0, string2).sendToTarget();
        }

    }

    private static class StorageEventListenerDelegate
    extends IStorageEventListener.Stub
    implements Handler.Callback {
        private static final int MSG_DISK_DESTROYED = 6;
        private static final int MSG_DISK_SCANNED = 5;
        private static final int MSG_STORAGE_STATE_CHANGED = 1;
        private static final int MSG_VOLUME_FORGOTTEN = 4;
        private static final int MSG_VOLUME_RECORD_CHANGED = 3;
        private static final int MSG_VOLUME_STATE_CHANGED = 2;
        final StorageEventListener mCallback;
        final Handler mHandler;

        public StorageEventListenerDelegate(StorageEventListener storageEventListener, Looper looper) {
            this.mCallback = storageEventListener;
            this.mHandler = new Handler(looper, this);
        }

        @Override
        public boolean handleMessage(Message message) {
            SomeArgs someArgs = (SomeArgs)message.obj;
            switch (message.what) {
                default: {
                    someArgs.recycle();
                    return false;
                }
                case 6: {
                    this.mCallback.onDiskDestroyed((DiskInfo)someArgs.arg1);
                    someArgs.recycle();
                    return true;
                }
                case 5: {
                    this.mCallback.onDiskScanned((DiskInfo)someArgs.arg1, someArgs.argi2);
                    someArgs.recycle();
                    return true;
                }
                case 4: {
                    this.mCallback.onVolumeForgotten((String)someArgs.arg1);
                    someArgs.recycle();
                    return true;
                }
                case 3: {
                    this.mCallback.onVolumeRecordChanged((VolumeRecord)someArgs.arg1);
                    someArgs.recycle();
                    return true;
                }
                case 2: {
                    this.mCallback.onVolumeStateChanged((VolumeInfo)someArgs.arg1, someArgs.argi2, someArgs.argi3);
                    someArgs.recycle();
                    return true;
                }
                case 1: 
            }
            this.mCallback.onStorageStateChanged((String)someArgs.arg1, (String)someArgs.arg2, (String)someArgs.arg3);
            someArgs.recycle();
            return true;
        }

        @Override
        public void onDiskDestroyed(DiskInfo diskInfo) throws RemoteException {
            SomeArgs someArgs = SomeArgs.obtain();
            someArgs.arg1 = diskInfo;
            this.mHandler.obtainMessage(6, someArgs).sendToTarget();
        }

        @Override
        public void onDiskScanned(DiskInfo diskInfo, int n) {
            SomeArgs someArgs = SomeArgs.obtain();
            someArgs.arg1 = diskInfo;
            someArgs.argi2 = n;
            this.mHandler.obtainMessage(5, someArgs).sendToTarget();
        }

        @Override
        public void onStorageStateChanged(String string2, String string3, String string4) {
            SomeArgs someArgs = SomeArgs.obtain();
            someArgs.arg1 = string2;
            someArgs.arg2 = string3;
            someArgs.arg3 = string4;
            this.mHandler.obtainMessage(1, someArgs).sendToTarget();
        }

        @Override
        public void onUsbMassStorageConnectionChanged(boolean bl) throws RemoteException {
        }

        @Override
        public void onVolumeForgotten(String string2) {
            SomeArgs someArgs = SomeArgs.obtain();
            someArgs.arg1 = string2;
            this.mHandler.obtainMessage(4, someArgs).sendToTarget();
        }

        @Override
        public void onVolumeRecordChanged(VolumeRecord volumeRecord) {
            SomeArgs someArgs = SomeArgs.obtain();
            someArgs.arg1 = volumeRecord;
            this.mHandler.obtainMessage(3, someArgs).sendToTarget();
        }

        @Override
        public void onVolumeStateChanged(VolumeInfo volumeInfo, int n, int n2) {
            SomeArgs someArgs = SomeArgs.obtain();
            someArgs.arg1 = volumeInfo;
            someArgs.argi2 = n;
            someArgs.argi3 = n2;
            this.mHandler.obtainMessage(2, someArgs).sendToTarget();
        }
    }

}

