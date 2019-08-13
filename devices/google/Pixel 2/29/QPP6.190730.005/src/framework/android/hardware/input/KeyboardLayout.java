/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.input;

import android.os.LocaleList;
import android.os.Parcel;
import android.os.Parcelable;

public final class KeyboardLayout
implements Parcelable,
Comparable<KeyboardLayout> {
    public static final Parcelable.Creator<KeyboardLayout> CREATOR = new Parcelable.Creator<KeyboardLayout>(){

        @Override
        public KeyboardLayout createFromParcel(Parcel parcel) {
            return new KeyboardLayout(parcel);
        }

        public KeyboardLayout[] newArray(int n) {
            return new KeyboardLayout[n];
        }
    };
    private final String mCollection;
    private final String mDescriptor;
    private final String mLabel;
    private final LocaleList mLocales;
    private final int mPriority;
    private final int mProductId;
    private final int mVendorId;

    private KeyboardLayout(Parcel parcel) {
        this.mDescriptor = parcel.readString();
        this.mLabel = parcel.readString();
        this.mCollection = parcel.readString();
        this.mPriority = parcel.readInt();
        this.mLocales = LocaleList.CREATOR.createFromParcel(parcel);
        this.mVendorId = parcel.readInt();
        this.mProductId = parcel.readInt();
    }

    public KeyboardLayout(String string2, String string3, String string4, int n, LocaleList localeList, int n2, int n3) {
        this.mDescriptor = string2;
        this.mLabel = string3;
        this.mCollection = string4;
        this.mPriority = n;
        this.mLocales = localeList;
        this.mVendorId = n2;
        this.mProductId = n3;
    }

    @Override
    public int compareTo(KeyboardLayout keyboardLayout) {
        int n;
        int n2 = n = Integer.compare(keyboardLayout.mPriority, this.mPriority);
        if (n == 0) {
            n2 = this.mLabel.compareToIgnoreCase(keyboardLayout.mLabel);
        }
        n = n2;
        if (n2 == 0) {
            n = this.mCollection.compareToIgnoreCase(keyboardLayout.mCollection);
        }
        return n;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getCollection() {
        return this.mCollection;
    }

    public String getDescriptor() {
        return this.mDescriptor;
    }

    public String getLabel() {
        return this.mLabel;
    }

    public LocaleList getLocales() {
        return this.mLocales;
    }

    public int getProductId() {
        return this.mProductId;
    }

    public int getVendorId() {
        return this.mVendorId;
    }

    public String toString() {
        if (this.mCollection.isEmpty()) {
            return this.mLabel;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.mLabel);
        stringBuilder.append(" - ");
        stringBuilder.append(this.mCollection);
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mDescriptor);
        parcel.writeString(this.mLabel);
        parcel.writeString(this.mCollection);
        parcel.writeInt(this.mPriority);
        this.mLocales.writeToParcel(parcel, 0);
        parcel.writeInt(this.mVendorId);
        parcel.writeInt(this.mProductId);
    }

}

