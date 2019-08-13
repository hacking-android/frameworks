/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;

public final class WifiActivityEnergyInfo
implements Parcelable {
    public static final Parcelable.Creator<WifiActivityEnergyInfo> CREATOR = new Parcelable.Creator<WifiActivityEnergyInfo>(){

        @Override
        public WifiActivityEnergyInfo createFromParcel(Parcel parcel) {
            return new WifiActivityEnergyInfo(parcel.readLong(), parcel.readInt(), parcel.readLong(), parcel.createLongArray(), parcel.readLong(), parcel.readLong(), parcel.readLong(), parcel.readLong());
        }

        public WifiActivityEnergyInfo[] newArray(int n) {
            return new WifiActivityEnergyInfo[n];
        }
    };
    public static final int STACK_STATE_INVALID = 0;
    public static final int STACK_STATE_STATE_ACTIVE = 1;
    public static final int STACK_STATE_STATE_IDLE = 3;
    public static final int STACK_STATE_STATE_SCANNING = 2;
    public long mControllerEnergyUsed;
    public long mControllerIdleTimeMs;
    public long mControllerRxTimeMs;
    public long mControllerScanTimeMs;
    public long mControllerTxTimeMs;
    public long[] mControllerTxTimePerLevelMs;
    public int mStackState;
    public long mTimestamp;

    public WifiActivityEnergyInfo(long l, int n, long l2, long[] arrl, long l3, long l4, long l5, long l6) {
        this.mTimestamp = l;
        this.mStackState = n;
        this.mControllerTxTimeMs = l2;
        this.mControllerTxTimePerLevelMs = arrl;
        this.mControllerRxTimeMs = l3;
        this.mControllerScanTimeMs = l4;
        this.mControllerIdleTimeMs = l5;
        this.mControllerEnergyUsed = l6;
    }

    @Override
    public int describeContents() {
        return 0;
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

    public long getControllerScanTimeMillis() {
        return this.mControllerScanTimeMs;
    }

    public long getControllerTxTimeMillis() {
        return this.mControllerTxTimeMs;
    }

    public long getControllerTxTimeMillisAtLevel(int n) {
        long[] arrl = this.mControllerTxTimePerLevelMs;
        if (n < arrl.length) {
            return arrl[n];
        }
        return 0L;
    }

    public int getStackState() {
        return this.mStackState;
    }

    public long getTimeStamp() {
        return this.mTimestamp;
    }

    public boolean isValid() {
        boolean bl = this.mControllerTxTimeMs >= 0L && this.mControllerRxTimeMs >= 0L && this.mControllerScanTimeMs >= 0L && this.mControllerIdleTimeMs >= 0L;
        return bl;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("WifiActivityEnergyInfo{ timestamp=");
        stringBuilder.append(this.mTimestamp);
        stringBuilder.append(" mStackState=");
        stringBuilder.append(this.mStackState);
        stringBuilder.append(" mControllerTxTimeMs=");
        stringBuilder.append(this.mControllerTxTimeMs);
        stringBuilder.append(" mControllerTxTimePerLevelMs=");
        stringBuilder.append(Arrays.toString(this.mControllerTxTimePerLevelMs));
        stringBuilder.append(" mControllerRxTimeMs=");
        stringBuilder.append(this.mControllerRxTimeMs);
        stringBuilder.append(" mControllerScanTimeMs=");
        stringBuilder.append(this.mControllerScanTimeMs);
        stringBuilder.append(" mControllerIdleTimeMs=");
        stringBuilder.append(this.mControllerIdleTimeMs);
        stringBuilder.append(" mControllerEnergyUsed=");
        stringBuilder.append(this.mControllerEnergyUsed);
        stringBuilder.append(" }");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeLong(this.mTimestamp);
        parcel.writeInt(this.mStackState);
        parcel.writeLong(this.mControllerTxTimeMs);
        parcel.writeLongArray(this.mControllerTxTimePerLevelMs);
        parcel.writeLong(this.mControllerRxTimeMs);
        parcel.writeLong(this.mControllerScanTimeMs);
        parcel.writeLong(this.mControllerIdleTimeMs);
        parcel.writeLong(this.mControllerEnergyUsed);
    }

}

