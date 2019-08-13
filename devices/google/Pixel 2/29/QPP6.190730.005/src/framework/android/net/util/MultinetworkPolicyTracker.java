/*
 * Decompiled with CFR 0.145.
 */
package android.net.util;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.net.Uri;
import android.net.util._$$Lambda$MultinetworkPolicyTracker$0siHK6f4lHJz8hbdHbT6G4Kp_V4;
import android.os.Handler;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Slog;
import com.android.internal.annotations.VisibleForTesting;
import java.util.Arrays;
import java.util.List;

public class MultinetworkPolicyTracker {
    private static String TAG = MultinetworkPolicyTracker.class.getSimpleName();
    private volatile boolean mAvoidBadWifi = true;
    private final BroadcastReceiver mBroadcastReceiver;
    private final Context mContext;
    private final Handler mHandler;
    private volatile int mMeteredMultipathPreference;
    private final Runnable mReevaluateRunnable;
    private final ContentResolver mResolver;
    private final SettingObserver mSettingObserver;
    private final List<Uri> mSettingsUris;

    public MultinetworkPolicyTracker(Context context, Handler handler) {
        this(context, handler, null);
    }

    public MultinetworkPolicyTracker(Context context, Handler handler, Runnable runnable) {
        this.mContext = context;
        this.mHandler = handler;
        this.mReevaluateRunnable = new _$$Lambda$MultinetworkPolicyTracker$0siHK6f4lHJz8hbdHbT6G4Kp_V4(this, runnable);
        this.mSettingsUris = Arrays.asList(Settings.Global.getUriFor("network_avoid_bad_wifi"), Settings.Global.getUriFor("network_metered_multipath_preference"));
        this.mResolver = this.mContext.getContentResolver();
        this.mSettingObserver = new SettingObserver();
        this.mBroadcastReceiver = new BroadcastReceiver(){

            @Override
            public void onReceive(Context context, Intent intent) {
                MultinetworkPolicyTracker.this.reevaluate();
            }
        };
        this.updateAvoidBadWifi();
        this.updateMeteredMultipathPreference();
    }

    public int configMeteredMultipathPreference() {
        return this.mContext.getResources().getInteger(17694851);
    }

    public boolean configRestrictsAvoidBadWifi() {
        boolean bl = this.mContext.getResources().getInteger(17694849) == 0;
        return bl;
    }

    public boolean getAvoidBadWifi() {
        return this.mAvoidBadWifi;
    }

    public String getAvoidBadWifiSetting() {
        return Settings.Global.getString(this.mResolver, "network_avoid_bad_wifi");
    }

    public int getMeteredMultipathPreference() {
        return this.mMeteredMultipathPreference;
    }

    public /* synthetic */ void lambda$new$0$MultinetworkPolicyTracker(Runnable runnable) {
        if (this.updateAvoidBadWifi() && runnable != null) {
            runnable.run();
        }
        this.updateMeteredMultipathPreference();
    }

    @VisibleForTesting
    public void reevaluate() {
        this.mHandler.post(this.mReevaluateRunnable);
    }

    public boolean shouldNotifyWifiUnvalidated() {
        boolean bl = this.configRestrictsAvoidBadWifi() && this.getAvoidBadWifiSetting() == null;
        return bl;
    }

    public void shutdown() {
        this.mResolver.unregisterContentObserver(this.mSettingObserver);
        this.mContext.unregisterReceiver(this.mBroadcastReceiver);
    }

    public void start() {
        for (Uri uri : this.mSettingsUris) {
            this.mResolver.registerContentObserver(uri, false, this.mSettingObserver);
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.CONFIGURATION_CHANGED");
        this.mContext.registerReceiverAsUser(this.mBroadcastReceiver, UserHandle.ALL, intentFilter, null, null);
        this.reevaluate();
    }

    public boolean updateAvoidBadWifi() {
        boolean bl = "1".equals(this.getAvoidBadWifiSetting());
        boolean bl2 = this.mAvoidBadWifi;
        boolean bl3 = false;
        bl = bl || !this.configRestrictsAvoidBadWifi();
        this.mAvoidBadWifi = bl;
        bl = bl3;
        if (this.mAvoidBadWifi != bl2) {
            bl = true;
        }
        return bl;
    }

    public void updateMeteredMultipathPreference() {
        String string2 = Settings.Global.getString(this.mResolver, "network_metered_multipath_preference");
        try {
            this.mMeteredMultipathPreference = Integer.parseInt(string2);
        }
        catch (NumberFormatException numberFormatException) {
            this.mMeteredMultipathPreference = this.configMeteredMultipathPreference();
        }
    }

    private class SettingObserver
    extends ContentObserver {
        public SettingObserver() {
            super(null);
        }

        @Override
        public void onChange(boolean bl) {
            Slog.wtf(TAG, "Should never be reached.");
        }

        @Override
        public void onChange(boolean bl, Uri uri) {
            if (!MultinetworkPolicyTracker.this.mSettingsUris.contains(uri)) {
                String string2 = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unexpected settings observation: ");
                stringBuilder.append(uri);
                Slog.wtf(string2, stringBuilder.toString());
            }
            MultinetworkPolicyTracker.this.reevaluate();
        }
    }

}

