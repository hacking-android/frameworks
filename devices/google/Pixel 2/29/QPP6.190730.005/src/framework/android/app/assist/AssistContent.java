/*
 * Decompiled with CFR 0.145.
 */
package android.app.assist;

import android.annotation.UnsupportedAppUsage;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public class AssistContent
implements Parcelable {
    public static final Parcelable.Creator<AssistContent> CREATOR = new Parcelable.Creator<AssistContent>(){

        @Override
        public AssistContent createFromParcel(Parcel parcel) {
            return new AssistContent(parcel);
        }

        public AssistContent[] newArray(int n) {
            return new AssistContent[n];
        }
    };
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private ClipData mClipData;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private final Bundle mExtras;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private Intent mIntent;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private boolean mIsAppProvidedIntent;
    private boolean mIsAppProvidedWebUri;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private String mStructuredData;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private Uri mUri;

    public AssistContent() {
        this.mIsAppProvidedIntent = false;
        this.mIsAppProvidedWebUri = false;
        this.mExtras = new Bundle();
    }

    @UnsupportedAppUsage
    AssistContent(Parcel parcel) {
        boolean bl = false;
        this.mIsAppProvidedIntent = false;
        this.mIsAppProvidedWebUri = false;
        if (parcel.readInt() != 0) {
            this.mIntent = Intent.CREATOR.createFromParcel(parcel);
        }
        if (parcel.readInt() != 0) {
            this.mClipData = ClipData.CREATOR.createFromParcel(parcel);
        }
        if (parcel.readInt() != 0) {
            this.mUri = Uri.CREATOR.createFromParcel(parcel);
        }
        if (parcel.readInt() != 0) {
            this.mStructuredData = parcel.readString();
        }
        boolean bl2 = parcel.readInt() == 1;
        this.mIsAppProvidedIntent = bl2;
        this.mExtras = parcel.readBundle();
        bl2 = bl;
        if (parcel.readInt() == 1) {
            bl2 = true;
        }
        this.mIsAppProvidedWebUri = bl2;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public ClipData getClipData() {
        return this.mClipData;
    }

    public Bundle getExtras() {
        return this.mExtras;
    }

    public Intent getIntent() {
        return this.mIntent;
    }

    public String getStructuredData() {
        return this.mStructuredData;
    }

    public Uri getWebUri() {
        return this.mUri;
    }

    public boolean isAppProvidedIntent() {
        return this.mIsAppProvidedIntent;
    }

    public boolean isAppProvidedWebUri() {
        return this.mIsAppProvidedWebUri;
    }

    public void setClipData(ClipData clipData) {
        this.mClipData = clipData;
    }

    public void setDefaultIntent(Intent parcelable) {
        this.mIntent = parcelable;
        this.mIsAppProvidedIntent = false;
        this.mIsAppProvidedWebUri = false;
        this.mUri = null;
        if (parcelable != null && "android.intent.action.VIEW".equals(((Intent)parcelable).getAction()) && (parcelable = ((Intent)parcelable).getData()) != null && ("http".equals(((Uri)parcelable).getScheme()) || "https".equals(((Uri)parcelable).getScheme()))) {
            this.mUri = parcelable;
        }
    }

    public void setIntent(Intent intent) {
        this.mIsAppProvidedIntent = true;
        this.mIntent = intent;
    }

    public void setStructuredData(String string2) {
        this.mStructuredData = string2;
    }

    public void setWebUri(Uri uri) {
        this.mIsAppProvidedWebUri = true;
        this.mUri = uri;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        this.writeToParcelInternal(parcel, n);
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    void writeToParcelInternal(Parcel parcel, int n) {
        if (this.mIntent != null) {
            parcel.writeInt(1);
            this.mIntent.writeToParcel(parcel, n);
        } else {
            parcel.writeInt(0);
        }
        if (this.mClipData != null) {
            parcel.writeInt(1);
            this.mClipData.writeToParcel(parcel, n);
        } else {
            parcel.writeInt(0);
        }
        if (this.mUri != null) {
            parcel.writeInt(1);
            this.mUri.writeToParcel(parcel, n);
        } else {
            parcel.writeInt(0);
        }
        if (this.mStructuredData != null) {
            parcel.writeInt(1);
            parcel.writeString(this.mStructuredData);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeInt((int)this.mIsAppProvidedIntent);
        parcel.writeBundle(this.mExtras);
        parcel.writeInt((int)this.mIsAppProvidedWebUri);
    }

}

