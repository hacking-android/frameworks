/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.List;

public final class RemoteDisplayState
implements Parcelable {
    public static final Parcelable.Creator<RemoteDisplayState> CREATOR = new Parcelable.Creator<RemoteDisplayState>(){

        @Override
        public RemoteDisplayState createFromParcel(Parcel parcel) {
            return new RemoteDisplayState(parcel);
        }

        public RemoteDisplayState[] newArray(int n) {
            return new RemoteDisplayState[n];
        }
    };
    public static final int DISCOVERY_MODE_ACTIVE = 2;
    public static final int DISCOVERY_MODE_NONE = 0;
    public static final int DISCOVERY_MODE_PASSIVE = 1;
    public static final String SERVICE_INTERFACE = "com.android.media.remotedisplay.RemoteDisplayProvider";
    @UnsupportedAppUsage
    public final ArrayList<RemoteDisplayInfo> displays;

    @UnsupportedAppUsage
    public RemoteDisplayState() {
        this.displays = new ArrayList();
    }

    RemoteDisplayState(Parcel parcel) {
        this.displays = parcel.createTypedArrayList(RemoteDisplayInfo.CREATOR);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean isValid() {
        ArrayList<RemoteDisplayInfo> arrayList = this.displays;
        if (arrayList == null) {
            return false;
        }
        int n = arrayList.size();
        for (int i = 0; i < n; ++i) {
            if (this.displays.get(i).isValid()) continue;
            return false;
        }
        return true;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeTypedList(this.displays);
    }

    public static final class RemoteDisplayInfo
    implements Parcelable {
        public static final Parcelable.Creator<RemoteDisplayInfo> CREATOR = new Parcelable.Creator<RemoteDisplayInfo>(){

            @Override
            public RemoteDisplayInfo createFromParcel(Parcel parcel) {
                return new RemoteDisplayInfo(parcel);
            }

            public RemoteDisplayInfo[] newArray(int n) {
                return new RemoteDisplayInfo[n];
            }
        };
        public static final int PLAYBACK_VOLUME_FIXED = 0;
        public static final int PLAYBACK_VOLUME_VARIABLE = 1;
        public static final int STATUS_AVAILABLE = 2;
        public static final int STATUS_CONNECTED = 4;
        public static final int STATUS_CONNECTING = 3;
        public static final int STATUS_IN_USE = 1;
        public static final int STATUS_NOT_AVAILABLE = 0;
        public String description;
        public String id;
        public String name;
        public int presentationDisplayId;
        public int status;
        public int volume;
        public int volumeHandling;
        public int volumeMax;

        public RemoteDisplayInfo(RemoteDisplayInfo remoteDisplayInfo) {
            this.id = remoteDisplayInfo.id;
            this.name = remoteDisplayInfo.name;
            this.description = remoteDisplayInfo.description;
            this.status = remoteDisplayInfo.status;
            this.volume = remoteDisplayInfo.volume;
            this.volumeMax = remoteDisplayInfo.volumeMax;
            this.volumeHandling = remoteDisplayInfo.volumeHandling;
            this.presentationDisplayId = remoteDisplayInfo.presentationDisplayId;
        }

        RemoteDisplayInfo(Parcel parcel) {
            this.id = parcel.readString();
            this.name = parcel.readString();
            this.description = parcel.readString();
            this.status = parcel.readInt();
            this.volume = parcel.readInt();
            this.volumeMax = parcel.readInt();
            this.volumeHandling = parcel.readInt();
            this.presentationDisplayId = parcel.readInt();
        }

        public RemoteDisplayInfo(String string2) {
            this.id = string2;
            this.status = 0;
            this.volumeHandling = 0;
            this.presentationDisplayId = -1;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public boolean isValid() {
            boolean bl = !TextUtils.isEmpty(this.id) && !TextUtils.isEmpty(this.name);
            return bl;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("RemoteDisplayInfo{ id=");
            stringBuilder.append(this.id);
            stringBuilder.append(", name=");
            stringBuilder.append(this.name);
            stringBuilder.append(", description=");
            stringBuilder.append(this.description);
            stringBuilder.append(", status=");
            stringBuilder.append(this.status);
            stringBuilder.append(", volume=");
            stringBuilder.append(this.volume);
            stringBuilder.append(", volumeMax=");
            stringBuilder.append(this.volumeMax);
            stringBuilder.append(", volumeHandling=");
            stringBuilder.append(this.volumeHandling);
            stringBuilder.append(", presentationDisplayId=");
            stringBuilder.append(this.presentationDisplayId);
            stringBuilder.append(" }");
            return stringBuilder.toString();
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeString(this.id);
            parcel.writeString(this.name);
            parcel.writeString(this.description);
            parcel.writeInt(this.status);
            parcel.writeInt(this.volume);
            parcel.writeInt(this.volumeMax);
            parcel.writeInt(this.volumeHandling);
            parcel.writeInt(this.presentationDisplayId);
        }

    }

}

