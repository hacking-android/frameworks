/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.impl;

import android.hardware.camera2.impl.CameraMetadataNative;
import android.os.Parcel;
import android.os.Parcelable;

public class PhysicalCaptureResultInfo
implements Parcelable {
    public static final Parcelable.Creator<PhysicalCaptureResultInfo> CREATOR = new Parcelable.Creator<PhysicalCaptureResultInfo>(){

        @Override
        public PhysicalCaptureResultInfo createFromParcel(Parcel parcel) {
            return new PhysicalCaptureResultInfo(parcel);
        }

        public PhysicalCaptureResultInfo[] newArray(int n) {
            return new PhysicalCaptureResultInfo[n];
        }
    };
    private String cameraId;
    private CameraMetadataNative cameraMetadata;

    private PhysicalCaptureResultInfo(Parcel parcel) {
        this.readFromParcel(parcel);
    }

    public PhysicalCaptureResultInfo(String string2, CameraMetadataNative cameraMetadataNative) {
        this.cameraId = string2;
        this.cameraMetadata = cameraMetadataNative;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getCameraId() {
        return this.cameraId;
    }

    public CameraMetadataNative getCameraMetadata() {
        return this.cameraMetadata;
    }

    public void readFromParcel(Parcel parcel) {
        this.cameraId = parcel.readString();
        this.cameraMetadata = new CameraMetadataNative();
        this.cameraMetadata.readFromParcel(parcel);
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.cameraId);
        this.cameraMetadata.writeToParcel(parcel, n);
    }

}

