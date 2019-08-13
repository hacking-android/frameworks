/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.annotation.UnsupportedAppUsage;
import android.hardware.radio.V1_0.GsmSignalStrength;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.telephony.CellSignalStrength;
import android.telephony.Rlog;
import android.telephony.ServiceState;
import java.util.Objects;

public final class CellSignalStrengthGsm
extends CellSignalStrength
implements Parcelable {
    public static final Parcelable.Creator<CellSignalStrengthGsm> CREATOR;
    private static final boolean DBG = false;
    private static final int GSM_RSSI_GOOD = -97;
    private static final int GSM_RSSI_GREAT = -89;
    private static final int GSM_RSSI_MAX = -51;
    private static final int GSM_RSSI_MIN = -113;
    private static final int GSM_RSSI_MODERATE = -103;
    private static final int GSM_RSSI_POOR = -107;
    private static final String LOG_TAG = "CellSignalStrengthGsm";
    private static final CellSignalStrengthGsm sInvalid;
    private static final int[] sRssiThresholds;
    @UnsupportedAppUsage
    private int mBitErrorRate;
    private int mLevel;
    private int mRssi;
    @UnsupportedAppUsage(maxTargetSdk=28)
    private int mTimingAdvance;

    static {
        sRssiThresholds = new int[]{-107, -103, -97, -89};
        sInvalid = new CellSignalStrengthGsm();
        CREATOR = new Parcelable.Creator<CellSignalStrengthGsm>(){

            @Override
            public CellSignalStrengthGsm createFromParcel(Parcel parcel) {
                return new CellSignalStrengthGsm(parcel);
            }

            public CellSignalStrengthGsm[] newArray(int n) {
                return new CellSignalStrengthGsm[n];
            }
        };
    }

    @UnsupportedAppUsage
    public CellSignalStrengthGsm() {
        this.setDefaultValues();
    }

    public CellSignalStrengthGsm(int n, int n2, int n3) {
        this.mRssi = CellSignalStrengthGsm.inRangeOrUnavailable(n, -113, -51);
        this.mBitErrorRate = CellSignalStrengthGsm.inRangeOrUnavailable(n2, 0, 7, 99);
        this.mTimingAdvance = CellSignalStrengthGsm.inRangeOrUnavailable(n3, 0, 219);
        this.updateLevel(null, null);
    }

    public CellSignalStrengthGsm(GsmSignalStrength gsmSignalStrength) {
        this(CellSignalStrengthGsm.getRssiDbmFromAsu(gsmSignalStrength.signalStrength), gsmSignalStrength.bitErrorRate, gsmSignalStrength.timingAdvance);
        if (this.mRssi == Integer.MAX_VALUE) {
            this.setDefaultValues();
        }
    }

    private CellSignalStrengthGsm(Parcel parcel) {
        this.mRssi = parcel.readInt();
        this.mBitErrorRate = parcel.readInt();
        this.mTimingAdvance = parcel.readInt();
        this.mLevel = parcel.readInt();
    }

    public CellSignalStrengthGsm(CellSignalStrengthGsm cellSignalStrengthGsm) {
        this.copyFrom(cellSignalStrengthGsm);
    }

    private static void log(String string2) {
        Rlog.w(LOG_TAG, string2);
    }

    @Override
    public CellSignalStrengthGsm copy() {
        return new CellSignalStrengthGsm(this);
    }

    protected void copyFrom(CellSignalStrengthGsm cellSignalStrengthGsm) {
        this.mRssi = cellSignalStrengthGsm.mRssi;
        this.mBitErrorRate = cellSignalStrengthGsm.mBitErrorRate;
        this.mTimingAdvance = cellSignalStrengthGsm.mTimingAdvance;
        this.mLevel = cellSignalStrengthGsm.mLevel;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public boolean equals(Object object) {
        boolean bl = object instanceof CellSignalStrengthGsm;
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        object = (CellSignalStrengthGsm)object;
        bl = bl2;
        if (this.mRssi == ((CellSignalStrengthGsm)object).mRssi) {
            bl = bl2;
            if (this.mBitErrorRate == ((CellSignalStrengthGsm)object).mBitErrorRate) {
                bl = bl2;
                if (this.mTimingAdvance == ((CellSignalStrengthGsm)object).mTimingAdvance) {
                    bl = bl2;
                    if (this.mLevel == ((CellSignalStrengthGsm)object).mLevel) {
                        bl = true;
                    }
                }
            }
        }
        return bl;
    }

    @Override
    public int getAsuLevel() {
        return CellSignalStrengthGsm.getAsuFromRssiDbm(this.mRssi);
    }

    public int getBitErrorRate() {
        return this.mBitErrorRate;
    }

    @Override
    public int getDbm() {
        return this.mRssi;
    }

    @Override
    public int getLevel() {
        return this.mLevel;
    }

    public int getRssi() {
        return this.mRssi;
    }

    public int getTimingAdvance() {
        return this.mTimingAdvance;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.mRssi, this.mBitErrorRate, this.mTimingAdvance);
    }

    @Override
    public boolean isValid() {
        return this.equals(sInvalid) ^ true;
    }

    @Override
    public void setDefaultValues() {
        this.mRssi = Integer.MAX_VALUE;
        this.mBitErrorRate = Integer.MAX_VALUE;
        this.mTimingAdvance = Integer.MAX_VALUE;
        this.mLevel = 0;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CellSignalStrengthGsm: rssi=");
        stringBuilder.append(this.mRssi);
        stringBuilder.append(" ber=");
        stringBuilder.append(this.mBitErrorRate);
        stringBuilder.append(" mTa=");
        stringBuilder.append(this.mTimingAdvance);
        stringBuilder.append(" mLevel=");
        stringBuilder.append(this.mLevel);
        return stringBuilder.toString();
    }

    @Override
    public void updateLevel(PersistableBundle arrn, ServiceState arrn2) {
        block6 : {
            block7 : {
                block5 : {
                    if (arrn != null) break block5;
                    arrn = sRssiThresholds;
                    break block6;
                }
                arrn2 = arrn.getIntArray("gsm_rssi_thresholds_int_array");
                if (arrn2 == null) break block7;
                arrn = arrn2;
                if (arrn2.length == 4) break block6;
            }
            arrn = sRssiThresholds;
        }
        int n = this.mRssi;
        if (n >= -113 && n <= -51) {
            int n2;
            for (n2 = 4; n2 > 0 && this.mRssi < arrn[n2 - 1]; --n2) {
            }
            this.mLevel = n2;
            return;
        }
        this.mLevel = 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mRssi);
        parcel.writeInt(this.mBitErrorRate);
        parcel.writeInt(this.mTimingAdvance);
        parcel.writeInt(this.mLevel);
    }

}

