/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth;

import android.annotation.UnsupportedAppUsage;
import android.bluetooth.BluetoothGattCharacteristic;
import android.os.Parcel;
import android.os.ParcelUuid;
import android.os.Parcelable;
import java.util.UUID;

public class BluetoothGattDescriptor
implements Parcelable {
    public static final Parcelable.Creator<BluetoothGattDescriptor> CREATOR;
    public static final byte[] DISABLE_NOTIFICATION_VALUE;
    public static final byte[] ENABLE_INDICATION_VALUE;
    public static final byte[] ENABLE_NOTIFICATION_VALUE;
    public static final int PERMISSION_READ = 1;
    public static final int PERMISSION_READ_ENCRYPTED = 2;
    public static final int PERMISSION_READ_ENCRYPTED_MITM = 4;
    public static final int PERMISSION_WRITE = 16;
    public static final int PERMISSION_WRITE_ENCRYPTED = 32;
    public static final int PERMISSION_WRITE_ENCRYPTED_MITM = 64;
    public static final int PERMISSION_WRITE_SIGNED = 128;
    public static final int PERMISSION_WRITE_SIGNED_MITM = 256;
    @UnsupportedAppUsage
    protected BluetoothGattCharacteristic mCharacteristic;
    @UnsupportedAppUsage
    protected int mInstance;
    protected int mPermissions;
    protected UUID mUuid;
    protected byte[] mValue;

    static {
        ENABLE_NOTIFICATION_VALUE = new byte[]{1, 0};
        ENABLE_INDICATION_VALUE = new byte[]{2, 0};
        DISABLE_NOTIFICATION_VALUE = new byte[]{0, 0};
        CREATOR = new Parcelable.Creator<BluetoothGattDescriptor>(){

            @Override
            public BluetoothGattDescriptor createFromParcel(Parcel parcel) {
                return new BluetoothGattDescriptor(parcel);
            }

            public BluetoothGattDescriptor[] newArray(int n) {
                return new BluetoothGattDescriptor[n];
            }
        };
    }

    BluetoothGattDescriptor(BluetoothGattCharacteristic bluetoothGattCharacteristic, UUID uUID, int n, int n2) {
        this.initDescriptor(bluetoothGattCharacteristic, uUID, n, n2);
    }

    private BluetoothGattDescriptor(Parcel parcel) {
        this.mUuid = ((ParcelUuid)parcel.readParcelable(null)).getUuid();
        this.mInstance = parcel.readInt();
        this.mPermissions = parcel.readInt();
    }

    public BluetoothGattDescriptor(UUID uUID, int n) {
        this.initDescriptor(null, uUID, 0, n);
    }

    public BluetoothGattDescriptor(UUID uUID, int n, int n2) {
        this.initDescriptor(null, uUID, n, n2);
    }

    private void initDescriptor(BluetoothGattCharacteristic bluetoothGattCharacteristic, UUID uUID, int n, int n2) {
        this.mCharacteristic = bluetoothGattCharacteristic;
        this.mUuid = uUID;
        this.mInstance = n;
        this.mPermissions = n2;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public BluetoothGattCharacteristic getCharacteristic() {
        return this.mCharacteristic;
    }

    public int getInstanceId() {
        return this.mInstance;
    }

    public int getPermissions() {
        return this.mPermissions;
    }

    public UUID getUuid() {
        return this.mUuid;
    }

    public byte[] getValue() {
        return this.mValue;
    }

    @UnsupportedAppUsage
    void setCharacteristic(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        this.mCharacteristic = bluetoothGattCharacteristic;
    }

    public void setInstanceId(int n) {
        this.mInstance = n;
    }

    public boolean setValue(byte[] arrby) {
        this.mValue = arrby;
        return true;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeParcelable(new ParcelUuid(this.mUuid), 0);
        parcel.writeInt(this.mInstance);
        parcel.writeInt(this.mPermissions);
    }

}

