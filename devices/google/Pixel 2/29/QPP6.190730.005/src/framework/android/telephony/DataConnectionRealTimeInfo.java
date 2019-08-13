/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.os.Parcel;
import android.os.Parcelable;

public class DataConnectionRealTimeInfo
implements Parcelable {
    public static final Parcelable.Creator<DataConnectionRealTimeInfo> CREATOR = new Parcelable.Creator<DataConnectionRealTimeInfo>(){

        @Override
        public DataConnectionRealTimeInfo createFromParcel(Parcel parcel) {
            return new DataConnectionRealTimeInfo(parcel);
        }

        public DataConnectionRealTimeInfo[] newArray(int n) {
            return new DataConnectionRealTimeInfo[n];
        }
    };
    public static final int DC_POWER_STATE_HIGH = 3;
    public static final int DC_POWER_STATE_LOW = 1;
    public static final int DC_POWER_STATE_MEDIUM = 2;
    public static final int DC_POWER_STATE_UNKNOWN = Integer.MAX_VALUE;
    private int mDcPowerState;
    private long mTime;

    public DataConnectionRealTimeInfo() {
        this.mTime = Long.MAX_VALUE;
        this.mDcPowerState = Integer.MAX_VALUE;
    }

    public DataConnectionRealTimeInfo(long l, int n) {
        this.mTime = l;
        this.mDcPowerState = n;
    }

    private DataConnectionRealTimeInfo(Parcel parcel) {
        this.mTime = parcel.readLong();
        this.mDcPowerState = parcel.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (this.getClass() != object.getClass()) {
            return false;
        }
        object = (DataConnectionRealTimeInfo)object;
        if (this.mTime != ((DataConnectionRealTimeInfo)object).mTime || this.mDcPowerState != ((DataConnectionRealTimeInfo)object).mDcPowerState) {
            bl = false;
        }
        return bl;
    }

    public int getDcPowerState() {
        return this.mDcPowerState;
    }

    public long getTime() {
        return this.mTime;
    }

    public int hashCode() {
        long l = 1L * 17L + this.mTime;
        return (int)(l + (17L * l + (long)this.mDcPowerState));
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("mTime=");
        stringBuffer.append(this.mTime);
        stringBuffer.append(" mDcPowerState=");
        stringBuffer.append(this.mDcPowerState);
        return stringBuffer.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeLong(this.mTime);
        parcel.writeInt(this.mDcPowerState);
    }

}

