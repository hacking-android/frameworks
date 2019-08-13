/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.EventLog;

public final class BluetoothHidDeviceAppSdpSettings
implements Parcelable {
    public static final Parcelable.Creator<BluetoothHidDeviceAppSdpSettings> CREATOR = new Parcelable.Creator<BluetoothHidDeviceAppSdpSettings>(){

        @Override
        public BluetoothHidDeviceAppSdpSettings createFromParcel(Parcel parcel) {
            return new BluetoothHidDeviceAppSdpSettings(parcel.readString(), parcel.readString(), parcel.readString(), parcel.readByte(), parcel.createByteArray());
        }

        public BluetoothHidDeviceAppSdpSettings[] newArray(int n) {
            return new BluetoothHidDeviceAppSdpSettings[n];
        }
    };
    private static final int MAX_DESCRIPTOR_SIZE = 2048;
    private final String mDescription;
    private final byte[] mDescriptors;
    private final String mName;
    private final String mProvider;
    private final byte mSubclass;

    public BluetoothHidDeviceAppSdpSettings(String string2, String string3, String string4, byte by, byte[] arrby) {
        this.mName = string2;
        this.mDescription = string3;
        this.mProvider = string4;
        this.mSubclass = by;
        if (arrby != null && arrby.length <= 2048) {
            this.mDescriptors = (byte[])arrby.clone();
            return;
        }
        EventLog.writeEvent(1397638484, "119819889", -1, "");
        throw new IllegalArgumentException("descriptors must be not null and shorter than 2048");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getDescription() {
        return this.mDescription;
    }

    public byte[] getDescriptors() {
        return this.mDescriptors;
    }

    public String getName() {
        return this.mName;
    }

    public String getProvider() {
        return this.mProvider;
    }

    public byte getSubclass() {
        return this.mSubclass;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mName);
        parcel.writeString(this.mDescription);
        parcel.writeString(this.mProvider);
        parcel.writeByte(this.mSubclass);
        parcel.writeByteArray(this.mDescriptors);
    }

}

