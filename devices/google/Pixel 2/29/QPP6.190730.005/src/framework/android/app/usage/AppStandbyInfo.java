/*
 * Decompiled with CFR 0.145.
 */
package android.app.usage;

import android.os.Parcel;
import android.os.Parcelable;

public final class AppStandbyInfo
implements Parcelable {
    public static final Parcelable.Creator<AppStandbyInfo> CREATOR = new Parcelable.Creator<AppStandbyInfo>(){

        @Override
        public AppStandbyInfo createFromParcel(Parcel parcel) {
            return new AppStandbyInfo(parcel);
        }

        public AppStandbyInfo[] newArray(int n) {
            return new AppStandbyInfo[n];
        }
    };
    public String mPackageName;
    public int mStandbyBucket;

    private AppStandbyInfo(Parcel parcel) {
        this.mPackageName = parcel.readString();
        this.mStandbyBucket = parcel.readInt();
    }

    public AppStandbyInfo(String string2, int n) {
        this.mPackageName = string2;
        this.mStandbyBucket = n;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mPackageName);
        parcel.writeInt(this.mStandbyBucket);
    }

}

