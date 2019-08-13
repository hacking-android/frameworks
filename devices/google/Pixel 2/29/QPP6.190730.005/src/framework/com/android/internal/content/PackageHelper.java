/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.io.IoUtils
 */
package com.android.internal.content;

import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.content.pm.PackageParser;
import android.content.pm.dex.DexMetadataHelper;
import android.content.res.Resources;
import android.os.Environment;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.storage.IStorageManager;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.os.storage.VolumeInfo;
import android.provider.Settings;
import android.util.ArraySet;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.content.NativeLibraryHelper;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import libcore.io.IoUtils;

public class PackageHelper {
    public static final int APP_INSTALL_AUTO = 0;
    public static final int APP_INSTALL_EXTERNAL = 2;
    public static final int APP_INSTALL_INTERNAL = 1;
    public static final int RECOMMEND_FAILED_ALREADY_EXISTS = -4;
    public static final int RECOMMEND_FAILED_INSUFFICIENT_STORAGE = -1;
    public static final int RECOMMEND_FAILED_INVALID_APK = -2;
    public static final int RECOMMEND_FAILED_INVALID_LOCATION = -3;
    public static final int RECOMMEND_FAILED_INVALID_URI = -6;
    public static final int RECOMMEND_FAILED_VERSION_DOWNGRADE = -7;
    public static final int RECOMMEND_FAILED_WRONG_INSTALLED_VERSION = -8;
    public static final int RECOMMEND_INSTALL_EPHEMERAL = 3;
    public static final int RECOMMEND_INSTALL_EXTERNAL = 2;
    public static final int RECOMMEND_INSTALL_INTERNAL = 1;
    public static final int RECOMMEND_MEDIA_UNAVAILABLE = -5;
    private static final String TAG = "PackageHelper";
    private static TestableInterface sDefaultTestableInterface = null;

    public static long calculateInstalledSize(PackageParser.PackageLite packageLite, NativeLibraryHelper.Handle handle, String string2) throws IOException {
        long l = 0L;
        Iterator<String> iterator = packageLite.getAllCodePaths().iterator();
        while (iterator.hasNext()) {
            l += new File(iterator.next()).length();
        }
        return l + DexMetadataHelper.getPackageDexMetadataSize(packageLite) + NativeLibraryHelper.sumNativeBinariesWithOverride(handle, string2);
    }

    public static long calculateInstalledSize(PackageParser.PackageLite packageLite, String string2) throws IOException {
        return PackageHelper.calculateInstalledSize(packageLite, string2, null);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static long calculateInstalledSize(PackageParser.PackageLite var0, String var1_2, FileDescriptor var2_3) throws IOException {
        var3_4 = null;
        if (var2_3 == null) ** GOTO lbl6
        try {
            block2 : {
                var2_3 = NativeLibraryHelper.Handle.createFd(var0, (FileDescriptor)var2_3);
                break block2;
lbl6: // 1 sources:
                var2_3 = NativeLibraryHelper.Handle.create(var0);
            }
            var3_4 = var2_3;
            var4_5 = PackageHelper.calculateInstalledSize(var0, (NativeLibraryHelper.Handle)var2_3, var1_2);
        }
        catch (Throwable var0_1) {
            IoUtils.closeQuietly(var3_4);
            throw var0_1;
        }
        IoUtils.closeQuietly((AutoCloseable)var2_3);
        return var4_5;
    }

    @Deprecated
    public static long calculateInstalledSize(PackageParser.PackageLite packageLite, boolean bl, NativeLibraryHelper.Handle handle, String string2) throws IOException {
        return PackageHelper.calculateInstalledSize(packageLite, handle, string2);
    }

    @Deprecated
    public static long calculateInstalledSize(PackageParser.PackageLite packageLite, boolean bl, String string2) throws IOException {
        return PackageHelper.calculateInstalledSize(packageLite, string2);
    }

    public static boolean fitsOnExternal(Context object, PackageInstaller.SessionParams sessionParams) {
        StorageManager storageManager = ((Context)object).getSystemService(StorageManager.class);
        object = storageManager.getPrimaryVolume();
        boolean bl = sessionParams.sizeBytes > 0L && !((StorageVolume)object).isEmulated() && "mounted".equals(((StorageVolume)object).getState()) && sessionParams.sizeBytes <= storageManager.getStorageBytesUntilLow(((StorageVolume)object).getPathFile());
        return bl;
    }

    public static boolean fitsOnInternal(Context object, PackageInstaller.SessionParams sessionParams) throws IOException {
        UUID uUID;
        boolean bl = sessionParams.sizeBytes <= ((StorageManager)(object = ((Context)object).getSystemService(StorageManager.class))).getAllocatableBytes(uUID = ((StorageManager)object).getUuidForPath(Environment.getDataDirectory()), PackageHelper.translateAllocateFlags(sessionParams.installFlags));
        return bl;
    }

    private static TestableInterface getDefaultTestableInterface() {
        synchronized (PackageHelper.class) {
            TestableInterface testableInterface;
            if (sDefaultTestableInterface == null) {
                testableInterface = new TestableInterface(){

                    @Override
                    public boolean getAllow3rdPartyOnInternalConfig(Context context) {
                        return context.getResources().getBoolean(17891339);
                    }

                    @Override
                    public File getDataDirectory() {
                        return Environment.getDataDirectory();
                    }

                    @Override
                    public ApplicationInfo getExistingAppInfo(Context object, String string2) {
                        Object var3_4 = null;
                        try {
                            object = ((Context)object).getPackageManager().getApplicationInfo(string2, 4194304);
                        }
                        catch (PackageManager.NameNotFoundException nameNotFoundException) {
                            object = var3_4;
                        }
                        return object;
                    }

                    @Override
                    public boolean getForceAllowOnExternalSetting(Context object) {
                        object = ((Context)object).getContentResolver();
                        boolean bl = false;
                        if (Settings.Global.getInt((ContentResolver)object, "force_allow_on_external", 0) != 0) {
                            bl = true;
                        }
                        return bl;
                    }

                    @Override
                    public StorageManager getStorageManager(Context context) {
                        return context.getSystemService(StorageManager.class);
                    }
                };
                sDefaultTestableInterface = testableInterface;
            }
            testableInterface = sDefaultTestableInterface;
            return testableInterface;
        }
    }

    public static IStorageManager getStorageManager() throws RemoteException {
        IBinder iBinder = ServiceManager.getService("mount");
        if (iBinder != null) {
            return IStorageManager.Stub.asInterface(iBinder);
        }
        Log.e(TAG, "Can't get storagemanager service");
        throw new RemoteException("Could not contact storagemanager service");
    }

    public static String replaceEnd(String string2, String string3, String charSequence) {
        if (string2.endsWith(string3)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string2.substring(0, string2.length() - string3.length()));
            stringBuilder.append((String)charSequence);
            return stringBuilder.toString();
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Expected ");
        ((StringBuilder)charSequence).append(string2);
        ((StringBuilder)charSequence).append(" to end with ");
        ((StringBuilder)charSequence).append(string3);
        throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
    }

    public static int resolveInstallLocation(Context context, PackageInstaller.SessionParams sessionParams) throws IOException {
        boolean bl;
        ApplicationInfo applicationInfo = null;
        try {
            ApplicationInfo applicationInfo2;
            applicationInfo = applicationInfo2 = context.getPackageManager().getApplicationInfo(sessionParams.appPackageName, 4194304);
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            // empty catch block
        }
        boolean bl2 = false;
        int n = sessionParams.installFlags;
        int n2 = 1;
        if ((n & 2048) != 0) {
            n = 1;
            bl2 = true;
            bl = false;
        } else if ((sessionParams.installFlags & 16) != 0) {
            n = 1;
            bl = false;
        } else if (sessionParams.installLocation == 1) {
            n = 1;
            bl = false;
        } else if (sessionParams.installLocation == 2) {
            n = 2;
            bl = true;
        } else if (sessionParams.installLocation == 0) {
            n = applicationInfo != null ? ((applicationInfo.flags & 262144) != 0 ? 2 : 1) : 1;
            bl = true;
        } else {
            n = 1;
            bl = false;
        }
        boolean bl3 = false;
        if (bl || n == 1) {
            bl3 = PackageHelper.fitsOnInternal(context, sessionParams);
        }
        boolean bl4 = false;
        if (bl || n == 2) {
            bl4 = PackageHelper.fitsOnExternal(context, sessionParams);
        }
        if (n == 1) {
            if (bl3) {
                n = bl2 ? 3 : n2;
                return n;
            }
        } else if (n == 2 && bl4) {
            return 2;
        }
        if (bl) {
            if (bl3) {
                return 1;
            }
            if (bl4) {
                return 2;
            }
        }
        return -1;
    }

    @Deprecated
    public static int resolveInstallLocation(Context context, String string2, int n, long l, int n2) {
        PackageInstaller.SessionParams sessionParams = new PackageInstaller.SessionParams(-1);
        sessionParams.appPackageName = string2;
        sessionParams.installLocation = n;
        sessionParams.sizeBytes = l;
        sessionParams.installFlags = n2;
        try {
            n = PackageHelper.resolveInstallLocation(context, sessionParams);
            return n;
        }
        catch (IOException iOException) {
            throw new IllegalStateException(iOException);
        }
    }

    public static String resolveInstallVolume(Context context, PackageInstaller.SessionParams sessionParams) throws IOException {
        TestableInterface testableInterface = PackageHelper.getDefaultTestableInterface();
        return PackageHelper.resolveInstallVolume(context, sessionParams.appPackageName, sessionParams.installLocation, sessionParams.sizeBytes, testableInterface);
    }

    @VisibleForTesting
    public static String resolveInstallVolume(Context object, PackageInstaller.SessionParams sessionParams, TestableInterface object2) throws IOException {
        Object object3 = ((TestableInterface)object2).getStorageManager((Context)object);
        boolean bl = ((TestableInterface)object2).getForceAllowOnExternalSetting((Context)object);
        boolean bl2 = ((TestableInterface)object2).getAllow3rdPartyOnInternalConfig((Context)object);
        ApplicationInfo applicationInfo = ((TestableInterface)object2).getExistingAppInfo((Context)object, sessionParams.appPackageName);
        ArraySet<String> arraySet = new ArraySet<String>();
        boolean bl3 = false;
        object2 = null;
        long l = Long.MIN_VALUE;
        Iterator<VolumeInfo> iterator = ((StorageManager)object3).getVolumes().iterator();
        object = object3;
        while (iterator.hasNext()) {
            boolean bl4;
            long l2;
            block18 : {
                block16 : {
                    long l3;
                    VolumeInfo volumeInfo;
                    block17 : {
                        volumeInfo = iterator.next();
                        if (volumeInfo.type != 1 || !volumeInfo.isMountedWritable()) break block16;
                        boolean bl5 = "private".equals(volumeInfo.id);
                        l3 = ((StorageManager)object).getAllocatableBytes(((StorageManager)object).getUuidForPath(new File(volumeInfo.path)), PackageHelper.translateAllocateFlags(sessionParams.installFlags));
                        if (bl5) {
                            bl3 = sessionParams.sizeBytes <= l3;
                        }
                        if (!bl5) break block17;
                        bl4 = bl3;
                        object3 = object2;
                        l2 = l;
                        if (!bl2) break block18;
                    }
                    if (l3 >= sessionParams.sizeBytes) {
                        arraySet.add(volumeInfo.fsUuid);
                    }
                    bl4 = bl3;
                    object3 = object2;
                    l2 = l;
                    if (l3 >= l) {
                        l2 = l3;
                        object3 = volumeInfo;
                        bl4 = bl3;
                    }
                    break block18;
                }
                l2 = l;
                object3 = object2;
                bl4 = bl3;
            }
            bl3 = bl4;
            object2 = object3;
            l = l2;
        }
        if (applicationInfo != null && applicationInfo.isSystemApp()) {
            if (bl3) {
                return StorageManager.UUID_PRIVATE_INTERNAL;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Not enough space on existing volume ");
            ((StringBuilder)object).append(applicationInfo.volumeUuid);
            ((StringBuilder)object).append(" for system app ");
            ((StringBuilder)object).append(sessionParams.appPackageName);
            ((StringBuilder)object).append(" upgrade");
            throw new IOException(((StringBuilder)object).toString());
        }
        if (!bl && sessionParams.installLocation == 1) {
            if (applicationInfo != null && !Objects.equals(applicationInfo.volumeUuid, StorageManager.UUID_PRIVATE_INTERNAL)) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Cannot automatically move ");
                ((StringBuilder)object).append(sessionParams.appPackageName);
                ((StringBuilder)object).append(" from ");
                ((StringBuilder)object).append(applicationInfo.volumeUuid);
                ((StringBuilder)object).append(" to internal storage");
                throw new IOException(((StringBuilder)object).toString());
            }
            if (bl2) {
                if (bl3) {
                    return StorageManager.UUID_PRIVATE_INTERNAL;
                }
                throw new IOException("Requested internal only, but not enough space");
            }
            throw new IOException("Not allowed to install non-system apps on internal storage");
        }
        if (applicationInfo != null) {
            if (Objects.equals(applicationInfo.volumeUuid, StorageManager.UUID_PRIVATE_INTERNAL) && bl3) {
                return StorageManager.UUID_PRIVATE_INTERNAL;
            }
            if (arraySet.contains(applicationInfo.volumeUuid)) {
                return applicationInfo.volumeUuid;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Not enough space on existing volume ");
            ((StringBuilder)object).append(applicationInfo.volumeUuid);
            ((StringBuilder)object).append(" for ");
            ((StringBuilder)object).append(sessionParams.appPackageName);
            ((StringBuilder)object).append(" upgrade");
            throw new IOException(((StringBuilder)object).toString());
        }
        if (object2 != null) {
            return ((VolumeInfo)object2).fsUuid;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("No special requests, but no room on allowed volumes.  allow3rdPartyOnInternal? ");
        ((StringBuilder)object).append(bl2);
        throw new IOException(((StringBuilder)object).toString());
    }

    @Deprecated
    @VisibleForTesting
    public static String resolveInstallVolume(Context context, String string2, int n, long l, TestableInterface testableInterface) throws IOException {
        PackageInstaller.SessionParams sessionParams = new PackageInstaller.SessionParams(-1);
        sessionParams.appPackageName = string2;
        sessionParams.installLocation = n;
        sessionParams.sizeBytes = l;
        return PackageHelper.resolveInstallVolume(context, sessionParams, testableInterface);
    }

    public static int translateAllocateFlags(int n) {
        return (32768 & n) != 0;
    }

    public static abstract class TestableInterface {
        public abstract boolean getAllow3rdPartyOnInternalConfig(Context var1);

        public abstract File getDataDirectory();

        public abstract ApplicationInfo getExistingAppInfo(Context var1, String var2);

        public abstract boolean getForceAllowOnExternalSetting(Context var1);

        public abstract StorageManager getStorageManager(Context var1);
    }

}

