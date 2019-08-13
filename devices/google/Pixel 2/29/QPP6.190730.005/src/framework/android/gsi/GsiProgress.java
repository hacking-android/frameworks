/*
 * Decompiled with CFR 0.145.
 */
package android.gsi;

import android.os.Parcel;
import android.os.Parcelable;

public class GsiProgress
implements Parcelable {
    public static final Parcelable.Creator<GsiProgress> CREATOR = new Parcelable.Creator<GsiProgress>(){

        @Override
        public GsiProgress createFromParcel(Parcel parcel) {
            GsiProgress gsiProgress = new GsiProgress();
            gsiProgress.readFromParcel(parcel);
            return gsiProgress;
        }

        public GsiProgress[] newArray(int n) {
            return new GsiProgress[n];
        }
    };
    public long bytes_processed;
    public int status;
    public String step;
    public long total_bytes;

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
            this.step = parcel.readString();
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
        this.status = parcel.readInt();
        n = parcel.dataPosition();
        if (n - n2 >= n3) {
            parcel.setDataPosition(n2 + n3);
            return;
        }
        this.bytes_processed = parcel.readLong();
        n = parcel.dataPosition();
        if (n - n2 >= n3) {
            parcel.setDataPosition(n2 + n3);
            return;
        }
        this.total_bytes = parcel.readLong();
        n = parcel.dataPosition();
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
        parcel.writeString(this.step);
        parcel.writeInt(this.status);
        parcel.writeLong(this.bytes_processed);
        parcel.writeLong(this.total_bytes);
        n = parcel.dataPosition();
        parcel.setDataPosition(n2);
        parcel.writeInt(n - n2);
        parcel.setDataPosition(n);
    }

}

