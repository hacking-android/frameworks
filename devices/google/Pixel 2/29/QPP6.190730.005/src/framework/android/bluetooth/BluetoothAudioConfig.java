/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth;

import android.os.Parcel;
import android.os.Parcelable;

public final class BluetoothAudioConfig
implements Parcelable {
    public static final Parcelable.Creator<BluetoothAudioConfig> CREATOR = new Parcelable.Creator<BluetoothAudioConfig>(){

        @Override
        public BluetoothAudioConfig createFromParcel(Parcel parcel) {
            return new BluetoothAudioConfig(parcel.readInt(), parcel.readInt(), parcel.readInt());
        }

        public BluetoothAudioConfig[] newArray(int n) {
            return new BluetoothAudioConfig[n];
        }
    };
    private final int mAudioFormat;
    private final int mChannelConfig;
    private final int mSampleRate;

    public BluetoothAudioConfig(int n, int n2, int n3) {
        this.mSampleRate = n;
        this.mChannelConfig = n2;
        this.mAudioFormat = n3;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof BluetoothAudioConfig;
        boolean bl2 = false;
        if (bl) {
            object = (BluetoothAudioConfig)object;
            bl = bl2;
            if (((BluetoothAudioConfig)object).mSampleRate == this.mSampleRate) {
                bl = bl2;
                if (((BluetoothAudioConfig)object).mChannelConfig == this.mChannelConfig) {
                    bl = bl2;
                    if (((BluetoothAudioConfig)object).mAudioFormat == this.mAudioFormat) {
                        bl = true;
                    }
                }
            }
            return bl;
        }
        return false;
    }

    public int getAudioFormat() {
        return this.mAudioFormat;
    }

    public int getChannelConfig() {
        return this.mChannelConfig;
    }

    public int getSampleRate() {
        return this.mSampleRate;
    }

    public int hashCode() {
        return this.mSampleRate | this.mChannelConfig << 24 | this.mAudioFormat << 28;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{mSampleRate:");
        stringBuilder.append(this.mSampleRate);
        stringBuilder.append(",mChannelConfig:");
        stringBuilder.append(this.mChannelConfig);
        stringBuilder.append(",mAudioFormat:");
        stringBuilder.append(this.mAudioFormat);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mSampleRate);
        parcel.writeInt(this.mChannelConfig);
        parcel.writeInt(this.mAudioFormat);
    }

}

