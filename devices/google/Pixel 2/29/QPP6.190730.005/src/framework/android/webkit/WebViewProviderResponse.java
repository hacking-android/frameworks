/*
 * Decompiled with CFR 0.145.
 */
package android.webkit;

import android.annotation.UnsupportedAppUsage;
import android.content.pm.PackageInfo;
import android.os.Parcel;
import android.os.Parcelable;

public final class WebViewProviderResponse
implements Parcelable {
    public static final Parcelable.Creator<WebViewProviderResponse> CREATOR = new Parcelable.Creator<WebViewProviderResponse>(){

        @Override
        public WebViewProviderResponse createFromParcel(Parcel parcel) {
            return new WebViewProviderResponse(parcel);
        }

        public WebViewProviderResponse[] newArray(int n) {
            return new WebViewProviderResponse[n];
        }
    };
    @UnsupportedAppUsage
    public final PackageInfo packageInfo;
    public final int status;

    public WebViewProviderResponse(PackageInfo packageInfo, int n) {
        this.packageInfo = packageInfo;
        this.status = n;
    }

    private WebViewProviderResponse(Parcel parcel) {
        this.packageInfo = parcel.readTypedObject(PackageInfo.CREATOR);
        this.status = parcel.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeTypedObject(this.packageInfo, n);
        parcel.writeInt(this.status);
    }

}

