/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.mbms;

import android.annotation.SystemApi;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

@SystemApi
public final class UriPathPair
implements Parcelable {
    public static final Parcelable.Creator<UriPathPair> CREATOR = new Parcelable.Creator<UriPathPair>(){

        @Override
        public UriPathPair createFromParcel(Parcel parcel) {
            return new UriPathPair(parcel);
        }

        public UriPathPair[] newArray(int n) {
            return new UriPathPair[n];
        }
    };
    private final Uri mContentUri;
    private final Uri mFilePathUri;

    public UriPathPair(Uri uri, Uri uri2) {
        if (uri != null && "file".equals(uri.getScheme())) {
            if (uri2 != null && "content".equals(uri2.getScheme())) {
                this.mFilePathUri = uri;
                this.mContentUri = uri2;
                return;
            }
            throw new IllegalArgumentException("Content URI must have content scheme");
        }
        throw new IllegalArgumentException("File URI must have file scheme");
    }

    private UriPathPair(Parcel parcel) {
        this.mFilePathUri = (Uri)parcel.readParcelable(Uri.class.getClassLoader());
        this.mContentUri = (Uri)parcel.readParcelable(Uri.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Uri getContentUri() {
        return this.mContentUri;
    }

    public Uri getFilePathUri() {
        return this.mFilePathUri;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeParcelable(this.mFilePathUri, n);
        parcel.writeParcelable(this.mContentUri, n);
    }

}

