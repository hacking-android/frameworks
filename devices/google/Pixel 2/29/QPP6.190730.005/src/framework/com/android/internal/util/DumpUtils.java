/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.android.internal.util.-$
 *  com.android.internal.util.-$$Lambda
 *  com.android.internal.util.-$$Lambda$DumpUtils
 *  com.android.internal.util.-$$Lambda$DumpUtils$D1OlZP6xIpu72ypnJd0fzx0wd6I
 *  com.android.internal.util.-$$Lambda$JwOUSWW2-Jzu15y4Kn4JuPh8tWM
 *  com.android.internal.util.-$$Lambda$TCbPpgWlKJUHZgFKCczglAvxLfw
 *  com.android.internal.util.-$$Lambda$eRa1rlfDk6Og2yFeXGHqUGPzRF0
 *  com.android.internal.util.-$$Lambda$grRTg3idX3yJe9Zyx-tmLBiD1DM
 *  com.android.internal.util.-$$Lambda$kVylv1rl9MOSbHFZoVyK5dl1kfY
 */
package com.android.internal.util;

import android.app.AppOpsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Handler;
import android.text.TextUtils;
import com.android.internal.util.-$;
import com.android.internal.util.FastPrintWriter;
import com.android.internal.util.ParseUtils;
import com.android.internal.util._$$Lambda$DumpUtils$D1OlZP6xIpu72ypnJd0fzx0wd6I;
import com.android.internal.util._$$Lambda$DumpUtils$X8irOs5hfloCKy89_l1HRA1QeG0;
import com.android.internal.util._$$Lambda$DumpUtils$vCLO_0ezRxkpSERUWCFrJ0ph5jg;
import com.android.internal.util._$$Lambda$JwOUSWW2_Jzu15y4Kn4JuPh8tWM;
import com.android.internal.util._$$Lambda$TCbPpgWlKJUHZgFKCczglAvxLfw;
import com.android.internal.util._$$Lambda$eRa1rlfDk6Og2yFeXGHqUGPzRF0;
import com.android.internal.util._$$Lambda$grRTg3idX3yJe9Zyx_tmLBiD1DM;
import com.android.internal.util._$$Lambda$kVylv1rl9MOSbHFZoVyK5dl1kfY;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.function.Predicate;

public final class DumpUtils {
    public static final ComponentName[] CRITICAL_SECTION_COMPONENTS = new ComponentName[]{new ComponentName("com.android.systemui", "com.android.systemui.SystemUIService")};
    private static final boolean DEBUG = false;
    private static final String TAG = "DumpUtils";

    private DumpUtils() {
    }

    public static boolean checkDumpAndUsageStatsPermission(Context context, String string2, PrintWriter printWriter) {
        boolean bl = DumpUtils.checkDumpPermission(context, string2, printWriter) && DumpUtils.checkUsageStatsPermission(context, string2, printWriter);
        return bl;
    }

    public static boolean checkDumpPermission(Context object, String string2, PrintWriter printWriter) {
        if (((Context)object).checkCallingOrSelfPermission("android.permission.DUMP") != 0) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Permission Denial: can't dump ");
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append(" from from pid=");
            ((StringBuilder)object).append(Binder.getCallingPid());
            ((StringBuilder)object).append(", uid=");
            ((StringBuilder)object).append(Binder.getCallingUid());
            ((StringBuilder)object).append(" due to missing android.permission.DUMP permission");
            DumpUtils.logMessage(printWriter, ((StringBuilder)object).toString());
            return false;
        }
        return true;
    }

    public static boolean checkUsageStatsPermission(Context object, String string2, PrintWriter printWriter) {
        int n = Binder.getCallingUid();
        if (n != 0 && n != 1000 && n != 1067 && n != 2000) {
            if (((Context)object).checkCallingOrSelfPermission("android.permission.PACKAGE_USAGE_STATS") != 0) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Permission Denial: can't dump ");
                ((StringBuilder)object).append(string2);
                ((StringBuilder)object).append(" from from pid=");
                ((StringBuilder)object).append(Binder.getCallingPid());
                ((StringBuilder)object).append(", uid=");
                ((StringBuilder)object).append(Binder.getCallingUid());
                ((StringBuilder)object).append(" due to missing android.permission.PACKAGE_USAGE_STATS permission");
                DumpUtils.logMessage(printWriter, ((StringBuilder)object).toString());
                return false;
            }
            AppOpsManager appOpsManager = ((Context)object).getSystemService(AppOpsManager.class);
            if ((object = ((Context)object).getPackageManager().getPackagesForUid(n)) != null) {
                int n2 = ((String[])object).length;
                for (int i = 0; i < n2; ++i) {
                    int n3 = appOpsManager.noteOpNoThrow(43, n, (String)object[i]);
                    if (n3 != 0) {
                        if (n3 != 3) {
                            continue;
                        }
                        return true;
                    }
                    return true;
                }
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Permission Denial: can't dump ");
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append(" from from pid=");
            ((StringBuilder)object).append(Binder.getCallingPid());
            ((StringBuilder)object).append(", uid=");
            ((StringBuilder)object).append(Binder.getCallingUid());
            ((StringBuilder)object).append(" due to android:get_usage_stats app-op not allowed");
            DumpUtils.logMessage(printWriter, ((StringBuilder)object).toString());
            return false;
        }
        return true;
    }

    public static void dumpAsync(Handler handler, final Dump dump, PrintWriter printWriter, final String string2, long l) {
        StringWriter stringWriter = new StringWriter();
        if (handler.runWithScissors(new Runnable(){

            @Override
            public void run() {
                FastPrintWriter fastPrintWriter = new FastPrintWriter(StringWriter.this);
                dump.dump(fastPrintWriter, string2);
                ((PrintWriter)fastPrintWriter).close();
            }
        }, l)) {
            printWriter.print(stringWriter.toString());
        } else {
            printWriter.println("... timed out");
        }
    }

    public static <TRec extends ComponentName.WithComponentName> Predicate<TRec> filterRecord(String string2) {
        if (TextUtils.isEmpty(string2)) {
            return _$$Lambda$DumpUtils$D1OlZP6xIpu72ypnJd0fzx0wd6I.INSTANCE;
        }
        if ("all".equals(string2)) {
            return _$$Lambda$eRa1rlfDk6Og2yFeXGHqUGPzRF0.INSTANCE;
        }
        if ("all-platform".equals(string2)) {
            return _$$Lambda$kVylv1rl9MOSbHFZoVyK5dl1kfY.INSTANCE;
        }
        if ("all-non-platform".equals(string2)) {
            return _$$Lambda$JwOUSWW2_Jzu15y4Kn4JuPh8tWM.INSTANCE;
        }
        if ("all-platform-critical".equals(string2)) {
            return _$$Lambda$grRTg3idX3yJe9Zyx_tmLBiD1DM.INSTANCE;
        }
        if ("all-platform-non-critical".equals(string2)) {
            return _$$Lambda$TCbPpgWlKJUHZgFKCczglAvxLfw.INSTANCE;
        }
        ComponentName componentName = ComponentName.unflattenFromString(string2);
        if (componentName != null) {
            return new _$$Lambda$DumpUtils$X8irOs5hfloCKy89_l1HRA1QeG0(componentName);
        }
        return new _$$Lambda$DumpUtils$vCLO_0ezRxkpSERUWCFrJ0ph5jg(ParseUtils.parseIntWithBase(string2, 16, -1), string2);
    }

    private static boolean isCriticalPackage(ComponentName componentName) {
        ComponentName[] arrcomponentName;
        if (componentName == null) {
            return false;
        }
        for (int i = 0; i < (arrcomponentName = CRITICAL_SECTION_COMPONENTS).length; ++i) {
            if (!componentName.equals(arrcomponentName[i])) continue;
            return true;
        }
        return false;
    }

    public static boolean isNonPlatformPackage(ComponentName.WithComponentName withComponentName) {
        boolean bl = withComponentName != null && !DumpUtils.isPlatformPackage(withComponentName.getComponentName());
        return bl;
    }

    public static boolean isNonPlatformPackage(ComponentName componentName) {
        boolean bl = componentName != null && DumpUtils.isNonPlatformPackage(componentName.getPackageName());
        return bl;
    }

    public static boolean isNonPlatformPackage(String string2) {
        boolean bl = string2 != null && !DumpUtils.isPlatformPackage(string2);
        return bl;
    }

    public static boolean isPlatformCriticalPackage(ComponentName.WithComponentName withComponentName) {
        boolean bl = withComponentName != null && DumpUtils.isPlatformPackage(withComponentName.getComponentName()) && DumpUtils.isCriticalPackage(withComponentName.getComponentName());
        return bl;
    }

    public static boolean isPlatformNonCriticalPackage(ComponentName.WithComponentName withComponentName) {
        boolean bl = withComponentName != null && DumpUtils.isPlatformPackage(withComponentName.getComponentName()) && !DumpUtils.isCriticalPackage(withComponentName.getComponentName());
        return bl;
    }

    public static boolean isPlatformPackage(ComponentName.WithComponentName withComponentName) {
        boolean bl = withComponentName != null && DumpUtils.isPlatformPackage(withComponentName.getComponentName());
        return bl;
    }

    public static boolean isPlatformPackage(ComponentName componentName) {
        boolean bl = componentName != null && DumpUtils.isPlatformPackage(componentName.getPackageName());
        return bl;
    }

    public static boolean isPlatformPackage(String string2) {
        boolean bl = string2 != null && (string2.equals("android") || string2.startsWith("android.") || string2.startsWith("com.android."));
        return bl;
    }

    static /* synthetic */ boolean lambda$filterRecord$0(ComponentName.WithComponentName withComponentName) {
        return false;
    }

    static /* synthetic */ boolean lambda$filterRecord$1(ComponentName componentName, ComponentName.WithComponentName withComponentName) {
        boolean bl = withComponentName != null && componentName.equals(withComponentName.getComponentName());
        return bl;
    }

    static /* synthetic */ boolean lambda$filterRecord$2(int n, String string2, ComponentName.WithComponentName withComponentName) {
        ComponentName componentName = withComponentName.getComponentName();
        boolean bl = n != -1 && System.identityHashCode(withComponentName) == n || componentName.flattenToString().toLowerCase().contains(string2.toLowerCase());
        return bl;
    }

    private static void logMessage(PrintWriter printWriter, String string2) {
        printWriter.println(string2);
    }

    public static interface Dump {
        public void dump(PrintWriter var1, String var2);
    }

}

