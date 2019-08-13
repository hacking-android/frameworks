/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.params;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public final class VendorTagDescriptor
implements Parcelable {
    public static final Parcelable.Creator<VendorTagDescriptor> CREATOR = new Parcelable.Creator<VendorTagDescriptor>(){

        @Override
        public VendorTagDescriptor createFromParcel(Parcel object) {
            try {
                object = new VendorTagDescriptor((Parcel)object);
                return object;
            }
            catch (Exception exception) {
                Log.e(VendorTagDescriptor.TAG, "Exception creating VendorTagDescriptor from parcel", exception);
                return null;
            }
        }

        public VendorTagDescriptor[] newArray(int n) {
            return new VendorTagDescriptor[n];
        }
    };
    private static final String TAG = "VendorTagDescriptor";

    private VendorTagDescriptor(Parcel parcel) {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        if (parcel != null) {
            return;
        }
        throw new IllegalArgumentException("dest must not be null");
    }

}

