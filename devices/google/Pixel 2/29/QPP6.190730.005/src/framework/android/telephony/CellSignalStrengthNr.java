/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.hardware.radio.V1_4.NrSignalStrength;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.telephony.CellSignalStrength;
import android.telephony.ServiceState;
import java.util.Objects;

public final class CellSignalStrengthNr
extends CellSignalStrength
implements Parcelable {
    public static final Parcelable.Creator<CellSignalStrengthNr> CREATOR;
    private static final int SIGNAL_GOOD_THRESHOLD = -105;
    private static final int SIGNAL_GREAT_THRESHOLD = -95;
    private static final int SIGNAL_MODERATE_THRESHOLD = -115;
    private static final String TAG = "CellSignalStrengthNr";
    public static final int UNKNOWN_ASU_LEVEL = 99;
    private static final CellSignalStrengthNr sInvalid;
    private int mCsiRsrp;
    private int mCsiRsrq;
    private int mCsiSinr;
    private int mLevel;
    private int mSsRsrp;
    private int mSsRsrq;
    private int mSsSinr;

    static {
        sInvalid = new CellSignalStrengthNr();
        CREATOR = new Parcelable.Creator<CellSignalStrengthNr>(){

            @Override
            public CellSignalStrengthNr createFromParcel(Parcel parcel) {
                return new CellSignalStrengthNr(parcel);
            }

            public CellSignalStrengthNr[] newArray(int n) {
                return new CellSignalStrengthNr[n];
            }
        };
    }

    public CellSignalStrengthNr() {
        this.setDefaultValues();
    }

    public CellSignalStrengthNr(int n, int n2, int n3, int n4, int n5, int n6) {
        this.mCsiRsrp = CellSignalStrengthNr.inRangeOrUnavailable(n, -140, -44);
        this.mCsiRsrq = CellSignalStrengthNr.inRangeOrUnavailable(n2, -20, -3);
        this.mCsiSinr = CellSignalStrengthNr.inRangeOrUnavailable(n3, -23, 23);
        this.mSsRsrp = CellSignalStrengthNr.inRangeOrUnavailable(n4, -140, -44);
        this.mSsRsrq = CellSignalStrengthNr.inRangeOrUnavailable(n5, -20, -3);
        this.mSsSinr = CellSignalStrengthNr.inRangeOrUnavailable(n6, -23, 40);
        this.updateLevel(null, null);
    }

    public CellSignalStrengthNr(NrSignalStrength nrSignalStrength) {
        this(nrSignalStrength.csiRsrp, nrSignalStrength.csiRsrq, nrSignalStrength.csiSinr, nrSignalStrength.ssRsrp, nrSignalStrength.ssRsrq, nrSignalStrength.ssSinr);
    }

    private CellSignalStrengthNr(Parcel parcel) {
        this.mCsiRsrp = parcel.readInt();
        this.mCsiRsrq = parcel.readInt();
        this.mCsiSinr = parcel.readInt();
        this.mSsRsrp = parcel.readInt();
        this.mSsRsrq = parcel.readInt();
        this.mSsSinr = parcel.readInt();
        this.mLevel = parcel.readInt();
    }

    public CellSignalStrengthNr(CellSignalStrengthNr cellSignalStrengthNr) {
        this.mCsiRsrp = cellSignalStrengthNr.mCsiRsrp;
        this.mCsiRsrq = cellSignalStrengthNr.mCsiRsrq;
        this.mCsiSinr = cellSignalStrengthNr.mCsiSinr;
        this.mSsRsrp = cellSignalStrengthNr.mSsRsrp;
        this.mSsRsrq = cellSignalStrengthNr.mSsRsrq;
        this.mSsSinr = cellSignalStrengthNr.mSsSinr;
        this.mLevel = cellSignalStrengthNr.mLevel;
    }

    @Override
    public CellSignalStrengthNr copy() {
        return new CellSignalStrengthNr(this);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public boolean equals(Object object) {
        boolean bl = object instanceof CellSignalStrengthNr;
        boolean bl2 = false;
        if (bl) {
            object = (CellSignalStrengthNr)object;
            bl = bl2;
            if (this.mCsiRsrp == ((CellSignalStrengthNr)object).mCsiRsrp) {
                bl = bl2;
                if (this.mCsiRsrq == ((CellSignalStrengthNr)object).mCsiRsrq) {
                    bl = bl2;
                    if (this.mCsiSinr == ((CellSignalStrengthNr)object).mCsiSinr) {
                        bl = bl2;
                        if (this.mSsRsrp == ((CellSignalStrengthNr)object).mSsRsrp) {
                            bl = bl2;
                            if (this.mSsRsrq == ((CellSignalStrengthNr)object).mSsRsrq) {
                                bl = bl2;
                                if (this.mSsSinr == ((CellSignalStrengthNr)object).mSsSinr) {
                                    bl = bl2;
                                    if (this.mLevel == ((CellSignalStrengthNr)object).mLevel) {
                                        bl = true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return bl;
        }
        return false;
    }

    @Override
    public int getAsuLevel() {
        int n = this.getDbm();
        n = n == Integer.MAX_VALUE ? 99 : (n <= -140 ? 0 : (n >= -43 ? 97 : (n += 140)));
        return n;
    }

    public int getCsiRsrp() {
        return this.mCsiRsrp;
    }

    public int getCsiRsrq() {
        return this.mCsiRsrq;
    }

    public int getCsiSinr() {
        return this.mCsiSinr;
    }

    @Override
    public int getDbm() {
        return this.mCsiRsrp;
    }

    @Override
    public int getLevel() {
        return this.mLevel;
    }

    public int getSsRsrp() {
        return this.mSsRsrp;
    }

    public int getSsRsrq() {
        return this.mSsRsrq;
    }

    public int getSsSinr() {
        return this.mSsSinr;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.mCsiRsrp, this.mCsiRsrq, this.mCsiSinr, this.mSsRsrp, this.mSsRsrq, this.mSsSinr, this.mLevel);
    }

    @Override
    public boolean isValid() {
        return this.equals(sInvalid) ^ true;
    }

    @Override
    public void setDefaultValues() {
        this.mCsiRsrp = Integer.MAX_VALUE;
        this.mCsiRsrq = Integer.MAX_VALUE;
        this.mCsiSinr = Integer.MAX_VALUE;
        this.mSsRsrp = Integer.MAX_VALUE;
        this.mSsRsrq = Integer.MAX_VALUE;
        this.mSsSinr = Integer.MAX_VALUE;
        this.mLevel = 0;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CellSignalStrengthNr:{");
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(" csiRsrp = ");
        stringBuilder2.append(this.mCsiRsrp);
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(" csiRsrq = ");
        stringBuilder2.append(this.mCsiRsrq);
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(" csiSinr = ");
        stringBuilder2.append(this.mCsiSinr);
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(" ssRsrp = ");
        stringBuilder2.append(this.mSsRsrp);
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(" ssRsrq = ");
        stringBuilder2.append(this.mSsRsrq);
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(" ssSinr = ");
        stringBuilder2.append(this.mSsSinr);
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(" level = ");
        stringBuilder2.append(this.mLevel);
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder.append(" }");
        return stringBuilder.toString();
    }

    @Override
    public void updateLevel(PersistableBundle persistableBundle, ServiceState serviceState) {
        int n = this.mCsiRsrp;
        this.mLevel = n == Integer.MAX_VALUE ? 0 : (n >= -95 ? 4 : (n >= -105 ? 3 : (n >= -115 ? 2 : 1)));
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mCsiRsrp);
        parcel.writeInt(this.mCsiRsrq);
        parcel.writeInt(this.mCsiSinr);
        parcel.writeInt(this.mSsRsrp);
        parcel.writeInt(this.mSsRsrq);
        parcel.writeInt(this.mSsSinr);
        parcel.writeInt(this.mLevel);
    }

}

