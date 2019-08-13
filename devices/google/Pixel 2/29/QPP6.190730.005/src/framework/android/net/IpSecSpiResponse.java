/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.os.Parcel;
import android.os.Parcelable;

public final class IpSecSpiResponse
implements Parcelable {
    public static final Parcelable.Creator<IpSecSpiResponse> CREATOR = new Parcelable.Creator<IpSecSpiResponse>(){

        @Override
        public IpSecSpiResponse createFromParcel(Parcel parcel) {
            return new IpSecSpiResponse(parcel);
        }

        public IpSecSpiResponse[] newArray(int n) {
            return new IpSecSpiResponse[n];
        }
    };
    private static final String TAG = "IpSecSpiResponse";
    public final int resourceId;
    public final int spi;
    public final int status;

    public IpSecSpiResponse(int n) {
        if (n != 0) {
            this.status = n;
            this.resourceId = -1;
            this.spi = 0;
            return;
        }
        throw new IllegalArgumentException("Valid status implies other args must be provided");
    }

    public IpSecSpiResponse(int n, int n2, int n3) {
        this.status = n;
        this.resourceId = n2;
        this.spi = n3;
    }

    private IpSecSpiResponse(Parcel parcel) {
        this.status = parcel.readInt();
        this.resourceId = parcel.readInt();
        this.spi = parcel.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.status);
        parcel.writeInt(this.resourceId);
        parcel.writeInt(this.spi);
    }

}

