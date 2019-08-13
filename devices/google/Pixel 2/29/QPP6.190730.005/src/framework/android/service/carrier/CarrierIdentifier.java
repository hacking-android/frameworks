/*
 * Decompiled with CFR 0.145.
 */
package android.service.carrier;

import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.Rlog;
import com.android.internal.telephony.uicc.IccUtils;
import java.util.Objects;

public class CarrierIdentifier
implements Parcelable {
    public static final Parcelable.Creator<CarrierIdentifier> CREATOR = new Parcelable.Creator<CarrierIdentifier>(){

        @Override
        public CarrierIdentifier createFromParcel(Parcel parcel) {
            return new CarrierIdentifier(parcel);
        }

        public CarrierIdentifier[] newArray(int n) {
            return new CarrierIdentifier[n];
        }
    };
    private int mCarrierId = -1;
    private String mGid1;
    private String mGid2;
    private String mImsi;
    private String mMcc;
    private String mMnc;
    private int mSpecificCarrierId = -1;
    private String mSpn;

    public CarrierIdentifier(Parcel parcel) {
        this.readFromParcel(parcel);
    }

    public CarrierIdentifier(String string2, String string3, String string4, String string5, String string6, String string7) {
        this(string2, string3, string4, string5, string6, string7, -1, -1);
    }

    public CarrierIdentifier(String string2, String string3, String string4, String string5, String string6, String string7, int n, int n2) {
        this.mMcc = string2;
        this.mMnc = string3;
        this.mSpn = string4;
        this.mImsi = string5;
        this.mGid1 = string6;
        this.mGid2 = string7;
        this.mCarrierId = n;
        this.mSpecificCarrierId = n2;
    }

    public CarrierIdentifier(byte[] object, String charSequence, String string2) {
        if (((byte[])object).length == 3) {
            object = IccUtils.bytesToHexString((byte[])object);
            this.mMcc = new String(new char[]{((String)object).charAt(1), ((String)object).charAt(0), ((String)object).charAt(3)});
            this.mMnc = ((String)object).charAt(2) == 'F' ? new String(new char[]{((String)object).charAt(5), ((String)object).charAt(4)}) : new String(new char[]{((String)object).charAt(5), ((String)object).charAt(4), ((String)object).charAt(2)});
            this.mGid1 = charSequence;
            this.mGid2 = string2;
            this.mSpn = null;
            this.mImsi = null;
            return;
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("MCC & MNC must be set by a 3-byte array: byte[");
        ((StringBuilder)charSequence).append(((byte[])object).length);
        ((StringBuilder)charSequence).append("]");
        throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object != null && this.getClass() == object.getClass()) {
            object = (CarrierIdentifier)object;
            if (!(Objects.equals(this.mMcc, ((CarrierIdentifier)object).mMcc) && Objects.equals(this.mMnc, ((CarrierIdentifier)object).mMnc) && Objects.equals(this.mSpn, ((CarrierIdentifier)object).mSpn) && Objects.equals(this.mImsi, ((CarrierIdentifier)object).mImsi) && Objects.equals(this.mGid1, ((CarrierIdentifier)object).mGid1) && Objects.equals(this.mGid2, ((CarrierIdentifier)object).mGid2) && Objects.equals(this.mCarrierId, ((CarrierIdentifier)object).mCarrierId) && Objects.equals(this.mSpecificCarrierId, ((CarrierIdentifier)object).mSpecificCarrierId))) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    public int getCarrierId() {
        return this.mCarrierId;
    }

    public String getGid1() {
        return this.mGid1;
    }

    public String getGid2() {
        return this.mGid2;
    }

    public String getImsi() {
        return this.mImsi;
    }

    public String getMcc() {
        return this.mMcc;
    }

    public String getMnc() {
        return this.mMnc;
    }

    public int getSpecificCarrierId() {
        return this.mSpecificCarrierId;
    }

    public String getSpn() {
        return this.mSpn;
    }

    public int hashCode() {
        return Objects.hash(this.mMcc, this.mMnc, this.mSpn, this.mImsi, this.mGid1, this.mGid2, this.mCarrierId, this.mSpecificCarrierId);
    }

    public void readFromParcel(Parcel parcel) {
        this.mMcc = parcel.readString();
        this.mMnc = parcel.readString();
        this.mSpn = parcel.readString();
        this.mImsi = parcel.readString();
        this.mGid1 = parcel.readString();
        this.mGid2 = parcel.readString();
        this.mCarrierId = parcel.readInt();
        this.mSpecificCarrierId = parcel.readInt();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CarrierIdentifier{mcc=");
        stringBuilder.append(this.mMcc);
        stringBuilder.append(",mnc=");
        stringBuilder.append(this.mMnc);
        stringBuilder.append(",spn=");
        stringBuilder.append(this.mSpn);
        stringBuilder.append(",imsi=");
        stringBuilder.append(Rlog.pii(false, (Object)this.mImsi));
        stringBuilder.append(",gid1=");
        stringBuilder.append(this.mGid1);
        stringBuilder.append(",gid2=");
        stringBuilder.append(this.mGid2);
        stringBuilder.append(",carrierid=");
        stringBuilder.append(this.mCarrierId);
        stringBuilder.append(",specificCarrierId=");
        stringBuilder.append(this.mSpecificCarrierId);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mMcc);
        parcel.writeString(this.mMnc);
        parcel.writeString(this.mSpn);
        parcel.writeString(this.mImsi);
        parcel.writeString(this.mGid1);
        parcel.writeString(this.mGid2);
        parcel.writeInt(this.mCarrierId);
        parcel.writeInt(this.mSpecificCarrierId);
    }

    public static interface MatchType {
        public static final int ALL = 0;
        public static final int GID1 = 3;
        public static final int GID2 = 4;
        public static final int IMSI_PREFIX = 2;
        public static final int SPN = 1;
    }

}

