/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.cdma;

import android.os.Parcel;
import android.os.Parcelable;

public class CdmaSmsCbProgramResults
implements Parcelable {
    public static final Parcelable.Creator<CdmaSmsCbProgramResults> CREATOR = new Parcelable.Creator<CdmaSmsCbProgramResults>(){

        @Override
        public CdmaSmsCbProgramResults createFromParcel(Parcel parcel) {
            return new CdmaSmsCbProgramResults(parcel);
        }

        public CdmaSmsCbProgramResults[] newArray(int n) {
            return new CdmaSmsCbProgramResults[n];
        }
    };
    public static final int RESULT_CATEGORY_ALREADY_ADDED = 3;
    public static final int RESULT_CATEGORY_ALREADY_DELETED = 4;
    public static final int RESULT_CATEGORY_LIMIT_EXCEEDED = 2;
    public static final int RESULT_INVALID_ALERT_OPTION = 6;
    public static final int RESULT_INVALID_CATEGORY_NAME = 7;
    public static final int RESULT_INVALID_MAX_MESSAGES = 5;
    public static final int RESULT_MEMORY_LIMIT_EXCEEDED = 1;
    public static final int RESULT_SUCCESS = 0;
    public static final int RESULT_UNSPECIFIED_FAILURE = 8;
    private final int mCategory;
    private final int mCategoryResult;
    private final int mLanguage;

    public CdmaSmsCbProgramResults(int n, int n2, int n3) {
        this.mCategory = n;
        this.mLanguage = n2;
        this.mCategoryResult = n3;
    }

    CdmaSmsCbProgramResults(Parcel parcel) {
        this.mCategory = parcel.readInt();
        this.mLanguage = parcel.readInt();
        this.mCategoryResult = parcel.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getCategory() {
        return this.mCategory;
    }

    public int getCategoryResult() {
        return this.mCategoryResult;
    }

    public int getLanguage() {
        return this.mLanguage;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CdmaSmsCbProgramResults{category=");
        stringBuilder.append(this.mCategory);
        stringBuilder.append(", language=");
        stringBuilder.append(this.mLanguage);
        stringBuilder.append(", result=");
        stringBuilder.append(this.mCategoryResult);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mCategory);
        parcel.writeInt(this.mLanguage);
        parcel.writeInt(this.mCategoryResult);
    }

}

