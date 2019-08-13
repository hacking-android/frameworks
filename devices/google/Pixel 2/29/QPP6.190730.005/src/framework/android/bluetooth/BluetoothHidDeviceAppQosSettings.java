/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth;

import android.os.Parcel;
import android.os.Parcelable;

public final class BluetoothHidDeviceAppQosSettings
implements Parcelable {
    public static final Parcelable.Creator<BluetoothHidDeviceAppQosSettings> CREATOR = new Parcelable.Creator<BluetoothHidDeviceAppQosSettings>(){

        @Override
        public BluetoothHidDeviceAppQosSettings createFromParcel(Parcel parcel) {
            return new BluetoothHidDeviceAppQosSettings(parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt());
        }

        public BluetoothHidDeviceAppQosSettings[] newArray(int n) {
            return new BluetoothHidDeviceAppQosSettings[n];
        }
    };
    public static final int MAX = -1;
    public static final int SERVICE_BEST_EFFORT = 1;
    public static final int SERVICE_GUARANTEED = 2;
    public static final int SERVICE_NO_TRAFFIC = 0;
    private final int mDelayVariation;
    private final int mLatency;
    private final int mPeakBandwidth;
    private final int mServiceType;
    private final int mTokenBucketSize;
    private final int mTokenRate;

    public BluetoothHidDeviceAppQosSettings(int n, int n2, int n3, int n4, int n5, int n6) {
        this.mServiceType = n;
        this.mTokenRate = n2;
        this.mTokenBucketSize = n3;
        this.mPeakBandwidth = n4;
        this.mLatency = n5;
        this.mDelayVariation = n6;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getDelayVariation() {
        return this.mDelayVariation;
    }

    public int getLatency() {
        return this.mLatency;
    }

    public int getPeakBandwidth() {
        return this.mPeakBandwidth;
    }

    public int getServiceType() {
        return this.mServiceType;
    }

    public int getTokenBucketSize() {
        return this.mTokenBucketSize;
    }

    public int getTokenRate() {
        return this.mTokenRate;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mServiceType);
        parcel.writeInt(this.mTokenRate);
        parcel.writeInt(this.mTokenBucketSize);
        parcel.writeInt(this.mPeakBandwidth);
        parcel.writeInt(this.mLatency);
        parcel.writeInt(this.mDelayVariation);
    }

}

