/*
 * Decompiled with CFR 0.145.
 */
package android.text;

import android.os.Parcel;
import android.text.ParcelableSpan;

public class Annotation
implements ParcelableSpan {
    private final String mKey;
    private final String mValue;

    public Annotation(Parcel parcel) {
        this.mKey = parcel.readString();
        this.mValue = parcel.readString();
    }

    public Annotation(String string2, String string3) {
        this.mKey = string2;
        this.mValue = string3;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getKey() {
        return this.mKey;
    }

    @Override
    public int getSpanTypeId() {
        return this.getSpanTypeIdInternal();
    }

    @Override
    public int getSpanTypeIdInternal() {
        return 18;
    }

    public String getValue() {
        return this.mValue;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        this.writeToParcelInternal(parcel, n);
    }

    @Override
    public void writeToParcelInternal(Parcel parcel, int n) {
        parcel.writeString(this.mKey);
        parcel.writeString(this.mValue);
    }
}

