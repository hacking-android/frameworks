/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

public class ScreenRecordHelper {
    private static final String SYSUI_PACKAGE = "com.android.systemui";
    private static final String SYSUI_SCREENRECORD_LAUNCHER = "com.android.systemui.screenrecord.ScreenRecordDialog";
    private final Context mContext;

    public ScreenRecordHelper(Context context) {
        this.mContext = context;
    }

    public void launchRecordPrompt() {
        ComponentName componentName = new ComponentName(SYSUI_PACKAGE, SYSUI_SCREENRECORD_LAUNCHER);
        Intent intent = new Intent();
        intent.setComponent(componentName);
        intent.setFlags(268435456);
        this.mContext.startActivity(intent);
    }
}

