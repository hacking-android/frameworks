/*
 * Decompiled with CFR 0.145.
 */
package android.app.usage;

import android.os.Parcel;
import android.os.Parcelable;

public final class StorageStats
implements Parcelable {
    public static final Parcelable.Creator<StorageStats> CREATOR = new Parcelable.Creator<StorageStats>(){

        @Override
        public StorageStats createFromParcel(Parcel parcel) {
            return new StorageStats(parcel);
        }

        public StorageStats[] newArray(int n) {
            return new StorageStats[n];
        }
    };
    public long cacheBytes;
    public long codeBytes;
    public long dataBytes;

    public StorageStats() {
    }

    public StorageStats(Parcel parcel) {
        this.codeBytes = parcel.readLong();
        this.dataBytes = parcel.readLong();
        this.cacheBytes = parcel.readLong();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public long getAppBytes() {
        return this.codeBytes;
    }

    public long getCacheBytes() {
        return this.cacheBytes;
    }

    @Deprecated
    public long getCodeBytes() {
        return this.getAppBytes();
    }

    public long getDataBytes() {
        return this.dataBytes;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeLong(this.codeBytes);
        parcel.writeLong(this.dataBytes);
        parcel.writeLong(this.cacheBytes);
    }

}

