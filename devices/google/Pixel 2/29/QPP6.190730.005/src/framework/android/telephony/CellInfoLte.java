/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.annotation.UnsupportedAppUsage;
import android.hardware.radio.V1_0.LteSignalStrength;
import android.hardware.radio.V1_4.CellInfo;
import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.CellConfigLte;
import android.telephony.CellIdentity;
import android.telephony.CellIdentityLte;
import android.telephony.CellInfo;
import android.telephony.CellSignalStrength;
import android.telephony.CellSignalStrengthLte;
import android.telephony.Rlog;
import java.util.ArrayList;
import java.util.Objects;

public final class CellInfoLte
extends CellInfo
implements Parcelable {
    public static final Parcelable.Creator<CellInfoLte> CREATOR = new Parcelable.Creator<CellInfoLte>(){

        @Override
        public CellInfoLte createFromParcel(Parcel parcel) {
            parcel.readInt();
            return CellInfoLte.createFromParcelBody(parcel);
        }

        public CellInfoLte[] newArray(int n) {
            return new CellInfoLte[n];
        }
    };
    private static final boolean DBG = false;
    private static final String LOG_TAG = "CellInfoLte";
    private CellConfigLte mCellConfig;
    private CellIdentityLte mCellIdentityLte;
    private CellSignalStrengthLte mCellSignalStrengthLte;

    @UnsupportedAppUsage
    public CellInfoLte() {
        this.mCellIdentityLte = new CellIdentityLte();
        this.mCellSignalStrengthLte = new CellSignalStrengthLte();
        this.mCellConfig = new CellConfigLte();
    }

    public CellInfoLte(android.hardware.radio.V1_0.CellInfo object) {
        super((android.hardware.radio.V1_0.CellInfo)object);
        object = ((android.hardware.radio.V1_0.CellInfo)object).lte.get(0);
        this.mCellIdentityLte = new CellIdentityLte(((android.hardware.radio.V1_0.CellInfoLte)object).cellIdentityLte);
        this.mCellSignalStrengthLte = new CellSignalStrengthLte(((android.hardware.radio.V1_0.CellInfoLte)object).signalStrengthLte);
        this.mCellConfig = new CellConfigLte();
    }

    public CellInfoLte(android.hardware.radio.V1_2.CellInfo object) {
        super((android.hardware.radio.V1_2.CellInfo)object);
        object = ((android.hardware.radio.V1_2.CellInfo)object).lte.get(0);
        this.mCellIdentityLte = new CellIdentityLte(((android.hardware.radio.V1_2.CellInfoLte)object).cellIdentityLte);
        this.mCellSignalStrengthLte = new CellSignalStrengthLte(((android.hardware.radio.V1_2.CellInfoLte)object).signalStrengthLte);
        this.mCellConfig = new CellConfigLte();
    }

    public CellInfoLte(android.hardware.radio.V1_4.CellInfo object, long l) {
        super((android.hardware.radio.V1_4.CellInfo)object, l);
        object = ((android.hardware.radio.V1_4.CellInfo)object).info.lte();
        this.mCellIdentityLte = new CellIdentityLte(object.base.cellIdentityLte);
        this.mCellSignalStrengthLte = new CellSignalStrengthLte(object.base.signalStrengthLte);
        this.mCellConfig = new CellConfigLte(((android.hardware.radio.V1_4.CellInfoLte)object).cellConfig);
    }

    private CellInfoLte(Parcel parcel) {
        super(parcel);
        this.mCellIdentityLte = CellIdentityLte.CREATOR.createFromParcel(parcel);
        this.mCellSignalStrengthLte = CellSignalStrengthLte.CREATOR.createFromParcel(parcel);
        this.mCellConfig = CellConfigLte.CREATOR.createFromParcel(parcel);
    }

    public CellInfoLte(CellInfoLte cellInfoLte) {
        super(cellInfoLte);
        this.mCellIdentityLte = cellInfoLte.mCellIdentityLte.copy();
        this.mCellSignalStrengthLte = cellInfoLte.mCellSignalStrengthLte.copy();
        this.mCellConfig = new CellConfigLte(cellInfoLte.mCellConfig);
    }

    protected static CellInfoLte createFromParcelBody(Parcel parcel) {
        return new CellInfoLte(parcel);
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
        boolean bl;
        block1 : {
            boolean bl2 = object instanceof CellInfoLte;
            bl = false;
            if (!bl2) {
                return false;
            }
            if (!super.equals(object = (CellInfoLte)object) || !this.mCellIdentityLte.equals(((CellInfoLte)object).mCellIdentityLte) || !this.mCellSignalStrengthLte.equals(((CellInfoLte)object).mCellSignalStrengthLte) || !this.mCellConfig.equals(((CellInfoLte)object).mCellConfig)) break block1;
            bl = true;
        }
        return bl;
    }

    public CellConfigLte getCellConfig() {
        return this.mCellConfig;
    }

    @Override
    public CellIdentityLte getCellIdentity() {
        return this.mCellIdentityLte;
    }

    @Override
    public CellSignalStrengthLte getCellSignalStrength() {
        return this.mCellSignalStrengthLte;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.mCellIdentityLte.hashCode(), this.mCellSignalStrengthLte.hashCode(), this.mCellConfig.hashCode());
    }

    @Override
    public CellInfo sanitizeLocationInfo() {
        CellInfoLte cellInfoLte = new CellInfoLte(this);
        cellInfoLte.mCellIdentityLte = this.mCellIdentityLte.sanitizeLocationInfo();
        return cellInfoLte;
    }

    public void setCellConfig(CellConfigLte cellConfigLte) {
        this.mCellConfig = cellConfigLte;
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public void setCellIdentity(CellIdentityLte cellIdentityLte) {
        this.mCellIdentityLte = cellIdentityLte;
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public void setCellSignalStrength(CellSignalStrengthLte cellSignalStrengthLte) {
        this.mCellSignalStrengthLte = cellSignalStrengthLte;
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("CellInfoLte:{");
        stringBuffer.append(super.toString());
        stringBuffer.append(" ");
        stringBuffer.append(this.mCellIdentityLte);
        stringBuffer.append(" ");
        stringBuffer.append(this.mCellSignalStrengthLte);
        stringBuffer.append(" ");
        stringBuffer.append(this.mCellConfig);
        stringBuffer.append("}");
        return stringBuffer.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        super.writeToParcel(parcel, n, 3);
        this.mCellIdentityLte.writeToParcel(parcel, n);
        this.mCellSignalStrengthLte.writeToParcel(parcel, n);
        this.mCellConfig.writeToParcel(parcel, n);
    }

}

