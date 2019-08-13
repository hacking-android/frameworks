/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.android.internal.telephony.dataconnection;

import android.os.Parcel;
import android.os.Parcelable;

public class KeepaliveStatus
implements Parcelable {
    public static final Parcelable.Creator<KeepaliveStatus> CREATOR = new Parcelable.Creator<KeepaliveStatus>(){

        public KeepaliveStatus createFromParcel(Parcel parcel) {
            return new KeepaliveStatus(parcel);
        }

        public KeepaliveStatus[] newArray(int n) {
            return new KeepaliveStatus[n];
        }
    };
    public static final int ERROR_NONE = 0;
    public static final int ERROR_NO_RESOURCES = 2;
    public static final int ERROR_UNKNOWN = 3;
    public static final int ERROR_UNSUPPORTED = 1;
    public static final int INVALID_HANDLE = Integer.MAX_VALUE;
    private static final String LOG_TAG = "KeepaliveStatus";
    public static final int STATUS_ACTIVE = 0;
    public static final int STATUS_INACTIVE = 1;
    public static final int STATUS_PENDING = 2;
    public final int errorCode;
    public final int sessionHandle;
    public final int statusCode;

    public KeepaliveStatus(int n) {
        this.sessionHandle = Integer.MAX_VALUE;
        this.statusCode = 1;
        this.errorCode = n;
    }

    public KeepaliveStatus(int n, int n2) {
        this.sessionHandle = n;
        this.statusCode = n2;
        this.errorCode = 0;
    }

    private KeepaliveStatus(Parcel parcel) {
        this.errorCode = parcel.readInt();
        this.sessionHandle = parcel.readInt();
        this.statusCode = parcel.readInt();
    }

    public int describeContents() {
        return 0;
    }

    public String toString() {
        return String.format("{errorCode=%d, sessionHandle=%d, statusCode=%d}", this.errorCode, this.sessionHandle, this.statusCode);
    }

    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.errorCode);
        parcel.writeInt(this.sessionHandle);
        parcel.writeInt(this.statusCode);
    }

}

