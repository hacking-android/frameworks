/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.cdma;

import android.os.Parcel;
import android.os.Parcelable;

public class CdmaSmsCbProgramData
implements Parcelable {
    public static final int ALERT_OPTION_DEFAULT_ALERT = 1;
    public static final int ALERT_OPTION_HIGH_PRIORITY_ONCE = 10;
    public static final int ALERT_OPTION_HIGH_PRIORITY_REPEAT = 11;
    public static final int ALERT_OPTION_LOW_PRIORITY_ONCE = 6;
    public static final int ALERT_OPTION_LOW_PRIORITY_REPEAT = 7;
    public static final int ALERT_OPTION_MED_PRIORITY_ONCE = 8;
    public static final int ALERT_OPTION_MED_PRIORITY_REPEAT = 9;
    public static final int ALERT_OPTION_NO_ALERT = 0;
    public static final int ALERT_OPTION_VIBRATE_ONCE = 2;
    public static final int ALERT_OPTION_VIBRATE_REPEAT = 3;
    public static final int ALERT_OPTION_VISUAL_ONCE = 4;
    public static final int ALERT_OPTION_VISUAL_REPEAT = 5;
    public static final Parcelable.Creator<CdmaSmsCbProgramData> CREATOR = new Parcelable.Creator<CdmaSmsCbProgramData>(){

        @Override
        public CdmaSmsCbProgramData createFromParcel(Parcel parcel) {
            return new CdmaSmsCbProgramData(parcel);
        }

        public CdmaSmsCbProgramData[] newArray(int n) {
            return new CdmaSmsCbProgramData[n];
        }
    };
    public static final int OPERATION_ADD_CATEGORY = 1;
    public static final int OPERATION_CLEAR_CATEGORIES = 2;
    public static final int OPERATION_DELETE_CATEGORY = 0;
    private final int mAlertOption;
    private final int mCategory;
    private final String mCategoryName;
    private final int mLanguage;
    private final int mMaxMessages;
    private final int mOperation;

    public CdmaSmsCbProgramData(int n, int n2, int n3, int n4, int n5, String string2) {
        this.mOperation = n;
        this.mCategory = n2;
        this.mLanguage = n3;
        this.mMaxMessages = n4;
        this.mAlertOption = n5;
        this.mCategoryName = string2;
    }

    CdmaSmsCbProgramData(Parcel parcel) {
        this.mOperation = parcel.readInt();
        this.mCategory = parcel.readInt();
        this.mLanguage = parcel.readInt();
        this.mMaxMessages = parcel.readInt();
        this.mAlertOption = parcel.readInt();
        this.mCategoryName = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getAlertOption() {
        return this.mAlertOption;
    }

    public int getCategory() {
        return this.mCategory;
    }

    public String getCategoryName() {
        return this.mCategoryName;
    }

    public int getLanguage() {
        return this.mLanguage;
    }

    public int getMaxMessages() {
        return this.mMaxMessages;
    }

    public int getOperation() {
        return this.mOperation;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CdmaSmsCbProgramData{operation=");
        stringBuilder.append(this.mOperation);
        stringBuilder.append(", category=");
        stringBuilder.append(this.mCategory);
        stringBuilder.append(", language=");
        stringBuilder.append(this.mLanguage);
        stringBuilder.append(", max messages=");
        stringBuilder.append(this.mMaxMessages);
        stringBuilder.append(", alert option=");
        stringBuilder.append(this.mAlertOption);
        stringBuilder.append(", category name=");
        stringBuilder.append(this.mCategoryName);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mOperation);
        parcel.writeInt(this.mCategory);
        parcel.writeInt(this.mLanguage);
        parcel.writeInt(this.mMaxMessages);
        parcel.writeInt(this.mAlertOption);
        parcel.writeString(this.mCategoryName);
    }

}

