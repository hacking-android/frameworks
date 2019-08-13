/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.ArrayMap;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@SystemApi
public final class BatterySaverPolicyConfig
implements Parcelable {
    public static final Parcelable.Creator<BatterySaverPolicyConfig> CREATOR = new Parcelable.Creator<BatterySaverPolicyConfig>(){

        @Override
        public BatterySaverPolicyConfig createFromParcel(Parcel parcel) {
            return new BatterySaverPolicyConfig(parcel);
        }

        public BatterySaverPolicyConfig[] newArray(int n) {
            return new BatterySaverPolicyConfig[n];
        }
    };
    private final float mAdjustBrightnessFactor;
    private final boolean mAdvertiseIsEnabled;
    private final boolean mDeferFullBackup;
    private final boolean mDeferKeyValueBackup;
    private final Map<String, String> mDeviceSpecificSettings;
    private final boolean mDisableAnimation;
    private final boolean mDisableAod;
    private final boolean mDisableLaunchBoost;
    private final boolean mDisableOptionalSensors;
    private final boolean mDisableSoundTrigger;
    private final boolean mDisableVibration;
    private final boolean mEnableAdjustBrightness;
    private final boolean mEnableDataSaver;
    private final boolean mEnableFirewall;
    private final boolean mEnableNightMode;
    private final boolean mEnableQuickDoze;
    private final boolean mForceAllAppsStandby;
    private final boolean mForceBackgroundCheck;
    private final int mLocationMode;

    private BatterySaverPolicyConfig(Builder builder) {
        this.mAdjustBrightnessFactor = Math.max(0.0f, Math.min(builder.mAdjustBrightnessFactor, 1.0f));
        this.mAdvertiseIsEnabled = builder.mAdvertiseIsEnabled;
        this.mDeferFullBackup = builder.mDeferFullBackup;
        this.mDeferKeyValueBackup = builder.mDeferKeyValueBackup;
        this.mDeviceSpecificSettings = Collections.unmodifiableMap(new ArrayMap(builder.mDeviceSpecificSettings));
        this.mDisableAnimation = builder.mDisableAnimation;
        this.mDisableAod = builder.mDisableAod;
        this.mDisableLaunchBoost = builder.mDisableLaunchBoost;
        this.mDisableOptionalSensors = builder.mDisableOptionalSensors;
        this.mDisableSoundTrigger = builder.mDisableSoundTrigger;
        this.mDisableVibration = builder.mDisableVibration;
        this.mEnableAdjustBrightness = builder.mEnableAdjustBrightness;
        this.mEnableDataSaver = builder.mEnableDataSaver;
        this.mEnableFirewall = builder.mEnableFirewall;
        this.mEnableNightMode = builder.mEnableNightMode;
        this.mEnableQuickDoze = builder.mEnableQuickDoze;
        this.mForceAllAppsStandby = builder.mForceAllAppsStandby;
        this.mForceBackgroundCheck = builder.mForceBackgroundCheck;
        this.mLocationMode = Math.max(0, Math.min(builder.mLocationMode, 4));
    }

    private BatterySaverPolicyConfig(Parcel parcel) {
        this.mAdjustBrightnessFactor = Math.max(0.0f, Math.min(parcel.readFloat(), 1.0f));
        this.mAdvertiseIsEnabled = parcel.readBoolean();
        this.mDeferFullBackup = parcel.readBoolean();
        this.mDeferKeyValueBackup = parcel.readBoolean();
        int n = parcel.readInt();
        ArrayMap<String, String> arrayMap = new ArrayMap<String, String>(n);
        for (int i = 0; i < n; ++i) {
            String string2 = TextUtils.emptyIfNull(parcel.readString());
            String string3 = TextUtils.emptyIfNull(parcel.readString());
            if (string2.trim().isEmpty()) continue;
            arrayMap.put(string2, string3);
        }
        this.mDeviceSpecificSettings = Collections.unmodifiableMap(arrayMap);
        this.mDisableAnimation = parcel.readBoolean();
        this.mDisableAod = parcel.readBoolean();
        this.mDisableLaunchBoost = parcel.readBoolean();
        this.mDisableOptionalSensors = parcel.readBoolean();
        this.mDisableSoundTrigger = parcel.readBoolean();
        this.mDisableVibration = parcel.readBoolean();
        this.mEnableAdjustBrightness = parcel.readBoolean();
        this.mEnableDataSaver = parcel.readBoolean();
        this.mEnableFirewall = parcel.readBoolean();
        this.mEnableNightMode = parcel.readBoolean();
        this.mEnableQuickDoze = parcel.readBoolean();
        this.mForceAllAppsStandby = parcel.readBoolean();
        this.mForceBackgroundCheck = parcel.readBoolean();
        this.mLocationMode = Math.max(0, Math.min(parcel.readInt(), 4));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public float getAdjustBrightnessFactor() {
        return this.mAdjustBrightnessFactor;
    }

    public boolean getAdvertiseIsEnabled() {
        return this.mAdvertiseIsEnabled;
    }

    public boolean getDeferFullBackup() {
        return this.mDeferFullBackup;
    }

    public boolean getDeferKeyValueBackup() {
        return this.mDeferKeyValueBackup;
    }

    public Map<String, String> getDeviceSpecificSettings() {
        return this.mDeviceSpecificSettings;
    }

    public boolean getDisableAnimation() {
        return this.mDisableAnimation;
    }

    public boolean getDisableAod() {
        return this.mDisableAod;
    }

    public boolean getDisableLaunchBoost() {
        return this.mDisableLaunchBoost;
    }

    public boolean getDisableOptionalSensors() {
        return this.mDisableOptionalSensors;
    }

    public boolean getDisableSoundTrigger() {
        return this.mDisableSoundTrigger;
    }

    public boolean getDisableVibration() {
        return this.mDisableVibration;
    }

    public boolean getEnableAdjustBrightness() {
        return this.mEnableAdjustBrightness;
    }

    public boolean getEnableDataSaver() {
        return this.mEnableDataSaver;
    }

    public boolean getEnableFirewall() {
        return this.mEnableFirewall;
    }

    public boolean getEnableNightMode() {
        return this.mEnableNightMode;
    }

    public boolean getEnableQuickDoze() {
        return this.mEnableQuickDoze;
    }

    public boolean getForceAllAppsStandby() {
        return this.mForceAllAppsStandby;
    }

    public boolean getForceBackgroundCheck() {
        return this.mForceBackgroundCheck;
    }

    public int getLocationMode() {
        return this.mLocationMode;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, String> object2 : this.mDeviceSpecificSettings.entrySet()) {
            stringBuilder.append(object2.getKey());
            stringBuilder.append("=");
            stringBuilder.append(object2.getValue());
            stringBuilder.append(",");
        }
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("adjust_brightness_disabled=");
        stringBuilder2.append(this.mEnableAdjustBrightness ^ true);
        stringBuilder2.append(",adjust_brightness_factor=");
        stringBuilder2.append(this.mAdjustBrightnessFactor);
        stringBuilder2.append(",advertise_is_enabled=");
        stringBuilder2.append(this.mAdvertiseIsEnabled);
        stringBuilder2.append(",animation_disabled=");
        stringBuilder2.append(this.mDisableAnimation);
        stringBuilder2.append(",aod_disabled=");
        stringBuilder2.append(this.mDisableAod);
        stringBuilder2.append(",datasaver_disabled=");
        stringBuilder2.append(this.mEnableDataSaver ^ true);
        stringBuilder2.append(",enable_night_mode=");
        stringBuilder2.append(this.mEnableNightMode);
        stringBuilder2.append(",firewall_disabled=");
        stringBuilder2.append(this.mEnableFirewall ^ true);
        stringBuilder2.append(",force_all_apps_standby=");
        stringBuilder2.append(this.mForceAllAppsStandby);
        stringBuilder2.append(",force_background_check=");
        stringBuilder2.append(this.mForceBackgroundCheck);
        stringBuilder2.append(",fullbackup_deferred=");
        stringBuilder2.append(this.mDeferFullBackup);
        stringBuilder2.append(",gps_mode=");
        stringBuilder2.append(this.mLocationMode);
        stringBuilder2.append(",keyvaluebackup_deferred=");
        stringBuilder2.append(this.mDeferKeyValueBackup);
        stringBuilder2.append(",launch_boost_disabled=");
        stringBuilder2.append(this.mDisableLaunchBoost);
        stringBuilder2.append(",optional_sensors_disabled=");
        stringBuilder2.append(this.mDisableOptionalSensors);
        stringBuilder2.append(",quick_doze_enabled=");
        stringBuilder2.append(this.mEnableQuickDoze);
        stringBuilder2.append(",soundtrigger_disabled=");
        stringBuilder2.append(this.mDisableSoundTrigger);
        stringBuilder2.append(",vibration_disabled=");
        stringBuilder2.append(this.mDisableVibration);
        stringBuilder2.append(",");
        stringBuilder2.append(stringBuilder.toString());
        return stringBuilder2.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeFloat(this.mAdjustBrightnessFactor);
        parcel.writeBoolean(this.mAdvertiseIsEnabled);
        parcel.writeBoolean(this.mDeferFullBackup);
        parcel.writeBoolean(this.mDeferKeyValueBackup);
        Object object = this.mDeviceSpecificSettings.entrySet();
        parcel.writeInt(object.size());
        object = object.iterator();
        while (object.hasNext()) {
            Map.Entry entry = (Map.Entry)object.next();
            parcel.writeString((String)entry.getKey());
            parcel.writeString((String)entry.getValue());
        }
        parcel.writeBoolean(this.mDisableAnimation);
        parcel.writeBoolean(this.mDisableAod);
        parcel.writeBoolean(this.mDisableLaunchBoost);
        parcel.writeBoolean(this.mDisableOptionalSensors);
        parcel.writeBoolean(this.mDisableSoundTrigger);
        parcel.writeBoolean(this.mDisableVibration);
        parcel.writeBoolean(this.mEnableAdjustBrightness);
        parcel.writeBoolean(this.mEnableDataSaver);
        parcel.writeBoolean(this.mEnableFirewall);
        parcel.writeBoolean(this.mEnableNightMode);
        parcel.writeBoolean(this.mEnableQuickDoze);
        parcel.writeBoolean(this.mForceAllAppsStandby);
        parcel.writeBoolean(this.mForceBackgroundCheck);
        parcel.writeInt(this.mLocationMode);
    }

    public static final class Builder {
        private float mAdjustBrightnessFactor = 1.0f;
        private boolean mAdvertiseIsEnabled = false;
        private boolean mDeferFullBackup = false;
        private boolean mDeferKeyValueBackup = false;
        private final ArrayMap<String, String> mDeviceSpecificSettings = new ArrayMap();
        private boolean mDisableAnimation = false;
        private boolean mDisableAod = false;
        private boolean mDisableLaunchBoost = false;
        private boolean mDisableOptionalSensors = false;
        private boolean mDisableSoundTrigger = false;
        private boolean mDisableVibration = false;
        private boolean mEnableAdjustBrightness = false;
        private boolean mEnableDataSaver = false;
        private boolean mEnableFirewall = false;
        private boolean mEnableNightMode = false;
        private boolean mEnableQuickDoze = false;
        private boolean mForceAllAppsStandby = false;
        private boolean mForceBackgroundCheck = false;
        private int mLocationMode = 0;

        public Builder addDeviceSpecificSetting(String string2, String string3) {
            if (string2 != null) {
                if (!TextUtils.isEmpty(string2 = string2.trim())) {
                    this.mDeviceSpecificSettings.put(string2, TextUtils.emptyIfNull(string3));
                    return this;
                }
                throw new IllegalArgumentException("Key cannot be empty");
            }
            throw new IllegalArgumentException("Key cannot be null");
        }

        public BatterySaverPolicyConfig build() {
            return new BatterySaverPolicyConfig(this);
        }

        public Builder setAdjustBrightnessFactor(float f) {
            this.mAdjustBrightnessFactor = f;
            return this;
        }

        public Builder setAdvertiseIsEnabled(boolean bl) {
            this.mAdvertiseIsEnabled = bl;
            return this;
        }

        public Builder setDeferFullBackup(boolean bl) {
            this.mDeferFullBackup = bl;
            return this;
        }

        public Builder setDeferKeyValueBackup(boolean bl) {
            this.mDeferKeyValueBackup = bl;
            return this;
        }

        public Builder setDisableAnimation(boolean bl) {
            this.mDisableAnimation = bl;
            return this;
        }

        public Builder setDisableAod(boolean bl) {
            this.mDisableAod = bl;
            return this;
        }

        public Builder setDisableLaunchBoost(boolean bl) {
            this.mDisableLaunchBoost = bl;
            return this;
        }

        public Builder setDisableOptionalSensors(boolean bl) {
            this.mDisableOptionalSensors = bl;
            return this;
        }

        public Builder setDisableSoundTrigger(boolean bl) {
            this.mDisableSoundTrigger = bl;
            return this;
        }

        public Builder setDisableVibration(boolean bl) {
            this.mDisableVibration = bl;
            return this;
        }

        public Builder setEnableAdjustBrightness(boolean bl) {
            this.mEnableAdjustBrightness = bl;
            return this;
        }

        public Builder setEnableDataSaver(boolean bl) {
            this.mEnableDataSaver = bl;
            return this;
        }

        public Builder setEnableFirewall(boolean bl) {
            this.mEnableFirewall = bl;
            return this;
        }

        public Builder setEnableNightMode(boolean bl) {
            this.mEnableNightMode = bl;
            return this;
        }

        public Builder setEnableQuickDoze(boolean bl) {
            this.mEnableQuickDoze = bl;
            return this;
        }

        public Builder setForceAllAppsStandby(boolean bl) {
            this.mForceAllAppsStandby = bl;
            return this;
        }

        public Builder setForceBackgroundCheck(boolean bl) {
            this.mForceBackgroundCheck = bl;
            return this;
        }

        public Builder setLocationMode(int n) {
            this.mLocationMode = n;
            return this;
        }
    }

}

