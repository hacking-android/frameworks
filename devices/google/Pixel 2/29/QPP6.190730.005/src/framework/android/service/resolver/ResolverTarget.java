/*
 * Decompiled with CFR 0.145.
 */
package android.service.resolver;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;

@SystemApi
public final class ResolverTarget
implements Parcelable {
    public static final Parcelable.Creator<ResolverTarget> CREATOR = new Parcelable.Creator<ResolverTarget>(){

        @Override
        public ResolverTarget createFromParcel(Parcel parcel) {
            return new ResolverTarget(parcel);
        }

        public ResolverTarget[] newArray(int n) {
            return new ResolverTarget[n];
        }
    };
    private static final String TAG = "ResolverTarget";
    private float mChooserScore;
    private float mLaunchScore;
    private float mRecencyScore;
    private float mSelectProbability;
    private float mTimeSpentScore;

    public ResolverTarget() {
    }

    ResolverTarget(Parcel parcel) {
        this.mRecencyScore = parcel.readFloat();
        this.mTimeSpentScore = parcel.readFloat();
        this.mLaunchScore = parcel.readFloat();
        this.mChooserScore = parcel.readFloat();
        this.mSelectProbability = parcel.readFloat();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public float getChooserScore() {
        return this.mChooserScore;
    }

    public float getLaunchScore() {
        return this.mLaunchScore;
    }

    public float getRecencyScore() {
        return this.mRecencyScore;
    }

    public float getSelectProbability() {
        return this.mSelectProbability;
    }

    public float getTimeSpentScore() {
        return this.mTimeSpentScore;
    }

    public void setChooserScore(float f) {
        this.mChooserScore = f;
    }

    public void setLaunchScore(float f) {
        this.mLaunchScore = f;
    }

    public void setRecencyScore(float f) {
        this.mRecencyScore = f;
    }

    public void setSelectProbability(float f) {
        this.mSelectProbability = f;
    }

    public void setTimeSpentScore(float f) {
        this.mTimeSpentScore = f;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ResolverTarget{");
        stringBuilder.append(this.mRecencyScore);
        stringBuilder.append(", ");
        stringBuilder.append(this.mTimeSpentScore);
        stringBuilder.append(", ");
        stringBuilder.append(this.mLaunchScore);
        stringBuilder.append(", ");
        stringBuilder.append(this.mChooserScore);
        stringBuilder.append(", ");
        stringBuilder.append(this.mSelectProbability);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeFloat(this.mRecencyScore);
        parcel.writeFloat(this.mTimeSpentScore);
        parcel.writeFloat(this.mLaunchScore);
        parcel.writeFloat(this.mChooserScore);
        parcel.writeFloat(this.mSelectProbability);
    }

}

