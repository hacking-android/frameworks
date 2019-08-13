/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class GrantedUriPermission
implements Parcelable {
    public static final Parcelable.Creator<GrantedUriPermission> CREATOR = new Parcelable.Creator<GrantedUriPermission>(){

        @Override
        public GrantedUriPermission createFromParcel(Parcel parcel) {
            return new GrantedUriPermission(parcel);
        }

        public GrantedUriPermission[] newArray(int n) {
            return new GrantedUriPermission[n];
        }
    };
    public final String packageName;
    public final Uri uri;

    public GrantedUriPermission(Uri uri, String string2) {
        this.uri = uri;
        this.packageName = string2;
    }

    private GrantedUriPermission(Parcel parcel) {
        this.uri = (Uri)parcel.readParcelable(null);
        this.packageName = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.packageName);
        stringBuilder.append(":");
        stringBuilder.append(this.uri);
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeParcelable(this.uri, n);
        parcel.writeString(this.packageName);
    }

}

