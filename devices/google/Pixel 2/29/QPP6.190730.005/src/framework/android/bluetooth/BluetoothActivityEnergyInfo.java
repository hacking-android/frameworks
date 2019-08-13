/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth;

import android.bluetooth.UidTraffic;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;

public final class BluetoothActivityEnergyInfo
implements Parcelable {
    public static final int BT_STACK_STATE_INVALID = 0;
    public static final int BT_STACK_STATE_STATE_ACTIVE = 1;
    public static final int BT_STACK_STATE_STATE_IDLE = 3;
    public static final int BT_STACK_STATE_STATE_SCANNING = 2;
    public static final Parcelable.Creator<BluetoothActivityEnergyInfo> CREATOR = new Parcelable.Creator<BluetoothActivityEnergyInfo>(){

        @Override
        public BluetoothActivityEnergyInfo createFromParcel(Parcel parcel) {
            return new BluetoothActivityEnergyInfo(parcel);
        }

        public BluetoothActivityEnergyInfo[] newArray(int n) {
            return new BluetoothActivityEnergyInfo[n];
        }
    };
    private int mBluetoothStackState;
    private long mControllerEnergyUsed;
    private long mControllerIdleTimeMs;
    private long mControllerRxTimeMs;
    private long mControllerTxTimeMs;
    private final long mTimestamp;
    private UidTraffic[] mUidTraffic;

    public BluetoothActivityEnergyInfo(long l, int n, long l2, long l3, long l4, long l5) {
        this.mTimestamp = l;
        this.mBluetoothStackState = n;
        this.mControllerTxTimeMs = l2;
        this.mControllerRxTimeMs = l3;
        this.mControllerIdleTimeMs = l4;
        this.mControllerEnergyUsed = l5;
    }

    BluetoothActivityEnergyInfo(Parcel parcel) {
        this.mTimestamp = parcel.readLong();
        this.mBluetoothStackState = parcel.readInt();
        this.mControllerTxTimeMs = parcel.readLong();
        this.mControllerRxTimeMs = parcel.readLong();
        this.mControllerIdleTimeMs = parcel.readLong();
        this.mControllerEnergyUsed = parcel.readLong();
        this.mUidTraffic = parcel.createTypedArray(UidTraffic.CREATOR);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getBluetoothStackState() {
        return this.mBluetoothStackState;
    }

    public long getControllerEnergyUsed() {
        return this.mControllerEnergyUsed;
    }

    public long getControllerIdleTimeMillis() {
        return this.mControllerIdleTimeMs;
    }

    public long getControllerRxTimeMillis() {
        return this.mControllerRxTimeMs;
    }

    public long getControllerTxTimeMillis() {
        return this.mControllerTxTimeMs;
    }

    public long getTimeStamp() {
        return this.mTimestamp;
    }

    public UidTraffic[] getUidTraffic() {
        return this.mUidTraffic;
    }

    public boolean isValid() {
        boolean bl = this.mControllerTxTimeMs >= 0L && this.mControllerRxTimeMs >= 0L && this.mControllerIdleTimeMs >= 0L;
        return bl;
    }

    public void setUidTraffic(UidTraffic[] arruidTraffic) {
        this.mUidTraffic = arruidTraffic;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("BluetoothActivityEnergyInfo{ mTimestamp=");
        stringBuilder.append(this.mTimestamp);
        stringBuilder.append(" mBluetoothStackState=");
        stringBuilder.append(this.mBluetoothStackState);
        stringBuilder.append(" mControllerTxTimeMs=");
        stringBuilder.append(this.mControllerTxTimeMs);
        stringBuilder.append(" mControllerRxTimeMs=");
        stringBuilder.append(this.mControllerRxTimeMs);
        stringBuilder.append(" mControllerIdleTimeMs=");
        stringBuilder.append(this.mControllerIdleTimeMs);
        stringBuilder.append(" mControllerEnergyUsed=");
        stringBuilder.append(this.mControllerEnergyUsed);
        stringBuilder.append(" mUidTraffic=");
        stringBuilder.append(Arrays.toString(this.mUidTraffic));
        stringBuilder.append(" }");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeLong(this.mTimestamp);
        parcel.writeInt(this.mBluetoothStackState);
        parcel.writeLong(this.mControllerTxTimeMs);
        parcel.writeLong(this.mControllerRxTimeMs);
        parcel.writeLong(this.mControllerIdleTimeMs);
        parcel.writeLong(this.mControllerEnergyUsed);
        parcel.writeTypedArray((Parcelable[])this.mUidTraffic, n);
    }

}

