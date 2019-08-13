/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.app.AlarmManager
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.content.Intent
 *  android.database.ContentObserver
 *  android.net.Uri
 *  android.os.Handler
 *  android.os.SystemClock
 *  android.os.SystemProperties
 *  android.os.UserHandle
 *  android.provider.Settings
 *  android.provider.Settings$Global
 *  android.provider.Settings$SettingNotFoundException
 */
package com.android.internal.telephony;

import android.app.AlarmManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.Settings;

public class OldTimeServiceHelper {
    private static final String TIMEZONE_PROPERTY = "persist.sys.timezone";
    private final Context mContext;
    private final ContentResolver mCr;
    private Listener mListener;

    public OldTimeServiceHelper(Context context) {
        this.mContext = context;
        this.mCr = context.getContentResolver();
    }

    static boolean isTimeZoneSettingInitializedStatic() {
        String string = SystemProperties.get((String)TIMEZONE_PROPERTY);
        boolean bl = string != null && string.length() > 0 && !string.equals("GMT");
        return bl;
    }

    static void setDeviceTimeZoneStatic(Context context, String string) {
        ((AlarmManager)context.getSystemService("alarm")).setTimeZone(string);
        Intent intent = new Intent("android.intent.action.NETWORK_SET_TIMEZONE");
        intent.addFlags(536870912);
        intent.putExtra("time-zone", string);
        context.sendStickyBroadcastAsUser(intent, UserHandle.ALL);
    }

    public long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    public long elapsedRealtime() {
        return SystemClock.elapsedRealtime();
    }

    public boolean isTimeDetectionEnabled() {
        boolean bl = true;
        try {
            int n = Settings.Global.getInt((ContentResolver)this.mCr, (String)"auto_time");
            if (n <= 0) {
                bl = false;
            }
            return bl;
        }
        catch (Settings.SettingNotFoundException settingNotFoundException) {
            return true;
        }
    }

    public boolean isTimeZoneDetectionEnabled() {
        boolean bl = true;
        try {
            int n = Settings.Global.getInt((ContentResolver)this.mCr, (String)"auto_time_zone");
            if (n <= 0) {
                bl = false;
            }
            return bl;
        }
        catch (Settings.SettingNotFoundException settingNotFoundException) {
            return true;
        }
    }

    public boolean isTimeZoneSettingInitialized() {
        return OldTimeServiceHelper.isTimeZoneSettingInitializedStatic();
    }

    public void setDeviceTime(long l) {
        SystemClock.setCurrentTimeMillis((long)l);
        Intent intent = new Intent("android.intent.action.NETWORK_SET_TIME");
        intent.addFlags(536870912);
        intent.putExtra("time", l);
        this.mContext.sendStickyBroadcastAsUser(intent, UserHandle.ALL);
    }

    public void setDeviceTimeZone(String string) {
        OldTimeServiceHelper.setDeviceTimeZoneStatic(this.mContext, string);
    }

    public void setListener(final Listener listener) {
        if (listener != null) {
            if (this.mListener == null) {
                this.mListener = listener;
                this.mCr.registerContentObserver(Settings.Global.getUriFor((String)"auto_time"), true, new ContentObserver(new Handler()){

                    public void onChange(boolean bl) {
                        listener.onTimeDetectionChange(OldTimeServiceHelper.this.isTimeDetectionEnabled());
                    }
                });
                this.mCr.registerContentObserver(Settings.Global.getUriFor((String)"auto_time_zone"), true, new ContentObserver(new Handler()){

                    public void onChange(boolean bl) {
                        listener.onTimeZoneDetectionChange(OldTimeServiceHelper.this.isTimeZoneDetectionEnabled());
                    }
                });
                return;
            }
            throw new IllegalStateException("listener already set");
        }
        throw new NullPointerException("listener==null");
    }

    public static interface Listener {
        public void onTimeDetectionChange(boolean var1);

        public void onTimeZoneDetectionChange(boolean var1);
    }

}

