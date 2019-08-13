/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 */
package com.android.internal.telephony.uicc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.android.internal.telephony.uicc.InstallCarrierAppUtils;
import com.android.internal.telephony.uicc.UiccProfile;

public class ShowInstallAppNotificationReceiver
extends BroadcastReceiver {
    private static final String EXTRA_PACKAGE_NAME = "package_name";

    public static Intent get(Context context, String string) {
        context = new Intent(context, ShowInstallAppNotificationReceiver.class);
        context.putExtra(EXTRA_PACKAGE_NAME, string);
        return context;
    }

    public void onReceive(Context context, Intent object) {
        if (!UiccProfile.isPackageInstalled(context, (String)(object = object.getStringExtra(EXTRA_PACKAGE_NAME)))) {
            InstallCarrierAppUtils.showNotification(context, (String)object);
            InstallCarrierAppUtils.registerPackageInstallReceiver(context);
        }
    }
}

