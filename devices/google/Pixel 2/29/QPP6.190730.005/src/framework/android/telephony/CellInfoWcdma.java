/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.hardware.radio.V1_2.WcdmaSignalStrength;
import android.hardware.radio.V1_4.CellInfo;
import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.CellIdentity;
import android.telephony.CellIdentityWcdma;
import android.telephony.CellInfo;
import android.telephony.CellSignalStrength;
import android.telephony.CellSignalStrengthWcdma;
import android.telephony.Rlog;
import java.util.ArrayList;
import java.util.Objects;

public final class CellInfoWcdma
extends CellInfo
implements Parcelable {
    public static final Parcelable.Creator<CellInfoWcdma> CREATOR = new Parcelable.Creator<CellInfoWcdma>(){

        @Override
        public CellInfoWcdma createFromParcel(Parcel parcel) {
            parcel.readInt();
            return CellInfoWcdma.createFromParcelBody(parcel);
        }

        public CellInfoWcdma[] newArray(int n) {
            return new CellInfoWcdma[n];
        }
    };
    private static final boolean DBG = false;
    private static final String LOG_TAG = "CellInfoWcdma";
    private CellIdentityWcdma mCellIdentityWcdma;
    private CellSignalStrengthWcdma mCellSignalStrengthWcdma;

    public CellInfoWcdma() {
        this.mCellIdentityWcdma = new CellIdentityWcdma();
        this.mCellSignalStrengthWcdma = new CellSignalStrengthWcdma();
    }

    public CellInfoWcdma(android.hardware.radio.V1_0.CellInfo object) {
        super((android.hardware.radio.V1_0.CellInfo)object);
        object = ((android.hardware.radio.V1_0.CellInfo)object).wcdma.get(0);
        this.mCellIdentityWcdma = new CellIdentityWcdma(((android.hardware.radio.V1_0.CellInfoWcdma)object).cellIdentityWcdma);
        this.mCellSignalStrengthWcdma = new CellSignalStrengthWcdma(((android.hardware.radio.V1_0.CellInfoWcdma)object).signalStrengthWcdma);
    }

    public CellInfoWcdma(android.hardware.radio.V1_2.CellInfo object) {
        super((android.hardware.radio.V1_2.CellInfo)object);
        object = ((android.hardware.radio.V1_2.CellInfo)object).wcdma.get(0);
        this.mCellIdentityWcdma = new CellIdentityWcdma(((android.hardware.radio.V1_2.CellInfoWcdma)object).cellIdentityWcdma);
        this.mCellSignalStrengthWcdma = new CellSignalStrengthWcdma(((android.hardware.radio.V1_2.CellInfoWcdma)object).signalStrengthWcdma);
    }

    public CellInfoWcdma(android.hardware.radio.V1_4.CellInfo object, long l) {
        super((android.hardware.radio.V1_4.CellInfo)object, l);
        object = ((android.hardware.radio.V1_4.CellInfo)object).info.wcdma();
        this.mCellIdentityWcdma = new CellIdentityWcdma(((android.hardware.radio.V1_2.CellInfoWcdma)object).cellIdentityWcdma);
        this.mCellSignalStrengthWcdma = new CellSignalStrengthWcdma(((android.hardware.radio.V1_2.CellInfoWcdma)object).signalStrengthWcdma);
    }

    private CellInfoWcdma(Parcel parcel) {
        super(parcel);
        this.mCellIdentityWcdma = CellIdentityWcdma.CREATOR.createFromParcel(parcel);
        this.mCellSignalStrengthWcdma = CellSignalStrengthWcdma.CREATOR.createFromParcel(parcel);
    }

    public CellInfoWcdma(CellInfoWcdma cellInfoWcdma) {
        super(cellInfoWcdma);
        this.mCellIdentityWcdma = cellInfoWcdma.mCellIdentityWcdma.copy();
        this.mCellSignalStrengthWcdma = cellInfoWcdma.mCellSignalStrengthWcdma.copy();
    }

    protected static CellInfoWcdma createFromParcelBody(Parcel parcel) {
        return new CellInfoWcdma(parcel);
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
            object = (CellInfoWcdma)object;
            if (this.mCellIdentityWcdma.equals(((CellInfoWcdma)object).mCellIdentityWcdma) && (bl = this.mCellSignalStrengthWcdma.equals(((CellInfoWcdma)object).mCellSignalStrengthWcdma))) {
                bl2 = true;
            }
            return bl2;
        }
        catch (ClassCastException classCastException) {
            return false;
        }
    }

    @Override
    public CellIdentityWcdma getCellIdentity() {
        return this.mCellIdentityWcdma;
    }

    @Override
    public CellSignalStrengthWcdma getCellSignalStrength() {
        return this.mCellSignalStrengthWcdma;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.mCellIdentityWcdma, this.mCellSignalStrengthWcdma);
    }

    @Override
    public CellInfo sanitizeLocationInfo() {
        CellInfoWcdma cellInfoWcdma = new CellInfoWcdma(this);
        cellInfoWcdma.mCellIdentityWcdma = this.mCellIdentityWcdma.sanitizeLocationInfo();
        return cellInfoWcdma;
    }

    public void setCellIdentity(CellIdentityWcdma cellIdentityWcdma) {
        this.mCellIdentityWcdma = cellIdentityWcdma;
    }

    public void setCellSignalStrength(CellSignalStrengthWcdma cellSignalStrengthWcdma) {
        this.mCellSignalStrengthWcdma = cellSignalStrengthWcdma;
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("CellInfoWcdma:{");
        stringBuffer.append(super.toString());
        stringBuffer.append(" ");
        stringBuffer.append(this.mCellIdentityWcdma);
        stringBuffer.append(" ");
        stringBuffer.append(this.mCellSignalStrengthWcdma);
        stringBuffer.append("}");
        return stringBuffer.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        super.writeToParcel(parcel, n, 4);
        this.mCellIdentityWcdma.writeToParcel(parcel, n);
        this.mCellSignalStrengthWcdma.writeToParcel(parcel, n);
    }

}

