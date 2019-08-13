/*
 * Decompiled with CFR 0.145.
 */
package android.security.keymaster;

import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.os.Parcelable;

public class ExportResult
implements Parcelable {
    @UnsupportedAppUsage
    public static final Parcelable.Creator<ExportResult> CREATOR = new Parcelable.Creator<ExportResult>(){

        @Override
        public ExportResult createFromParcel(Parcel parcel) {
            return new ExportResult(parcel);
        }

        public ExportResult[] newArray(int n) {
            return new ExportResult[n];
        }
    };
    public final byte[] exportData;
    public final int resultCode;

    public ExportResult(int n) {
        this.resultCode = n;
        this.exportData = new byte[0];
    }

    protected ExportResult(Parcel parcel) {
        this.resultCode = parcel.readInt();
        this.exportData = parcel.createByteArray();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.resultCode);
        parcel.writeByteArray(this.exportData);
    }

}

