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

public final class CellIdentityWcdma
extends CellIdentity {
    public static final Parcelable.Creator<CellIdentityWcdma> CREATOR;
    private static final boolean DBG = false;
    private static final int MAX_CID = 268435455;
    private static final int MAX_LAC = 65535;
    private static final int MAX_PSC = 511;
    private static final int MAX_UARFCN = 16383;
    private static final String TAG;
    private final int mCid;
    private final int mLac;
    private final int mPsc;
    @UnsupportedAppUsage
    private final int mUarfcn;

    static {
        TAG = CellIdentityWcdma.class.getSimpleName();
        CREATOR = new Parcelable.Creator<CellIdentityWcdma>(){

            @Override
            public CellIdentityWcdma createFromParcel(Parcel parcel) {
                parcel.readInt();
                return CellIdentityWcdma.createFromParcelBody(parcel);
            }

            public CellIdentityWcdma[] newArray(int n) {
                return new CellIdentityWcdma[n];
            }
        };
    }

    public CellIdentityWcdma() {
        super(TAG, 4, null, null, null, null);
        this.mLac = Integer.MAX_VALUE;
        this.mCid = Integer.MAX_VALUE;
        this.mPsc = Integer.MAX_VALUE;
        this.mUarfcn = Integer.MAX_VALUE;
    }

    public CellIdentityWcdma(int n, int n2, int n3, int n4, String string2, String string3, String string4, String string5) {
        super(TAG, 4, string2, string3, string4, string5);
        this.mLac = CellIdentityWcdma.inRangeOrUnavailable(n, 0, 65535);
        this.mCid = CellIdentityWcdma.inRangeOrUnavailable(n2, 0, 268435455);
        this.mPsc = CellIdentityWcdma.inRangeOrUnavailable(n3, 0, 511);
        this.mUarfcn = CellIdentityWcdma.inRangeOrUnavailable(n4, 0, 16383);
    }

    public CellIdentityWcdma(android.hardware.radio.V1_0.CellIdentityWcdma cellIdentityWcdma) {
        this(cellIdentityWcdma.lac, cellIdentityWcdma.cid, cellIdentityWcdma.psc, cellIdentityWcdma.uarfcn, cellIdentityWcdma.mcc, cellIdentityWcdma.mnc, "", "");
    }

    public CellIdentityWcdma(android.hardware.radio.V1_2.CellIdentityWcdma cellIdentityWcdma) {
        this(cellIdentityWcdma.base.lac, cellIdentityWcdma.base.cid, cellIdentityWcdma.base.psc, cellIdentityWcdma.base.uarfcn, cellIdentityWcdma.base.mcc, cellIdentityWcdma.base.mnc, cellIdentityWcdma.operatorNames.alphaLong, cellIdentityWcdma.operatorNames.alphaShort);
    }

    private CellIdentityWcdma(Parcel parcel) {
        super(TAG, 4, parcel);
        this.mLac = parcel.readInt();
        this.mCid = parcel.readInt();
        this.mPsc = parcel.readInt();
        this.mUarfcn = parcel.readInt();
    }

    private CellIdentityWcdma(CellIdentityWcdma cellIdentityWcdma) {
        this(cellIdentityWcdma.mLac, cellIdentityWcdma.mCid, cellIdentityWcdma.mPsc, cellIdentityWcdma.mUarfcn, cellIdentityWcdma.mMccStr, cellIdentityWcdma.mMncStr, cellIdentityWcdma.mAlphaLong, cellIdentityWcdma.mAlphaShort);
    }

    protected static CellIdentityWcdma createFromParcelBody(Parcel parcel) {
        return new CellIdentityWcdma(parcel);
    }

    @Override
    public GsmCellLocation asCellLocation() {
        GsmCellLocation gsmCellLocation = new GsmCellLocation();
        int n = this.mLac;
        int n2 = -1;
        if (n == Integer.MAX_VALUE) {
            n = -1;
        }
        int n3 = this.mCid;
        if (n3 == Integer.MAX_VALUE) {
            n3 = -1;
        }
        int n4 = this.mPsc;
        if (n4 != Integer.MAX_VALUE) {
            n2 = n4;
        }
        gsmCellLocation.setLacAndCid(n, n3);
        gsmCellLocation.setPsc(n2);
        return gsmCellLocation;
    }

    CellIdentityWcdma copy() {
        return new CellIdentityWcdma(this);
    }

    @Override
    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (!(object instanceof CellIdentityWcdma)) {
            return false;
        }
        CellIdentityWcdma cellIdentityWcdma = (CellIdentityWcdma)object;
        if (!(this.mLac == cellIdentityWcdma.mLac && this.mCid == cellIdentityWcdma.mCid && this.mPsc == cellIdentityWcdma.mPsc && this.mUarfcn == cellIdentityWcdma.mUarfcn && TextUtils.equals(this.mMccStr, cellIdentityWcdma.mMccStr) && TextUtils.equals(this.mMncStr, cellIdentityWcdma.mMncStr) && super.equals(object))) {
            bl = false;
        }
        return bl;
    }

    @Override
    public int getChannelNumber() {
        return this.mUarfcn;
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

    public int getPsc() {
        return this.mPsc;
    }

    public int getUarfcn() {
        return this.mUarfcn;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.mLac, this.mCid, this.mPsc, super.hashCode());
    }

    public CellIdentityWcdma sanitizeLocationInfo() {
        return new CellIdentityWcdma(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, this.mMccStr, this.mMncStr, this.mAlphaLong, this.mAlphaShort);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(TAG);
        stringBuilder.append(":{ mLac=");
        stringBuilder.append(this.mLac);
        stringBuilder.append(" mCid=");
        stringBuilder.append(this.mCid);
        stringBuilder.append(" mPsc=");
        stringBuilder.append(this.mPsc);
        stringBuilder.append(" mUarfcn=");
        stringBuilder.append(this.mUarfcn);
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
        super.writeToParcel(parcel, 4);
        parcel.writeInt(this.mLac);
        parcel.writeInt(this.mCid);
        parcel.writeInt(this.mPsc);
        parcel.writeInt(this.mUarfcn);
    }

}

