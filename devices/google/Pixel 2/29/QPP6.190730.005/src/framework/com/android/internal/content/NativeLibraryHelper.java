/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.system.ErrnoException
 *  android.system.Os
 *  android.system.OsConstants
 *  dalvik.system.CloseGuard
 *  dalvik.system.VMRuntime
 */
package com.android.internal.content;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageParser;
import android.os.Build;
import android.os.SELinux;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.util.Slog;
import dalvik.system.CloseGuard;
import dalvik.system.VMRuntime;
import java.io.Closeable;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.List;

public class NativeLibraryHelper {
    private static final int BITCODE_PRESENT = 1;
    public static final String CLEAR_ABI_OVERRIDE = "-";
    private static final boolean DEBUG_NATIVE = false;
    public static final String LIB64_DIR_NAME = "lib64";
    public static final String LIB_DIR_NAME = "lib";
    private static final String TAG = "NativeHelper";

    public static int copyNativeBinaries(Handle handle, File file, String string2) {
        long[] arrl = handle.apkHandles;
        int n = arrl.length;
        for (int i = 0; i < n; ++i) {
            int n2 = NativeLibraryHelper.nativeCopyNativeBinaries(arrl[i], file.getPath(), string2, handle.extractNativeLibs, handle.debuggable);
            if (n2 == 1) continue;
            return n2;
        }
        return 1;
    }

    public static int copyNativeBinariesForSupportedAbi(Handle handle, File file, String[] arrstring, boolean bl) throws IOException {
        NativeLibraryHelper.createNativeLibrarySubdir(file);
        int n = NativeLibraryHelper.findSupportedAbi(handle, arrstring);
        if (n >= 0) {
            int n2;
            String string2 = VMRuntime.getInstructionSet((String)arrstring[n]);
            if (bl) {
                file = new File(file, string2);
                NativeLibraryHelper.createNativeLibrarySubdir(file);
            }
            if ((n2 = NativeLibraryHelper.copyNativeBinaries(handle, file, arrstring[n])) != 1) {
                return n2;
            }
        }
        return n;
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static int copyNativeBinariesWithOverride(Handle object, File file, String arrstring) {
        try {
            boolean bl = ((Handle)object).multiArch;
            if (bl) {
                int n;
                if (arrstring != null && !CLEAR_ABI_OVERRIDE.equals(arrstring)) {
                    Slog.w(TAG, "Ignoring abiOverride for multi arch application.");
                }
                if (Build.SUPPORTED_32_BIT_ABIS.length > 0 && (n = NativeLibraryHelper.copyNativeBinariesForSupportedAbi((Handle)object, file, Build.SUPPORTED_32_BIT_ABIS, true)) < 0 && n != -114 && n != -113) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Failure copying 32 bit native libraries; copyRet=");
                    ((StringBuilder)object).append(n);
                    Slog.w(TAG, ((StringBuilder)object).toString());
                    return n;
                }
                if (Build.SUPPORTED_64_BIT_ABIS.length <= 0 || (n = NativeLibraryHelper.copyNativeBinariesForSupportedAbi((Handle)object, file, Build.SUPPORTED_64_BIT_ABIS, true)) >= 0 || n == -114 || n == -113) return 1;
                {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Failure copying 64 bit native libraries; copyRet=");
                    ((StringBuilder)object).append(n);
                    Slog.w(TAG, ((StringBuilder)object).toString());
                    return n;
                }
            } else {
                int n;
                String[] arrstring2 = null;
                if (CLEAR_ABI_OVERRIDE.equals(arrstring)) {
                    arrstring2 = null;
                } else if (arrstring != null) {
                    arrstring2 = arrstring;
                }
                arrstring = arrstring2 != null ? new String[]{arrstring2} : Build.SUPPORTED_ABIS;
                String[] arrstring3 = arrstring;
                if (Build.SUPPORTED_64_BIT_ABIS.length > 0) {
                    arrstring3 = arrstring;
                    if (arrstring2 == null) {
                        arrstring3 = arrstring;
                        if (NativeLibraryHelper.hasRenderscriptBitcode((Handle)object)) {
                            arrstring3 = Build.SUPPORTED_32_BIT_ABIS;
                        }
                    }
                }
                if ((n = NativeLibraryHelper.copyNativeBinariesForSupportedAbi((Handle)object, file, arrstring3, true)) >= 0 || n == -114) return 1;
                {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Failure copying native libraries [errorCode=");
                    ((StringBuilder)object).append(n);
                    ((StringBuilder)object).append("]");
                    Slog.w(TAG, ((StringBuilder)object).toString());
                    return n;
                }
            }
        }
        catch (IOException iOException) {
            Slog.e(TAG, "Copying native libraries failed", iOException);
            return -110;
        }
    }

    public static void createNativeLibrarySubdir(File file) throws IOException {
        block7 : {
            if (!file.isDirectory()) {
                file.delete();
                if (file.mkdir()) {
                    try {
                        Os.chmod((String)file.getPath(), (int)(OsConstants.S_IRWXU | OsConstants.S_IRGRP | OsConstants.S_IXGRP | OsConstants.S_IROTH | OsConstants.S_IXOTH));
                    }
                    catch (ErrnoException errnoException) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Cannot chmod native library directory ");
                        stringBuilder.append(file.getPath());
                        throw new IOException(stringBuilder.toString(), errnoException);
                    }
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Cannot create ");
                stringBuilder.append(file.getPath());
                throw new IOException(stringBuilder.toString());
            }
            if (!SELinux.restorecon(file)) break block7;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Cannot set SELinux context for ");
        stringBuilder.append(file.getPath());
        throw new IOException(stringBuilder.toString());
    }

    public static int findSupportedAbi(Handle handle, String[] arrstring) {
        int n = -114;
        long[] arrl = handle.apkHandles;
        int n2 = arrl.length;
        for (int i = 0; i < n2; ++i) {
            int n3;
            block9 : {
                int n4;
                block7 : {
                    block10 : {
                        block8 : {
                            block6 : {
                                n3 = NativeLibraryHelper.nativeFindSupportedAbi(arrl[i], arrstring, handle.debuggable);
                                if (n3 != -114) break block6;
                                n4 = n;
                                break block7;
                            }
                            if (n3 != -113) break block8;
                            n4 = n;
                            if (n < 0) {
                                n4 = -113;
                            }
                            break block7;
                        }
                        if (n3 < 0) break block9;
                        if (n < 0) break block10;
                        n4 = n;
                        if (n3 >= n) break block7;
                    }
                    n4 = n3;
                }
                n = n4;
                continue;
            }
            return n3;
        }
        return n;
    }

    private static native int hasRenderscriptBitcode(long var0);

    public static boolean hasRenderscriptBitcode(Handle object) throws IOException {
        object = ((Handle)object).apkHandles;
        int n = ((long[])object).length;
        for (int i = 0; i < n; ++i) {
            int n2 = NativeLibraryHelper.hasRenderscriptBitcode(object[i]);
            if (n2 >= 0) {
                if (n2 != 1) continue;
                return true;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Error scanning APK, code: ");
            ((StringBuilder)object).append(n2);
            throw new IOException(((StringBuilder)object).toString());
        }
        return false;
    }

    private static native void nativeClose(long var0);

    private static native int nativeCopyNativeBinaries(long var0, String var2, String var3, boolean var4, boolean var5);

    private static native int nativeFindSupportedAbi(long var0, String[] var2, boolean var3);

    private static native long nativeOpenApk(String var0);

    private static native long nativeOpenApkFd(FileDescriptor var0, String var1);

    private static native long nativeSumNativeBinaries(long var0, String var2, boolean var3);

    public static void removeNativeBinariesFromDirLI(File file, boolean bl) {
        if (file.exists()) {
            Object object = file.listFiles();
            if (object != null) {
                for (int i = 0; i < ((File[])object).length; ++i) {
                    if (object[i].isDirectory()) {
                        NativeLibraryHelper.removeNativeBinariesFromDirLI((File)object[i], true);
                        continue;
                    }
                    if (((File)object[i]).delete()) continue;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Could not delete native binary: ");
                    stringBuilder.append(((File)object[i]).getPath());
                    Slog.w("NativeHelper", stringBuilder.toString());
                }
            }
            if (bl && !file.delete()) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Could not delete native binary directory: ");
                ((StringBuilder)object).append(file.getPath());
                Slog.w("NativeHelper", ((StringBuilder)object).toString());
            }
        }
    }

    public static void removeNativeBinariesLI(String string2) {
        if (string2 == null) {
            return;
        }
        NativeLibraryHelper.removeNativeBinariesFromDirLI(new File(string2), false);
    }

    private static long sumNativeBinaries(Handle handle, String string2) {
        long l = 0L;
        long[] arrl = handle.apkHandles;
        int n = arrl.length;
        for (int i = 0; i < n; ++i) {
            l += NativeLibraryHelper.nativeSumNativeBinaries(arrl[i], string2, handle.debuggable);
        }
        return l;
    }

    private static long sumNativeBinariesForSupportedAbi(Handle handle, String[] arrstring) {
        int n = NativeLibraryHelper.findSupportedAbi(handle, arrstring);
        if (n >= 0) {
            return NativeLibraryHelper.sumNativeBinaries(handle, arrstring[n]);
        }
        return 0L;
    }

    public static long sumNativeBinariesWithOverride(Handle handle, String arrstring) throws IOException {
        long l;
        long l2 = 0L;
        if (handle.multiArch) {
            if (arrstring != null && !"-".equals(arrstring)) {
                Slog.w("NativeHelper", "Ignoring abiOverride for multi arch application.");
            }
            if (Build.SUPPORTED_32_BIT_ABIS.length > 0) {
                l2 = 0L + NativeLibraryHelper.sumNativeBinariesForSupportedAbi(handle, Build.SUPPORTED_32_BIT_ABIS);
            }
            l = l2;
            if (Build.SUPPORTED_64_BIT_ABIS.length > 0) {
                l = l2 + NativeLibraryHelper.sumNativeBinariesForSupportedAbi(handle, Build.SUPPORTED_64_BIT_ABIS);
            }
        } else {
            String[] arrstring2 = null;
            if ("-".equals(arrstring)) {
                arrstring2 = null;
            } else if (arrstring != null) {
                arrstring2 = arrstring;
            }
            arrstring = arrstring2 != null ? new String[]{arrstring2} : Build.SUPPORTED_ABIS;
            String[] arrstring3 = arrstring;
            if (Build.SUPPORTED_64_BIT_ABIS.length > 0) {
                arrstring3 = arrstring;
                if (arrstring2 == null) {
                    arrstring3 = arrstring;
                    if (NativeLibraryHelper.hasRenderscriptBitcode(handle)) {
                        arrstring3 = Build.SUPPORTED_32_BIT_ABIS;
                    }
                }
            }
            l = 0L + NativeLibraryHelper.sumNativeBinariesForSupportedAbi(handle, arrstring3);
        }
        return l;
    }

    public static class Handle
    implements Closeable {
        final long[] apkHandles;
        final boolean debuggable;
        final boolean extractNativeLibs;
        private volatile boolean mClosed;
        private final CloseGuard mGuard = CloseGuard.get();
        final boolean multiArch;

        Handle(long[] arrl, boolean bl, boolean bl2, boolean bl3) {
            this.apkHandles = arrl;
            this.multiArch = bl;
            this.extractNativeLibs = bl2;
            this.debuggable = bl3;
            this.mGuard.open("close");
        }

        public static Handle create(PackageParser.Package package_) throws IOException {
            List<String> list = package_.getAllCodePaths();
            int n = package_.applicationInfo.flags;
            boolean bl = true;
            boolean bl2 = (n & Integer.MIN_VALUE) != 0;
            boolean bl3 = (package_.applicationInfo.flags & 268435456) != 0;
            if ((package_.applicationInfo.flags & 2) == 0) {
                bl = false;
            }
            return Handle.create(list, bl2, bl3, bl);
        }

        public static Handle create(PackageParser.PackageLite packageLite) throws IOException {
            return Handle.create(packageLite.getAllCodePaths(), packageLite.multiArch, packageLite.extractNativeLibs, packageLite.debuggable);
        }

        public static Handle create(File file) throws IOException {
            try {
                Handle handle = Handle.create(PackageParser.parsePackageLite(file, 0));
                return handle;
            }
            catch (PackageParser.PackageParserException packageParserException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Failed to parse package: ");
                stringBuilder.append(file);
                throw new IOException(stringBuilder.toString(), packageParserException);
            }
        }

        private static Handle create(List<String> object, boolean bl, boolean bl2, boolean bl3) throws IOException {
            int n = object.size();
            long[] arrl = new long[n];
            for (int i = 0; i < n; ++i) {
                String string2 = object.get(i);
                arrl[i] = NativeLibraryHelper.nativeOpenApk(string2);
                if (arrl[i] != 0L) continue;
                for (n = 0; n < i; ++n) {
                    NativeLibraryHelper.nativeClose(arrl[n]);
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("Unable to open APK: ");
                ((StringBuilder)object).append(string2);
                throw new IOException(((StringBuilder)object).toString());
            }
            return new Handle(arrl, bl, bl2, bl3);
        }

        public static Handle createFd(PackageParser.PackageLite object, FileDescriptor fileDescriptor) throws IOException {
            long[] arrl = new long[1];
            String string2 = ((PackageParser.PackageLite)object).baseCodePath;
            arrl[0] = NativeLibraryHelper.nativeOpenApkFd(fileDescriptor, string2);
            if (arrl[0] != 0L) {
                return new Handle(arrl, ((PackageParser.PackageLite)object).multiArch, ((PackageParser.PackageLite)object).extractNativeLibs, ((PackageParser.PackageLite)object).debuggable);
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Unable to open APK ");
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append(" from fd ");
            ((StringBuilder)object).append(fileDescriptor);
            throw new IOException(((StringBuilder)object).toString());
        }

        @Override
        public void close() {
            long[] arrl = this.apkHandles;
            int n = arrl.length;
            for (int i = 0; i < n; ++i) {
                NativeLibraryHelper.nativeClose(arrl[i]);
            }
            this.mGuard.close();
            this.mClosed = true;
        }

        protected void finalize() throws Throwable {
            CloseGuard closeGuard = this.mGuard;
            if (closeGuard != null) {
                closeGuard.warnIfOpen();
            }
            try {
                if (!this.mClosed) {
                    this.close();
                }
                return;
            }
            finally {
                super.finalize();
            }
        }
    }

}

