/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

public class MediaDescription
implements Parcelable {
    public static final long BT_FOLDER_TYPE_ALBUMS = 2L;
    public static final long BT_FOLDER_TYPE_ARTISTS = 3L;
    public static final long BT_FOLDER_TYPE_GENRES = 4L;
    public static final long BT_FOLDER_TYPE_MIXED = 0L;
    public static final long BT_FOLDER_TYPE_PLAYLISTS = 5L;
    public static final long BT_FOLDER_TYPE_TITLES = 1L;
    public static final long BT_FOLDER_TYPE_YEARS = 6L;
    public static final Parcelable.Creator<MediaDescription> CREATOR = new Parcelable.Creator<MediaDescription>(){

        @Override
        public MediaDescription createFromParcel(Parcel parcel) {
            return new MediaDescription(parcel);
        }

        public MediaDescription[] newArray(int n) {
            return new MediaDescription[n];
        }
    };
    public static final String EXTRA_BT_FOLDER_TYPE = "android.media.extra.BT_FOLDER_TYPE";
    private final CharSequence mDescription;
    private final Bundle mExtras;
    private final Bitmap mIcon;
    private final Uri mIconUri;
    private final String mMediaId;
    private final Uri mMediaUri;
    private final CharSequence mSubtitle;
    private final CharSequence mTitle;

    private MediaDescription(Parcel parcel) {
        this.mMediaId = parcel.readString();
        this.mTitle = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mSubtitle = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mDescription = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mIcon = (Bitmap)parcel.readParcelable(null);
        this.mIconUri = (Uri)parcel.readParcelable(null);
        this.mExtras = parcel.readBundle();
        this.mMediaUri = (Uri)parcel.readParcelable(null);
    }

    private MediaDescription(String string2, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, Bitmap bitmap, Uri uri, Bundle bundle, Uri uri2) {
        this.mMediaId = string2;
        this.mTitle = charSequence;
        this.mSubtitle = charSequence2;
        this.mDescription = charSequence3;
        this.mIcon = bitmap;
        this.mIconUri = uri;
        this.mExtras = bundle;
        this.mMediaUri = uri2;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (!(object instanceof MediaDescription)) {
            return false;
        }
        object = (MediaDescription)object;
        if (!String.valueOf(this.mTitle).equals(String.valueOf(((MediaDescription)object).mTitle))) {
            return false;
        }
        if (!String.valueOf(this.mSubtitle).equals(String.valueOf(((MediaDescription)object).mSubtitle))) {
            return false;
        }
        return String.valueOf(this.mDescription).equals(String.valueOf(((MediaDescription)object).mDescription));
    }

    public CharSequence getDescription() {
        return this.mDescription;
    }

    public Bundle getExtras() {
        return this.mExtras;
    }

    public Bitmap getIconBitmap() {
        return this.mIcon;
    }

    public Uri getIconUri() {
        return this.mIconUri;
    }

    public String getMediaId() {
        return this.mMediaId;
    }

    public Uri getMediaUri() {
        return this.mMediaUri;
    }

    public CharSequence getSubtitle() {
        return this.mSubtitle;
    }

    public CharSequence getTitle() {
        return this.mTitle;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append((Object)this.mTitle);
        stringBuilder.append(", ");
        stringBuilder.append((Object)this.mSubtitle);
        stringBuilder.append(", ");
        stringBuilder.append((Object)this.mDescription);
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mMediaId);
        TextUtils.writeToParcel(this.mTitle, parcel, 0);
        TextUtils.writeToParcel(this.mSubtitle, parcel, 0);
        TextUtils.writeToParcel(this.mDescription, parcel, 0);
        parcel.writeParcelable(this.mIcon, n);
        parcel.writeParcelable(this.mIconUri, n);
        parcel.writeBundle(this.mExtras);
        parcel.writeParcelable(this.mMediaUri, n);
    }

    public static class Builder {
        private CharSequence mDescription;
        private Bundle mExtras;
        private Bitmap mIcon;
        private Uri mIconUri;
        private String mMediaId;
        private Uri mMediaUri;
        private CharSequence mSubtitle;
        private CharSequence mTitle;

        public MediaDescription build() {
            return new MediaDescription(this.mMediaId, this.mTitle, this.mSubtitle, this.mDescription, this.mIcon, this.mIconUri, this.mExtras, this.mMediaUri);
        }

        public Builder setDescription(CharSequence charSequence) {
            this.mDescription = charSequence;
            return this;
        }

        public Builder setExtras(Bundle bundle) {
            this.mExtras = bundle;
            return this;
        }

        public Builder setIconBitmap(Bitmap bitmap) {
            this.mIcon = bitmap;
            return this;
        }

        public Builder setIconUri(Uri uri) {
            this.mIconUri = uri;
            return this;
        }

        public Builder setMediaId(String string2) {
            this.mMediaId = string2;
            return this;
        }

        public Builder setMediaUri(Uri uri) {
            this.mMediaUri = uri;
            return this;
        }

        public Builder setSubtitle(CharSequence charSequence) {
            this.mSubtitle = charSequence;
            return this;
        }

        public Builder setTitle(CharSequence charSequence) {
            this.mTitle = charSequence;
            return this;
        }
    }

}

