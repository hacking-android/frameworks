/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.widget;

import android.os.Parcel;
import android.os.Parcelable;
import android.service.gatekeeper.GateKeeperResponse;
import android.util.Slog;

public final class VerifyCredentialResponse
implements Parcelable {
    public static final Parcelable.Creator<VerifyCredentialResponse> CREATOR;
    public static final VerifyCredentialResponse ERROR;
    public static final VerifyCredentialResponse OK;
    public static final int RESPONSE_ERROR = -1;
    public static final int RESPONSE_OK = 0;
    public static final int RESPONSE_RETRY = 1;
    private static final String TAG = "VerifyCredentialResponse";
    private byte[] mPayload;
    private int mResponseCode;
    private int mTimeout;

    static {
        OK = new VerifyCredentialResponse();
        ERROR = new VerifyCredentialResponse(-1, 0, null);
        CREATOR = new Parcelable.Creator<VerifyCredentialResponse>(){

            @Override
            public VerifyCredentialResponse createFromParcel(Parcel parcel) {
                int n = parcel.readInt();
                VerifyCredentialResponse verifyCredentialResponse = new VerifyCredentialResponse(n, 0, null);
                if (n == 1) {
                    verifyCredentialResponse.setTimeout(parcel.readInt());
                } else if (n == 0 && (n = parcel.readInt()) > 0) {
                    byte[] arrby = new byte[n];
                    parcel.readByteArray(arrby);
                    verifyCredentialResponse.setPayload(arrby);
                }
                return verifyCredentialResponse;
            }

            public VerifyCredentialResponse[] newArray(int n) {
                return new VerifyCredentialResponse[n];
            }
        };
    }

    public VerifyCredentialResponse() {
        this.mResponseCode = 0;
        this.mPayload = null;
    }

    public VerifyCredentialResponse(int n) {
        this.mTimeout = n;
        this.mResponseCode = 1;
        this.mPayload = null;
    }

    private VerifyCredentialResponse(int n, int n2, byte[] arrby) {
        this.mResponseCode = n;
        this.mTimeout = n2;
        this.mPayload = arrby;
    }

    public VerifyCredentialResponse(byte[] arrby) {
        this.mPayload = arrby;
        this.mResponseCode = 0;
    }

    public static VerifyCredentialResponse fromGateKeeperResponse(GateKeeperResponse object) {
        int n = object.getResponseCode();
        if (n == 1) {
            object = new VerifyCredentialResponse(object.getTimeout());
        } else if (n == 0) {
            if ((object = object.getPayload()) == null) {
                Slog.e(TAG, "verifyChallenge response had no associated payload");
                object = ERROR;
            } else {
                object = new VerifyCredentialResponse((byte[])object);
            }
        } else {
            object = ERROR;
        }
        return object;
    }

    private void setPayload(byte[] arrby) {
        this.mPayload = arrby;
    }

    private void setTimeout(int n) {
        this.mTimeout = n;
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

    public int getTimeout() {
        return this.mTimeout;
    }

    public VerifyCredentialResponse stripPayload() {
        return new VerifyCredentialResponse(this.mResponseCode, this.mTimeout, new byte[0]);
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mResponseCode);
        n = this.mResponseCode;
        if (n == 1) {
            parcel.writeInt(this.mTimeout);
        } else if (n == 0) {
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

