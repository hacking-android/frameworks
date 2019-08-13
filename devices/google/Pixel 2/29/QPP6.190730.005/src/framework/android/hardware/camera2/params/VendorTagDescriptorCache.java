/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.params;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public final class VendorTagDescriptorCache
implements Parcelable {
    public static final Parcelable.Creator<VendorTagDescriptorCache> CREATOR = new Parcelable.Creator<VendorTagDescriptorCache>(){

        @Override
        public VendorTagDescriptorCache createFromParcel(Parcel object) {
            try {
                object = new VendorTagDescriptorCache((Parcel)object);
                return object;
            }
            catch (Exception exception) {
                Log.e(VendorTagDescriptorCache.TAG, "Exception creating VendorTagDescriptorCache from parcel", exception);
                return null;
            }
        }

        public VendorTagDescriptorCache[] newArray(int n) {
            return new VendorTagDescriptorCache[n];
        }
    };
    private static final String TAG = "VendorTagDescriptorCache";

    private VendorTagDescriptorCache(Parcel parcel) {
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

