/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.os.Parcel;
import android.os.Parcelable;

public class SmsCbCmasInfo
implements Parcelable {
    public static final int CMAS_CATEGORY_CBRNE = 10;
    public static final int CMAS_CATEGORY_ENV = 7;
    public static final int CMAS_CATEGORY_FIRE = 5;
    public static final int CMAS_CATEGORY_GEO = 0;
    public static final int CMAS_CATEGORY_HEALTH = 6;
    public static final int CMAS_CATEGORY_INFRA = 9;
    public static final int CMAS_CATEGORY_MET = 1;
    public static final int CMAS_CATEGORY_OTHER = 11;
    public static final int CMAS_CATEGORY_RESCUE = 4;
    public static final int CMAS_CATEGORY_SAFETY = 2;
    public static final int CMAS_CATEGORY_SECURITY = 3;
    public static final int CMAS_CATEGORY_TRANSPORT = 8;
    public static final int CMAS_CATEGORY_UNKNOWN = -1;
    public static final int CMAS_CERTAINTY_LIKELY = 1;
    public static final int CMAS_CERTAINTY_OBSERVED = 0;
    public static final int CMAS_CERTAINTY_UNKNOWN = -1;
    public static final int CMAS_CLASS_CHILD_ABDUCTION_EMERGENCY = 3;
    public static final int CMAS_CLASS_CMAS_EXERCISE = 5;
    public static final int CMAS_CLASS_EXTREME_THREAT = 1;
    public static final int CMAS_CLASS_OPERATOR_DEFINED_USE = 6;
    public static final int CMAS_CLASS_PRESIDENTIAL_LEVEL_ALERT = 0;
    public static final int CMAS_CLASS_REQUIRED_MONTHLY_TEST = 4;
    public static final int CMAS_CLASS_SEVERE_THREAT = 2;
    public static final int CMAS_CLASS_UNKNOWN = -1;
    public static final int CMAS_RESPONSE_TYPE_ASSESS = 6;
    public static final int CMAS_RESPONSE_TYPE_AVOID = 5;
    public static final int CMAS_RESPONSE_TYPE_EVACUATE = 1;
    public static final int CMAS_RESPONSE_TYPE_EXECUTE = 3;
    public static final int CMAS_RESPONSE_TYPE_MONITOR = 4;
    public static final int CMAS_RESPONSE_TYPE_NONE = 7;
    public static final int CMAS_RESPONSE_TYPE_PREPARE = 2;
    public static final int CMAS_RESPONSE_TYPE_SHELTER = 0;
    public static final int CMAS_RESPONSE_TYPE_UNKNOWN = -1;
    public static final int CMAS_SEVERITY_EXTREME = 0;
    public static final int CMAS_SEVERITY_SEVERE = 1;
    public static final int CMAS_SEVERITY_UNKNOWN = -1;
    public static final int CMAS_URGENCY_EXPECTED = 1;
    public static final int CMAS_URGENCY_IMMEDIATE = 0;
    public static final int CMAS_URGENCY_UNKNOWN = -1;
    public static final Parcelable.Creator<SmsCbCmasInfo> CREATOR = new Parcelable.Creator<SmsCbCmasInfo>(){

        @Override
        public SmsCbCmasInfo createFromParcel(Parcel parcel) {
            return new SmsCbCmasInfo(parcel);
        }

        public SmsCbCmasInfo[] newArray(int n) {
            return new SmsCbCmasInfo[n];
        }
    };
    private final int mCategory;
    private final int mCertainty;
    private final int mMessageClass;
    private final int mResponseType;
    private final int mSeverity;
    private final int mUrgency;

    public SmsCbCmasInfo(int n, int n2, int n3, int n4, int n5, int n6) {
        this.mMessageClass = n;
        this.mCategory = n2;
        this.mResponseType = n3;
        this.mSeverity = n4;
        this.mUrgency = n5;
        this.mCertainty = n6;
    }

    SmsCbCmasInfo(Parcel parcel) {
        this.mMessageClass = parcel.readInt();
        this.mCategory = parcel.readInt();
        this.mResponseType = parcel.readInt();
        this.mSeverity = parcel.readInt();
        this.mUrgency = parcel.readInt();
        this.mCertainty = parcel.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getCategory() {
        return this.mCategory;
    }

    public int getCertainty() {
        return this.mCertainty;
    }

    public int getMessageClass() {
        return this.mMessageClass;
    }

    public int getResponseType() {
        return this.mResponseType;
    }

    public int getSeverity() {
        return this.mSeverity;
    }

    public int getUrgency() {
        return this.mUrgency;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SmsCbCmasInfo{messageClass=");
        stringBuilder.append(this.mMessageClass);
        stringBuilder.append(", category=");
        stringBuilder.append(this.mCategory);
        stringBuilder.append(", responseType=");
        stringBuilder.append(this.mResponseType);
        stringBuilder.append(", severity=");
        stringBuilder.append(this.mSeverity);
        stringBuilder.append(", urgency=");
        stringBuilder.append(this.mUrgency);
        stringBuilder.append(", certainty=");
        stringBuilder.append(this.mCertainty);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mMessageClass);
        parcel.writeInt(this.mCategory);
        parcel.writeInt(this.mResponseType);
        parcel.writeInt(this.mSeverity);
        parcel.writeInt(this.mUrgency);
        parcel.writeInt(this.mCertainty);
    }

}

