/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.annotation.UnsupportedAppUsage;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

public class ResultInfo
implements Parcelable {
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public static final Parcelable.Creator<ResultInfo> CREATOR = new Parcelable.Creator<ResultInfo>(){

        @Override
        public ResultInfo createFromParcel(Parcel parcel) {
            return new ResultInfo(parcel);
        }

        public ResultInfo[] newArray(int n) {
            return new ResultInfo[n];
        }
    };
    @UnsupportedAppUsage
    public final Intent mData;
    @UnsupportedAppUsage
    public final int mRequestCode;
    public final int mResultCode;
    @UnsupportedAppUsage
    public final String mResultWho;

    public ResultInfo(Parcel parcel) {
        this.mResultWho = parcel.readString();
        this.mRequestCode = parcel.readInt();
        this.mResultCode = parcel.readInt();
        this.mData = parcel.readInt() != 0 ? Intent.CREATOR.createFromParcel(parcel) : null;
    }

    @UnsupportedAppUsage
    public ResultInfo(String string2, int n, int n2, Intent intent) {
        this.mResultWho = string2;
        this.mRequestCode = n;
        this.mResultCode = n2;
        this.mData = intent;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = false;
        if (object != null && object instanceof ResultInfo) {
            object = (ResultInfo)object;
            Intent intent = this.mData;
            boolean bl2 = intent == null ? ((ResultInfo)object).mData == null : intent.filterEquals(((ResultInfo)object).mData);
            boolean bl3 = bl;
            if (bl2) {
                bl3 = bl;
                if (Objects.equals(this.mResultWho, ((ResultInfo)object).mResultWho)) {
                    bl3 = bl;
                    if (this.mResultCode == ((ResultInfo)object).mResultCode) {
                        bl3 = bl;
                        if (this.mRequestCode == ((ResultInfo)object).mRequestCode) {
                            bl3 = true;
                        }
                    }
                }
            }
            return bl3;
        }
        return false;
    }

    public int hashCode() {
        int n = ((17 * 31 + this.mRequestCode) * 31 + this.mResultCode) * 31 + Objects.hashCode(this.mResultWho);
        Intent intent = this.mData;
        int n2 = n;
        if (intent != null) {
            n2 = n * 31 + intent.filterHashCode();
        }
        return n2;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ResultInfo{who=");
        stringBuilder.append(this.mResultWho);
        stringBuilder.append(", request=");
        stringBuilder.append(this.mRequestCode);
        stringBuilder.append(", result=");
        stringBuilder.append(this.mResultCode);
        stringBuilder.append(", data=");
        stringBuilder.append(this.mData);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mResultWho);
        parcel.writeInt(this.mRequestCode);
        parcel.writeInt(this.mResultCode);
        if (this.mData != null) {
            parcel.writeInt(1);
            this.mData.writeToParcel(parcel, 0);
        } else {
            parcel.writeInt(0);
        }
    }

}

