/*
 * Decompiled with CFR 0.145.
 */
package android.webkit;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.content.pm.Signature;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;

@SystemApi
public final class WebViewProviderInfo
implements Parcelable {
    public static final Parcelable.Creator<WebViewProviderInfo> CREATOR = new Parcelable.Creator<WebViewProviderInfo>(){

        @Override
        public WebViewProviderInfo createFromParcel(Parcel parcel) {
            return new WebViewProviderInfo(parcel);
        }

        public WebViewProviderInfo[] newArray(int n) {
            return new WebViewProviderInfo[n];
        }
    };
    public final boolean availableByDefault;
    public final String description;
    public final boolean isFallback;
    public final String packageName;
    public final Signature[] signatures;

    @UnsupportedAppUsage
    private WebViewProviderInfo(Parcel parcel) {
        this.packageName = parcel.readString();
        this.description = parcel.readString();
        int n = parcel.readInt();
        boolean bl = true;
        boolean bl2 = n > 0;
        this.availableByDefault = bl2;
        bl2 = parcel.readInt() > 0 ? bl : false;
        this.isFallback = bl2;
        this.signatures = parcel.createTypedArray(Signature.CREATOR);
    }

    public WebViewProviderInfo(String string2, String string3, boolean bl, boolean bl2, String[] arrstring) {
        this.packageName = string2;
        this.description = string3;
        this.availableByDefault = bl;
        this.isFallback = bl2;
        if (arrstring == null) {
            this.signatures = new Signature[0];
        } else {
            this.signatures = new Signature[arrstring.length];
            for (int i = 0; i < arrstring.length; ++i) {
                this.signatures[i] = new Signature(Base64.decode(arrstring[i], 0));
            }
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.packageName);
        parcel.writeString(this.description);
        parcel.writeInt((int)this.availableByDefault);
        parcel.writeInt((int)this.isFallback);
        parcel.writeTypedArray((Parcelable[])this.signatures, 0);
    }

}

