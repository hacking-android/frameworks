/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.hardware.radio.V1_2.CellIdentityOperatorNames;
import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.CellIdentity;
import android.telephony.CellLocation;
import android.telephony.gsm.GsmCellLocation;
import java.util.Objects;

public final class CellIdentityTdscdma
extends CellIdentity {
    public static final Parcelable.Creator<CellIdentityTdscdma> CREATOR;
    private static final boolean DBG = false;
    private static final int MAX_CID = 268435455;
    private static final int MAX_CPID = 127;
    private static final int MAX_LAC = 65535;
    private static final int MAX_UARFCN = 65535;
    private static final String TAG;
    private final int mCid;
    private final int mCpid;
    private final int mLac;
    private final int mUarfcn;

    static {
        TAG = CellIdentityTdscdma.class.getSimpleName();
        CREATOR = new Parcelable.Creator<CellIdentityTdscdma>(){

            @Override
            public CellIdentityTdscdma createFromParcel(Parcel parcel) {
                parcel.readInt();
                return CellIdentityTdscdma.createFromParcelBody(parcel);
            }

            public CellIdentityTdscdma[] newArray(int n) {
                return new CellIdentityTdscdma[n];
            }
        };
    }

    public CellIdentityTdscdma() {
        super(TAG, 5, null, null, null, null);
        this.mLac = Integer.MAX_VALUE;
        this.mCid = Integer.MAX_VALUE;
        this.mCpid = Integer.MAX_VALUE;
        this.mUarfcn = Integer.MAX_VALUE;
    }

    public CellIdentityTdscdma(android.hardware.radio.V1_0.CellIdentityTdscdma cellIdentityTdscdma) {
        this(cellIdentityTdscdma.mcc, cellIdentityTdscdma.mnc, cellIdentityTdscdma.lac, cellIdentityTdscdma.cid, cellIdentityTdscdma.cpid, Integer.MAX_VALUE, "", "");
    }

    public CellIdentityTdscdma(android.hardware.radio.V1_2.CellIdentityTdscdma cellIdentityTdscdma) {
        this(cellIdentityTdscdma.base.mcc, cellIdentityTdscdma.base.mnc, cellIdentityTdscdma.base.lac, cellIdentityTdscdma.base.cid, cellIdentityTdscdma.base.cpid, cellIdentityTdscdma.uarfcn, cellIdentityTdscdma.operatorNames.alphaLong, cellIdentityTdscdma.operatorNames.alphaShort);
    }

    private CellIdentityTdscdma(Parcel parcel) {
        super(TAG, 5, parcel);
        this.mLac = parcel.readInt();
        this.mCid = parcel.readInt();
        this.mCpid = parcel.readInt();
        this.mUarfcn = parcel.readInt();
    }

    private CellIdentityTdscdma(CellIdentityTdscdma cellIdentityTdscdma) {
        this(cellIdentityTdscdma.mMccStr, cellIdentityTdscdma.mMncStr, cellIdentityTdscdma.mLac, cellIdentityTdscdma.mCid, cellIdentityTdscdma.mCpid, cellIdentityTdscdma.mUarfcn, cellIdentityTdscdma.mAlphaLong, cellIdentityTdscdma.mAlphaShort);
    }

    public CellIdentityTdscdma(String string2, String string3, int n, int n2, int n3, int n4, String string4, String string5) {
        super(TAG, 5, string2, string3, string4, string5);
        this.mLac = CellIdentityTdscdma.inRangeOrUnavailable(n, 0, 65535);
        this.mCid = CellIdentityTdscdma.inRangeOrUnavailable(n2, 0, 268435455);
        this.mCpid = CellIdentityTdscdma.inRangeOrUnavailable(n3, 0, 127);
        this.mUarfcn = CellIdentityTdscdma.inRangeOrUnavailable(n4, 0, 65535);
    }

    protected static CellIdentityTdscdma createFromParcelBody(Parcel parcel) {
        return new CellIdentityTdscdma(parcel);
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

    CellIdentityTdscdma copy() {
        return new CellIdentityTdscdma(this);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (!(object instanceof CellIdentityTdscdma)) {
            return false;
        }
        CellIdentityTdscdma cellIdentityTdscdma = (CellIdentityTdscdma)object;
        if (this.mLac != cellIdentityTdscdma.mLac || this.mCid != cellIdentityTdscdma.mCid || this.mCpid != cellIdentityTdscdma.mCpid || this.mUarfcn != cellIdentityTdscdma.mUarfcn || !super.equals(object)) {
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

    public int getCpid() {
        return this.mCpid;
    }

    public int getLac() {
        return this.mLac;
    }

    @Override
    public String getMccString() {
        return this.mMccStr;
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

    public int getUarfcn() {
        return this.mUarfcn;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.mLac, this.mCid, this.mCpid, this.mUarfcn, super.hashCode());
    }

    public CellIdentityTdscdma sanitizeLocationInfo() {
        return new CellIdentityTdscdma(this.mMccStr, this.mMncStr, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, this.mAlphaLong, this.mAlphaShort);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(TAG);
        stringBuilder.append(":{ mMcc=");
        stringBuilder.append(this.mMccStr);
        stringBuilder.append(" mMnc=");
        stringBuilder.append(this.mMncStr);
        stringBuilder.append(" mAlphaLong=");
        stringBuilder.append(this.mAlphaLong);
        stringBuilder.append(" mAlphaShort=");
        stringBuilder.append(this.mAlphaShort);
        stringBuilder.append(" mLac=");
        stringBuilder.append(this.mLac);
        stringBuilder.append(" mCid=");
        stringBuilder.append(this.mCid);
        stringBuilder.append(" mCpid=");
        stringBuilder.append(this.mCpid);
        stringBuilder.append(" mUarfcn=");
        stringBuilder.append(this.mUarfcn);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        super.writeToParcel(parcel, 5);
        parcel.writeInt(this.mLac);
        parcel.writeInt(this.mCid);
        parcel.writeInt(this.mCpid);
        parcel.writeInt(this.mUarfcn);
    }

}

