/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 *  android.app.AppOpsManager
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.os.Binder
 *  android.telephony.Rlog
 *  android.util.Log
 *  com.android.internal.telephony.TelephonyPermissions
 */
package com.android.internal.telephony;

import android.annotation.UnsupportedAppUsage;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.telephony.Rlog;
import android.util.Log;
import com.android.internal.telephony.CarrierSmsUtils;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.TelephonyPermissions;

public class SmsPermissions {
    static final String LOG_TAG = "SmsPermissions";
    @UnsupportedAppUsage
    private final AppOpsManager mAppOps;
    @UnsupportedAppUsage
    private final Context mContext;
    @UnsupportedAppUsage
    private final Phone mPhone;

    public SmsPermissions(Phone phone, Context context, AppOpsManager appOpsManager) {
        this.mPhone = phone;
        this.mContext = context;
        this.mAppOps = appOpsManager;
    }

    public boolean checkCallingCanSendSms(String string, String string2) {
        this.mContext.enforceCallingPermission("android.permission.SEND_SMS", string2);
        boolean bl = this.mAppOps.noteOp(20, Binder.getCallingUid(), string) == 0;
        return bl;
    }

    public boolean checkCallingCanSendText(boolean bl, String string, String string2) {
        if (!bl) {
            try {
                this.enforceCallerIsImsAppOrCarrierApp(string2);
                return true;
            }
            catch (SecurityException securityException) {
                this.mContext.enforceCallingPermission("android.permission.MODIFY_PHONE_STATE", string2);
            }
        }
        return this.checkCallingCanSendSms(string, string2);
    }

    public boolean checkCallingOrSelfCanSendSms(String string, String string2) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.SEND_SMS", string2);
        boolean bl = this.mAppOps.noteOp(20, Binder.getCallingUid(), string) == 0;
        return bl;
    }

    public void enforceCallerIsImsAppOrCarrierApp(String string) {
        block4 : {
            int n = Binder.getCallingUid();
            String string2 = CarrierSmsUtils.getCarrierImsPackageForIntent(this.mContext, this.mPhone, new Intent("android.service.carrier.CarrierMessagingService"));
            if (string2 != null) {
                try {
                    int n2 = this.mContext.getPackageManager().getPackageUid(string2, 0);
                    if (n == n2) {
                        return;
                    }
                }
                catch (PackageManager.NameNotFoundException nameNotFoundException) {
                    if (!Rlog.isLoggable((String)"SMS", (int)3)) break block4;
                    this.log("Cannot find configured carrier ims package");
                }
            }
        }
        TelephonyPermissions.enforceCallingOrSelfCarrierPrivilege((int)this.mPhone.getSubId(), (String)string);
    }

    @UnsupportedAppUsage
    protected void log(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[IccSmsInterfaceManager] ");
        stringBuilder.append(string);
        Log.d((String)LOG_TAG, (String)stringBuilder.toString());
    }
}

