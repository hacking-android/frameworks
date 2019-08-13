/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.annotation.UnsupportedAppUsage;
import android.util.Slog;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;

public class SELinux {
    private static final int SELINUX_ANDROID_RESTORECON_CROSS_FILESYSTEMS = 64;
    private static final int SELINUX_ANDROID_RESTORECON_DATADATA = 16;
    private static final int SELINUX_ANDROID_RESTORECON_FORCE = 8;
    private static final int SELINUX_ANDROID_RESTORECON_NOCHANGE = 1;
    private static final int SELINUX_ANDROID_RESTORECON_RECURSE = 4;
    private static final int SELINUX_ANDROID_RESTORECON_SKIPCE = 32;
    private static final int SELINUX_ANDROID_RESTORECON_SKIP_SEHASH = 128;
    private static final int SELINUX_ANDROID_RESTORECON_VERBOSE = 2;
    private static final String TAG = "SELinux";

    @UnsupportedAppUsage
    public static final native boolean checkSELinuxAccess(String var0, String var1, String var2, String var3);

    public static final native String fileSelabelLookup(String var0);

    @UnsupportedAppUsage
    public static final native String getContext();

    public static final native String getFileContext(FileDescriptor var0);

    @UnsupportedAppUsage
    public static final native String getFileContext(String var0);

    public static final native String getPeerContext(FileDescriptor var0);

    @UnsupportedAppUsage
    public static final native String getPidContext(int var0);

    @UnsupportedAppUsage
    public static final native boolean isSELinuxEnabled();

    @UnsupportedAppUsage
    public static final native boolean isSELinuxEnforced();

    private static native boolean native_restorecon(String var0, int var1);

    public static boolean restorecon(File file) throws NullPointerException {
        try {
            boolean bl = SELinux.native_restorecon(file.getCanonicalPath(), 0);
            return bl;
        }
        catch (IOException iOException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Error getting canonical path. Restorecon failed for ");
            stringBuilder.append(file.getPath());
            Slog.e("SELinux", stringBuilder.toString(), iOException);
            return false;
        }
    }

    public static boolean restorecon(String string2) throws NullPointerException {
        if (string2 != null) {
            return SELinux.native_restorecon(string2, 0);
        }
        throw new NullPointerException();
    }

    @UnsupportedAppUsage
    public static boolean restoreconRecursive(File file) {
        try {
            boolean bl = SELinux.native_restorecon(file.getCanonicalPath(), 132);
            return bl;
        }
        catch (IOException iOException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Error getting canonical path. Restorecon failed for ");
            stringBuilder.append(file.getPath());
            Slog.e("SELinux", stringBuilder.toString(), iOException);
            return false;
        }
    }

    public static final native boolean setFSCreateContext(String var0);

    public static final native boolean setFileContext(String var0, String var1);
}

