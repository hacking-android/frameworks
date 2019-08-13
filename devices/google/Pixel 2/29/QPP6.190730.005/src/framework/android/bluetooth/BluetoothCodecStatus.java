/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth;

import android.annotation.UnsupportedAppUsage;
import android.bluetooth.BluetoothCodecConfig;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

public final class BluetoothCodecStatus
implements Parcelable {
    public static final Parcelable.Creator<BluetoothCodecStatus> CREATOR = new Parcelable.Creator<BluetoothCodecStatus>(){

        @Override
        public BluetoothCodecStatus createFromParcel(Parcel parcel) {
            return new BluetoothCodecStatus(parcel.readTypedObject(BluetoothCodecConfig.CREATOR), parcel.createTypedArray(BluetoothCodecConfig.CREATOR), parcel.createTypedArray(BluetoothCodecConfig.CREATOR));
        }

        public BluetoothCodecStatus[] newArray(int n) {
            return new BluetoothCodecStatus[n];
        }
    };
    @UnsupportedAppUsage
    public static final String EXTRA_CODEC_STATUS = "android.bluetooth.codec.extra.CODEC_STATUS";
    private final BluetoothCodecConfig mCodecConfig;
    private final BluetoothCodecConfig[] mCodecsLocalCapabilities;
    private final BluetoothCodecConfig[] mCodecsSelectableCapabilities;

    public BluetoothCodecStatus(BluetoothCodecConfig bluetoothCodecConfig, BluetoothCodecConfig[] arrbluetoothCodecConfig, BluetoothCodecConfig[] arrbluetoothCodecConfig2) {
        this.mCodecConfig = bluetoothCodecConfig;
        this.mCodecsLocalCapabilities = arrbluetoothCodecConfig;
        this.mCodecsSelectableCapabilities = arrbluetoothCodecConfig2;
    }

    public static boolean sameCapabilities(BluetoothCodecConfig[] arrbluetoothCodecConfig, BluetoothCodecConfig[] arrbluetoothCodecConfig2) {
        boolean bl = false;
        if (arrbluetoothCodecConfig == null) {
            if (arrbluetoothCodecConfig2 == null) {
                bl = true;
            }
            return bl;
        }
        if (arrbluetoothCodecConfig2 == null) {
            return false;
        }
        if (arrbluetoothCodecConfig.length != arrbluetoothCodecConfig2.length) {
            return false;
        }
        return Arrays.asList(arrbluetoothCodecConfig).containsAll(Arrays.asList(arrbluetoothCodecConfig2));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof BluetoothCodecStatus;
        boolean bl2 = false;
        if (bl) {
            object = (BluetoothCodecStatus)object;
            if (Objects.equals(((BluetoothCodecStatus)object).mCodecConfig, this.mCodecConfig) && BluetoothCodecStatus.sameCapabilities(((BluetoothCodecStatus)object).mCodecsLocalCapabilities, this.mCodecsLocalCapabilities) && BluetoothCodecStatus.sameCapabilities(((BluetoothCodecStatus)object).mCodecsSelectableCapabilities, this.mCodecsSelectableCapabilities)) {
                bl2 = true;
            }
            return bl2;
        }
        return false;
    }

    @UnsupportedAppUsage
    public BluetoothCodecConfig getCodecConfig() {
        return this.mCodecConfig;
    }

    @UnsupportedAppUsage
    public BluetoothCodecConfig[] getCodecsLocalCapabilities() {
        return this.mCodecsLocalCapabilities;
    }

    @UnsupportedAppUsage
    public BluetoothCodecConfig[] getCodecsSelectableCapabilities() {
        return this.mCodecsSelectableCapabilities;
    }

    public int hashCode() {
        BluetoothCodecConfig bluetoothCodecConfig = this.mCodecConfig;
        BluetoothCodecConfig[] arrbluetoothCodecConfig = this.mCodecsLocalCapabilities;
        return Objects.hash(bluetoothCodecConfig, arrbluetoothCodecConfig, arrbluetoothCodecConfig);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{mCodecConfig:");
        stringBuilder.append(this.mCodecConfig);
        stringBuilder.append(",mCodecsLocalCapabilities:");
        stringBuilder.append(Arrays.toString(this.mCodecsLocalCapabilities));
        stringBuilder.append(",mCodecsSelectableCapabilities:");
        stringBuilder.append(Arrays.toString(this.mCodecsSelectableCapabilities));
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeTypedObject(this.mCodecConfig, 0);
        parcel.writeTypedArray((Parcelable[])this.mCodecsLocalCapabilities, 0);
        parcel.writeTypedArray((Parcelable[])this.mCodecsSelectableCapabilities, 0);
    }

}

