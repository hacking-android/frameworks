/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth;

import android.os.Parcel;
import android.os.Parcelable;

@Deprecated
public final class BluetoothHealthAppConfiguration
implements Parcelable {
    @Deprecated
    public static final Parcelable.Creator<BluetoothHealthAppConfiguration> CREATOR = new Parcelable.Creator<BluetoothHealthAppConfiguration>(){

        @Override
        public BluetoothHealthAppConfiguration createFromParcel(Parcel parcel) {
            return new BluetoothHealthAppConfiguration();
        }

        public BluetoothHealthAppConfiguration[] newArray(int n) {
            return new BluetoothHealthAppConfiguration[n];
        }
    };

    BluetoothHealthAppConfiguration() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Deprecated
    public int getDataType() {
        return 0;
    }

    @Deprecated
    public String getName() {
        return null;
    }

    @Deprecated
    public int getRole() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
    }

}

