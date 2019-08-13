/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth;

import android.os.Parcel;
import android.os.ParcelUuid;
import android.os.Parcelable;
import java.util.UUID;

public class BluetoothGattIncludedService
implements Parcelable {
    public static final Parcelable.Creator<BluetoothGattIncludedService> CREATOR = new Parcelable.Creator<BluetoothGattIncludedService>(){

        @Override
        public BluetoothGattIncludedService createFromParcel(Parcel parcel) {
            return new BluetoothGattIncludedService(parcel);
        }

        public BluetoothGattIncludedService[] newArray(int n) {
            return new BluetoothGattIncludedService[n];
        }
    };
    protected int mInstanceId;
    protected int mServiceType;
    protected UUID mUuid;

    private BluetoothGattIncludedService(Parcel parcel) {
        this.mUuid = ((ParcelUuid)parcel.readParcelable(null)).getUuid();
        this.mInstanceId = parcel.readInt();
        this.mServiceType = parcel.readInt();
    }

    public BluetoothGattIncludedService(UUID uUID, int n, int n2) {
        this.mUuid = uUID;
        this.mInstanceId = n;
        this.mServiceType = n2;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getInstanceId() {
        return this.mInstanceId;
    }

    public int getType() {
        return this.mServiceType;
    }

    public UUID getUuid() {
        return this.mUuid;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeParcelable(new ParcelUuid(this.mUuid), 0);
        parcel.writeInt(this.mInstanceId);
        parcel.writeInt(this.mServiceType);
    }

}

