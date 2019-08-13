/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.annotation.SystemApi;
import android.os.Bundle;

@SystemApi
public class BroadcastOptions {
    static final String KEY_ALLOW_BACKGROUND_ACTIVITY_STARTS = "android:broadcast.allowBackgroundActivityStarts";
    static final String KEY_DONT_SEND_TO_RESTRICTED_APPS = "android:broadcast.dontSendToRestrictedApps";
    static final String KEY_MAX_MANIFEST_RECEIVER_API_LEVEL = "android:broadcast.maxManifestReceiverApiLevel";
    static final String KEY_MIN_MANIFEST_RECEIVER_API_LEVEL = "android:broadcast.minManifestReceiverApiLevel";
    static final String KEY_TEMPORARY_APP_WHITELIST_DURATION = "android:broadcast.temporaryAppWhitelistDuration";
    private boolean mAllowBackgroundActivityStarts;
    private boolean mDontSendToRestrictedApps = false;
    private int mMaxManifestReceiverApiLevel = 10000;
    private int mMinManifestReceiverApiLevel = 0;
    private long mTemporaryAppWhitelistDuration;

    private BroadcastOptions() {
    }

    public BroadcastOptions(Bundle bundle) {
        this.mTemporaryAppWhitelistDuration = bundle.getLong(KEY_TEMPORARY_APP_WHITELIST_DURATION);
        this.mMinManifestReceiverApiLevel = bundle.getInt(KEY_MIN_MANIFEST_RECEIVER_API_LEVEL, 0);
        this.mMaxManifestReceiverApiLevel = bundle.getInt(KEY_MAX_MANIFEST_RECEIVER_API_LEVEL, 10000);
        this.mDontSendToRestrictedApps = bundle.getBoolean(KEY_DONT_SEND_TO_RESTRICTED_APPS, false);
        this.mAllowBackgroundActivityStarts = bundle.getBoolean(KEY_ALLOW_BACKGROUND_ACTIVITY_STARTS, false);
    }

    public static BroadcastOptions makeBasic() {
        return new BroadcastOptions();
    }

    public boolean allowsBackgroundActivityStarts() {
        return this.mAllowBackgroundActivityStarts;
    }

    public int getMaxManifestReceiverApiLevel() {
        return this.mMaxManifestReceiverApiLevel;
    }

    public int getMinManifestReceiverApiLevel() {
        return this.mMinManifestReceiverApiLevel;
    }

    public long getTemporaryAppWhitelistDuration() {
        return this.mTemporaryAppWhitelistDuration;
    }

    public boolean isDontSendToRestrictedApps() {
        return this.mDontSendToRestrictedApps;
    }

    public void setBackgroundActivityStartsAllowed(boolean bl) {
        this.mAllowBackgroundActivityStarts = bl;
    }

    public void setDontSendToRestrictedApps(boolean bl) {
        this.mDontSendToRestrictedApps = bl;
    }

    public void setMaxManifestReceiverApiLevel(int n) {
        this.mMaxManifestReceiverApiLevel = n;
    }

    public void setMinManifestReceiverApiLevel(int n) {
        this.mMinManifestReceiverApiLevel = n;
    }

    public void setTemporaryAppWhitelistDuration(long l) {
        this.mTemporaryAppWhitelistDuration = l;
    }

    public Bundle toBundle() {
        Bundle bundle;
        block5 : {
            int n;
            bundle = new Bundle();
            long l = this.mTemporaryAppWhitelistDuration;
            if (l > 0L) {
                bundle.putLong(KEY_TEMPORARY_APP_WHITELIST_DURATION, l);
            }
            if ((n = this.mMinManifestReceiverApiLevel) != 0) {
                bundle.putInt(KEY_MIN_MANIFEST_RECEIVER_API_LEVEL, n);
            }
            if ((n = this.mMaxManifestReceiverApiLevel) != 10000) {
                bundle.putInt(KEY_MAX_MANIFEST_RECEIVER_API_LEVEL, n);
            }
            if (this.mDontSendToRestrictedApps) {
                bundle.putBoolean(KEY_DONT_SEND_TO_RESTRICTED_APPS, true);
            }
            if (this.mAllowBackgroundActivityStarts) {
                bundle.putBoolean(KEY_ALLOW_BACKGROUND_ACTIVITY_STARTS, true);
            }
            if (!bundle.isEmpty()) break block5;
            bundle = null;
        }
        return bundle;
    }
}

