/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.display;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

@SystemApi
public final class BrightnessChangeEvent
implements Parcelable {
    public static final Parcelable.Creator<BrightnessChangeEvent> CREATOR = new Parcelable.Creator<BrightnessChangeEvent>(){

        @Override
        public BrightnessChangeEvent createFromParcel(Parcel parcel) {
            return new BrightnessChangeEvent(parcel);
        }

        public BrightnessChangeEvent[] newArray(int n) {
            return new BrightnessChangeEvent[n];
        }
    };
    public final float batteryLevel;
    public final float brightness;
    public final long colorSampleDuration;
    public final int colorTemperature;
    public final long[] colorValueBuckets;
    public final boolean isDefaultBrightnessConfig;
    public final boolean isUserSetBrightness;
    public final float lastBrightness;
    public final long[] luxTimestamps;
    public final float[] luxValues;
    public final boolean nightMode;
    public final String packageName;
    public final float powerBrightnessFactor;
    public final long timeStamp;
    public final int userId;

    private BrightnessChangeEvent(float f, long l, String string2, int n, float[] arrf, long[] arrl, float f2, float f3, boolean bl, int n2, float f4, boolean bl2, boolean bl3, long[] arrl2, long l2) {
        this.brightness = f;
        this.timeStamp = l;
        this.packageName = string2;
        this.userId = n;
        this.luxValues = arrf;
        this.luxTimestamps = arrl;
        this.batteryLevel = f2;
        this.powerBrightnessFactor = f3;
        this.nightMode = bl;
        this.colorTemperature = n2;
        this.lastBrightness = f4;
        this.isDefaultBrightnessConfig = bl2;
        this.isUserSetBrightness = bl3;
        this.colorValueBuckets = arrl2;
        this.colorSampleDuration = l2;
    }

    public BrightnessChangeEvent(BrightnessChangeEvent brightnessChangeEvent, boolean bl) {
        this.brightness = brightnessChangeEvent.brightness;
        this.timeStamp = brightnessChangeEvent.timeStamp;
        String string2 = bl ? null : brightnessChangeEvent.packageName;
        this.packageName = string2;
        this.userId = brightnessChangeEvent.userId;
        this.luxValues = brightnessChangeEvent.luxValues;
        this.luxTimestamps = brightnessChangeEvent.luxTimestamps;
        this.batteryLevel = brightnessChangeEvent.batteryLevel;
        this.powerBrightnessFactor = brightnessChangeEvent.powerBrightnessFactor;
        this.nightMode = brightnessChangeEvent.nightMode;
        this.colorTemperature = brightnessChangeEvent.colorTemperature;
        this.lastBrightness = brightnessChangeEvent.lastBrightness;
        this.isDefaultBrightnessConfig = brightnessChangeEvent.isDefaultBrightnessConfig;
        this.isUserSetBrightness = brightnessChangeEvent.isUserSetBrightness;
        this.colorValueBuckets = brightnessChangeEvent.colorValueBuckets;
        this.colorSampleDuration = brightnessChangeEvent.colorSampleDuration;
    }

    private BrightnessChangeEvent(Parcel parcel) {
        this.brightness = parcel.readFloat();
        this.timeStamp = parcel.readLong();
        this.packageName = parcel.readString();
        this.userId = parcel.readInt();
        this.luxValues = parcel.createFloatArray();
        this.luxTimestamps = parcel.createLongArray();
        this.batteryLevel = parcel.readFloat();
        this.powerBrightnessFactor = parcel.readFloat();
        this.nightMode = parcel.readBoolean();
        this.colorTemperature = parcel.readInt();
        this.lastBrightness = parcel.readFloat();
        this.isDefaultBrightnessConfig = parcel.readBoolean();
        this.isUserSetBrightness = parcel.readBoolean();
        this.colorValueBuckets = parcel.createLongArray();
        this.colorSampleDuration = parcel.readLong();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeFloat(this.brightness);
        parcel.writeLong(this.timeStamp);
        parcel.writeString(this.packageName);
        parcel.writeInt(this.userId);
        parcel.writeFloatArray(this.luxValues);
        parcel.writeLongArray(this.luxTimestamps);
        parcel.writeFloat(this.batteryLevel);
        parcel.writeFloat(this.powerBrightnessFactor);
        parcel.writeBoolean(this.nightMode);
        parcel.writeInt(this.colorTemperature);
        parcel.writeFloat(this.lastBrightness);
        parcel.writeBoolean(this.isDefaultBrightnessConfig);
        parcel.writeBoolean(this.isUserSetBrightness);
        parcel.writeLongArray(this.colorValueBuckets);
        parcel.writeLong(this.colorSampleDuration);
    }

    public static class Builder {
        private float mBatteryLevel;
        private float mBrightness;
        private long mColorSampleDuration;
        private int mColorTemperature;
        private long[] mColorValueBuckets;
        private boolean mIsDefaultBrightnessConfig;
        private boolean mIsUserSetBrightness;
        private float mLastBrightness;
        private long[] mLuxTimestamps;
        private float[] mLuxValues;
        private boolean mNightMode;
        private String mPackageName;
        private float mPowerBrightnessFactor;
        private long mTimeStamp;
        private int mUserId;

        public BrightnessChangeEvent build() {
            return new BrightnessChangeEvent(this.mBrightness, this.mTimeStamp, this.mPackageName, this.mUserId, this.mLuxValues, this.mLuxTimestamps, this.mBatteryLevel, this.mPowerBrightnessFactor, this.mNightMode, this.mColorTemperature, this.mLastBrightness, this.mIsDefaultBrightnessConfig, this.mIsUserSetBrightness, this.mColorValueBuckets, this.mColorSampleDuration);
        }

        public Builder setBatteryLevel(float f) {
            this.mBatteryLevel = f;
            return this;
        }

        public Builder setBrightness(float f) {
            this.mBrightness = f;
            return this;
        }

        public Builder setColorTemperature(int n) {
            this.mColorTemperature = n;
            return this;
        }

        public Builder setColorValues(long[] arrl, long l) {
            Objects.requireNonNull(arrl);
            this.mColorValueBuckets = arrl;
            this.mColorSampleDuration = l;
            return this;
        }

        public Builder setIsDefaultBrightnessConfig(boolean bl) {
            this.mIsDefaultBrightnessConfig = bl;
            return this;
        }

        public Builder setLastBrightness(float f) {
            this.mLastBrightness = f;
            return this;
        }

        public Builder setLuxTimestamps(long[] arrl) {
            this.mLuxTimestamps = arrl;
            return this;
        }

        public Builder setLuxValues(float[] arrf) {
            this.mLuxValues = arrf;
            return this;
        }

        public Builder setNightMode(boolean bl) {
            this.mNightMode = bl;
            return this;
        }

        public Builder setPackageName(String string2) {
            this.mPackageName = string2;
            return this;
        }

        public Builder setPowerBrightnessFactor(float f) {
            this.mPowerBrightnessFactor = f;
            return this;
        }

        public Builder setTimeStamp(long l) {
            this.mTimeStamp = l;
            return this;
        }

        public Builder setUserBrightnessPoint(boolean bl) {
            this.mIsUserSetBrightness = bl;
            return this;
        }

        public Builder setUserId(int n) {
            this.mUserId = n;
            return this;
        }
    }

}

