/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.SmsCbCmasInfo;
import android.telephony.SmsCbEtwsInfo;
import android.telephony.SmsCbLocation;

public class SmsCbMessage
implements Parcelable {
    public static final Parcelable.Creator<SmsCbMessage> CREATOR = new Parcelable.Creator<SmsCbMessage>(){

        @Override
        public SmsCbMessage createFromParcel(Parcel parcel) {
            return new SmsCbMessage(parcel);
        }

        public SmsCbMessage[] newArray(int n) {
            return new SmsCbMessage[n];
        }
    };
    public static final int GEOGRAPHICAL_SCOPE_CELL_WIDE = 3;
    public static final int GEOGRAPHICAL_SCOPE_CELL_WIDE_IMMEDIATE = 0;
    public static final int GEOGRAPHICAL_SCOPE_LA_WIDE = 2;
    public static final int GEOGRAPHICAL_SCOPE_PLMN_WIDE = 1;
    protected static final String LOG_TAG = "SMSCB";
    public static final int MESSAGE_FORMAT_3GPP = 1;
    public static final int MESSAGE_FORMAT_3GPP2 = 2;
    public static final int MESSAGE_PRIORITY_EMERGENCY = 3;
    public static final int MESSAGE_PRIORITY_INTERACTIVE = 1;
    public static final int MESSAGE_PRIORITY_NORMAL = 0;
    public static final int MESSAGE_PRIORITY_URGENT = 2;
    private final String mBody;
    private final SmsCbCmasInfo mCmasWarningInfo;
    private final SmsCbEtwsInfo mEtwsWarningInfo;
    private final int mGeographicalScope;
    private final String mLanguage;
    private final SmsCbLocation mLocation;
    private final int mMessageFormat;
    private final int mPriority;
    private final int mSerialNumber;
    private final int mServiceCategory;

    public SmsCbMessage(int n, int n2, int n3, SmsCbLocation smsCbLocation, int n4, String string2, String string3, int n5, SmsCbEtwsInfo smsCbEtwsInfo, SmsCbCmasInfo smsCbCmasInfo) {
        this.mMessageFormat = n;
        this.mGeographicalScope = n2;
        this.mSerialNumber = n3;
        this.mLocation = smsCbLocation;
        this.mServiceCategory = n4;
        this.mLanguage = string2;
        this.mBody = string3;
        this.mPriority = n5;
        this.mEtwsWarningInfo = smsCbEtwsInfo;
        this.mCmasWarningInfo = smsCbCmasInfo;
    }

    public SmsCbMessage(Parcel parcel) {
        this.mMessageFormat = parcel.readInt();
        this.mGeographicalScope = parcel.readInt();
        this.mSerialNumber = parcel.readInt();
        this.mLocation = new SmsCbLocation(parcel);
        this.mServiceCategory = parcel.readInt();
        this.mLanguage = parcel.readString();
        this.mBody = parcel.readString();
        this.mPriority = parcel.readInt();
        int n = parcel.readInt();
        if (n != 67) {
            if (n != 69) {
                this.mEtwsWarningInfo = null;
                this.mCmasWarningInfo = null;
            } else {
                this.mEtwsWarningInfo = new SmsCbEtwsInfo(parcel);
                this.mCmasWarningInfo = null;
            }
        } else {
            this.mEtwsWarningInfo = null;
            this.mCmasWarningInfo = new SmsCbCmasInfo(parcel);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public SmsCbCmasInfo getCmasWarningInfo() {
        return this.mCmasWarningInfo;
    }

    public SmsCbEtwsInfo getEtwsWarningInfo() {
        return this.mEtwsWarningInfo;
    }

    public int getGeographicalScope() {
        return this.mGeographicalScope;
    }

    public String getLanguageCode() {
        return this.mLanguage;
    }

    public SmsCbLocation getLocation() {
        return this.mLocation;
    }

    public String getMessageBody() {
        return this.mBody;
    }

    public int getMessageFormat() {
        return this.mMessageFormat;
    }

    public int getMessagePriority() {
        return this.mPriority;
    }

    public int getSerialNumber() {
        return this.mSerialNumber;
    }

    public int getServiceCategory() {
        return this.mServiceCategory;
    }

    public boolean isCmasMessage() {
        boolean bl = this.mCmasWarningInfo != null;
        return bl;
    }

    public boolean isEmergencyMessage() {
        boolean bl = this.mPriority == 3;
        return bl;
    }

    public boolean isEtwsMessage() {
        boolean bl = this.mEtwsWarningInfo != null;
        return bl;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SmsCbMessage{geographicalScope=");
        stringBuilder.append(this.mGeographicalScope);
        stringBuilder.append(", serialNumber=");
        stringBuilder.append(this.mSerialNumber);
        stringBuilder.append(", location=");
        stringBuilder.append(this.mLocation);
        stringBuilder.append(", serviceCategory=");
        stringBuilder.append(this.mServiceCategory);
        stringBuilder.append(", language=");
        stringBuilder.append(this.mLanguage);
        stringBuilder.append(", body=");
        stringBuilder.append(this.mBody);
        stringBuilder.append(", priority=");
        stringBuilder.append(this.mPriority);
        Object object = this.mEtwsWarningInfo;
        String string2 = "";
        if (object != null) {
            object = new StringBuilder();
            ((StringBuilder)object).append(", ");
            ((StringBuilder)object).append(this.mEtwsWarningInfo.toString());
            object = ((StringBuilder)object).toString();
        } else {
            object = "";
        }
        stringBuilder.append((String)object);
        object = string2;
        if (this.mCmasWarningInfo != null) {
            object = new StringBuilder();
            ((StringBuilder)object).append(", ");
            ((StringBuilder)object).append(this.mCmasWarningInfo.toString());
            object = ((StringBuilder)object).toString();
        }
        stringBuilder.append((String)object);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mMessageFormat);
        parcel.writeInt(this.mGeographicalScope);
        parcel.writeInt(this.mSerialNumber);
        this.mLocation.writeToParcel(parcel, n);
        parcel.writeInt(this.mServiceCategory);
        parcel.writeString(this.mLanguage);
        parcel.writeString(this.mBody);
        parcel.writeInt(this.mPriority);
        if (this.mEtwsWarningInfo != null) {
            parcel.writeInt(69);
            this.mEtwsWarningInfo.writeToParcel(parcel, n);
        } else if (this.mCmasWarningInfo != null) {
            parcel.writeInt(67);
            this.mCmasWarningInfo.writeToParcel(parcel, n);
        } else {
            parcel.writeInt(48);
        }
    }

}

