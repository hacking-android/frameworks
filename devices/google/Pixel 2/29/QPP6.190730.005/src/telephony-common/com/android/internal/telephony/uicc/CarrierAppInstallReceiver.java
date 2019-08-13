/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.net.Uri
 *  android.text.TextUtils
 *  android.util.Log
 */
package com.android.internal.telephony.uicc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.telephony.uicc.InstallCarrierAppUtils;

public class CarrierAppInstallReceiver
extends BroadcastReceiver {
    private static final String LOG_TAG = "CarrierAppInstall";

    public void onReceive(Context context, Intent object) {
        if ("android.intent.action.PACKAGE_ADDED".equals(object.getAction())) {
            Log.d((String)LOG_TAG, (String)"Received package install intent");
            object = object.getData().getSchemeSpecificPart();
            if (TextUtils.isEmpty((CharSequence)object)) {
                Log.w((String)LOG_TAG, (String)"Package is empty, ignoring");
                return;
            }
            InstallCarrierAppUtils.hideNotification(context, (String)object);
            if (!InstallCarrierAppUtils.isPackageInstallNotificationActive(context)) {
                InstallCarrierAppUtils.unregisterPackageInstallReceiver(context);
            }
        }
    }
}

