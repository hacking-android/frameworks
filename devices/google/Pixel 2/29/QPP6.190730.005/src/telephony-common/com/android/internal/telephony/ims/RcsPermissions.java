/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.app.AppOpsManager
 *  android.content.Context
 *  android.os.Binder
 */
package com.android.internal.telephony.ims;

import android.app.AppOpsManager;
import android.content.Context;
import android.os.Binder;

class RcsPermissions {
    RcsPermissions() {
    }

    private static void checkOp(Context object, int n, String string, int n2) {
        if (((AppOpsManager)object.getSystemService("appops")).noteOp(n2, n, string) == 0) {
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(AppOpsManager.opToName((int)n2));
        ((StringBuilder)object).append(" not allowed for ");
        ((StringBuilder)object).append(string);
        throw new SecurityException(((StringBuilder)object).toString());
    }

    static void checkReadPermissions(Context context, String string) {
        int n = Binder.getCallingPid();
        int n2 = Binder.getCallingUid();
        context.enforcePermission("android.permission.READ_SMS", n, n2, null);
        RcsPermissions.checkOp(context, n2, string, 14);
    }

    static void checkWritePermissions(Context context, String string) {
        RcsPermissions.checkOp(context, Binder.getCallingUid(), string, 15);
    }
}

