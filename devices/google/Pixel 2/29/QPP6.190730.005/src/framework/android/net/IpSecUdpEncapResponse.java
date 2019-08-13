/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import java.io.FileDescriptor;
import java.io.IOException;

public final class IpSecUdpEncapResponse
implements Parcelable {
    public static final Parcelable.Creator<IpSecUdpEncapResponse> CREATOR = new Parcelable.Creator<IpSecUdpEncapResponse>(){

        @Override
        public IpSecUdpEncapResponse createFromParcel(Parcel parcel) {
            return new IpSecUdpEncapResponse(parcel);
        }

        public IpSecUdpEncapResponse[] newArray(int n) {
            return new IpSecUdpEncapResponse[n];
        }
    };
    private static final String TAG = "IpSecUdpEncapResponse";
    public final ParcelFileDescriptor fileDescriptor;
    public final int port;
    public final int resourceId;
    public final int status;

    public IpSecUdpEncapResponse(int n) {
        if (n != 0) {
            this.status = n;
            this.resourceId = -1;
            this.port = -1;
            this.fileDescriptor = null;
            return;
        }
        throw new IllegalArgumentException("Valid status implies other args must be provided");
    }

    public IpSecUdpEncapResponse(int n, int n2, int n3, FileDescriptor object) throws IOException {
        if (n == 0 && object == null) {
            throw new IllegalArgumentException("Valid status implies FD must be non-null");
        }
        this.status = n;
        this.resourceId = n2;
        this.port = n3;
        object = this.status == 0 ? ParcelFileDescriptor.dup((FileDescriptor)object) : null;
        this.fileDescriptor = object;
    }

    private IpSecUdpEncapResponse(Parcel parcel) {
        this.status = parcel.readInt();
        this.resourceId = parcel.readInt();
        this.port = parcel.readInt();
        this.fileDescriptor = (ParcelFileDescriptor)parcel.readParcelable(ParcelFileDescriptor.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        int n = this.fileDescriptor != null ? 1 : 0;
        return n;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.status);
        parcel.writeInt(this.resourceId);
        parcel.writeInt(this.port);
        parcel.writeParcelable(this.fileDescriptor, 1);
    }

}

