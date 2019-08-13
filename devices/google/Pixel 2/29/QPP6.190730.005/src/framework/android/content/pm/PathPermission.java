/*
 * Decompiled with CFR 0.145.
 */
package android.content.pm;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.PatternMatcher;

public class PathPermission
extends PatternMatcher {
    public static final Parcelable.Creator<PathPermission> CREATOR = new Parcelable.Creator<PathPermission>(){

        @Override
        public PathPermission createFromParcel(Parcel parcel) {
            return new PathPermission(parcel);
        }

        public PathPermission[] newArray(int n) {
            return new PathPermission[n];
        }
    };
    private final String mReadPermission;
    private final String mWritePermission;

    public PathPermission(Parcel parcel) {
        super(parcel);
        this.mReadPermission = parcel.readString();
        this.mWritePermission = parcel.readString();
    }

    public PathPermission(String string2, int n, String string3, String string4) {
        super(string2, n);
        this.mReadPermission = string3;
        this.mWritePermission = string4;
    }

    public String getReadPermission() {
        return this.mReadPermission;
    }

    public String getWritePermission() {
        return this.mWritePermission;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        super.writeToParcel(parcel, n);
        parcel.writeString(this.mReadPermission);
        parcel.writeString(this.mWritePermission);
    }

}

