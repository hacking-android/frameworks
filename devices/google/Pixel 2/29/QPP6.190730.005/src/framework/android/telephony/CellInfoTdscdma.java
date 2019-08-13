/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.hardware.radio.V1_0.TdScdmaSignalStrength;
import android.hardware.radio.V1_2.TdscdmaSignalStrength;
import android.hardware.radio.V1_4.CellInfo;
import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.CellIdentity;
import android.telephony.CellIdentityTdscdma;
import android.telephony.CellInfo;
import android.telephony.CellSignalStrength;
import android.telephony.CellSignalStrengthTdscdma;
import android.telephony.Rlog;
import java.util.ArrayList;
import java.util.Objects;

public final class CellInfoTdscdma
extends CellInfo
implements Parcelable {
    public static final Parcelable.Creator<CellInfoTdscdma> CREATOR = new Parcelable.Creator<CellInfoTdscdma>(){

        @Override
        public CellInfoTdscdma createFromParcel(Parcel parcel) {
            parcel.readInt();
            return CellInfoTdscdma.createFromParcelBody(parcel);
        }

        public CellInfoTdscdma[] newArray(int n) {
            return new CellInfoTdscdma[n];
        }
    };
    private static final boolean DBG = false;
    private static final String LOG_TAG = "CellInfoTdscdma";
    private CellIdentityTdscdma mCellIdentityTdscdma;
    private CellSignalStrengthTdscdma mCellSignalStrengthTdscdma;

    public CellInfoTdscdma() {
        this.mCellIdentityTdscdma = new CellIdentityTdscdma();
        this.mCellSignalStrengthTdscdma = new CellSignalStrengthTdscdma();
    }

    public CellInfoTdscdma(android.hardware.radio.V1_0.CellInfo object) {
        super((android.hardware.radio.V1_0.CellInfo)object);
        object = ((android.hardware.radio.V1_0.CellInfo)object).tdscdma.get(0);
        this.mCellIdentityTdscdma = new CellIdentityTdscdma(((android.hardware.radio.V1_0.CellInfoTdscdma)object).cellIdentityTdscdma);
        this.mCellSignalStrengthTdscdma = new CellSignalStrengthTdscdma(((android.hardware.radio.V1_0.CellInfoTdscdma)object).signalStrengthTdscdma);
    }

    public CellInfoTdscdma(android.hardware.radio.V1_2.CellInfo object) {
        super((android.hardware.radio.V1_2.CellInfo)object);
        object = ((android.hardware.radio.V1_2.CellInfo)object).tdscdma.get(0);
        this.mCellIdentityTdscdma = new CellIdentityTdscdma(((android.hardware.radio.V1_2.CellInfoTdscdma)object).cellIdentityTdscdma);
        this.mCellSignalStrengthTdscdma = new CellSignalStrengthTdscdma(((android.hardware.radio.V1_2.CellInfoTdscdma)object).signalStrengthTdscdma);
    }

    public CellInfoTdscdma(android.hardware.radio.V1_4.CellInfo object, long l) {
        super((android.hardware.radio.V1_4.CellInfo)object, l);
        object = ((android.hardware.radio.V1_4.CellInfo)object).info.tdscdma();
        this.mCellIdentityTdscdma = new CellIdentityTdscdma(((android.hardware.radio.V1_2.CellInfoTdscdma)object).cellIdentityTdscdma);
        this.mCellSignalStrengthTdscdma = new CellSignalStrengthTdscdma(((android.hardware.radio.V1_2.CellInfoTdscdma)object).signalStrengthTdscdma);
    }

    private CellInfoTdscdma(Parcel parcel) {
        super(parcel);
        this.mCellIdentityTdscdma = CellIdentityTdscdma.CREATOR.createFromParcel(parcel);
        this.mCellSignalStrengthTdscdma = CellSignalStrengthTdscdma.CREATOR.createFromParcel(parcel);
    }

    public CellInfoTdscdma(CellInfoTdscdma cellInfoTdscdma) {
        super(cellInfoTdscdma);
        this.mCellIdentityTdscdma = cellInfoTdscdma.mCellIdentityTdscdma.copy();
        this.mCellSignalStrengthTdscdma = cellInfoTdscdma.mCellSignalStrengthTdscdma.copy();
    }

    protected static CellInfoTdscdma createFromParcelBody(Parcel parcel) {
        return new CellInfoTdscdma(parcel);
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
            object = (CellInfoTdscdma)object;
            if (this.mCellIdentityTdscdma.equals(((CellInfoTdscdma)object).mCellIdentityTdscdma) && (bl = this.mCellSignalStrengthTdscdma.equals(((CellInfoTdscdma)object).mCellSignalStrengthTdscdma))) {
                bl2 = true;
            }
            return bl2;
        }
        catch (ClassCastException classCastException) {
            return false;
        }
    }

    @Override
    public CellIdentityTdscdma getCellIdentity() {
        return this.mCellIdentityTdscdma;
    }

    @Override
    public CellSignalStrengthTdscdma getCellSignalStrength() {
        return this.mCellSignalStrengthTdscdma;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.mCellIdentityTdscdma, this.mCellSignalStrengthTdscdma);
    }

    @Override
    public CellInfo sanitizeLocationInfo() {
        CellInfoTdscdma cellInfoTdscdma = new CellInfoTdscdma(this);
        cellInfoTdscdma.mCellIdentityTdscdma = this.mCellIdentityTdscdma.sanitizeLocationInfo();
        return cellInfoTdscdma;
    }

    public void setCellIdentity(CellIdentityTdscdma cellIdentityTdscdma) {
        this.mCellIdentityTdscdma = cellIdentityTdscdma;
    }

    public void setCellSignalStrength(CellSignalStrengthTdscdma cellSignalStrengthTdscdma) {
        this.mCellSignalStrengthTdscdma = cellSignalStrengthTdscdma;
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("CellInfoTdscdma:{");
        stringBuffer.append(super.toString());
        stringBuffer.append(" ");
        stringBuffer.append(this.mCellIdentityTdscdma);
        stringBuffer.append(" ");
        stringBuffer.append(this.mCellSignalStrengthTdscdma);
        stringBuffer.append("}");
        return stringBuffer.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        super.writeToParcel(parcel, n, 5);
        this.mCellIdentityTdscdma.writeToParcel(parcel, n);
        this.mCellSignalStrengthTdscdma.writeToParcel(parcel, n);
    }

}

