/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.hardware.radio.V1_0.CdmaSignalStrength;
import android.hardware.radio.V1_0.EvdoSignalStrength;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.telephony.CellSignalStrength;
import android.telephony.Rlog;
import android.telephony.ServiceState;
import java.util.Objects;

public final class CellSignalStrengthCdma
extends CellSignalStrength
implements Parcelable {
    public static final Parcelable.Creator<CellSignalStrengthCdma> CREATOR;
    private static final boolean DBG = false;
    private static final String LOG_TAG = "CellSignalStrengthCdma";
    private static final CellSignalStrengthCdma sInvalid;
    private int mCdmaDbm;
    private int mCdmaEcio;
    private int mEvdoDbm;
    private int mEvdoEcio;
    private int mEvdoSnr;
    private int mLevel;

    static {
        sInvalid = new CellSignalStrengthCdma();
        CREATOR = new Parcelable.Creator<CellSignalStrengthCdma>(){

            @Override
            public CellSignalStrengthCdma createFromParcel(Parcel parcel) {
                return new CellSignalStrengthCdma(parcel);
            }

            public CellSignalStrengthCdma[] newArray(int n) {
                return new CellSignalStrengthCdma[n];
            }
        };
    }

    public CellSignalStrengthCdma() {
        this.setDefaultValues();
    }

    public CellSignalStrengthCdma(int n, int n2, int n3, int n4, int n5) {
        this.mCdmaDbm = CellSignalStrengthCdma.inRangeOrUnavailable(n, -120, 0);
        this.mCdmaEcio = CellSignalStrengthCdma.inRangeOrUnavailable(n2, -160, 0);
        this.mEvdoDbm = CellSignalStrengthCdma.inRangeOrUnavailable(n3, -120, 0);
        this.mEvdoEcio = CellSignalStrengthCdma.inRangeOrUnavailable(n4, -160, 0);
        this.mEvdoSnr = CellSignalStrengthCdma.inRangeOrUnavailable(n5, 0, 8);
        this.updateLevel(null, null);
    }

    public CellSignalStrengthCdma(CdmaSignalStrength cdmaSignalStrength, EvdoSignalStrength evdoSignalStrength) {
        this(-cdmaSignalStrength.dbm, -cdmaSignalStrength.ecio, -evdoSignalStrength.dbm, -evdoSignalStrength.ecio, evdoSignalStrength.signalNoiseRatio);
    }

    private CellSignalStrengthCdma(Parcel parcel) {
        this.mCdmaDbm = parcel.readInt();
        this.mCdmaEcio = parcel.readInt();
        this.mEvdoDbm = parcel.readInt();
        this.mEvdoEcio = parcel.readInt();
        this.mEvdoSnr = parcel.readInt();
        this.mLevel = parcel.readInt();
    }

    public CellSignalStrengthCdma(CellSignalStrengthCdma cellSignalStrengthCdma) {
        this.copyFrom(cellSignalStrengthCdma);
    }

    private static void log(String string2) {
        Rlog.w(LOG_TAG, string2);
    }

    @Override
    public CellSignalStrengthCdma copy() {
        return new CellSignalStrengthCdma(this);
    }

    protected void copyFrom(CellSignalStrengthCdma cellSignalStrengthCdma) {
        this.mCdmaDbm = cellSignalStrengthCdma.mCdmaDbm;
        this.mCdmaEcio = cellSignalStrengthCdma.mCdmaEcio;
        this.mEvdoDbm = cellSignalStrengthCdma.mEvdoDbm;
        this.mEvdoEcio = cellSignalStrengthCdma.mEvdoEcio;
        this.mEvdoSnr = cellSignalStrengthCdma.mEvdoSnr;
        this.mLevel = cellSignalStrengthCdma.mLevel;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public boolean equals(Object object) {
        boolean bl = object instanceof CellSignalStrengthCdma;
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        object = (CellSignalStrengthCdma)object;
        bl = bl2;
        if (this.mCdmaDbm == ((CellSignalStrengthCdma)object).mCdmaDbm) {
            bl = bl2;
            if (this.mCdmaEcio == ((CellSignalStrengthCdma)object).mCdmaEcio) {
                bl = bl2;
                if (this.mEvdoDbm == ((CellSignalStrengthCdma)object).mEvdoDbm) {
                    bl = bl2;
                    if (this.mEvdoEcio == ((CellSignalStrengthCdma)object).mEvdoEcio) {
                        bl = bl2;
                        if (this.mEvdoSnr == ((CellSignalStrengthCdma)object).mEvdoSnr) {
                            bl = bl2;
                            if (this.mLevel == ((CellSignalStrengthCdma)object).mLevel) {
                                bl = true;
                            }
                        }
                    }
                }
            }
        }
        return bl;
    }

    @Override
    public int getAsuLevel() {
        int n = this.getCdmaDbm();
        int n2 = this.getCdmaEcio();
        if ((n = n == Integer.MAX_VALUE ? 99 : (n >= -75 ? 16 : (n >= -82 ? 8 : (n >= -90 ? 4 : (n >= -95 ? 2 : (n >= -100 ? 1 : 99)))))) >= (n2 = n2 == Integer.MAX_VALUE ? 99 : (n2 >= -90 ? 16 : (n2 >= -100 ? 8 : (n2 >= -115 ? 4 : (n2 >= -130 ? 2 : (n2 >= -150 ? 1 : 99))))))) {
            n = n2;
        }
        return n;
    }

    public int getCdmaDbm() {
        return this.mCdmaDbm;
    }

    public int getCdmaEcio() {
        return this.mCdmaEcio;
    }

    public int getCdmaLevel() {
        int n;
        block0 : {
            int n2 = this.getCdmaDbm();
            n = this.getCdmaEcio();
            if ((n2 = n2 == Integer.MAX_VALUE ? 0 : (n2 >= -75 ? 4 : (n2 >= -85 ? 3 : (n2 >= -95 ? 2 : (n2 >= -100 ? 1 : 0))))) >= (n = n == Integer.MAX_VALUE ? 0 : (n >= -90 ? 4 : (n >= -110 ? 3 : (n >= -130 ? 2 : (n >= -150 ? 1 : 0)))))) break block0;
            n = n2;
        }
        return n;
    }

    @Override
    public int getDbm() {
        int n;
        int n2 = this.getCdmaDbm();
        if (n2 >= (n = this.getEvdoDbm())) {
            n2 = n;
        }
        return n2;
    }

    public int getEvdoAsuLevel() {
        int n = this.getEvdoDbm();
        int n2 = this.getEvdoSnr();
        if ((n = n >= -65 ? 16 : (n >= -75 ? 8 : (n >= -85 ? 4 : (n >= -95 ? 2 : (n >= -105 ? 1 : 99))))) >= (n2 = n2 >= 7 ? 16 : (n2 >= 6 ? 8 : (n2 >= 5 ? 4 : (n2 >= 3 ? 2 : (n2 >= 1 ? 1 : 99)))))) {
            n = n2;
        }
        return n;
    }

    public int getEvdoDbm() {
        return this.mEvdoDbm;
    }

    public int getEvdoEcio() {
        return this.mEvdoEcio;
    }

    public int getEvdoLevel() {
        int n = this.getEvdoDbm();
        int n2 = this.getEvdoSnr();
        if ((n = n == Integer.MAX_VALUE ? 0 : (n >= -65 ? 4 : (n >= -75 ? 3 : (n >= -90 ? 2 : (n >= -105 ? 1 : 0))))) >= (n2 = n2 == Integer.MAX_VALUE ? 0 : (n2 >= 7 ? 4 : (n2 >= 5 ? 3 : (n2 >= 3 ? 2 : (n2 >= 1 ? 1 : 0)))))) {
            n = n2;
        }
        return n;
    }

    public int getEvdoSnr() {
        return this.mEvdoSnr;
    }

    @Override
    public int getLevel() {
        return this.mLevel;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.mCdmaDbm, this.mCdmaEcio, this.mEvdoDbm, this.mEvdoEcio, this.mEvdoSnr, this.mLevel);
    }

    @Override
    public boolean isValid() {
        return this.equals(sInvalid) ^ true;
    }

    public void setCdmaDbm(int n) {
        this.mCdmaDbm = n;
    }

    public void setCdmaEcio(int n) {
        this.mCdmaEcio = n;
    }

    @Override
    public void setDefaultValues() {
        this.mCdmaDbm = Integer.MAX_VALUE;
        this.mCdmaEcio = Integer.MAX_VALUE;
        this.mEvdoDbm = Integer.MAX_VALUE;
        this.mEvdoEcio = Integer.MAX_VALUE;
        this.mEvdoSnr = Integer.MAX_VALUE;
        this.mLevel = 0;
    }

    public void setEvdoDbm(int n) {
        this.mEvdoDbm = n;
    }

    public void setEvdoEcio(int n) {
        this.mEvdoEcio = n;
    }

    public void setEvdoSnr(int n) {
        this.mEvdoSnr = n;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CellSignalStrengthCdma: cdmaDbm=");
        stringBuilder.append(this.mCdmaDbm);
        stringBuilder.append(" cdmaEcio=");
        stringBuilder.append(this.mCdmaEcio);
        stringBuilder.append(" evdoDbm=");
        stringBuilder.append(this.mEvdoDbm);
        stringBuilder.append(" evdoEcio=");
        stringBuilder.append(this.mEvdoEcio);
        stringBuilder.append(" evdoSnr=");
        stringBuilder.append(this.mEvdoSnr);
        stringBuilder.append(" level=");
        stringBuilder.append(this.mLevel);
        return stringBuilder.toString();
    }

    @Override
    public void updateLevel(PersistableBundle persistableBundle, ServiceState serviceState) {
        int n = this.getCdmaLevel();
        int n2 = this.getEvdoLevel();
        if (n2 == 0) {
            this.mLevel = this.getCdmaLevel();
        } else if (n == 0) {
            this.mLevel = this.getEvdoLevel();
        } else {
            if (n < n2) {
                n2 = n;
            }
            this.mLevel = n2;
        }
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mCdmaDbm);
        parcel.writeInt(this.mCdmaEcio);
        parcel.writeInt(this.mEvdoDbm);
        parcel.writeInt(this.mEvdoEcio);
        parcel.writeInt(this.mEvdoSnr);
        parcel.writeInt(this.mLevel);
    }

}

