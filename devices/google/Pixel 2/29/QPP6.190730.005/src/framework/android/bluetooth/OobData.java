/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth;

import android.os.Parcel;
import android.os.Parcelable;

public class OobData
implements Parcelable {
    public static final Parcelable.Creator<OobData> CREATOR = new Parcelable.Creator<OobData>(){

        @Override
        public OobData createFromParcel(Parcel parcel) {
            return new OobData(parcel);
        }

        public OobData[] newArray(int n) {
            return new OobData[n];
        }
    };
    private byte[] mLeBluetoothDeviceAddress;
    private byte[] mLeSecureConnectionsConfirmation;
    private byte[] mLeSecureConnectionsRandom;
    private byte[] mSecurityManagerTk;

    public OobData() {
    }

    private OobData(Parcel parcel) {
        this.mLeBluetoothDeviceAddress = parcel.createByteArray();
        this.mSecurityManagerTk = parcel.createByteArray();
        this.mLeSecureConnectionsConfirmation = parcel.createByteArray();
        this.mLeSecureConnectionsRandom = parcel.createByteArray();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public byte[] getLeBluetoothDeviceAddress() {
        return this.mLeBluetoothDeviceAddress;
    }

    public byte[] getLeSecureConnectionsConfirmation() {
        return this.mLeSecureConnectionsConfirmation;
    }

    public byte[] getLeSecureConnectionsRandom() {
        return this.mLeSecureConnectionsRandom;
    }

    public byte[] getSecurityManagerTk() {
        return this.mSecurityManagerTk;
    }

    public void setLeBluetoothDeviceAddress(byte[] arrby) {
        this.mLeBluetoothDeviceAddress = arrby;
    }

    public void setLeSecureConnectionsConfirmation(byte[] arrby) {
        this.mLeSecureConnectionsConfirmation = arrby;
    }

    public void setLeSecureConnectionsRandom(byte[] arrby) {
        this.mLeSecureConnectionsRandom = arrby;
    }

    public void setSecurityManagerTk(byte[] arrby) {
        this.mSecurityManagerTk = arrby;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeByteArray(this.mLeBluetoothDeviceAddress);
        parcel.writeByteArray(this.mSecurityManagerTk);
        parcel.writeByteArray(this.mLeSecureConnectionsConfirmation);
        parcel.writeByteArray(this.mLeSecureConnectionsRandom);
    }

}

