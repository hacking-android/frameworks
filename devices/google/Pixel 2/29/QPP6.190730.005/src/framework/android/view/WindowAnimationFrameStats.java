/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.FrameStats;

public final class WindowAnimationFrameStats
extends FrameStats
implements Parcelable {
    public static final Parcelable.Creator<WindowAnimationFrameStats> CREATOR = new Parcelable.Creator<WindowAnimationFrameStats>(){

        @Override
        public WindowAnimationFrameStats createFromParcel(Parcel parcel) {
            return new WindowAnimationFrameStats(parcel);
        }

        public WindowAnimationFrameStats[] newArray(int n) {
            return new WindowAnimationFrameStats[n];
        }
    };

    public WindowAnimationFrameStats() {
    }

    private WindowAnimationFrameStats(Parcel parcel) {
        this.mRefreshPeriodNano = parcel.readLong();
        this.mFramesPresentedTimeNano = parcel.createLongArray();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @UnsupportedAppUsage
    public void init(long l, long[] arrl) {
        this.mRefreshPeriodNano = l;
        this.mFramesPresentedTimeNano = arrl;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("WindowAnimationFrameStats[");
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("frameCount:");
        stringBuilder2.append(this.getFrameCount());
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(", fromTimeNano:");
        stringBuilder2.append(this.getStartTimeNano());
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(", toTimeNano:");
        stringBuilder2.append(this.getEndTimeNano());
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder.append(']');
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeLong(this.mRefreshPeriodNano);
        parcel.writeLongArray(this.mFramesPresentedTimeNano);
    }

}

