/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.os.Parcel;
import android.os.Parcelable;

public class IccOpenLogicalChannelResponse
implements Parcelable {
    public static final Parcelable.Creator<IccOpenLogicalChannelResponse> CREATOR = new Parcelable.Creator<IccOpenLogicalChannelResponse>(){

        @Override
        public IccOpenLogicalChannelResponse createFromParcel(Parcel parcel) {
            return new IccOpenLogicalChannelResponse(parcel);
        }

        public IccOpenLogicalChannelResponse[] newArray(int n) {
            return new IccOpenLogicalChannelResponse[n];
        }
    };
    public static final int INVALID_CHANNEL = -1;
    public static final int STATUS_MISSING_RESOURCE = 2;
    public static final int STATUS_NO_ERROR = 1;
    public static final int STATUS_NO_SUCH_ELEMENT = 3;
    public static final int STATUS_UNKNOWN_ERROR = 4;
    private final int mChannel;
    private final byte[] mSelectResponse;
    private final int mStatus;

    public IccOpenLogicalChannelResponse(int n, int n2, byte[] arrby) {
        this.mChannel = n;
        this.mStatus = n2;
        this.mSelectResponse = arrby;
    }

    private IccOpenLogicalChannelResponse(Parcel parcel) {
        this.mChannel = parcel.readInt();
        this.mStatus = parcel.readInt();
        int n = parcel.readInt();
        if (n > 0) {
            this.mSelectResponse = new byte[n];
            parcel.readByteArray(this.mSelectResponse);
        } else {
            this.mSelectResponse = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getChannel() {
        return this.mChannel;
    }

    public byte[] getSelectResponse() {
        return this.mSelectResponse;
    }

    public int getStatus() {
        return this.mStatus;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Channel: ");
        stringBuilder.append(this.mChannel);
        stringBuilder.append(" Status: ");
        stringBuilder.append(this.mStatus);
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mChannel);
        parcel.writeInt(this.mStatus);
        byte[] arrby = this.mSelectResponse;
        if (arrby != null && arrby.length > 0) {
            parcel.writeInt(arrby.length);
            parcel.writeByteArray(this.mSelectResponse);
        } else {
            parcel.writeInt(0);
        }
    }

}

