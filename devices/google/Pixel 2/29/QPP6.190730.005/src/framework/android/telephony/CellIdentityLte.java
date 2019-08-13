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

public final class CellIdentityLte
extends CellIdentity {
    public static final Parcelable.Creator<CellIdentityLte> CREATOR;
    private static final boolean DBG = false;
    private static final int MAX_BANDWIDTH = 20000;
    private static final int MAX_CI = 268435455;
    private static final int MAX_EARFCN = 262143;
    private static final int MAX_PCI = 503;
    private static final int MAX_TAC = 65535;
    private static final String TAG;
    private final int mBandwidth;
    private final int mCi;
    private final int mEarfcn;
    private final int mPci;
    private final int mTac;

    static {
        TAG = CellIdentityLte.class.getSimpleName();
        CREATOR = new Parcelable.Creator<CellIdentityLte>(){

            @Override
            public CellIdentityLte createFromParcel(Parcel parcel) {
                parcel.readInt();
                return CellIdentityLte.createFromParcelBody(parcel);
            }

            public CellIdentityLte[] newArray(int n) {
                return new CellIdentityLte[n];
            }
        };
    }

    @UnsupportedAppUsage
    public CellIdentityLte() {
        super(TAG, 3, null, null, null, null);
        this.mCi = Integer.MAX_VALUE;
        this.mPci = Integer.MAX_VALUE;
        this.mTac = Integer.MAX_VALUE;
        this.mEarfcn = Integer.MAX_VALUE;
        this.mBandwidth = Integer.MAX_VALUE;
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public CellIdentityLte(int n, int n2, int n3, int n4, int n5) {
        this(n3, n4, n5, Integer.MAX_VALUE, Integer.MAX_VALUE, String.valueOf(n), String.valueOf(n2), null, null);
    }

    public CellIdentityLte(int n, int n2, int n3, int n4, int n5, String string2, String string3, String string4, String string5) {
        super(TAG, 3, string2, string3, string4, string5);
        this.mCi = CellIdentityLte.inRangeOrUnavailable(n, 0, 268435455);
        this.mPci = CellIdentityLte.inRangeOrUnavailable(n2, 0, 503);
        this.mTac = CellIdentityLte.inRangeOrUnavailable(n3, 0, 65535);
        this.mEarfcn = CellIdentityLte.inRangeOrUnavailable(n4, 0, 262143);
        this.mBandwidth = CellIdentityLte.inRangeOrUnavailable(n5, 0, 20000);
    }

    public CellIdentityLte(android.hardware.radio.V1_0.CellIdentityLte cellIdentityLte) {
        this(cellIdentityLte.ci, cellIdentityLte.pci, cellIdentityLte.tac, cellIdentityLte.earfcn, Integer.MAX_VALUE, cellIdentityLte.mcc, cellIdentityLte.mnc, "", "");
    }

    public CellIdentityLte(android.hardware.radio.V1_2.CellIdentityLte cellIdentityLte) {
        this(cellIdentityLte.base.ci, cellIdentityLte.base.pci, cellIdentityLte.base.tac, cellIdentityLte.base.earfcn, cellIdentityLte.bandwidth, cellIdentityLte.base.mcc, cellIdentityLte.base.mnc, cellIdentityLte.operatorNames.alphaLong, cellIdentityLte.operatorNames.alphaShort);
    }

    private CellIdentityLte(Parcel parcel) {
        super(TAG, 3, parcel);
        this.mCi = parcel.readInt();
        this.mPci = parcel.readInt();
        this.mTac = parcel.readInt();
        this.mEarfcn = parcel.readInt();
        this.mBandwidth = parcel.readInt();
    }

    private CellIdentityLte(CellIdentityLte cellIdentityLte) {
        this(cellIdentityLte.mCi, cellIdentityLte.mPci, cellIdentityLte.mTac, cellIdentityLte.mEarfcn, cellIdentityLte.mBandwidth, cellIdentityLte.mMccStr, cellIdentityLte.mMncStr, cellIdentityLte.mAlphaLong, cellIdentityLte.mAlphaShort);
    }

    protected static CellIdentityLte createFromParcelBody(Parcel parcel) {
        return new CellIdentityLte(parcel);
    }

    @Override
    public GsmCellLocation asCellLocation() {
        GsmCellLocation gsmCellLocation = new GsmCellLocation();
        int n = this.mTac;
        int n2 = -1;
        if (n == Integer.MAX_VALUE) {
            n = -1;
        }
        int n3 = this.mCi;
        if (n3 != Integer.MAX_VALUE) {
            n2 = n3;
        }
        gsmCellLocation.setLacAndCid(n, n2);
        gsmCellLocation.setPsc(0);
        return gsmCellLocation;
    }

    CellIdentityLte copy() {
        return new CellIdentityLte(this);
    }

    @Override
    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (!(object instanceof CellIdentityLte)) {
            return false;
        }
        CellIdentityLte cellIdentityLte = (CellIdentityLte)object;
        if (!(this.mCi == cellIdentityLte.mCi && this.mPci == cellIdentityLte.mPci && this.mTac == cellIdentityLte.mTac && this.mEarfcn == cellIdentityLte.mEarfcn && this.mBandwidth == cellIdentityLte.mBandwidth && TextUtils.equals(this.mMccStr, cellIdentityLte.mMccStr) && TextUtils.equals(this.mMncStr, cellIdentityLte.mMncStr) && super.equals(object))) {
            bl = false;
        }
        return bl;
    }

    public int getBandwidth() {
        return this.mBandwidth;
    }

    @Override
    public int getChannelNumber() {
        return this.mEarfcn;
    }

    public int getCi() {
        return this.mCi;
    }

    public int getEarfcn() {
        return this.mEarfcn;
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

    public int getPci() {
        return this.mPci;
    }

    public int getTac() {
        return this.mTac;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.mCi, this.mPci, this.mTac, super.hashCode());
    }

    public CellIdentityLte sanitizeLocationInfo() {
        return new CellIdentityLte(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, this.mMccStr, this.mMncStr, this.mAlphaLong, this.mAlphaShort);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(TAG);
        stringBuilder.append(":{ mCi=");
        stringBuilder.append(this.mCi);
        stringBuilder.append(" mPci=");
        stringBuilder.append(this.mPci);
        stringBuilder.append(" mTac=");
        stringBuilder.append(this.mTac);
        stringBuilder.append(" mEarfcn=");
        stringBuilder.append(this.mEarfcn);
        stringBuilder.append(" mBandwidth=");
        stringBuilder.append(this.mBandwidth);
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
        super.writeToParcel(parcel, 3);
        parcel.writeInt(this.mCi);
        parcel.writeInt(this.mPci);
        parcel.writeInt(this.mTac);
        parcel.writeInt(this.mEarfcn);
        parcel.writeInt(this.mBandwidth);
    }

}

