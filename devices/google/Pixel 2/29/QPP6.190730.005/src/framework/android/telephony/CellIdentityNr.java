/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.CellIdentity;
import android.telephony.CellLocation;
import android.telephony.gsm.GsmCellLocation;
import java.util.Objects;

public final class CellIdentityNr
extends CellIdentity {
    public static final Parcelable.Creator<CellIdentityNr> CREATOR = new Parcelable.Creator<CellIdentityNr>(){

        @Override
        public CellIdentityNr createFromParcel(Parcel parcel) {
            parcel.readInt();
            return CellIdentityNr.createFromParcelBody(parcel);
        }

        public CellIdentityNr[] newArray(int n) {
            return new CellIdentityNr[n];
        }
    };
    private static final long MAX_NCI = 0xFFFFFFFFFL;
    private static final int MAX_NRARFCN = 3279165;
    private static final int MAX_PCI = 1007;
    private static final int MAX_TAC = 65535;
    private static final String TAG = "CellIdentityNr";
    private final long mNci;
    private final int mNrArfcn;
    private final int mPci;
    private final int mTac;

    public CellIdentityNr(int n, int n2, int n3, String string2, String string3, long l, String string4, String string5) {
        super(TAG, 6, string2, string3, string4, string5);
        this.mPci = CellIdentityNr.inRangeOrUnavailable(n, 0, 1007);
        this.mTac = CellIdentityNr.inRangeOrUnavailable(n2, 0, 65535);
        this.mNrArfcn = CellIdentityNr.inRangeOrUnavailable(n3, 0, 3279165);
        this.mNci = CellIdentityNr.inRangeOrUnavailable(l, 0L, 0xFFFFFFFFFL);
    }

    private CellIdentityNr(Parcel parcel) {
        super(TAG, 6, parcel);
        this.mPci = parcel.readInt();
        this.mTac = parcel.readInt();
        this.mNrArfcn = parcel.readInt();
        this.mNci = parcel.readLong();
    }

    protected static CellIdentityNr createFromParcelBody(Parcel parcel) {
        return new CellIdentityNr(parcel);
    }

    @Override
    public CellLocation asCellLocation() {
        return new GsmCellLocation();
    }

    @Override
    public boolean equals(Object object) {
        boolean bl = object instanceof CellIdentityNr;
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        object = (CellIdentityNr)object;
        bl = bl2;
        if (super.equals(object)) {
            bl = bl2;
            if (this.mPci == ((CellIdentityNr)object).mPci) {
                bl = bl2;
                if (this.mTac == ((CellIdentityNr)object).mTac) {
                    bl = bl2;
                    if (this.mNrArfcn == ((CellIdentityNr)object).mNrArfcn) {
                        bl = bl2;
                        if (this.mNci == ((CellIdentityNr)object).mNci) {
                            bl = true;
                        }
                    }
                }
            }
        }
        return bl;
    }

    @Override
    public String getMccString() {
        return this.mMccStr;
    }

    @Override
    public String getMncString() {
        return this.mMncStr;
    }

    public long getNci() {
        return this.mNci;
    }

    public int getNrarfcn() {
        return this.mNrArfcn;
    }

    public int getPci() {
        return this.mPci;
    }

    public int getTac() {
        return this.mTac;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.mPci, this.mTac, this.mNrArfcn, this.mNci);
    }

    public CellIdentityNr sanitizeLocationInfo() {
        return new CellIdentityNr(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, this.mMccStr, this.mMncStr, Integer.MAX_VALUE, this.mAlphaLong, this.mAlphaShort);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("CellIdentityNr:{");
        stringBuilder.append(" mPci = ");
        stringBuilder.append(this.mPci);
        stringBuilder.append(" mTac = ");
        stringBuilder.append(this.mTac);
        stringBuilder.append(" mNrArfcn = ");
        stringBuilder.append(this.mNrArfcn);
        stringBuilder.append(" mMcc = ");
        stringBuilder.append(this.mMccStr);
        stringBuilder.append(" mMnc = ");
        stringBuilder.append(this.mMncStr);
        stringBuilder.append(" mNci = ");
        stringBuilder.append(this.mNci);
        stringBuilder.append(" mAlphaLong = ");
        stringBuilder.append(this.mAlphaLong);
        stringBuilder.append(" mAlphaShort = ");
        stringBuilder.append(this.mAlphaShort);
        stringBuilder.append(" }");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        super.writeToParcel(parcel, 6);
        parcel.writeInt(this.mPci);
        parcel.writeInt(this.mTac);
        parcel.writeInt(this.mNrArfcn);
        parcel.writeLong(this.mNci);
    }

}

