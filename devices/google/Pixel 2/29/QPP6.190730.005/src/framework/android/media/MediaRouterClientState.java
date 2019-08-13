/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;

public final class MediaRouterClientState
implements Parcelable {
    public static final Parcelable.Creator<MediaRouterClientState> CREATOR = new Parcelable.Creator<MediaRouterClientState>(){

        @Override
        public MediaRouterClientState createFromParcel(Parcel parcel) {
            return new MediaRouterClientState(parcel);
        }

        public MediaRouterClientState[] newArray(int n) {
            return new MediaRouterClientState[n];
        }
    };
    public final ArrayList<RouteInfo> routes;

    public MediaRouterClientState() {
        this.routes = new ArrayList();
    }

    MediaRouterClientState(Parcel parcel) {
        this.routes = parcel.createTypedArrayList(RouteInfo.CREATOR);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public RouteInfo getRoute(String string2) {
        int n = this.routes.size();
        for (int i = 0; i < n; ++i) {
            RouteInfo routeInfo = this.routes.get(i);
            if (!routeInfo.id.equals(string2)) continue;
            return routeInfo;
        }
        return null;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("MediaRouterClientState{ routes=");
        stringBuilder.append(this.routes.toString());
        stringBuilder.append(" }");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeTypedList(this.routes);
    }

    public static final class RouteInfo
    implements Parcelable {
        public static final Parcelable.Creator<RouteInfo> CREATOR = new Parcelable.Creator<RouteInfo>(){

            @Override
            public RouteInfo createFromParcel(Parcel parcel) {
                return new RouteInfo(parcel);
            }

            public RouteInfo[] newArray(int n) {
                return new RouteInfo[n];
            }
        };
        public String description;
        public int deviceType;
        public boolean enabled;
        public String id;
        public String name;
        public int playbackStream;
        public int playbackType;
        public int presentationDisplayId;
        public int statusCode;
        public int supportedTypes;
        public int volume;
        public int volumeHandling;
        public int volumeMax;

        public RouteInfo(RouteInfo routeInfo) {
            this.id = routeInfo.id;
            this.name = routeInfo.name;
            this.description = routeInfo.description;
            this.supportedTypes = routeInfo.supportedTypes;
            this.enabled = routeInfo.enabled;
            this.statusCode = routeInfo.statusCode;
            this.playbackType = routeInfo.playbackType;
            this.playbackStream = routeInfo.playbackStream;
            this.volume = routeInfo.volume;
            this.volumeMax = routeInfo.volumeMax;
            this.volumeHandling = routeInfo.volumeHandling;
            this.presentationDisplayId = routeInfo.presentationDisplayId;
            this.deviceType = routeInfo.deviceType;
        }

        RouteInfo(Parcel parcel) {
            this.id = parcel.readString();
            this.name = parcel.readString();
            this.description = parcel.readString();
            this.supportedTypes = parcel.readInt();
            boolean bl = parcel.readInt() != 0;
            this.enabled = bl;
            this.statusCode = parcel.readInt();
            this.playbackType = parcel.readInt();
            this.playbackStream = parcel.readInt();
            this.volume = parcel.readInt();
            this.volumeMax = parcel.readInt();
            this.volumeHandling = parcel.readInt();
            this.presentationDisplayId = parcel.readInt();
            this.deviceType = parcel.readInt();
        }

        public RouteInfo(String string2) {
            this.id = string2;
            this.enabled = true;
            this.statusCode = 0;
            this.playbackType = 1;
            this.playbackStream = -1;
            this.volumeHandling = 0;
            this.presentationDisplayId = -1;
            this.deviceType = 0;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("RouteInfo{ id=");
            stringBuilder.append(this.id);
            stringBuilder.append(", name=");
            stringBuilder.append(this.name);
            stringBuilder.append(", description=");
            stringBuilder.append(this.description);
            stringBuilder.append(", supportedTypes=0x");
            stringBuilder.append(Integer.toHexString(this.supportedTypes));
            stringBuilder.append(", enabled=");
            stringBuilder.append(this.enabled);
            stringBuilder.append(", statusCode=");
            stringBuilder.append(this.statusCode);
            stringBuilder.append(", playbackType=");
            stringBuilder.append(this.playbackType);
            stringBuilder.append(", playbackStream=");
            stringBuilder.append(this.playbackStream);
            stringBuilder.append(", volume=");
            stringBuilder.append(this.volume);
            stringBuilder.append(", volumeMax=");
            stringBuilder.append(this.volumeMax);
            stringBuilder.append(", volumeHandling=");
            stringBuilder.append(this.volumeHandling);
            stringBuilder.append(", presentationDisplayId=");
            stringBuilder.append(this.presentationDisplayId);
            stringBuilder.append(", deviceType=");
            stringBuilder.append(this.deviceType);
            stringBuilder.append(" }");
            return stringBuilder.toString();
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeString(this.id);
            parcel.writeString(this.name);
            parcel.writeString(this.description);
            parcel.writeInt(this.supportedTypes);
            parcel.writeInt((int)this.enabled);
            parcel.writeInt(this.statusCode);
            parcel.writeInt(this.playbackType);
            parcel.writeInt(this.playbackStream);
            parcel.writeInt(this.volume);
            parcel.writeInt(this.volumeMax);
            parcel.writeInt(this.volumeHandling);
            parcel.writeInt(this.presentationDisplayId);
            parcel.writeInt(this.deviceType);
        }

    }

}

