/*
 * Decompiled with CFR 0.145.
 */
package android.apex;

import android.apex.ApexInfo;
import android.os.Parcel;
import android.os.Parcelable;

public class ApexInfoList
implements Parcelable {
    public static final Parcelable.Creator<ApexInfoList> CREATOR = new Parcelable.Creator<ApexInfoList>(){

        @Override
        public ApexInfoList createFromParcel(Parcel parcel) {
            ApexInfoList apexInfoList = new ApexInfoList();
            apexInfoList.readFromParcel(parcel);
            return apexInfoList;
        }

        public ApexInfoList[] newArray(int n) {
            return new ApexInfoList[n];
        }
    };
    public ApexInfo[] apexInfos;

    @Override
    public int describeContents() {
        return 0;
    }

    public final void readFromParcel(Parcel parcel) {
        int n;
        int n2 = parcel.dataPosition();
        int n3 = parcel.readInt();
        if (n3 < 0) {
            return;
        }
        try {
            this.apexInfos = parcel.createTypedArray(ApexInfo.CREATOR);
            n = parcel.dataPosition();
        }
        catch (Throwable throwable) {
            parcel.setDataPosition(n2 + n3);
            throw throwable;
        }
        if (n - n2 >= n3) {
            parcel.setDataPosition(n2 + n3);
            return;
        }
        parcel.setDataPosition(n2 + n3);
    }

    @Override
    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedArray((Parcelable[])this.apexInfos, 0);
        n = parcel.dataPosition();
        parcel.setDataPosition(n2);
        parcel.writeInt(n - n2);
        parcel.setDataPosition(n);
    }

}

