/*
 * Decompiled with CFR 0.145.
 */
package android.content;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public final class UriPermission
implements Parcelable {
    public static final Parcelable.Creator<UriPermission> CREATOR = new Parcelable.Creator<UriPermission>(){

        @Override
        public UriPermission createFromParcel(Parcel parcel) {
            return new UriPermission(parcel);
        }

        public UriPermission[] newArray(int n) {
            return new UriPermission[n];
        }
    };
    public static final long INVALID_TIME = Long.MIN_VALUE;
    private final int mModeFlags;
    private final long mPersistedTime;
    private final Uri mUri;

    public UriPermission(Uri uri, int n, long l) {
        this.mUri = uri;
        this.mModeFlags = n;
        this.mPersistedTime = l;
    }

    public UriPermission(Parcel parcel) {
        this.mUri = (Uri)parcel.readParcelable(null);
        this.mModeFlags = parcel.readInt();
        this.mPersistedTime = parcel.readLong();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public long getPersistedTime() {
        return this.mPersistedTime;
    }

    public Uri getUri() {
        return this.mUri;
    }

    public boolean isReadPermission() {
        int n = this.mModeFlags;
        boolean bl = true;
        if ((n & 1) == 0) {
            bl = false;
        }
        return bl;
    }

    public boolean isWritePermission() {
        boolean bl = (this.mModeFlags & 2) != 0;
        return bl;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("UriPermission {uri=");
        stringBuilder.append(this.mUri);
        stringBuilder.append(", modeFlags=");
        stringBuilder.append(this.mModeFlags);
        stringBuilder.append(", persistedTime=");
        stringBuilder.append(this.mPersistedTime);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeParcelable(this.mUri, n);
        parcel.writeInt(this.mModeFlags);
        parcel.writeLong(this.mPersistedTime);
    }

}

