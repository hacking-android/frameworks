/*
 * Decompiled with CFR 0.145.
 */
package android.content;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Process;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class PermissionChecker {
    public static final int PERMISSION_DENIED = -1;
    public static final int PERMISSION_DENIED_APP_OP = -2;
    public static final int PERMISSION_GRANTED = 0;

    private PermissionChecker() {
    }

    public static int checkCallingOrSelfPermission(Context context, String string2) {
        String string3 = Binder.getCallingPid() == Process.myPid() ? context.getPackageName() : null;
        return PermissionChecker.checkPermission(context, string2, Binder.getCallingPid(), Binder.getCallingUid(), string3);
    }

    public static int checkCallingPermission(Context context, String string2, String string3) {
        if (Binder.getCallingPid() == Process.myPid()) {
            return -1;
        }
        return PermissionChecker.checkPermission(context, string2, Binder.getCallingPid(), Binder.getCallingUid(), string3);
    }

    public static int checkPermission(Context arrstring, String string2, int n, int n2, String string3) {
        if (arrstring.checkPermission(string2, n, n2) == -1) {
            return -1;
        }
        AppOpsManager appOpsManager = arrstring.getSystemService(AppOpsManager.class);
        String string4 = AppOpsManager.permissionToOp(string2);
        if (string4 == null) {
            return 0;
        }
        string2 = string3;
        if (string3 == null) {
            if ((arrstring = arrstring.getPackageManager().getPackagesForUid(n2)) != null && arrstring.length > 0) {
                string2 = arrstring[0];
            } else {
                return -1;
            }
        }
        if (appOpsManager.noteProxyOpNoThrow(string4, string2, n2) != 0) {
            return -2;
        }
        return 0;
    }

    public static int checkSelfPermission(Context context, String string2) {
        return PermissionChecker.checkPermission(context, string2, Process.myPid(), Process.myUid(), context.getPackageName());
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface PermissionResult {
    }

}

