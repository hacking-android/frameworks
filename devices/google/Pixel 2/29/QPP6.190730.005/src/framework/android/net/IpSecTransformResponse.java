/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.os.Parcel;
import android.os.Parcelable;

public final class IpSecTransformResponse
implements Parcelable {
    public static final Parcelable.Creator<IpSecTransformResponse> CREATOR = new Parcelable.Creator<IpSecTransformResponse>(){

        @Override
        public IpSecTransformResponse createFromParcel(Parcel parcel) {
            return new IpSecTransformResponse(parcel);
        }

        public IpSecTransformResponse[] newArray(int n) {
            return new IpSecTransformResponse[n];
        }
    };
    private static final String TAG = "IpSecTransformResponse";
    public final int resourceId;
    public final int status;

    public IpSecTransformResponse(int n) {
        if (n != 0) {
            this.status = n;
            this.resourceId = -1;
            return;
        }
        throw new IllegalArgumentException("Valid status implies other args must be provided");
    }

    public IpSecTransformResponse(int n, int n2) {
        this.status = n;
        this.resourceId = n2;
    }

    private IpSecTransformResponse(Parcel parcel) {
        this.status = parcel.readInt();
        this.resourceId = parcel.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.status);
        parcel.writeInt(this.resourceId);
    }

}

