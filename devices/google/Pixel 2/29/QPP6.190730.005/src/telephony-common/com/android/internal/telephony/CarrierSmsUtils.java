/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.PackageManager
 *  android.content.pm.ResolveInfo
 *  android.content.pm.ServiceInfo
 *  android.os.Binder
 *  android.os.PersistableBundle
 *  android.telephony.CarrierConfigManager
 *  android.telephony.Rlog
 */
package com.android.internal.telephony;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Binder;
import android.os.PersistableBundle;
import android.telephony.CarrierConfigManager;
import android.telephony.Rlog;
import com.android.internal.telephony.Phone;
import java.util.List;

public class CarrierSmsUtils {
    private static final String CARRIER_IMS_PACKAGE_KEY = "config_ims_package_override_string";
    protected static final String TAG = CarrierSmsUtils.class.getSimpleName();
    protected static final boolean VDBG = false;

    private CarrierSmsUtils() {
    }

    private static String getCarrierImsPackage(Context object, Phone phone) {
        long l;
        block5 : {
            if ((object = (CarrierConfigManager)object.getSystemService("carrier_config")) == null) {
                Rlog.e((String)TAG, (String)"Failed to retrieve CarrierConfigManager");
                return null;
            }
            l = Binder.clearCallingIdentity();
            object = object.getConfigForSubId(phone.getSubId());
            if (object != null) break block5;
            Binder.restoreCallingIdentity((long)l);
            return null;
        }
        try {
            object = object.getString(CARRIER_IMS_PACKAGE_KEY, null);
            return object;
        }
        finally {
            Binder.restoreCallingIdentity((long)l);
        }
    }

    public static String getCarrierImsPackageForIntent(Context object, Phone object2, Intent object3) {
        if ((object2 = CarrierSmsUtils.getCarrierImsPackage((Context)object, (Phone)object2)) == null) {
            return null;
        }
        for (ResolveInfo resolveInfo : object.getPackageManager().queryIntentServices(object3, 0)) {
            if (resolveInfo.serviceInfo == null) {
                object3 = TAG;
                object = new StringBuilder();
                ((StringBuilder)object).append("Can't get service information from ");
                ((StringBuilder)object).append((Object)resolveInfo);
                Rlog.e((String)object3, (String)((StringBuilder)object).toString());
                continue;
            }
            if (!((String)object2).equals(resolveInfo.serviceInfo.packageName)) continue;
            return object2;
        }
        return null;
    }
}

