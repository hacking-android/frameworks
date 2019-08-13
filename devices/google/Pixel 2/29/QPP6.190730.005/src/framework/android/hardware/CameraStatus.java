/*
 * Decompiled with CFR 0.145.
 */
package android.hardware;

import android.os.Parcel;
import android.os.Parcelable;

public class CameraStatus
implements Parcelable {
    public static final Parcelable.Creator<CameraStatus> CREATOR = new Parcelable.Creator<CameraStatus>(){

        @Override
        public CameraStatus createFromParcel(Parcel parcel) {
            CameraStatus cameraStatus = new CameraStatus();
            cameraStatus.readFromParcel(parcel);
            return cameraStatus;
        }

        public CameraStatus[] newArray(int n) {
            return new CameraStatus[n];
        }
    };
    public String cameraId;
    public int status;

    @Override
    public int describeContents() {
        return 0;
    }

    public void readFromParcel(Parcel parcel) {
        this.cameraId = parcel.readString();
        this.status = parcel.readInt();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.cameraId);
        parcel.writeInt(this.status);
    }

}

