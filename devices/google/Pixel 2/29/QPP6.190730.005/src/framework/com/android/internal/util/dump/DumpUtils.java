/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.util.dump;

import android.content.ComponentName;
import com.android.internal.util.dump.DualDumpOutputStream;

public class DumpUtils {
    public static void writeComponentName(DualDumpOutputStream dualDumpOutputStream, String string2, long l, ComponentName componentName) {
        l = dualDumpOutputStream.start(string2, l);
        dualDumpOutputStream.write("package_name", 1138166333441L, componentName.getPackageName());
        dualDumpOutputStream.write("class_name", 1138166333442L, componentName.getClassName());
        dualDumpOutputStream.end(l);
    }

    public static void writeStringIfNotNull(DualDumpOutputStream dualDumpOutputStream, String string2, long l, String string3) {
        if (string3 != null) {
            dualDumpOutputStream.write(string2, l, string3);
        }
    }
}

