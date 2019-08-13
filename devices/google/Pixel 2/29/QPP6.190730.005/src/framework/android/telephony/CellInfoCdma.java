/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.annotation.UnsupportedAppUsage;
import android.hardware.radio.V1_0.CdmaSignalStrength;
import android.hardware.radio.V1_0.EvdoSignalStrength;
import android.hardware.radio.V1_4.CellInfo;
import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.CellIdentity;
import android.telephony.CellIdentityCdma;
import android.telephony.CellInfo;
import android.telephony.CellSignalStrength;
import android.telephony.CellSignalStrengthCdma;
import android.telephony.Rlog;
import java.util.ArrayList;

public final class CellInfoCdma
extends CellInfo
implements Parcelable {
    public static final Parcelable.Creator<CellInfoCdma> CREATOR = new Parcelable.Creator<CellInfoCdma>(){

        @Override
        public CellInfoCdma createFromParcel(Parcel parcel) {
            parcel.readInt();
            return CellInfoCdma.createFromParcelBody(parcel);
        }

        public CellInfoCdma[] newArray(int n) {
            return new CellInfoCdma[n];
        }
    };
    private static final boolean DBG = false;
    private static final String LOG_TAG = "CellInfoCdma";
    private CellIdentityCdma mCellIdentityCdma;
    private CellSignalStrengthCdma mCellSignalStrengthCdma;

    @UnsupportedAppUsage
    public CellInfoCdma() {
        this.mCellIdentityCdma = new CellIdentityCdma();
        this.mCellSignalStrengthCdma = new CellSignalStrengthCdma();
    }

    public CellInfoCdma(android.hardware.radio.V1_0.CellInfo object) {
        super((android.hardware.radio.V1_0.CellInfo)object);
        object = ((android.hardware.radio.V1_0.CellInfo)object).cdma.get(0);
        this.mCellIdentityCdma = new CellIdentityCdma(((android.hardware.radio.V1_0.CellInfoCdma)object).cellIdentityCdma);
        this.mCellSignalStrengthCdma = new CellSignalStrengthCdma(((android.hardware.radio.V1_0.CellInfoCdma)object).signalStrengthCdma, ((android.hardware.radio.V1_0.CellInfoCdma)object).signalStrengthEvdo);
    }

    public CellInfoCdma(android.hardware.radio.V1_2.CellInfo object) {
        super((android.hardware.radio.V1_2.CellInfo)object);
        object = ((android.hardware.radio.V1_2.CellInfo)object).cdma.get(0);
        this.mCellIdentityCdma = new CellIdentityCdma(((android.hardware.radio.V1_2.CellInfoCdma)object).cellIdentityCdma);
        this.mCellSignalStrengthCdma = new CellSignalStrengthCdma(((android.hardware.radio.V1_2.CellInfoCdma)object).signalStrengthCdma, ((android.hardware.radio.V1_2.CellInfoCdma)object).signalStrengthEvdo);
    }

    public CellInfoCdma(android.hardware.radio.V1_4.CellInfo object, long l) {
        super((android.hardware.radio.V1_4.CellInfo)object, l);
        object = ((android.hardware.radio.V1_4.CellInfo)object).info.cdma();
        this.mCellIdentityCdma = new CellIdentityCdma(((android.hardware.radio.V1_2.CellInfoCdma)object).cellIdentityCdma);
        this.mCellSignalStrengthCdma = new CellSignalStrengthCdma(((android.hardware.radio.V1_2.CellInfoCdma)object).signalStrengthCdma, ((android.hardware.radio.V1_2.CellInfoCdma)object).signalStrengthEvdo);
    }

    private CellInfoCdma(Parcel parcel) {
        super(parcel);
        this.mCellIdentityCdma = CellIdentityCdma.CREATOR.createFromParcel(parcel);
        this.mCellSignalStrengthCdma = CellSignalStrengthCdma.CREATOR.createFromParcel(parcel);
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public CellInfoCdma(CellInfoCdma cellInfoCdma) {
        super(cellInfoCdma);
        this.mCellIdentityCdma = cellInfoCdma.mCellIdentityCdma.copy();
        this.mCellSignalStrengthCdma = cellInfoCdma.mCellSignalStrengthCdma.copy();
    }

    protected static CellInfoCdma createFromParcelBody(Parcel parcel) {
        return new CellInfoCdma(parcel);
    }

    private static void log(String string2) {
        Rlog.w(LOG_TAG, string2);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public boolean equals(Object object) {
        boolean bl = super.equals(object);
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        try {
            object = (CellInfoCdma)object;
            if (this.mCellIdentityCdma.equals(((CellInfoCdma)object).mCellIdentityCdma) && (bl = this.mCellSignalStrengthCdma.equals(((CellInfoCdma)object).mCellSignalStrengthCdma))) {
                bl2 = true;
            }
            return bl2;
        }
        catch (ClassCastException classCastException) {
            return false;
        }
    }

    @Override
    public CellIdentityCdma getCellIdentity() {
        return this.mCellIdentityCdma;
    }

    @Override
    public CellSignalStrengthCdma getCellSignalStrength() {
        return this.mCellSignalStrengthCdma;
    }

    @Override
    public int hashCode() {
        return super.hashCode() + this.mCellIdentityCdma.hashCode() + this.mCellSignalStrengthCdma.hashCode();
    }

    @Override
    public CellInfo sanitizeLocationInfo() {
        CellInfoCdma cellInfoCdma = new CellInfoCdma(this);
        cellInfoCdma.mCellIdentityCdma = this.mCellIdentityCdma.sanitizeLocationInfo();
        return cellInfoCdma;
    }

    @UnsupportedAppUsage
    public void setCellIdentity(CellIdentityCdma cellIdentityCdma) {
        this.mCellIdentityCdma = cellIdentityCdma;
    }

    public void setCellSignalStrength(CellSignalStrengthCdma cellSignalStrengthCdma) {
        this.mCellSignalStrengthCdma = cellSignalStrengthCdma;
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("CellInfoCdma:{");
        stringBuffer.append(super.toString());
        stringBuffer.append(" ");
        stringBuffer.append(this.mCellIdentityCdma);
        stringBuffer.append(" ");
        stringBuffer.append(this.mCellSignalStrengthCdma);
        stringBuffer.append("}");
        return stringBuffer.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        super.writeToParcel(parcel, n, 2);
        this.mCellIdentityCdma.writeToParcel(parcel, n);
        this.mCellSignalStrengthCdma.writeToParcel(parcel, n);
    }

}

