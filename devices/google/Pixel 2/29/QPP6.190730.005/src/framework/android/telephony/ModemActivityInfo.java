/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;

public class ModemActivityInfo
implements Parcelable {
    public static final Parcelable.Creator<ModemActivityInfo> CREATOR = new Parcelable.Creator<ModemActivityInfo>(){

        @Override
        public ModemActivityInfo createFromParcel(Parcel parcel) {
            long l = parcel.readLong();
            int n = parcel.readInt();
            int n2 = parcel.readInt();
            int[] arrn = new int[5];
            for (int i = 0; i < 5; ++i) {
                arrn[i] = parcel.readInt();
            }
            return new ModemActivityInfo(l, n, n2, arrn, parcel.readInt(), parcel.readInt());
        }

        public ModemActivityInfo[] newArray(int n) {
            return new ModemActivityInfo[n];
        }
    };
    public static final int TX_POWER_LEVELS = 5;
    private int mEnergyUsed;
    private int mIdleTimeMs;
    private int mRxTimeMs;
    private int mSleepTimeMs;
    private long mTimestamp;
    private int[] mTxTimeMs = new int[5];

    public ModemActivityInfo(long l, int n, int n2, int[] arrn, int n3, int n4) {
        this.mTimestamp = l;
        this.mSleepTimeMs = n;
        this.mIdleTimeMs = n2;
        if (arrn != null) {
            System.arraycopy(arrn, 0, this.mTxTimeMs, 0, Math.min(arrn.length, 5));
        }
        this.mRxTimeMs = n3;
        this.mEnergyUsed = n4;
    }

    private boolean isEmpty() {
        boolean bl;
        block1 : {
            int[] arrn = this.getTxTimeMillis();
            int n = arrn.length;
            bl = false;
            for (int i = 0; i < n; ++i) {
                if (arrn[i] == 0) continue;
                return false;
            }
            if (this.getIdleTimeMillis() != 0 || this.getSleepTimeMillis() != 0 || this.getRxTimeMillis() != 0 || this.getEnergyUsed() != 0) break block1;
            bl = true;
        }
        return bl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getEnergyUsed() {
        return this.mEnergyUsed;
    }

    public int getIdleTimeMillis() {
        return this.mIdleTimeMs;
    }

    public int getRxTimeMillis() {
        return this.mRxTimeMs;
    }

    public int getSleepTimeMillis() {
        return this.mSleepTimeMs;
    }

    public long getTimestamp() {
        return this.mTimestamp;
    }

    public int[] getTxTimeMillis() {
        return this.mTxTimeMs;
    }

    public boolean isValid() {
        boolean bl;
        block1 : {
            int[] arrn = this.getTxTimeMillis();
            int n = arrn.length;
            bl = false;
            for (int i = 0; i < n; ++i) {
                if (arrn[i] >= 0) continue;
                return false;
            }
            if (this.getIdleTimeMillis() < 0 || this.getSleepTimeMillis() < 0 || this.getRxTimeMillis() < 0 || this.getEnergyUsed() < 0 || this.isEmpty()) break block1;
            bl = true;
        }
        return bl;
    }

    public void setEnergyUsed(int n) {
        this.mEnergyUsed = n;
    }

    public void setIdleTimeMillis(int n) {
        this.mIdleTimeMs = n;
    }

    public void setRxTimeMillis(int n) {
        this.mRxTimeMs = n;
    }

    public void setSleepTimeMillis(int n) {
        this.mSleepTimeMs = n;
    }

    public void setTimestamp(long l) {
        this.mTimestamp = l;
    }

    public void setTxTimeMillis(int[] arrn) {
        this.mTxTimeMs = arrn;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ModemActivityInfo{ mTimestamp=");
        stringBuilder.append(this.mTimestamp);
        stringBuilder.append(" mSleepTimeMs=");
        stringBuilder.append(this.mSleepTimeMs);
        stringBuilder.append(" mIdleTimeMs=");
        stringBuilder.append(this.mIdleTimeMs);
        stringBuilder.append(" mTxTimeMs[]=");
        stringBuilder.append(Arrays.toString(this.mTxTimeMs));
        stringBuilder.append(" mRxTimeMs=");
        stringBuilder.append(this.mRxTimeMs);
        stringBuilder.append(" mEnergyUsed=");
        stringBuilder.append(this.mEnergyUsed);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeLong(this.mTimestamp);
        parcel.writeInt(this.mSleepTimeMs);
        parcel.writeInt(this.mIdleTimeMs);
        for (n = 0; n < 5; ++n) {
            parcel.writeInt(this.mTxTimeMs[n]);
        }
        parcel.writeInt(this.mRxTimeMs);
        parcel.writeInt(this.mEnergyUsed);
    }

}

