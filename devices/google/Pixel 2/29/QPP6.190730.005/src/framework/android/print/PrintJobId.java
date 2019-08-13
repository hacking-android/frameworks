/*
 * Decompiled with CFR 0.145.
 */
package android.print;

import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.Preconditions;
import java.util.UUID;

public final class PrintJobId
implements Parcelable {
    public static final Parcelable.Creator<PrintJobId> CREATOR = new Parcelable.Creator<PrintJobId>(){

        @Override
        public PrintJobId createFromParcel(Parcel parcel) {
            return new PrintJobId(Preconditions.checkNotNull(parcel.readString()));
        }

        public PrintJobId[] newArray(int n) {
            return new PrintJobId[n];
        }
    };
    private final String mValue;

    public PrintJobId() {
        this(UUID.randomUUID().toString());
    }

    public PrintJobId(String string2) {
        this.mValue = string2;
    }

    public static PrintJobId unflattenFromString(String string2) {
        return new PrintJobId(string2);
    }

    @Override
    public int describeContents() {
        return 0;
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
        object = (PrintJobId)object;
        return this.mValue.equals(((PrintJobId)object).mValue);
    }

    public String flattenToString() {
        return this.mValue;
    }

    public int hashCode() {
        return 1 * 31 + this.mValue.hashCode();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mValue);
    }

}

