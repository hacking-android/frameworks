/*
 * Decompiled with CFR 0.145.
 */
package android.hardware;

import android.hardware.Camera;
import android.os.Parcel;
import android.os.Parcelable;

public class CameraInfo
implements Parcelable {
    public static final Parcelable.Creator<CameraInfo> CREATOR = new Parcelable.Creator<CameraInfo>(){

        @Override
        public CameraInfo createFromParcel(Parcel parcel) {
            CameraInfo cameraInfo = new CameraInfo();
            cameraInfo.readFromParcel(parcel);
            return cameraInfo;
        }

        public CameraInfo[] newArray(int n) {
            return new CameraInfo[n];
        }
    };
    public Camera.CameraInfo info = new Camera.CameraInfo();

    @Override
    public int describeContents() {
        return 0;
    }

    public void readFromParcel(Parcel parcel) {
        this.info.facing = parcel.readInt();
        this.info.orientation = parcel.readInt();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.info.facing);
        parcel.writeInt(this.info.orientation);
    }

}

