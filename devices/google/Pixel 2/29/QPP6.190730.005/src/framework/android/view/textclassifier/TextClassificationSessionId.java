/*
 * Decompiled with CFR 0.145.
 */
package android.view.textclassifier;

import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.Preconditions;
import java.util.Locale;
import java.util.UUID;

public final class TextClassificationSessionId
implements Parcelable {
    public static final Parcelable.Creator<TextClassificationSessionId> CREATOR = new Parcelable.Creator<TextClassificationSessionId>(){

        @Override
        public TextClassificationSessionId createFromParcel(Parcel parcel) {
            return new TextClassificationSessionId(Preconditions.checkNotNull(parcel.readString()));
        }

        public TextClassificationSessionId[] newArray(int n) {
            return new TextClassificationSessionId[n];
        }
    };
    private final String mValue;

    public TextClassificationSessionId() {
        this(UUID.randomUUID().toString());
    }

    public TextClassificationSessionId(String string2) {
        this.mValue = string2;
    }

    public static TextClassificationSessionId unflattenFromString(String string2) {
        return new TextClassificationSessionId(string2);
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
        object = (TextClassificationSessionId)object;
        return this.mValue.equals(((TextClassificationSessionId)object).mValue);
    }

    public String flattenToString() {
        return this.mValue;
    }

    public int hashCode() {
        return 1 * 31 + this.mValue.hashCode();
    }

    public String toString() {
        return String.format(Locale.US, "TextClassificationSessionId {%s}", this.mValue);
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mValue);
    }

}

