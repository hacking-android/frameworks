/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.AnomalyReporter;
import android.telephony.CellIdentityCdma;
import android.telephony.CellIdentityGsm;
import android.telephony.CellIdentityLte;
import android.telephony.CellIdentityNr;
import android.telephony.CellIdentityTdscdma;
import android.telephony.CellIdentityWcdma;
import android.telephony.CellLocation;
import android.telephony.Rlog;
import android.text.TextUtils;
import java.util.Objects;
import java.util.UUID;

public abstract class CellIdentity
implements Parcelable {
    public static final Parcelable.Creator<CellIdentity> CREATOR = new Parcelable.Creator<CellIdentity>(){

        @Override
        public CellIdentity createFromParcel(Parcel parcel) {
            switch (parcel.readInt()) {
                default: {
                    throw new IllegalArgumentException("Bad Cell identity Parcel");
                }
                case 6: {
                    return CellIdentityNr.createFromParcelBody(parcel);
                }
                case 5: {
                    return CellIdentityTdscdma.createFromParcelBody(parcel);
                }
                case 4: {
                    return CellIdentityWcdma.createFromParcelBody(parcel);
                }
                case 3: {
                    return CellIdentityLte.createFromParcelBody(parcel);
                }
                case 2: {
                    return CellIdentityCdma.createFromParcelBody(parcel);
                }
                case 1: 
            }
            return CellIdentityGsm.createFromParcelBody(parcel);
        }

        public CellIdentity[] newArray(int n) {
            return new CellIdentity[n];
        }
    };
    public static final int INVALID_CHANNEL_NUMBER = -1;
    protected String mAlphaLong;
    protected String mAlphaShort;
    protected final String mMccStr;
    protected final String mMncStr;
    protected final String mTag;
    protected final int mType;

    protected CellIdentity(String string2, int n, Parcel parcel) {
        this(string2, n, parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString());
    }

    protected CellIdentity(String charSequence, int n, String string2, String string3, String string4, String string5) {
        this.mTag = charSequence;
        this.mType = n;
        if (string2 != null && !string2.matches("^[0-9]{3}$")) {
            if (!string2.isEmpty() && !string2.equals(String.valueOf(Integer.MAX_VALUE))) {
                this.mMccStr = null;
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("invalid MCC format: ");
                ((StringBuilder)charSequence).append(string2);
                this.log(((StringBuilder)charSequence).toString());
            } else {
                this.mMccStr = null;
            }
        } else {
            this.mMccStr = string2;
        }
        if (string3 != null && !string3.matches("^[0-9]{2,3}$")) {
            if (!string3.isEmpty() && !string3.equals(String.valueOf(Integer.MAX_VALUE))) {
                this.mMncStr = null;
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("invalid MNC format: ");
                ((StringBuilder)charSequence).append(string3);
                this.log(((StringBuilder)charSequence).toString());
            } else {
                this.mMncStr = null;
            }
        } else {
            this.mMncStr = string3;
        }
        if (this.mMccStr != null && this.mMncStr == null || this.mMccStr == null && this.mMncStr != null) {
            AnomalyReporter.reportAnomaly(UUID.fromString("a3ab0b9d-f2aa-4baf-911d-7096c0d4645a"), "CellIdentity Missing Half of PLMN ID");
        }
        this.mAlphaLong = string4;
        this.mAlphaShort = string5;
    }

    protected static final int inRangeOrUnavailable(int n, int n2, int n3) {
        if (n >= n2 && n <= n3) {
            return n;
        }
        return Integer.MAX_VALUE;
    }

    protected static final int inRangeOrUnavailable(int n, int n2, int n3, int n4) {
        if ((n < n2 || n > n3) && n != n4) {
            return Integer.MAX_VALUE;
        }
        return n;
    }

    protected static final long inRangeOrUnavailable(long l, long l2, long l3) {
        if (l >= l2 && l <= l3) {
            return l;
        }
        return Long.MAX_VALUE;
    }

    public abstract CellLocation asCellLocation();

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl;
        block1 : {
            boolean bl2 = object instanceof CellIdentity;
            bl = false;
            if (!bl2) {
                return false;
            }
            object = (CellIdentity)object;
            if (this.mType != ((CellIdentity)object).mType || !TextUtils.equals(this.mMccStr, ((CellIdentity)object).mMccStr) || !TextUtils.equals(this.mMncStr, ((CellIdentity)object).mMncStr) || !TextUtils.equals(this.mAlphaLong, ((CellIdentity)object).mAlphaLong) || !TextUtils.equals(this.mAlphaShort, ((CellIdentity)object).mAlphaShort)) break block1;
            bl = true;
        }
        return bl;
    }

    public int getChannelNumber() {
        return -1;
    }

    public String getMccString() {
        return this.mMccStr;
    }

    public String getMncString() {
        return this.mMncStr;
    }

    public CharSequence getOperatorAlphaLong() {
        return this.mAlphaLong;
    }

    public CharSequence getOperatorAlphaShort() {
        return this.mAlphaShort;
    }

    public int getType() {
        return this.mType;
    }

    public int hashCode() {
        return Objects.hash(this.mAlphaLong, this.mAlphaShort, this.mMccStr, this.mMncStr, this.mType);
    }

    protected void log(String string2) {
        Rlog.w(this.mTag, string2);
    }

    public void setOperatorAlphaLong(String string2) {
        this.mAlphaLong = string2;
    }

    public void setOperatorAlphaShort(String string2) {
        this.mAlphaShort = string2;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(n);
        parcel.writeString(this.mMccStr);
        parcel.writeString(this.mMncStr);
        parcel.writeString(this.mAlphaLong);
        parcel.writeString(this.mAlphaShort);
    }

}

