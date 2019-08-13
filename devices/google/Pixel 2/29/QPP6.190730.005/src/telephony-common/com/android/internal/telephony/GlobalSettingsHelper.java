/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.provider.Settings
 *  android.provider.Settings$Global
 *  android.provider.Settings$SettingNotFoundException
 *  android.telephony.SubscriptionManager
 *  android.telephony.TelephonyManager
 */
package com.android.internal.telephony;

import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;

public class GlobalSettingsHelper {
    public static boolean getBoolean(Context context, String string, int n) throws Settings.SettingNotFoundException {
        string = GlobalSettingsHelper.getSettingName(context, string, n);
        n = Settings.Global.getInt((ContentResolver)context.getContentResolver(), (String)string);
        boolean bl = true;
        if (n != 1) {
            bl = false;
        }
        return bl;
    }

    public static boolean getBoolean(Context context, String string, int n, boolean bl) {
        string = GlobalSettingsHelper.getSettingName(context, string, n);
        context = context.getContentResolver();
        n = Settings.Global.getInt((ContentResolver)context, (String)string, (int)bl);
        boolean bl2 = true;
        if (n != 1) {
            bl2 = false;
        }
        return bl2;
    }

    public static int getInt(Context context, String string, int n, int n2) {
        string = GlobalSettingsHelper.getSettingName(context, string, n);
        return Settings.Global.getInt((ContentResolver)context.getContentResolver(), (String)string, (int)n2);
    }

    private static String getSettingName(Context object, String string, int n) {
        if (TelephonyManager.from((Context)object).getSimCount() > 1 && SubscriptionManager.isValidSubscriptionId((int)n)) {
            object = new StringBuilder();
            ((StringBuilder)object).append(string);
            ((StringBuilder)object).append(n);
            return ((StringBuilder)object).toString();
        }
        return string;
    }

    public static boolean setBoolean(Context context, String string, int n, boolean bl) {
        return GlobalSettingsHelper.setInt(context, string, n, (int)bl);
    }

    public static boolean setInt(Context context, String string, int n, int n2) {
        boolean bl;
        string = GlobalSettingsHelper.getSettingName(context, string, n);
        try {
            n = Settings.Global.getInt((ContentResolver)context.getContentResolver(), (String)string);
            bl = n != n2;
        }
        catch (Settings.SettingNotFoundException settingNotFoundException) {
            bl = true;
        }
        if (bl) {
            Settings.Global.putInt((ContentResolver)context.getContentResolver(), (String)string, (int)n2);
        }
        return bl;
    }
}

