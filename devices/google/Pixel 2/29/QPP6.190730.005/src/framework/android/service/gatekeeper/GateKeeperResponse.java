/*
 * Decompiled with CFR 0.145.
 */
package android.service.gatekeeper;

import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.annotations.VisibleForTesting;

public final class GateKeeperResponse
implements Parcelable {
    public static final Parcelable.Creator<GateKeeperResponse> CREATOR = new Parcelable.Creator<GateKeeperResponse>(){

        @Override
        public GateKeeperResponse createFromParcel(Parcel object) {
            int n = ((Parcel)object).readInt();
            boolean bl = true;
            if (n == 1) {
                object = GateKeeperResponse.createRetryResponse(((Parcel)object).readInt());
            } else if (n == 0) {
                if (((Parcel)object).readInt() != 1) {
                    bl = false;
                }
                byte[] arrby = null;
                n = ((Parcel)object).readInt();
                if (n > 0) {
                    arrby = new byte[n];
                    ((Parcel)object).readByteArray(arrby);
                }
                object = GateKeeperResponse.createOkResponse(arrby, bl);
            } else {
                object = GateKeeperResponse.createGenericResponse(n);
            }
            return object;
        }

        public GateKeeperResponse[] newArray(int n) {
            return new GateKeeperResponse[n];
        }
    };
    public static final int RESPONSE_ERROR = -1;
    public static final int RESPONSE_OK = 0;
    public static final int RESPONSE_RETRY = 1;
    private byte[] mPayload;
    private final int mResponseCode;
    private boolean mShouldReEnroll;
    private int mTimeout;

    private GateKeeperResponse(int n) {
        this.mResponseCode = n;
    }

    @VisibleForTesting
    public static GateKeeperResponse createGenericResponse(int n) {
        return new GateKeeperResponse(n);
    }

    @VisibleForTesting
    public static GateKeeperResponse createOkResponse(byte[] arrby, boolean bl) {
        GateKeeperResponse gateKeeperResponse = new GateKeeperResponse(0);
        gateKeeperResponse.mPayload = arrby;
        gateKeeperResponse.mShouldReEnroll = bl;
        return gateKeeperResponse;
    }

    private static GateKeeperResponse createRetryResponse(int n) {
        GateKeeperResponse gateKeeperResponse = new GateKeeperResponse(1);
        gateKeeperResponse.mTimeout = n;
        return gateKeeperResponse;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public byte[] getPayload() {
        return this.mPayload;
    }

    public int getResponseCode() {
        return this.mResponseCode;
    }

    public boolean getShouldReEnroll() {
        return this.mShouldReEnroll;
    }

    public int getTimeout() {
        return this.mTimeout;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mResponseCode);
        n = this.mResponseCode;
        if (n == 1) {
            parcel.writeInt(this.mTimeout);
        } else if (n == 0) {
            parcel.writeInt((int)this.mShouldReEnroll);
            byte[] arrby = this.mPayload;
            if (arrby != null) {
                parcel.writeInt(arrby.length);
                parcel.writeByteArray(this.mPayload);
            } else {
                parcel.writeInt(0);
            }
        }
    }

}

