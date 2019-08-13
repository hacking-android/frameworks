/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.mbms;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.mbms.FileInfo;
import android.telephony.mbms.ServiceInfo;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public final class FileServiceInfo
extends ServiceInfo
implements Parcelable {
    public static final Parcelable.Creator<FileServiceInfo> CREATOR = new Parcelable.Creator<FileServiceInfo>(){

        @Override
        public FileServiceInfo createFromParcel(Parcel parcel) {
            return new FileServiceInfo(parcel);
        }

        public FileServiceInfo[] newArray(int n) {
            return new FileServiceInfo[n];
        }
    };
    private final List<FileInfo> files;

    FileServiceInfo(Parcel parcel) {
        super(parcel);
        this.files = new ArrayList<FileInfo>();
        parcel.readList(this.files, FileInfo.class.getClassLoader());
    }

    @SystemApi
    public FileServiceInfo(Map<Locale, String> map, String string2, List<Locale> list, String string3, Date date, Date date2, List<FileInfo> list2) {
        super(map, string2, list, string3, date, date2);
        this.files = new ArrayList<FileInfo>(list2);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public List<FileInfo> getFiles() {
        return this.files;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        super.writeToParcel(parcel, n);
        parcel.writeList(this.files);
    }

}

