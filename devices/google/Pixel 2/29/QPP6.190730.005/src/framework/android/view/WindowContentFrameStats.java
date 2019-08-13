/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.FrameStats;

public final class WindowContentFrameStats
extends FrameStats
implements Parcelable {
    public static final Parcelable.Creator<WindowContentFrameStats> CREATOR = new Parcelable.Creator<WindowContentFrameStats>(){

        @Override
        public WindowContentFrameStats createFromParcel(Parcel parcel) {
            return new WindowContentFrameStats(parcel);
        }

        public WindowContentFrameStats[] newArray(int n) {
            return new WindowContentFrameStats[n];
        }
    };
    private long[] mFramesPostedTimeNano;
    private long[] mFramesReadyTimeNano;

    public WindowContentFrameStats() {
    }

    private WindowContentFrameStats(Parcel parcel) {
        this.mRefreshPeriodNano = parcel.readLong();
        this.mFramesPostedTimeNano = parcel.createLongArray();
        this.mFramesPresentedTimeNano = parcel.createLongArray();
        this.mFramesReadyTimeNano = parcel.createLongArray();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public long getFramePostedTimeNano(int n) {
        long[] arrl = this.mFramesPostedTimeNano;
        if (arrl != null) {
            return arrl[n];
        }
        throw new IndexOutOfBoundsException();
    }

    public long getFrameReadyTimeNano(int n) {
        long[] arrl = this.mFramesReadyTimeNano;
        if (arrl != null) {
            return arrl[n];
        }
        throw new IndexOutOfBoundsException();
    }

    @UnsupportedAppUsage
    public void init(long l, long[] arrl, long[] arrl2, long[] arrl3) {
        this.mRefreshPeriodNano = l;
        this.mFramesPostedTimeNano = arrl;
        this.mFramesPresentedTimeNano = arrl2;
        this.mFramesReadyTimeNano = arrl3;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("WindowContentFrameStats[");
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
        parcel.writeLongArray(this.mFramesPostedTimeNano);
        parcel.writeLongArray(this.mFramesPresentedTimeNano);
        parcel.writeLongArray(this.mFramesReadyTimeNano);
    }

}

