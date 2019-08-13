/*
 * Decompiled with CFR 0.145.
 */
package android.app.backup;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;

@SystemApi
public class BackupProgress
implements Parcelable {
    public static final Parcelable.Creator<BackupProgress> CREATOR = new Parcelable.Creator<BackupProgress>(){

        @Override
        public BackupProgress createFromParcel(Parcel parcel) {
            return new BackupProgress(parcel);
        }

        public BackupProgress[] newArray(int n) {
            return new BackupProgress[n];
        }
    };
    public final long bytesExpected;
    public final long bytesTransferred;

    public BackupProgress(long l, long l2) {
        this.bytesExpected = l;
        this.bytesTransferred = l2;
    }

    private BackupProgress(Parcel parcel) {
        this.bytesExpected = parcel.readLong();
        this.bytesTransferred = parcel.readLong();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeLong(this.bytesExpected);
        parcel.writeLong(this.bytesTransferred);
    }

}

