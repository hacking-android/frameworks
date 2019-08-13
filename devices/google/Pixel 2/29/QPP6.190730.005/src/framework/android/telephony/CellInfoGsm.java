/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.annotation.UnsupportedAppUsage;
import android.hardware.radio.V1_0.GsmSignalStrength;
import android.hardware.radio.V1_4.CellInfo;
import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.CellIdentity;
import android.telephony.CellIdentityGsm;
import android.telephony.CellInfo;
import android.telephony.CellSignalStrength;
import android.telephony.CellSignalStrengthGsm;
import android.telephony.Rlog;
import java.util.ArrayList;

public final class CellInfoGsm
extends CellInfo
implements Parcelable {
    public static final Parcelable.Creator<CellInfoGsm> CREATOR = new Parcelable.Creator<CellInfoGsm>(){

        @Override
        public CellInfoGsm createFromParcel(Parcel parcel) {
            parcel.readInt();
            return CellInfoGsm.createFromParcelBody(parcel);
        }

        public CellInfoGsm[] newArray(int n) {
            return new CellInfoGsm[n];
        }
    };
    private static final boolean DBG = false;
    private static final String LOG_TAG = "CellInfoGsm";
    private CellIdentityGsm mCellIdentityGsm;
    private CellSignalStrengthGsm mCellSignalStrengthGsm;

    @UnsupportedAppUsage
    public CellInfoGsm() {
        this.mCellIdentityGsm = new CellIdentityGsm();
        this.mCellSignalStrengthGsm = new CellSignalStrengthGsm();
    }

    public CellInfoGsm(android.hardware.radio.V1_0.CellInfo object) {
        super((android.hardware.radio.V1_0.CellInfo)object);
        object = ((android.hardware.radio.V1_0.CellInfo)object).gsm.get(0);
        this.mCellIdentityGsm = new CellIdentityGsm(((android.hardware.radio.V1_0.CellInfoGsm)object).cellIdentityGsm);
        this.mCellSignalStrengthGsm = new CellSignalStrengthGsm(((android.hardware.radio.V1_0.CellInfoGsm)object).signalStrengthGsm);
    }

    public CellInfoGsm(android.hardware.radio.V1_2.CellInfo object) {
        super((android.hardware.radio.V1_2.CellInfo)object);
        object = ((android.hardware.radio.V1_2.CellInfo)object).gsm.get(0);
        this.mCellIdentityGsm = new CellIdentityGsm(((android.hardware.radio.V1_2.CellInfoGsm)object).cellIdentityGsm);
        this.mCellSignalStrengthGsm = new CellSignalStrengthGsm(((android.hardware.radio.V1_2.CellInfoGsm)object).signalStrengthGsm);
    }

    public CellInfoGsm(android.hardware.radio.V1_4.CellInfo object, long l) {
        super((android.hardware.radio.V1_4.CellInfo)object, l);
        object = ((android.hardware.radio.V1_4.CellInfo)object).info.gsm();
        this.mCellIdentityGsm = new CellIdentityGsm(((android.hardware.radio.V1_2.CellInfoGsm)object).cellIdentityGsm);
        this.mCellSignalStrengthGsm = new CellSignalStrengthGsm(((android.hardware.radio.V1_2.CellInfoGsm)object).signalStrengthGsm);
    }

    private CellInfoGsm(Parcel parcel) {
        super(parcel);
        this.mCellIdentityGsm = CellIdentityGsm.CREATOR.createFromParcel(parcel);
        this.mCellSignalStrengthGsm = CellSignalStrengthGsm.CREATOR.createFromParcel(parcel);
    }

    public CellInfoGsm(CellInfoGsm cellInfoGsm) {
        super(cellInfoGsm);
        this.mCellIdentityGsm = cellInfoGsm.mCellIdentityGsm.copy();
        this.mCellSignalStrengthGsm = cellInfoGsm.mCellSignalStrengthGsm.copy();
    }

    protected static CellInfoGsm createFromParcelBody(Parcel parcel) {
        return new CellInfoGsm(parcel);
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
            object = (CellInfoGsm)object;
            if (this.mCellIdentityGsm.equals(((CellInfoGsm)object).mCellIdentityGsm) && (bl = this.mCellSignalStrengthGsm.equals(((CellInfoGsm)object).mCellSignalStrengthGsm))) {
                bl2 = true;
            }
            return bl2;
        }
        catch (ClassCastException classCastException) {
            return false;
        }
    }

    @Override
    public CellIdentityGsm getCellIdentity() {
        return this.mCellIdentityGsm;
    }

    @Override
    public CellSignalStrengthGsm getCellSignalStrength() {
        return this.mCellSignalStrengthGsm;
    }

    @Override
    public int hashCode() {
        return super.hashCode() + this.mCellIdentityGsm.hashCode() + this.mCellSignalStrengthGsm.hashCode();
    }

    @Override
    public CellInfo sanitizeLocationInfo() {
        CellInfoGsm cellInfoGsm = new CellInfoGsm(this);
        cellInfoGsm.mCellIdentityGsm = this.mCellIdentityGsm.sanitizeLocationInfo();
        return cellInfoGsm;
    }

    public void setCellIdentity(CellIdentityGsm cellIdentityGsm) {
        this.mCellIdentityGsm = cellIdentityGsm;
    }

    public void setCellSignalStrength(CellSignalStrengthGsm cellSignalStrengthGsm) {
        this.mCellSignalStrengthGsm = cellSignalStrengthGsm;
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("CellInfoGsm:{");
        stringBuffer.append(super.toString());
        stringBuffer.append(" ");
        stringBuffer.append(this.mCellIdentityGsm);
        stringBuffer.append(" ");
        stringBuffer.append(this.mCellSignalStrengthGsm);
        stringBuffer.append("}");
        return stringBuffer.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        super.writeToParcel(parcel, n, 1);
        this.mCellIdentityGsm.writeToParcel(parcel, n);
        this.mCellSignalStrengthGsm.writeToParcel(parcel, n);
    }

}

