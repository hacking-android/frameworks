/*
 * Decompiled with CFR 0.145.
 */
package android.content;

import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.Preconditions;
import java.io.PrintWriter;

public final class LocusId
implements Parcelable {
    public static final Parcelable.Creator<LocusId> CREATOR = new Parcelable.Creator<LocusId>(){

        @Override
        public LocusId createFromParcel(Parcel parcel) {
            return new LocusId(parcel.readString());
        }

        public LocusId[] newArray(int n) {
            return new LocusId[n];
        }
    };
    private final String mId;

    public LocusId(String string2) {
        this.mId = Preconditions.checkStringNotEmpty(string2, "id cannot be empty");
    }

    private String getSanitizedId() {
        int n = this.mId.length();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(n);
        stringBuilder.append("_chars");
        return stringBuilder.toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void dump(PrintWriter printWriter) {
        printWriter.print("id:");
        printWriter.println(this.getSanitizedId());
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
        object = (LocusId)object;
        String string2 = this.mId;
        return !(string2 == null ? ((LocusId)object).mId != null : !string2.equals(((LocusId)object).mId));
    }

    public String getId() {
        return this.mId;
    }

    public int hashCode() {
        String string2 = this.mId;
        int n = string2 == null ? 0 : string2.hashCode();
        return 1 * 31 + n;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("LocusId[");
        stringBuilder.append(this.getSanitizedId());
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mId);
    }

}

