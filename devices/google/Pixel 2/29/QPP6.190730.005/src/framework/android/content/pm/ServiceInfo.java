/*
 * Decompiled with CFR 0.145.
 */
package android.content.pm;

import android.content.pm.ComponentInfo;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Printer;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class ServiceInfo
extends ComponentInfo
implements Parcelable {
    public static final Parcelable.Creator<ServiceInfo> CREATOR = new Parcelable.Creator<ServiceInfo>(){

        @Override
        public ServiceInfo createFromParcel(Parcel parcel) {
            return new ServiceInfo(parcel);
        }

        public ServiceInfo[] newArray(int n) {
            return new ServiceInfo[n];
        }
    };
    public static final int FLAG_EXTERNAL_SERVICE = 4;
    public static final int FLAG_ISOLATED_PROCESS = 2;
    public static final int FLAG_SINGLE_USER = 1073741824;
    public static final int FLAG_STOP_WITH_TASK = 1;
    public static final int FLAG_USE_APP_ZYGOTE = 8;
    public static final int FLAG_VISIBLE_TO_INSTANT_APP = 1048576;
    public static final int FOREGROUND_SERVICE_TYPE_CONNECTED_DEVICE = 16;
    public static final int FOREGROUND_SERVICE_TYPE_DATA_SYNC = 1;
    public static final int FOREGROUND_SERVICE_TYPE_LOCATION = 8;
    public static final int FOREGROUND_SERVICE_TYPE_MANIFEST = -1;
    public static final int FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK = 2;
    public static final int FOREGROUND_SERVICE_TYPE_MEDIA_PROJECTION = 32;
    public static final int FOREGROUND_SERVICE_TYPE_NONE = 0;
    public static final int FOREGROUND_SERVICE_TYPE_PHONE_CALL = 4;
    public int flags;
    public int mForegroundServiceType = 0;
    public String permission;

    public ServiceInfo() {
    }

    public ServiceInfo(ServiceInfo serviceInfo) {
        super(serviceInfo);
        this.permission = serviceInfo.permission;
        this.flags = serviceInfo.flags;
        this.mForegroundServiceType = serviceInfo.mForegroundServiceType;
    }

    private ServiceInfo(Parcel parcel) {
        super(parcel);
        this.permission = parcel.readString();
        this.flags = parcel.readInt();
        this.mForegroundServiceType = parcel.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void dump(Printer printer, String string2) {
        this.dump(printer, string2, 3);
    }

    void dump(Printer printer, String string2, int n) {
        super.dumpFront(printer, string2);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append("permission=");
        stringBuilder.append(this.permission);
        printer.println(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append("flags=0x");
        stringBuilder.append(Integer.toHexString(this.flags));
        printer.println(stringBuilder.toString());
        super.dumpBack(printer, string2, n);
    }

    public int getForegroundServiceType() {
        return this.mForegroundServiceType;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ServiceInfo{");
        stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
        stringBuilder.append(" ");
        stringBuilder.append(this.name);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        super.writeToParcel(parcel, n);
        parcel.writeString(this.permission);
        parcel.writeInt(this.flags);
        parcel.writeInt(this.mForegroundServiceType);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ForegroundServiceType {
    }

}

