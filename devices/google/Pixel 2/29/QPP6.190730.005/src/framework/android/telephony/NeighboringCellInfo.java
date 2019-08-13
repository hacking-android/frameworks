/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.CellIdentity;
import android.telephony.CellIdentityGsm;
import android.telephony.CellIdentityWcdma;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoWcdma;
import android.telephony.CellSignalStrength;
import android.telephony.CellSignalStrengthGsm;
import android.telephony.CellSignalStrengthWcdma;

@Deprecated
public class NeighboringCellInfo
implements Parcelable {
    public static final Parcelable.Creator<NeighboringCellInfo> CREATOR = new Parcelable.Creator<NeighboringCellInfo>(){

        @Override
        public NeighboringCellInfo createFromParcel(Parcel parcel) {
            return new NeighboringCellInfo(parcel);
        }

        public NeighboringCellInfo[] newArray(int n) {
            return new NeighboringCellInfo[n];
        }
    };
    public static final int UNKNOWN_CID = -1;
    public static final int UNKNOWN_RSSI = 99;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private int mCid;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private int mLac;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private int mNetworkType;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private int mPsc;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private int mRssi;

    @Deprecated
    public NeighboringCellInfo() {
        this.mRssi = 99;
        this.mLac = -1;
        this.mCid = -1;
        this.mPsc = -1;
        this.mNetworkType = 0;
    }

    @Deprecated
    public NeighboringCellInfo(int n, int n2) {
        this.mRssi = n;
        this.mCid = n2;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public NeighboringCellInfo(int var1_1, String var2_2, int var3_4) {
        super();
        this.mRssi = var1_1;
        this.mNetworkType = 0;
        this.mPsc = -1;
        this.mLac = -1;
        this.mCid = -1;
        var4_5 = var2_2.length();
        if (var4_5 > 8) {
            return;
        }
        var5_6 = var2_2;
        if (var4_5 < 8) {
            var1_1 = 0;
            do {
                var5_6 = var2_2;
                if (var1_1 >= 8 - var4_5) break;
                var5_6 = new StringBuilder();
                var5_6.append("0");
                var5_6.append(var2_2);
                var2_2 = var5_6.toString();
                ++var1_1;
            } while (true);
        }
        if (var3_4 == 1 || var3_4 == 2) ** GOTO lbl34
        if (var3_4 != 3) {
            switch (var3_4) {
                default: {
                    return;
                }
                case 8: 
                case 9: 
                case 10: 
            }
        }
        try {
            this.mNetworkType = var3_4;
            this.mPsc = Integer.parseInt((String)var5_6, 16);
            return;
lbl34: // 1 sources:
            this.mNetworkType = var3_4;
            if (var5_6.equalsIgnoreCase("FFFFFFFF") != false) return;
            this.mCid = Integer.parseInt(var5_6.substring(4), 16);
            this.mLac = Integer.parseInt(var5_6.substring(0, 4), 16);
            return;
        }
        catch (NumberFormatException var2_3) {
            this.mPsc = -1;
            this.mLac = -1;
            this.mCid = -1;
            this.mNetworkType = 0;
        }
    }

    public NeighboringCellInfo(Parcel parcel) {
        this.mRssi = parcel.readInt();
        this.mLac = parcel.readInt();
        this.mCid = parcel.readInt();
        this.mPsc = parcel.readInt();
        this.mNetworkType = parcel.readInt();
    }

    public NeighboringCellInfo(CellInfoGsm cellInfoGsm) {
        this.mNetworkType = 1;
        this.mRssi = ((CellSignalStrengthGsm)cellInfoGsm.getCellSignalStrength()).getAsuLevel();
        if (this.mRssi == Integer.MAX_VALUE) {
            this.mRssi = 99;
        }
        this.mLac = ((CellIdentityGsm)cellInfoGsm.getCellIdentity()).getLac();
        if (this.mLac == Integer.MAX_VALUE) {
            this.mLac = -1;
        }
        this.mCid = ((CellIdentityGsm)cellInfoGsm.getCellIdentity()).getCid();
        if (this.mCid == Integer.MAX_VALUE) {
            this.mCid = -1;
        }
        this.mPsc = -1;
    }

    public NeighboringCellInfo(CellInfoWcdma cellInfoWcdma) {
        this.mNetworkType = 3;
        this.mRssi = ((CellSignalStrengthWcdma)cellInfoWcdma.getCellSignalStrength()).getAsuLevel();
        if (this.mRssi == Integer.MAX_VALUE) {
            this.mRssi = 99;
        }
        this.mLac = ((CellIdentityWcdma)cellInfoWcdma.getCellIdentity()).getLac();
        if (this.mLac == Integer.MAX_VALUE) {
            this.mLac = -1;
        }
        this.mCid = ((CellIdentityWcdma)cellInfoWcdma.getCellIdentity()).getCid();
        if (this.mCid == Integer.MAX_VALUE) {
            this.mCid = -1;
        }
        this.mPsc = ((CellIdentityWcdma)cellInfoWcdma.getCellIdentity()).getPsc();
        if (this.mPsc == Integer.MAX_VALUE) {
            this.mPsc = -1;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getCid() {
        return this.mCid;
    }

    public int getLac() {
        return this.mLac;
    }

    public int getNetworkType() {
        return this.mNetworkType;
    }

    public int getPsc() {
        return this.mPsc;
    }

    public int getRssi() {
        return this.mRssi;
    }

    @Deprecated
    public void setCid(int n) {
        this.mCid = n;
    }

    @Deprecated
    public void setRssi(int n) {
        this.mRssi = n;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        int n = this.mPsc;
        Object object = "-";
        if (n != -1) {
            stringBuilder.append(Integer.toHexString(n));
            stringBuilder.append("@");
            n = this.mRssi;
            if (n != 99) {
                object = n;
            }
            stringBuilder.append(object);
        } else {
            n = this.mLac;
            if (n != -1 && this.mCid != -1) {
                stringBuilder.append(Integer.toHexString(n));
                stringBuilder.append(Integer.toHexString(this.mCid));
                stringBuilder.append("@");
                n = this.mRssi;
                if (n != 99) {
                    object = n;
                }
                stringBuilder.append(object);
            }
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mRssi);
        parcel.writeInt(this.mLac);
        parcel.writeInt(this.mCid);
        parcel.writeInt(this.mPsc);
        parcel.writeInt(this.mNetworkType);
    }

}

