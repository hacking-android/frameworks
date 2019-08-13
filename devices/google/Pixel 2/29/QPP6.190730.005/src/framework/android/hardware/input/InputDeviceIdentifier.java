/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.input;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import java.util.Objects;

public final class InputDeviceIdentifier
implements Parcelable {
    public static final Parcelable.Creator<InputDeviceIdentifier> CREATOR = new Parcelable.Creator<InputDeviceIdentifier>(){

        @Override
        public InputDeviceIdentifier createFromParcel(Parcel parcel) {
            return new InputDeviceIdentifier(parcel);
        }

        public InputDeviceIdentifier[] newArray(int n) {
            return new InputDeviceIdentifier[n];
        }
    };
    private final String mDescriptor;
    private final int mProductId;
    private final int mVendorId;

    private InputDeviceIdentifier(Parcel parcel) {
        this.mDescriptor = parcel.readString();
        this.mVendorId = parcel.readInt();
        this.mProductId = parcel.readInt();
    }

    public InputDeviceIdentifier(String string2, int n, int n2) {
        this.mDescriptor = string2;
        this.mVendorId = n;
        this.mProductId = n2;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object != null && object instanceof InputDeviceIdentifier) {
            object = (InputDeviceIdentifier)object;
            if (this.mVendorId != ((InputDeviceIdentifier)object).mVendorId || this.mProductId != ((InputDeviceIdentifier)object).mProductId || !TextUtils.equals(this.mDescriptor, ((InputDeviceIdentifier)object).mDescriptor)) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    public String getDescriptor() {
        return this.mDescriptor;
    }

    public int getProductId() {
        return this.mProductId;
    }

    public int getVendorId() {
        return this.mVendorId;
    }

    public int hashCode() {
        return Objects.hash(this.mDescriptor, this.mVendorId, this.mProductId);
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mDescriptor);
        parcel.writeInt(this.mVendorId);
        parcel.writeInt(this.mProductId);
    }

}

