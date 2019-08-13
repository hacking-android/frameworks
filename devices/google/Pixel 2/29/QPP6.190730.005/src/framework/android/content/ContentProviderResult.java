/*
 * Decompiled with CFR 0.145.
 */
package android.content;

import android.content.ContentProvider;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.Preconditions;

public class ContentProviderResult
implements Parcelable {
    public static final Parcelable.Creator<ContentProviderResult> CREATOR = new Parcelable.Creator<ContentProviderResult>(){

        @Override
        public ContentProviderResult createFromParcel(Parcel parcel) {
            return new ContentProviderResult(parcel);
        }

        public ContentProviderResult[] newArray(int n) {
            return new ContentProviderResult[n];
        }
    };
    public final Integer count;
    public final String failure;
    public final Uri uri;

    public ContentProviderResult(int n) {
        this(null, n, null);
    }

    public ContentProviderResult(ContentProviderResult contentProviderResult, int n) {
        this.uri = ContentProvider.maybeAddUserId(contentProviderResult.uri, n);
        this.count = contentProviderResult.count;
        this.failure = contentProviderResult.failure;
    }

    public ContentProviderResult(Uri uri) {
        this(Preconditions.checkNotNull(uri), null, null);
    }

    public ContentProviderResult(Uri uri, Integer n, String string2) {
        this.uri = uri;
        this.count = n;
        this.failure = string2;
    }

    public ContentProviderResult(Parcel parcel) {
        this.uri = parcel.readInt() != 0 ? Uri.CREATOR.createFromParcel(parcel) : null;
        this.count = parcel.readInt() != 0 ? Integer.valueOf(parcel.readInt()) : null;
        this.failure = parcel.readInt() != 0 ? parcel.readString() : null;
    }

    public ContentProviderResult(String string2) {
        this(null, null, string2);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String toString() {
        StringBuilder stringBuilder;
        StringBuilder stringBuilder2 = new StringBuilder("ContentProviderResult(");
        if (this.uri != null) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("uri=");
            stringBuilder.append(this.uri);
            stringBuilder.append(" ");
            stringBuilder2.append(stringBuilder.toString());
        }
        if (this.count != null) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("count=");
            stringBuilder.append(this.count);
            stringBuilder.append(" ");
            stringBuilder2.append(stringBuilder.toString());
        }
        if (this.uri != null) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("failure=");
            stringBuilder.append(this.failure);
            stringBuilder.append(" ");
            stringBuilder2.append(stringBuilder.toString());
        }
        stringBuilder2.deleteCharAt(stringBuilder2.length() - 1);
        stringBuilder2.append(")");
        return stringBuilder2.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        if (this.uri != null) {
            parcel.writeInt(1);
            this.uri.writeToParcel(parcel, n);
        } else {
            parcel.writeInt(0);
        }
        if (this.count != null) {
            parcel.writeInt(1);
            parcel.writeInt(this.count);
        } else {
            parcel.writeInt(0);
        }
        if (this.failure != null) {
            parcel.writeInt(1);
            parcel.writeString(this.failure);
        } else {
            parcel.writeInt(0);
        }
    }

}

