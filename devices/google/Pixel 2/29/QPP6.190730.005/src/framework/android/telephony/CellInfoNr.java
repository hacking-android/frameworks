/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.CellIdentity;
import android.telephony.CellIdentityNr;
import android.telephony.CellInfo;
import android.telephony.CellSignalStrength;
import android.telephony.CellSignalStrengthNr;
import java.util.Objects;

public final class CellInfoNr
extends CellInfo {
    public static final Parcelable.Creator<CellInfoNr> CREATOR = new Parcelable.Creator<CellInfoNr>(){

        @Override
        public CellInfoNr createFromParcel(Parcel parcel) {
            parcel.readInt();
            return new CellInfoNr(parcel);
        }

        public CellInfoNr[] newArray(int n) {
            return new CellInfoNr[n];
        }
    };
    private static final String TAG = "CellInfoNr";
    private final CellIdentityNr mCellIdentity;
    private final CellSignalStrengthNr mCellSignalStrength;

    private CellInfoNr(Parcel parcel) {
        super(parcel);
        this.mCellIdentity = CellIdentityNr.CREATOR.createFromParcel(parcel);
        this.mCellSignalStrength = CellSignalStrengthNr.CREATOR.createFromParcel(parcel);
    }

    private CellInfoNr(CellInfoNr cellInfoNr, boolean bl) {
        super(cellInfoNr);
        CellIdentityNr cellIdentityNr = bl ? cellInfoNr.mCellIdentity.sanitizeLocationInfo() : cellInfoNr.mCellIdentity;
        this.mCellIdentity = cellIdentityNr;
        this.mCellSignalStrength = cellInfoNr.mCellSignalStrength;
    }

    protected static CellInfoNr createFromParcelBody(Parcel parcel) {
        return new CellInfoNr(parcel);
    }

    @Override
    public boolean equals(Object object) {
        boolean bl;
        block1 : {
            boolean bl2 = object instanceof CellInfoNr;
            bl = false;
            if (!bl2) {
                return false;
            }
            if (!super.equals(object = (CellInfoNr)object) || !this.mCellIdentity.equals(((CellInfoNr)object).mCellIdentity) || !this.mCellSignalStrength.equals(((CellInfoNr)object).mCellSignalStrength)) break block1;
            bl = true;
        }
        return bl;
    }

    @Override
    public CellIdentity getCellIdentity() {
        return this.mCellIdentity;
    }

    @Override
    public CellSignalStrength getCellSignalStrength() {
        return this.mCellSignalStrength;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.mCellIdentity, this.mCellSignalStrength);
    }

    @Override
    public CellInfo sanitizeLocationInfo() {
        return new CellInfoNr(this, true);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CellInfoNr:{");
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(" ");
        stringBuilder2.append(super.toString());
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(" ");
        stringBuilder2.append(this.mCellIdentity);
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(" ");
        stringBuilder2.append(this.mCellSignalStrength);
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder.append(" }");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        super.writeToParcel(parcel, n, 6);
        this.mCellIdentity.writeToParcel(parcel, n);
        this.mCellSignalStrength.writeToParcel(parcel, n);
    }

}

