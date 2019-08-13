/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.util;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.UserHandle;
import android.provider.Settings;

public class EmergencyAffordanceManager {
    private static final String EMERGENCY_CALL_NUMBER_SETTING = "emergency_affordance_number";
    public static final boolean ENABLED = true;
    private static final String FORCE_EMERGENCY_AFFORDANCE_SETTING = "force_emergency_affordance";
    private final Context mContext;

    public EmergencyAffordanceManager(Context context) {
        this.mContext = context;
    }

    private boolean forceShowing() {
        ContentResolver contentResolver = this.mContext.getContentResolver();
        boolean bl = false;
        if (Settings.Global.getInt(contentResolver, FORCE_EMERGENCY_AFFORDANCE_SETTING, 0) != 0) {
            bl = true;
        }
        return bl;
    }

    private static Uri getPhoneUri(Context object) {
        String string2 = ((Context)object).getResources().getString(17039728);
        Object object2 = string2;
        if (Build.IS_DEBUGGABLE) {
            object = Settings.Global.getString(((Context)object).getContentResolver(), EMERGENCY_CALL_NUMBER_SETTING);
            object2 = string2;
            if (object != null) {
                object2 = object;
            }
        }
        return Uri.fromParts("tel", (String)object2, null);
    }

    private boolean isEmergencyAffordanceNeeded() {
        ContentResolver contentResolver = this.mContext.getContentResolver();
        boolean bl = false;
        if (Settings.Global.getInt(contentResolver, "emergency_affordance_needed", 0) != 0) {
            bl = true;
        }
        return bl;
    }

    private static void performEmergencyCall(Context context) {
        Intent intent = new Intent("android.intent.action.CALL_EMERGENCY");
        intent.setData(EmergencyAffordanceManager.getPhoneUri(context));
        intent.setFlags(268435456);
        context.startActivityAsUser(intent, UserHandle.CURRENT);
    }

    public boolean needsEmergencyAffordance() {
        if (this.forceShowing()) {
            return true;
        }
        return this.isEmergencyAffordanceNeeded();
    }

    public final void performEmergencyCall() {
        EmergencyAffordanceManager.performEmergencyCall(this.mContext);
    }
}

