/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.ComponentName
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.PackageManager
 *  android.content.res.Resources
 *  android.os.Bundle
 *  android.provider.Settings
 *  android.provider.Settings$Global
 *  android.text.TextUtils
 *  android.util.Log
 */
package com.android.internal.telephony.uicc;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.telephony.uicc.InstallCarrierAppUtils;
import java.util.concurrent.TimeUnit;

public class InstallCarrierAppTrampolineActivity
extends Activity {
    private static final String BUNDLE_KEY_PACKAGE_NAME = "package_name";
    private static final String CARRIER_NAME = "carrier_name";
    private static final int DOWNLOAD_RESULT = 2;
    private static final int INSTALL_CARRIER_APP_DIALOG_REQUEST = 1;
    private static final String LOG_TAG = "CarrierAppInstall";
    private String mPackageName;

    private void finishNoAnimation() {
        this.finish();
        this.overridePendingTransition(0, 0);
    }

    public static Intent get(Context context, String string) {
        context = new Intent(context, InstallCarrierAppTrampolineActivity.class);
        context.putExtra(BUNDLE_KEY_PACKAGE_NAME, string);
        return context;
    }

    protected void onActivityResult(int n, int n2, Intent intent) {
        super.onActivityResult(n, n2, intent);
        if (n == 1) {
            if (n2 == 2) {
                this.startActivity(InstallCarrierAppUtils.getPlayStoreIntent(this.mPackageName));
            }
            this.finishNoAnimation();
        }
    }

    protected void onCreate(Bundle object) {
        super.onCreate((Bundle)object);
        Object object2 = this.getIntent();
        if (object2 != null) {
            this.mPackageName = object2.getStringExtra(BUNDLE_KEY_PACKAGE_NAME);
        }
        if (object == null) {
            long l = Settings.Global.getLong((ContentResolver)this.getContentResolver(), (String)"install_carrier_app_notification_sleep_millis", (long)TimeUnit.HOURS.toMillis(24L));
            object = new StringBuilder();
            ((StringBuilder)object).append("Sleeping carrier app install notification for : ");
            ((StringBuilder)object).append(l);
            ((StringBuilder)object).append(" millis");
            Log.d((String)LOG_TAG, (String)((StringBuilder)object).toString());
            InstallCarrierAppUtils.showNotificationIfNotInstalledDelayed((Context)this, this.mPackageName, l);
        }
        object = new Intent();
        object.setComponent(ComponentName.unflattenFromString((String)Resources.getSystem().getString(17039684)));
        object2 = InstallCarrierAppUtils.getAppNameFromPackageName((Context)this, this.mPackageName);
        if (!TextUtils.isEmpty((CharSequence)object2)) {
            object.putExtra(CARRIER_NAME, (String)object2);
        }
        if (object.resolveActivity(this.getPackageManager()) == null) {
            Log.d((String)LOG_TAG, (String)"Could not resolve activity for installing the carrier app");
            this.finishNoAnimation();
        } else {
            this.startActivityForResult((Intent)object, 1);
        }
    }
}

