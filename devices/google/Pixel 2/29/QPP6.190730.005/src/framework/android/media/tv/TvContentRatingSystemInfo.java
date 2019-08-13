/*
 * Decompiled with CFR 0.145.
 */
package android.media.tv;

import android.annotation.SystemApi;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

@SystemApi
public final class TvContentRatingSystemInfo
implements Parcelable {
    public static final Parcelable.Creator<TvContentRatingSystemInfo> CREATOR = new Parcelable.Creator<TvContentRatingSystemInfo>(){

        @Override
        public TvContentRatingSystemInfo createFromParcel(Parcel parcel) {
            return new TvContentRatingSystemInfo(parcel);
        }

        public TvContentRatingSystemInfo[] newArray(int n) {
            return new TvContentRatingSystemInfo[n];
        }
    };
    private final ApplicationInfo mApplicationInfo;
    private final Uri mXmlUri;

    private TvContentRatingSystemInfo(Uri uri, ApplicationInfo applicationInfo) {
        this.mXmlUri = uri;
        this.mApplicationInfo = applicationInfo;
    }

    private TvContentRatingSystemInfo(Parcel parcel) {
        this.mXmlUri = (Uri)parcel.readParcelable(null);
        this.mApplicationInfo = (ApplicationInfo)parcel.readParcelable(null);
    }

    public static final TvContentRatingSystemInfo createTvContentRatingSystemInfo(int n, ApplicationInfo applicationInfo) {
        return new TvContentRatingSystemInfo(new Uri.Builder().scheme("android.resource").authority(applicationInfo.packageName).appendPath(String.valueOf(n)).build(), applicationInfo);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public final Uri getXmlUri() {
        return this.mXmlUri;
    }

    public final boolean isSystemDefined() {
        int n = this.mApplicationInfo.flags;
        boolean bl = true;
        if ((n & 1) == 0) {
            bl = false;
        }
        return bl;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeParcelable(this.mXmlUri, n);
        parcel.writeParcelable(this.mApplicationInfo, n);
    }

}

