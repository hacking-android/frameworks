/*
 * Decompiled with CFR 0.145.
 */
package android.telecom;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

public class GatewayInfo
implements Parcelable {
    public static final Parcelable.Creator<GatewayInfo> CREATOR = new Parcelable.Creator<GatewayInfo>(){

        @Override
        public GatewayInfo createFromParcel(Parcel parcel) {
            return new GatewayInfo(parcel.readString(), Uri.CREATOR.createFromParcel(parcel), Uri.CREATOR.createFromParcel(parcel));
        }

        public GatewayInfo[] newArray(int n) {
            return new GatewayInfo[n];
        }
    };
    private final Uri mGatewayAddress;
    private final String mGatewayProviderPackageName;
    private final Uri mOriginalAddress;

    public GatewayInfo(String string2, Uri uri, Uri uri2) {
        this.mGatewayProviderPackageName = string2;
        this.mGatewayAddress = uri;
        this.mOriginalAddress = uri2;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Uri getGatewayAddress() {
        return this.mGatewayAddress;
    }

    public String getGatewayProviderPackageName() {
        return this.mGatewayProviderPackageName;
    }

    public Uri getOriginalAddress() {
        return this.mOriginalAddress;
    }

    public boolean isEmpty() {
        boolean bl = TextUtils.isEmpty(this.mGatewayProviderPackageName) || this.mGatewayAddress == null;
        return bl;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mGatewayProviderPackageName);
        this.mGatewayAddress.writeToParcel(parcel, 0);
        this.mOriginalAddress.writeToParcel(parcel, 0);
    }

}

