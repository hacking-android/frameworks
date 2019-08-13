/*
 * Decompiled with CFR 0.145.
 */
package android.view.contentcapture;

import android.os.Parcel;
import android.os.Parcelable;
import java.io.PrintWriter;

public final class ContentCaptureSessionId
implements Parcelable {
    public static final Parcelable.Creator<ContentCaptureSessionId> CREATOR = new Parcelable.Creator<ContentCaptureSessionId>(){

        @Override
        public ContentCaptureSessionId createFromParcel(Parcel parcel) {
            return new ContentCaptureSessionId(parcel.readInt());
        }

        public ContentCaptureSessionId[] newArray(int n) {
            return new ContentCaptureSessionId[n];
        }
    };
    private final int mValue;

    public ContentCaptureSessionId(int n) {
        this.mValue = n;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void dump(PrintWriter printWriter) {
        printWriter.print(this.mValue);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (this.getClass() != object.getClass()) {
            return false;
        }
        object = (ContentCaptureSessionId)object;
        return this.mValue == ((ContentCaptureSessionId)object).mValue;
    }

    public int getValue() {
        return this.mValue;
    }

    public int hashCode() {
        return 1 * 31 + this.mValue;
    }

    public String toString() {
        return Integer.toString(this.mValue);
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mValue);
    }

}

