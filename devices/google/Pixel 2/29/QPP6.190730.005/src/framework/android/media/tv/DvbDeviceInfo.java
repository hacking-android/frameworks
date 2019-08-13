/*
 * Decompiled with CFR 0.145.
 */
package android.media.tv;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public final class DvbDeviceInfo
implements Parcelable {
    public static final Parcelable.Creator<DvbDeviceInfo> CREATOR = new Parcelable.Creator<DvbDeviceInfo>(){

        @Override
        public DvbDeviceInfo createFromParcel(Parcel object) {
            try {
                object = new DvbDeviceInfo((Parcel)object);
                return object;
            }
            catch (Exception exception) {
                Log.e(DvbDeviceInfo.TAG, "Exception creating DvbDeviceInfo from parcel", exception);
                return null;
            }
        }

        public DvbDeviceInfo[] newArray(int n) {
            return new DvbDeviceInfo[n];
        }
    };
    static final String TAG = "DvbDeviceInfo";
    private final int mAdapterId;
    private final int mDeviceId;

    public DvbDeviceInfo(int n, int n2) {
        this.mAdapterId = n;
        this.mDeviceId = n2;
    }

    private DvbDeviceInfo(Parcel parcel) {
        this.mAdapterId = parcel.readInt();
        this.mDeviceId = parcel.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getAdapterId() {
        return this.mAdapterId;
    }

    public int getDeviceId() {
        return this.mDeviceId;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mAdapterId);
        parcel.writeInt(this.mDeviceId);
    }

}

