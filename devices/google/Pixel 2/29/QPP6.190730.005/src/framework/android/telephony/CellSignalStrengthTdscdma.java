/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.hardware.radio.V1_0.TdScdmaSignalStrength;
import android.hardware.radio.V1_2.TdscdmaSignalStrength;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.telephony.CellSignalStrength;
import android.telephony.Rlog;
import android.telephony.ServiceState;
import java.util.Objects;

public final class CellSignalStrengthTdscdma
extends CellSignalStrength
implements Parcelable {
    public static final Parcelable.Creator<CellSignalStrengthTdscdma> CREATOR;
    private static final boolean DBG = false;
    private static final String LOG_TAG = "CellSignalStrengthTdscdma";
    private static final int TDSCDMA_RSCP_GOOD = -73;
    private static final int TDSCDMA_RSCP_GREAT = -49;
    private static final int TDSCDMA_RSCP_MAX = -24;
    private static final int TDSCDMA_RSCP_MIN = -120;
    private static final int TDSCDMA_RSCP_MODERATE = -97;
    private static final int TDSCDMA_RSCP_POOR = -110;
    private static final CellSignalStrengthTdscdma sInvalid;
    private int mBitErrorRate;
    private int mLevel;
    private int mRscp;
    private int mRssi;

    static {
        sInvalid = new CellSignalStrengthTdscdma();
        CREATOR = new Parcelable.Creator<CellSignalStrengthTdscdma>(){

            @Override
            public CellSignalStrengthTdscdma createFromParcel(Parcel parcel) {
                return new CellSignalStrengthTdscdma(parcel);
            }

            public CellSignalStrengthTdscdma[] newArray(int n) {
                return new CellSignalStrengthTdscdma[n];
            }
        };
    }

    public CellSignalStrengthTdscdma() {
        this.setDefaultValues();
    }

    public CellSignalStrengthTdscdma(int n, int n2, int n3) {
        this.mRssi = CellSignalStrengthTdscdma.inRangeOrUnavailable(n, -113, -51);
        this.mBitErrorRate = CellSignalStrengthTdscdma.inRangeOrUnavailable(n2, 0, 7, 99);
        this.mRscp = CellSignalStrengthTdscdma.inRangeOrUnavailable(n3, -120, -24);
        this.updateLevel(null, null);
    }

    public CellSignalStrengthTdscdma(TdScdmaSignalStrength tdScdmaSignalStrength) {
        int n = tdScdmaSignalStrength.rscp != Integer.MAX_VALUE ? -tdScdmaSignalStrength.rscp : tdScdmaSignalStrength.rscp;
        this(Integer.MAX_VALUE, Integer.MAX_VALUE, n);
        if (this.mRssi == Integer.MAX_VALUE && this.mRscp == Integer.MAX_VALUE) {
            this.setDefaultValues();
        }
    }

    public CellSignalStrengthTdscdma(TdscdmaSignalStrength tdscdmaSignalStrength) {
        this(CellSignalStrengthTdscdma.getRssiDbmFromAsu(tdscdmaSignalStrength.signalStrength), tdscdmaSignalStrength.bitErrorRate, CellSignalStrengthTdscdma.getRscpDbmFromAsu(tdscdmaSignalStrength.rscp));
        if (this.mRssi == Integer.MAX_VALUE && this.mRscp == Integer.MAX_VALUE) {
            this.setDefaultValues();
        }
    }

    private CellSignalStrengthTdscdma(Parcel parcel) {
        this.mRssi = parcel.readInt();
        this.mBitErrorRate = parcel.readInt();
        this.mRscp = parcel.readInt();
        this.mLevel = parcel.readInt();
    }

    public CellSignalStrengthTdscdma(CellSignalStrengthTdscdma cellSignalStrengthTdscdma) {
        this.copyFrom(cellSignalStrengthTdscdma);
    }

    private static void log(String string2) {
        Rlog.w(LOG_TAG, string2);
    }

    @Override
    public CellSignalStrengthTdscdma copy() {
        return new CellSignalStrengthTdscdma(this);
    }

    protected void copyFrom(CellSignalStrengthTdscdma cellSignalStrengthTdscdma) {
        this.mRssi = cellSignalStrengthTdscdma.mRssi;
        this.mBitErrorRate = cellSignalStrengthTdscdma.mBitErrorRate;
        this.mRscp = cellSignalStrengthTdscdma.mRscp;
        this.mLevel = cellSignalStrengthTdscdma.mLevel;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public boolean equals(Object object) {
        boolean bl = object instanceof CellSignalStrengthTdscdma;
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        object = (CellSignalStrengthTdscdma)object;
        bl = bl2;
        if (this.mRssi == ((CellSignalStrengthTdscdma)object).mRssi) {
            bl = bl2;
            if (this.mBitErrorRate == ((CellSignalStrengthTdscdma)object).mBitErrorRate) {
                bl = bl2;
                if (this.mRscp == ((CellSignalStrengthTdscdma)object).mRscp) {
                    bl = bl2;
                    if (this.mLevel == ((CellSignalStrengthTdscdma)object).mLevel) {
                        bl = true;
                    }
                }
            }
        }
        return bl;
    }

    @Override
    public int getAsuLevel() {
        int n = this.mRscp;
        if (n != Integer.MAX_VALUE) {
            return CellSignalStrengthTdscdma.getAsuFromRscpDbm(n);
        }
        n = this.mRssi;
        if (n != Integer.MAX_VALUE) {
            return CellSignalStrengthTdscdma.getAsuFromRssiDbm(n);
        }
        return CellSignalStrengthTdscdma.getAsuFromRscpDbm(Integer.MAX_VALUE);
    }

    public int getBitErrorRate() {
        return this.mBitErrorRate;
    }

    @Override
    public int getDbm() {
        return this.mRscp;
    }

    @Override
    public int getLevel() {
        return this.mLevel;
    }

    public int getRscp() {
        return this.mRscp;
    }

    public int getRssi() {
        return this.mRssi;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.mRssi, this.mBitErrorRate, this.mRscp, this.mLevel);
    }

    @Override
    public boolean isValid() {
        return this.equals(sInvalid) ^ true;
    }

    @Override
    public void setDefaultValues() {
        this.mRssi = Integer.MAX_VALUE;
        this.mBitErrorRate = Integer.MAX_VALUE;
        this.mRscp = Integer.MAX_VALUE;
        this.mLevel = 0;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CellSignalStrengthTdscdma: rssi=");
        stringBuilder.append(this.mRssi);
        stringBuilder.append(" ber=");
        stringBuilder.append(this.mBitErrorRate);
        stringBuilder.append(" rscp=");
        stringBuilder.append(this.mRscp);
        stringBuilder.append(" level=");
        stringBuilder.append(this.mLevel);
        return stringBuilder.toString();
    }

    @Override
    public void updateLevel(PersistableBundle persistableBundle, ServiceState serviceState) {
        int n = this.mRscp;
        this.mLevel = n > -24 ? 0 : (n >= -49 ? 4 : (n >= -73 ? 3 : (n >= -97 ? 2 : (n >= -110 ? 1 : 0))));
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mRssi);
        parcel.writeInt(this.mBitErrorRate);
        parcel.writeInt(this.mRscp);
        parcel.writeInt(this.mLevel);
    }

}

