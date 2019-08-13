/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SystemApi
public final class ImsCallForwardInfo
implements Parcelable {
    public static final int CDIV_CF_REASON_ALL = 4;
    public static final int CDIV_CF_REASON_ALL_CONDITIONAL = 5;
    public static final int CDIV_CF_REASON_BUSY = 1;
    public static final int CDIV_CF_REASON_NOT_LOGGED_IN = 6;
    public static final int CDIV_CF_REASON_NOT_REACHABLE = 3;
    public static final int CDIV_CF_REASON_NO_REPLY = 2;
    public static final int CDIV_CF_REASON_UNCONDITIONAL = 0;
    public static final Parcelable.Creator<ImsCallForwardInfo> CREATOR = new Parcelable.Creator<ImsCallForwardInfo>(){

        @Override
        public ImsCallForwardInfo createFromParcel(Parcel parcel) {
            return new ImsCallForwardInfo(parcel);
        }

        public ImsCallForwardInfo[] newArray(int n) {
            return new ImsCallForwardInfo[n];
        }
    };
    public static final int STATUS_ACTIVE = 1;
    public static final int STATUS_NOT_ACTIVE = 0;
    public static final int TYPE_OF_ADDRESS_INTERNATIONAL = 145;
    public static final int TYPE_OF_ADDRESS_UNKNOWN = 129;
    @UnsupportedAppUsage
    public int mCondition;
    @UnsupportedAppUsage
    public String mNumber;
    @UnsupportedAppUsage
    public int mServiceClass;
    @UnsupportedAppUsage
    public int mStatus;
    @UnsupportedAppUsage
    public int mTimeSeconds;
    @UnsupportedAppUsage
    public int mToA;

    @UnsupportedAppUsage
    public ImsCallForwardInfo() {
    }

    public ImsCallForwardInfo(int n, int n2, int n3, int n4, String string2, int n5) {
        this.mCondition = n;
        this.mStatus = n2;
        this.mToA = n3;
        this.mServiceClass = n4;
        this.mNumber = string2;
        this.mTimeSeconds = n5;
    }

    public ImsCallForwardInfo(Parcel parcel) {
        this.readFromParcel(parcel);
    }

    private void readFromParcel(Parcel parcel) {
        this.mCondition = parcel.readInt();
        this.mStatus = parcel.readInt();
        this.mToA = parcel.readInt();
        this.mNumber = parcel.readString();
        this.mTimeSeconds = parcel.readInt();
        this.mServiceClass = parcel.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getCondition() {
        return this.mCondition;
    }

    public String getNumber() {
        return this.mNumber;
    }

    public int getServiceClass() {
        return this.mServiceClass;
    }

    public int getStatus() {
        return this.mStatus;
    }

    public int getTimeSeconds() {
        return this.mTimeSeconds;
    }

    public int getToA() {
        return this.mToA;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString());
        stringBuilder.append(", Condition: ");
        stringBuilder.append(this.mCondition);
        stringBuilder.append(", Status: ");
        String string2 = this.mStatus == 0 ? "disabled" : "enabled";
        stringBuilder.append(string2);
        stringBuilder.append(", ToA: ");
        stringBuilder.append(this.mToA);
        stringBuilder.append(", Service Class: ");
        stringBuilder.append(this.mServiceClass);
        stringBuilder.append(", Number=");
        stringBuilder.append(this.mNumber);
        stringBuilder.append(", Time (seconds): ");
        stringBuilder.append(this.mTimeSeconds);
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mCondition);
        parcel.writeInt(this.mStatus);
        parcel.writeInt(this.mToA);
        parcel.writeString(this.mNumber);
        parcel.writeInt(this.mTimeSeconds);
        parcel.writeInt(this.mServiceClass);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface CallForwardReasons {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface CallForwardStatus {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface TypeOfAddress {
    }

}

