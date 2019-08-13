/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.impl;

import android.os.Parcel;
import android.os.Parcelable;

public class CaptureResultExtras
implements Parcelable {
    public static final Parcelable.Creator<CaptureResultExtras> CREATOR = new Parcelable.Creator<CaptureResultExtras>(){

        @Override
        public CaptureResultExtras createFromParcel(Parcel parcel) {
            return new CaptureResultExtras(parcel);
        }

        public CaptureResultExtras[] newArray(int n) {
            return new CaptureResultExtras[n];
        }
    };
    private int afTriggerId;
    private String errorPhysicalCameraId;
    private int errorStreamId;
    private long frameNumber;
    private int partialResultCount;
    private int precaptureTriggerId;
    private int requestId;
    private int subsequenceId;

    public CaptureResultExtras(int n, int n2, int n3, int n4, long l, int n5, int n6, String string2) {
        this.requestId = n;
        this.subsequenceId = n2;
        this.afTriggerId = n3;
        this.precaptureTriggerId = n4;
        this.frameNumber = l;
        this.partialResultCount = n5;
        this.errorStreamId = n6;
        this.errorPhysicalCameraId = string2;
    }

    private CaptureResultExtras(Parcel parcel) {
        this.readFromParcel(parcel);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getAfTriggerId() {
        return this.afTriggerId;
    }

    public String getErrorPhysicalCameraId() {
        return this.errorPhysicalCameraId;
    }

    public int getErrorStreamId() {
        return this.errorStreamId;
    }

    public long getFrameNumber() {
        return this.frameNumber;
    }

    public int getPartialResultCount() {
        return this.partialResultCount;
    }

    public int getPrecaptureTriggerId() {
        return this.precaptureTriggerId;
    }

    public int getRequestId() {
        return this.requestId;
    }

    public int getSubsequenceId() {
        return this.subsequenceId;
    }

    public void readFromParcel(Parcel parcel) {
        this.requestId = parcel.readInt();
        this.subsequenceId = parcel.readInt();
        this.afTriggerId = parcel.readInt();
        this.precaptureTriggerId = parcel.readInt();
        this.frameNumber = parcel.readLong();
        this.partialResultCount = parcel.readInt();
        this.errorStreamId = parcel.readInt();
        if (parcel.readBoolean()) {
            this.errorPhysicalCameraId = parcel.readString();
        }
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.requestId);
        parcel.writeInt(this.subsequenceId);
        parcel.writeInt(this.afTriggerId);
        parcel.writeInt(this.precaptureTriggerId);
        parcel.writeLong(this.frameNumber);
        parcel.writeInt(this.partialResultCount);
        parcel.writeInt(this.errorStreamId);
        String string2 = this.errorPhysicalCameraId;
        if (string2 != null && !string2.isEmpty()) {
            parcel.writeBoolean(true);
            parcel.writeString(this.errorPhysicalCameraId);
        } else {
            parcel.writeBoolean(false);
        }
    }

}

