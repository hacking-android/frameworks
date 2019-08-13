/*
 * Decompiled with CFR 0.145.
 */
package android.app.usage;

import android.os.Parcel;
import android.os.Parcelable;

public final class ExternalStorageStats
implements Parcelable {
    public static final Parcelable.Creator<ExternalStorageStats> CREATOR = new Parcelable.Creator<ExternalStorageStats>(){

        @Override
        public ExternalStorageStats createFromParcel(Parcel parcel) {
            return new ExternalStorageStats(parcel);
        }

        public ExternalStorageStats[] newArray(int n) {
            return new ExternalStorageStats[n];
        }
    };
    public long appBytes;
    public long audioBytes;
    public long imageBytes;
    public long obbBytes;
    public long totalBytes;
    public long videoBytes;

    public ExternalStorageStats() {
    }

    public ExternalStorageStats(Parcel parcel) {
        this.totalBytes = parcel.readLong();
        this.audioBytes = parcel.readLong();
        this.videoBytes = parcel.readLong();
        this.imageBytes = parcel.readLong();
        this.appBytes = parcel.readLong();
        this.obbBytes = parcel.readLong();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public long getAppBytes() {
        return this.appBytes;
    }

    public long getAudioBytes() {
        return this.audioBytes;
    }

    public long getImageBytes() {
        return this.imageBytes;
    }

    public long getObbBytes() {
        return this.obbBytes;
    }

    public long getTotalBytes() {
        return this.totalBytes;
    }

    public long getVideoBytes() {
        return this.videoBytes;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeLong(this.totalBytes);
        parcel.writeLong(this.audioBytes);
        parcel.writeLong(this.videoBytes);
        parcel.writeLong(this.imageBytes);
        parcel.writeLong(this.appBytes);
        parcel.writeLong(this.obbBytes);
    }

}

