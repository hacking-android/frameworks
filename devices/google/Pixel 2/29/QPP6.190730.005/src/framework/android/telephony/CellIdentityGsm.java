/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.annotation.UnsupportedAppUsage;
import android.hardware.radio.V1_2.CellIdentityOperatorNames;
import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.CellIdentity;
import android.telephony.CellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;
import java.util.Objects;

public final class CellIdentityGsm
extends CellIdentity {
    public static final Parcelable.Creator<CellIdentityGsm> CREATOR;
    private static final boolean DBG = false;
    private static final int MAX_ARFCN = 65535;
    private static final int MAX_BSIC = 63;
    private static final int MAX_CID = 65535;
    private static final int MAX_LAC = 65535;
    private static final String TAG;
    private final int mArfcn;
    private final int mBsic;
    private final int mCid;
    private final int mLac;

    static {
        TAG = CellIdentityGsm.class.getSimpleName();
        CREATOR = new Parcelable.Creator<CellIdentityGsm>(){

            @Override
            public CellIdentityGsm createFromParcel(Parcel parcel) {
                parcel.readInt();
                return CellIdentityGsm.createFromParcelBody(parcel);
            }

            public CellIdentityGsm[] newArray(int n) {
                return new CellIdentityGsm[n];
            }
        };
    }

    @UnsupportedAppUsage
    public CellIdentityGsm() {
        super(TAG, 1, null, null, null, null);
        this.mLac = Integer.MAX_VALUE;
        this.mCid = Integer.MAX_VALUE;
        this.mArfcn = Integer.MAX_VALUE;
        this.mBsic = Integer.MAX_VALUE;
    }

    public CellIdentityGsm(int n, int n2, int n3, int n4, String string2, String string3, String string4, String string5) {
        super(TAG, 1, string2, string3, string4, string5);
        this.mLac = CellIdentityGsm.inRangeOrUnavailable(n, 0, 65535);
        this.mCid = CellIdentityGsm.inRangeOrUnavailable(n2, 0, 65535);
        this.mArfcn = CellIdentityGsm.inRangeOrUnavailable(n3, 0, 65535);
        this.mBsic = CellIdentityGsm.inRangeOrUnavailable(n4, 0, 63);
    }

    public CellIdentityGsm(android.hardware.radio.V1_0.CellIdentityGsm cellIdentityGsm) {
        int n = cellIdentityGsm.lac;
        int n2 = cellIdentityGsm.cid;
        int n3 = cellIdentityGsm.arfcn;
        int n4 = cellIdentityGsm.bsic == -1 ? Integer.MAX_VALUE : (int)cellIdentityGsm.bsic;
        this(n, n2, n3, n4, cellIdentityGsm.mcc, cellIdentityGsm.mnc, "", "");
    }

    public CellIdentityGsm(android.hardware.radio.V1_2.CellIdentityGsm cellIdentityGsm) {
        int n = cellIdentityGsm.base.lac;
        int n2 = cellIdentityGsm.base.cid;
        int n3 = cellIdentityGsm.base.arfcn;
        int n4 = cellIdentityGsm.base.bsic == -1 ? Integer.MAX_VALUE : (int)cellIdentityGsm.base.bsic;
        this(n, n2, n3, n4, cellIdentityGsm.base.mcc, cellIdentityGsm.base.mnc, cellIdentityGsm.operatorNames.alphaLong, cellIdentityGsm.operatorNames.alphaShort);
    }

    private CellIdentityGsm(Parcel parcel) {
        super(TAG, 1, parcel);
        this.mLac = parcel.readInt();
        this.mCid = parcel.readInt();
        this.mArfcn = parcel.readInt();
        this.mBsic = parcel.readInt();
    }

    private CellIdentityGsm(CellIdentityGsm cellIdentityGsm) {
        this(cellIdentityGsm.mLac, cellIdentityGsm.mCid, cellIdentityGsm.mArfcn, cellIdentityGsm.mBsic, cellIdentityGsm.mMccStr, cellIdentityGsm.mMncStr, cellIdentityGsm.mAlphaLong, cellIdentityGsm.mAlphaShort);
    }

    protected static CellIdentityGsm createFromParcelBody(Parcel parcel) {
        return new CellIdentityGsm(parcel);
    }

    @Override
    public GsmCellLocation asCellLocation() {
        GsmCellLocation gsmCellLocation = new GsmCellLocation();
        int n = this.mLac;
        if (n == Integer.MAX_VALUE) {
            n = -1;
        }
        int n2 = this.mCid;
        if (n2 == Integer.MAX_VALUE) {
            n2 = -1;
        }
        gsmCellLocation.setLacAndCid(n, n2);
        gsmCellLocation.setPsc(-1);
        return gsmCellLocation;
    }

    CellIdentityGsm copy() {
        return new CellIdentityGsm(this);
    }

    @Override
    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (!(object instanceof CellIdentityGsm)) {
            return false;
        }
        CellIdentityGsm cellIdentityGsm = (CellIdentityGsm)object;
        if (!(this.mLac == cellIdentityGsm.mLac && this.mCid == cellIdentityGsm.mCid && this.mArfcn == cellIdentityGsm.mArfcn && this.mBsic == cellIdentityGsm.mBsic && TextUtils.equals(this.mMccStr, cellIdentityGsm.mMccStr) && TextUtils.equals(this.mMncStr, cellIdentityGsm.mMncStr) && super.equals(object))) {
            bl = false;
        }
        return bl;
    }

    public int getArfcn() {
        return this.mArfcn;
    }

    public int getBsic() {
        return this.mBsic;
    }

    @Override
    public int getChannelNumber() {
        return this.mArfcn;
    }

    public int getCid() {
        return this.mCid;
    }

    public int getLac() {
        return this.mLac;
    }

    @Deprecated
    public int getMcc() {
        int n = this.mMccStr != null ? Integer.valueOf(this.mMccStr) : Integer.MAX_VALUE;
        return n;
    }

    @Override
    public String getMccString() {
        return this.mMccStr;
    }

    @Deprecated
    public int getMnc() {
        int n = this.mMncStr != null ? Integer.valueOf(this.mMncStr) : Integer.MAX_VALUE;
        return n;
    }

    @Override
    public String getMncString() {
        return this.mMncStr;
    }

    public String getMobileNetworkOperator() {
        CharSequence charSequence;
        if (this.mMccStr != null && this.mMncStr != null) {
            charSequence = new StringBuilder();
            charSequence.append(this.mMccStr);
            charSequence.append(this.mMncStr);
            charSequence = charSequence.toString();
        } else {
            charSequence = null;
        }
        return charSequence;
    }

    @Deprecated
    public int getPsc() {
        return Integer.MAX_VALUE;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.mLac, this.mCid, super.hashCode());
    }

    public CellIdentityGsm sanitizeLocationInfo() {
        return new CellIdentityGsm(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, this.mMccStr, this.mMncStr, this.mAlphaLong, this.mAlphaShort);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(TAG);
        stringBuilder.append(":{ mLac=");
        stringBuilder.append(this.mLac);
        stringBuilder.append(" mCid=");
        stringBuilder.append(this.mCid);
        stringBuilder.append(" mArfcn=");
        stringBuilder.append(this.mArfcn);
        stringBuilder.append(" mBsic=");
        stringBuilder.append("0x");
        stringBuilder.append(Integer.toHexString(this.mBsic));
        stringBuilder.append(" mMcc=");
        stringBuilder.append(this.mMccStr);
        stringBuilder.append(" mMnc=");
        stringBuilder.append(this.mMncStr);
        stringBuilder.append(" mAlphaLong=");
        stringBuilder.append(this.mAlphaLong);
        stringBuilder.append(" mAlphaShort=");
        stringBuilder.append(this.mAlphaShort);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        super.writeToParcel(parcel, 1);
        parcel.writeInt(this.mLac);
        parcel.writeInt(this.mCid);
        parcel.writeInt(this.mArfcn);
        parcel.writeInt(this.mBsic);
    }

}

