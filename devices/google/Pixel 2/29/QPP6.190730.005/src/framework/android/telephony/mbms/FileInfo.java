/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.mbms;

import android.annotation.SystemApi;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

public final class FileInfo
implements Parcelable {
    public static final Parcelable.Creator<FileInfo> CREATOR = new Parcelable.Creator<FileInfo>(){

        @Override
        public FileInfo createFromParcel(Parcel parcel) {
            return new FileInfo(parcel);
        }

        public FileInfo[] newArray(int n) {
            return new FileInfo[n];
        }
    };
    private final String mimeType;
    private final Uri uri;

    @SystemApi
    public FileInfo(Uri uri, String string2) {
        this.uri = uri;
        this.mimeType = string2;
    }

    private FileInfo(Parcel parcel) {
        this.uri = (Uri)parcel.readParcelable(null);
        this.mimeType = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object != null && this.getClass() == object.getClass()) {
            object = (FileInfo)object;
            if (!Objects.equals(this.uri, ((FileInfo)object).uri) || !Objects.equals(this.mimeType, ((FileInfo)object).mimeType)) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    public String getMimeType() {
        return this.mimeType;
    }

    public Uri getUri() {
        return this.uri;
    }

    public int hashCode() {
        return Objects.hash(this.uri, this.mimeType);
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeParcelable(this.uri, n);
        parcel.writeString(this.mimeType);
    }

}

