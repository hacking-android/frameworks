/*
 * Decompiled with CFR 0.145.
 */
package android.content.pm;

import android.content.pm.FeatureInfo;
import android.os.Parcel;
import android.os.Parcelable;

public final class FeatureGroupInfo
implements Parcelable {
    public static final Parcelable.Creator<FeatureGroupInfo> CREATOR = new Parcelable.Creator<FeatureGroupInfo>(){

        @Override
        public FeatureGroupInfo createFromParcel(Parcel parcel) {
            FeatureGroupInfo featureGroupInfo = new FeatureGroupInfo();
            featureGroupInfo.features = parcel.createTypedArray(FeatureInfo.CREATOR);
            return featureGroupInfo;
        }

        public FeatureGroupInfo[] newArray(int n) {
            return new FeatureGroupInfo[n];
        }
    };
    public FeatureInfo[] features;

    public FeatureGroupInfo() {
    }

    public FeatureGroupInfo(FeatureGroupInfo featureGroupInfo) {
        this.features = featureGroupInfo.features;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeTypedArray((Parcelable[])this.features, n);
    }

}

